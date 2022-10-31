package com.sa.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Esta clase encapsula todos los objetos globales necesarios para la aplicacion
 * que seran usados en los Action. Los clientes deben pedir los parametros
 * mediantes las constantes de clase que se proveen para obtener los objetos
 * deseados y castear a la clase correspondiente.
 */
public final class ParametrosSUM {

    private Map<String, Object> params;
    /**
     * Constante para solicitar un objeto Connection.
     */
    public static final String CONEXION_DB = "con";
    /**
     * Constante para solicitar un objeto XMLConfigReader.
     */
    public static final String XML_CONFIGURACION = "xml";
    /**
     * Constante para solicitar un objeto LoggerSIA.
     */
    public static final String LOGGER_SIA = "logger";

    /**
     * Constante para solicitar un objeto Usuario, el usuario actualmente
     * logueado en el sistema.
     */
    public static final String USUARIO = "usuario";

    /**
     * Constante para solicitar un objeto Transacion de Altamira.
     */
    public static final String TRANSACCION_MANAGER = "transaccionManager";
    /**
     * Constante para solicitar un objeto Transacion de Altamira.
     */
    public static final String GLOBAL_CONFIG = "globalConfig";

    public ParametrosSUM() {
        params = new HashMap<String, Object>();
    }

    /**
     * Agrega un objeto a la tabla de parametros.
     *
     * @param nombre String definir y usar una constante de clase.
     * @param parametro Object
     */
    public void agregarParametro(String nombre, Object parametro) {
        params.put(nombre, parametro);
    }

    /**
     * Devuelve un Object que debe ser casteado al tipo de dato especificado por
     * la constante.
     *
     * @param nombre utilizar alguna de las constantes de esta clase para
     * obtener el objeto deseado.
     * @return Object con el parametro solicitado, castear al tipo de dato que
     * indica la constante.
     */
    public Object getParametro(String nombre) {
        return params.get(nombre);
    }

}
