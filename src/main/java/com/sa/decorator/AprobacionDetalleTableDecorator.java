package com.sa.decorator;

import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

public class AprobacionDetalleTableDecorator extends SumTableDecorator {

    @Override
    protected String getVerLink() {

        return "";
    }

    @Override
    protected String getEditarLink() {
        PageContext pc = this.getPageContext();
        HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        String contextPath = request.getContextPath();
        Gastos gasto = (Gastos) this.getCurrentRowObject();
        String idRendicion = request.getParameter("codigo");
        String codMotivo = request.getParameter("codMotivo");
        String idGasto = gasto.getIdGasto();
        String estadoRend = request.getParameter("estadoRend").toString();
        String tieneCupon = "";
        String listadoAprob = "1";
        String user = request.getParameter("usuarioRendicion");
        String glg = request.getParameter("glg");
        if (!gasto.getCuponGasto().equalsIgnoreCase("")) {
            tieneCupon = "1";
        }
        request.setAttribute("idGasto", idGasto);
        String imgTag = "<a href=\"#\" onclick=\"showEditarGastoPopup(" + idGasto + ",'" + estadoRend + "','" + tieneCupon + "','" + listadoAprob + "','" + user + "','" + glg + "')\"><img src=\"" + contextPath
                + "/images/iconos/editar.png\" alt=\"Editar\" title=\"Editar\" border=\"0\" /> </a>";
        String editarLink = "<a href=\"" + contextPath
                + "/editarGasto.do?action=editarGasto.do&idGasto=" + idGasto + "&"
                + "codigo=" + idRendicion + "&" + "codMotivo=" + codMotivo + "&estadoRend=" + estadoRend
                + "\">" + imgTag + "</a>";

        return editarLink;

    }

    @Override
    protected String getBorrarLink() {
        return "";

    }

    @Override
    protected String getDestinatariosLink() {
        PageContext pc = this.getPageContext();
        HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        String contextPath = request.getContextPath();
        Gastos gasto = (Gastos) this.getCurrentRowObject();
        Rendicion rend = (Rendicion) request.getAttribute("Rendicion");
        String codMotivo = rend.getCodMotivo();
        String estadoRend = request.getParameter("estadoRend");
        if (gasto.getObsObligatoria().equals("") || !gasto.getObsObligatoria().equals("S")) {
            return "";
        } else {
            String idGasto = gasto.getIdGasto();
            String codGasto = gasto.getNroGasto();
            String codObs = gasto.getObs();
            // if (gasto.getObs()==(""))
            String imgTag = "<img src=\"" + contextPath
                    + "/images/iconos/message.png\" alt=\"Descripcion\" title=\"Descripcion\" border=\"0\" />";
            String mensaje = "<a href=\"#\" onclick=\"showDescripcionObligatoriaPopup("
                    + idGasto + "," + codGasto + ",'" + codObs + "',1,'" + codMotivo + "','" + estadoRend + "')\">"
                    + imgTag + "</a>";
            return mensaje;

        }
    }

    @Override
    protected String getCuponesLink() {
        PageContext pc = this.getPageContext();
        HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        String contextPath = request.getContextPath();
        Gastos gasto = (Gastos) this.getCurrentRowObject();
        if (gasto.getTarjeta().equals("S")) {
            String idGasto = gasto.getIdGasto();
            String fgasto = gasto.getFechagastos();
            String cuponSel = gasto.getCuponGasto().equalsIgnoreCase("") ? "0" : gasto.getCuponGasto();
            String imgTag = "<a href=\"#\" onclick=\"showCuponesTarjetasPopup(" + idGasto + ",'" + cuponSel + "','" + fgasto + "','t')\"><img src=\"" + contextPath
                    + "/images/iconos/creditcards.png\" alt=\"Cupones\" title=\"Cupones\" border=\"0\" /> </a>";
            String mensaje2 = "<a href=\"" + contextPath + "/cuponesPopup.do?action=cuponesPopup&view=f&gasto="
                    + gasto.getNroGasto() + "\">" + imgTag + "</a></td>";
            return mensaje2;
        } else {
            return "";
        }
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
