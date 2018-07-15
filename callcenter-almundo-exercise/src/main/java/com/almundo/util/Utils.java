package com.almundo.util;

import java.util.Random;

/**
 * @author Jefrey Padilla
 */

public class Utils {

    private static final Integer DURACION_MIN = 5;
    private static final Integer DURACION_MAX = 10;

    /**
     * Metodo para el calculo de la duración de la llamada de manera aleatoria
     * @return
     */
    public static int getDuracionLlamada(){
        return new Random().nextInt(DURACION_MAX - DURACION_MIN + 1) + DURACION_MIN;
    }

}
