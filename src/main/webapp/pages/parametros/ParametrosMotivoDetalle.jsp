<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>

<bean:define id="ParametrosMotivoForm" name="ParametrosMotivoForm" scope="session" toScope="request" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

        <link rel="stylesheet" type="text/css" href="./css/validation.css">
        <link rel="stylesheet" type="text/css" href="./css/Parametros.css">
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

        <html:form action="saveMotivo" styleId="parametrosMotivoForm">
            <table>
                <tbody>
                    <tr>
                        <td style="font-weight: bold;">Motivo</td>
                        <td>
                            <html:text property="codigo" styleId="codigo" style="width:96px;" maxlength="4" onkeypress="return numericOnly(event);"/>
                        </td>
                        <td style="padding-left:30px; font-weight: bold;">
                            Descripci&oacute;n</td>
                        <td colspan="3">
                            <html:text property="descripcion" style="width:286px;" maxlength="50"/>
                        </td>
                        <td style="font-weight: bold;">Estado</td>
                        <td>
                            <html:select property="estado" styleId="estado" style="width:100px; color:black;">
                                <html:option value=""></html:option>
                                <html:option value="I">Inactivo</html:option>
                                <html:option value="A">Activo</html:option>
                            </html:select>
                        </td>
                    </tr>
                    <tr>
                        <td style="font-weight: bold;">Superior</td>
                        <td>
                            <html:select property="codSup" style="width:100px; color:black;">
                                <html:option value=""></html:option>
                                <html:option value="PSUP">PSUP</html:option>
                            </html:select>
                        </td>
                        <td style="padding-left:30px; font-weight: bold;">Firma</td>
                        <td>
                            <html:select property="codFirma" style="width:100px; color:black;">
                                <html:option value=""></html:option>
                                <html:option value="PFIRM">PFIRM</html:option>
                            </html:select>
                        </td>
                        <td style="font-weight: bold;">GLG</td>
                        <td>
                            <html:text property="idGlg" style="width:96px;" maxlength="2" onkeypress="return numericOnly(event);"/>
                        </td>
                        <td style="font-weight: bold;">Ctrl. GLG</td>
                        <td>
                            <html:select property="codAprobacionGlg" style="width:100px;">
                                <html:option value=""></html:option>
                                <html:option value="PGLG">PGLG</html:option>
                            </html:select>
                        </td>
                    </tr>
                    <tr>
                        <td style="font-weight: bold;">Nvl. Ingreso</td>
                        <td>
                            <html:select property="idNivCarga" style="width:100px; color:black;">
                                <html:option value=""></html:option>
                                <html:option value="01">01</html:option>
                                <html:option value="02">02</html:option>
                                <html:option value="03">03</html:option>
                                <html:option value="04">04</html:option>
                                <html:option value="05">05</html:option>
                                <html:option value="06">06</html:option>
                                <html:option value="07">07</html:option>
                                <html:option value="08">08</html:option>
                                <html:option value="09">09</html:option>
                                <html:option value="10">10</html:option>
                            </html:select>
                        </td>
                        <td style="padding-left:30px; font-weight: bold;">Nvl. Firma</td>
                        <td>
                            <html:select property="idNivAutoriz" style="width:100px; color:black;">
                                <html:option value=""></html:option>
                                <html:option value="01">01</html:option>
                                <html:option value="02">02</html:option>
                                <html:option value="03">03</html:option>
                                <html:option value="04">04</html:option>
                                <html:option value="05">05</html:option>
                                <html:option value="06">06</html:option>
                                <html:option value="07">07</html:option>
                                <html:option value="08">08</html:option>
                                <html:option value="09">09</html:option>
                                <html:option value="10">10</html:option>
                            </html:select>
                        </td>
                        <td style="font-weight: bold;">Incl/Excl</td>
                        <td>
                            <html:select property="maInclExcl" style="width:100px; color:black;">
                                <html:option value=""></html:option>
                                <html:option value="I">Incl</html:option>
                                <html:option value="E">Excl</html:option>
                            </html:select>
                        </td>
                        <td style="font-weight: bold;">C. Costos</td>
                        <td>
                            <html:text property="idCentroCostos" style="width:96px;" maxlength="4" onkeypress="return numericOnly(event);"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="font-weight: bold;">Desde</td>
                        <td>
                            <html:text property="fechaDesde" styleId="fechaDesde" size="8" style="width:96px; color:black;" styleClass="fechaDDMMYY"/>
                            <img id="imageCal1" src='./images/Calendar.png' border='0' 
                                 style="float:left;position:absolute;cursor:pointer;margin-left:5px;">
                        </td>
                        <td style="padding-left:30px; font-weight: bold;">Hasta</td>
                        <td colspan="2">
                            <html:text property="fechaHasta" styleId="fechaHasta" size="8" style="width:96px; color:black;" styleClass="fechaDDMMYY"/>
                            <img id="imageCal2" src='./images/Calendar.png' border='0' 
                                 style="float:left;position:absolute;cursor:pointer;margin-left:5px;">
                        </td>
                        <td colspan="2">
                            <div class="ck-button first"><label><html:checkbox value="O" property="oscar.o"/><span>O</span></label></div>
                            <div class="ck-button"><label><html:checkbox value="S" property="oscar.s"/><span>S</span></label></div>
                            <div class="ck-button"><label><html:checkbox value="C" property="oscar.c"/><span>C</span></label></div>
                            <div class="ck-button"><label><html:checkbox value="A" property="oscar.a"/><span>A</span></label></div>
                            <div class="ck-button last"><label><html:checkbox value="R" property="oscar.r"/><span>R</span></label></div>
                                        <html:hidden styleId="oscarO" property="oscar.o"/>
                                        <html:hidden styleId="oscarS" property="oscar.s"/>
                                        <html:hidden styleId="oscarC" property="oscar.c"/>
                                        <html:hidden styleId="oscarA" property="oscar.a"/>
                                        <html:hidden styleId="oscarR" property="oscar.r"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="1"></td>
                        <td colspan="7">
                            <div id="errorFechas" style="color:red;"></div>
                        </td>
                    </tr>
                    <tr>
                        <td style="font-weight: bold;">Oper. Especial</td>
                        <td>
                            <html:text property="idOperEspe" style="width:96px;" maxlength="5"/>
                        </td>
                        <td style="padding-left:30px; font-weight: bold;">Medida Cant. Días</td>
                        <td>
                            <html:text property="meDiasInterv" style="width:96px;" maxlength="9" onkeypress="return numericOnly(event);"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="font-weight:bold;vertical-align:top;padding-top:6px;">Centros Costo</td>
                        <td colspan="7" id="tdCentrosCosto" style="white-space:normal;padding-right:90px;height:29px;width:750px;vertical-align:top;">
                            <logic:iterate name="ParametrosMotivoForm" property="centrosCosto" id="centrosCostoI" indexId="i">
                                <%String cc="centrosCostoI[" + i + "]"; %>
                                <%String ccId="centrosCosto_" + i; %>
                                <html:text styleId="<%=ccId%>" property="<%=cc%>" maxlength="4" style="width:50px;"
                                           onkeypress="return numericOnly(event);"/>
                                <a href="#" onclick="borrarCentroCosto(<%=i%>);" name="d_<%=cc%>">
                                    <img src="./images/iconos/borrar.png" alt="Eliminar" title="Eliminar" style="width:10px;"/>
                                </a>
                            </logic:iterate>
                            <a id="addCC" href="#" onclick="agregarCentroCosto();">
                                <img src="./images/iconos/add.png" alt="Agregar" title="Agregar" style="width:10px;margin-top:6px;"/>
                            </a>
                            <input type="hidden" name="centrosCosto"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="1"></td>
                        <td colspan="7">
                            <div id="errorCentrosCosto" style="color:red;"></div>
                        </td>
                    </tr>
                    <tr>
                        <td style="font-weight: bold;">Aviso</td>
                        <td colspan="8">
                            <html:textarea styleId="txAviso" property="txAviso" style="width:98%;"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="8" style="text-align:right;padding-right:10px;">
                            <logic:equal value="alta" name="ParametrosMotivoForm" property="accion">
                                <html:submit styleClass="buttonSave" value="Guardar"/>
                            </logic:equal>
                            <logic:equal value="modificacion" name="ParametrosMotivoForm" property="accion">
                                <html:submit styleClass="buttonSave" value="Guardar"/>
                            </logic:equal>
                            <logic:equal value="baja" name="ParametrosMotivoForm" property="accion">
                                <html:button property="" styleClass="buttonCancel" onclick="confirmEliminarMotivo()" value="Eliminar"/>
                            </logic:equal>
                            <a href="parametrosMotivoFiltro.do">
                                <input type="button" class="buttonCancel" value="Volver"/>
                            </a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </html:form>

        <script type="text/javascript" src="./js/parametrosMotivoDetalle.js"></script>

        <logic:equal value="alta" name="ParametrosMotivoForm" property="accion">
            <script>
                                $(document).ready(function () {
                                    $('#estado').attr('disabled', 'disabled');
                                });
            </script>
        </logic:equal>

        <logic:equal value="modificacion" name="ParametrosMotivoForm" property="accion">
            <script>
                $(document).ready(function () {
                    $('#codigo').attr('readonly', true);
                });
            </script>
        </logic:equal>

        <logic:equal value="baja" name="ParametrosMotivoForm" property="accion">
            <script>
                $(document).ready(function () {
                    $('input').attr('readonly', true);
                    $('textarea').attr('readonly', true);
                    $('select').attr('disabled', 'disabled');
                    $('#fechaDesde').attr('disabled', 'disabled');
                    $('#fechaHasta').attr('disabled', 'disabled');
                    $(".ck-button").attr('disabled', 'disabled');
                    $("[name^='oscar']").attr('disabled', 'disabled');
                    $("[name^='d_centrosCostoI']").hide();
                    $("#addCC").hide();
                    $("#imageCal1").hide();
                    $("#imageCal2").hide();
                });
            </script>
        </logic:equal>
    </body>
</html>