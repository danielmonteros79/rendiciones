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
            <% request.getSession().removeAttribute("message"); %>
        </logic:present>
        <table>
            <thead><tr><th>Filtro alertas</th></tr></thead>
            <html:form action="parametrosAlertasFiltro" styleId="parametrosAlertasFiltro"><tbody class="filtros">
                    <tr>
                        <td align="left">
                            Motivo
                            <html:select property="codMotivo" style="width:256px;" onchange="selectMotivo();ayudaMotivo();" styleId="motivo">
                                <html:option value=""></html:option>
                                <html:options collection="cmbMotivo" property="id" labelProperty="descripcion"/>
                            </html:select>
                            <a id="ayudaMotivo" target="_blank">
                                <img width="15px" src='./images/iconos/question-mark-2-48.png' style="display:none;" alt='Ayuda' title="Ayuda"
                                     border='0' style="margin-left: 5px;" align="top" id="imagen" />
                            </a>
                            Gasto
                            <html:select property="codGasto" onchange="selectGasto();" style="width:256px;" styleId="gasto">
                                <html:option value=""></html:option>
                                <html:options collection="cmbGasto" property="id" labelProperty="descripcion"/>
                                <html:option value="9999 - TODOS LOS GASTOS"></html:option>
                            </html:select>

                            <html:hidden property="codGasto" styleId="codGasto"/>

                            <html:button property="" value="Limpiar" styleClass="buttonClear" style="float:right;" onclick="resetForm();" />
                            <html:submit styleClass="buttonFilter" style="float:right;" value="Filtrar" />
                        </td>
                    </tr>
                </tbody></html:form>
            </table>
        <html:form action="parametrosAlertasDetalle" styleId="addAlerta">
            <input type="hidden" name="accion" value="alta"/>
            <a href="#" onclick="agregarAlerta()" style="float:right; margin-right:1.2%;margin-top:3px;">
                <img src="./images/addButtonGoogle.png" alt="Nueva alerta" height="32" width="32"> 
            </a>
        </html:form>
        <div id="paginacion" style="margin-top:43px;">
            <display:table uid="row" name="alerta"
                           requestURI="parametrosAlertasFiltro.do" id="ParametrosAlertasTable" excludedParams="false"
                           decorator="com.sa.decorator.parametros.ParametrosAlertasTableDecorator" pagesize="15"
                           style="margin-left:-0.9%;width:99.7%;" export="true">
                <display:column media="html csv excel" property="codMotivo" title="Motivo" style="width:4%" sortable="true" style="text-align:right;" />
                <display:column media="html csv excel" property="desMotivo" title="Descripción Motivo" />
                <display:column media="html csv excel" property="codGasto" title="Gasto" sortable="true" style="text-align:right;"/>
                <display:column media="html csv excel" property="desGasto" title="Descripción Gasto" />
                <display:column media="html csv excel" property="montCant" title="Mont/Cant" style="text-align:center;" />
                <display:column media="html csv excel" property="rend" title="Rend" />
                <display:column media="html csv excel" property="periodo" title="Período" />
                <display:column media="html csv excel" property="nivelMin" title="Nivel Min" style="text-align:center;" />
                <display:column media="html csv excel" property="nivelMax" title="Nivel Max" style="text-align:center;" />
                <display:column media="html csv excel" property="estado" title="Estado" style="text-align:center;" />
                <display:column media="html" property="opciones" title="Opciones" style="width:4%" />

                <display:setProperty name="export.csv.filename" value="ListadoParametrosAlerta.csv"/>
                <display:setProperty name="export.excel.filename" value="ListadoParametrosAlerta.xls"/>
            </display:table>
        </div>
        <script type="text/javascript" src="./static/js/parametrosAlertas.js"></script>
    </body>
</html>