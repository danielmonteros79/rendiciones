package com.sa.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FormatosCamposTest {

    @Test
    void testFormatString() {
        assertEquals("Str Origddell@example.org", FormatosCampos.formatString("Str Orig", "alice.liddell@example.org"));
        assertEquals("foo", FormatosCampos.formatString("foo", "foo"));
        assertEquals(ParamsConstants.GASTOS_CANTIDAD,
                FormatosCampos.formatString("Str Orig", ParamsConstants.GASTOS_CANTIDAD));
    }
}

