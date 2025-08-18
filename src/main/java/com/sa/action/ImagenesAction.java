package com.sa.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import com.sa.entities.Gastos;
import com.sa.form.RendicionAvisoForm;
import com.sa.services.AprobacionesService;
import com.sa.services.RendicionesService;
import com.sa.services.trxs.WM95;
import java.util.HashMap;
import java.util.Map;

import com.sa.entities.Archivo;
import com.sa.entities.Rendicion;
import com.sa.services.ThubanService;
import org.apache.commons.text.StringEscapeUtils;

public class ImagenesAction extends RestriccionTransaccionAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			RendicionAvisoForm frm = (RendicionAvisoForm) form;

			if ("inicializar".equals(frm.getAction()))
				return this.inicializar(request, response, frm, samClient);
			else if ("cargarArchivo".equals(frm.getAction())) {
				return this.cargarArchivo(request,response, frm);}
			else if ("borrarArchivo".equals(frm.getAction()))
				return this.borrarArchivo(request, response, frm);
			else if ("generar".equals(frm.getAction()))
				return this.generar(frm, mapping, request, response, samClient);
			else if("descargarImg".equals(frm.getAction())) {
				return this.descargarImg(request, response, frm, samClient);
			}

			return null;
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}

	private ActionForward inicializar(HttpServletRequest request, HttpServletResponse response, RendicionAvisoForm frm, SAMWebClient samClient) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		ThubanService thubanService = new ThubanService(samClient);
		String thubanUser = (String) request.getSession().getServletContext().getAttribute("esb.thuban.user");
		String thubanPass = (String) request.getSession().getServletContext().getAttribute("esb.thuban.pass");
		String thubanClaseDoc = (String) request.getSession().getServletContext().getAttribute("esb.thuban.clase.documental");
		
		frm.clean();
		
		String idRendicion = request.getParameter("idRend");
		
		List<Archivo> archivos = thubanService.buscarArchivos(thubanClaseDoc, thubanUser, thubanPass, idRendicion);
		resp.put("archivos", archivos);

		return writeJson(response, resp);
	}
	
	private ActionForward descargarImg(HttpServletRequest request, HttpServletResponse response, RendicionAvisoForm frm, SAMWebClient samClient) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		ThubanService thubanService = new ThubanService(samClient);
		String thubanUser = (String) request.getSession().getServletContext().getAttribute("esb.thuban.user");
		String thubanPass = (String) request.getSession().getServletContext().getAttribute("esb.thuban.pass");
		String thubanClaseDoc = (String) request.getSession().getServletContext().getAttribute("esb.thuban.clase.documental");
		
		frm.clean();
		
		String idImagen = request.getParameter("idImagen");
		
		List<Archivo> archivo = thubanService.descargarArchivo(thubanClaseDoc, thubanUser, thubanPass, idImagen);
		
		resp.put("archivo", archivo);

		return writeJson(response, resp);
	}
	
	

	private ActionForward cargarArchivo(HttpServletRequest request,HttpServletResponse response, RendicionAvisoForm frm) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		
		String extension = request.getParameter("tipoArchivo").toLowerCase();
		String base64Decoded = request.getParameter("base64");
	    //byte[] decodedData = DatatypeConverter.parseBase64Binary(base64Decoded);
     
		if (!extension.contains("pdf") && !extension.contains("tif")) {
		    String safeFileName = StringEscapeUtils.escapeHtml4(frm.getArchivo().getFileName());
		    return writeError(response, safeFileName + ": El archivo no es un PDF válido.");
		}
		
		String nombreArchivo = StringEscapeUtils.escapeHtml4(request.getParameter("nombreArchivo"));
		resp.put("nombreArchivo", nombreArchivo);

		Archivo archivo = new Archivo();
		archivo.setNomArchivo(request.getParameter("nombreArchivo"));
		archivo.setInputStream(frm.getArchivo().getInputStream());
		archivo.setBase64File(base64Decoded);
		
		frm.getArchivosASubir().add(archivo);

		return writeJson(response, resp);

	}


	private ActionForward borrarArchivo(HttpServletRequest request, HttpServletResponse response, RendicionAvisoForm frm) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		
		resp.put("nombreArchivo", request.getParameter("nombreArchivo"));
		
		boolean encontro = false;
		for (int i = 0; i < frm.getArchivosASubir().size() && !encontro; i++) {
			Archivo archivo = frm.getArchivosASubir().get(i);

			if (archivo.getNomArchivo().equals(request.getParameter("nombreArchivo"))) {
				encontro = true;
				frm.getArchivosASubir().remove(i);
			}
		}

		return writeJson(response, resp);
	}

	private ActionForward generar(RendicionAvisoForm frm, ActionMapping mapping, HttpServletRequest request, HttpServletResponse response,
			SAMWebClient samClient) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		String thubanUser = (String) request.getSession().getServletContext().getAttribute("esb.thuban.user");
		String thubanPass = (String) request.getSession().getServletContext().getAttribute("esb.thuban.pass");
		String thubanClaseDoc = (String) request.getSession().getServletContext().getAttribute("esb.thuban.clase.documental");
		
		String message = "OK: ARCHIVOS SUBIDOS CON ÉXITO.";
		String idRendicion = request.getParameter("idRendicion");
	
		String glg = request.getParameter("glg");
		boolean esAprobacion = request.getParameter("esAprobacion").equals("true");

		RendicionesService rendicionesService = new RendicionesService(samClient);
		AprobacionesService aprobacionesService = new AprobacionesService(samClient);
		ThubanService thubanService = new ThubanService(samClient);
		
		Rendicion rendicion = null;
		if (esAprobacion)
			rendicion = aprobacionesService.getAprobacionesPendientes(idRendicion, null, null, glg, this.getSessionUserWorking().getIdUser()).get(0);
		else
			rendicion = rendicionesService.obtenerListadoRendiciones(this.getSessionUserWorking().getIdUser(), idRendicion, null, null, null).get(0);
	
		rendicion.setUsuarioRendicion(this.getSessionUserWorking().getIdUser());
		rendicion.setId(Integer.parseInt(idRendicion));
		rendicion.setCostosDestino(String.valueOf(this.getSessionUserWorking().getCcostos()));

		List<String> errores = thubanService.publicarDocumentos(thubanClaseDoc, thubanUser, thubanPass, rendicion, frm.getArchivosASubir());
		
		if (errores.size() == frm.getArchivosASubir().size())
			return writeError(response, StringUtils.join(errores.toArray(), "<br><br>"));

		
		if (errores.size() > 0)
			message += "<br><br>" + StringUtils.join(errores.toArray(), "<br>");
		
		resp.put("message", message);

		return writeJson(response, resp);
	
	}
	
	

}
