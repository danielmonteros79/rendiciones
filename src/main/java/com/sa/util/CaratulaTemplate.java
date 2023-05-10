package com.sa.util;

public class CaratulaTemplate {

    public final static String REPLACE_RENDICION = "<!-- idRendicion -->";
    public final static String REPLACE_USUARIO = "<!-- usuario -->";
    public final static String REPLACE_CCOSTOS = "<!-- ccostos -->";
    public final static String REPLACE_MOTIVO = "<!-- motivo -->";
    public final static String REPLACE_FDESDE = "<!-- fechaDesde -->";
    public final static String REPLACE_FHASTA = "<!-- fechaHasta -->";
    public final static String REPLACE_DESCRIPCION = "<!-- descripcion -->";
    public final static String REPLACE_GASTOS = "<!-- gastos -->";
    public final static String REPLACE_IDU = "<!-- idu -->";
    public final static String REPLACE_ADEA = "<!-- adea -->";
    public final static String REPLACE_FECHAHOY = "<!-- fechaHoy -->";
    public final static String REPLACE_BBVAIMAGEN = "<!-- BbvaLogo -->";

    /**
     * TEMPLATE HTML DE LA CARATULA.
     */
    public final static String CARATULA_HTML = "<html> <head>"
            + "<table border='0' width='500' >"
            + "<tr>"
            + "<td  align='left' style='font-weight: normal;vertical-align:top'> "
            + "<table border='0' width='500' style='font-weight:normal;' >"
            + "<tr>"
            + "<td align='left' style='vertical-align:top;'> "
            + REPLACE_BBVAIMAGEN
            + "</td>"
            //			+ "<td align='center' style='vertical-align:top;margin-left:25px;' colspan='2'> <h3 style='font-weight:normal;'>CRE - RENDICION DE GASTOS </br> </h3> <h4> NRO: "
            + "<td align='right'><h4> NRO: "
            + REPLACE_RENDICION
            //			+ "</h4></br><h4>Caratula</h4></td></tr>"
            + "</h4></td>"
            + "</tr>"
            + "</table>"
            + "</td>"
            + "</tr>"
            + "</table> "
            + "</head> "
            + "<body> "
            + "<table width='600'>"
            + "<tr style='width:100%;'>"
            + "<td style='width:100%';>"
            + "<div style='padding:0px;margin-right:0;width:100%;'><strong>USUARIO:</strong> "
            + REPLACE_USUARIO
            + "</div>"
            + "</td>"
            + "</tr>"
            //			+ "<tr style='width:100%;'>"
            //			+ "<td style='width:100%';>"
            //			+ "<div style='padding:0px;margin-right:0;width:100%;'><strong>FECHA ALTA:</strong> "
            //			+ REPLACE_FECHAHOY
            //			+ "</div>"
            //			+ "</td>"
            //			+ "</tr>"
            + "<tr style='width:100%;'>"
            + "<td style='width:100%';>"
            + "	<div  style='padding:0px;margin-right:0;width:100%;'><strong>CCOSTOS:</strong> "
            + REPLACE_CCOSTOS
            + "</div>"
            + "</td>"
            + "</tr>"
            + "<tr style='width:100%;'>"
            + "<td style='width:100%';>"
            + "<div style='padding:0px;margin-right:0;width:100%;'><strong>MOTIVO RENDICION:</strong> "
            + REPLACE_MOTIVO
            + " </div>"
            + "</td>"
            + "</tr>"
            + "<tr style='width:100%;'>"
            + "<td style='width:100%';>"
            + "	<div  style='padding:0px;margin-right:0;width:100%;'><strong>INTERVALO FECHAS:</strong> "
            + REPLACE_FDESDE
            + " - "
            + REPLACE_FHASTA
            + "</div>"
            + "</td>"
            + "</tr>"
            + "<tr style='width:100%;'>"
            + "<td style='width:100%';>"
            + "<div style='padding:0px;margin-right:0;width:100%;'><strong>DESCRIPCION:</strong> "
            + REPLACE_DESCRIPCION
            + " </div>"
            + "  </td>"
            + "  </tr>"
            + "</table>"
            + "<br> <h4> GASTOS: </h4>"
            + "<table width='600'>"
            + REPLACE_GASTOS
            + "<tr></tr>"
            + "</table>"
            + "<br>"
            + "<table width='600'>"
            + "<tr style='width:100%;'>"
            + "<td style='width:100%';>"
            + "<div style='padding:0px;margin-right:0;width:100%;'><strong>FECHA DE PRESENTACION:</strong> "
            + REPLACE_FECHAHOY
            + "</div>"
            + "</td>"
            + "</tr>"
            + "</table>"
            + "</body> "
            //			+ "<table border='0' width='500'>"
            //			+ "<tr>"
            //			+ "	<td  align='left' style='font-weight: bold;padding:1px;'> "
            //			+ "	<table border='0' width='250' height='30' float='right'>"
            //			+ "		<tr><td align='center' style='vertical-align:top;'>Etiqueta ADEA </td></tr>"
            //			+ "		<tr><td align='center' style='vertical-align:top;'> "
            //			+ REPLACE_ADEA
            //			+ " </td></tr>"
            //			+ "		</table>"
            //			+ "</td>"
            //			+ "<td  align='left' style='font-weight: bold;padding:1px;' width='20'> "
            //			+ "<table width='10'>"
            //			+ "	<tr><td align='center' style='vertical-align:top;'> </td></tr>"
            //			+ "					</table></td>"
            //			+ "<td  align='right' style='font-weight: bold;vertical-align:top;' float='right'> "
            //			+ "<table border='0' width='250' height='75'>"
            //			+ "		<tr><td align='center' style='vertical-align:top;'>Id Unico </td></tr>"
            //			+ "		<tr><td align='center' style='vertical-align:top;'> "
            //			+ REPLACE_IDU
            //			+ " </td></tr>"
            //			+ "	</table>"
            //			+ "				</td>"
            //			+ "   </tr>" 
            //			+ "</table>" 
            + "</html>";

}
