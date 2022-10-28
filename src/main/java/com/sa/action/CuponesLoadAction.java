package com.sa.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import com.sa.entities.Cupones;
import com.sa.entities.Usuario;
import com.sa.form.CuponesForm;
import com.sa.services.PagosService;

public class CuponesLoadAction extends RestriccionTransaccionAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CuponesForm renForm = (CuponesForm) form;

		Usuario u = (Usuario) request.getSession().getAttribute("userWorking");
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");

		log.info("Entra al action CuponesLoadAction. Usuario (" + user.getIdUser() + ")");

		String cuponGasto = request.getParameter("cg");
		String view = request.getParameter("view");
		String idRendicion = request.getParameter("codigo");
		String idGasto = request.getParameter("idGasto");
		String codMotivo = request.getParameter("codMotivo");
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String feD = df.format(formatter.parse(request.getParameter("feD")));
		String feH = df.format(formatter.parse(request.getParameter("feH")));

		PagosService service = new PagosService(samClient);
		List<Cupones> cupones = new ArrayList<Cupones>();

		try {
			if (view.equalsIgnoreCase("f"))
				cupones = service.getCupones("USU", "MOP", "SU", u.getIdUser().trim(), feD, feH, idRendicion, codMotivo);
			else
				cupones = service.getCuponUnico(idRendicion, idGasto, user.getIdUser(), codMotivo);
			request.setAttribute("message", service.getMsg());
		} catch (Exception e) {
			request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
		}
		
		renForm.setIdGastoRend(idGasto);
		request.setAttribute("Cupones", cupones);
		request.setAttribute("cuponGastoSelect", cuponGasto);
		request.setAttribute("codigo", idRendicion);
		request.setAttribute("idGasto", idGasto);
		renForm.setFechaD(request.getParameter("feD"));
		renForm.setFechaH(request.getParameter("feH"));
		renForm.setCentroCostos(request.getParameter("cCosto"));
		renForm.setCodMotivo(codMotivo);
		request.setAttribute("tipoConsulta", view);
		request.setAttribute("tipoClick", request.getParameter("tipoClick"));

		return mapping.findForward("success");
	}
}