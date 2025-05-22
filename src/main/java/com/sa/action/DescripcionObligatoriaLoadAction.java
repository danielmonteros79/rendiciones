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
	
	private static final String MSG_MODIF_TCJP = "messageModifTCJP";
	private static final String TIPO_ENTRADA = "tipoEntrada";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		DescripcionObligatoriaForm descForm = (DescripcionObligatoriaForm) form;
		descForm.reset();
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("Entra al action DescripcionObligatoriaLoadAction. Usuario ("+user.getIdUser()+")");
		
		try{
			if(request.getParameter(TIPO_ENTRADA).equalsIgnoreCase("1"))
				request.setAttribute(TIPO_ENTRADA, "1");
			else
				request.setAttribute(TIPO_ENTRADA, "2");
			String message = request.getAttribute(MSG_MODIF_TCJP) == null ? "" :(String) request.getAttribute(MSG_MODIF_TCJP) + "<br>";
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
			descForm.setTipoEntrada((String)request.getAttribute(TIPO_ENTRADA));
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
			    int tipoCampoValue;
		        switch (dato.getTipoCampo()) {
		            case "COD1":
		                tipoCampoValue = 1;
		                break;
		            case "COD2":
		                tipoCampoValue = 2;
		                break;
		            case "TXT1":
		                tipoCampoValue = 3;
		                break;
		            case "TXT2":
		                tipoCampoValue = 4;
		                break;
		            case "NUM1":
		                tipoCampoValue = 5;
		                break;
		            case "NUM2":
		                tipoCampoValue = 6;
		                break;
		            case "FEC1":
		                tipoCampoValue = 7;
		                break;
		            case "FEC2":
		                tipoCampoValue = 8;
		                break;
		            case "TXT250":
		                tipoCampoValue = 9;
		                break;
		            default:
		                tipoCampoValue = -1; // Valor por defecto o manejo de error
		                break;
		        }
		        if (tipoCampoValue != -1) {
		            headerMap.put(tipoCampoValue, dato.getTituloCampo());
		        }
			}
			request.setAttribute("headers", new ArrayList<String>(headerMap.values()));
			
			List<List<String>> filas = service.consultaDetallesGastos(idRen, idGasto, codObserv, fieldsScreen);
			request.setAttribute("filas", filas);
			if (service.getMsg() != null && !service.getMsg().equals("CLAVE PARCIAL NO EXISTE"))
				message += service.getMsg() + "<br>";
			
			if (!message.equals(""))
				request.setAttribute(MSG_MODIF_TCJP, message);
		} catch (Exception e) {
			log.error(e);
			request.setAttribute("messageModifLoad", "ERROR AL CARGAR LAS DESCRIPCIONES OBLIGATORIAS: " + e.getCause().getMessage());
		}
		
		return mapping.findForward("success");
	}
}