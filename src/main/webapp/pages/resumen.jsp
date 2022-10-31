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
        <title>Resumen</title>
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

        <div id="paginacion" style="margin-top:43px;">
            <display:table uid="row" name="resumen"
                           requestURI="resumen.do" id="ResumenTable" excludedParams="false"
                           decorator="com.sa.decorator.ResumenTableDecorator" pagesize="15"
                           style="margin-left:-0.9%;width:99.7%;" export="true">
                <display:column media="html csv excel" property="fecha" title="Fecha" sortable="true" style="width:10%;text-align:center;" format="{0,date,dd/MM/yyyy}"/>
                <display:column media="html csv excel" property="fechaDebito" title="Fecha Debito" sortable="true" style="width:13%;text-align:center;" format="{0,date,dd/MM/yyyy}"/>
                <display:column media="html csv excel" property="cupon" title="Cupon" style="text-align:right;" />
                <display:column media="html csv excel" property="establecimiento" title="Establecimiento" style="text-align:left;" sortable="true" />
                <display:column media="html csv excel" property="monto" title="Monto" style="text-align:right;" />
                <display:column media="html csv excel" property="moneda" title="Moneda" style="width:5%;text-align:center;" />
                <display:column media="html csv excel" property="estado" title="Estado" sortable="true" style="width:12%;" class="estado"/>

                <display:setProperty name="export.csv.filename" value="ListadoResumen.csv"/>
                <display:setProperty name="export.excel.filename" value="ListadoResumen.xls"/>
            </display:table>
        </div>

        <script type="text/javascript">
            jQuery(document).ready(function () {
                $('#checkResumen').show();

                $(".balloon").each(function () {
                    $(this).balloon({
                        html: true,
                        contents: $(this).prop('title'),
                        tipSize: 20,
                        position: "top",
                        css: {
                            border: 'solid 4px #0080FF',
                            padding: '10px',
                            fontSize: '14px',
                            fontWeight: 'bold',
                            backgroundColor: '#FFFFFF',
                            color: '#000000'
                        }
                    });
                });
            });
        </script>
    </body>
</html>