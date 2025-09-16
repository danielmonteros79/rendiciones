package com.sa.core;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;

import com.sa.exceptions.ImposibleLeerXMLException;

//import core.altamira.AltamiraPool;

public class InitServlet extends HttpServlet {

	private static String fileSeparator = System.getProperty("file.separator");
	private static String pathConfig;

	private static final long serialVersionUID = 1L;
	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);

		System.out.println("SUM - Inicializando aplicacion.");

		List<Exception> excepciones = new ArrayList<Exception>();
		List<String> listadoEx = new ArrayList<String>();
		// Lee el directorio donde estan los archivos de configuracion
//		 String realpath = "/var/lib/tomcat6/webapps/sia";
		String realpath = getServletContext().getRealPath(
				"/applicationConfig.xml");
		System.out
				.println("SUM - Ruta realPath del /applicationConfig.xml del WAS -> "
						+ realpath);
//		String pathRaiz = "/var/lib/tomcat6/webapps/sia";
		String pathRaiz = new File(realpath).getParentFile().getParentFile()
				.getAbsolutePath();
		System.out.println("SUM - Ruta raiz del WAS -> " + pathRaiz);
		pathConfig = pathRaiz + fileSeparator + "syscfg";
		System.out.println("SUM - Ruta archivos de configuracion -> "
				+ pathConfig);

		// Establesco el directorio de configuracion como propiedad del sistema
		// para poder acceder desde cualquier para de la applicacion
		System.setProperty("dirConfig", pathConfig);
		//

		// Lee el directorio donde va a ser colocado el archivo de logs
		try {
			inicializoLog(config);
		} catch (Exception e) {
			System.out.println("SUM - " + e.getMessage());
			listadoEx.add(e.getMessage());
			excepciones.add(e);
		}
		//

		// Inicializa la configuracion del sistema
		try {
			System.out
					.println("SUM - Inicilizando configuracion de la aplicacion.");
			XMLConfigReader.getXml();
		} catch (ImposibleLeerXMLException e) {
			System.out
					.println("SUM - No se pudo inicializar la configuracion de la aplicacion");
		}
		//

		// altamira
//		AltamiraPool altamiraPool = new AltamiraPool();
//		Properties properties = new Properties();
//		String pathAltamira = pathConfig + fileSeparator
//				+ "altamira.properties";
//		try {
//			System.out
//					.println("SIA - Ruta archivo ALTAMIRA -> " + pathAltamira);
//			properties.load(new FileInputStream(pathAltamira));
//		} catch (Exception e) {
//			e.printStackTrace();
//			String msg = "Ocurrio un problema al cargar el archivo altamira.properties en la ruta: "
//					+ pathAltamira;
//			System.out.println("SIA - " + msg);
//			listadoEx.add(msg);
//			excepciones.add(e);
//		}
//		String pathTranfile = pathRaiz + fileSeparator + "tranfile";
//		try {
//			properties.setProperty("pathTranfiles", pathTranfile);
//			System.out.println("SIA - Ruta carpeta de tranfile -> "
//					+ pathTranfile);
//			altamiraPool.init(properties);
//		} catch (Exception e) {
//			e.printStackTrace();
//			String msg = "Ocurrio un problema al cargar los archivos TRANFILE en la ruta: "
//					+ pathTranfile;
//			System.out.println(msg);
//			listadoEx.add(msg);
//			excepciones.add(e);
//		}
//		//
//
//		getServletContext().setAttribute("altmPool", altamiraPool);
//		getServletContext().setAttribute("listaExcepciones", listadoEx);
//		getServletContext().setAttribute("excepciones", excepciones);
	}

	private void inicializoLog(ServletConfig config) throws ServletException {
		System.out.println("SUM - Inicializa el Log4j");
		// String directory = getInitParameter("log-directory");

		// Adiciona el parametro del directorio como un Property del sistema
		// para que pueda ser utilizado dentro del archivo de configuracion del
		// Log4J

		// System.setProperty("log.directory", directory);

		// Extrae el path donde seencuentra el contexto
		// Asume que el archivo de configuracion se encuentra en este
		// directorio

		// Lee el nombre del archivo de configuracion de Log4J
		String nombreArchivo = getInitParameter("log4j-init-file");
		String rutaArchivo = pathConfig + fileSeparator + nombreArchivo;
		System.out.println("SUM - Ruta archivo log4j -> " + rutaArchivo);
		if (nombreArchivo == null || nombreArchivo.length() == 0
				|| !(new File(rutaArchivo).isFile())) {
			System.err
					.println("SUM - ERROR: No se pudo leer el archivo de configuracion: "
							+ pathConfig + fileSeparator + nombreArchivo);
			System.out
					.println("SUM - ERROR: No se pudo leer el archivo de configuracion: "
							+ pathConfig + fileSeparator + nombreArchivo);
			throw new ServletException(
					"No se pudo leer el archivo de configuracion: "
							+ pathConfig + fileSeparator + nombreArchivo);
		}

		// Revisa otra parametro de configuracion que le
		// indica
		// si debe revisar elarchivo de log por cambios.
		String watch = config.getInitParameter("watch");

		// Extrae el parametro que le indica cada que tiempo debe
		// revisar
		// el
		// archivo de configuracion
		String timeWatch = config.getInitParameter("time-watch");
		// Revisa como debe realizar la configuracion de Log4J y llama al metodo
		// adecuado
		if (watch != null && watch.equalsIgnoreCase("true")) {
			if (timeWatch != null) {
				PropertyConfigurator.configureAndWatch(rutaArchivo,
						Long.parseLong(timeWatch));
			} else {
				PropertyConfigurator.configureAndWatch(rutaArchivo);
			}
		} else {
			PropertyConfigurator.configure(rutaArchivo);
		}

	}
	@Override
	public void destroy() {
		super.destroy();
	}

}