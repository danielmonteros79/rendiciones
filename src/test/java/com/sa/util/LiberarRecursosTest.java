package com.sa.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.com.itrsa.sam.IContext;
import ar.com.itrsa.sam.IServiceAccessManager;
import ar.com.itrsa.sam.impl.ContextImpl;
import ar.com.itrsa.sam.impl.ServiceAccessManagerImpl;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class LiberarRecursosTest {

    @Test
    void testExecute2() {
        LiberarRecursos liberarRecursos = new LiberarRecursos(new ServiceAccessManagerImpl(), null);
        assertTrue(liberarRecursos.execute(new HashMap<>()).isEmpty());
    }

    @Test
    void testExecute3() {
        LiberarRecursos liberarRecursos = new LiberarRecursos(null, null);
        assertTrue(liberarRecursos.execute(new HashMap<>()).isEmpty());
    }
}

