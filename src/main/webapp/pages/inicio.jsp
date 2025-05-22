<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page import="com.sa.entities.Usuario"%>

<% 
	Usuario userSession = (Usuario) request.getSession().getAttribute("usuario");
	Usuario userWorking = (Usuario) request.getSession().getAttribute("userWorking");
%>

<div class="position-relative">
	<img src="images/bienvenida.png" class="img-fluid w-100" style="height:320px" >
	<div class="image-text-center text-white font-weight-bold">
		<span class="d-block" style="font-size: 4vw">Rendici&oacute;n de Gastos</span>
		<span>Bienvenido, <%= userSession.getNombre() %></span>
		<%-- <div>
			<html:link action="altaRendicion.do" styleClass="btn btn-info px-5 py-3 mt-2">
				Crear Rendici&oacute;n
			</html:link>
		</div> --%>
	</div>
</div>

<div class="container py-3 py-md-4">
	<div class="row">
		<div class="col-sm-12 mb-md-2">
			<i class="bbva-icon icon-coronita_bullet fab fa-rotate-270 text-blue align-middle mx-2"></i>
			<small class="font-weight-bold">GASTOS</small>
		</div>
	</div>
	<div class="row">
		<div class="col-md-8 mt-3">
			<h2>Bienvenido al sistema de Rendici&oacute;n de Gastos</h2>
			<h5 class="font-weight-400 mt-3 mt-md-5">
				Realiz&aacute; una nueva rendici&oacute;n de gastos, aprobaciones y delegaciones dentro del sistema de rendiciones.
				<br><br>
				En una misma rendici&oacute;n se podr&aacute;n rendir tanto gastos en efectivo como gastos de consumos con tarjeta corporativa.
			</h5>
		</div>
		<div class="col-md-4 mt-2 mt-md-3 pr-md-0">
			<div class="card bg-light border-light rounded-0 mb-3 w-85">
				<div class="card-body text-center p-5 p-md-3 p-lg-5">
					<h4 class="card-title"><b>&iquest;Quer&eacute;s cargar una rendici&oacute;n ahora?</b></h4>
					<html:link action="altaRendicion.do" styleClass="btn btn-info px-5 py-3">
						Comenzar
					</html:link>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="bg-primary text-center">
	<div class="container py-3 py-md-3">
		<div class="row">
			<div class="col-sm-12 text-white">
				<h3 class="font-weight-light pb-2">Segu&iacute; los siguientes pasos:</h3>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-3 my-3 text-white">
				<i class="bbva-icon icon-coronita_desktop fa-lg"></i>
				<br>
				Cargar motivos
			</div>
			<div class="col-sm-3 my-3 text-white-50">
				<i class="bbva-icon icon-coronita_cash fa-lg"></i>
				<br>
				Cargar todos los gastos
			</div>
			<div class="col-sm-3 my-3 text-white-50">
				<i class="bbva-icon icon-coronita_document fa-lg"></i>
				<br>
				Adjuntar las im&aacute;genes que corroboren los gastos
			</div>
			<div class="col-sm-3 mt-3 text-white-50">
				<i class="bbva-icon icon-coronita_alert fa-lg"></i>
				<br>
				Presionar Generar
			</div>
		</div>
	</div>
</div>

<div class="bg-light">
	<div class="container py-3 py-md-3">
		<div class="row">
			<div class="col-sm-12 my-2 my-md-4 text-center">
				<h2 class="font-weight-light">Los distintos estados de cada rendici&oacute;n</h2>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6 col-md-3 my-md-3 p-3">
				<i class="fas fa-luggage-cart"></i>
				<br>
				<b>Estado 0:</b>
				<br>
				La rendici&oacute;n est&aacute; pendiente, no pasa de estado hasta que se termine de cargar, adjuntando la documentaci&oacute;n 
				requerida, previamente digitalizada.
			</div>
			<div class="col-sm-6 col-md-3 my-md-3 p-3">
				<i class="fas fa-luggage-cart"></i>
				<br>
				<b>Estado 1:</b>
				<br>
				<p>Las im&aacute;genes adjuntadas (documentos que avalan el gasto) est&aacute;n pendientes de procesarse por Thuban, sistema de
				resguarda los documento digitales. Este proceso se realiza por las noches, por lo que el estado 1 no cambiar&aacute; hasta el d&iacute;a
				siguiente.</p>
			</div>
			<div class="col-sm-6 col-md-3 my-md-3 p-3">
				<i class="fas fa-luggage-cart"></i>
				<br>
				<b>Estado 2:</b>
				<br>
				<p>Pendiente de aprobaci&oacute;n del Supervisor (jefe directo).</p>
			</div>
			<div class="col-sm-6 col-md-3 my-md-3 p-3">
				<i class="fas fa-luggage-cart"></i>
				<br>
				<b>Estado 3:</b>
				<br>
				<p>Pendiente de aprobaci&oacute;n de Firmante.</p>
			</div>
			<div class="col-sm-6 col-md-3 my-md-3 p-3">
				<i class="fas fa-luggage-cart text-primary"></i>
				<br>
				<b>Estado 4 (AZUL):</b>
				<br>
				<p>Pendiente de aprobaci&oacute;n de GLG.</p>
			</div>
			<div class="col-sm-6 col-md-3 my-md-3 p-3">
				<i class="fas fa-luggage-cart text-primary"></i>
				<br>
				<b>Estado 5 (AZUL):</b>
				<br>
				<p>Rendici&oacute;n Aprobada.</p>
				<br>
				<i class="fas fa-luggage-cart text-success"></i>
				<b>Estado 5 (VERDE):</b>
				<br>
				<p>Rendici&oacute;n Procesada.</p>
			</div>
			<div class="col-sm-6 col-md-3 my-md-3 p-3">
				<i class="fas fa-luggage-cart text-warning"></i>
				<br>
				<b>Estado 4 (AMARILLO):</b>
				<br>
				<p>Rendici&oacute;n observada, es decir se le solicita agregar informaci&oacute;n para avanzar con la aprobaci&oacute;n de la
				rendici&oacute;n. La rendici&oacute;n no avanzar&aacute; hasta que no se agregue una imagen.</p>
			</div>
			<div class="col-sm-6 col-md-3 my-md-3 p-3">
				<i class="fas fa-luggage-cart text-danger"></i>
				<br>
				<b>Estado ROJO:</b>
				<br>
				<p>Se le rechaz&oacute; la rendici&oacute;n.</p>
			</div>
		</div>
	</div>
</div>

<jsp:include page="./global/modalPrimerIngreso.jsp" />