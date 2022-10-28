package com.sa.util;

public class FormatosCampos {
	public static String formatString(String strOrig, String strFormato) {

		String valor = "";
		if (strOrig.length() < strFormato.length()) {
			valor += strOrig
					+ strFormato.substring(strOrig.length(),
							strFormato.length());
		} else if (strOrig.length() > strFormato.length()) {
			valor += strOrig.substring(0, strFormato.length());
		} else {
			valor += strOrig;
		}
		return valor;
	}

	
}
