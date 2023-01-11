<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Res&uacute;menes anteriores</title>
    </head>
    <body>
        <logic:present name="message">
            <% String message = (String) request.getAttribute("message");
		
            if(message.contains("ERROR")) { %>
            <div id="messageErr" class="message">
                <%= message.substring(7) %>
            </div>
            <%} else if(message.contains("OK")) { %>
            <div id="messageOk" class="message">
                <%= message.substring(4) %>
            </div>
            <%} else {%>
            <div id="messageAviso" class="message">
                AVISO: <%= message %>
            </div>
            <%}%>
        </logic:present>

        <table style="margin-bottom:10px;">
            <thead>
                <tr>
                    <th>Filtro res&uacute;menes anteriores</th>
                </tr>
            </thead>
            <html:form action="filtrarResumen" styleId="ResumenForm">
                <tbody class="filtro" style="font-size: small; font-weight: bold;">
                    <tr>
                        <td style="text-align:left;">
                            Resumen
                            <html:select styleId="resumen" property="resumen" onchange="fechaChange()">
                                <html:option value=""></html:option>
                                <html:options collection="cmbResumen" property="id" labelProperty="descripcion"/>
                            </html:select>
                        </td>
                    </tr>
                </tbody>
            </html:form>
        </table>

        <div id="paginacion" style="margin-top:43px;display:none;">
            <display:table uid="row" name="resumenes" requestURI="filtrarResumen.do" id="ResumenTable" excludedParams="false"
                           decorator="com.sa.decorator.parametros.ParametrosMotivoTableDecorator" pagesize="15"
                           style="margin-left:-0.9%;width:99.7%;" export="true">
                <display:column media="html csv excel" property="fecha" title="Fecha" style="width:4%" sortable="true"
                                style="text-align:right;" format="{0,date,dd/MM/yyyy}"/>
                <display:column media="html csv excel" property="cupon" title="Cup&oacute;n" style="text-align:right;" />
                <display:column media="html csv excel" property="establecimiento" title="Establecimiento" sortable="true" style="text-align:left;"/>
                <display:column media="html csv excel" property="monto" title="Monto" style="text-align:right;"/>
                <display:column media="html csv excel" property="moneda" title="Moneda" />
                <display:column media="html csv excel" property="estado" sortable="true" title="Estado" />

                <display:setProperty name="export.csv.filename" value="ListadoResumen.csv"/>
                <display:setProperty name="export.excel.filename" value="ListadoResumen.xls"/>
            </display:table>
        </div>
        <script type="text/javascript">
            $(function () {
                $('#checkResumen').show();

                if ($('#resumen').val())
                    $('#paginacion').show();
            });

            function fechaChange() {
                $('form').submit();
            }
        </script>
    </body>
</html>