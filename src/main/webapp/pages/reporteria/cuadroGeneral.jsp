<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <link rel="stylesheet" type="text/css" href="./css/Parametros.css">
    <link rel="stylesheet" type="text/css" href="./css/validation.css">

    <style>
        td {
            white-space:nowrap;
            text-align:left;
        }
        label.error {
            position: absolute;
        }
    </style>
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
    <html:form action="cuadroGeneralFiltro" styleId="cuadroGeneralFiltro">
        <table>
            <thead>
                <tr>
                    <th colspan="8">Filtro Cuadro General</th>
                </tr>
            </thead>
            <tbody class="filtro" style="font-size: small; font-weight: bold;">
                <tr>
                    <td colspan="4">Usuario:
                        <html:text styleClass="green" property="nombreUsuario" style="width: 280px;" readonly="true"/>
                    </td>
                    <td colspan="4">C.Costos:
                        <html:text styleClass="green" property="costos" readonly="true" />
                    </td>
                </tr>
                <tr>
                    <td>Opci&oacute;n:</td>
                    <td>
                        <html:select property="opcion" styleId="opcion" onchange="selectOpcion()">
                            <html:option value="01">Estado actual</html:option>
                            <html:option value="02">Estado final</html:option>
                            <html:option value="03">Fecha de carga</html:option>
                        </html:select>
                    </td>
                    <td>GLG:</td>
                    <td>
                        <html:select property="codGlg" styleId="codGlg" style="width:100px;">
                            <html:options collection="ComboGlg" property="id" labelProperty="descripcion"/>
                        </html:select>
                    </td>
                    <td>Motivo:</td>
                    <td colspan="3">
                        <html:select property="codMotivo" styleId="codMotivo" style="width:320px;">
                            <html:option value="">TODOS</html:option>
                            <html:options collection="ComboMotivo" property="id" labelProperty="descripcion"/>
                        </html:select>
                    </td>
                </tr>
                <tr>
                    <td>Desde:</td>
                    <td>
                        <html:text property="fechaDesde" styleId="fechaDesde" size="8" 
                                   style="width:80px; color:black;" styleClass="fechaDDMMYY"/>
                        <img id="imageCal1" src='./images/Calendar.png' border='0' 
                             style="float:left;position:absolute;cursor:pointer;margin-left:5px;">
                    </td>
                    <td>Hasta:</td>
                    <td style="padding-right:30px;">
                        <html:text property="fechaHasta" styleId="fechaHasta" size="8" 
                                   style="width:80px; color:black;" styleClass="fechaDDMMYY"/>
                        <img id="imageCal2" src='./images/Calendar.png' border='0' 
                             style="float:left;position:absolute;cursor:pointer;margin-left:5px;">
                    </td>
                    <td>Monto Desde:</td>
                    <td>
                        <html:text property="montoDesde" styleId="montoDesde" style="width:110px;" 
                                   maxlength="16" onkeypress="return keyPressMonto(event, 'montoDesde');"/>
                    </td>
                    <td>Monto Hasta:</td>
                    <td>
                        <html:text property="montoHasta" styleId="montoHasta" style="width:110px;" 
                                   maxlength="16" onkeypress="return keyPressMonto(event, 'montoHasta');"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td colspan="3">
                        <div id="errorFechas" style="color:red;"></div>
                    </td>
                    <td></td>
                    <td colspan="3">
                        <div id="errorMonto" style="color:red;"></div>
                    </td>
                </tr>
                <tr>
                    <td>Usuario:</td>
                    <td>
                        <html:text property="usuario" styleId="usuario" style="width:110px;text-transform:uppercase;" maxlength="8"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="13" style="text-align:right;padding-right:10px;">
                        <html:button styleClass="buttonFilter" value="Filtrar" property="" onclick="filtrar()"/>
                        <html:button styleClass="buttonClear" value="Limpiar" property="" onclick="resetForm()" />
                    </td>
                </tr>
            </tbody>
        </table>
        <html:hidden styleId="codEstado" property="codEstado"/>
    </html:form>

    <logic:equal value="t" name="Tabla">
        <div id="paginacion" style="margin-top: 43px;">
            <display:table uid="row" name="Rendicion"
                           requestURI="cuadroGeneralFiltro.do" id="RendicionesTable" excludedParams="username password"
                           decorator="com.sa.decorator.CuadroGeneralTableDecorator" pagesize="15"
                           style="width:50%; margin:auto;" export="true">
                <display:column property="estado" title="Estado" style="width:4%;white-space:nowrap" media="html csv excel" />
                <display:column property="cantRend" title="Cantidad de rendiciones" style="width:16%;text-align:right;" media="html csv excel" />
                <display:column property="montoTotal" title="Monto total (*)" style="width:20%;text-align:right;" maxLength="55" media="html csv excel" />
                <display:column property="opciones" title="Consultar detalle" style="width:3%;text-align:center;" media="html" />

                <display:setProperty name="paging.banner.all_items_found" value="" />
                <display:setProperty name="paging.banner.onepage" value="" />

                <display:setProperty name="export.csv.filename" value="CuadroGeneral.csv"/>
                <display:setProperty name="export.excel.filename" value="CuadroGeneral.xls"/>
            </display:table>
        </div>

        <div style="color:#FF0000;">
            <br/><br/>
            <p>(*) importe rendici&oacute;n estimado</p>
        </div>
    </logic:equal>

    <script type="text/javascript" src="./js/cuadroGeneral.js"></script>
</body>
</html>