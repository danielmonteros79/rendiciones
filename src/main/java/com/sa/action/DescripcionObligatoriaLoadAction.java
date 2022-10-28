package com.sa.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import com.sa.entities.DatosPantallaDinamica;
import com.sa.entities.Usuario;
import com.sa.form.DescripcionObligatoriaForm;
import com.sa.services.PagosService;

public class DescripcionObligatoriaLoadAction extends RestriccionTransaccionAction {
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		DescripcionObligatoriaForm descForm = (DescripcionObligatoriaForm) form;
		descForm.reset();
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("Entra al action DescripcionObligatoriaLoadAction. Usuario ("+user.getIdUser()+")");
		
		try{
			if(request.getParameter("tipoEntrada").equalsIgnoreCase("1"))
				request.setAttribute("tipoEntrada", "1");
			else
				request.setAttribute("tipoEntrada", "2");
			
			String message = request.getAttribute("messageModifTCJP") == null ? "" :(String) request.getAttribute("messageModifTCJP") + "<br>";
			String idRen = request.getParameter("rnd");
			String idGasto = request.getParameter("rndg");
			String codMotivo = request.getParameter("rndm");
			String codGasto = request.getParameter("codGasto");
			String codObserv = String.format("%05d", Integer.parseInt(request.getParameter("codObserv")));
			
			PagosService service = new PagosService(samClient);
			
			descForm.setIdRendicion(idRen);
			descForm.setIdGasto(idGasto);
			descForm.setCodGasto(codGasto);
			descForm.setCodDetOblig(codObserv);
			descForm.setIdRendicion(request.getParameter("rnd"));
			descForm.setTipoEntrada((String)request.getAttribute("tipoEntrada"));
			descForm.setEstadoRend(request.getParameter("estadoRend"));
			if (descForm.getEstadoRend()==null || descForm.getEstadoRend().equalsIgnoreCase("")){
				descForm.setEstadoRend("PENDI");
			}
			descForm.setCodMotivo(request.getParameter("codMotivo"));
			log.info("Se llama al metodo que trae el detalle obligatorio");
			
			List<DatosPantallaDinamica> fieldsScreen = service.consultaDetObligatorio(idRen, idGasto, codMotivo, codObserv);
			request.setAttribute("listCampos", fieldsScreen);
			if (service.getMsg() != null)
				message += service.getMsg() + "<br>";
			
			Map<Integer, String> headerMap = new TreeMap<Integer, String>();
			for (DatosPantallaDinamica dato : fieldsScreen) {
				if (dato.getTipoCampo().equals("COD1")) headerMap.put(1, dato.getTituloCampo());
				else if (dato.getTipoCampo().equals("COD2")) headerMap.put(2, dato.getTituloCampo());
				else if (dato.getTipoCampo().equals("TXT1")) headerMap.put(3, dato.getTituloCampo());
				else if (dato.getTipoCampo().equals("TXT2")) headerMap.put(4, dato.getTituloCampo());
				else if (dato.getTipoCampo().equals("NUM1")) headerMap.put(5, dato.getTituloCampo());
				else if (dato.getTipoCampo().equals("NUM2")) headerMap.put(6, dato.getTituloCampo());
				else if (dato.getTipoCampo().equals("FEC1")) headerMap.put(7, dato.getTituloCampo());
				else if (dato.getTipoCampo().equals("FEC2")) headerMap.put(8, dato.getTituloCampo());
				else if (dato.getTipoCampo().equals("TXT250")) headerMap.put(9, dato.getTituloCampo());
			}
			request.setAttribute("headers", new ArrayList<String>(headerMap.values()));
			
			List<List<String>> filas = service.consultaDetallesGastos(idRen, idGasto, codObserv, fieldsScreen);
			request.setAttribute("filas", filas);
			if (service.getMsg() != null && !service.getMsg().equals("CLAVE PARCIAL NO EXISTE"))
				message += service.getMsg() + "<br>";
			
			if (!message.equals(""))
				request.setAttribute("messageModifTCJP", message);
		} catch (Exception e) {
			log.error(e);
			request.setAttribute("messageModifLoad", "ERROR AL CARGAR LAS DESCRIPCIONES OBLIGATORIAS: " + e.getCause().getMessage());
		}
		
		return mapping.findForward("success");
	}
}