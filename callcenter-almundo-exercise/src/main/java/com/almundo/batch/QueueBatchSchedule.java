package com.almundo.batch;

import com.almundo.config.ApplicationProperties;
import com.almundo.job.QueueJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Jefrey Padilla
 */
@Component
public class QueueBatchSchedule {

    private Logger logger = LoggerFactory.getLogger(QueueBatchSchedule.class);

    @Autowired
    private QueueJob queueJob;

    @Autowired
    private ApplicationProperties prop;

    /**
     * Metodo para la ejecución de metodos asincronos mediante
     * la tecnologia schedule cron para atender las solicitudes pendientes
     */
    @Scheduled(cron = "#{@cron_queue_time_bean}")
    protected void cronJobQueue(){
        logger.info("Ejecutando cronJobQueue");
        try {

            if(Boolean.parseBoolean(prop.getCron_queue_activate())){
                queueJob.asignarEmpleado();
            }

        } catch (Exception ex){
            logger.error("Error cronJobQueue ", ex);
        }

    }

}
