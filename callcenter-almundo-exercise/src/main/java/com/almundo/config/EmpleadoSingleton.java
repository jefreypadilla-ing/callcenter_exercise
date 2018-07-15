package com.almundo.config;

import com.almundo.bean.Empleado;
import com.almundo.exception.EmpleadoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author Jefrey Padilla
 */
public enum EmpleadoSingleton {
    INSTANCE;

    private static Logger logger = LoggerFactory.getLogger(EmpleadoSingleton.class);

    private Map<String, Empleado> empleadoTipo;
    private PriorityQueue<Empleado> priorityQueue;
    private boolean queueBol;
    private int cantQueueCall;

    /**
     * Constructor de la clase para la instancia de la lista de empleados, y
     * la inicialización de la cola de espera de peticiones de llamadas
     */
    EmpleadoSingleton() {empleadoTipo = new HashMap<>(); cantQueueCall=0;}

    /**
     * Metodo para el decremento del listado de llamadas en espera
     */
    public void callCantQueue(){
        if(cantQueueCall > 0){
            cantQueueCall--;
        }
    }

    public int getCantQueueCall(){
        return cantQueueCall;
    }

    /**
     * Meotodo para obtener el listado de empleados que se listan en /home
     * @return
     */
    public List<Empleado> getListEmpleado(){
        return new ArrayList<Empleado>(empleadoTipo.values());
    }

    /**
     * Metodo para obtener la disponibilidad de empleados a nivel de vista /home
     * @return
     */
    public List<Empleado> getListEmpleadosView(){

        if(priorityQueue == null){
            return getListEmpleado();
        }

        List<Empleado> list = getListEmpleado();
        for (Empleado e: list){
            boolean found = false;
            for(Empleado x: priorityQueue){
                if(e.getCodigo().equalsIgnoreCase(x.getCodigo())){
                    found = true;
                }
            }
            if(found){
                e.setDisponibilidad("Disponible");
            } else {
                e.setDisponibilidad("No Disponible");
            }
        }

        return list;

    }

    /**
     * Metodo que se encarga de la carga de los empleados en la lista empleadoTipo
     * @param emp
     * @throws EmpleadoException
     */
    public void setEmpleado(Empleado emp) throws EmpleadoException {
        if(empleadoTipo.containsKey(emp.getCodigo())){throw new EmpleadoException("Código encontrado");}
        empleadoTipo.put(emp.getCodigo(), emp);
    }

    /**
     * Metodo para la carga del metodo de priorización de la lista de empleados por tipo(Operario, Supervisor, Director)
     */
    protected void initEmpleadoQueue(){
        queueBol = true;
        priorityQueue = new PriorityQueue<Empleado>(
                new Comparator<Empleado>() {
                    @Override
                    public int compare(Empleado empleado, Empleado t1) {
                        return empleado.getPrioridadTipoEmpleado() - t1.getPrioridadTipoEmpleado();
                    }
                }
        );

        getListEmpleado().forEach(x -> priorityQueue.add(x));
    }

    /**
     * Metodo que se encarga de remover de la lista de empleados por prioridad
     * y realizar el llamado para el metodo dispatchCall
     * @return
     */
    public Empleado getEmpleadoQueue(){

        if(!queueBol){
            initEmpleadoQueue();
        }

        if (!priorityQueue.isEmpty()) {
            Empleado emp = priorityQueue.remove();
            logger.info("Empleado singleton: enviando empleado: " + emp);
            printPriorityQueue();
            return emp;
        } else {
            cantQueueCall++;
        }

        return null;
    }

    /**
     * Metodo que se encarga de reasignar al empleado a la lista de prioridades
     * cuando este ha terminado su atención de la llamada
     * @param empleado
     */
    public void adicionarEmpleadoQueue(Empleado empleado){
        logger.info("Adicionando a la cola Empleado: " + empleado);
        priorityQueue.add(empleado);
        printPriorityQueue();
    }

    /**
     * Metodo para imprimir la lista de prioridad en logger
     */
    protected void printPriorityQueue(){
        priorityQueue.forEach(System.out::println);
    }

}
