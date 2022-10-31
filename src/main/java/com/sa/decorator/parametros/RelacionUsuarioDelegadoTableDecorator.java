package com.sa.decorator.parametros;

import com.sa.decorator.SumTableDecorator;
import com.sa.entities.parametros.ParametriaUsuarioDelegado;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

public class RelacionUsuarioDelegadoTableDecorator extends SumTableDecorator {

    @Override
    protected String getVerLink() {
        return "";
    }

    @Override
    protected String getEditarLink() {
        PageContext pc = this.getPageContext();
        HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        String contextPath = request.getContextPath();
        ParametriaUsuarioDelegado params = (ParametriaUsuarioDelegado) this
                .getCurrentRowObject();
        boolean show = params.getDelegadoEstado().equals("A") || params.getDelegadoEstado().equals("I");
        String icon = show ? "editar.png" : "ver.png";
        String title = show ? "Editar" : "Ver";
        String imgTag = "<img src=\""
                + contextPath
                + "/images/iconos/" + icon + "\" alt='" + title + "' title='" + title + "' border=\"0\" />";
        String editarLink = "<a href=\"" + contextPath
                + "/relacionUsuarioDelegadoAlta.do?optn=M&callParam="
                + params.getId() + "\">" + imgTag + "</a>";
        return editarLink;

    }

    @Override
    protected String getBorrarLink() {

        PageContext pc = this.getPageContext();
        HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        String contextPath = request.getContextPath();
        ParametriaUsuarioDelegado params = (ParametriaUsuarioDelegado) this
                .getCurrentRowObject();
        boolean show = params.getDelegadoEstado().equals("A");
        if (show) {
            SimpleDateFormat sdfYMD = new SimpleDateFormat("dd-MM-yyyy");
            String form = "<form method='post' id='delete_"
                    + params.getId() + "' action='" + contextPath
                    + "/execAbmDelegaciones.do' style='display:none;'>"
                    + "<input type='hidden' name='delegadoUser' value='"
                    + params.getDelegadoUser() + "'/>"
                    + "<input type='hidden' name='opcion' value='BAJA'/>"
                    + "<input type='hidden' name='feDesde' value='"
                    + sdfYMD.format(params.getFeDesde()) + "'/>"
                    + "<input type='hidden' name='feHasta' value='"
                    + sdfYMD.format(params.getFeHasta()) + "'/>"
                    + "</form>";
            String imgTag = "<img src='"
                    + contextPath
                    + "/images/iconos/borrar.png' alt='Eliminar' title='Eliminar' border='0'/>";
            String borrarLink = "<a href='#' onclick='eliminar(" + params.getId() + ")'>" + imgTag + "</a>";

            return form + borrarLink;
        } // String imgTag = "<a href=\"#\" onclick=\"eliminar()\"><img src=\""
        // + contextPath
        // +
        // "/images/iconos/borrar.png\" alt=\"Eliminar\" title=\"Eliminar\" border=\"0\" /> </a>";
        // String borrarLink = "<a href=\"" + contextPath
        // + "/listaRendiciones.do?action=delete&codigo=" + 1 + "\">"
        // + imgTag + "</a></td>";
        else {

            return "";
        }
    }

    @Override
    protected String getDestinatariosLink() {
        // PageContext pc = this.getPageContext();
        // HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        // String contextPath = request.getContextPath();
        // Gastos sector = (Gastos) this.getCurrentRowObject();
        // String imgTag =
        // "<a href=\"#\" onclick=\"showDescripcionObligatoriaPopup()\"><img src=\""+contextPath+"/images/iconos/message.png\" alt=\"Descripcion\" title=\"Descripcion\" border=\"0\" /> </a>";
        // String mensaje =
        // "<a href=\""+contextPath+"/descripcionObligatoriaPopup.do?action=descripcionObligatoriaPopup&codigo="+sector.getNroGasto()+"\">"+imgTag+"</a>";
        return "";
    }

    @Override
    protected String getCuponesLink() {
        return "";
    }

    @Override
    protected String getScanLink() {
        // TODO Auto-generated method stub
        return "";
    }

    @Override
    protected String getCaratulaLink() {
        // TODO Auto-generated method stub
        return null;
    }

}
