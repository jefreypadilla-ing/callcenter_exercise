package com.almundo.dao.impl;

import com.almundo.bean.Empleado;
import com.almundo.config.ApplicationProperties;
import com.almundo.config.EmpleadoSingleton;
import com.almundo.dao.DispatcherDao;
import com.almundo.service.LlamadaService;
import com.almundo.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Jefrey Padilla
 */
@Component
public class DispatcherDaoImpl implements DispatcherDao {

    private static Logger logger = LoggerFactory.getLogger(DispatcherDaoImpl.class);

    @Autowired
    private ApplicationProperties prop;

    @Autowired
    private LlamadaService llamadaService;

    private ExecutorService executorService;

    private int cantAtendidos;

    private int cantCola;

    /**
     * Metodo para la instancia y limitación de cantidad de concurrencias que debe ejecutar el proceso
     */
    @PostConstruct
    protected void limiteConcurrencias(){
        try{
            logger.info("Inicializando limite concurrencias: " + Integer.parseInt(prop.getCantidadConcurrencia()));
            this.executorService = Executors.newFixedThreadPool(Integer.parseInt(prop.getCantidadConcurrencia()));
            cantAtendidos = 0;
        } catch (Exception ex){
            logger.error("Error limiteConcurrencias, ", ex);
        }
    }

    /**
     * Metodo dispatchCall, para la ejecución del core principal del proceso
     */
    @Override
    public void dispatchCall() {
        logger.info("Llamando metodo dispatchCall");

        try {
            // Se obtiene el empleado de la lista de prioritarios
            Empleado empleado = EmpleadoSingleton.INSTANCE.getEmpleadoQueue();
            if(empleado != null) {
                // Se implementa ExecutorService para el control de las concurrencias y ejecución interna de los hilos
                // Con la implementación del ExecutorService se controlan los llamados si estos superan la cantidad de concurrencias, manejando colas internas.
                this.executorService.submit(llamadaService.iniciarLlamada(empleado, Utils.getDuracionLlamada()));
                cantAtendidos++;
            } else {
                // En caso de no haber disponibilidad se envia un mensaje a la vista, informando la cantidad de turnos que se deben esperar
                logger.info("No se encontró empleado disponible, se envia a la cola de espera");
                cantCola++;
            }
        } catch (Exception ex){
            logger.error("Error dispatchCall: ", ex);
        }

    }

    /**
     * Metodo para parametrizar la configuración de cierre de los hilos, administrador por ExecutorService
     */
    @Override
    public int[] cierreExecutorService(){

        int[] v = new int[2];
        logger.info("Iniciando configuración de cierre :: cierreExecutorService, tiempo configurado: " + prop.getDispatcher_executorservice_time_max() + " Minutos");
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(Integer.parseInt(prop.getDispatcher_executorservice_time_max()), TimeUnit.MINUTES)) {
                logger.info("Cerrando executor service");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            logger.error("Error cierreExecutorService");
        }

        logger.info("Se realizó cierre del proceso, total de llamadas atendidas: " + cantAtendidos);

        v[0] = cantAtendidos;
        v[1] = cantCola;

        return v;

    }

}
