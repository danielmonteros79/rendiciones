package com.sa.form;

import org.apache.struts.action.ActionForm;
import org.apache.commons.text.StringEscapeUtils;

public class LoginForm extends ActionForm {
	String username;
	String password;
	
	public String getUsername() {
	    return StringEscapeUtils.escapeHtml4(username);
	}

	public void setUsername(String username) {
	    this.username = username != null ? StringEscapeUtils.escapeHtml4(username) : null;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}