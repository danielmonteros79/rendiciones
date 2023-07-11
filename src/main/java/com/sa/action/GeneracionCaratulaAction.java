package com.sa.action;

import java.io.OutputStream;
import java.io.StringReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.sa.entities.Usuario;
import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class GeneracionCaratulaAction extends RestriccionTransaccionAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
		log.info("Entra al action GeneracionCaratulaAction. Usuario ("+user.getIdUser()+")");
			try {
				String buffer = (String) request.getSession().getAttribute("templateCaratula");//this.genHtml();// rf.getHtml();
				String fileName = "Rendicion";
				String fileType = "pdf";

				
				response.setContentType("application/vnd.pdf");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"" + fileName + "." + fileType
								+ "\"");
				OutputStream out = response.getOutputStream();
				// FileOutputStream fout = new
				// FileOutputStream("plantilla.pdf");

				
				Document document = new Document(PageSize.A4);
				PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
				Image imagen = Image
				.getInstance("F:/Fpven1_e/Usr/Metodologia/Intercambio FSW/SSDD/Para DyD/Java/Rendiciones/Project/workspace/RendicionesGastos/WebContent/images/logoBBVA.png");

				document.open();
//				document.addAuthor("Persona creadora");
//				document.addCreator("Software generador");
//				document.addCreationDate();
//				document.addTitle("Titulo del documento");
				imagen.scaleAbsoluteWidth(100f);
//				imagen.setAbsolutePosition(150f, 650f);
				// document.add(parrafo);
				document.add(imagen);
				// HTMLWorker
				HTMLWorker htmlWorker = new HTMLWorker(document);
				// String str =
				// "Este es el contenido HTML, bien en String o reemplazalo por el contenido del fichero del ejemplo anterior";
				htmlWorker.parse(new StringReader(buffer));
				
				document.close();
				out.close();

			} catch (Exception e) {
				log.error(e);
				throw new Exception(e);
			}

			return null;
		
//		return mapping.findForward("mostrarDetalleGastos");

	}

	private String genHtml() {
		// TODO Auto-generated method stub
		String ret = "<html> <head>"
				+ "<table border='0' width='500'>"
				+ "<tr>"
				+ "<td  align='left' style='font-weight: bold;padding:1px;'>  </td>"
				+ "<td  align='right' style='font-weight: bold;vertical-align:top'> "
				+ "	<table border='1' width='250'>"
				+ "		<tr><td align='center' style='vertical-align:top;'> <h2>RENDICION DE GASTOS </br> </h2> <h4> NRO: <!-- idRendicion --> </h4></td></tr>"
				+ "	</table>"
				+ "</td>"
				+ " </tr>"
				+ "</table> "
				+ "</head> "
				+ "<body> "
				+ "<table width='500'>"
				+ "<tr>"
				+ "	<td width='26%' style='font-weight: bold;padding:5px;'>USUARIO: </td>"
				+ "	<td width='74%' align='left'> <!-- usuario --> </td>"
				+ "  </tr>"
				+ "  <tr>"
				+ "	<td style='font-weight: bold;padding:5px;'>CCOSTOS:</td>"
				+ "	<td> <!-- ccostos --> </td>"
				+ "  </tr>"
				+ "  <tr>"
				+ "	<td style='font-weight: bold;padding:5px;'>MOTIVO RENDICION:</td>"
				+ "	<td><!-- motivo --></td>"
				+ "  </tr>"
				+ "  <tr>"
				+ "	<td style='font-weight: bold;padding:6px;'>INTERVALO FECHAS</td>"
				+ "	<td> <!-- fechaDesde --> - <!-- fechaHasta --></td>"
				+ "  </tr>"
				+ "  <tr>"
				+ "	<td style='font-weight: bold;padding:5px;'>DESCRIPCION</td>"
				+ "	<td > <!-- descripcion --> </td>"
				+ "  </tr>"
				+ "</table>"
				+ "<br> <h4> GASTOS: </h4>"
				+ "<table width='600'>"
				+ "	<tr> <!-- gastos -->"
//				+ "	<td align='left' style='vertical-align:top;'> Peajes </td>"
//				+ "	<td align='left' style='vertical-align:top;'> $400 </td>"
//				+ "	<td align='left' style='vertical-align:top;'> 01/05/2016 </td>"
//				+ "	<td align='left' style='vertical-align:top;'> COMPROBANTE </td>"
//				+ "	<td align='left' style='vertical-align:top;'> EFECTIVO </td>		"
//				+ "</tr>"

				+ "<tr></tr>"
				+ "</table>"
				+ "<br>"
				+ "</body> "
				+ "<table border='0' width='500'>"
				+ "<tr>"
				+ "	<td  align='left' style='font-weight: bold;padding:1px;'> "
				+ "<table border='1' width='200'>"
				+ "		<tr><td align='center' style='vertical-align:top;'>Informacion para scaneo </td></tr>"
				+ "	</table></td>"
				+ "<td  align='left' style='font-weight: bold;padding:1px;'> "
				+ "<table width='100'>"
				+ "	<tr><td align='center' style='vertical-align:top;'> </td></tr>"
				+ "					</table></td>"
				+ "<td  align='right' style='font-weight: bold;vertical-align:top'> "
				+ "	<table border='1' width='200'>"
				+ "		<tr><td align='center' style='vertical-align:top;'>Informacion para archivo </td></tr>"
				+ "		</table>" + "				</td>" + "   </tr>" + "</table>"
				+ "</html>";
		return ret;
	}
}