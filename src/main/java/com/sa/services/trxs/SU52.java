package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Usuario;
import com.sa.services.Transaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BasicDynaBean;

public class SU52 extends Transaction {

    private final String[] FIELDS_INPUT = new String[]{};

    public SU52() {
        // TODO Auto-generated constructor stub
        this.PARAMETER_TRX = "SUM_CONS_USERDATA";
        this.CURRENT_TRX = "SU52";
    }

    @Override
    public void executeTrx(IWebClient client, Map parametersExecute)
            throws TransactionException {
        // TODO Auto-generated method stub

        try {

            execute(client, this.PARAMETER_TRX, parametersExecute);

            // Mapear los datos
            mapData(parametersExecute);

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
        // TODO Auto-generated method stub
        log.info(this.CURRENT_TRX
                + " --> Se procede a mapear los datos de la transaccion");
        List<Usuario> delegados = new ArrayList<Usuario>();
        List retorno = (List) parametersExecute.get("lista");

        for (Object obj : retorno) {
            Object object;
            BasicDynaBean bean = (BasicDynaBean) obj;
            String str = (String) bean.get("lista");
            if (str.substring(0, 8).trim().equals(
                    parametersExecute.get("cod_user"))) {
                delegados.add(new Usuario(str.substring(0, 8).trim(),
                        (String) parametersExecute.get("facultad"), str
                        .substring(8, str.length()).trim(), Integer
                        .valueOf((String) parametersExecute
                                .get("ctro_costos")),
                        (String) parametersExecute.get("sector"), null));
            } else {
                delegados.add(new Usuario(str.substring(0, 8).trim(), str
                        .substring((str.length() - 1), str.length()).trim(),
                        str.substring(8, 83).trim(), Integer.valueOf(str
                        .substring(83, 87)), str.substring(87, (str
                        .length() - 1)), null));
            }
        }
        this.dataReturn = new Usuario(
                ((String) parametersExecute.get("cod_user")).trim(),
                (String) parametersExecute.get("facultad"),
                ((String) parametersExecute.get("nombre_apellido")).trim(),
                !parametersExecute.get("ctro_costos").equals("") ? Integer
                .valueOf((String) parametersExecute.get("ctro_costos"))
                : 0,
                !parametersExecute.get("sector").equals("") ? ((String) parametersExecute
                .get("sector")).trim()
                : "", delegados);

    }
}
