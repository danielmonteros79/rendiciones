package com.sa.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

//import servicios.LoggerSIA;
import com.sa.core.XMLConfigReader;

/**
 * Clase base para DAO's que fuerza a estos a cumplir con las responsabilidades
 * basicas de estos como ser la de logging de eventos/errores.
 * 
 */
public abstract class Dao {

	public static final Logger log = Logger.getLogger(Dao.class);
//	private LoggerSIA logger;
	private XMLConfigReader xml;

	/**
	 * 
	 * @param xml
	 *            archivo de configuracion.
	 */
	public Dao(XMLConfigReader xml) {
		this.xml = xml;
//		logger = new LoggerSIA(xml);
	}

	/**
	 * Cierra los recursos que esten activos. En caso de error se imprime el
	 * stack trace por consola.
	 * 
	 * @param stmt
	 *            Cualquier tipo de Statement.
	 * @param rs
	 *            ResultSet.
	 */
	public final void close(Statement stmt, ResultSet rs) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.error(e);
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				log.error(e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * Realiza la logica por default para manejar excepciones de este nivel
	 * (logging y tirar excepcion de alto nivel envolviendo a la q se pasa).
	 * 
	 * @param e
	 * @throws DAOException
	 */
	public final void doException(Exception e) throws DAOException {
		log.error(e);
//		logger.logExceptionStackTrace(e);
		throw new DAOException(e);
	}

//	protected LoggerSIA getLogger() {
//		return logger;
//	}

	protected XMLConfigReader getXml() {
		return xml;
	}

}
