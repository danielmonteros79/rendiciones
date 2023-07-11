<%@page import="org.apache.struts.action.ActionForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@page import="java.util.*"%>

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
	<logic:notPresent name="message">
		<table>
			<html:form action="/replacePageForm.do" styleId="delegados">
				<tr>
					<td colspan="2" align="center" class="fieldTable">
						Selecci&oacute;n:
						<html:select property="delegado" >
							<html:options  collection="ComboUsuarios" property="id" labelProperty="descripcion" />
						</html:select>
						<html:submit styleClass="buttonReplace" value="Reemplazar"/>
					 </td>
				</tr>
			</html:form>
		</table>
	</logic:notPresent>

	<script type="text/javascript">
		function ChangeDelegado() {
			document.getElementById("delegados").reset();
		}
		
		jQuery(document).ready(function() {
			$('#checkReemplazar').show();
		});
	</script>
</body>