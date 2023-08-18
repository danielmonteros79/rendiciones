package com.sa.action.rendiciones;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sa.entities.ComboMotivo;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import com.sa.services.RendicionesService;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class RendicionLoadActionTest {

  @Mock
  Usuario usuarioMocked;
  @Mock
  PrintWriter printWriterMocked;
  @Mock
  ActionForward actionForwardMocked;
  @Mock
  ActionMapping actionMappingMocked;
  @Mock
  RendicionForm rendicionFormMocked;
  @Mock
  SAMWebApplication samWebApplicationMocked;
  @Mock
  SAMWebClient samWebClientMocked;
  @Mock
  HttpServletResponse httpServletResponseMocked;
  @Mock
  HttpServletRequest httpServletRequestMocked;
  @Mock
  HttpSession httpSessionMocked;
  @InjectMocks
  RendicionLoadAction rendicionLoadAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    String accion = "";
    String accionCheckDestino = "checkCDestino";

    ComboMotivo comboMotivo = new ComboMotivo();
    comboMotivo.setId("0");
    comboMotivo.setCostosDestino("0");
    List<ComboMotivo> comboMotivoList = new ArrayList<>();
    comboMotivoList.add(comboMotivo);

    return Stream.of(
        Arguments.of(accion, comboMotivoList),
        Arguments.of(accionCheckDestino, comboMotivoList)
                    );
  }

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  /**
   * Method under test: {@link RendicionLoadAction#executeAction(ActionMapping, ActionForm, SAMWebApplication, SAMWebClient, HttpServletRequest, HttpServletResponse)}
   */
  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("should execute action")
  void shouldExecuteAction(String accion, List<ComboMotivo> comboMotivoList) throws Exception {
    //given
    Map<String, String> mapMotivoCostos = new HashMap<>();
    mapMotivoCostos.put("", "0");

    Field mapMotivoCostosSetPrivate = RendicionLoadAction.class.getDeclaredField("mapMotivoCostos");
    mapMotivoCostosSetPrivate.setAccessible(true);
    mapMotivoCostosSetPrivate.set(rendicionLoadAction, mapMotivoCostos);

    //when
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("userWorking")).thenReturn(usuarioMocked);
    when(httpSessionMocked.getAttribute("usuario")).thenReturn(usuarioMocked);
    when(httpServletRequestMocked.getParameter("accion")).thenReturn(accion);
    when(httpServletRequestMocked.getParameter("codMotivo")).thenReturn("");

    when(usuarioMocked.getIdUser()).thenReturn("0");
    when(usuarioMocked.getNombre()).thenReturn("nombre");
    when(usuarioMocked.getCcostos()).thenReturn(0);
    when(usuarioMocked.getSector()).thenReturn("sector");

    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    when(actionMappingMocked.findForward(anyString())).thenReturn(actionForwardMocked);

    try (MockedConstruction<RendicionesService> rendicionesServiceMC = Mockito.mockConstruction(RendicionesService.class,
        (mockRendicionesService, context) -> {
          when(mockRendicionesService.getMotivoRendiciones(anyString(), anyString())).thenReturn(comboMotivoList);
        })) {
      //then
      ActionForward actionForwardToAssert = rendicionLoadAction.executeAction(actionMappingMocked, rendicionFormMocked, samWebApplicationMocked,
          samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
      if (accion.equals("checkCDestino")) {
        assertNull(actionForwardToAssert);
      } else {
        assertNotNull(actionForwardToAssert);
      }
    }
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("should catch exception")
  void shouldCatchException(String accion, List<ComboMotivo> comboMotivoList) throws Exception {
    //when
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("userWorking")).thenReturn(usuarioMocked);
    when(httpSessionMocked.getAttribute("usuario")).thenReturn(usuarioMocked);
    when(httpServletRequestMocked.getParameter("accion")).thenReturn(accion);

    when(usuarioMocked.getIdUser()).thenReturn("0");
    when(usuarioMocked.getNombre()).thenReturn("nombre");
    when(usuarioMocked.getCcostos()).thenReturn(0);
    when(usuarioMocked.getSector()).thenReturn("sector");

    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    when(actionMappingMocked.findForward(anyString())).thenReturn(actionForwardMocked);
    //then
    ActionForward actionForwardToAssert = rendicionLoadAction.executeAction(actionMappingMocked, rendicionFormMocked, samWebApplicationMocked,
        samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
    if (accion.equals("checkCDestino")) {
      assertNull(actionForwardToAssert);
    } else {
      assertNotNull(actionForwardToAssert);
    }
  }
}
