package com.sa.services;

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
import com.bbva.sam.bbvaPaq.BbvaPaqConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public abstract class Transaction {

    protected static final Logger log = Logger.getLogger(Transaction.class);

    protected String PARAMETER_TRX;
    protected String CURRENT_TRX;
    protected boolean mustDestroyContext = false;
    protected IContext contexto;
    protected IWebClient client;
    protected final String CONECTOR_SOA = "TM_WSSOACON";
    protected Object dataReturn = new Object();
    protected List dataReturnList = new ArrayList<Object>();
    private int CANT_INTENTOS = 2;
    private String aviso;

    /**
     * Metodo abstracto para sobreescribirlo quin extienda de la clase
     * Transaction. Debe adapatarse a la transaccion configurada y llamar al
     * metodo execute() el cual este metodo es el que realiza la conexion y
     * ejecucion a Soaconectores.
     *
     * @param client IWebclient -> Instancia de Sam.
     *
     * @param parametersExecute Map -> hashMap con los parametros de entrada a
     * la transaccion.
     *
     * @throws TransactionException Lanzar esta excepcion que ser capturada por
     * la obtencion de Sam, o al chequear el status de lo que devolvio la
     * consulta a host. Se deber manejar a travez del getMessage().
     */
    public abstract void executeTrx(IWebClient client, Map parametersExecute)
            throws TransactionException;

    /**
     * Metodo abstracto para sobreescribirlo quin extienda de la clase
     * Transaction. Debe adapatarse a la transaccion configurada y llamar al
     * metodo execute() el cual ste metodo es el que realiza la conexion y
     * ejecucion a Soaconectores.
     *
     * @param client IWebclient -> Instancia de Sam.
     *
     * @param parameters String[] -> Vector de parametros de entrada.
     *
     * @throws TransactionException Lanzar esta excepcion que ser capturada por
     * la obtencion de Sam, o al chequear el status de lo que devolvio la
     * consulta a host. Se deber manejar a travez del getMessage().
     */
    public abstract void executeTrx(IWebClient client, String... parameters)
            throws TransactionException;

    /**
     * Metodo abstracto para sobreescribirlo de quin exienda de la clase
     * Transaction. Generar ciclo de trx definidas con sus params de entrada y
     * colocarle los valores corresopndientes para su consulta.
     *
     *
     * @param parameters String[] -> Vector de parametros de entrada para
     * ejecucion de trx.
     *
     * @return Map -> Devolver un hashMap.
     */
    protected abstract Map mapInputParams(String... parameters);

    /**
     * Metodo abstracto para sobreescribir, adaptar el mapeo de los datos que
     * retorna al ejecutar la trx.
     *
     * @param parametersExecute Map -> Se le coloca el hashMap que devolvio la
     * trx.
     *
     */
    protected abstract void mapData(Map parametersExecute) throws Exception;

    /**
     * Mtodo que realiza la conexion a SoaConectores y ejecucion de la trx que
     * se le ingresa como parametro de entrada.
     *
     * @param client IWebClient -> Instancia de Sam.
     *
     * @param trxExecute String -> Nombre con el que se llamara a la trx en
     * soaConectores.
     *
     * @param parametersExecute Map -> hashMap con los parametros de entrada a
     * la transaccion.
     *
     * @throws Exception Lanzar la exeption al chequear el status o si hay algun
     * problema con sam.
     */
    protected void execute(IWebClient client, String trxExecute,
            Map parametersExecute) throws Exception {
        int intentos = 1;
        this.client = client;

        log.info(CURRENT_TRX + " --> Se ejecuta trx. Map parameters in: "
                + parametersExecute.entrySet());
        if (trxExecute.contains("ABM")) {
            CANT_INTENTOS = 1;
        }
        log.info("Parametros:" + DumpUtils.dumpMap(parametersExecute));
        do {

            try {
                log.info(CURRENT_TRX + " --> Intento execute: " + intentos);
                getSAM().execute(CONECTOR_SOA + "." + trxExecute,
                        getSAMContext(), parametersExecute);
                log.info(CURRENT_TRX + " --> Se chequea status de ejecucion");
                checkSoaStatus(parametersExecute);
                log
                        .info(CURRENT_TRX
                                + " --> Se ejecuto correctamente trx. Map parameters out: "
                                + parametersExecute.entrySet());
                log.info(CURRENT_TRX + " --> Se chequea avisos de ejecucion");
                aviso = checkSoaAvisos(parametersExecute);

                intentos = CANT_INTENTOS + 1;
            } catch (Exception e) {
//				// TODO: handle exception
                if (intentos == CANT_INTENTOS) {
                    throw e;
                }
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
    private IContext getSAMContext() throws TransactionException,
            GeneralException {
        if (this.contexto == null) {
            if ((this.client != null) && (this.client instanceof SAMWebClient)) {
                this.mustDestroyContext = false;
                this.contexto = ((SAMWebClient) this.client).getSamContext();
            } else {
                this.mustDestroyContext = true;
                this.contexto = SAMReference.getSAM().getContextManager()
                        .createContext("", new HashMap());
            }
            log.info("Context obtenido: "
                    + (String) this.client.getAttribute("userLoggin"));
            this.contexto.setUserName((String) this.client
                    .getAttribute("userLoggin"));
        }
        return this.contexto;
    }

    private IServiceAccessManager getSAM() throws GeneralException {
        return SAMReference.getSAM();
    }

    /**
     * Mtodo que realiza el check del status al ejecutar la trx. Hace el mapeo
     * con el xml y devuelve si hay error interno con SAM o si ocurri algn error
     * con la Trx internamente.
     *
     * @param parameters
     *
     * @throws Exception Excepcion General. Se acceder atravz de getMessage()
     * del stactrace.
     */
    private void checkSoaStatus(Map parameters) throws Exception {
        BbvaSoaStatus status = (parameters == null) ? null
                : (BbvaSoaStatus) parameters
                        .get(BbvaPaqConstants.NOMBRE_PARAM_STATUS);
        BbvaSoaMensaje msj = null;
        String msjError = null, msjCode = null;

        if (status != null && !status.isOk()) {
            if (status.getListaErrores() != null
                    && status.getListaErrores().size() > 0) {
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

    private String checkSoaAvisos(Map parameters) throws Exception {
        BbvaSoaStatus status = (parameters == null) ? null
                : (BbvaSoaStatus) parameters
                        .get(BbvaPaqConstants.NOMBRE_PARAM_STATUS);
        BbvaSoaMensaje msj = null;
        String msjAviso = null, msjAvisoCode = null;

        if (status != null) {
            if (status.getListaAvisos() != null
                    && status.getListaAvisos().size() > 0) {
                msj = (BbvaSoaMensaje) status.getListaAvisos().get(0);
                msjAviso = msj.getDescripcion();
                msjAvisoCode = msj.getCodigo();
            }
        }
        return msjAviso;
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
}
