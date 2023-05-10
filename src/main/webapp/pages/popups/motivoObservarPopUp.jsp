<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.Locale"%>
<%@page import="com.sa.entities.Usuario"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Motivo de observacion</title>
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
        <link rel='stylesheet' type='text/css'
              href='./css/displaytag.css' />
        <link rel="stylesheet" type='text/css'
              href="./css/jquery-ui.structure.css" />
        <link rel="stylesheet" type='text/css' href="./css/jquery-ui.theme.css" />
        <link rel="stylesheet" type='text/css' href="./css/buttons.css" />


        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/jquery-ui.js"></script>
        <script type="text/javascript" src="js/jquery.ui.datepicker-es.js"></script>
        <script type="text/javascript" src="js/datepicker-settings.js"></script>
        <script type="text/javascript" src="js/jquery.validate.min.js"></script>
        <script type="text/javascript" src="./static/js/popUpsOpen.js"></script>
        <script type="text/javascript" src="./static/js/onClose.js"></script>
        <script type="text/javascript" src="./static/js/validarRechazo.js"></script>
        <style>
            body {
                background-color: #c2d5f1;
            }
        </style>
    </head>
    <body>
        <logic:present name="messageModifTCJP">
            <% String message = (String) request.getAttribute("messageModifTCJP"); 
            if(message.contains("ERROR")){
            %>
            <div id="messageErr" style="color: red; font-weight: bold; text-align: center; width: 100%; font-size: 14">
                <%= message %>
            </div>
            <%} else { %>
            <div id="messageOk" style="color: green; font-weight: bold; text-align: center; width: 100%; font-size: 14">
                <%= message %>
            </div>
            <%} %>
            <script>
                function salir() {
                    window.opener.location.href = 'aprobacion.do?glg=' + $('#glg').val();
                }
            </script>
            <html:form action="saveMotivoObservar" styleId="RechazarRendicionForm">
                <html:hidden property="glg" styleId="glg"/>
            </html:form>
            <input type="button" value="Salir" onClick="salir()" class="buttonCancel" style="float: right; margin-right: 1%;" />
        </logic:present>
        <logic:notPresent name="messageModifTCJP">
            <html:form action="saveMotivoObservar" styleId="RechazarRendicionForm"
                       method="post" onsubmit="redirect(window.opener);">
                <html:hidden property="id" styleId="idRendicion" />
                <html:hidden property="glg" styleId="glg" />

                <table>
                    <tbody>
                        <tr>
                            <td style="width:150px;"> 
                                <label for="cmboMotivo" style="font-weight:bold">Motivo de observaci&oacute;n:</label> 
                            </td>
                            <td>
                                <html:select property="cmboMotivo" styleId="cmboMotivo" style="width:285px; color:black;">
                                    <html:options collection="ComboMotivo" property="id" labelProperty="descripcion" />
                                </html:select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="motivoRechazo" style="font-weight:bold;">Descripci&oacute;n:</label>
                            </td>
                            <td>
                                <html:textarea property="motivoRechazo" styleId="motivoRechazo"style="width:280px;height:140px;" />
                            </td>
                        </tr>	
                    </tbody>
                </table>

                <div style="text-align:center;">			
                    <html:link href="#" styleClass="buttonCancel" onclick="guardar()">
                        Guardar
                    </html:link>
                    <input type="button" value="Salir" onClick="window.close();" class="buttonCancel"/> 	
                </div>	
            </html:form>

            <script>
                document.getElementById('motivoRechazo').setAttribute('maxlength', '113');

                function guardar() {
                    document.forms[0].action = document.forms[0].action + location.search;
                    document.getElementById("RechazarRendicionForm").submit();

                    return false;
                }
            </script>
        </logic:notPresent>
    </body>
</html>