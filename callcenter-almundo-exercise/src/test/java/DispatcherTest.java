import com.almundo.bean.Empleado;
import com.almundo.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Se reutiliza implementación de atención de llamadas
 * para el control de colas y prueba de las clases ExecutorService, PriorityQueue,
 * Se realiza ajuste por la implementación de Patron Singleton.
 *
 * @author Jefrey Padilla
 */
public class DispatcherTest {

    private static Logger logger = LoggerFactory.getLogger(DispatcherTest.class);

    private ExecutorService executorService;
    private PriorityQueue<Empleado> priorityQueue;
    private int cantQueueCall;
    private int cantAtendidos;

    /**
     * Inicialización de DispatcherTest
     * @param concurrencias Control de cantidad de concurrencias
     * @param listEmpleados Listado de empleados
     */
    public DispatcherTest(int concurrencias, List<Empleado> listEmpleados){
        logger.info("Inicializando DispatcherTest");
        this.executorService = Executors.newFixedThreadPool(concurrencias);
        initEmpleadoQueue();
        listEmpleados.forEach(x -> priorityQueue.add(x));
        cantAtendidos = 0;
    }

    /**
     * Método dispatchCall
     */
    public void dispatchCall(){
        logger.info("Llamando dispatchCall");
        Empleado empleado = getEmpleadoQueue();

        if(empleado != null) {
            // Se implementa ExecutorService para el control de las concurrencias y ejecución interna de los hilos
            this.executorService.submit(iniciarLlamada(empleado, Utils.getDuracionLlamada()));
            cantAtendidos++;
        } else {
            // En caso de no haber disponibilidad se envia un mensaje a la vista, informando la cantidad de turnos que se deben esperar
            logger.info("No se encontró empleado disponible, se envia a la cola de espera");
        }

    }

    /**
     * Metodo para obtener el Empleado con prioridad
     * @return
     */
    private Empleado getEmpleadoQueue(){

        if (!priorityQueue.isEmpty()) {
            Empleado emp = priorityQueue.remove();
            logger.info("Empleado DispatcherTest: enviando empleado: " + emp);
            printPriorityQueue();
            return emp;
        } else {
            cantQueueCall++;
        }

        return null;
    }

    /**
     * Inicialización de lista PriorityQueue
     */
    private void initEmpleadoQueue(){
        priorityQueue = new PriorityQueue<Empleado>(
            new Comparator<Empleado>() {
                @Override
                public int compare(Empleado empleado, Empleado t1) {
                    return empleado.getPrioridadTipoEmpleado() - t1.getPrioridadTipoEmpleado();
                }
            }
                );
    }

    /**
     * Imprimir lista PriorityQueue
     */
    private void printPriorityQueue(){
        priorityQueue.forEach(System.out::println);
    }

    /**
     * Metodo para inicar hilo de ejecución (Llamada)
     * @param empleado Empleado de atención
     * @param duracionLlamada Duración de la llamada
     * @return
     */
    private Runnable iniciarLlamada(Empleado empleado, int duracionLlamada) {

        logger.info("Iniciando metodo iniciarLlamada, Params: Empleado: " + empleado + " Duración llamada: " + duracionLlamada);

        Runnable runnable = () -> {
            try {
                logger.info("Iniciando hilo : " + Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(duracionLlamada); // Variable de control del tiempo de la llamada
                adicionarEmpleadoQueue(empleado);
                logger.info("Finalizando hilo : " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                logger.error("Ocurrio un error en la ejecución de hilo: " + Thread.currentThread().getName(), e);
            }
        };

        return runnable;

    }

    /**
     * Metodo para adicionar empleado a PriorityQueue cuando este haya terminado la llamada
     * @param empleado
     */
    private void adicionarEmpleadoQueue(Empleado empleado){
        logger.info("Adicionando a la cola Empleado: " + empleado);
        priorityQueue.add(empleado);
        printPriorityQueue();
    }

    private void asignarEmpleado() {

        logger.info("Ejecutando asignarEmpleado Job");
        logger.info("Cola de peticiones: " + cantQueueCall);

        if(cantQueueCall > 0) {

            for (int i = 0; i < cantQueueCall; i++) {
                try {

                    dispatchCall();
                    callCantQueue();

                } catch (Exception ex) {
                    logger.error("Error asignarEmpleado job", ex);
                }
            }

        }

    }

    public void callCantQueue(){
        if(cantQueueCall > 0){
            cantQueueCall--;
        }
    }

    public int getCantQueueCall() {
        return cantQueueCall;
    }

    public int getCantAtendidos() {
        return cantAtendidos;
    }

}
