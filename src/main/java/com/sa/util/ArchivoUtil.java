package com.sa.util;

import java.io.File;
import java.io.FileOutputStream;
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
		Map<String, Object> resp = new HashMap<String, Object>();
		resp.put("nombreArchivo", formFile.getFileName());
		System.out.println(resp.get("nombreArchivo"));
		jsonObject = JSONObject.fromObject(resp);
		out.print(jsonObject);

		return archivo;
	}

	public static void borrarArchivo(String json, List<Archivo> archivos) throws Exception {
		byte[] parameterByte = json.getBytes("ISO-8859-15");
		String jsonEnc = new String(parameterByte, "UTF-8");
		GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("dd/MM/yyyy");
		Gson gson = gsonBuilder.create();
		String nombreArchivo = gson.fromJson(jsonEnc, String.class);

		boolean encontro = false;
		for (int i = 0; i < archivos.size() && encontro == false; i++) {
			Archivo archivo = archivos.get(i);

			if (archivo.getNomArchivo().equals(nombreArchivo)) {
				encontro = true;
				archivos.remove(i);
			}
		}
	}

	public static List<String> grabarArchivos(RendicionAvisoForm form, AprobacionesService aprobacionesService,
			String path, String nombreNuevo, boolean apr) throws Exception {
		List<String> errores = new ArrayList<String>();
		Rendicion rend = new Rendicion();
		rend = form.getRendicion();
		String idu = "";

		idu = aprobacionesService.obtenerIDU(form, WM95.DELIM_04_SIN_ADEA);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		for (Archivo archivo : form.getArchivosASubir()) {
			String error = "";
			
			cal.add(Calendar.SECOND, 1);
			Date fecha = cal.getTime();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
			String fechaString = "";

			fechaString = df.format(fecha).trim();
			String cCostos = "";
			String idRend = "";

			Integer cc = form.getUsuario().getCcostos();
			if (cc != 0) {
				cCostos = String.format("%04d", cc);
			}

			if (rend.getId() != null) {
				idRend = String.format("%010d", rend.getId());
			}

			if (idu.equals(""))
				error = "Error al obtener IDU del archivo " + archivo.getNomArchivo();
			else {
				archivo.setIdu(idu.substring(0, idu.indexOf(";")));

				String ext = archivo.getNomArchivo().substring(archivo.getNomArchivo().lastIndexOf("."));

				error = copyFile(archivo, nombreNuevo + (String) archivo.getIdu() + "_" + form.getUsuario().getIdUser()
						+ "_" + cCostos + "_" + fechaString + ext, path);
			}
			if (!error.equals(""))
				errores.add(error);
		}
		if (errores.size() == 0) {
			try {
				if(!apr) {
				aprobacionesService.cambiarEscanRendicion(String.valueOf(form.getRendicion().getId()),
						form.getRendicion().getUsuarioRendicion(), idu, null);}
			} catch (Exception e) {
				e.printStackTrace();
				errores.add(e.getCause().getMessage());
			}
		}
		return errores;
	}

	private static String copyFile(Archivo archivo, String newFileName, String uploadPath) {
		String error = "";

		File file = new File(uploadPath, newFileName);

		try {
			FileOutputStream fout = null;
			fout = new FileOutputStream(file);
			byte data[] = new byte[1024];
			int count;
			while ((count = archivo.getInputStream().read(data, 0, 1024)) != -1) {
				fout.write(data, 0, count);
			}
			fout.close();
			archivo.getInputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
			error = " Error en la grabacion o lectura del archivo " + archivo.getNomArchivo();
		}

		return error;
	}
}
