package com.sa.decorator.parametros;

import com.sa.decorator.SumTableDecorator;
import com.sa.entities.parametros.ParametroMotivo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

public class ParametrosMotivoTableDecorator extends SumTableDecorator {

    @Override
    protected String getVerLink() {
        return "";
    }

    @Override
    protected String getEditarLink() {
        PageContext pc = this.getPageContext();
        HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        String contextPath = request.getContextPath();
        ParametroMotivo motivo = (ParametroMotivo) this.getCurrentRowObject();

        String form = "<form method='post' id='edit_" + motivo.getCodigo() + "' action='"
                + contextPath + "/parametrosMotivoDetalle.do' style='display:none;'>"
                + "<input type='hidden' name='codigo' value='" + motivo.getCodigo() + "'/>"
                + "<input type='hidden' name='accion' value='modificacion'/>"
                + "</form>";
        String imgTag = "<img src='" + contextPath + "/images/iconos/editar.png' alt='Modificar' title='Modificar' border='0'/>";
        String editarLink = "<a href='#' onclick='modificarMotivo(\"" + motivo.getCodigo() + "\")'>" + imgTag + "</a>";

        return form + editarLink;
    }

    @Override
    protected String getBorrarLink() {
        PageContext pc = this.getPageContext();
        HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        String contextPath = request.getContextPath();
        ParametroMotivo motivo = (ParametroMotivo) this.getCurrentRowObject();

        String form = "<form method='post' id='delete_" + motivo.getCodigo() + "' action='"
                + contextPath + "/parametrosMotivoDetalle.do' style='display:none;'>"
                + "<input type='hidden' name='codigo' value='" + motivo.getCodigo() + "'/>"
                + "<input type='hidden' name='accion' value='baja'/>"
                + "</form>";
        String imgTag = "<img src='" + contextPath + "/images/iconos/borrar.png' alt='Eliminar' title='Eliminar' border='0'/>";
        String borrarLink = "<a href='#' onclick='eliminarMotivo(\"" + motivo.getCodigo() + "\")'>" + imgTag + "</a>";

        return form + borrarLink;
    }

    @Override
    protected String getDestinatariosLink() {
        return "";
    }

    @Override
    protected String getCuponesLink() {
        return "";
    }

    @Override
    protected String getScanLink() {
        return "";
    }

    @Override
    protected String getCaratulaLink() {
        return "";
    }
}
