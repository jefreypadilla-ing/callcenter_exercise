package com.almundo.dao.impl;

import com.almundo.bean.Empleado;
import com.almundo.config.EmpleadoSingleton;
import com.almundo.dao.EmpleadoDao;
import com.almundo.exception.EmpleadoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Jefrey Padilla
 */

@Component
public class EmpleadoDaoImpl implements EmpleadoDao {

    private static Logger logger = LoggerFactory.getLogger(EmpleadoDaoImpl.class);

    @Override
    public void adicionarEmpleado(Empleado emp) throws Exception {

        try {

            logger.info("Adicionar Empleado init");


            EmpleadoSingleton.INSTANCE.setEmpleado(emp);

            logger.info("Se adicionó empleado, " + emp);

        } catch (EmpleadoException ex){
            logger.info("Error adicionarEmpleado: " + ex.getMessage());
        } catch (Exception ex){
            logger.error("Error adicionarEmpleado: ", ex);
            throw new Exception(ex.getMessage());
        }

    }
}
