package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.ComboGasto;
import com.sa.entities.ComboOpcion2;
import com.sa.entities.Usuario;
import com.sa.form.CuponesForm;
import com.sa.form.RendicionDetalleForm;
import com.sa.manager.ManagerTransaction;
import com.sa.services.PagosService;
import com.sa.services.RendicionesService;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class DetallePopUpActionTest {

  @Mock
  Usuario usuarioMocked;
  @Mock
  HttpSession httpSessionMocked;
  @Mock
  CuponesForm cuponesFormMocked;
  @Mock
  PrintWriter printWriterMocked;
  @Mock
  ActionMapping actionMappingMock;
  @Mock
  ActionForward actionForwardMock;
  @Mock
  RendicionDetalleForm rendicionDetalleFormMocked;
  @Mock
  SAMWebClient samWebClientMocked;
  @Mock
  SAMWebApplication samWebApplicationMocked;
  @Mock
  HttpServletRequest httpServletRequestMocked;
  @Mock
  HttpServletResponse httpServletResponseMocked;
  @InjectMocks
  DetallePopUpAction detallePopUpAction;

  public static Stream<Arguments> executeActionSource() {
    //then
    String action = "";
    String actionSelectTipoGasto = "selectTipoGasto";
    String codigo = "1";
    String costosDestino = "0";
    String costosDestino2 = "0000";

    ComboOpcion2 comboOpcion2 = new ComboOpcion2();
    comboOpcion2.setId("1");

    List<ComboOpcion2> comboOpcion2List = new ArrayList<>();
    comboOpcion2List.add(comboOpcion2);

    return Stream.of(
        Arguments.of(action, comboOpcion2List, null,costosDestino),
        Arguments.of(action, comboOpcion2List, codigo,costosDestino),
        Arguments.of(action, comboOpcion2List, codigo,costosDestino2),
        Arguments.of(actionSelectTipoGasto, comboOpcion2List, codigo,costosDestino)
    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should execute the action")
  void shouldExecuteTheAction(String action, List<ComboOpcion2> comboOpcion2List, String codigo,String costosDestino) throws Exception {
    //given
    ComboGasto comboGasto = new ComboGasto();
    List<ComboGasto> comboGastoList = new ArrayList<>();
    comboGastoList.add(comboGasto);

    //when
    when(httpServletRequestMocked.getParameter("accion")).thenReturn(action);
    when(httpServletRequestMocked.getAttribute("formCupones")).thenReturn(cuponesFormMocked);
    when(httpServletRequestMocked.getParameter("codigo")).thenReturn(codigo);
    when(httpServletRequestMocked.getAttribute("codigo")).thenReturn("1");
    when(httpServletRequestMocked.getParameter("costosDestino")).thenReturn(costosDestino);
    when(httpServletRequestMocked.getParameter("codTipoGasto")).thenReturn("0");
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("userWorking")).thenReturn(usuarioMocked);
    when(httpSessionMocked.getAttribute("usuario")).thenReturn(usuarioMocked);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    when(actionMappingMock.findForward("success")).thenReturn(actionForwardMock);
    when(cuponesFormMocked.getImporteCupon()).thenReturn("importeCupon");
    when(cuponesFormMocked.getFechaPresentacion()).thenReturn("2023-01-01");
    when(rendicionDetalleFormMocked.getMoneda()).thenReturn("1");

    try (MockedConstruction<RendicionesService> rendicionesServiceMC = Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
      when(mockRendicionesService.getComboOpcion2(any(),any(),any(),any())).thenReturn(comboOpcion2List);
      when(mockRendicionesService.getComboOpcion2(any(),any(),any(),any(),any())).thenReturn(comboOpcion2List);
    })) {
      try (MockedConstruction<PagosService> pagosServiceMC = Mockito.mockConstruction(PagosService.class, (mockPagosService, context) -> {
        when(mockPagosService.getComboGasto(any(),any(),any())).thenReturn(comboGastoList);
      })) {
        ActionForward actionForwardToAssert = detallePopUpAction.executeAction(actionMappingMock, rendicionDetalleFormMocked, samWebApplicationMocked,
                samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
        if ("selectTipoGasto".equals(action)) {
          assertNull(actionForwardToAssert);
        } else {
          assertNotNull(actionForwardToAssert);
        }
      }
    }
  }


  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should execute the action")
  void shouldExecuteTheActionCuponesFormNull(String action, List<ComboOpcion2> comboOpcion2List, String codigo,String costosDestino) throws Exception {
    //given
    ComboGasto comboGasto = new ComboGasto();
    List<ComboGasto> comboGastoList = new ArrayList<>();
    comboGastoList.add(comboGasto);

    //when
    when(httpServletRequestMocked.getParameter("accion")).thenReturn(action);
    when(httpServletRequestMocked.getAttribute("formCupones")).thenReturn(null);
    when(httpServletRequestMocked.getParameter("codigo")).thenReturn(codigo);
    when(httpServletRequestMocked.getAttribute("codigo")).thenReturn("1");
    when(httpServletRequestMocked.getParameter("costosDestino")).thenReturn(costosDestino);
    when(httpServletRequestMocked.getParameter("codTipoGasto")).thenReturn("0");
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("userWorking")).thenReturn(usuarioMocked);
    when(httpSessionMocked.getAttribute("usuario")).thenReturn(usuarioMocked);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    when(actionMappingMock.findForward("success")).thenReturn(actionForwardMock);
    when(rendicionDetalleFormMocked.getMoneda()).thenReturn("1");

    try (MockedConstruction<RendicionesService> rendicionesServiceMC = Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
      when(mockRendicionesService.getComboOpcion2(any(),any(),any(),any())).thenReturn(comboOpcion2List);
      when(mockRendicionesService.getComboOpcion2(any(),any(),any(),any(),any())).thenReturn(comboOpcion2List);
    })) {
      try (MockedConstruction<PagosService> pagosServiceMC = Mockito.mockConstruction(PagosService.class, (mockPagosService, context) -> {
        when(mockPagosService.getComboGasto(any(),any(),any())).thenReturn(comboGastoList);
      })) {
        ActionForward actionForwardToAssert = detallePopUpAction.executeAction(actionMappingMock, rendicionDetalleFormMocked, samWebApplicationMocked,
                samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
        if ("selectTipoGasto".equals(action)) {
          assertNull(actionForwardToAssert);
        } else {
          assertNotNull(actionForwardToAssert);
        }
      }
    }
  }
}
