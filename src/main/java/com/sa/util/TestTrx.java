package com.sa.util;

import java.util.HashMap;
import java.util.Map;

import com.sa.entities.TipoPerfil;
import com.sa.manager.ManagerTransaction;
import com.sa.services.trxs.SU53;
import com.sa.services.trxs.SU54;

import ar.com.itrsa.sam.TransactionException;

public class TestTrx {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		System.out.println(TipoPerfil.VIEW_ALL);
//		System.out.println(TipoPerfil.VIEW_CIERRE);
//
//
//		for (TipoPerfil str : TipoPerfil.values()) {
//			System.out.println(str.getPantalla());
//		}
		
//		System.out.println(TipoPerfil.VIEW_ALL);

		
//		List<Integer> list = new ArrayList<Integer>();
//		list.add(1);
//		list.add(2);
//		list.add(3);
//		list.add(4);
//		list.add(5);
//		String retorno = "";
//		for (Integer val : list) {
//			retorno = retorno+  String.valueOf(val);
//			retorno += ";";
//		}
//		System.out.println(retorno);
//		System.out.println();
		String str = "XA02254 Andres Torres                                                              01-01-201608-12-2016SSS";
		
		Map parametersExecute = new HashMap();
		
		parametersExecute.put("nombre_apellido", "German Gambera");
		parametersExecute.put("ctro_costos", "7777777");
		parametersExecute.put("desc_ctro_costos", "Ctro costo 777");
		parametersExecute.put("sector", "1-77777");
		parametersExecute.put("desc_sector", "Cajas");
		parametersExecute.put("sucursal_user", "");
		parametersExecute.put("cod_puesto", "99999");
		parametersExecute.put("cta_monetaria", "cta monetaria");
		
		
//		ManagerTransaction manager = new ManagerTransaction(new SU53());
//		try {
//			manager.executeTrx(null, null);
//		} catch (TransactionException e) {
//			// TODO Auto-generated catch blocks
//			e.printStackTrace();
//		}
//		manager = new ManagerTransaction(new SU54());
//		try {
//			manager.executeTrx(null, null);
//		} catch (TransactionException e) {
//			// TODO Auto-generated catch blocks
//			e.printStackTrace();
//		}
	}

}
