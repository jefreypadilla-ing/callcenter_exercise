package com.almundo.job;

/**
 * @author Jefrey Padilla
 */

public interface QueueJob {
    /**
     * Metodo de asignación de empleados a las llamadas, esto se lleva a cabo
     * al tener llamadas en espera por no disponibilidad
     */
    void asignarEmpleado();
}
