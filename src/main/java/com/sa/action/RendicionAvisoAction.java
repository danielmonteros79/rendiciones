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
import com.sa.form.RendicionAvisoForm;
import com.sa.services.AprobacionesService;
import com.sa.services.CaratulaService;
import com.sa.services.RendicionesService;
import com.sa.services.trxs.WM95;
import com.sa.util.ArchivoUtil;
import com.sa.util.CaratulaTemplate;

public class RendicionAvisoAction extends RestriccionTransaccionAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            SAMWebApplication samApplication, SAMWebClient samClient,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        RendicionAvisoForm frm = (RendicionAvisoForm) form;

        if (request.getParameter("generate") != null) {

            String idRendicion = (String) request.getParameter("rnd");
            log.info("Se procede a reimprimir la cartaula RENDICION:" + idRendicion);
            frm = new RendicionAvisoForm();
            frm.setAccion("caratula");
            frm.setAction("generar");
            frm.setRendicion(new Rendicion());
            frm.getRendicion().setId(Integer.valueOf(idRendicion));
        }
        if ("mostrarPantalla".equals(frm.getAction())) {
            this.mostrarPantalla(frm, request);
        } else if ("generar".equals(frm.getAction())) {
            return this.generar(frm, mapping, request, response, samClient);
        } else if ("cargarArchivo".equals(frm.getAction())) {
            frm.getArchivosASubir().add(
                    ArchivoUtil.cargarArchivo(response.getWriter(), frm.getArchivo()));
            response.setContentType("application/json");
            response.getWriter().flush();
            response.getWriter().close();
        } else if ("borrarArchivo".equals(frm.getAction())) {
            ArchivoUtil.borrarArchivo(request.getParameter("nombreArchivo"),
                    frm.getArchivosASubir());
        } else if ("getAccion".equals(frm.getAction())) {
            this.getAccion(response.getWriter(), frm.getAccion());
        } else if ("getArchivosASubir".equals(frm.getAction())) {
            ArchivoUtil.getArchivosASubir(response.getWriter(), frm
                    .getArchivosASubir());
        }

        return mapping.findForward("success");
    }

    private void mostrarPantalla(RendicionAvisoForm frm, HttpServletRequest request) {
        frm.clean();

        frm.getRendicion().setId(Integer.parseInt(request.getParameter("codigo")));
        frm.getRendicion().setUsuarioRendicion((request.getParameter("usuarioRend")));
        frm.getRendicion().setGlg((request.getParameter("glg")));
    }

    private ActionRedirect generar(RendicionAvisoForm frm,
            ActionMapping mapping, HttpServletRequest request,
            HttpServletResponse response, SAMWebClient samClient)
            throws Exception {
        Usuario user = ((Usuario) request.getSession().getAttribute("userWorking"));
        String nombreNuevo = (String) request.getSession().getServletContext().getAttribute("rendicion.aviso.rename.archivo");
        String msg = "";

        ActionRedirect redirect = new ActionRedirect(mapping.findForward("successGenerar"));
        redirect.addParameter("action", "mostrarDetalleGastos");
        redirect.addParameter("codigo", frm.getRendicion().getId());
        redirect.addParameter("codMotivo", frm.getRendicion().getCodMotivo());
        redirect.addParameter("estadoRend", frm.getRendicion().getEstado());

        RendicionesService rendicionesService = new RendicionesService(samClient);
        AprobacionesService aprobacionesService = new AprobacionesService(samClient);

        boolean apr = frm.getRendicion().getUsuarioRendicion() != null ? true : false;
        frm.setUsuario((Usuario) request.getSession().getAttribute("usuario"));

        if (apr) {
            frm.setRendicion(aprobacionesService.getAprobacionesPendientes(
                    frm.getRendicion().getId().toString(), "", "", frm.getRendicion().getGlg(), user.getIdUser()).get(0));
        } else {
            frm.setRendicion(rendicionesService.obtenerListadoRendiciones(
                    user.getIdUser(), frm.getRendicion().getId().toString(), null, null, null).get(0));
        }

        frm.getRendicion().setUsuarioRendicion(frm.getRendicion().getUsuarioRendicion() != null ? frm.getRendicion().getUsuarioRendicion() : frm.getUsuario().getIdUser());

        // Validacion si tiene gastos la rendicion
        log.info("Se obtienen gastos");
        List<Gastos> gastos = rendicionesService.getGastos(String.valueOf(frm
                .getRendicion().getId()), "", frm.getRendicion()
                        .getUsuarioRendicion(), frm.getRendicion().getCodMotivo());
        if (gastos.size() == 0) {
            redirect = new ActionRedirect(mapping.findForward("failureGenerar"));
            redirect.addParameter("action", "errorGenerar");
            msg = "NO SE PUDO GENERAR CARATULA - La rendicion no tiene gastos cargados";
            request.getSession().setAttribute("msg", msg);
            return redirect;
        }
        // 
        if (frm.getAccion().equals("caratula")) {
            // Verifica si ya tiene codigo adea la rendicion
            boolean isCaratula = frm.getRendicion().getAdea().equalsIgnoreCase(
                    "") ? false : true;
            String iduAdea = null;
            if (!isCaratula) {
                log.info("Se obtiene idu y adea para caratula");
                iduAdea = aprobacionesService.obtenerIDU(frm,
                        WM95.DELIM_04_CON_ADEA);
            } else {
                log
                        .info("Caratula ya generada se obtiene el idu y adea de la rendicion");
                iduAdea = frm.getRendicion().getIdu() + ";"
                        + frm.getRendicion().getAdea();
            }
            if (iduAdea == null) {
                redirect = new ActionRedirect(mapping
                        .findForward("failureGenerar"));
                redirect.addParameter("action", "errorGenerar");
                msg = "Error al generar IDU y ADEA";
                request.getSession().setAttribute("msg", msg);
                return redirect;
            }

            CaratulaService servCaratula = new CaratulaService(samClient);
            String[] thubanCod = iduAdea.split(";");

            log.info("Se obtiene template para caratula");
            String html = servCaratula.generarCaratulaTemplate(frm, thubanCod,
                    gastos);
            log.info("Template obtenido: " + html);
            if (!isCaratula) {
                aprobacionesService.cambiarEscanRendicion(String.valueOf(frm
                        .getRendicion().getId()), frm.getRendicion()
                                .getUsuarioRendicion(), thubanCod[0], thubanCod[1]);
                frm.getRendicion().setIdu(thubanCod[0]);
                frm.getRendicion().setAdea(thubanCod[1]);
            }
            log.info("Se obtiene caratula pdf");
            this.generateCaratula(html, response, frm, request, samClient);
            // request.getSession().setAttribute("templateCaratula", html);
            msg = "Car\u00e1tula generada con \u00e9xito.";

            return null;
        } else {
            String path = (String) request.getSession().getServletContext()
                    .getAttribute("rendicion.aviso.path");
            List<String> errores = ArchivoUtil.grabarArchivos(frm,
                    aprobacionesService, path, nombreNuevo, apr);
            msg = "OK: ARCHIVOS SUBIDOS CON \u00c9XITO.";
            if (errores.size() != 0) {
                msg = StringUtils.join(errores.toArray(), "\\n");
                redirect = new ActionRedirect(mapping.findForward("failureGenerar"));
                redirect.addParameter("action", "errorGenerar");
            }
        }
        if (apr) {
            request.getSession().setAttribute("msg", msg);
            redirect = new ActionRedirect(mapping.findForward("success"));
//			redirect.addParameter("action", "errorGenerar");
//			msg = "Error al generar IDU y ADEA";

            return redirect;

        }

        request.getSession().setAttribute("msg", msg);

        return redirect;
    }

    private void generateCaratula(String html, HttpServletResponse response,
            RendicionAvisoForm frm, HttpServletRequest request, SAMWebClient samClient)
            throws Exception {
        // TODO Auto-generated method stub
        try {
            CaratulaService servCaratula = new CaratulaService(samClient);
            String codigoBarraPath = (String) request.getSession().getServletContext()
                    .getAttribute("rendicion.image.idu");
            File codigoBarrasIdu = servCaratula.createBarcodeImg(codigoBarraPath, frm.getRendicion().getIdu());
            File codigoBarrasAdea = servCaratula.createBarcodeImg(codigoBarraPath, frm.getRendicion().getAdea());
            String imgIdu = "<img src='" + codigoBarrasIdu + "' width='245px' height='65px' />";
            String imgAdea = "<img src='" + codigoBarrasAdea + "' width='245px' height='65px'  />";
            html = html.replace(CaratulaTemplate.REPLACE_IDU, imgIdu);
            html = html.replace(CaratulaTemplate.REPLACE_ADEA, imgAdea);// rf.getHtml();

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

            log.info("CARTULA - IMG:_ " + img);
            File fileImg = new File(img);
            String imgLogo = "<img src='" + img + "' width='250px' height='45px' />";
            html = html.replace(CaratulaTemplate.REPLACE_BBVAIMAGEN, imgLogo);

            String buffer = html;
            log.info("CARATULA - IMG FILE: " + fileImg.getAbsolutePath());
            log.info("CARATULA - IMG FILE EXIST? " + fileImg.exists());
            log.info("CARATULA - IMG IS FILE ? " + fileImg.isFile());
            byte[] imgByte = FileUtils.readFileToByteArray(fileImg);
//			byte[] codigoBarrasByte =  FileUtils.readFileToByteArray(codigoBarras);

            Image imagen = Image.getInstance(imgByte);
//			Image imagenCodigoBarras = Image.getInstance(codigoBarrasByte);

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
