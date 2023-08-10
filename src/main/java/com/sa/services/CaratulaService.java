package com.sa.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

//import sun.util.logging.resources.logging;
import ar.com.bbva.web.impl.SAMWebClient;

import com.sa.entities.Gastos;
import com.sa.form.ImagenesForm;
import com.sa.form.RendicionAvisoForm;
import com.sa.util.CaratulaTemplate;
//import com.sa.util.ParamsConstants;

public class CaratulaService {
	private static final Log log = LogFactory.getLog(AprobacionesService.class);
	private File imgBarcode;
	private SAMWebClient client;
	public CaratulaService(SAMWebClient client) {		
		this.client = client;
	}
	public String generarCaratulaTemplate(RendicionAvisoForm frm, String[] iduAdea, List<Gastos> gastos ) {		
		
		String html = CaratulaTemplate.CARATULA_HTML;
		
		html = this.replaceCabeceraCaratula(frm, html);
		html = this.replaceGastosCaratula(gastos,html);

		
		
		return html;
		
	}
	private String replaceGastosCaratula(List<Gastos> gastos, String html) {		
		String trGastos = "";
		for (Gastos g : gastos) {
			trGastos += "<tr>";
			
			trGastos += "<td>" + g.getDescGasto() + "</td>";
			trGastos += "<td style='text-align:right;'>$" + g.getMonto() + "</td>";
			trGastos += "<td style='text-align:center;'>" + g.getFechagastos() + "</td>";
			trGastos += "<td>" + this.getDescripComprobante(g.getComprobante()) + "</td>";
			trGastos += "<td>" + (g.getTarjeta().equals("N") ? "EFECTIVO" : "TARJ CORP") + "</td>";
			
			trGastos += "</tr>";
		}
		html = html.replace(CaratulaTemplate.REPLACE_GASTOS, trGastos);
		
		return html;
	}
	
	public String getDescripComprobante(String comprobante) {
		if (comprobante.equals("FACTU"))
			return "FACTURA";
		else if (comprobante.equals("MAIL-"))
			return "MAIL";
		else if (comprobante.equals("SCOMP"))
			return "SIN COMPROBANTE";
		else if (comprobante.equals("TICK-"))
			return "TICKET";
		else if (comprobante.equals("FOBL"))
			return "FACTURA OBLIGATORIA";
			
		return comprobante;
	}
	
	private String replaceCabeceraCaratula(RendicionAvisoForm frm, String html) {	
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaHoy = new Date ();
		html = html.replace(CaratulaTemplate.REPLACE_RENDICION, String.valueOf(frm.getRendicion().getId()));
		html = html.replace(CaratulaTemplate.REPLACE_USUARIO, frm.getUsuario().getIdUser() +" - "+frm.getUsuario().getNombre());
		html = html.replace(CaratulaTemplate.REPLACE_CCOSTOS, String.valueOf(frm.getUsuario().getCcostos()));
		html = html.replace(CaratulaTemplate.REPLACE_MOTIVO, frm.getRendicion().getMotivo());
		html = html.replace(CaratulaTemplate.REPLACE_FDESDE, f.format(frm.getRendicion().getFechaDesde()));
		html = html.replace(CaratulaTemplate.REPLACE_FHASTA, f.format(frm.getRendicion().getFechaHasta()));
		html = html.replace(CaratulaTemplate.REPLACE_DESCRIPCION, frm.getRendicion().getDescripcion());
		html = html.replace(CaratulaTemplate.REPLACE_FECHAHOY,f.format(fechaHoy));
		return html;
	}
	public File createBarcodeImg(String pathTemp, String codigoBarra)
	throws ServiceException {


try {
	Code39Bean bean39 = new Code39Bean();
	final int dpi = 200;

	// Configure the barcode generator
	bean39.setModuleWidth(UnitConv.in2mm(6f / dpi));

	bean39.doQuietZone(true);
//	bean39.setChecksumMode(ChecksumMode.CP_IGNORE);
	bean39.setDisplayStartStop(true);
//	bean39.setModuleWidth(0.75);
	bean39.setWideFactor(2.5d);
	bean39.setFontSize(8.0d);
	bean39.setIntercharGapWidth(0.75d);

	// Open output file

	File pathReportes = new File(pathTemp);
	imgBarcode = File.createTempFile(codigoBarra, ".png", pathReportes);

	// File outputFile = new File(barCodePath + fileName + ".JPG");

	FileOutputStream out = new FileOutputStream(imgBarcode);

	// Set up the canvas provider for monochrome PNG output
	BitmapCanvasProvider canvas = new BitmapCanvasProvider(out,
			"image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false,
			0);

	// Generate the barcode
	bean39.generateBarcode(canvas, codigoBarra);

	// Signal end of generation
	canvas.finish();
	out.close();
	log.info("Bar Code is generated successfully ");
//	File file = new File();
	return imgBarcode;

} catch (Exception ex) {
	log.error(ex);
	throw new ServiceException(
			"Error al obtener path raiz para generar la imagen del codigo de Barra. ",
			ex);
}

}
	
}
