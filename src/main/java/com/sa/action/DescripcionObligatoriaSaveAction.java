package com.sa.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.entities.Usuario;
import com.sa.form.DescripcionObligatoriaForm;
import com.sa.services.PagosService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class DescripcionObligatoriaSaveAction extends RestriccionTransaccionAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
			SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DescripcionObligatoriaForm descForm = (DescripcionObligatoriaForm) form;
		descForm.reset(mapping, request);
		PagosService service = new PagosService(samClient);
		Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");

		log.info("Entra al action DescripcionObligatoriaSaveAction. Usuario ("+user.getIdUser()+")");

		SimpleDateFormat sdfDMY = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");

		String txt1 = descForm.getTXT1() == null ? "" : descForm.getTXT1();
		String txt2 = descForm.getTXT2() == null ? "" : descForm.getTXT2();
		String txt250 = descForm.getTXT250() == null ? "" : descForm.getTXT250();
		String num1 = descForm.getNUM1() == null ? "" : descForm.getNUM1().toString();
		String num2 = descForm.getNUM2() == null ? "" : descForm.getNUM2().toString();
		String cod1 = descForm.getCOD1() == null ? "" : StringUtils.leftPad(descForm.getCOD1(), 5, "0");
		String cod2 = descForm.getCOD2() == null ? "" : StringUtils.leftPad(descForm.getCOD2(), 5, "0");
		String fecha1 = "";
		String fecha2 = "";
		
		if (descForm.getFEC1() != null && !descForm.getFEC1().equals(""))
			fecha1 = sdfYMD.format(sdfDMY.parse(descForm.getFEC1()));
		
		if (descForm.getFEC2() != null && !descForm.getFEC2().equals(""))
			fecha2 = sdfYMD.format(sdfDMY.parse(descForm.getFEC2()));
		
		try{
			log.info("Se envian los parametros para guardar el detalle obligatorio");
			service.addDescripcionObligatoria(descForm.getIdRendicion(), descForm.getIdGasto(),
					descForm.getCodGasto(), descForm.getCodDetOblig(),
					txt1, txt2, num1, num2, cod1, cod2, txt250, fecha1, fecha2);
			request.setAttribute("messageModifTCJP", "LUEGO DE CARGAR TODAS LAS OBSERVACIONES, PRESIONE SALIR");
		} catch (Exception e) {
			log.error(e);
			request.setAttribute("messageModifTCJP", "ERROR AL GENERAR LA DESCRIPCION: " + e.getCause().getMessage());
		}
		return mapping.findForward("success");
	}

}
