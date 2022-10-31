package com.sa.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontProvider;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.ChainedProperties;
import com.itextpdf.text.html.simpleparser.ImageProvider;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import com.sa.services.AprobacionesService;
import com.sa.services.RendicionesService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class RendicionScanSaveAction extends RestriccionTransaccionAction {

    private static final Log log = LogFactory
            .getLog(RendicionScanSaveAction.class);
    private RendicionForm rf;

    // private Integer idu = null;
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            SAMWebApplication samApplication, SAMWebClient samClient,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // TODO Auto-generated method stub
        samClient.setAttribute("userLoggin", "XA01073");
        Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
        log.info("Entra al action RendicionScanSaveAction. Usuario (" + user.getIdUser() + ")");
        rf = (RendicionForm) form;
        String idRendicion = request.getParameter("codigo");

        AprobacionesService service = new AprobacionesService(samClient);

        String idu = "23232323";//service.obtenerIDU(rf);

        if (idu != null) {

            RendicionesService svc = new RendicionesService(samClient);

            /* VERIFICAR EL ABM SU54. */
//			svc.cambiarEstadoScann(user.getIdUser(), rf, "", "", "");
            try {
                String buffer = this.genHtml();// rf.getHtml();
                String fileName = rf.getNameFile();
                String fileType = rf.getFileType();
                // String filePath = rf.getFilePath() == null ? "" :
                // rf.getFilePath();

                response.setContentType("application/vnd.pdf");
                response.setHeader("Content-Disposition",
                        "attachment;filename=\"" + fileName + "." + fileType
                        + "\"");
                OutputStream out = response.getOutputStream();

                Document document = new Document(PageSize.A4.rotate(), 10, 10,
                        10, 10);

                PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
                document.open();

                InputStream stream = new ByteArrayInputStream(buffer
                        .getBytes("utf-8"));
                XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
                worker.parseXHtml(pdfWriter, document, stream, Charset
                        .forName("utf-8"));
                document.close();
                pdfWriter.flush();
                pdfWriter.close();

                out.close();

                // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // Graba el PDF sin abrirlo en la ruta indicada.
                // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // Document documento = new Document(PageSize.A4.rotate(), 10,
                // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // 10,
                // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // 10,
                // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // 10);
                //
                // FileOutputStream ficheroPdf;
                //
                // try
                // {
                // ficheroPdf = new
                // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // FileOutputStream(rf.getFilePath()+rf.getNameFile()+rf.getFileType());
                // pdfWriter = PdfWriter.getInstance(document, ficheroPdf);
                // PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);
                // }
                // catch (Exception ex)
                // {
                // System.out.println(ex.toString());
                // }
                //
                // try{
                // documento.open();
                // InputStream st = new
                // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // ByteArrayInputStream(buffer.getBytes("utf-8"));
                // XMLWorkerHelper wo = XMLWorkerHelper.getInstance();
                // wo.parseXHtml(pdfWriter, documento, st,
                // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // Charset.forName("utf-8"));
                // documento.close();
                // pdfWriter.flush();
                // pdfWriter.close();
                // documento.close();
                //
                // }catch(Exception ex){
                // System.out.println(ex.toString());
                // }
                // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            } catch (Exception e) {
                log.error(e);
                throw new Exception(e);
            }

            return null;
        }
        request.setAttribute("error", "No se pudo obtener el IDU");
        return mapping.findForward("failure");
    }

    private String genHtml() {
        // TODO Auto-generated method stub
        String ret = "<html> <head>"
                + "<table border='0' width='700'>"
                + "<tr>"
                + "<td  align='left' style='font-weight: bold;padding:1px;'> <h1>img  </h1></td>"
                + "<td  align='right' style='font-weight: bold;vertical-align:top'> "
                + "	<table border='1' width='350'>"
                + "		<tr><td align='center' style='vertical-align:top;'> <h2>RENDICION DE GASTOS aaaaaaaa </br> </h2> <h4> NRO: 1231212312131318 </h4></td></tr>"
                + "	</table>"
                + "</td>"
                + " </tr>"
                + "</table> "
                + "</head> "
                + "<body> "
                + "<table width='700'>"
                + "<tr>"
                + "	<td width='26%' style='font-weight: bold;padding:5px;'>USUARIO: </td>"
                + "	<td width='74%' align='left'> replace usuario</td>"
                + "  </tr>"
                + "  <tr>"
                + "	<td style='font-weight: bold;padding:5px;'>CCOSTOS:</td>"
                + "	<td>replace centro costos</td>"
                + "  </tr>"
                + "  <tr>"
                + "	<td style='font-weight: bold;padding:5px;'>MOTIVO RENDICION:</td>"
                + "	<td>replace motivo</td>"
                + "  </tr>"
                + "  <tr>"
                + "	<td style='font-weight: bold;padding:6px;'>INTERVALO FECHAS</td>"
                + "	<td> replace fecha</td>"
                + "  </tr>"
                + "  <tr>"
                + "	<td style='font-weight: bold;padding:5px;'>DESCRIPCION</td>"
                + "	<td > replace fecha</td>"
                + "  </tr>"
                + "</table>"
                + "<br> <h4> GASTOS: </h4>"
                + "<table width='700'>"
                + "	<tr>"
                + "	<td align='left' style='vertical-align:top;'> Peajes </td>"
                + "	<td align='left' style='vertical-align:top;'> $400 </td>"
                + "	<td align='left' style='vertical-align:top;'> 01/05/2016 </td>"
                + "	<td align='left' style='vertical-align:top;'> COMPROBANTE </td>"
                + "	<td align='left' style='vertical-align:top;'> EFECTIVO </td>		"
                + "</tr>"
                + "<tr></tr>"
                + "</table>"
                + "<br>"
                + "</body> "
                + "<table border='0' width='700'>"
                + "<tr>"
                + "	<td  align='left' style='font-weight: bold;padding:1px;'> "
                + "<table border='1' width='300'>"
                + "		<tr><td align='center' style='vertical-align:top;'>Informacion para scaneo </td></tr>"
                + "	</table></td>"
                + "<td  align='left' style='font-weight: bold;padding:1px;'> "
                + "<table width='100'>"
                + "	<tr><td align='center' style='vertical-align:top;'> </td></tr>"
                + "					</table></td>"
                + "<td  align='right' style='font-weight: bold;vertical-align:top'> "
                + "	<table border='1' width='300'>"
                + "		<tr><td align='center' style='vertical-align:top;'>Informacion para archivo </td></tr>"
                + "		</table>" + "				</td>" + "   </tr>" + "</table>"
                + "</html>";
        return ret;
    }

    /**
     * Inner class implementing the ImageProvider class. This is needed if you
     * want to resolve the paths to images.
     */
    public static class MyImageFactory implements ImageProvider {

        public Image getImage(String src, Map<String, String> h,
                ChainedProperties cprops, DocListener doc) {
            try {
                return Image.getInstance(String.format("resources/posters/%s",
                        src.substring(src.lastIndexOf("/") + 1)));
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Inner class implementing the FontProvider class. This is needed if you
     * want to select the correct fonts.
     */
    public static class MyFontFactory implements FontProvider {

        public Font getFont(String fontname, String encoding, boolean embedded,
                float size, int style, BaseColor color) {
            return new Font(FontFamily.TIMES_ROMAN, size, style, color);
        }

        public boolean isRegistered(String fontname) {
            return false;
        }
    }

}
