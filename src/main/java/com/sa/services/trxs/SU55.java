package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Cupones;
import com.sa.entities.Gastos;
import com.sa.services.Transaction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BasicDynaBean;

public class SU55 extends Transaction {

    public List<Gastos> listaGastos = new ArrayList<Gastos>();
    public List<Gastos> listaGastosRedistribuidos = new ArrayList<Gastos>();
    public List<Cupones> listaCupones = new ArrayList<Cupones>();

    private final String[] FIELDS_INPUT = new String[]{};

    public SU55() {
        this.PARAMETER_TRX = "SUM_CONS_DET_GASTOS_REND";
        this.CURRENT_TRX = "SU55";
    }

    @Override
    public void executeTrx(IWebClient client, Map parametersExecute) throws TransactionException {
        // execute(client, this.PARAMETER_TRX, parametersExecute);
        try {
            execute(client, this.PARAMETER_TRX, parametersExecute);
            mapData(parametersExecute);

        } catch (Exception e) {
            log.error(e);
            if (!e.getMessage().contains("INEX")) {
                throw new TransactionException(e);
            }
        }

    }

    @Override
    public void executeTrx(IWebClient client, String... parameters) throws TransactionException {
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
        log.info("Mapeo SU55");
        List list = (List) parametersExecute.get("lista");
        List lista2 = (List) parametersExecute.get("lista2");

        // BasicDynaBean bean = (BasicDynaBean)
        // parametersExecute.get("mensajesRespuesta");
        try {
            if (lista2 == null || lista2.isEmpty()) {
                for (Object obj : list) {
                    Object object;
                    BasicDynaBean bean = (BasicDynaBean) obj;
                    String str = (String) bean.get("lista");

                    // parametersExecute = new HashMap();
                    Gastos gasto = new Gastos();
                    gasto.setCostosDestino((String) parametersExecute.get("centro_costo"));
                    gasto.setIdGasto(str.substring(0, 9).replaceFirst("^0*", ""));

                    gasto.setNroGasto(str.substring(9, 13));
                    gasto.setDescGasto(str.substring(13, 63).trim());
                    gasto.setMonto(str.substring(63, 80));// .replaceFirst("^0*",
                    // ""));
                    gasto.setMoneda(str.substring(80, 83));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String fechaG = (str.substring(83, 93));
                    Date date = null;
                    if (fechaG != null && !fechaG.equalsIgnoreCase("")) {
                        date = formatter.parse(fechaG);
                    }
                    if (date != null) {
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy ");

                        fechaG = df.format(date).trim();
                    }
                    gasto.setFechagastos(fechaG);
                    gasto.setTipoComprobante(str.substring(93, 97));
                    // gasto.setObs("00003");//
                    gasto.setObs(str.substring(112, 117).trim());

                    // gasto.setObsObligatoria("S");
                    gasto.setObsObligatoria(str.substring(117, 118).trim());// "S");
                    // gasto.setTarjeta("N");
                    gasto.setComprobante(str.substring(97, 112).trim());

                    gasto.setTarjeta(str.substring(118, 119));
                    gasto.setCuponGasto(str.substring(119, 131).replaceFirst("^0*", ""));
                    // gasto.set(str.substring(132,135)
                    // .replaceFirst("^0*", ""));
                    gasto.setObservacionGasto(str.substring(131, 251).trim());
                    gasto.setCuit1(str.substring(251, 253));
                    gasto.setCuit2(str.substring(253, 261));
                    gasto.setCuit3(str.substring(261, 262));
                    gasto.setCmbComprobante(str.substring(264, 265));
                    gasto.setComprobante1(str.substring(265, 269).equals("0000") ? "" : str.substring(265, 269));
                    gasto.setComprobante2(str.substring(269, 277).equals("00000000") ? "" : str.substring(269, 277));
                    gasto.setCentroCostoGasto(str.substring(277, 281).trim());

                    if (parametersExecute.containsKey("DERRAME")) {
                        if (str.length() > 281) {
                            gasto.setIdGastoOriginal(str.substring(281, str.length()).trim().replaceFirst("^0*", ""));
                            listaGastosRedistribuidos.add(gasto);
                        }
                    } else {
                        if (!(str.length() > 281)) {
                            listaGastos.add(gasto);
                        }
                    }
                }
            } else {
                for (Object obj : lista2) {
                    Object object;
                    BasicDynaBean bean = (BasicDynaBean) obj;
                    String str2 = (String) bean.get("lista2");
                    Cupones cupones = new Cupones();
                    cupones.setFechaPresentacion(str2.substring(0, 10));
                    cupones.setNroCupon(str2.substring(10, 22));
                    cupones.setEstablecimiento(str2.substring(22, 52));
                    cupones.setLiquidacionNeto(str2.substring(52, 69));
                    cupones.setMoneda(str2.substring(69, 72));
                    listaCupones.add(cupones);
                }

            }
            if (parametersExecute.containsKey("DERRAME")) {
                parametersExecute.remove("DERRAME");
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("Error mapeo de datos SU55", e);
            e.printStackTrace();
        }
    }

    @Override
    public List getDataReturnList() {
        log.info("Se da return al listado de rendiciones");
        if (listaCupones == null || listaCupones.isEmpty()) {
            if (listaGastos == null || listaGastos.isEmpty()) {
                return listaGastosRedistribuidos;
            }
            return listaGastos;
        } else {
            return listaCupones;
        }
    }
}
