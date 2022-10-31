<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>
<head>
    <link rel="stylesheet" type="text/css" href="./css/validation.css">
    <script type="text/javascript" src="./js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="./js/validarAprobacion.js"></script>

    <!--<logic:equal value="ok" name="trxOk">-->
        <!--<script>-->
        <!--alert("accion realizada con exito");-->
        <!--</script>-->
        <!--</logic:equal>-->
        <style type="text/css">
            #paginado{
                color:black!important;
            }
        </style>

    </head>
    <body >
    <% 
            Usuario userSession = (Usuario) request.getSession().getAttribute("usuario");
            if (userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL ||
                    userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL_LESS_PARAMS_CIERRE ||
                    userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL_LESS_CIERRE ||
                    userSession.getTipoPerfil() == TipoPerfil.VIEW_ALL_LESS_PARAMS ||
                    userSession.getTipoPerfil() == TipoPerfil.VIEW_APROBACION) { 
    %>
    <logic:present name="messageModifTCJP">
        <div>
            <% String message = (String) request.getAttribute("messageModifTCJP"); 
            if(message.contains("ERROR")){
            %>
            <div id="messageErr"
                 style="color: red; font-weight: bold; text-align: center; width: 100%; font-size: 14">
                <%= message %></div>
            <%} else if(message.contains("OK")){ %><div id="messageOk"
                 style="color: green; font-weight: bold; text-align: center; width: 100%; font-size: 14">
                <%= message %></div>
            <%} else {%><div id="messageAviso"
                 style="color: orange; font-weight: bold; text-align: center; width: 100%; font-size: 14">
                AVISO: <%= message %></div>
                <%}%>
        </div></logic:present>	
    <logic:present name="messageConsulta">
        <div>
            <% String message = (String) request.getAttribute("messageConsulta"); 
            if(message.contains("ERROR")){
            %>
            <div id="messageErr"
                 style="color: red; font-weight: bold; text-align: center; width: 100%; font-size: 14">
                <%= message %></div>
            <%} else if(message.contains("OK")){ %><div id="messageOk"
                 style="color: green; font-weight: bold; text-align: center; width: 100%; font-size: 14">
                <%= message %></div>
            <%} else {%><div id="messageAviso"
                 style="color: orange; font-weight: bold; text-align: center; width: 100%; font-size: 14">
                AVISO: <%= message %></div>
                <%}%>
        </div>
    </logic:present>
    <logic:notPresent name="messageConsulta">
        <table>
            <thead>
                <tr>
                    <th colspan="5">Filtro rendiciones</th>
                </tr>
            </thead>
            <html:form action="FiltrarAprobacion" styleId="filtroRendiciones">
                <tbody class="listadoRendiciones">
                    <tr>
                        <td style="width: 20%;">
                            ID
                            <html:text property="idRendicion" style="width:60%;" maxlength="16" onkeypress="return numericOnly(event);"/>
                        </td>
                        <td style="width: 30%;">
                            Usuario
                            <html:text property="user" style="width: 60%" maxlength="8"/>
                        </td>
                        <td style="width: 50%;">
                            Motivo
                            <html:select property="motivo" styleId="motivo" style="width:70%;" onchange="ayudaMotivo();">
                                <html:option value="">Todos</html:option>
                                <html:options collection="ComboMotivo" property="id" labelProperty="descripcion" />
                            </html:select>
                            <a id="ayudaMotivo" target="_blank">
                                <img width="15px" src='./images/iconos/question-mark-2-48.png' style="display:none" alt='Ayuda' title="Ayuda"
                                     border='0' style="margin-left: 5px;" align="top" id="imagen" />
                            </a>
                        </td>
                        <td align="center" style="text-alignt: center; width: 26%">
                            <html:submit styleClass="buttonFilter" value="Filtrar" />
                        </td>
                    </tr>
                </tbody>
                <html:hidden property="estado" styleId="estadoFiltro"/>
            </html:form>

        </table>
        <div
            style="display: none; text-align: center; color: green; font-size: small;"
            id="Aguarde">Aguarde mientras se aprueba la rendicion...</div>
        <div id="paginado">
            <html:form action="aprobacionesSend" styleId="AprobarRendicionForm">
                <logic:present name="cantRendiciones">
                    <label style="font-weight:bold;margin:10px;position:absolute;">
                        Rendiciones pendientes de aprobaci&oacute;n: <bean:write name="cantRendiciones" />
                    </label>
                </logic:present>

                <a onclick="aprobarRendiciones();this.disabled = true;"
                   class="buttonAprobar" style="float:right;margin-right:1.5%; color: black;margin-bottom:10px;margin-top:10px;" id="Aprobar">
                    Aprobar 
                </a>

                <display:table uid="row" name="Rendicion" requestURI="FiltrarAprobacion.do"
                               id="Rendiciontbl"
                               decorator="com.sa.decorator.AprobacionesTableDecorator" pagesize="20"
                               style="color:black;margin-top:20px;" export="true">

                    <display:column property="id" title="ID" style="width:5%"
                                    sortable="true" />
                    <display:column property="nombreUsuarioRendicion" title="Usuario" style="width:7.5%"
                                    sortable="true" maxLength="30" />
                    <display:column property="usuarioRendicion" title="Usuario" style="width:5%"
                                    sortable="true" media="none" />
                    <display:column property="descripcionMotivo" title="Motivo" style="width:10%"
                                    sortable="true" maxLength="30" />
                    <display:column property="idMotivo" title="Motivo" style="width:10%"
                                    sortable="true" media="none" />
                    <display:column property="estado" title="Estado" style="width:5%"
                                    sortable="true" media="none" />
                    <display:column property="descripcionEstado" title="Estado" style="width:11%"
                                    sortable="true" maxLength="30"/>
                    <display:column property="descripcion" title="Descripcion"
                                    style="width:10%" maxLength="50"/>
                    <display:column property="fechaDesde" format="{0,date,dd/MM/yyyy}"
                                    sortable="true" title="Desde" style="width:2%" />
                    <display:column property="fechaHasta" format="{0,date,dd/MM/yyyy}"
                                    sortable="true" title="Hasta" style="width:2%" />
                    <display:column property="importe" title="Importe" style="width:6%;"
                                    sortable="true" />
                    <%-- 			<display:column property="usuarioRendicion" title="Usuario" --%>
                    <%-- 				style="width:5%;" sortable="true" /> --%>
                    <display:column property="opciones" title="Detalle" style="width:1%;text-align:center;white-space:nowrap;" media="html"></display:column>
                    <display:column property="caratula" title="Carátula" style="width:1%; text-align:center;" media="false"></display:column>
                    <display:column style="width:1%" property="cupones" title="Asig." sortable="false" sortName="asignada" media=" html"></display:column>

                    <display:setProperty name="export.csv.filename" value="ListadoAprobacion.csv"/>
                    <display:setProperty name="export.excel.filename" value="ListadoAprobacion.xls"/>
                </display:table>
            </html:form>
        </div>
        <div id="overlay"></div>
        <script>
            function checkRendicion(formValue) {
                $('#formAprobacion').val(formValue);

            }
        </script>

        <script type="text/javascript">
            $(document).ready(function () {
                ayudaMotivo();
                $("#Aprobar").click(function () {

                    document.getElementById("AprobarRendicionForm").reset();

                });
            });


            function checkRendiciones(check) {
                var $checkboxes = $('#Rendiciontbl').find('input[id=checkAprobacion]');

                for (var i = 0; i < $checkboxes.size(); i++) {

                    if ($checkboxes.get(i).checked) {
                        seleccionado = false;
                        break;
                    }
                }

            }
            function aprobarRendiciones() {

                var query = "";
                var glg = $('#estadoFiltro').val();
                var $checkboxes = $('#Rendiciontbl').find('input[id=checkAprobacion]');

                var checkeado = false;

                for (var i = 0; i < $checkboxes.size(); i++) {
                    if ($checkboxes.get(i).checked) {

                        query += "idRendicion"
                                + i
                                + "="
                                + jQuery(
                                        jQuery($checkboxes[i]).parent().prevAll()
                                        .get(8)).text().trim() + "&";

                        query += "seleccionado" + i + "=true&";
                        checkeado = true;
                    }

                }

                if (checkeado) {
                    iz = (screen.height / 2) - (300 / 2);
                    de = (screen.width / 2) - (500 / 2);
                    $("#Aguarde").show();
                    $("#overlay").show();

                    var ventana = window
                            .location.replace(
                                    "aprobacionesSend.do?glg=" + glg + "&" + query,
                                    "",
                                    "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=500, height=300, left="
                                    + de + ", top=" + iz);
                    document.getElementById("AprobarRendicionForm").reset();

                } else {
                    alert("Debe seleccionar al menos una rendicion");
                }

            }
        </script>
        <script>
            function showThuban(Link, rend) {

                window.open(Link + rend);
            }
        </script>
        <script>
            function showJournal(rend) {
                popUpObj = window.open("ShowJournal.do" + "?codigo="
                        + rend, "ModalPopUp", "toolbar=no,"
                        + "scrollbars=no," + "location=no," + "statusbar=no,"
                        + "menubar=no," + "resizable=0," + "width=800,"
                        + "height=525," + "left = 500," + "right = 500,"
                        + "top=250," + "bottom = 250");
                popUpObj.focus();
            }
        </script>
    </logic:notPresent>
    <script type="text/javascript">

        jQuery(document).ready(function () {

            $('#checkAprobacion').show();
        });
    </script>
    <% } else { %>
    No tiene permisos para ver esta p&aacute;gina
    <% } %>
</body>