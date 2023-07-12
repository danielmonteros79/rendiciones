<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page import="java.util.Locale"%>
<%@page import="com.sa.entities.Usuario"%>

<% 
	Usuario userSession = (Usuario) request.getSession().getAttribute("usuario");
	Usuario userWorking = (Usuario) request.getSession().getAttribute("userWorking"); 
%>

<nav id="template-header" class="navbar navbar-dark bg-dark navbar-expand-md p-0">
	<div class="container p-0">
        <html:link action="bienvenida.do" styleClass="navbar-brand d-block d-md-none">
			<img src="./images/BBVA_WHITE.png" height="50px" alt="">
		</html:link>
        <button class="btn btn-link navbar-toggler navbar-toggler-right border-dark" type="button" data-toggle="collapse" data-target="#navbarCollapse">
            <i class="bbva-icon icon-coronita_menu text-white hover-blue align-middle"></i>
        </button>
        <div class="collapse navbar-collapse flex-column" id="navbarCollapse">
            <ul class="navbar-nav ml-auto mb-0 p-0">
				<li class="nav-item">
                    <a class="nav-link" href="#"><small><b>
                    <%
							java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("EEEE dd MMMM yyyy", new Locale("ES"));
							String fecha = df.format(new java.util.Date());
							String[] v = fecha.split("\\s");
							String fechaForm = v[0] + " " + v[1] + " de " + v[2] + " de " + v[3];
							out.print(fechaForm);
					%>
					</b></small></a>
                </li>
                <li class="nav-item px-md-3">
                    <a class="nav-link float-left" href="#">
                    	<small><b>
                    		Usuario: <%= userSession != null ? userSession.getIdUser() : "" %>
                    		<%= !userWorking.getIdUser().equals(userSession.getIdUser()) ? "( con atribuciones de " + userWorking.getNombre() + " )" : "" %>
                    	</b></small>
                    </a>
                    <a class="nav-link float-right text-info" href="logout.do" title="Cerrar sesi&oacute;n">
                    	<small>
	                    	<b>Salir</b>
							<i class="bbva-icon icon-coronita_upload fab fa-sm fa-rotate-90 text-info"></i>
						</small>
					</a>
                </li>
            </ul>
            <div class="navbar-collapse m-0 pb-0" id="navbarCollapse">
            	<html:link action="bienvenida.do" styleClass="navbar-brand d-none d-md-block">
					<img src="./images/BBVA_WHITE.png" height="83px" alt="" >
               	</html:link>
            	<ul class="navbar-nav flex-xs-row mb-0 pb-0" style="align-items: flex-end; flex-wrap: wrap;">
            		<% if (userWorking.getTipoPerfil().getPantalla().contains("Rendiciones")) { %>
	                <li class="nav-item pr-md-2 ">
	                	<html:link action="listadoRendiciones.do" styleClass="nav-link nav-rendiciones">
	                		<span>RENDICIONES</span>
	                	</html:link>
	                </li>
					<% } %>
					
					<% if (userSession.getTipoPerfil().getPantalla().contains("Delegacion")) { %>
	                <li class="nav-item dropdown pr-md-2 px-xl-3">
	                	<a class="nav-link dropdown-toggle nav-delegacion" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<span>DELEGACI&Oacute;N</span>
						</a>
						<div class="dropdown-menu bg-secondary" aria-labelledby="navbarDropdown">
							<html:link action="accesoDelegado.do" styleClass="dropdown-item bg-secondary text-white">
								<span>ACCESO COMO DELEGADO</span>
							</html:link>
							
							<% if(userWorking.getIdUser() == userSession.getIdUser()) { %>
							<html:link action="abmDelegado.do" styleClass="dropdown-item bg-secondary text-white">
								<span>FUNCIONES A TERCEROS</span>
							</html:link>
							<% } %>
						</div>
	                </li>
					<% } %>
					
					<% if (userWorking.getTipoPerfil().getPantalla().contains("Aprobacion") && userWorking.getGlgAprobacion().size() > 0) { %>
	                <li class="nav-item dropdown pr-md-2 ">
	                    <a class="nav-link dropdown-toggle nav-aprobacion" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<span>APROBACI&Oacute;N</span>
						</a>
						<div class="dropdown-menu bg-secondary" aria-labelledby="navbarDropdown">
							<% if (userWorking.getGlgAprobacion().contains(1)) { %>
							<html:link action="listadoAprobaciones.do?glg=1" styleClass="dropdown-item bg-secondary text-white">
								<span>SUPERVISOR</span>
							</html:link>
							<% } %>
							<% if (userWorking.getGlgAprobacion().contains(2)) { %>
							<html:link action="listadoAprobaciones.do?glg=2" styleClass="dropdown-item bg-secondary text-white">
								<span>FIRMA</span>
							</html:link>
							<% } %>
							<% if (userWorking.getGlgAprobacion().contains(4)) { %>
							<html:link action="listadoAprobaciones.do?glg=4" styleClass="dropdown-item bg-secondary text-white">
								<span>GLG (ENTRADA)</span>
							</html:link>
							<% } %>
							<% if (userWorking.getGlgAprobacion().contains(3)) { %>
							<html:link action="listadoAprobaciones.do?glg=3" styleClass="dropdown-item bg-secondary text-white">
								<span>GLG</span>
							</html:link>
							<% } %>
						</div>
	                </li>
					<% } %>
					
	              <%-- <% if (userWorking.getTipoPerfil().getPantalla().contains("Parametros")) { %>
	                <li class="nav-item dropdown pr-md-2 px-xl-3">
	                    <a class="nav-link dropdown-toggle nav-parametros" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<span>PAR&Aacute;METROS</span>
						</a>
						<div class="dropdown-menu bg-secondary" aria-labelledby="navbarDropdown">
							<html:link action="parametrosMotivo.do" styleClass="dropdown-item bg-secondary text-white">
								<span>MOTIVOS</span>
							</html:link>
							<html:link action="parametrosGastos.do" styleClass="dropdown-item bg-secondary text-white">
								<span>GASTOS</span>
							</html:link>
							<html:link action="parametrosExceptuados.do" styleClass="dropdown-item bg-secondary text-white">
								<span>EXCEPTUADOS</span>
							</html:link>
							<html:link action="parametrosAlertas.do" styleClass="dropdown-item bg-secondary text-white">
								<span>ALERTAS</span>
							</html:link>
						</div>
	                </li>
					<% } %>  --%>
					
				<% if (userWorking.getTipoPerfil().getPantalla().contains("Cierre")) { %>
	                <li class="nav-item dropdown pr-md-2 ">
	                    <a class="nav-link dropdown-toggle nav-cierre" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<span>CIERRE</span>
						</a>
						<div class="dropdown-menu bg-secondary" aria-labelledby="navbarDropdown">
							<html:link action="cierreOrdenDePago.do" styleClass="dropdown-item bg-secondary text-white">
								<span>CIERRE ORDEN DE PAGO</span>
							</html:link>
							<%-- <html:link action="cierreTarjeta.do" styleClass="dropdown-item bg-secondary text-white">
								<span>CIERRE TARJETA DE CR&Eacute;DITO</span>
							</html:link> --%>
						</div>
	                </li>
					<% } %> 
					
					<% if (userWorking.getTipoPerfil().getPantalla().contains("Resumen")) { %>
	                <li class="nav-item dropdown pr-md-2 ">
	                    <a class="nav-link dropdown-toggle nav-tarjeta-corporativa" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<span>TARJETA CORPORATIVA</span>
						</a>
						<div class="dropdown-menu bg-secondary" aria-labelledby="navbarDropdown">
							<html:link action="consumosNoRendidos.do" styleClass="dropdown-item bg-secondary text-white">
								<span>CONSUMOS NO RENDIDOS</span>
							</html:link>
							<html:link action="resumenesAnteriores.do" styleClass="dropdown-item bg-secondary text-white">
								<span>RES&Uacute;MENES ANTERIORES</span>
							</html:link>
						</div>
	                </li>
					<% } %>
										
					<% if (userWorking.getTipoPerfil().getPantalla().contains("Aprobacion") || userWorking.getTipoPerfil().getPantalla().contains("Cierre")) { %>
	                <li class="nav-item dropdown pr-md-2 ">
	                    <a class="nav-link dropdown-toggle nav-reporteria" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<span>REPORTER&Iacute;A</span>
						</a>
						<div class="dropdown-menu bg-secondary" aria-labelledby="navbarDropdown">
							<html:link action="cuadroGeneral.do" styleClass="dropdown-item bg-secondary text-white">
								<span>CUADRO GENERAL</span>
							</html:link>
							<html:link action="CuadroDetallado.do" styleClass="dropdown-item bg-secondary text-white">
								<span>CUADRO DETALLADO</span>
							</html:link>
						</div>
	                </li>
					<% } %>
            	</ul>
			</div>
			<div>
				<div class="nav-item pb-3">
					<a href="#" id="manualRendiciones"> <i class="fas fa-plus-circle text-center"></i>
						<b>Manual de rendiciones de gastos</b>
					</a>
				</div>
			</div>
        </div>
    </div>
</nav>