package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Rendicion;
import com.sa.services.Transaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SU67 extends Transaction {

    private static final Log log = LogFactory.getLog(SU64.class);

    private final String[] FIELDS_INPUT = new String[]{};
    public List<Rendicion> listaRendiciones = new ArrayList<Rendicion>();

    public SU67() {
        // TODO Auto-generated constructor stub
        this.PARAMETER_TRX = "SUM_REDISTRIBUCION_GASTO";
        this.CURRENT_TRX = "SU67";
    }

    @Override
    public void executeTrx(IWebClient client, Map parametersExecute)
            throws TransactionException {
        // TODO Auto-generated method stub
//		parametersExecute = new HashMap();	
        try {

            execute(client, this.PARAMETER_TRX, parametersExecute);

            // Mapear los datos
//			mapData(parametersExecute);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new TransactionException(e);
        }

    }

    @Override
    public void executeTrx(IWebClient client, String... parameters)
            throws TransactionException {
        // TODO Auto-generated method stub
        try {

            execute(client, this.PARAMETER_TRX, this.mapInputParams(parameters));

            // Mapear los datos
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new TransactionException(e);
        }
    }

    @Override
    protected Map mapInputParams(String... parameters) {
        // TODO Auto-generated method stub
        Map parametersExecute = new HashMap<Object, Object>();

        for (int i = 0; i < parameters.length; i++) {
            parametersExecute.put(FIELDS_INPUT[i], parameters[i]);
        }
        return parametersExecute;
    }

    @Override
    protected void mapData(Map parametersExecute) {
        log.info("Se mapean los datos");

        // BasicDynaBean bean = (BasicDynaBean)
        // parametersExecute.get("mensajesRespuesta");
    }

}
