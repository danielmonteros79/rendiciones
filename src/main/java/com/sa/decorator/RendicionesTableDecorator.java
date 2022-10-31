package com.sa.decorator;

//comentario
import com.sa.entities.Rendicion;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

public class RendicionesTableDecorator extends SumTableDecorator {

    @Override
    protected String getVerLink() {
        PageContext pc = this.getPageContext();
        HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        String contextPath = request.getContextPath();

        Rendicion rendicion = (Rendicion) this.getCurrentRowObject();
        String imgTag = "<img src=\"" + contextPath + "/images/iconos/ver.png\" alt=\"Ver\" title=\"Ver\" border=\"0\" />";
        String verLink = "<a href=\"" + contextPath
                + "/mostrarDetalleGastos.do?action=mostrarDetalleGastos"
                + "&codigo=" + rendicion.getId()
                + "\">" + imgTag + "</a>";
        return verLink;
    }

    @Override
    protected String getEditarLink() {
        return "";
    }

    @Override
    protected String getBorrarLink() {
        Rendicion rendicion = (Rendicion) this.getCurrentRowObject();

        PageContext pc = this.getPageContext();
        HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        String contextPath = request.getContextPath();
        String borrarLink = "";
        String idRendicion = rendicion.getId().toString();
        if (rendicion.getEstado().equalsIgnoreCase("PENDI")) {
            String imgTag = "<a href=\"#\" onclick=\"eliminarRendicion("
                    + idRendicion
                    + ")\"><img src=\""
                    + contextPath
                    + "/images/iconos/borrar.png\" alt=\"Eliminar\" title=\"Eliminar\" border=\"0\" /> </a>";
            borrarLink = "<a href=\"" + contextPath
                    + "/listaRendiciones.do?action=deleteSector&codigo="
                    + rendicion.getId() + "\">" + imgTag + "</a></td>";
        }

        return borrarLink;

    }

    @Override
    protected String getScanLink() {
        PageContext pc = this.getPageContext();
        HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        String contextPath = request.getContextPath();

        Rendicion sector = (Rendicion) this.getCurrentRowObject();
        String verLink = "";
        if (sector.getEstado().equalsIgnoreCase("PENDI") || sector.getEstado().equalsIgnoreCase("ESCAN")) {
            String imgTag = "<img src=\""
                    + contextPath
                    + "/images/iconos/scanner.png\" alt=\"Escan\" title=\"Escan\" border=\"0\"width=\"24\" height=\"24\" />";
            verLink = "<a href=\""
                    + contextPath
                    + "/mostrarDetalleScan.do?action=mostrarDetalleScan&codigo="
                    + sector.getId() + "\">" + imgTag + "</a>";
        }

        return verLink;
    }

    @Override
    protected String getDestinatariosLink() {
        return "";
    }

    @Override
    protected String getCuponesLink() {
        Rendicion rend = (Rendicion) this.getCurrentRowObject();
        String img = "";
        PageContext pc = this.getPageContext();
        HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        String contextPath = request.getContextPath();

        if (rend.getAlerta().equalsIgnoreCase("1")) {
            img = "<img width='25px' src='" + contextPath + "/images/iconos/alerta_riesgo_grave.png' alt='Riesgo grave' title='Riesgo grave'/>";
        } else if (rend.getAlerta().equalsIgnoreCase("2")) {
            img = "<img width='25px' src='" + contextPath + "/images/iconos/alerta_riesgo.png' alt='Riesgo' title='Riesgo'/>";
        } else if (rend.getAlerta().equalsIgnoreCase("3")) {
            img = "<img width='25px' src='" + contextPath + "/images/iconos/alerta_incidencia_grave.png' alt='Incidencia grave' title='Incidencia grave'/>";
        } else if (rend.getAlerta().equalsIgnoreCase("4")) {
            img = "<img width='25px' src='" + contextPath + "/images/iconos/alerta_incidente.png' alt='Incidente' title='Incidente'/>";
        } else if (rend.getAlerta().equalsIgnoreCase("5")) {
            img = "<img width='25px' src='" + contextPath + "/images/iconos/alerta_anomalia.png' alt='Anomal&iacute;a' title='Anomal&iacute;a'/>";
        }

        return img;
    }

    public String getStatusColor() {
        Rendicion rend = (Rendicion) this.getCurrentRowObject();
        String div = "";
        if (rend.getEstado().equalsIgnoreCase("PENDI")) {
            div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-zero-in-a-circle.png); background-color: yellow;margin-left:7px\"> </div>";
        } else if (rend.getEstado().equalsIgnoreCase("ESCAN")) {
            div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-one-in-a-circle.png); background-color: lightblue;margin-left:7px\"> </div>";
        } else if (rend.getEstado().trim().equalsIgnoreCase("PSUP")) {
            div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-two-in-a-circle.png); background-color: lightblue;margin-left:7px\"> </div>";
        } else if (rend.getEstado().equalsIgnoreCase("PFIRM")) {
            div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-three-in-a-circle.png); background-color: lightblue;margin-left:7px\"> </div>";
        } else if (rend.getEstado().trim().equalsIgnoreCase("PGLG")) {
            div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-four-in-circular-button.png); background-color: lightblue;margin-left:7px\"> </div>";
        } else if (rend.getEstado().equalsIgnoreCase("OBSER")) {
            div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-four-in-circular-button.png); background-color: yellow;margin-left:7px\"> </div>";
        } else if (rend.getEstado().equalsIgnoreCase("APROB")) {
            div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-five-in-circular-button.png); background-color: lightblue;margin-left:7px\"> </div>";
        } else if (rend.getEstado().equalsIgnoreCase("ORDPG")) {
            div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-five-in-circular-button.png); background-color: #5cb85c;margin-left:7px\"> </div>";
        } else if (rend.getEstado().equalsIgnoreCase("SUSPE")) {
            div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-five-in-circular-button.png); background-color: yellow;margin-left:7px\"> </div>";
        } else if (rend.getEstado().equalsIgnoreCase("RECHA")) {
            div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/circular-button.png); background-color: red;margin-left:7px\"> </div>";
        } else {
            div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-five-in-circular-button.png); background-color: red;margin-left:7px\"> </div>";
        }

        return div;
    }

    @Override
    protected String getCaratulaLink() {
        // TODO Auto-generated method stub
        PageContext pc = this.getPageContext();
        HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        String contextPath = request.getContextPath();

        Rendicion rendicion = (Rendicion) this.getCurrentRowObject();

        String verLink = "";
        if (!rendicion.getEstado().equalsIgnoreCase("PENDI")) {
            if (!rendicion.getIdu().equals("")
                    && !rendicion.getAdea().equalsIgnoreCase("")) {
                String imgTag = "<img src=\""
                        + contextPath
                        + "/images/iconos/pdf.png\" alt=\"Caratula\" title=\"Caratula\" border=\"0\" />";
                verLink = "<a href=\""
                        + contextPath
                        + "/rendicionAviso.do?generate=anymode&rnd="
                        + rendicion.getId() + "\">" + imgTag + "</a>";
            }

        }

        return verLink;
    }
}
