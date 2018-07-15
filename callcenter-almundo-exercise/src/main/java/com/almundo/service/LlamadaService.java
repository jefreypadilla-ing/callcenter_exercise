package com.almundo.service;

import com.almundo.bean.Empleado;

/**
 * @author Jefrey Padilla
 */
public interface LlamadaService {

    /**
     * Metodo para el inicio de la llamada, proceso de ejecución concurrente
     * @param empleado
     * @param duracionLlamada
     * @return
     */
    Runnable iniciarLlamada(Empleado empleado, int duracionLlamada);

}
