package com.almundo.job.impl;

import com.almundo.config.EmpleadoSingleton;
import com.almundo.dao.DispatcherDao;
import com.almundo.job.QueueJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jefrey Padilla
 */
@Component
public class QueueJobImpl implements QueueJob {

    private static Logger logger = LoggerFactory.getLogger(QueueJobImpl.class);

    @Autowired
    private DispatcherDao dispatcherDao;

    @Override
    public void asignarEmpleado() {

        logger.info("Ejecutando asignarEmpleado Job");
        logger.info("Cola de peticiones: " + EmpleadoSingleton.INSTANCE.getCantQueueCall());

        if(EmpleadoSingleton.INSTANCE.getCantQueueCall() > 0) {

            for (int i = 0; i < EmpleadoSingleton.INSTANCE.getCantQueueCall(); i++) {
                try {

                    dispatcherDao.dispatchCall();
                    EmpleadoSingleton.INSTANCE.callCantQueue();

                } catch (Exception ex) {
                    logger.error("Error asignarEmpleado job", ex);
                }
            }

        }

    }
}
