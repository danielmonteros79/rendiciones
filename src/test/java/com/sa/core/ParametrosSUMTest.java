package com.sa.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ParametrosSUMTest {

  @InjectMocks
  ParametrosSUM parametrosSUM;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should create an instance")
  void shouldCreateAnInstance() {
    //then
    ParametrosSUM parametrosSUM = new ParametrosSUM();
    assertNotNull(parametrosSUM);
  }

  @Test
  void testAgregarParametro() throws Exception {
    //then
    parametrosSUM.agregarParametro("nombre", "parametro");

    Field paramsGetPrivate = ParametrosSUM.class.getDeclaredField("params");
    paramsGetPrivate.setAccessible(true);
    Map<String, Object> paramsToAssert = (Map<String, Object>) paramsGetPrivate.get(parametrosSUM);

    assertNotNull(paramsToAssert);
    assertEquals("parametro", paramsToAssert.get("nombre"));
  }

  @Test
  void testGetParametro() throws Exception {
    //given
    Map<String, Object> params = new HashMap<>();
    params.put("nombre", "parametro");

    //then
    Field paramsSetPrivate = ParametrosSUM.class.getDeclaredField("params");
    paramsSetPrivate.setAccessible(true);
    paramsSetPrivate.set(parametrosSUM, params);

    Object paramsToAssert = parametrosSUM.getParametro("nombre");
    assertNotNull(paramsToAssert);
    assertEquals("parametro", paramsToAssert);
  }
}
