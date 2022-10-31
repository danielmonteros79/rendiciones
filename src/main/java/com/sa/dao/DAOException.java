package com.sa.dao;

/**
 * Se lanza desde cualquier DAO generalmente para notificar algun error de bajo
 * nivel en el acceso a datos.
 *
 */
public class DAOException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DAOException(Exception e) {
        super(e);
    }

    public DAOException(String msg) {
        super(msg);
    }

    public DAOException(String string, Exception e) {
        super(string, e);
    }

}
