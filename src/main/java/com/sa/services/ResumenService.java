package com.sa.services;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.ComboOpcion;
import com.sa.entities.parametros.Resumen;
import com.sa.manager.ManagerTransaction;
import com.sa.services.trxs.SU68;
import com.sa.services.trxs.SU69;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumenService {

    private SAMWebClient client;
    private String msg;

    public ResumenService(SAMWebClient samClient) {
        this.client = samClient;
    }

    @SuppressWarnings("unchecked")
    public List<Resumen> getConsumos(String user) throws TransactionException {
        ManagerTransaction manager = new ManagerTransaction(new SU68());
        Map<String, String> parametersExecute = new HashMap<String, String>();

        parametersExecute.put("pantalla", "resumen");
        parametersExecute.put("opcion", "USU");
        parametersExecute.put("subtran", "MOP");
        parametersExecute.put("codapli", "SU");
        parametersExecute.put("usuario", user);

        manager.executeTrx(this.client, parametersExecute);

        List<Resumen> resumen = (List<Resumen>) manager.getDataReturnList();
        this.msg = (String) manager.getMensajeAviso();

        return resumen;
    }

    @SuppressWarnings("unchecked")
    public List<ComboOpcion> getFechasResumenes(String user) throws TransactionException {
        ManagerTransaction manager = new ManagerTransaction(new SU69());
        Map<String, String> parametersExecute = new HashMap<String, String>();

        parametersExecute.put("opcion", "CON");
        parametersExecute.put("subtran", "FEC");
        parametersExecute.put("usuario", user);

        manager.executeTrx(this.client, parametersExecute);

        List<ComboOpcion> fechas = (List<ComboOpcion>) manager.getDataReturnList();
        this.msg = (String) manager.getMensajeAviso();

        return fechas;
    }

    @SuppressWarnings("unchecked")
    public List<Resumen> getResumenes(String user, String fecha) throws TransactionException {
        ManagerTransaction manager = new ManagerTransaction(new SU69());
        Map<String, String> parametersExecute = new HashMap<String, String>();

        parametersExecute.put("opcion", "CON");
        parametersExecute.put("subtran", "RES");
        parametersExecute.put("usuario", user);
        parametersExecute.put("fecierr", fecha);

        manager.executeTrx(this.client, parametersExecute);

        List<Resumen> resumen = (List<Resumen>) manager.getDataReturnList();
        this.msg = (String) manager.getMensajeAviso();

        return resumen;
    }

    public String getMsg() {
        return msg;
    }
}
