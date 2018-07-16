import static org.junit.Assert.assertEquals;

import com.almundo.bean.Empleado;
import com.almundo.dao.DispatcherDao;
import com.almundo.dao.InitDao;
import com.almundo.job.QueueJob;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:mvc-dispatcher-servlet.xml")
@WebAppConfiguration
public class DispatchCallTest {

    private static Logger logger = LoggerFactory.getLogger(DispatchCallTest.class);

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private InitDao initDao;

    @Autowired
    private DispatcherDao dispatcherDao;

    @Autowired
    private QueueJob queueJob;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();
    }

    /**
     * Metodo de prueba con 10 llamadas simultaneas
     * En este caso de prueba se evaluan los procesos internos
     * en la ejecución del metodo dispatchCall usando las listas
     * como atributos definidas en la clase EmpleadoSingleton
     */
    @Test
    public void testDispatchCall(){

        try {
            logger.info("init testDispatchCall");
            initDao.loadData();
            for (int i=0; i<10; i++){
                dispatcherDao.dispatchCall();
            }

            int[] v = dispatcherDao.cierreExecutorService();
            int cantidadAtentidos = v[0];

            logger.info("Finish testDispatchCall, Cantidad Atendidos: " +cantidadAtentidos);

            assertEquals(cantidadAtentidos, 10);

        } catch (Exception ex){
            logger.error("Error testDispatchCall: " , ex);
        }

    }

    /**
     * Metodo de prueba de colas con 20 llamadas simultaneas
     * En este caso de prueba se reutilizan los metodos y las clases
     * usadas en el proceso principal, para realizar las pruebas unitarias
     * con las clases PriorityQueue, ExecutorService.
     *
     * Se realizan pruebas controladas con un numero de 20 llamadas, con una
     * cantidad limitada de 20 concurrencias, por lo que los resultados esperados
     * seran 10 llamadas en cola, 10 llamadas atendidas.
     */
    @Test
    public void testDispatchCallQueue(){

        try {

            logger.info("init testDispatchCallQueue");

            DispatcherTest dispatcherTest = new DispatcherTest(10, getListEmpleados());

            for (int i=0; i<20; i++){
                dispatcherTest.dispatchCall();
            }

            logger.info("Finish testDispatchCallQueue, Cantidad Atendidos: " + dispatcherTest.getCantAtendidos() +
            ", Cantidad Queue: " + dispatcherTest.getCantQueueCall());

            assertEquals(dispatcherTest.getCantQueueCall() + dispatcherTest.getCantAtendidos(), 20);

        } catch (Exception ex){
            logger.error("Error testDispatchCallQueue: " , ex);
        }

    }

    private List<Empleado> getListEmpleados(){

        return Arrays.asList(
            // Operadores
            new Empleado("1", "Jefrey", "Padilla","Operador", 1, "Disponible"),
            new Empleado("2", "Juan", "Gonzalez", "Operador", 1, "Disponible"),
            new Empleado("3", "Pedro", "Torres", "Operador", 1, "Disponible"),
            new Empleado("4", "Alberto", "Ramirez", "Operador", 1, "Disponible"),
            new Empleado("5", "Jaime", "Perez", "Operador", 1, "Disponible"),
            new Empleado("6", "Alonso", "Mendoza", "Operador", 1, "Disponible"),

            // Supervisores
            new Empleado("7", "Miguel", "Cervantes", "Supervisor", 2, "Disponible"),
            new Empleado("8", "Johan", "Garcia", "Supervisor", 2, "Disponible"),
            new Empleado("9", "Ramiro", "Gonzalez", "Supervidor", 2, "Disponible"),

            // Director
            new Empleado("10", "Andres", "Vergel", "Director", 3, "Disponible")
        );

    }

}
