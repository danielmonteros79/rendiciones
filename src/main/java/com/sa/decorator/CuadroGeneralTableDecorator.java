package com.sa.decorator;

import com.sa.entities.CuadroGeneral;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

public class CuadroGeneralTableDecorator extends SumTableDecorator {

    @Override
    protected String getVerLink() {
        PageContext pc = this.getPageContext();
        HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        String contextPath = request.getContextPath();

        CuadroGeneral cuadroGral = (CuadroGeneral) this.getCurrentRowObject();
        String imgTag = "<img style=\"cursor:pointer;\"src=\""
                + contextPath
                + "/images/iconos/ver.png\" onClick=\"detalle('"
                + (cuadroGral.getEstado().trim().equals("GLG (ENTRADA)") ? "PGLGE" : cuadroGral.getCodEstado())
                + "')\" alt=\"Ver\" title=\"Ver\" border=\"0\" />";
        return imgTag;
    }

    @Override
    protected String getBorrarLink() {
        return "";
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

    @Override
    protected String getEditarLink() {
        return "";
    }

    protected String getThubanLink() {
        return "";
    }

    protected String getJournalLink() {
        return "";
    }

    @Override
    public String getOpciones() {
        String verLink = this.getVerLink();
        return verLink;
    }
}
