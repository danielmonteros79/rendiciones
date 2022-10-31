<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>
<bean:define id="ParametrosExceptuadosForm" name="ParametrosExceptuadosForm" scope="session" toScope="request" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

        <link rel="stylesheet" type="text/css" href="./css/validation.css">
        <style>td {
                white-space:nowrap;
                text-align:left;
            }</style>
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
        <html:form  action="saveExcepcion" styleId="parametrosExceptuadosForm">
            <table>
                <tbody>
                    <tr>
                        <td width="1%" class="fieldTable">
                            Motivo
                            <html:radio property="motivoUsuario" value="motivo" styleId="motivo"/>
                            Usuario
                            <html:radio property="motivoUsuario" value="usuario"  styleId="usuario"/>
                        </td>
                        <td width="23px" class="fieldTable">Hasta</td>
                        <td width="1px">
                            <html:text property="hasta" styleId="fechaHasta"  size="8" style="width:96px; color:black;" styleClass="fechaDDMMYY" />
                            <img id="imageCal1" src='./images/Calendar.png' border='0' style="float:left;position:absolute;cursor:pointer;">

                            <span style="margin-left:70px;font-weight:bold;">Desde</span>
                            <html:text property="desde" styleId="fechaDesde"  size="8" style="width:96px; color:black;" styleClass="fechaDDMMYY"/>
                            <img id="imageCal2" src='./images/Calendar.png' border='0' style="float:left;position:absolute;cursor:pointer;">
                            <div id="errorFechas" style="color:red;"></div>
                        </td>
                        <td width="15px"></td>
                        <td width="23px"></td>
                        <td width="1px">
                        </td>
                        <td width="15px"> </td>
                        <td class="fieldTable" width="1%">Estado</td>
                        <td>
                            <html:select property="estado" styleId="estado" style="color:black;">
                                <html:option value="I">Inactivo</html:option>
                                <html:option value="A">Activo</html:option>
                            </html:select>
                        </td>
                    </tr>
                    <tr>
                        <td width="1%" class="fieldTable">
                            C&oacute;digo
                            <html:text property="desMotivo" styleId="desMotivo" onchange="checkCodigo()"/>
                        </td>
                        <td colspan="2">	
                            <html:text property="descripcionCodigo" styleId="descripcionCodigo" style="width:300px; font-weight: bold;" readonly="true" maxlength="75"/>

                        </td>

                    </tr>

                    <tr>
                        <td colspan="9" style="text-align:right;padding-right:10px;">
                            <logic:equal value="baja" name="ParametrosExceptuadosForm" property="accion">
                                <html:button property="" styleClass="buttonCancel" onclick="confirmEliminarExceptuado()" value="Eliminar"/>
                            </logic:equal>
                            <logic:notEqual value="baja" name="ParametrosExceptuadosForm" property="accion">
                                <html:submit styleClass="buttonSave" value="Guardar"/>
                            </logic:notEqual>	
                            <a href="parametrosExceptuadosFiltro.do">
                                <input type="button" class="buttonCancel" value="Volver"/>
                            </a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </html:form>
        <logic:equal value="alta" name="ParametrosExceptuadosForm" property="accion">
            <script>
                $(document).ready(function () {
                    $('#estado').attr('disabled', 'disabled');
                });
            </script>
        </logic:equal>
        <logic:equal value="modificacion" name="ParametrosExceptuadosForm" property="accion">
            <script>
                $(document).ready(function () {
                    $('#desMotivo').attr('readonly', true);
                    $('#motivo').attr('disabled', 'disabled');
                    $('#usuario').attr('disabled', 'disabled');
                });
            </script>
        </logic:equal>
        <logic:equal value="baja" name="ParametrosExceptuadosForm" property="accion">
            <script>
                $(document).ready(function () {
                    $('#desMotivo').attr('readonly', true);
                    $('#motivo').attr('disabled', 'disabled');
                    $('#usuario').attr('disabled', 'disabled');
                    $('#estado').attr('disabled', 'disabled');
                    $('#fechaDesde').attr('readonly', true);
                    $('#fechaHasta').attr('readonly', true);
                    $('#fechaDesde').attr('disabled', 'disabled');
                    $('#fechaHasta').attr('disabled', 'disabled');
                    $("#imageCal1").hide();
                    $("#imageCal2").hide();
                });
            </script>
        </logic:equal>
        <script type="text/javascript" src="./js/parametrosExceptuadosDetalle.js"></script>
    </body>
</html>