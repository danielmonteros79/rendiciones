package com.sa.core;

import java.io.File;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import com.sa.exceptions.ImposibleLeerXMLException;

//import servicios.dao.AlertaDAO;

/**
 * Esta clase representa el archivo de configuracion. Posee getters con el
 * nombre del parametro del que se requiere el valor. El archivo se busca en el
 * servlet context para produccion o en el paquete core para desarrollo local;
 * en este ultimo este archivo debe ser movido a la carpeta syscfg al pasarse a
 * produccion.
 * 
 */

public class XMLConfigReader {

	public static final Logger log = Logger.getLogger(XMLConfigReader.class);
	private static final String ARCHIVO_CONF_NOMBRE = "applicationConfig.xml";

	public static String getArchivoConfNombre() {
		return ARCHIVO_CONF_NOMBRE;
	}

	private String applicationLogFileName;
	private String cantidadLineas;
	private String dbDriver;
	private String dbPassword;

	private String dbURL;

	private String dbUser;
	private String dbDriverOracle;
	private String dbPasswordOracle;
	private String dbURLOracle;

	private String dbUserOracle;
	private String dnsIp;
	private Document doc;

	private String exceptionsLogFileName;
	private String logsPath;

	private String pool;

	private String puertodns;

	/**
	 * Crea el archivo de configuracion con los atributos orientados a
	 * desarrollo local.
	 * 
	 * @throws ImposibleLeerXMLException
	 *             si ocurre algun error al acceder al sistema de archivos o
	 *             cuando el archivo de configuracion tiene un formato
	 *             incorrecto.
	 */
	public XMLConfigReader() throws ImposibleLeerXMLException {
		inicilizarXMLDesarrollo();
	}

	static private XMLConfigReader xml = null;

	/**
	 * Lanza excepcion en caso de ser desarrollo local, en tal caso usar el
	 * constructor sin parametros.
	 * 
	 * @param request
	 *            HttpServletRequest para poder leer desde el contexto de la
	 *            aplicacion
	 * @throws ImposibleLeerXMLException
	 *             si ocurre algun error al acceder al sistema de archivos o
	 *             cuando el archivo de configuracion tiene un formato
	 *             incorrecto. Tambien se tira si se esta en ambiente de
	 *             desarrollo.
	 */
	public XMLConfigReader(HttpServletRequest request)
			throws ImposibleLeerXMLException {
		inicilizarXMLProduccion(request);
	}

	public String getApplicationLogFileName() {
		return applicationLogFileName;
	}

	public static XMLConfigReader getXml() throws ImposibleLeerXMLException {

		// *Leeo el archivo de configuracion
		if (xml == null) {
			xml = new XMLConfigReader();

		}

		return xml;

	}

	/**
	 * Lee del ServletContext y devuelve el archivo de configuracion en el
	 * entorno del WebSphere del BBVA.
	 * 
	 * @param request
	 * @return File.
	 */
	protected File getArchivoConfigWAS(HttpServletRequest request) {
		String realpath = request.getSession().getServletContext()
				.getRealPath(ARCHIVO_CONF_NOMBRE);
		File f = new File(realpath);
		File f2 = f.getParentFile().getParentFile();
		String pathfinal = f2.getAbsolutePath() + "/syscfg/"
				+ ARCHIVO_CONF_NOMBRE;
		File f3 = new File(pathfinal);
		return f3;
	}

	public String getCantidadLineas() {
		return cantidadLineas;
	}

	public String getDbDriver() {
		return dbDriver;
	}

	public String getDbDriverOracle() {
		return dbDriverOracle;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public String getDbPasswordOracle() {
		return dbPasswordOracle;
	}

	public String getDbURL() {
		return dbURL;
	}

	public String getDbURLOracle() {
		return dbURLOracle;
	}

	public String getDbUser() {
		return dbUser;
	}

	public String getDbUserOracle() {
		return dbUserOracle;
	}

	public String getDnsIp() {
		return dnsIp;
	}

	public Document getDoc() {
		return doc;
	}

	public String getExceptionsLogFileName() {
		return exceptionsLogFileName;
	}

	public String getLogsPath() {
		return logsPath;
	}

	public String getPool() {
		return pool;
	}

	public String getPuertodns() {
		return puertodns;
	}

	private void inicilizarXMLDesarrollo() throws ImposibleLeerXMLException {
		try {
			InputStream is = getClass().getResourceAsStream(
					"applicationConfig.xml");

			DocumentBuilderFactory domFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
			Document confFile = domBuilder.parse(is);

			System.out.println("Antes de inicializar XML");
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();

			// parametros aplicacion

			// logs
			logsPath = xpath.evaluate("Config/Archivos/SA/LogsPath", confFile);
			applicationLogFileName = xpath.evaluate(
					"Config/Archivos/SA/ApplicationFileName", confFile);
			exceptionsLogFileName = xpath.evaluate(
					"Config/Archivos/SA/ExceptionsFileName", confFile);

			// sybase
			dbDriver = xpath.evaluate("Config/Database/SA/Driver", confFile);
			dbURL = xpath.evaluate("Config/Database/SA/URL", confFile);
			dbUser = xpath.evaluate("Config/Database/SA/User", confFile);
			dbPassword = xpath
					.evaluate("Config/Database/SA/Password", confFile);
			// Oracle
			dbDriverOracle = xpath.evaluate("Config/Database/SA/DriverOracle",
					confFile);
			dbURLOracle = xpath.evaluate("Config/Database/SA/URLOracle",
					confFile);
			dbUserOracle = xpath.evaluate("Config/Database/SA/UserOracle",
					confFile);
			dbPasswordOracle = xpath.evaluate(
					"Config/Database/SA/PasswordOracle", confFile);
			// ALTAMIRA
			dnsIp = xpath.evaluate("Config/Altamira/dnsIp", confFile);
			pool = xpath.evaluate("Config/Altamira/pool", confFile);
			puertodns = xpath.evaluate("Config/Altamira/puertodns", confFile);
			cantidadLineas = xpath.evaluate("Config/Altamira/cantidadLineas",
					confFile);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug("Error al inicializar XML en desarrollo", e);
			}
			log.error("No se pudo leer el archivo de configuracion en entorno de desarrollo", e);
			throw new ImposibleLeerXMLException(
					"No se pudo leer el archivo de configuracion", e);
		}

	}

	private void inicilizarXMLProduccion(HttpServletRequest request)
			throws ImposibleLeerXMLException {
		try {
			String realpath = request.getSession().getServletContext()
					.getRealPath("/applicationConfig.xml");
			File f = new File(realpath);
			File f2 = f.getParentFile().getParentFile();
			String pathfinal = f2.getAbsolutePath()
					+ "/syscfg/applicationConfig.xml";
//			String pathfinal = 
//			"/var/lib/tomcat6/webapps/sia" //f2.getAbsolutePath()
//			+ "/syscfg/applicationConfig.xml";
			File f3 = new File(pathfinal);

			DocumentBuilderFactory domFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
			// Document confFile = domBuilder
			// .parse("/src/main/resources/applicationConfig.xml");
			Document confFile = domBuilder.parse(f3);

			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();

			// parametros aplicacion

			// logs
			logsPath = xpath.evaluate("Config/Archivos/WAS/LogsPath", confFile);
			applicationLogFileName = xpath.evaluate(
					"Config/Archivos/WAS/ApplicationFileName", confFile);
			exceptionsLogFileName = xpath.evaluate(
					"Config/Archivos/WAS/ExceptionsFileName", confFile);

			// sybase
			dbDriver = xpath.evaluate("Config/Database/WAS/Driver", confFile);
			dbURL = xpath.evaluate("Config/Database/WAS/URL", confFile);
			dbUser = xpath.evaluate("Config/Database/WAS/User", confFile);
			dbPassword = xpath.evaluate("Config/Database/WAS/Password",
					confFile);
			// Oracle
			dbDriverOracle = xpath.evaluate("Config/Database/WAS/DriverOracle",
					confFile);
			dbURLOracle = xpath.evaluate("Config/Database/WAS/URLOracle",
					confFile);
			dbUserOracle = xpath.evaluate("Config/Database/WAS/UserOracle",
					confFile);
			dbPasswordOracle = xpath.evaluate(
					"Config/Database/WAS/PasswordOracle", confFile);
			// ALTAMIRA
			dnsIp = xpath.evaluate("Config/Altamira/dnsIp", confFile);
			pool = xpath.evaluate("Config/Altamira/pool", confFile);
			puertodns = xpath.evaluate("Config/Altamira/puertodns", confFile);
			cantidadLineas = xpath.evaluate("Config/Altamira/cantidadLineas",
					confFile);
		}  catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug("Error al inicializar XML en produccion", e);
			}
			log.error("No se pudo leer el archivo de configuracion en entorno de produccion", e);
			throw new ImposibleLeerXMLException("No se pudo leer el archivo de configuracion", e);
		}
	}

}