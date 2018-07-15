package com.almundo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Clase para el tratamiento de properties asociados a la aplicación
 * @author Jefrey Padilla
 */
@Configuration(value = "applicationProperties")
@PropertySource("classpath:custom.properties")
public class ApplicationProperties {

    @Value("${empleado.tipo.operador}")
    private String operador;

    @Value("${empleado.tipo.supervisor}")
    private String supervisor;

    @Value("${empleado.tipo.director}")
    private String director;

    @Value("${empleado.prioridad.tipo.operador}")
    private String prioridad_operador;

    @Value("${empleado.prioridad.tipo.supervisor}")
    private String prioridad_supervisor;

    @Value("${empleado.prioridad.tipo.director}")
    private String prioridad_director;

    @Value("${duracion.llamada.min}")
    private String duracionLlamadaMin;

    @Value("${duracion.llamada.max")
    private String duracionLlamadaMax;

    @Value("${dispatcher.concurrencia.cantidad}")
    private String cantidadConcurrencia;

    @Value("${dispatcher.executorservice.time.max}")
    private String dispatcher_executorservice_time_max;

    @Value("${cron.queue.activate}")
    private String cron_queue_activate;

    @Value("${cron.queue.time}")
    private String cron_queue_time;

    @Bean
    public String cron_queue_time_bean(){
        return this.cron_queue_time;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPrioridad_operador() {
        return prioridad_operador;
    }

    public void setPrioridad_operador(String prioridad_operador) {
        this.prioridad_operador = prioridad_operador;
    }

    public String getPrioridad_supervisor() {
        return prioridad_supervisor;
    }

    public void setPrioridad_supervisor(String prioridad_supervisor) {
        this.prioridad_supervisor = prioridad_supervisor;
    }

    public String getPrioridad_director() {
        return prioridad_director;
    }

    public void setPrioridad_director(String prioridad_director) {
        this.prioridad_director = prioridad_director;
    }

    public String getDuracionLlamadaMin() {
        return duracionLlamadaMin;
    }

    public void setDuracionLlamadaMin(String duracionLlamadaMin) {
        this.duracionLlamadaMin = duracionLlamadaMin;
    }

    public String getDuracionLlamadaMax() {
        return duracionLlamadaMax;
    }

    public void setDuracionLlamadaMax(String duracionLlamadaMax) {
        this.duracionLlamadaMax = duracionLlamadaMax;
    }

    public String getCantidadConcurrencia() {
        return cantidadConcurrencia;
    }

    public void setCantidadConcurrencia(String cantidadConcurrencia) {
        this.cantidadConcurrencia = cantidadConcurrencia;
    }

    public String getDispatcher_executorservice_time_max() {
        return dispatcher_executorservice_time_max;
    }

    public void setDispatcher_executorservice_time_max(String dispatcher_executorservice_time_max) {
        this.dispatcher_executorservice_time_max = dispatcher_executorservice_time_max;
    }

    public String getCron_queue_activate() {
        return cron_queue_activate;
    }

    public void setCron_queue_activate(String cron_queue_activate) {
        this.cron_queue_activate = cron_queue_activate;
    }

    public String getCron_queue_time() {
        return cron_queue_time;
    }

    public void setCron_queue_time(String cron_queue_time) {
        this.cron_queue_time = cron_queue_time;
    }
}
