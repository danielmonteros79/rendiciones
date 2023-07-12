package com.sa.util;

/* Algunos imports que les van a hacer falta */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.soa.conectores.BbvaSoaMensaje;
import ar.com.bbva.soa.conectores.BbvaSoaStatus;
import com.bbva.sam.bbvaPaq.BbvaPaqConstants;

import ar.com.bbva.utils.PropertiesUtils;
import ar.com.itrsa.sam.IContext;
import ar.org.bbva.util.DumpUtils;

import ar.com.itrsa.sam.IServiceAccessManager;
import ar.com.itrsa.sam.factory.SAMReference;

public class LiberarRecursos
{
    BbvaSoaStatus      stat = null;
    BbvaSoaMensaje     mens = null;
    Iterator<?>		   iterator = null;

	private static final Log log = LogFactory.getLog(LiberarRecursos.class);
	
	IServiceAccessManager 	sam = null; 
	IContext				samContext = null;
	
	public LiberarRecursos(IServiceAccessManager sam, IContext samContext) {
		super();
		this.sam = sam;
		this.samContext = samContext;
	}
	
	
	public Map<?, ?> execute(Map<?, ?> parameters){
		Map<?, ?> paramsLogoff = new HashMap<Object, Object>();
		
        try {
        		
        	if(sam==null)
        		sam = SAMReference.getSAM();
            sam.execute("TM_WSSOACON.CMD_RMVLU", samContext, paramsLogoff);
            
            /* Estas l�neas es s�lo si para desarrollo les interesa ver en el log un
             * dump de todo el mapa de par�metros que devolvi� SAM...
             */
            if (log.isDebugEnabled())
            log.debug(PropertiesUtils.LSEP + DumpUtils.dumpMap(paramsLogoff, true));

            stat = (BbvaSoaStatus) paramsLogoff.get(BbvaPaqConstants.NOMBRE_PARAM_STATUS);
            if (stat != null) {
             if (stat.isOk()) {                 
                    log.info("----- Liberar Recursos OK -----");
            } else {
                    for (iterator = stat.getListaErrores().iterator();
                           iterator.hasNext(); ) {
                           mens = (BbvaSoaMensaje)iterator.next();
                           log.error("Se recibio el error: Codigo=" + mens.getCodigo() +
                                        " Descripcion=" + mens.getDescripcion() +
                                        " DescDetallada=" + mens.getDescripcionDetallada());
                    }
            }
            } else
            log.error("No recibimos el " + BbvaPaqConstants.NOMBRE_PARAM_STATUS);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return paramsLogoff;
	}
}





