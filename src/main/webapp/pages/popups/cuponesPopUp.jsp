<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>
<%@page import="com.sa.entities.*"%>


<head>
    <link rel="stylesheet" type="text/css" href="./css/buttons.css">
    <link rel="stylesheet" type="text/css" href="./css/validation.css">
    <link rel="stylesheet" type="text/css" href="./css/main.css">
    <link rel="stylesheet" type="text/css" href="./css/rendicionDetalle.css">
    <link rel="stylesheet" type="text/css" href="./css/jquery-ui.css">
    <link rel='stylesheet' type='text/css'
          href='./css/displaytag.css' />
    <link rel="stylesheet" type='text/css'
          href="./css/jquery-ui.structure.css" />
    <link rel="stylesheet" type='text/css' href="./css/jquery-ui.theme.css" />
    <link rel="stylesheet" type="text/css" href="./css/validation.css">


    <script type="text/javascript" src="./static/js/jquery.js"></script>
    <script type="text/javascript" src="./static/js/jquery-ui.js"></script>
    <script type="text/javascript" src="./static/js/jquery.ui.datepicker-es.js"></script>
    <script type="text/javascript" src="./static/js/datepicker-settings.js"></script>
    <script type="text/javascript" src="./static/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="./static/js/date-calculator.js"></script>
    <script type="text/javascript" src="./static/js/listadoRendiciones.js"></script>

    <script type="text/javascript" src="./static/js/validacionCupones.js"></script>

    <style>
        label {
            display: none;
            padding-left: 0;
        }

        body {
            background-color: #c2d5f1;
            overflow: none;
        }

        #paginado {
            color: black;
        }
    </style>
    <logic:equal value="f" name="tipoConsulta">
        <title>Asignar cupon</title>
    </logic:equal>
    <logic:notEqual value="f" name="tipoConsulta">
        <title>Cupon asignado al gasto</title>
    </logic:notEqual>
</head>
<body>
    <div>
        <logic:equal value="f" name="tipoConsulta">

            <h2 style="text-align: center;">Asignar cupon
                <a href='http://intranetprod.arg.igrupobbva/operativas/datos/gr_rendiciones_frec_ques.pdf'
                   id="BotonPreguntas" style="position: absolute; z-index: 999; top: 10px; right: 90px;"
                   target="_blank">
                    <img id="Add" src='./images/iconos/solutions-48.png'
                         title="Preguntas Frecuentes" border='0' width="34" height="34">
                </a>
            </h2>
        </logic:equal>
        <logic:notEqual value="f" name="tipoConsulta">
            <h2 style="text-align: center;">Cupon asignado al gasto</h2></logic:notEqual>
        </div>

        <input type="hidden" id="tipoConsulta"
               value='<bean:write name="tipoConsulta"/>' />
    <% List<Cupones> cupones = (List<Cupones>) request.getAttribute("Cupones");%>
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
    <logic:notPresent name="message">
        <div style="width: 100%; font-size: small; font-weight: bold;"><input
                type="hidden" id="idRendicion" value='<bean:write name="codigo"/>'>


            <html:form action="cuponesPopupSend" styleId="CuponesForm">
                <html:hidden property="idRendicion" styleId="idRendicionFrm" value="" />
                <html:hidden property="idGastoRend" styleId="idGastoRendFrm" />
                <html:hidden property="cupon" styleId="nroCupon" value="" />
                <html:hidden property="importeCupon" styleId="impCuponTj" value="" />
                <html:hidden property="nroTarjeta" styleId="nroTarjeta" value="" />
                <html:hidden property="cupDeb" styleId="cuponDeb" value="" />
                <html:hidden property="cupCred" styleId="cuponCred" value="" />
                <html:hidden property="descCupon" styleId="descCupon" value="" />
                <html:hidden property="moneda" styleId="monedaCupon" value="" />
                <html:hidden property="fechaD" styleId="fechaD" />
                <html:hidden property="fechaH" styleId="fechaH" />
                <html:hidden property="centroCostos" styleId="cCostos" />
                <html:hidden property="fechaPresentacion" styleId="fechaPresentacion" />
                <html:hidden property="codMotivo" styleId="codMotivo" />
                <html:hidden property="esAdelanto" styleId="esAdelanto" />

                <div id="messageValidate"
                     style="color: red; font-weight: bold; display: none; text-align: center; width: 100%; font-size: 14">
                    Debe seleccionar un cupon.</div>
                <div style="overflow-x: hidden;height:auto; overflow-y: auto; height: 320px; margin-right: 50px;">
                    <display:table uid="row" name="Cupones" requestURI="cuponesPopup.do"
                                   style="width:91%;margin-left: 10%;margin-right:10%;" id="cuponestbl"
                                   decorator="com.sa.decorator.CuponesDecorator">

                        <display:column property="nroTarjetaCliente" title="Tarjeta" style="width:10%; text-align:right;"/>
                        <display:column property="fechaPresentacionDate" format="{0,date,dd/MM/yyyy}" title="Fecha" style="width:7%;text-align:center;"/>
                        <display:column property="nroCupon" title="Cupon" style="width:5%; text-align:right;" />
                        <display:column property="establecimiento" title="Establecimiento" style="width:20%" />
                        <display:column property="liquidacionNeto" title="Monto" style="width:5%; text-align:right;" />
                        <logic:equal value="f" name="tipoConsulta">
                            <display:column property="montoUtilizado" title="Monto Utilizado" style="width:5%; text-align:right;" />
                        </logic:equal>
                        <display:column property="moneda" title="Moneda" style="width:5%; text-align:center;" />
                        <logic:equal value="f" name="tipoConsulta">
                            <display:column property="opciones" title="Opciones" style="width:5%;"/>
                        </logic:equal>
                    </display:table>
                </div>
                <logic:equal value="1" name="tipoClick">
                    <%  
				if(cupones.size() > 0 ){%>
                    <input type="button" class="buttonSave" value="Guardar"
                           id="buttonSave" onclick="saveCupon('1');"
                           style="float: right; margin-right: 10%;" />
                    <%} %>
                </logic:equal>
                <logic:equal value="2" name="tipoClick">
                    <%  
				if(cupones.size() > 0 ){%>
                    <input type="button" class="buttonSave" value="Guardar"
                           id="buttonSave"  onclick="saveCupon('2');"style="float: right; margin-right: 10%;" />
                    <%} %>
                </logic:equal>
            </html:form> <%if(cupones.size() > 0 ){%> <input type="button" value="Cancelar"
                   onClick=window.close();window.opener.reload();
                   id="buttonCancel"
                   class="buttonCancel" style="float: right; margin-right: 1%;" /> <%} %>

            <input type="button" value="Salir" onClick=
                   window.close();;;;;
                   id="buttonSalir" class="buttonCancel"
                   style="float: right; display: none; margin-right: 10%;" /></div>
        </logic:notPresent>

    <script>
        $(document).ready(function () {
            if ($('#tipoConsulta').val() == "t") {
                $('input[name="radioCupon"]').attr('disabled', 'disabled');
                $('#buttonSave').hide();
                $('#buttonCancel').hide();
                $('#buttonSalir').show();
            }
        });

        function checkCupon(nroCupon, impCuponTj, nroTarjeta, cuponAdmin, cuponDeb,
                cuponCred, fecha, desc, monedaCupon, esAdelanto) {
            $('#idRendicionFrm').val($('#idRendicion').val());
            $('#nroCupon').val(cuponAdmin);
            $('#impCuponTj').val(impCuponTj);
            $('#nroTarjeta').val(nroTarjeta);
            $('#cuponDeb').val(cuponDeb);
            $('#cuponCred').val(cuponCred);
            $('#descCupon').val(desc);
            $('#fechaPresentacion').val(fecha);
            $('#monedaCupon').val(monedaCupon);
            $('#esAdelanto').val(esAdelanto);
        }

        function saveCupon(tipoSubmit) {
            if ($('#nroCupon').val() == "") {

                $('#messageValidate').show();
            } else {
                var costosDestino = getParameterByName('costosDestino');

                document.forms[0].action = document.forms[0].action
                        + "?tipoSubmit=" + tipoSubmit
                        + "&codigo=" + $('#idRendicion').val()
                        + "&cCosto=" + $('#cCostos').val()
                        + "&fechaD=" + $('#fechaD').val()
                        + "&fechaH=" + $('#fechaH').val()
                        + "&costosDestino=" + costosDestino
                        + "&codMotivo=" + $('#codMotivo').val()
                        + "&esAdelanto=" + $('#esAdelanto').val()
                        + "&opcion=ALTA";
                document.getElementById("CuponesForm").submit();

            }
        }
        function getParameterByName(name) {
            name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
            var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                    results = regex.exec(location.search);
            return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
        }
        function OnClose() {
            if (window.opener != null && !window.opener.closed) {
                window.opener.HideModalDiv();

                //	        window.close();

            }
            //        window.opener.location.href="mostrarDetalleGastos.do?codigo="+$('#idRendicion').val();
        }
        window.onunload = OnClose;
    </script>
</body>