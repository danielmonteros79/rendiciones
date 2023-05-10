package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.ComboGenerico;
import com.sa.entities.DatosPantallaDinamica;
import com.sa.services.Transaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.lang.StringUtils;

public class SU57 extends Transaction {

    private final String[] FIELDS_INPUT = new String[]{};

    public SU57() {
        this.PARAMETER_TRX = "SUM_CONS_DET_DINAMIC";
        this.CURRENT_TRX = "SU57";
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
        log.info("Mapeo SU57");

        List list = (List) parametersExecute.get("lista");
//		list.add("FEC1           SSFECHA TEST");
//		list.add("TXT250         SSTEXTAREA TEST");
//		list.add("NUM1             NUM TEST 2");
//		list.add("FEC2             FECHA TEST 2");
//		list.add("NUM2             NUM TEST 2");
//		list.add("COD2           SSCOMBO TEST                                        040010001 OPCION 1                                          040010002 OPCION 2                                          040010003 OPCION 3");
        for (Object obj : list) {
            DatosPantallaDinamica datoPantalla = new DatosPantallaDinamica();
            String str = "";
            try {
                BasicDynaBean bean = (BasicDynaBean) obj;
                str = (String) bean.get("lista_campo1");
            } catch (Exception e) {
                str = (String) obj;
            }
            datoPantalla.setTipoCampo(str.substring(0, 15).trim());
            datoPantalla.setMostrar(str.substring(15, 16));
            datoPantalla.setCampoObligatorio(str.substring(16, 17));

            // VERIFICA SI ES UN COMBO. LOS COMBOS SE DEFINEN CON COD.
            if (datoPantalla.getTipoCampo().contains("COD")) {
                try {
                    datoPantalla.setTituloCampo(str.substring(17, 67).trim());
                } catch (Exception e) {
                    datoPantalla.setTituloCampo(str.substring(17).trim());
                }

                int cantOpciones = (int) Math.ceil((str.length() - 67) / 60.0);
                for (int i = 0; i < cantOpciones; i++) {
                    int indexOpcion = 72 + 60 * i;
                    ComboGenerico cmb = new ComboGenerico();
                    cmb.setId(StringUtils.leftPad(str.substring(indexOpcion, indexOpcion + 5).trim(), 5, "0"));

                    try {
                        cmb.setDescripcion(cmb.getId() + " - " + str.substring(indexOpcion + 5, indexOpcion + 55).trim());
                    } catch (Exception e) {
                        cmb.setDescripcion(cmb.getId() + " - " + str.substring(indexOpcion + 5).trim());
                    }

                    datoPantalla.addOpcionCombo(cmb);
                }
            } else {
                datoPantalla.setTituloCampo(str.substring(17, str.length()).trim());
            }

            dataReturnList.add(datoPantalla);
        }

    }
}
