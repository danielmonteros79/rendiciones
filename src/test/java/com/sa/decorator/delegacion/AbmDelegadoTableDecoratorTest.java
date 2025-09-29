package com.sa.decorator.delegacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.sa.entities.parametros.ParametriaUsuarioDelegado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.stream.Stream;

class AbmDelegadoTableDecoratorTest {

    @InjectMocks
    AbmDelegadoTableDecorator abmDelegadoTableDecorator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConstructor() {
        AbmDelegadoTableDecorator actualAbmDelegadoTableDecorator = new AbmDelegadoTableDecorator();
        assertNull(actualAbmDelegadoTableDecorator.getCaratulaLink());
        assertNull(actualAbmDelegadoTableDecorator.getCuponesLink());
        assertNull(actualAbmDelegadoTableDecorator.getDestinatariosLink());
        assertNull(actualAbmDelegadoTableDecorator.getScanLink());
        assertEquals("", actualAbmDelegadoTableDecorator.getVerLink());
    }

    @Test
    @DisplayName("Testeando getEditarLink")
    void getEditarLink(){
        ParametriaUsuarioDelegado delegado = new ParametriaUsuarioDelegado();
        delegado.setId(1);

        abmDelegadoTableDecorator.initRow(delegado,0,0);

        String result = abmDelegadoTableDecorator.getEditarLink();
        assertEquals("<a href=\"#a\" class=\"text-gray\" onclick=\"modalDelegadoShow('1')\"><i class=\"bbva-icon icon-coronita_contract fa-lg\" data-toggle=\"tooltip\" title=\"Editar\"></i></a>",result);
    }

    @ParameterizedTest
    @MethodSource("getBorrarLinkSource")
    @DisplayName("Testeando getBorrarLink")
    void getBorrarLink(ParametriaUsuarioDelegado delegado, String expected){
        abmDelegadoTableDecorator.initRow(delegado,0,0);

        String result = abmDelegadoTableDecorator.getBorrarLink();
        assertEquals(expected,result);
    }

    // ------ Sources ------
    private static Stream<Arguments> getBorrarLinkSource() {
        ParametriaUsuarioDelegado delegado = new ParametriaUsuarioDelegado();
        ParametriaUsuarioDelegado delegado2 = new ParametriaUsuarioDelegado();
        delegado.setDelegadoEstado("A");
        delegado2.setDelegadoEstado("B");
        delegado.setDelegadoUser("user");
        delegado.setFeDesde(new Date(123,1,1));
        delegado.setFeHasta(new Date(123,1,1));

        return Stream.of(
                Arguments.of(delegado,"<a href=\"#a\" class=\"text-gray\" onclick=\"eliminarDelegado('user', '2023-02-01', '2023-02-01')\"><i class=\"bbva-icon icon-coronita_trash fa-lg\" data-toggle=\"tooltip\" title=\"Eliminar\"></i></a>"),
                Arguments.of(delegado2,"")
        );
    }


}

