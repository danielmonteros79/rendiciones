package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Rendicion;
import com.sa.services.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("rawtypes")
public class SU63 extends Transaction {

    private static final Log log = LogFactory.getLog(SU63.class);

    public List<Rendicion> listaRendiciones = new ArrayList<Rendicion>();

    public SU63() {
        this.PARAMETER_TRX = "SUM_CONS_RND_APROB";
        this.CURRENT_TRX = "SU63";
    }

    @Override
    public void executeTrx(IWebClient client, Map parametersExecute) throws TransactionException {
        try {
            execute(client, this.PARAMETER_TRX, parametersExecute);
            try {
                mapData(parametersExecute);
            } catch (Exception e) {
                log.error(e);
                throw new TransactionException("Error de mapeo " + this.CURRENT_TRX);
            }
        } catch (Exception e) {
            log.error(e);
            if (!e.getMessage().contains("SUE0310")) {
                throw new TransactionException(e);
            }
        }
    }

    @Override
    public void executeTrx(IWebClient client, String... parameters) throws TransactionException {
    }

    @Override
    protected Map mapInputParams(String... parameters) {
        return null;
    }

    @Override
    protected void mapData(Map parametersExecute) {
        if (parametersExecute.get("lista") != null) {
            for (Object obj : (List) parametersExecute.get("lista")) {
                String str = (String) ((BasicDynaBean) obj).get("lista");
                Rendicion rendicion = new Rendicion();

                rendicion.setId(Integer.parseInt(str.substring(0, 16)));
                rendicion.setUsuarioRendicion(str.substring(16, 24));
                rendicion.setNombreUsuarioRendicion(str.substring(84, 144).trim());
                rendicion.setMotivo(str.substring(24, 84).trim());
                rendicion.setImporte(str.substring(161, 178));
                rendicion.setImporteTarjeta(str.substring(145, 161).trim());

                listaRendiciones.add(rendicion);
            }
        }
    }

    @Override
    public List getDataReturnList() {
        return listaRendiciones;
    }
}
