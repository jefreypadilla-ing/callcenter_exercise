package com.almundo.dao.impl;

import com.almundo.bean.Empleado;
import com.almundo.config.ApplicationProperties;
import com.almundo.dao.EmpleadoDao;
import com.almundo.dao.InitDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitDaoImpl implements InitDao {

    private static Logger logger = LoggerFactory.getLogger(InitDaoImpl.class);

    @Autowired
    private ApplicationProperties prop;

    @Autowired
    private EmpleadoDao empleadoDao;

    @Override
    public void loadData() throws Exception {

        logger.info("Cargando listao de empleados :: loadData");

        try {

            // Operadores
            empleadoDao.adicionarEmpleado(new Empleado("1", "Jefrey", "Padilla",prop.getOperador(), Integer.parseInt(prop.getPrioridad_operador()), "Disponible"));
            empleadoDao.adicionarEmpleado(new Empleado("2", "Juan", "Gonzalez", prop.getOperador(), Integer.parseInt(prop.getPrioridad_operador()), "Disponible"));
            empleadoDao.adicionarEmpleado(new Empleado("3", "Pedro", "Torres", prop.getOperador(), Integer.parseInt(prop.getPrioridad_operador()), "Disponible"));
            empleadoDao.adicionarEmpleado(new Empleado("4", "Alberto", "Ramirez", prop.getOperador(), Integer.parseInt(prop.getPrioridad_operador()), "Disponible"));
            empleadoDao.adicionarEmpleado(new Empleado("5", "Jaime", "Perez", prop.getOperador(), Integer.parseInt(prop.getPrioridad_operador()), "Disponible"));
            empleadoDao.adicionarEmpleado(new Empleado("6", "Alonso", "Mendoza", prop.getOperador(), Integer.parseInt(prop.getPrioridad_operador()), "Disponible"));

            // Supervisores
            empleadoDao.adicionarEmpleado(new Empleado("7", "Miguel", "Cervantes", prop.getSupervisor(), Integer.parseInt(prop.getPrioridad_supervisor()), "Disponible"));
            empleadoDao.adicionarEmpleado(new Empleado("8", "Johan", "Garcia", prop.getSupervisor(), Integer.parseInt(prop.getPrioridad_supervisor()), "Disponible"));
            empleadoDao.adicionarEmpleado(new Empleado("9", "Ramiro", "Gonzalez", prop.getSupervisor(), Integer.parseInt(prop.getPrioridad_supervisor()), "Disponible"));

            // Director
            empleadoDao.adicionarEmpleado(new Empleado("10", "Andres", "Vergel", prop.getDirector(), Integer.parseInt(prop.getPrioridad_director()), "Disponible"));

        } catch (Exception ex){
            logger.error("Error loadData, ", ex);
            throw new Exception(ex.getMessage());
        }



    }

}
