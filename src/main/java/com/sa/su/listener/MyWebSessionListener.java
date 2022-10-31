package com.sa.su.listener;

import ar.com.bbva.web.impl.WebSessionListener;
import ar.com.itrsa.sam.IContext;
import ar.com.itrsa.sam.IServiceAccessManager;
import ar.com.itrsa.sam.factory.SAMReference;
import com.sa.util.LiberarRecursos;
import javax.servlet.http.HttpSessionEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyWebSessionListener extends WebSessionListener {

    private static final Log log = LogFactory.getLog(MyWebSessionListener.class);
    public static final String INVOCAR_LIBERAR_RECURSOS = "INVOCAR_LIBERAR_RECURSOS";

    public void sessionDestroyed(HttpSessionEvent arg0) {
        // Liberamos los recursos.
        try {
            Boolean hayQueLiberar = (Boolean) arg0.getSession().getAttribute(INVOCAR_LIBERAR_RECURSOS);
            if (hayQueLiberar != null && hayQueLiberar.equals(Boolean.TRUE)) {
                IServiceAccessManager sam = (IServiceAccessManager) arg0.getSession().getServletContext()
                        .getAttribute(ar.com.bbva.utils.IConstants.SAM);
                if (sam == null) {
                    sam = SAMReference.getSAM();
                }
                IContext samctx = (IContext) arg0.getSession().getAttribute(ar.com.bbva.utils.IConstants.SAM_CONTEXT);
                LiberarRecursos liberar = new LiberarRecursos(sam, samctx);
            } else {
                log.info("Ya fueron liberados los recursos de la session!");
            }
        } catch (Exception e) {
            log.error("Error al liberar los recursos de la sesion.", e);
        }
        super.sessionDestroyed(arg0);
    }

}
