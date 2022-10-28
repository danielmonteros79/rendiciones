package com.sa.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class fileEnabler extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7962405968680174754L;

	private static final Log log = LogFactory.getLog(fileEnabler.class);

	private String documentRoot;

	public fileEnabler() {
		documentRoot = "/../html/";
	}

	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		File f;
		String arch = arg0.getRequestURI();
		log.info("Se pide:" + arch.toString());
		int idx = arch.indexOf("/", 2);
		String s = "%20";
		if (idx != 0)
			arch = arch.substring(idx + 1);
		String resultado = "F:\\Fpven1_e\\Usr\\Metodologia\\Intercambio FSW\\SSDD\\Para DyD\\Java\\Sia\\Project\\EsqueletoAppWeb\\sum_00001_3\\WebContent\\"+ arch.toString(); // String.valueOf(documentRoot)
		
		// +
		// "/"
		// +
		// arch.toString();
		log.info("Se resuelve:" + resultado);
		f = new File(resultado);
		if (!f.exists()) {
			arg1.sendError(404);
			return;
		}
		try {
			String contentType = getServletContext().getMimeType(f.getName());
			if (contentType == null)
				contentType = "application/octet-stream";
			arg1.reset();
			arg1.setHeader("Content-Type", contentType);
			arg1.setHeader("Content-Length", String.valueOf(f.length()));
			FileInputStream fis = new FileInputStream(f);
			byte b[] = new byte[1024];
			OutputStream os = arg1.getOutputStream();
			int cant;
			while ((cant = fis.read(b)) >= 0)
				os.write(b, 0, cant);
			os.flush();
		} catch (Exception e) {
			log.warn(e);
		}
		return;
	}

	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		doGet(arg0, arg1);
	}

	public void init() throws ServletException {
		super.init();
		String dr = getServletContext().getInitParameter(
				"extended-document-root");
		getServletContext().setAttribute("realpath",
				getServletContext().getRealPath("."));
		if (dr == null)
			dr = "${realpath}/../html/";
		documentRoot = replaceVariablesInString(dr, getServletContext());
	}

	public String replaceVariablesInString(String strToReplace,
			ServletContext sc) {
		if (strToReplace == null)
			return null;
		int idxEnd;
		for (int idxBegin = strToReplace.indexOf("${"); idxBegin >= 0; idxBegin = strToReplace
				.indexOf("${", idxEnd)) {
			idxEnd = strToReplace.indexOf("}", idxBegin);
			String var = strToReplace.substring(idxBegin + "${".length(),
					idxEnd);
			String value = sc.getAttribute(var).toString();

			// (new StringBuilder("${")).append(var).append("}").toString(),
			// value
			if (strToReplace.indexOf(var) == strToReplace.lastIndexOf(var)) {
				strToReplace = value + strToReplace.substring(var.length() + 3);
			} else {
				strToReplace = strToReplace.replaceAll("${" + var + "}", value);
			}
		}

		return strToReplace;
	}

}
