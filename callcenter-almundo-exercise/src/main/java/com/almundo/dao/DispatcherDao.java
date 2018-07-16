package com.almundo.dao;

/**
 * @author Jefrey Padilla
 */
public interface DispatcherDao {
    /**
     * Metodo dispatchCall, contiene toda la logica del llamado a los servicios
     * @throws Exception
     */
    void dispatchCall() throws Exception;

    /**
     * Metodo para indicar la hora de cierre de los hilos de ejecución
     * @throws Exception
     */
    int[] cierreExecutorService() throws Exception;
}
