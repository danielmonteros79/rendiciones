package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Rendicion;
import com.sa.services.Transaction;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("rawtypes")
public class SU61 extends Transaction {

    private static final Log log = LogFactory.getLog(SU61.class);

    public List<Rendicion> listaRendiciones = new ArrayList<Rendicion>();
    public String cantRendiciones = "";

    public SU61() {
        this.PARAMETER_TRX = "SUM_CONS_REND_PEND_APROB";
        this.CURRENT_TRX = "SU61";
    }

    @Override
    public void executeTrx(IWebClient client, Map parametersExecute) throws TransactionException {
        try {
            execute(client, this.PARAMETER_TRX, parametersExecute);
            mapData(parametersExecute);
        } catch (Exception e) {
            log.error(e);
            throw new TransactionException(e);
        }
    }

    @Override
    public void executeTrx(IWebClient client, String... parameters) throws TransactionException {
        try {
            execute(client, this.PARAMETER_TRX, this.mapInputParams(parameters));
        } catch (Exception e) {
            log.error(e);
            throw new TransactionException(e);
        }
    }

    @Override
    protected Map mapInputParams(String... parameters) {
        return null;
    }

    @Override
    protected void mapData(Map parametersExecute) {
        List list = (List) parametersExecute.get("lista");

        if (list != null) {
            for (Object obj : list) {
                BasicDynaBean bean = (BasicDynaBean) obj;
                String str = (String) bean.get("lista");

                String importe = (str.substring(210, 227).replaceFirst("^0*", ""));
                Rendicion rendicion = new Rendicion();
                SimpleDateFormat toDate = new SimpleDateFormat("yyyy-MM-dd");
                rendicion.setGlg((String) parametersExecute.get("glg"));

                rendicion.setId(Integer.parseInt(str.substring(0, 16).replaceFirst("^0*", "")));
                rendicion.setIdMotivo(Integer.parseInt(str.substring(16, 20)));
                rendicion.setCodMotivo(str.substring(16, 20));
                rendicion.setDescripcionMotivo(str.substring(20, 70).trim());

                rendicion.setDescripcion(str.substring(70, 190).trim());
                rendicion.setImporte(importe);
                try {
                    rendicion.setFechaDesde(toDate.parse(str.substring(190, 200)));
                    rendicion.setFechaHasta(toDate.parse(str.substring(200, 210)));

                } catch (ParseException e1) {
                    e1.printStackTrace();
                }

                rendicion.setUsuarioRendicion(str.substring(227, 235));
                rendicion.setNombreUsuarioRendicion(str.substring(235, 310).trim());
                rendicion.setEstado(str.substring(310, 315).trim());
                rendicion.setDescripcionEstado(str.substring(315, str.length()).trim());
                if (str.length() > 365) {
                    rendicion.setDescripcionEstado(str.substring(315, 365).trim());
                    rendicion.setIdu(str.substring(365, 375));
                    rendicion.setAdea(str.substring(365, 375));

                }
                listaRendiciones.add(rendicion);
            }
        }

        if (parametersExecute.get("cantidad") != null && !((String) parametersExecute.get("cantidad")).trim().equals("")) {
            this.cantRendiciones = (String) parametersExecute.get("cantidad");
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(new Locale("es_AR"));
            DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            formatter.setDecimalFormatSymbols(symbols);
            this.cantRendiciones = formatter.format(Long.parseLong(this.cantRendiciones.replaceFirst("^0+(?!$)", "")));
        }
    }

    @Override
    public List getDataReturnList() {
        return listaRendiciones;
    }

    @Override
    public Object getDataReturn() {
        return this.cantRendiciones;
    }
}
