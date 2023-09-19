package com.sa.action.tarjetaCorporativa;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import com.sa.services.ResumenService;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class ConsumosNoRendidosActionTest {

  @Mock
  PrintWriter printWriterMocked;
  @Mock
  ActionForward actionForwardMocked;
  @Mock
  ActionMapping actionMappingMocked;
  @Mock
  ActionForm actionFormMocked;
  @Mock
  SAMWebApplication samWebApplicationMocked;
  @Mock
  SAMWebClient samWebClientMocked;
  @Mock
  HttpServletResponse httpServletResponseMocked;
  @Mock
  HttpServletRequest httpServletRequestMocked;
  @InjectMocks
  ConsumosNoRendidosAction consumosNoRendidosAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    String action = "";
    String actionFiltrar = "filtrar";

    return Stream.of(
        Arguments.of(action),
        Arguments.of(actionFiltrar)
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    Usuario usuario = new Usuario("", "", "", 0, "", new ArrayList<>());
    consumosNoRendidosAction.setSessionUserWorking(usuario);
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should execute action")
  void shouldExecuteAction(String action) throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("action")).thenReturn(action);

    when(actionMappingMocked.findForward(anyString())).thenReturn(actionForwardMocked);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ResumenService> resumenServiceMC = Mockito.mockConstruction(ResumenService.class,
        (mockResumenService, context) -> {
          when(mockResumenService.getConsumos(anyString(), anyString(), anyString(), anyString())).thenReturn(new ArrayList<>());
        })) {

      //then
      ActionForward actionForwardToAssert = consumosNoRendidosAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked,
          samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
      assertNotNull(actionForwardToAssert);
    }
  }

  @Test
  @DisplayName("Should catch exception")
  void shouldCatchException() throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("action")).thenReturn("filtrar");

    when(actionMappingMocked.findForward(anyString())).thenReturn(actionForwardMocked);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    ActionForward actionForwardToAssert = consumosNoRendidosAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked,
        samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
    assertNull(actionForwardToAssert);
  }
}
