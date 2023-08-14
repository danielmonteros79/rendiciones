package com.sa.services.trxs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Usuario;

import java.util.ArrayList;
import java.util.Collection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SU52Test {

    @Test
    void testConstructor() {
        assertTrue((new SU52()).getDataReturnList().isEmpty());
    }


    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SU52 su52 = new SU52();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", new ArrayList<>());
        parametersExecute.put((String) "facultad", "foo");
        parametersExecute.put((String) "ctro_costos", (Object) "");
        parametersExecute.put((String) "sector", (Object) "");
        parametersExecute.put((String) "cod_user", "foo");
        parametersExecute.put((String) "nombre_apellido", "foo");
        assertThrows(TransactionException.class, () -> su52.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando mapData")
    void mapData() {
        List<String> test = new ArrayList<>();
        test.add("A103555 AUDRUICQ, DIEGO ANDRES                                                  85701721S");
        SU52 su52 = new SU52();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", test);
        parametersExecute.put((String) "facultad", "foo");
        parametersExecute.put((String) "ctro_costos", (Object) "");
        parametersExecute.put((String) "sector", (Object) "");
        parametersExecute.put((String) "cod_user", "foo");
        parametersExecute.put((String) "nombre_apellido", "foo");
        //parte Pablo
        parametersExecute.put((String) "test","test");
        parametersExecute.put((String)"test2", "test2");

        su52.mapData(parametersExecute);
        Object dataReturn = su52.getDataReturn();
        assertTrue(dataReturn instanceof Usuario);
        Object expectedSector = parametersExecute.get("ctro_costos");
        assertSame(expectedSector, ((Usuario) dataReturn).getSector());
        Object getResult = parametersExecute.get("nombre_apellido");
        assertSame(getResult, ((Usuario) dataReturn).getNombre());
        assertSame(getResult, ((Usuario) dataReturn).getIdUser());
        assertTrue(((Usuario) dataReturn).getGlgAprobacion().isEmpty());
        assertEquals("N", ((Usuario) dataReturn).getFacultades());
//        assertTrue(((Usuario) dataReturn).getDelegadosAsignados().isEmpty());
        assertEquals(0, ((Usuario) dataReturn).getCcostos());
    }

    @Test
    @DisplayName("Testeando hardcodear")
    void hardcodear() {
        SU52 su52 = new SU52();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "cod_user", (Object) "A103555");
        su52.hardcodear(parametersExecute);
        assertEquals(7, parametersExecute.size());
        Object getResult = parametersExecute.get("lista");
        assertEquals(1, ((Collection<String>) getResult).size());
        assertEquals("A103555 AUDRUICQ, DIEGO ANDRES                                                     85701721S",
                ((List<String>) getResult).get(0));
    }

    @Test
    @DisplayName("Testeando tesHardcodear si no entra en el if")
    void hardcodearSegundaOpcion() {
        SU52 su52 = new SU52();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("cod_user", "42");
        su52.hardcodear(parametersExecute);
        assertEquals(7, parametersExecute.size());
        Object getResult = parametersExecute.get("lista");
        assertEquals(2, ((Collection<String>) getResult).size());
        assertEquals("A126661 RED DEVIL, ANALIA LAURA                                                    00998568S",
                ((List<String>) getResult).get(0));
        assertEquals("A103555 AUDRUICQ, DIEGO ANDRES                                                     85701721S",
                ((List<String>) getResult).get(1));
    }
}

