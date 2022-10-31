package com.sa.services;

import com.sa.core.XMLConfigReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Graba mensajes de log en archivos de texto. En desarrollo local los logs se
 * guardan en el raiz de la carpeta del proyecto.
 *
 */
public class LoggerSUM {

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

    private XMLConfigReader xml;

    public LoggerSUM(XMLConfigReader xml) {
        this.xml = xml;
    }

    /**
     * Metodo auxiliar de createFileNameUsingKey(), arma el nombre de archivo
     * segun las reglas del sistema.
     *
     * @param fileName el nombre de archivo que se desea.
     * @return el nombre de archivo formateado de acuerdo a las reglas del
     * sistema.
     * @throws Exception
     */
    private String createFileName(String fileName) throws Exception {
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
     * @param key el archivo donde grabar. Constantes provistas por esta clase.
     * @return el path al archivo como va a ser usado por los metodos de log().
     * @throws Exception
     */
    private String createPath(int key) throws Exception {
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

    private String getFormattedDate() {
        SimpleDateFormat df = new SimpleDateFormat();
        String formattedDate = df.format(Calendar.getInstance().getTime());
        return formattedDate;
    }

    /**
     * Graba la linea de texto provista en el archivo indicado con un formato
     * comun al sistema.
     *
     * @param msg el mensaje de log.
     * @param file el archivo deseado, segun el servicio al que corresponda. Se
     * provee una constante de esta clase.
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
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(createPath(file),
                    true));
            pw.println(out);
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
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
        PrintWriter pw = null;
        try {
            FileOutputStream fos = new FileOutputStream(
                    createPath(EXCEPTIONS_FILE), true);
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos,
                    "ISO-8859-1")), true);
            pw.println(out);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Metodo ad-hoc para imprimir el stack trace completo de la excepcion.
     *
     * @param e
     */
    public void logExceptionStackTrace(Exception e) {
        PrintWriter pw = null;
        try {
            FileOutputStream fos = new FileOutputStream(
                    createPath(EXCEPTIONS_FILE), true);
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos,
                    "ISO-8859-1")), true);
            e.printStackTrace(pw);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
