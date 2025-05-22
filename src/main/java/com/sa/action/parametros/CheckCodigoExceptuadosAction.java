package com.sa.action.parametros;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.Usuario;
import com.sa.services.ParametrosService;
import com.sa.util.ParamsConstants;

public class CheckCodigoExceptuadosAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(CheckCodigoExceptuadosAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
		ParametrosService service = new ParametrosService(samClient);
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		String codExcept = request.getParameter("desMotivo").trim();
		String marca = request.getParameter("motivoUsuario").trim();
		
		
		
		JSONObject jsonObject = null;
		Map<String, Object> resp = new HashMap<String, Object>();
		String error = "";
try {
			if(marca.equals("motivo"))
				marca = "M";
			if(marca.equals("usuario"))
				marca = "U";
					
			String descripcion = service.getCodigoExceptuado(user.getIdUser(), codExcept, marca);
			if(descripcion.equals(null)){
				error = "No existe el codigo ingresado";
			}else{
				resp.put("descripcion", descripcion);
//				resp.put("delegadoCentroCosto", usuarioCheck.getCcostos());
//				resp.put("delegadoSector", usuarioCheck.getSector());
				error = "";
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e);
			error = e.getMessage().substring(e.getMessage().indexOf(":")+1);
		}
		resp.put("error", error);
		
		jsonObject = JSONObject.fromObject(resp);
		response.getWriter().print(jsonObject);

		response.setContentType("application/json");
		response.getWriter().flush();
		response.getWriter().close();

		return null;
	}
}