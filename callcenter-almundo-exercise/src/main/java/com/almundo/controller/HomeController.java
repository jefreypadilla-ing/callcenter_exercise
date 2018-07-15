package com.almundo.controller;

import com.almundo.config.EmpleadoSingleton;
import com.almundo.dao.DispatcherDao;
import com.almundo.dao.InitDao;
import com.almundo.job.QueueJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Jefrey Padilla
 */
@Controller
@Scope("request")
public class HomeController {

    private static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private InitDao initDao;

    @Autowired
    private DispatcherDao dispatcherDao;

    @Autowired
    private QueueJob queueJob;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String initHome(Model model){
        return init(model);
    }

    @RequestMapping(value="/home", method = RequestMethod.GET)
    public String initHome1(Model model){
        return init(model);
    }

    /**
     * Método para realizar un llamado via frontend
     * @param model
     * @return
     */
    @RequestMapping(value="/dispatcher", method = RequestMethod.POST)
    public String callDispatcher(Model model){
        try {

            // Cantidad de llamadas pendientes en la cola (Esto se da en el caso de no haber disponibilidad de empleados)
            int cantQueueCall = EmpleadoSingleton.INSTANCE.getCantQueueCall();
            if(cantQueueCall > 0){
                // Se le da prioridad a los que se encuentren en la cola.
                for(int i=0; i<cantQueueCall; i++){
                    queueJob.asignarEmpleado();
                }
            }

            model.addAttribute("listEmpleados", EmpleadoSingleton.INSTANCE.getListEmpleado());
            dispatcherDao.dispatchCall();
            model.addAttribute("mensaje", (cantQueueCall > 0?"Espere un momento, turno en la cola "
                    + (cantQueueCall + 1) : ""));

        } catch (Exception ex){
            logger.error("Error callDispatcher, ", ex);
        }

        return "home";

    }

    /**
     * Metodo para refrescar la lista de empleados en la vista
     * @param model
     * @return
     */
    @RequestMapping(value = "/getListEmpleados", method = RequestMethod.GET)
    public @ResponseBody String getListEmpleados(Model model){
        model.addAttribute("listEmpleados", EmpleadoSingleton.INSTANCE.getListEmpleadosView());
        model.addAttribute("mensaje", (EmpleadoSingleton.INSTANCE.getCantQueueCall() > 0?"Un momento por favor, turno(s) en espera "
                + (EmpleadoSingleton.INSTANCE.getCantQueueCall() + 1) : ""));
        return "home";
    }

    /**
     * Metodo para la carga inicial de la data de empleados
     * @param model
     * @return
     */
    public String init(Model model){

        try {
            initDao.loadData();
            model.addAttribute("listEmpleados", EmpleadoSingleton.INSTANCE.getListEmpleado());

        } catch (Exception ex){
            logger.error("Error init, ", ex);
        }

        return "home";
    }

}
