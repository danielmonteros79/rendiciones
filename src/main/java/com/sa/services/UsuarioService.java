package com.sa.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.Usuario;
import com.sa.manager.ManagerTransaction;
import com.sa.services.trxs.SU52;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

public class UsuarioService {
	protected static final Log log = LogFactory.getLog(UsuarioService.class);

	List<Usuario> usuarios = new ArrayList<>();
	private SAMWebClient client;
	private String msg;

	public UsuarioService() {
	}

	public UsuarioService(SAMWebClient samClient) {
		this.client = samClient;
	}

	public Usuario obtenerDelegadosUsuario(String usuario) throws TransactionException {
		Usuario user = null;
		ManagerTransaction manager = new ManagerTransaction(new SU52());
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("cod_user", usuario);

		manager.executeTrx(this.client, parameters);

		user = (Usuario) manager.getDataReturn();
		msg = (String) manager.getMensajeAviso();

		return user;
	}

	public String getMsg() {
		return msg;
	}
}