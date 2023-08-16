package com.sa.action.aprobacion;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.manager.ManagerTransaction;
import com.sa.services.AprobacionesService;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class ListadoAprobacionesActionTest {

  @Mock
  ActionForward actionForwardMocked;
  @Mock
  PrintWriter printWriterMocked;
  @Mock
  ActionMapping actionMappingMocked;
  @Mock
  ActionForm actionFormMocked;
  @Mock
  HttpServletRequest httpServletRequestMocked;
  @Mock
  HttpServletResponse httpServletResponseMocked;
  @Mock
  SAMWebClient samWebClientMocked;
  @Mock
  SAMWebApplication samWebApplicationMocked;
  @Mock
  AprobacionesService aprobacionesServiceMocked;
  @InjectMocks
  ListadoAprobacionesAction listadoAprobacionesAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    ActionForward actionForward = new ActionForward();
    String actionFiltrar = "filtrar";
    String actionAprobar = "aprobar";
    String actionSuccess = "success";
    String alerta1 = "1";
    String alerta0 = "0";

    Rendicion rendicion = new Rendicion();
    rendicion.setAdea("1");
    List<Rendicion> rendicionList = new ArrayList<>();
    rendicionList.add(rendicion);

    return Stream.of(
        Arguments.of(actionSuccess, alerta0, rendicionList),
        Arguments.of(actionFiltrar, alerta1, rendicionList),
        Arguments.of(actionFiltrar, alerta0, rendicionList),
        Arguments.of(actionAprobar, alerta0, rendicionList)
                    );
  }

  public static Stream<Arguments> filtrarSource() {
    //given
    MockHttpServletRequest request = new MockHttpServletRequest();
    ActionForward actionForward = new ActionForward();
    List<Rendicion> rendicionList = new ArrayList<>();
    Rendicion rendicion = new Rendicion();

    rendicionList.add(rendicion);

    return Stream.of(
        Arguments.of(request, actionForward, rendicionList)
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    Usuario usuario = new Usuario("", "", "", 0, "", new ArrayList<>());
    listadoAprobacionesAction.setSessionUserWorking(usuario);
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should execute Action")
  void shouldExecuteAction(String action, String alerta, List<Rendicion> rendicionList) throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("action")).thenReturn(action);
    when(httpServletRequestMocked.getParameter("nroAlerta")).thenReturn(alerta);
    when(httpServletRequestMocked.getParameter("usuario")).thenReturn("");
    when(httpServletRequestMocked.getParameter("motivo")).thenReturn("");
    when(httpServletRequestMocked.getParameter("glg")).thenReturn("1234567");
    when(httpServletRequestMocked.getParameter("idRendiciones")).thenReturn("[\"1\", \"2\", \"3\"]");

    when(actionMappingMocked.findForward(anyString())).thenReturn(actionForwardMocked);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<AprobacionesService> aprobacionesServiceMC = Mockito.mockConstruction(AprobacionesService.class,
        (mockAprobacionesService, context) -> {
          when(mockAprobacionesService.getCantRendiciones()).thenReturn("1");
        })) {
      try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
          (mockManagerTransaction, context) -> {
            doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          })) {
        //then
        ActionForward actionForwardToAssert = listadoAprobacionesAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked,
            samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
        if (action.equals("aprobar")) {
          assertNull(actionForwardToAssert);
        } else {
          assertNotNull(actionForwardToAssert);
        }
      }
    }
  }

  @Test
  @DisplayName("Should catch Exception when execute Action")
  void shouldCatchExceptionWhenExecuteAction() throws Exception {
    //when
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    ActionForward actionForwardToAssert = listadoAprobacionesAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked,
        samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
    assertNull(actionForwardToAssert);
  }
}
