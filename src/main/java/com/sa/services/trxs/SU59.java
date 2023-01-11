package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.ComboGenerico;
import com.sa.entities.DatosPantallaDinamica;
import com.sa.services.Transaction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BasicDynaBean;

public class SU59 extends Transaction {

    private final String[] FIELDS_INPUT = new String[]{};
    private HashSet<String> headers = new HashSet<String>();
    private Map<String, String> mapCod1 = new HashMap<String, String>();
    private Map<String, String> mapCod2 = new HashMap<String, String>();
    private List<List<String>> filas = new ArrayList<List<String>>();

    SimpleDateFormat sdfDMY = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");

    public SU59(List<DatosPantallaDinamica> fieldsScreen) {
        this.PARAMETER_TRX = "SUM_CONS_DET_OBLIGATORIOS";
        this.CURRENT_TRX = "SU59";

        for (DatosPantallaDinamica dato : fieldsScreen) {
            this.headers.add(dato.getTipoCampo());

            if (dato.getTipoCampo().equals("COD1")) {
                for (ComboGenerico opcion : dato.getOpcionesCombo()) {
                    mapCod1.put(opcion.getId(), opcion.getDescripcion());
                }
            }

            if (dato.getTipoCampo().equals("COD2")) {
                for (ComboGenerico opcion : dato.getOpcionesCombo()) {
                    mapCod2.put(opcion.getId(), opcion.getDescripcion());
                }
            }
        }
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
            throw new TransactionException(e);
        }
    }

    @Override
    public void executeTrx(IWebClient client, String... parameters) throws TransactionException {
    }

    @Override
    protected Map mapInputParams(String... parameters) {
        Map parametersExecute = new HashMap<Object, Object>();

        for (int i = 0; i < parameters.length; i++) {
            parametersExecute.put(FIELDS_INPUT[i], parameters[i]);
        }
        return parametersExecute;
    }

    @Override
    protected void mapData(Map parametersExecute) {
        log.info("Mapeo SU59");

        List list = parametersExecute.get("lista") != null ? (List) parametersExecute.get("lista") : new ArrayList();;

        for (Object obj : list) {
            BasicDynaBean bean = (BasicDynaBean) obj;
            String str = (String) bean.get("lista");

            List<String> columnas = new ArrayList<String>();

            if (this.headers.contains("COD1")) {
                String cod1 = str.substring(9, 14);
                String descCod1 = mapCod1.get(cod1);
                if (descCod1 == null) {
                    columnas.add(cod1);
                } else {
                    columnas.add(descCod1);
                }
            }

            if (this.headers.contains("COD2")) {
                String cod2 = str.substring(14, 19);
                String descCod2 = mapCod2.get(cod2);
                if (descCod2 == null) {
                    columnas.add(cod2);
                } else {
                    columnas.add(descCod2);
                }
            }

            if (this.headers.contains("TXT1")) {
                columnas.add(str.substring(19, 69));
            }

            if (this.headers.contains("TXT2")) {
                columnas.add(str.substring(69, 119));
            }

            if (this.headers.contains("NUM1")) {
                columnas.add(str.substring(119, 128));
            }

            if (this.headers.contains("NUM2")) {
                columnas.add(str.substring(128, 137));
            }

            if (this.headers.contains("FEC1"))
				try {
                columnas.add(sdfDMY.format(sdfYMD.parse(str.substring(137, 147))));
            } catch (ParseException e) {
                columnas.add("");
                e.printStackTrace();
            }

            if (this.headers.contains("FEC2"))
				try {
                columnas.add(sdfDMY.format(sdfYMD.parse(str.substring(147, 157))));
            } catch (ParseException e) {
                columnas.add("");
                e.printStackTrace();
            }

            if (this.headers.contains("TXT250")) {
                columnas.add(str.substring(157));
            }

            this.filas.add(columnas);
        }
    }

    @Override
    public List getDataReturnList() {
        return filas;
    }
}
