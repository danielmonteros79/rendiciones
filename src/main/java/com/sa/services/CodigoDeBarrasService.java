package com.sa.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import ar.com.bbva.web.impl.SAMWebClient;

public class CodigoDeBarrasService {
	private static final Log log = LogFactory.getLog(RendicionesService.class);
	private SAMWebClient client;
	private File imgBarcode;
	private String pathTemp = "F:/Fpven1_e/Usr/Metodologia/Intercambio FSW/SSDD/Para DyD/Java/Sia/Project/EsqueletoAppWeb/sum_00001_3/";

	// private SAMWebClient samClient;

	public CodigoDeBarrasService(SAMWebClient samClient) {
		// TODO Auto-generated constructor stub
		this.client = samClient;

	}
	public void createBarcodeImg(String codigoBarra)
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
//	bean39.setFontSize(8.0d);
	bean39.setIntercharGapWidth(0.75d);

	// Open output file

	File pathReportes = new File(pathTemp);
	codigoBarra = "A123456789";
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

	log.info("Bar Code is generated successfully�");
} catch (Exception ex) {
	log.error(ex);
	throw new ServiceException(
			"Error al obtener path raiz para generar la imagen del codigo de Barra",
			ex);
}

}
}
