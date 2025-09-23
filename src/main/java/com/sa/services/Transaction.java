package com.sa.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;

import com.bbva.sam.bbvaPaq.BbvaPaqConstants;

import ar.com.bbva.soa.conectores.BbvaSoaMensaje;
import ar.com.bbva.soa.conectores.BbvaSoaStatus;
import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.GeneralException;
import ar.com.itrsa.sam.IContext;
import ar.com.itrsa.sam.IServiceAccessManager;
import ar.com.itrsa.sam.TransactionException;
import ar.com.itrsa.sam.factory.SAMReference;
import ar.org.bbva.util.DumpUtils;
import org.apache.commons.text.StringEscapeUtils;

@SuppressWarnings("rawtypes")
public abstract class Transaction {
	protected static final Logger log = Logger.getLogger(Transaction.class);

	protected String PARAMETER_TRX;
	protected String CURRENT_TRX;
	protected boolean mustDestroyContext = false;
	protected IContext contexto;
	protected IWebClient client;
	protected final String CONECTOR_SOA = "TM_WSSOACON";
	protected final String CONECTOR_SOA_THUBAN = "TM_ESB";
	protected Object dataReturn = new Object();
	protected List dataReturnList = new ArrayList<Object>();
	private int CANT_INTENTOS = 2;
	private String aviso;

	/**
	 * Metodo abstracto para sobreescribirlo quin extienda de la clase Transaction.
	 * Debe adapatarse a la transaccion configurada y llamar al metodo execute() el
	 * cual este metodo es el que realiza la conexion y ejecucion a Soaconectores.
	 * 
	 * @param client
	 *            IWebclient -> Instancia de Sam.
	 * 
	 * @param parametersExecute
	 *            Map -> hashMap con los parametros de entrada a la transaccion.
	 * 
	 * @throws TransactionException
	 *             Lanzar esta excepcion que ser capturada por la obtencion de Sam,
	 *             o al chequear el status de lo que devolvio la consulta a host. Se
	 *             deber manejar a travez del getMessage().
	 */
	public abstract void executeTrx(IWebClient client, Map<String, Object> parametersExecute) throws TransactionException;

	/**
	 * Metodo abstracto para sobreescribir, adaptar el mapeo de los datos que
	 * retorna al ejecutar la trx.
	 * 
	 * @param parametersExecute
	 *            Map -> Se le coloca el hashMap que devolvio la trx.
	 * 
	 */
	protected abstract void mapData(Map<String, Object> parametersExecute) throws Exception;
	
	protected abstract void hardcodear(Map<String, Object> parametersExecute) throws Exception;

	protected void execute(IWebClient client, String trxExecute, Map<String, Object> parametersExecute) throws Exception {
		execute(client, trxExecute, parametersExecute, CONECTOR_SOA);
	}

	/**
	 * Metodo que realiza la conexion a SoaConectores y ejecucion de la trx que se
	 * le ingresa como parametro de entrada.
	 * 
	 * @param client
	 *            IWebClient -> Instancia de Sam.
	 * 
	 * @param trxExecute
	 *            String -> Nombre con el que se llamara a la trx en soaConectores.
	 * 
	 * @param parametersExecute
	 *            Map -> hashMap con los parametros de entrada a la transaccion.
	 * 
	 * @throws Exception
	 *            Lanzar la exeption al chequear el status o si hay algun problema
	 *            con sam.
	 */
	protected void execute(IWebClient client, String trxExecute, Map<String, Object> parametersExecute, String conectorSoa) throws Exception {
		int intentos = 1;
		this.client = client;

		log.info(CURRENT_TRX + " --> Se ejecuta trx. Map parameters in: " + parametersExecute.entrySet());
		if (trxExecute.contains("ABM"))
			CANT_INTENTOS = 1;

		log.info("Parametros:" + DumpUtils.dumpMap(parametersExecute));
		do {
			try {
				log.info(CURRENT_TRX + " --> Intento execute: " + intentos);
				
				//Comentar aca y descomentar la linea de abajo para usar hardcodeo
				getSAM().execute(conectorSoa + "." + trxExecute, getSAMContext(), parametersExecute);
//				hardcodear(parametersExecute);
				
				log.info(CURRENT_TRX + " --> Se chequea status de ejecucion");
				checkSoaStatus(parametersExecute);
				log.info(CURRENT_TRX + " --> Se ejecuto correctamente trx. Map parameters out: " + parametersExecute.entrySet());
				log.info(CURRENT_TRX + " --> Se chequea avisos de ejecucion");
				aviso = checkSoaAvisos(parametersExecute);

				intentos = CANT_INTENTOS + 1;
			} catch (Exception e) {
				if (intentos == CANT_INTENTOS)
					throw e;

				intentos++;
			}
		} while (intentos <= CANT_INTENTOS);
	}

	/**
	 * Mtodo que obtiene el contexto con Sam.
	 * 
	 * @return IContext ->
	 * 
	 * @throws TransactionException
	 * @throws GeneralException
	 */
	private IContext getSAMContext() throws TransactionException, GeneralException {
	    if (this.contexto == null) {
	        if (this.client != null) {
	            if (this.client instanceof SAMWebClient) {
	                this.mustDestroyContext = false;
	                this.contexto = ((SAMWebClient) this.client).getSamContext();
	            } else {
	                this.mustDestroyContext = true;
	                this.contexto = SAMReference.getSAM().getContextManager().createContext("", new HashMap());
	            }
	            log.info("Context obtenido: " + (String) this.client.getAttribute("userLoggin"));
	            String userLoggin = (String) this.client.getAttribute("userLoggin");
	            if (userLoggin != null) {
	                this.contexto.setUserName(userLoggin);
	            } else {
	                // Manejar el caso en que userLoggin es null
	            }
	        } else {
	            // Manejar el caso en que this.client es null
	        }
	    }
	    return this.contexto;
	}




	private IServiceAccessManager getSAM() throws GeneralException {
		return SAMReference.getSAM();
	}

	/**
	 * Mtodo que realiza el check del status al ejecutar la trx. Hace el mapeo con
	 * el xml y devuelve si hay error interno con SAM o si ocurri algn error con la
	 * Trx internamente.
	 * 
	 * @param parameters
	 * 
	 * @throws Exception
	 *             Excepcion General. Se acceder atravz de getMessage() del
	 *             stactrace.
	 */
	private void checkSoaStatus(Map<String, Object> parameters) throws Exception {
		BbvaSoaStatus status = (parameters == null) ? null : (BbvaSoaStatus) parameters.get(BbvaPaqConstants.NOMBRE_PARAM_STATUS);
		BbvaSoaMensaje msj = null;
		String msjError = null, msjCode = null;

		if (status != null && !status.isOk()) {
			if (status.getListaErrores() != null && status.getListaErrores().size() > 0) {
				msj = (BbvaSoaMensaje) status.getListaErrores().get(0);
				msjError = msj.getDescripcion();
				msjCode = msj.getCodigo();
			} else {
				msjError = "Error generico";
				msjCode = "BBVA999";
			}
			throw new Exception(msjError + ":" + msjCode);
		} 
	}

	private String checkSoaAvisos(Map<String, Object> parameters) throws Exception {
		BbvaSoaStatus status = (parameters == null) ? null : (BbvaSoaStatus) parameters.get(BbvaPaqConstants.NOMBRE_PARAM_STATUS);
		BbvaSoaMensaje msj = null;
		String msjAviso = null;

		if (status != null) {
			if (status.getListaAvisos() != null && status.getListaAvisos().size() > 0) {
				msj = (BbvaSoaMensaje) status.getListaAvisos().get(0);
				msjAviso = msj.getDescripcion();
			}
		}
		return msjAviso;
	}
	
	public String getStrLista(Object obj) {
		return getStrLista(obj, "lista");
	}
	
	public String getStrLista(Object obj, String campo) {
		try {
			BasicDynaBean bean = (BasicDynaBean) obj;
			return (String) bean.get(campo);
		} catch (Exception e) {
			return (String) obj;
		}
	}

	public IContext getContexto() {
		return contexto;
	}

	public void setContexto(IContext contexto) {
		this.contexto = contexto;
	}

	public IWebClient getClient() {
		return client;
	}

	public void setClient(IWebClient client) {
		this.client = client;
	}

	/**
	 * Obtiene un objeto mapeado de la consulta en la transaccion
	 * 
	 * @return
	 */
	public Object getDataReturn() {
	    if (dataReturn instanceof String) {
	        return StringEscapeUtils.escapeHtml4((String) dataReturn);
	    }
	    return dataReturn;
	}

	/**
	 * Obtiene la lista de objetos mapeados obtenidos en la consulta de la
	 * transaccion.
	 * 
	 * @return
	 */
	public List getDataReturnList() {
		return dataReturnList;
	}

	public void setAviso(String aviso) {
		this.aviso = aviso;
	}

	public String getAviso() {
		return aviso;
	}

	protected void ejecutarTransaccion(IWebClient client, String parameterTrx, Map<String, Object> parametersExecute) throws TransactionException {
	    try {
	        execute(client, parameterTrx, parametersExecute);
	        mapData(parametersExecute);
	    } catch (Exception e) {
	        log.error(e);
	        throw new TransactionException(e);
	    }
	}
}