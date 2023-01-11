<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="template"%>

<div id="cargarRequerimiento"><logic:present name="error">
        <bean:write name="error" />
    </logic:present> 
    <logic:present name="listadoError">
        <ul style="color: black">
            <logic:iterate id="lista" name="listadoError">
                <li><bean:write name="lista" /></li>
            </logic:iterate>
        </ul>
    </logic:present>
    <logic:present name="listadoError2">
        <ul style="color: black">
            <logic:iterate id="lista" name="listadoError2">
                <li>
                    <%
    Exception exception = (Exception) lista;

            out.println("excepcion tipo: " + exception.getClass());
            out.println("<br>");
            out.println("excepcion msg: " + exception.getMessage());
            out.println("<br>");
            out.println("excepcion cause: " + exception.getCause());
            out.println("<br>");
            out.println("<br>");
// 			StackTraceElement[] ste = exception.getStackTrace();
// 			for (int i = 0; i < ste.length; i++) {
// 				out.println(ste[i].toString());
                    out.println("<br>");
// 			}
                    %>
                </li>
            </logic:iterate>
        </ul>
    </logic:present>
    <logic:present name="exception">
        <%
                Exception exception = (Exception) request
                                        .getAttribute("exception");

                        out.println("excepcion tipo: " + exception.getClass());
                        out.println("<br>");
                        out.println("excepcion msg: " + exception.getMessage());
                        out.println("<br>");
                        out.println("excepcion cause: " + exception.getCause());
                        out.println("<br>");
                        out.println("<br>");
// 			StackTraceElement[] ste = exception.getStackTrace();
// 			for (int i = 0; i < ste.length; i++) {
// 				out.println(ste[i].toString());
                                out.println("<br>");
// 			}
        %>
    </logic:present></div>