package com.sa.decorator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ConsumosNoRendidosTableDecoratorTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>default or parameterless constructor of {@link ConsumosNoRendidosTableDecorator}
     *   <li>{@link ConsumosNoRendidosTableDecorator#getBorrarLink()}
     *   <li>{@link ConsumosNoRendidosTableDecorator#getCaratulaLink()}
     *   <li>{@link ConsumosNoRendidosTableDecorator#getCuponesLink()}
     *   <li>{@link ConsumosNoRendidosTableDecorator#getDestinatariosLink()}
     *   <li>{@link ConsumosNoRendidosTableDecorator#getEditarLink()}
     *   <li>{@link ConsumosNoRendidosTableDecorator#getScanLink()}
     *   <li>{@link ConsumosNoRendidosTableDecorator#getVerLink()}
     * </ul>
     */
    @Test
    void testConstructor() {
        ConsumosNoRendidosTableDecorator actualConsumosNoRendidosTableDecorator = new ConsumosNoRendidosTableDecorator();
        assertEquals("", actualConsumosNoRendidosTableDecorator.getBorrarLink());
        assertNull(actualConsumosNoRendidosTableDecorator.getCaratulaLink());
        assertNull(actualConsumosNoRendidosTableDecorator.getCuponesLink());
        assertNull(actualConsumosNoRendidosTableDecorator.getDestinatariosLink());
        assertEquals("", actualConsumosNoRendidosTableDecorator.getEditarLink());
        assertNull(actualConsumosNoRendidosTableDecorator.getScanLink());
        assertEquals("", actualConsumosNoRendidosTableDecorator.getVerLink());
    }
}

