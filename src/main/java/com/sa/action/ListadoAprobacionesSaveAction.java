package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.AprobacionForm;
import com.sa.services.AprobacionesService;
import com.sa.services.RendicionesService;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListadoAprobacionesSaveAction extends RestriccionTransaccionAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String estado = "APROB";

        log.info("Se ingresa la accion de aprobar rendiciones...");
        AprobacionForm aprobacionForm = (AprobacionForm) form;
        RendicionesService service = new RendicionesService(samClient);
        Usuario user = (Usuario) request.getSession().getAttribute("usuario");
        Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
        log.info("Entra al action ListadoAprobacionesSaveAction. Usuario (" + user.getIdUser() + ")");

        Usuario userAprob = user;
        /**
         * Se verifica si esta delegando y tiene facultades para aprobar.
         */
        if (!user.getIdUser().equalsIgnoreCase(u.getIdUser())) {
            if (u.isManejaFacultades()) {
                log.info("El usuario: " + u.getIdUser() + " tiene facultades para poder aprobar el usuario: " + u.getIdUser());
                userAprob = u;
            }
        }

        log.info(aprobacionForm.getGlg());
        AprobacionesService aprobacionesservice = new AprobacionesService(samClient);
        Integer idRendicion = null;
        String codigo = request.getParameter("codigo");
        // String estado = aprobacionForm.getEstado();
        @SuppressWarnings("unchecked")
        Enumeration<String> params = request.getParameterNames();
        List<Rendicion> rendicionesSeleccionadas = new ArrayList<Rendicion>();

        if (codigo == null) {
            try {
                while (params.hasMoreElements()) {

                    int i = 0;
                    String param = params.nextElement();
                    if (param.length() > 4) {
                        if (param.substring(0, 11).equalsIgnoreCase("idRendicion")) {

                            Rendicion selectR = new Rendicion();
                            Boolean selecciono = Boolean
                                    .valueOf(request.getParameter("seleccionado" + param.substring(11, param.length())));
                            if (selecciono) {
                                selectR.setId(Integer.valueOf(request.getParameter(param)));
                                rendicionesSeleccionadas.add(selectR);

                            }
                        }
                        i++;
                    }
                }
                log.info("se llama al service que realiza el cambio de estado a las rendiciones (APROB)");
                String aviso = aprobacionesservice.cambiarEstadoRendiciones(userAprob.getIdUser(), rendicionesSeleccionadas, "APROB", null,
                        request.getParameter("glg"));
                if (rendicionesSeleccionadas.size() == 1) {
                    if (aviso != null && !aviso.equalsIgnoreCase("OPERACION EFECTUADA")) {
                        request.setAttribute("messageModifTCJP", aviso);
                    } else {
                        request.setAttribute("messageModifTCJP", "OK: APROBO CORRECTAMENTE LA RENDICION ");
                    }
                } else {
                    if (aviso != null && !aviso.equalsIgnoreCase("") && !aviso.equalsIgnoreCase("OPERACION EFECTUADA")) {
                        request.setAttribute("messageModifTCJP", aviso);
                    } else {
                        request.setAttribute("messageModifTCJP", "OK: APROBO CORRECTAMENTE LAS RENDICIONES ");
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
                log.error(e);
                request.setAttribute("messageModifTCJP", "ERROR AL APROBAR: " + e.getCause().getMessage());
            }
        } else {

            if (codigo != null) {
                aprobacionForm.setId(Integer.valueOf(codigo));
            }

            idRendicion = aprobacionForm.getId();
            if (aprobacionForm.getEstado().equals("1")) {
                estado = "APROB";
            } else {
                estado = "RECHA";
            }
            log.info("se llama al service que realiza el cambio de estado a la rendicion (APROB o RECHA)");
            try {
                if (estado.equalsIgnoreCase("APROB")) {
                    String aviso = aprobacionesservice.cambiarEstadoDeUnaRendicion(userAprob.getIdUser(), idRendicion, estado,
                            aprobacionForm.getMotivoRechazo(), request.getParameter("glg"));
                    if (aviso != null && !aviso.equalsIgnoreCase("") && !aviso.equalsIgnoreCase("OPERACION EFECTUADA")) {
                        request.setAttribute("messageModifTCJP", aviso);
                    } else {
                        request.setAttribute("messageModifTCJP", "OK: APROBO CORRECTAMENTE LA RENDICION ");
                    }
                } else {
                    String aviso = aprobacionesservice.cambiarEstadoDeUnaRendicion(userAprob.getIdUser(), idRendicion, estado,
                            aprobacionForm.getCmboMotivo() + " - " + aprobacionForm.getMotivoRechazo(), request.getParameter("glg"));
                    if (aviso != null && !aviso.equalsIgnoreCase("") && !aviso.equalsIgnoreCase("OPERACION EFECTUADA")) {
                        request.setAttribute("messageModifTCJP", aviso);
                    } else {
                        request.setAttribute("messageModifTCJP", "OK: RECHAZO CORRECTAMENTE LA RENDICION ");
                    }
                }

            } catch (Exception e) {
                // TODO: handle exception
                log.error(e);
                if (estado.equalsIgnoreCase("APROB")) {

                    request.setAttribute("messageModifTCJP", "ERROR AL APROBAR: " + e.getCause().getMessage());
                } else {
                    request.setAttribute("messageModifTCJP", "ERROR AL RECHAZAR: " + e.getCause().getMessage());
                }
            }

        }

        List<ComboMotivo> motivo = service.getMotivoRendiciones("4", u.getIdUser());
        request.setAttribute("ComboMotivo", motivo);
        request.setAttribute("trxOk", "ok");
        if (estado.equalsIgnoreCase("APROB")) {
            if (codigo == null) {
                return mapping.findForward("success");
            } else {
                return mapping.findForward("aprobacionPopUp");
            }
        } else {
            return mapping.findForward("rechazoPopUp");
        }
    }
}
