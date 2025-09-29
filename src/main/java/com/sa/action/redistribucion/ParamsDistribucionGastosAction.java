package com.sa.action.redistribucion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.google.gson.Gson;
import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.ComboGasto;
import com.sa.entities.Usuario;
import com.sa.services.PagosService;
import com.sa.util.ParamsConstants;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class ParamsDistribucionGastosAction extends RestriccionTransaccionAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
		String codMotivo = request.getParameter("codMotivo");

		log.info("Entra al action DistribucionGastosLoadAction. Usuario ("
				+ user.getIdUser() + ")");
		Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
		
		PagosService serv = new PagosService(samClient);
		
		List<ComboGasto> tipoGastos = serv.getComboGasto(ParamsConstants.TIPO_GASTO_OPCION, u.getIdUser(),
				codMotivo);
		request.setAttribute("ComboGastos", tipoGastos);

		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gastos",tipoGastos);

		ArrayList<String> arrayJsons = new ArrayList<>();

		arrayJsons.add(gson.toJson(map));

		response.getWriter().print(arrayJsons);


		response.setContentType("application/json");
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;

	}

	
}