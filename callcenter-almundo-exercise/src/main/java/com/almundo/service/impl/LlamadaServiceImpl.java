package com.almundo.service.impl;

import com.almundo.bean.Empleado;
import com.almundo.config.EmpleadoSingleton;
import com.almundo.service.LlamadaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Jefrey Padilla
 */

@Component
public class LlamadaServiceImpl implements LlamadaService {

    private static Logger logger = LoggerFactory.getLogger(LlamadaServiceImpl.class);

    @Override
    public Runnable iniciarLlamada(Empleado empleado, int duracionLlamada) {

        logger.info("Iniciando metodo iniciarLlamada, Params: Empleado: " + empleado + " Duración llamada: " + duracionLlamada);

        Runnable runnable = () -> {
            try {
                logger.info("Iniciando hilo : " + Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(duracionLlamada); // Variable de control del tiempo de la llamada
                EmpleadoSingleton.INSTANCE.adicionarEmpleadoQueue(empleado);
                logger.info("Finalizando hilo : " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                logger.error("Ocurrio un error en la ejecución de hilo: " + Thread.currentThread().getName(), e);
            }
        };

        return runnable;

    }

}
