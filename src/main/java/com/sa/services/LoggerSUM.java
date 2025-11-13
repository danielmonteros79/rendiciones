package com.sa.services;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.sa.core.XMLConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Graba mensajes de log en archivos de texto. En desarrollo local los logs se
 * guardan en el raiz de la carpeta del proyecto.
 * 
 */
public class LoggerSUM {
	private static final Logger log = LogManager.getLogger(LoggerSUM.class);
	/**
	 * Archivo de eventos del sistema.
	 */
	public static final int APPLICATION_FILE = 0;
	/**
	 * Archivo de excepciones del sistema.
	 */
	public static final int EXCEPTIONS_FILE = 1;
	private static final String LOG_DATE_FORMAT = "yyyyMMdd";
	private static final String LOG_FILE_EXTENSION = ".txt";

	protected XMLConfigReader xml;

	public LoggerSUM(XMLConfigReader xml) {
		this.xml = xml;
	}

	/**
	 * Metodo auxiliar de createFileNameUsingKey(), arma el nombre de archivo
	 * segun las reglas del sistema.
	 * 
	 * @param fileName
	 *            el nombre de archivo que se desea.
	 * @return el nombre de archivo formateado de acuerdo a las reglas del
	 *         sistema.
	 * @throws Exception
	 */
	protected String createFileName(String fileName) throws Exception {
		String path = "";
		if (this.xml != null) {
			path = xml.getLogsPath()
					+ fileName
					+ new SimpleDateFormat(LOG_DATE_FORMAT).format(Calendar
							.getInstance().getTime()) + LOG_FILE_EXTENSION;
		} else {
			path = fileName
					+ new SimpleDateFormat(LOG_DATE_FORMAT).format(Calendar
							.getInstance().getTime()) + LOG_FILE_EXTENSION;
		}
		return path;
	}

	/**
	 * Arma el path completo al archivo donde se graba el mensaje de log.
	 * 
	 * @param key
	 *            el archivo donde grabar. Constantes provistas por esta clase.
	 * @return el path al archivo como va a ser usado por los metodos de log().
	 * @throws Exception
	 */
	protected String createPath(int key) throws Exception {
		String fileName = null;
		switch (key) {
		case APPLICATION_FILE:
			if (xml != null) {
				fileName = createFileName(xml.getApplicationLogFileName());
			} else {
				fileName = "adaApplicationLog";
			}
			break;
		case EXCEPTIONS_FILE:
			if (xml != null) {
				fileName = createFileName(xml.getExceptionsLogFileName());
			} else {
				fileName = "adaExceptionsLog";
			}
			break;
		default:
			break;
		}
		return fileName;
	}

	protected String getFormattedDate() {
		SimpleDateFormat df = new SimpleDateFormat();
		String formattedDate = df.format(Calendar.getInstance().getTime());
		return formattedDate;
	}

	/**
	 * Graba la linea de texto provista en el archivo indicado con un formato
	 * comun al sistema.
	 * 
	 * @param msg
	 *            el mensaje de log.
	 * @param file
	 *            el archivo deseado, segun el servicio al que corresponda. Se
	 *            provee una constante de esta clase.
	 */
	public void log(String msg, int file) {
		// String out = getFormattedDate() + " <SIA> " + msg;
		// PrintWriter pw = null;
		// try {
		// FileOutputStream fos = new FileOutputStream(
		// createFileNameUsingKey(file), true);
		// pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos,
		// "ISO-8859-1")), true);
		// pw.println(out);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		String out = getFormattedDate() + " <SIA> " + msg;
		//PrintWriter pw = null;
		try (
			    FileOutputStream fos = new FileOutputStream(createPath(EXCEPTIONS_FILE), true);
			    PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos, "ISO-8859-1")), true)
			) {
			    pw.println(out);
		} catch (Exception ex) {
			// Registrar error sin imprimir stack trace en stdout. Si está en debug, registrar la excepción completa.
			if (log.isDebugEnabled()) {
				log.debug("Error al escribir log: " + ex.getMessage(), ex);
			}
			log.error("Error al escribir log: " + ex.getMessage());
		}
	}

	/**
	 * Metodo ad-hoc para imprimir una linea de error en el log con un mensaje
	 * predeterminado.
	 * 
	 * @param e
	 */
	public void logException(Exception e) {
	    String out = getFormattedDate() + " <SIA> Excepcion: " + e.getClass()
	            + " - Causa: " + e.getCause() + " - Mensaje: " + e.getMessage();
	    
	    try (
	        FileOutputStream fos = new FileOutputStream(createPath(EXCEPTIONS_FILE), true);
	        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos, "ISO-8859-1")), true)
	    ) {
	        pw.println(out);
		} catch (Exception ex) {
			if (log.isDebugEnabled()) {
				log.debug("Error al escribir exception log: " + ex.getMessage(), ex);
			}
			log.error("Error al escribir exception log: " + ex.getMessage());
		}
	}

	/**
	 * Metodo ad-hoc para imprimir el stack trace completo de la excepcion.
	 * 
	 * @param e
	 */
	public void logExceptionStackTrace(Exception e) {
		try (
			FileOutputStream fos = new FileOutputStream(createPath(EXCEPTIONS_FILE), true);
			PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos, "ISO-8859-1")), true)
		) {
			if (log.isDebugEnabled()) {
				// Solo escribir la traza completa en archivo si el logger está en modo debug
				e.printStackTrace(pw);
				log.debug("Stack trace completo escrito en archivo para depuración.", e);
			} else {
				// En producción, evitar volcar la traza completa; dejar registro mínimo
				pw.println(getFormattedDate() + " <SIA> Stack trace omitido en entorno de producción. Excepción: " + e);
				log.error("Stack trace omitido en entorno de producción. Excepción: " + e.getClass() + " - " + e.getMessage());
			}
		} catch (Exception ex) {
			if (log.isDebugEnabled()) {
				log.debug("Error al escribir stack trace en archivo: " + ex.getMessage(), ex);
			}
			log.error("Error al escribir stack trace en archivo: " + ex.getMessage());
		}
	}
	

}