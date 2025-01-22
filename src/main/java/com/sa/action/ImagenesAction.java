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
     
		if (!extension.contains("pdf")) {
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
			rendicion = aprobacionesService.getAprobacionesPendientes(idRendicion, null, null, glg, this.sessionUserWorking.getIdUser()).get(0);
		else
			rendicion = rendicionesService.obtenerListadoRendiciones(this.sessionUserWorking.getIdUser(), idRendicion, null, null, null).get(0);
	
		rendicion.setUsuarioRendicion(this.sessionUserWorking.getIdUser());
		rendicion.setId(Integer.parseInt(idRendicion));
		rendicion.setCostosDestino(String.valueOf(this.sessionUserWorking.getCcostos()));
	
		
//		List<Gastos> gastos = rendicionesService.getGastos(idRendicion, "", rendicion.getUsuarioRendicion() != null ? rendicion.getUsuarioRendicion() : 
//			this.sessionUserWorking.getIdUser(), rendicion.getCodMotivo());
//		if (gastos.size() == 0)
//			return writeError(response, "La rendici&oacute;n no tiene gastos cargados.");
		
		List<String> errores = thubanService.publicarDocumentos(thubanClaseDoc, thubanUser, thubanPass, rendicion, frm.getArchivosASubir());
		
		if (errores.size() == frm.getArchivosASubir().size())
			return writeError(response, StringUtils.join(errores.toArray(), "<br><br>"));
//		else if (rendicion.getEstado().equals("PENDI") || rendicion.getEstado().equals("OBSER")) {
//			String idu = aprobacionesService.obtenerIDU(rendicion, this.sessionUserWorking.getIdUser(), WM95.DELIM_04_SIN_ADEA);
//			aprobacionesService.cambiarEscanRendicion(String.valueOf(rendicion.getId()), this.sessionUserWorking.getIdUser(), idu);
//			if (aprobacionesService.getMsg() != null)
//				message += "<br>" + aprobacionesService.getMsg();
//		}
		
		if (errores.size() > 0)
			message += "<br><br>" + StringUtils.join(errores.toArray(), "<br>");
		
		resp.put("message", message);

		return writeJson(response, resp);
	
	}
	
	
//	private void generateCaratula(HttpServletResponse response, RendicionAvisoForm frm, HttpServletRequest request,
//			SAMWebClient samClient, AprobacionesService aprobacionesService, List<Gastos> gastos) throws Exception {
//		try {
//			String msg = "";
//			
//			// Verifica si ya tiene codigo adea la rendicion
//			boolean isCaratula = frm.getRendicion().getAdea().equalsIgnoreCase("") ? false : true;
//			String iduAdea = null;
//			if (!isCaratula) {
//				log.info("Se obtiene idu y adea para caratula");
//				iduAdea = aprobacionesService.obtenerIDU(frm, WM95.DELIM_04_CON_ADEA);
//			} else {
//				log.info("Caratula ya generada se obtiene el idu y adea de la rendicion");
//				iduAdea = frm.getRendicion().getIdu() + ";" + frm.getRendicion().getAdea();
//			}
//			if (iduAdea == null) {
//				msg = "ERROR: Error al generar IDU y ADEA";
//				request.setAttribute("msg", msg);
//				return;
//			}
//
//			CaratulaService servCaratula = new CaratulaService(samClient);
//			String[] thubanCod = iduAdea.split(";");
//
//			log.info("Se obtiene template para caratula. ");
//			String html = servCaratula.generarCaratulaTemplate(frm, thubanCod, gastos);
//			log.info("Template obtenido: " + html);
//			if (!isCaratula) {
//				aprobacionesService.cambiarEscanRendicion(String.valueOf(frm.getRendicion().getId()), frm.getRendicion()
//						.getUsuarioRendicion(), thubanCod[0], thubanCod[1]);
//				frm.getRendicion().setIdu(thubanCod[0]);
//				frm.getRendicion().setAdea(thubanCod[1]);
//			}
//			log.info("Se obtiene caratula pdf");
//			
//			String codigoBarraPath = (String) request.getSession().getServletContext().getAttribute("rendicion.image.idu");
//			File codigoBarrasIdu = servCaratula.createBarcodeImg(codigoBarraPath, frm.getRendicion().getIdu());
//			File codigoBarrasAdea = servCaratula.createBarcodeImg(codigoBarraPath, frm.getRendicion().getAdea());
//			String imgIdu = "<img src='" + codigoBarrasIdu + "' width='245px' height='65px' />";
//			String imgAdea = "<img src='" + codigoBarrasAdea + "' width='245px' height='65px'  />";
//			html = html.replace(CaratulaTemplate.REPLACE_IDU, imgIdu);
//			html = html.replace(CaratulaTemplate.REPLACE_ADEA, imgAdea);
//
//			String fileName = "caratulaRendicion_" + frm.getRendicion().getId();
//			String fileType = "pdf";
//
//			response.setContentType("application/vnd.pdf");
//			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "." + fileType + "\"");
//			OutputStream out = response.getOutputStream();
//
//			Document document = new Document(PageSize.A4);
//			PdfWriter.getInstance(document, out);
//			String img = (String) request.getSession().getServletContext().getAttribute("rendicion.image.caratula");
//
//			log.info("CARTULA - IMG:_ " + img);
//			File fileImg = new File(img);
//			String imgLogo = "<img src='" + img + "' height='45px' />";
//			html = html.replace(CaratulaTemplate.REPLACE_BBVAIMAGEN, imgLogo);
//
//			String buffer = html;
//			log.info("CARATULA - IMG FILE: " + fileImg.getAbsolutePath());
//			log.info("CARATULA - IMG FILE EXIST? " + fileImg.exists());
//			log.info("CARATULA - IMG IS FILE ? " + fileImg.isFile());
//			byte[] imgByte = FileUtils.readFileToByteArray(fileImg);
//
//			Image imagen = Image.getInstance(imgByte);
//
//			document.open();
//
//			imagen.scaleAbsoluteWidth(100f);
//
//			// HTMLWorker
//			HTMLWorker htmlWorker = new HTMLWorker(document);
//			// String str =
//			// "Este es el contenido HTML, bien en String o reemplazalo por el contenido del fichero del ejemplo anterior";
//			htmlWorker.parse(new StringReader(buffer));
//
//			document.close();
//			out.flush();
//			out.close();
//			codigoBarrasIdu.delete();
//			codigoBarrasAdea.delete();
//		} catch (Exception e) {
//			request.setAttribute("msg", "ERROR: Error al generar car\u00e1tula");
//			log.error(e);
//			throw new Exception(e);
//		}
//	}
	
	
	
	
}
