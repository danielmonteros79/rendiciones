package com.sa.decorator;

import com.sa.entities.Gastos;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

public class ComentariosDecorator extends SumTableDecorator {

    @Override
    protected String getVerLink() {
        return null;
    }

    @Override
    protected String getEditarLink() {
        return null;

    }

    @Override
    protected String getBorrarLink() {
        return null;

    }

    @Override
    protected String getDestinatariosLink() {
        PageContext pc = this.getPageContext();
        HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        String contextPath = request.getContextPath();
        Gastos sector = (Gastos) this.getCurrentRowObject();
        String imgTag = "<a href=\"#\" onclick=\"showDescripcionObligatoriaPopup()\"><img src=\"" + contextPath + "/images/iconos/message.png\" alt=\"Descripcion\" title=\"Descripcion\" border=\"0\" /> </a>";
        String mensaje = "<a href=\"" + contextPath + "/descripcionObligatoriaPopup.do?action=descripcionObligatoriaPopup&codigo=" + sector.getNroGasto() + "\">" + imgTag + "</a>";
        return mensaje;
    }

    @Override
    protected String getCuponesLink() {
        return null;
    }

    @Override
    protected String getScanLink() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getCaratulaLink() {
        // TODO Auto-generated method stub
        return null;
    }
}
