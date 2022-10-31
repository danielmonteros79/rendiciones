<html>
    <%@ page language="java" contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8"%>
    <%@page import="org.apache.struts.action.ActionForm"%>
    <%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
    <%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
    <%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
    <%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
    <%@page import="java.util.*"%>



    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./css/rendicionDetalle.css">
        <link rel="stylesheet" type="text/css" href="./css/validation.css">
        <link rel="stylesheet" type="text/css" href="./css/detalleGastos.css">

    </head>
    <style type="text/css">
        .selected {
            background-color: #1f497d;
            color: #FFF;
        }

        td {
            cursor: pointer;
        }
    </style>
    <body>
        <input type="hidden" id="idRendicion"
               value='<bean:write name="idRendicion"/>' />
        <input type="hidden" id="motivoRendicion"
               value='<bean:write name="codMotivo"/>' />
        <input type="hidden" id="usuarioRendicion"
               value='<bean:write name="usuarioRendicion"/>' />
        <fieldset>
            <table style="height: 70%;">

                <tbody>
                    <tr>
                        <td align="left" colspan="8">ID-Rendici&oacute;n: <label
                                class="green" id="idRendicionL"><bean:write
                                    name="idRendicion" /> </label>

                        </td>
                    </tr>


                    <tr>
                        <td align="left" colspan="4"><label for="motivo">Motivo:</label>
                            <label class="green"> <bean:write name="descripcionMotivo" />
                            </label></td>
                    </tr>
                    <tr></tr>
                </tbody>

            </table>
        </fieldset>
        <%
                int i = 1;
        %>
        <table class="CambioFondo"
               style="margin-top: 10px; width: 97% !important;">
            <thead>

                <tr>
                    <td align="center" style="font-weight: bold !important;">Gasto</td>
                    <td align="center" style="font-weight: bold !important;">Descripción</td>
                    <td align="center" style="font-weight: bold !important;" width="5%">C.Costo</td>
                    <td align="center" style="font-weight: bold !important;">Importe</td>
                    <td align="center" style="font-weight: bold !important;" width="16%">Saldo</td>
                </tr>
            </thead>
            <tbody>
                <logic:iterate id="gasto" name="Gastos" type="com.sa.entities.Gastos">
                    <tr id="idGasto<bean:write name="gasto" property="idGasto" />">

                        <td align="center"><bean:write name="gasto" property="idGasto" /></td>
                        <td align="center" style="display: none;"><bean:write name="gasto" property="nroGasto" /></td>
                        <td align="center" id="descripcionGasto"><bean:write
                                name="gasto" property="descGasto" /></td>
                        <td align="center"><bean:write name="gasto"
                                    property="centroCostoGasto" /></td>
                        <td align="center"><bean:write name="gasto" property="monto" /></td>
                        <td align="center"><input
                                style="font-size: 11px; font-weight: bold; color: red; right: 10px"
                                disabled="disabled" type="text"
                                id="saldoTable<bean:write name="gasto" property="idGasto" />"
                                size="16%" maxlength="16" /></td>
                    </tr>
                </logic:iterate>
            </tbody>
        </table>

        <input type="hidden" id="montoOriginal" />
        <input type="hidden" id="idGastoOriginal" />
        <input type="hidden" id="saldoPendiente" />
        <input type="hidden" id="centroCostoOriginal" />

        <div id="datosGastoSelected">
            <fieldset>
                <table style="height: 80%;">

                    <tbody>
                        <tr>
                            <td align="left" colspan="8"><select id="comboGasto"></select>
                                Monto: <input type="text" maxlength="16" size="24%"
                                              id="montoModificado" onkeypress="return keyPressMonto(event);" />
                                C.Costo: <input type="text" maxlength="4" size="1%"
                                                id="centroCostoModificado"
                                                onkeypress="return keyPressNumber(event);" /></td>
                            <td align="right" colspan="2"><html:link href="#"
                                       styleClass="buttonCancel" style="float:right;margin-right:1.5%;"
                                       onclick="anularGastos()">
                                    Anular
                                </html:link> <html:link href="#" styleClass="buttonAprobar"
                                                        style="float:right; margin-right:1.5%;"
                                                        onclick="guardarModifGasto()" styleId="buttonAprobar">Guardar</html:link></td>
                        </tr>

                        <tr>
                            <td align="left" colspan="8"></td>
                        </tr>
                    </tbody>

                </table>
            </fieldset>
        </div>

        <div id="gastosDistribuidosTable">

            <table class="gastosDistribuidosClass"
                   style="margin-top: 10px; width: 97% !important;">
                <thead>
                    <tr>
                        <td colspan="4" align="center" style="font-weight: bold !important; font-size: 14px; background-color: #1f497d;">Redistribuciones realizadas</td>
                    </tr>
                    <tr>
                        <td align="center" style="font-weight: bold !important;">Cod.Gasto</td>
                        <td align="center" style="font-weight: bold !important;">Descripción</td>
                        <td align="center" style="font-weight: bold !important;" width="5%">C.Costo</td>
                        <td align="center" style="font-weight: bold !important;">Importe</td>

                    </tr>
                </thead>
                <tbody>

                </tbody>
                <tfoot>
                    <tr></tr>
                    <tr></tr>
                    <tr></tr>

                </tfoot>
            </table>
        </div>

        <div id="msgEspera">
            <span class="green" style="font-weight: bold;">Aguarde mientras
                se procesan los datos...</span>
        </div>
        <div id="distribucionTable">

            <table class="distribucionTableClass"
                   style="margin-top: 10px; width: 97% !important;">
                <thead>
                    <tr>
                        <td align="center" style="display:none; font-weight: bold !important;">Cod.Gasto</td>
                        <td align="center" style="font-weight: bold !important;">Descripción</td>
                        <td align="center" style="font-weight: bold !important;">Importe</td>
                        <td align="center" style="font-weight: bold !important;" width="5%">C.Costo</td>
                        <td align="center" style="font-weight: bold !important;" width="5%">Borrar</td>
                    </tr>
                </thead>
                <tbody>

                </tbody>
                <tfoot>
                    <tr></tr>
                    <tr></tr>
                    <tr></tr>
                    <tr></tr>
                    <tr>

                        <td align="right" colspan="2"><a href="#"
                                                         onclick="confirmarNuevosGastos();"> <input type="button"
                                                                       class="buttonSave" value="Confirmar" />
                            </a></td>
                    </tr>
                </tfoot>
            </table>
        </div>
        <table style="margin-top: 10px; width: 97% !important;">
            <tr>
                <td align="center" colspan="8"><a href="#" onclick="volver();">
                        <input type="button" class="buttonReplace" value="Volver" />
                    </a></td>

            </tr>
        </table>

    </body>
</html>
<script type="text/javascript" src="./js/distribucionGastos.js"></script>