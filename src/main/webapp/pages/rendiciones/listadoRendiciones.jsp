<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>
<head>
    <script type="text/javascript" src="./js/listadoRendiciones.js"></script>

    <style type="text/css">
        #paginado{
            color:black!important;
        }
    </style>

    <logic:equal value="1" name="tipoSubmit">
        <script>
            window.location.href = "listaRendiciones.do";
        </script>
    </logic:equal>
</head>
<body>
    <script type="text/javascript">
        $(document).ready(function () {
            var msg = "<%= request.getSession().getAttribute("msg") %>";
            if (msg != "null") {
                recuperarForm();
                alert(msg);
                if (msg.includes("Error"))
                    window.location.href = "listaRendiciones.do";
            }
        <% request.getSession().removeAttribute("msg"); %>

            document.getElementById("BotonManual").style.display = "";
            document.getElementById("BotonPreguntas").style.display = "";
        });
    </script>	
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

    <table>
        <thead>
            <tr>
                <th colspan="3">Filtro rendiciones</th>
            </tr>
        </thead>

        <% Usuario delegado = (Usuario) request.getSession().getAttribute("userWorking"); %>

        <html:form action="filtroRendiciones" styleId="filtroRendiciones">
            <html:hidden property="delegado" value="<%=delegado.getIdUser()%>" 
                         style="color:#027362; width:30%; text-align:left; font-weight: bold; border-style:none;" />
            <tbody class="listadoRendiciones">
                <tr>
                    <td align="left" style="width: 25%; float: left;">
                        ID
                        <html:text property="id" style="width: 70%" styleId="id" maxlength="16" onkeypress="return numericOnly(event);"/>
                    </td>
                    <td align="left" style="width: 30%; float: left;">
                        Desde
                        <html:text property="fechaDesde" styleId="fechaDesde" size="10" styleClass="fechaDDMMYY" />
                        <a href='#' onClick="showCalendar('fechaDesde')">
                            <img id="imageCal1" src='./images/Calendar.png' border='0' style="float:left;position:absolute;">
                        </a>
                    </td>
                    <td align="left" style="width: 25%; float: left;">
                        Hasta
                        <html:text property="fechaHasta" styleId="fechaHasta" size="10"/>
                        <a href='#' onClick="showCalendar('fechaHasta')" style="width:20%;">
                            <img id="imageCal2" src='./images/Calendar.png' width="22px" height="22px" style="float:left;position:absolute;">
                        </a>
                    </td>
                    <td>
                        <html:submit style="float:left; margin-right:4.1%" styleClass="buttonFilter" value="Filtrar" />
                    </td>
                    <td>
                        <html:button style="float:left; margin-rigth:4.1%" property="" value="Limpiar" styleClass="buttonClear" onclick="resetForm();"/>
                    </td>
                </tr>
            </tbody>
        </html:form>
    </table>
    <html:link styleId="altaRendicion" action="AltaRendicion.do" title="ALTA DE RENDICI&Oacute;N" style="float:right; margin-right:1.2%;margin-top:3px;" >
        <input type="hidden" name="accion" value="alta"/>
        <img src="./images/iconos/edit-11-48.png" alt="Alta de rendici&oacute;n" height="32" width="32"> 
    </html:link>
    <html:form action="borrarRendicion" styleId="RendicionForm">
        <div id="paginacion" style="margin-top: 43px;">
            <display:table uid="row" name="Rendicion"
                           requestURI="filtroRendiciones.do" id="RendicionesTable" excludedParams="username password"
                           decorator="com.sa.decorator.RendicionesTableDecorator" pagesize="15"
                           style="margin-left:-0.9%;width:99.7%;" export="false">
                <display:column property="id" title="ID" style="width:4%;text-align:center" sortable="true" media="html csv excel" />
                <display:column property="motivo" title="Motivo" style="width:16%" media="html csv excel" />
                <display:column property="descripcion" title="Descripcion" style="width:20%" maxLength="55" media="html csv excel" />
                <display:column property="estado" title="Estado" style="width:5%" maxLength="5" media="none" />
                <display:column property="fechaDesde" format="{0,date,dd/MM/yyyy}" title="Desde" style="width:7%;text-align:center;" media="html csv excel"/>
                <display:column property="fechaHasta" format="{0,date,dd/MM/yyyy}" title="Hasta" style="width:7%;text-align:center;" media="html csv excel"/>
                <display:column property="importe" title="Importe (*)" style="width:10%;text-align:right;" media="html csv excel"/>
                <display:column property="statusColor" title="Estado"  style="width:3%;text-align:center;" media="html" />
                <display:column property="opciones" title="Opciones" style="width:3%;text-align:center;" media="html" />
                <display:column property="caratula" title="Carátula" style="width:1%;text-align:center;" media="none" />
                <display:column property="cupones" title="Alerta" style="width:1%; text-align:center;" media="html" />

                <display:setProperty name="export.csv.filename" value="ListadoRendiciones.csv"/>
                <display:setProperty name="export.excel.filename" value="ListadoRendiciones.xls"/>
            </display:table>
        </div>
    </html:form>
    <div style="color:#FF0000;">
        <br/><br/>
        <p>(*) importe rendici&oacute;n estimado</p>
    </div>
    <script type="text/javascript">
        function resetForm() {
            document.getElementById("filtroRendiciones").reset();
            $("#fechaDesde").val("");
            $("#fechaHasta").val("");
            $("#id").val("");
            $("#estado").val("");
        }
        function nofunciona() {
            alert("Temporalmente no disponible");
        }
    </script>
    <logic:equal value="1" name="tipoSubmit">
        <script>
            alert("Se dio de alta una rendicion");
        </script>
    </logic:equal>
    <script type="text/javascript">
        function eliminarRendicion(idRendicion) {
            if (confirm("Estas seguro que quieres eliminar la rendicion?")) {
                document.forms[1].action = document.forms[1].action + "?codigo="
                        + idRendicion;
                document.getElementById("RendicionForm").submit();
            }
            return false;
        }

        $(document).ready(function () {
            $('#checkRendiciones').show();

            $.validator.addMethod("fechaHasta", function (value, element) {
                var startDate = $('.fechaDesde').val();
                return Date.parse(startDate) <= Date.parse(value) || value == "";
            }, "* End date must be after start date");
            $('#filtroRendiciones').validate();
        });
    </script>
</body>