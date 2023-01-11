package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Rendicion;
import com.sa.services.Transaction;
import com.sa.util.FormatosCampos;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BasicDynaBean;

public class SU53 extends Transaction {

    protected final String FORMATO_IDRENDICION = "0000000000000000";
    protected final String FORMATO_IDMOTIVO = "000000000";
    protected final String FORMATO_COD_MOTIVO = "    ";
    protected final String FORMATO_DESCRIPCION_MOTIVO = "                                                  ";
    protected final String FORMATO_DESCRIPCION_RENDICION = "                                                                                                                        ";
    protected final String FORMATO_ESTADO_RENDICION = "     ";
    protected final String FORMATO_FECHA_DESDE = "          ";
    protected final String FORMATO_FECHA_HASTA = "          ";
    protected final String FORMATO_IMPORTE = "          ";
    public List<Rendicion> listaRendiciones = new ArrayList<Rendicion>();

    public static String[] FIELDS_INPUT = new String[]{"codUsuario", "estadoRendicion", "fDesde", "fHasta", "cod_gasto"};
    public static String[] FIELDS_OUTPUT = new String[]{"mensajesRespuesta"};

    public SU53() {
        this.PARAMETER_TRX = "SUM_CONS_RENDICIONES";
        this.CURRENT_TRX = "SU53";
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
        try {
            execute(client, this.PARAMETER_TRX, this.mapInputParams(parameters));
        } catch (Exception e) {
            log.error(e);
            throw new TransactionException(e);
        }
    }

    @Override
    protected Map mapInputParams(String... parameters) {
        log.info("Se ejecuta el mapeo de datos con los parametros de entrada");

        Map parametersExecute = new HashMap();

        for (int i = 0; i < parameters.length; i++) {
            parametersExecute.put(FIELDS_INPUT[i], parameters[i]);
        }

        return parametersExecute;
    }

    @Override
    protected void mapData(Map parametersExecute) throws Exception {
        if (parametersExecute.get("lista") != null) {
            for (Object obj : (List) parametersExecute.get("lista")) {
                String str = (String) ((BasicDynaBean) obj).get("lista");
                Rendicion rendicion = new Rendicion();

                if (parametersExecute.get("aviso") != null && !((String) parametersExecute.get("aviso")).trim().equals("")) {
                    rendicion.setAviso((String) parametersExecute.get("aviso"));
                } else {
                    rendicion.setAviso("");
                }

                SimpleDateFormat toDate = new SimpleDateFormat("yyyy-MM-dd");
                String importe = str.substring(215, 232).replaceFirst("^0*", "");
                rendicion.setId(Integer.parseInt(str.substring(0, 16).replaceFirst("^0*", "")));
                rendicion.setCodMotivo(str.substring(16, 20));
                rendicion.setMotivo(str.substring(20, 66));
                rendicion.setCostosDestino(str.substring(66, 70));
                rendicion.setDescripcion(str.substring(70, 190).trim());
                rendicion.setEstado(str.substring(190, 195));
                rendicion.setFechaDesde(toDate.parse(str.substring(195, 205)));
                rendicion.setFechaHasta(toDate.parse(str.substring(205, 215)));
                rendicion.setImporte(str.substring(215, 232).replaceFirst("^0*", ""));

                int codThuban = str.length();
                if (codThuban > 232) {
                    rendicion.setIdu(str.substring(232, 242).trim());
                    if (codThuban > 242) {
                        rendicion.setAdea(str.substring(248, 259).trim());
                    } else {
                        rendicion.setAdea("");
                    }
                } else {
                    rendicion.setIdu("");
                    rendicion.setAdea("");
                }
                rendicion.setDescripcionEstado(((String) parametersExecute.get("desc_est_rend")).trim());
                rendicion.setUsuarioAprobador(((String) parametersExecute.get("nomUsrAprob")).trim());
                rendicion.setCodUsuarioAprobador(((String) parametersExecute.get("codUsrAprob")).trim());
                rendicion.setMotivoRechazo(((String) parametersExecute.get("desc_rechazo")).trim());

                if (parametersExecute.get("fec_ult_mod") != null) {
                    rendicion.setFechaUltimaModificacion(((String) parametersExecute.get("fec_ult_mod")).trim());
                }
                //rendicion.setAlerta(((String) obj).substring(259, 260));
                rendicion.setAlerta("0");

                listaRendiciones.add(rendicion);
            }
        }
    }

    @Override
    public List getDataReturnList() {
        log.info("Se da return al listado de rendiciones");
        return listaRendiciones;
    }

    public String getRendiciones(String idRendicion, String idMotivo, String codMotivo, String descripcionMotivo,
            String descripcionRendicion, String fechaDesde, String fechaHasta, String Importe) throws TransactionException {
        String strIn241 = FormatosCampos.formatString(idRendicion, FORMATO_IDRENDICION)
                + FormatosCampos.formatString(idMotivo, FORMATO_IDMOTIVO + "")
                + FormatosCampos.formatString(codMotivo, FORMATO_COD_MOTIVO + "")
                + FormatosCampos.formatString(descripcionMotivo, FORMATO_DESCRIPCION_MOTIVO + "")
                + FormatosCampos.formatString(descripcionRendicion, FORMATO_DESCRIPCION_RENDICION + "")
                + FormatosCampos.formatString(fechaDesde, FORMATO_FECHA_DESDE + "")
                + FormatosCampos.formatString(fechaHasta, FORMATO_FECHA_HASTA + "")
                + FormatosCampos.formatString(Importe, FORMATO_IMPORTE + "");

        return strIn241;
    }
}
