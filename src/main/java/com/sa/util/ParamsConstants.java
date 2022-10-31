package com.sa.util;

import com.sa.entities.DelegacionAccion;
import java.util.ArrayList;
import java.util.List;

public class ParamsConstants {

    /**
     * PARAMETROS PARA COMBO DE MOTIVOS.
     */
    public final static String MOTIVOS_OPCION = "4";
    public final static String MOTIVOS_TABLA = "";
    public final static String MOTIVOS_SUBTABLA = "";
    public final static String MOTIVOS_CODIGO = "";
    public final static String MOTIVOS_CANTIDAD = "";

    /**
     * PARAMETROS PARA COMBO DE GASTOS
     */
    public final static String GASTOS_OPCION = "3";
    public final static String GASTOS_TABLA = "";
    public final static String GASTOS_SUBTABLA = "";
    public final static String GASTOS_CODIGO = "";
    public final static String GASTOS_CANTIDAD = "";
    /**
     * PARAMETROS PARA COMBO ESTADOS DE RENDICION.
     */
    public final static String EST_REND_OPCION = "2";
    public final static String EST_REND_TABLA = "00002";
    public final static String EST_REND_SUBTABLA = "00003";
    public final static String EST_REND_CODIGO = "00000";
    public final static String EST_REND_CANTIDAD = "01";

    /**
     * PARAMETROS PARA COMBO TIPO DE MONEDA
     */
    public final static String MONEDA_OPCION = "2";
    public final static String MONEDA_TABLA = "00002";
    public final static String MONEDA_SUBTABLA = "00004";
    public final static String MONEDA_CODIGO = "00000";
    public final static String MONEDA_CANTIDAD = "01";

    /**
     * PARAMETROS PARA COMBO TIPO DE COMPROBANTE
     */
    public final static String COMPROBANTE_OPCION = "2";
    public final static String COMPROBANTE_TABLA = "00002";
    public final static String COMPROBANTE_SUBTABLA = "00006";
    public final static String COMPROBANTE_CODIGO = "00000";
    public final static String COMPROBANTE_CANTIDAD = "01";

    /**
     * PARAMETROS PARA COMBO TIPO DE GASTO
     */
    public final static String TIPO_GASTO_OPCION = "3";
    public final static String TIPO_GASTO_TABLA = "";
    public final static String TIPO_GASTO_SUBTABLA = "";
    public final static String TIPO_GASTO_CODIGO = "";
    public final static String TIPO_GASTO_CANTIDAD = "";

    /**
     * PARAMETROS SU65
     */
    public final static String PAGOS_OPCION = "ALTA";
    public final static String PAGOS_IDPROCESO = "0";
    public final static String PAGOS_TIPOPROCESOCIERREDIARIO = "PCIEDIAR";
    public final static String PAGOS_ESTADOPROCESO = "PENDI";
    public final static String PAGOS_NUMEROREGISTRO = "0";
    public final static String PAGOS_DESCRIPCION = "CIERRE DIARIO";
    public final static String PAGOS_TIPOPROCESOREACTIVACIONSUSPENSOS = "PREACTIV";
    public final static String PAGOS_TIPOPROCESOCIERREMENSUALFORMAL = "PCIEMENF";
    public final static String PAGOS_TIPOPROCESOCIERREMENSUALSIMULADO = "PCIEMENS";
    public final static String PAGOS_TIPOPROCESOCIERREMENSUALCONTABLE = "PCIEMENC";
    public final static String PAGOS_TIPOPROCESOVERSIMULACION = "PSIMULAC";

    /**
     * ***************** PARAMS PARA AMB
     * DELEGACIONES****************************************************
     */
    public final static String SU81_CONSULTA = "CONS";
    public final static String SU81_ALTA = "ALTA";
    public final static String SU81_MODIFICACION = "MODI";
    public final static String SU81_BAJA = "BAJA";

    /**
     * Obtiene el objeto fijo para el combo y valores de Acciones de abm
     * delegaciones.
     *
     * @return
     */
    public final static List<DelegacionAccion> getAccionesDelegaciones() {
        List<DelegacionAccion> acciones = new ArrayList<DelegacionAccion>();
        acciones.add(new DelegacionAccion("I", "I - Ingreso de rendiciones"));
        acciones
                .add(new DelegacionAccion("A", "A - Aprobaci\u00f3n de rendiciones"));
        acciones.add(new DelegacionAccion("T", "T - Todas las anteriores"));
        return acciones;
    }

    public final static String MJE_MODIF_OK = "Se modificaron correctamente los datos";
    public final static String MJE_MODIF_NO_OK = "Error al grabar los datos.";
    public final static String MJE_ALTA_OK = "Alta efectuada.";
}
