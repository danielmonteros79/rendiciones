<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="./css/Parametros.css">

        <script type="text/javascript">
            jQuery(document).ready(function () {
                $('#checkParametros').show();
            });
        </script>
    </head>
    <body>
        <div class="divParametros">
            <a href="parametrosGenerales.do"  class="linkParametros">Parametría general (SUDTCOD)</a></div>
        <div class="espacioBlanco"><br> </div>
        <div style="text-align:center;" class="divParametros">

            <a href="parametrosMotivo.do"  class="linkParametros">Parámetros de motivo (SUDTMOT)</a></div>
        <div  class="espacioBlanco"><br> </div>

        <div class="divParametros">
            <a href="parametrosGastos.do" class="linkParametros">Parámetros de gastos (SUDTPGA)</a></div>
        <div class="espacioBlanco"><br> </div>

        <div  class="divParametros">
            <a href="relacionMotivoGasto.do" class="linkParametros">Relación motivo/gasto (SUDTCOS)</a></div>
        <div class="espacioBlanco"><br> </div>

        <div class="divParametros">
            <a href="relacionMotivoCcostos.do" class="linkParametros">Relación motivo/ centro de costo (SUDTREL)</a></div>
        <div class="espacioBlanco"><br> </div>

        <div class="divParametros">
            <a href="relacionUsuarioDelegado.do" class="linkParametros">Relación usuarios / delegados (SUDTDEL)</a></div>
        <div class="espacioBlanco"><br> </div>

        <div class="divParametros">
            <a href="parametrosExceptuados.do" class="linkParametros">Exceptuados (SUDTEXC)</a></div>
        <div class="espacioBlanco"><br> </div>

        <div class="divParametros">
            <a href="parametrosAlertas.do" class="linkParametros">Alertas (SUDTALE)</a></div>
    </body>
</html>