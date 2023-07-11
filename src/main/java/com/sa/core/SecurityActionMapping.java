package com.sa.core;

import org.apache.struts.action.ActionMapping;

public class SecurityActionMapping extends ActionMapping {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String zone = null;

	public String getApplicationZone() {
		return zone;
	}

	public void setApplicationZone(String newZone) {
		zone = newZone;
	}
}