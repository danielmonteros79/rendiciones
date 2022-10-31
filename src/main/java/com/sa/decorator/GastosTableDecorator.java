package com.sa.decorator;

import com.sa.entities.Gastos;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

public class GastosTableDecorator extends SumTableDecorator {

    @Override
    protected String getVerLink() {
        // PageContext pc = this.getPageContext();
        // HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        // String contextPath = request.getContextPath();
        //
        // Gastos gasto = (Gastos) this.getCurrentRowObject();
        // String imgTag = "<a href=\"#\" onclick=\"nofunciona()\"><img src=\""
        // + contextPath
        // +
        // "/images/iconos/ver.png\" alt=\"Ver\" title=\"Ver\" border=\"0\" /></a>";
        // String verLink = "<a href=\"" + contextPath +
        // "/mostrarDetalleGastos.do?action=mostrarDetalleGastos&gasto="
        // + gasto.getNroGasto() + "\">" + imgTag + "</a>";
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

        String estadoRend = (String) request.getAttribute("estadoRend");
        String tieneCupon = "";
        if (!gasto.getCuponGasto().equalsIgnoreCase("")) {
            tieneCupon = "1";
        }

        request.setAttribute("idGasto", idGasto);
        String imgTag = "<a href=\"#\" onclick=\"showEditarGastoPopup(" + idGasto + ",'" + estadoRend + "','" + tieneCupon
                + "')\"><img src=\"" + contextPath + "/images/iconos/editar.png\" alt=\"Editar\" title=\"Editar\" border=\"0\" /> </a>";
        String editarLink = "<a href=\"" + contextPath + "/editarGasto.do?action=editarGasto.do&idGasto=" + idGasto + "&" + "codigo="
                + idRendicion + "&" + "codMotivo=" + codMotivo + "&estadoRend=" + estadoRend + "\">" + imgTag + "</a>";
        if (estadoRend.equalsIgnoreCase("APROB") || estadoRend.equalsIgnoreCase("RECHA") || estadoRend.equalsIgnoreCase("ORDPG")
                || estadoRend.equalsIgnoreCase("SUSPE") || !estadoRend.equalsIgnoreCase("PENDI")) {
            return "";
        } else {
            return editarLink;
        }
    }

    @Override
    protected String getBorrarLink() {
        Gastos sector = (Gastos) this.getCurrentRowObject();

        PageContext pc = this.getPageContext();
        HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        String contextPath = request.getContextPath();
        Gastos gasto = (Gastos) this.getCurrentRowObject();
        String idRendicion = request.getParameter("codigo");
        String codMotivo = request.getParameter("codMotivo");
        String idGasto = gasto.getIdGasto();
        String estadoRend = (String) request.getAttribute("estadoRend");
        // String estadoRendicion =
        // request.getParameter("estadoRend").toString();
        String imgTag = "<a href=\"#\" onclick=\"eliminarGasto(" + idGasto + ",'" + estadoRend + "')\"><img src=\"" + contextPath
                + "/images/iconos/borrar.png\" alt=\"Eliminar\" title=\"Eliminar\" border=\"0\" /> </a>";
        String borrarLink = "<a href=\"" + contextPath + "/bajaGasto.do?action=bajaGasto.do&idGasto=" + idGasto + "&" + "codigo="
                + idRendicion + "&" + "codMotivo=" + codMotivo + "\">" + imgTag + "</a>";
        if (estadoRend.equals("PENDI") || estadoRend.equals("PENDI")) {
            return borrarLink;
        } else {
            return "";
        }

    }

    @Override
    protected String getDestinatariosLink() {
        PageContext pc = this.getPageContext();
        HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        String contextPath = request.getContextPath();
        Gastos gasto = (Gastos) this.getCurrentRowObject();
        String codMotivo = request.getParameter("codMotivo");
        String estadoRend = (String) request.getAttribute("estadoRend");
        String readonly = (String) request.getAttribute("readonly");
        if (gasto.getObsObligatoria().equals("") || gasto.getObs().equals("00000")) {
            return "";
        } else {
            String tipoEntrada = "";
            if ((estadoRend.equalsIgnoreCase("PENDI") || estadoRend.equalsIgnoreCase("OBSER")) && readonly == null) {
                tipoEntrada = "2";
            } else {
                tipoEntrada = "1";
            }
            String idGasto = gasto.getIdGasto();
            String codGasto = gasto.getNroGasto();
            String codObs = gasto.getObs();
            String imgTag = "<a href=\"#\" onclick=\"showDescripcionObligatoriaPopup(" + idGasto + "," + codGasto + ",'" + codObs + "',"
                    + tipoEntrada + ",'" + codMotivo + "','" + estadoRend + "')\"><img src=\"" + contextPath
                    + "/images/iconos/message.png\" alt=\"Descripcion\" title=\"Descripcion\" border=\"0\" /> </a>";
            String mensaje = "<a href=\"" + contextPath + "/descripcionObligatoriaPopup.do?action=descripcionObligatoriaPopup&gasto="
                    + gasto.getNroGasto() + "\">" + imgTag + "</a>";
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
            String tipoView = "";
            String codigoMotivo = request.getParameter("codMotivo");
//			if (request.getParameter("estadoRend").equalsIgnoreCase("PENDI")) {
//				tipoView = "f";
//			} else {
            tipoView = "t";
//			}
            String imgTag = "<a href=\"#\" onclick=\"showCuponesTarjetasPopup(" + idGasto + ",'" + cuponSel + "','" + fgasto + "','"
                    + tipoView + "','" + codigoMotivo + "')\"><img src=\"" + contextPath
                    + "/images/iconos/creditcards.png\" alt=\"Cupones\" title=\"Cupones\" border=\"0\" /> </a>";
            String mensaje2 = "<a href=\"" + contextPath + "/cuponesPopup.do?action=cuponesPopup&gasto=" + gasto.getNroGasto() + "\">"
                    + imgTag + "</a></td>";
            return mensaje2;
        } else {
            return "";
        }

    }

    @Override
    protected String getScanLink() {
        return null;
    }

    @Override
    protected String getCaratulaLink() {
        return null;
    }

    public String getComprobante() {
        String comprobante = ((Gastos) this.getCurrentRowObject()).getComprobante();

        if (comprobante.equals("FACTU")) {
            return "FACTURA";
        } else if (comprobante.equals("MAIL-")) {
            return "MAIL";
        } else if (comprobante.equals("SCOMP")) {
            return "SIN COMPROBANTE";
        } else if (comprobante.equals("TICK-")) {
            return "TICKET";
        } else if (comprobante.equals("FOBL")) {
            return "FACTURA OBLIGATORIA";
        }

        return comprobante;
    }
}
