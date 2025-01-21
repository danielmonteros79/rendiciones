<%@ page contentType="text/html;charset=ISO-8859-1" language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<script type="text/javascript">
	window.onload = function () {
		var href = encodeURIComponent(location.href);

		var myDir = href.substring( 0, href.lastIndexOf( "/" ) + 1);
		var myWindow = window.open("/pkmslogout.form");
		alert("HA FINALIZADO LA SESION.");
		myWindow.close();

		window.location.href = decodeURIComponent(myDir);
	}
</script>
