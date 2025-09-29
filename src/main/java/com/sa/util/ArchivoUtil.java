package com.sa.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.struts.upload.FormFile;
import org.json.simple.JSONArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sa.entities.Archivo;
import com.sa.entities.Rendicion;
import com.sa.form.RendicionAvisoForm;
import com.sa.services.AprobacionesService;
import com.sa.services.trxs.WM95;

public class ArchivoUtil {
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");

	@SuppressWarnings("unchecked")
	public static void getArchivosASubir(PrintWriter writer, List<Archivo> archivos) {
		JSONArray jArray = new JSONArray();

		for (Archivo archivo : archivos) {
			JSONObject jGroup = new JSONObject();
			jGroup.put("nombre", archivo.getNomArchivo());

			jArray.add(jGroup);
		}

		writer.print(jArray);
		writer.flush();
		writer.close();
	}

	public static Archivo cargarArchivo(PrintWriter out, FormFile formFile) throws Exception {
		JSONObject jsonObject = null;

		Archivo archivo = new Archivo();
		archivo.setNomArchivo(formFile.getFileName());
		archivo.setInputStream(formFile.getInputStream());

		String archivoBase64 = convertInputStreamToBase64(formFile.getInputStream());
		archivo.setBase64File(archivoBase64);
		return archivo;
	}

	
	private static String convertInputStreamToBase64(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        byte[] bytes = outputStream.toByteArray();
        byte[] base64Bytes = Base64.encodeBase64(bytes);

        return new String(base64Bytes,"UTF-8");
    }

	
}
