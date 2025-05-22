package com.sa.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CsvSeparadorTest {

    @Test
    void testConstructor() {
        assertEquals(";", (new CsvSeparador()).getCellEnd());
    }
}

