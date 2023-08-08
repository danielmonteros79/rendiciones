package com.sa.decorator.delegacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class AbmDelegadoTableDecoratorTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>default or parameterless constructor of {@link AbmDelegadoTableDecorator}
     *   <li>{@link AbmDelegadoTableDecorator#getCaratulaLink()}
     *   <li>{@link AbmDelegadoTableDecorator#getCuponesLink()}
     *   <li>{@link AbmDelegadoTableDecorator#getDestinatariosLink()}
     *   <li>{@link AbmDelegadoTableDecorator#getScanLink()}
     *   <li>{@link AbmDelegadoTableDecorator#getVerLink()}
     * </ul>
     */
    @Test
    void testConstructor() {
        AbmDelegadoTableDecorator actualAbmDelegadoTableDecorator = new AbmDelegadoTableDecorator();
        assertNull(actualAbmDelegadoTableDecorator.getCaratulaLink());
        assertNull(actualAbmDelegadoTableDecorator.getCuponesLink());
        assertNull(actualAbmDelegadoTableDecorator.getDestinatariosLink());
        assertNull(actualAbmDelegadoTableDecorator.getScanLink());
        assertEquals("", actualAbmDelegadoTableDecorator.getVerLink());
    }
}

