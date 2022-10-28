package com.sa.action.redistribucion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.Days;

import com.google.gson.Gson;
import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.ComboGasto;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import com.sa.services.PagosService;
import com.sa.services.RendicionesService;
import com.sa.util.ParamsConstants;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class ParamsDistribucionGastosAction extends RestriccionTransaccionAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		RendicionForm renForm = (RendicionForm) form;
		Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
		String codMotivo = request.getParameter("codMotivo");
		log.info("Entra al action DistribucionGastosLoadAction. Usuario ("
				+ user.getIdUser() + ")");
		Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
		
		PagosService serv = new PagosService(samClient);
		
		List<ComboGasto> tipoGastos = serv.getComboGasto(ParamsConstants.TIPO_GASTO_OPCION, u.getIdUser(),
				codMotivo);
		request.setAttribute("ComboGastos", tipoGastos);

		JSONObject jsonObject = null;
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		int i = 0;
		map.put("gastos",tipoGastos);
//		for (ComboGasto comboGasto : tipoGastos) {
//			resp.put("comboCodGasto"+i, comboGasto.getId().substring(0,4));
//			resp.put("comboDescGasto"+i, comboGasto.getDescripcion());
//			i++;
//		}
//		resp.put("sizeCombo", i);
		ArrayList<String> arrayJsons = new ArrayList<String>();

		arrayJsons.add(gson.toJson(map));

		response.getWriter().print(arrayJsons);
//		jsonObject = JSONObject.fromObject(resp);
//		response.getWriter().print(jsonObject);

		response.setContentType("application/json");
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;

	}

	
}