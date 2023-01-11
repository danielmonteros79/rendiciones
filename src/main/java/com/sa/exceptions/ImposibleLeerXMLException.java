package com.sa.exceptions;

/**
 * Se lanza este tipo de excepcion en caso de ocurrir algun error de bajo nivel
 * al leer el archivo de configuracion. Esto puede deberse a falta de permisos,
 * que no exista el archivo o que este mal formateado.
 *
 */
public class ImposibleLeerXMLException extends Exception {

    private static final String MENSAJE_ERROR = "No se pudo leer el archivo de configuracion.";
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ImposibleLeerXMLException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImposibleLeerXMLException(Throwable cause) {
        super(MENSAJE_ERROR, cause);
    }

}
