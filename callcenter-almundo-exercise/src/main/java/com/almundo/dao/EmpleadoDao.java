package com.almundo.dao;

import com.almundo.bean.Empleado;

/**
 * @author Jefrey Padilla
 */
public interface EmpleadoDao {
    /**
     * Metodo para la adición de empleados a la lista global
     * @param emp
     * @throws Exception
     */
    void adicionarEmpleado(Empleado emp) throws Exception;
}
