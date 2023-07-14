package com.sa.action;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.ImagenesForm;
import com.sa.services.AprobacionesService;
import com.sa.services.CaratulaService;
import com.sa.services.RendicionesService;
import com.sa.services.trxs.WM95;
import com.sa.util.ArchivoUtil;
import com.sa.util.CaratulaTemplate;

public class RendicionAvisoAction extends RestriccionTransaccionAction {
	private static final String ACTION = "action";
	private static final String ERROR_GENERAR = "errorGenerar";
	private static final String FAILURE_GENERAR = "failureGenerar";
	private static final String IMG_SRC = "<img src='";

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ImagenesForm frm = (ImagenesForm) form;
		
		if (request.getParameter("generate") != null) {
			
			String idRendicion = (String) request.getParameter("rnd");
			log.info("Se procede a reimprimir la cartaula RENDICION:"+idRendicion);
			frm = new ImagenesForm();
			frm.setAccion("caratula");
			frm.setAction("generar");
			frm.setRendicion(new Rendicion());
			frm.getRendicion().setId(Integer.valueOf(idRendicion));
		}
		if ("mostrarPantalla".equals(frm.getAction()))
			this.mostrarPantalla(frm, request);
		else if ("generar".equals(frm.getAction()))
 			return this.generar(frm, mapping, request, response, samClient);
		else if ("cargarArchivo".equals(frm.getAction())) {
			frm.getArchivosASubir().add(
					ArchivoUtil.cargarArchivo(response.getWriter(), frm.getArchivo()));
			response.setContentType("application/json");
			response.getWriter().flush();
			response.getWriter().close();
		}
		else if ("borrarArchivo".equals(frm.getAction()))
			ArchivoUtil.borrarArchivo(request.getParameter("nombreArchivo"),
					frm.getArchivosASubir());
		else if ("getAccion".equals(frm.getAction()))
			this.getAccion(response.getWriter(), frm.getAccion());
		else if ("getArchivosASubir".equals(frm.getAction()))
			ArchivoUtil.getArchivosASubir(response.getWriter(), frm
					.getArchivosASubir());

		return mapping.findForward("success");
	}

	private void mostrarPantalla(ImagenesForm frm, HttpServletRequest request) {
		frm.clean();
		
		frm.getRendicion().setId(Integer.parseInt(request.getParameter("codigo")));
		frm.getRendicion().setUsuarioRendicion((request.getParameter("usuarioRend")));
		frm.getRendicion().setGlg((request.getParameter("glg")));
	}

	private ActionRedirect generar(ImagenesForm frm, ActionMapping mapping, HttpServletRequest request,
			HttpServletResponse response, SAMWebClient samClient) throws Exception {
		Usuario user = (Usuario) request.getSession().getAttribute("userWorking");
		String nombreNuevo = (String) request.getSession().getServletContext().getAttribute("rendicion.aviso.rename.archivo");

		ActionRedirect redirect = new ActionRedirect(mapping.findForward("successGenerar"));
		redirect.addParameter(ACTION, "mostrarDetalleGastos");
		redirect.addParameter("codigo", frm.getRendicion().getId());
		redirect.addParameter("codMotivo", frm.getRendicion().getCodMotivo());
		redirect.addParameter("estadoRend", frm.getRendicion().getEstado());

		RendicionesService rendicionesService = new RendicionesService(samClient);
		AprobacionesService aprobacionesService = new AprobacionesService(samClient);

		boolean apr = frm.getRendicion().getUsuarioRendicion() != null;
		frm.setUsuario((Usuario) request.getSession().getAttribute("usuario"));

		if (apr) {
			frm.setRendicion(aprobacionesService.getAprobacionesPendientes(
					frm.getRendicion().getId().toString(), "", "", frm.getRendicion().getGlg(), user.getIdUser()).get(0));
		} else {
			frm.setRendicion(rendicionesService.obtenerListadoRendiciones(
					user.getIdUser(), frm.getRendicion().getId().toString(), null, null, null).get(0));
		}

		frm.getRendicion().setUsuarioRendicion(frm.getRendicion().getUsuarioRendicion() != null ? frm.getRendicion().getUsuarioRendicion() : frm.getUsuario().getIdUser());

		if (!validarGastos(frm, rendicionesService, redirect, request, mapping)) {
			return redirect;
		}

		if (frm.getAccion().equals("caratula")) {
			boolean isCaratula = frm.getRendicion().getAdea().isEmpty();
			String iduAdea = obtenerIduAdea(frm, aprobacionesService, isCaratula);

			if (iduAdea == null) {
				return crearRedirectErrorGenerar(request, redirect, "Error al generar IDU y ADEA", mapping);
			}

			CaratulaService servCaratula = new CaratulaService(samClient);
			String[] thubanCod = iduAdea.split(";");

			List<Gastos> gastos = rendicionesService.getGastos(String.valueOf(frm.getRendicion().getId()), "",
					frm.getRendicion().getUsuarioRendicion(), frm.getRendicion().getCodMotivo());

			log.info("Se obtiene template para caratula");
			String html = servCaratula.generarCaratulaTemplate(frm, thubanCod, gastos);
			log.info("Template obtenido: " + html);

			if (!isCaratula) {
				aprobacionesService.cambiarEscanRendicion(String.valueOf(frm.getRendicion().getId()),
						frm.getRendicion().getUsuarioRendicion(), thubanCod[0]);
				frm.getRendicion().setIdu(thubanCod[0]);
				frm.getRendicion().setAdea(thubanCod[1]);
			}

			generateCaratula(html, response, frm, request, samClient);
			request.getSession().setAttribute("msg", "Car\u00e1tula generada con \u00e9xito.");

			return null;
		} else {
			String path = (String) request.getSession().getServletContext().getAttribute("rendicion.aviso.path");
			List<String> errores = ArchivoUtil.grabarArchivos(frm, aprobacionesService, path, nombreNuevo);
			String msg = "Archivos subidos con \u00e9xito.";
			if (!errores.isEmpty()) {
				msg = StringUtils.join(errores.toArray(), "\\n");
				redirect = new ActionRedirect(mapping.findForward(FAILURE_GENERAR));
				redirect.addParameter(ACTION, ERROR_GENERAR);
			}
			request.getSession().setAttribute("msg", msg);
			return redirect;
		}
	}

	private boolean validarGastos(ImagenesForm frm, RendicionesService rendicionesService, ActionRedirect redirect, HttpServletRequest request, ActionMapping mapping) throws Exception {
		List<Gastos> gastos = rendicionesService.getGastos(String.valueOf(frm.getRendicion().getId()), "",
				frm.getRendicion().getUsuarioRendicion(), frm.getRendicion().getCodMotivo());
		if (gastos.isEmpty()) {
			redirect = new ActionRedirect(mapping.findForward(FAILURE_GENERAR));
			redirect.addParameter(ACTION, ERROR_GENERAR);
			String msg = "NO SE PUDO GENERAR CARATULA - La rendicion no tiene gastos cargados";
			request.getSession().setAttribute("msg", msg);
			return false;
		}
		return true;
	}

	private String obtenerIduAdea(ImagenesForm frm, AprobacionesService aprobacionesService, boolean isCaratula) throws Exception {
		if (!isCaratula) {
			return aprobacionesService.obtenerIDU(frm, WM95.DELIM_04_CON_ADEA);
		} else {
			return frm.getRendicion().getIdu() + ";" + frm.getRendicion().getAdea();
		}
	}

	private ActionRedirect crearRedirectErrorGenerar(HttpServletRequest request, ActionRedirect redirect, String msg, ActionMapping mapping) {
		redirect = new ActionRedirect(mapping.findForward(FAILURE_GENERAR));
		redirect.addParameter(ACTION, ERROR_GENERAR);
		request.getSession().setAttribute("msg", msg);
		return redirect;
	}






	private void generateCaratula(String html, HttpServletResponse response,
			ImagenesForm frm, HttpServletRequest request, SAMWebClient samClient)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			CaratulaService servCaratula = new CaratulaService(samClient);
			String codigoBarraPath = (String) request.getSession().getServletContext()
			.getAttribute("rendicion.image.idu");
			File codigoBarrasIdu = servCaratula.createBarcodeImg(codigoBarraPath, frm.getRendicion().getIdu());
			File codigoBarrasAdea = servCaratula.createBarcodeImg(codigoBarraPath, frm.getRendicion().getAdea());
			String imgIdu = IMG_SRC+codigoBarrasIdu+"' width='245px' height='65px' />";
			String imgAdea = IMG_SRC+codigoBarrasAdea+"' width='245px' height='65px'  />";	
			html = html.replace(CaratulaTemplate.REPLACE_IDU, imgIdu);
			html = html.replace(CaratulaTemplate.REPLACE_ADEA, imgAdea);
		
		
			String fileName = "caratulaRendicion_" + frm.getRendicion().getId();
			String fileType = "pdf";

			response.setContentType("application/vnd.pdf");
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ fileName + "." + fileType + "\"");
			OutputStream out = response.getOutputStream();

			Document document = new Document(PageSize.A4);
			PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
			String img = (String) request.getSession().getServletContext()
					.getAttribute("rendicion.image.caratula");
			
			
			log.info("CARTULA - IMG:_ "+ img);
			File fileImg = new File(img);
			String imgLogo = IMG_SRC+img+"' width='250px' height='45px' />";
			html = html.replace(CaratulaTemplate.REPLACE_BBVAIMAGEN, imgLogo);

			String buffer = html;
			log.info("CARATULA - IMG FILE: "+fileImg.getAbsolutePath());
			log.info("CARATULA - IMG FILE EXIST? "+fileImg.exists());
			log.info("CARATULA - IMG IS FILE ? "+fileImg.isFile());
			byte[] imgByte = FileUtils.readFileToByteArray(fileImg);
//			byte[] codigoBarrasByte =  FileUtils.readFileToByteArray(codigoBarras);
			
			Image imagen = Image.getInstance(imgByte);
//			


			document.open();

			imagen.scaleAbsoluteWidth(100f);
//			imagenCodigoBarras.scaleAbsoluteWidth(250f);
//			imagenCodigoBarras.scaleAbsoluteHeight(50f);
//			imagenCodigoBarras.setAbsolutePosition(0, 750);
//		imagenCodigoBarras.setAbsolutePosition(150f, 650f);

			// imagen.setAbsolutePosition(150f, 650f);
//			document.add(imagen);

			// HTMLWorker
			HTMLWorker htmlWorker = new HTMLWorker(document);
			// String str =
			// "Este es el contenido HTML, bien en String o reemplazalo por el contenido del fichero del ejemplo anterior";
			htmlWorker.parse(new StringReader(buffer));

			document.close();
			out.flush();
			out.close();
			codigoBarrasIdu.delete();
			codigoBarrasAdea.delete();

		} catch (Exception e) {
			log.error(e);
			throw new Exception(e);
		}
	}

	private void getAccion(PrintWriter writer, String accion) {
		JSONObject obj = new JSONObject();
		obj.put("accion", accion);
		writer.print(obj);
		writer.flush();
		writer.close();
	}
}
