package com.sa.action.parametros;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.OSCAR;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroGasto;
import com.sa.form.parametros.ParametrosGastosForm;
import com.sa.manager.ManagerTransaction;
import com.sa.services.ParametrosService;
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
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class ParametrosGastosFiltroActionTest {

  @Mock
  ManagerTransaction managerTransactionMocked;
  @Mock
  ParametroGasto parametroGastoMocked;
  @Mock
  Usuario usuarioMocked;
  @Mock
  ActionForward actionForwardMocked;
  @Mock
  PrintWriter printWriterMocked;
  @Mock
  ActionMapping actionMappingMocked;
  @Mock
  SAMWebApplication samWebApplicationMocked;
  @Mock
  SAMWebClient samWebClientMocked;
  @Mock
  HttpServletResponse httpServletResponseMocked;
  @Mock
  HttpServletRequest httpServletRequestMocked;
  @Mock
  ParametrosGastosForm parametrosGastosFormMocked;
  @Mock
  List<String> centroCostoListMocked;
  @Mock
  HttpSession httpSessionMocked;
  @InjectMocks
  ParametrosGastosFiltroAction parametrosGastosFiltroAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    String action = "";
    String actionBorrarCentroCosto = "borrarCentroCosto";
    String actionAgregarCentroCosto = "agregarCentroCosto";
    boolean isBack = true;
    String formAccion = "success";
    String formAccionAlta = "alta";
    String formAccionModificacion = "modificacion";
    String formAccionBaja = "baja";

    List<String> centrosCostoList = new ArrayList<>();
    centrosCostoList.add("MDmotivo");
    centrosCostoList.add("TC3456789012345678901234567890123456789012345678901234567890");
    centrosCostoList.add("OBobservacion");

    return Stream.of(
        Arguments.of(action, isBack, formAccion, centrosCostoList, 1),
        Arguments.of(actionBorrarCentroCosto, isBack, formAccion, centrosCostoList, 1),
        Arguments.of(actionAgregarCentroCosto, isBack, formAccion, centrosCostoList, 1),
        Arguments.of(actionAgregarCentroCosto, isBack, formAccion, centrosCostoList, 16),
        Arguments.of(action, !isBack, formAccionAlta, centrosCostoList, 1),
        Arguments.of(action, !isBack, formAccionModificacion, centrosCostoList, 1),
        Arguments.of(action, !isBack, formAccionBaja, centrosCostoList, 1)
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should execute action")
  void shouldExecuteAction(String action, boolean isBack, String formAccion, List<String> centrosCostoList, int size) throws Exception {
    //when
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("usuario")).thenReturn(usuarioMocked);
    when(usuarioMocked.getIdUser()).thenReturn("00");
    when(httpServletRequestMocked.getParameter("accionJson")).thenReturn(action);
    when(httpServletRequestMocked.getParameter("index")).thenReturn("0");

    when(parametrosGastosFormMocked.isBack()).thenReturn(isBack);
    when(parametrosGastosFormMocked.getAccion()).thenReturn(formAccion);
    doReturn(centroCostoListMocked).when(parametrosGastosFormMocked).getCentrosCosto();
    when(centroCostoListMocked.remove("0")).thenReturn(true);
    when(parametrosGastosFormMocked.getCodigo()).thenReturn("");
    when(parametrosGastosFormMocked.getCentrosCosto()).thenReturn(centroCostoListMocked);
    when(centroCostoListMocked.size()).thenReturn(size);

    when(actionMappingMocked.findForward(anyString())).thenReturn(actionForwardMocked);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class,
        (mockParametrosService, context) -> {
          when(mockParametrosService.getGastosCombos()).thenReturn(centrosCostoList);

          doReturn(managerTransactionMocked).when(mockParametrosService).loadModificacionGasto(anyString(), anyString());
          doReturn(managerTransactionMocked).when(mockParametrosService).loadBajaGasto(anyString(), anyString());

          doReturn(parametroGastoMocked).when(managerTransactionMocked).getDataReturn();
          doReturn("OK").when(parametroGastoMocked).getEstado();
          doReturn("").when(parametroGastoMocked).getCcostos();
          doReturn("").when(parametroGastoMocked).getRistra();
          doReturn(new OSCAR("oscar")).when(parametroGastoMocked).getOscar();
          doReturn("").when(parametroGastoMocked).getMaInclExcl();
          doReturn("").when(parametroGastoMocked).getComprob();
          doReturn("").when(parametroGastoMocked).getAntiguedad();
          doReturn("").when(parametroGastoMocked).getBimon();
          doReturn("").when(parametroGastoMocked).getAutoriz();
          doReturn("").when(parametroGastoMocked).getObserv();
          doReturn("").when(parametroGastoMocked).getNivelIngreso();
          doReturn("").when(parametroGastoMocked).getPlazoAprob();
          doReturn(centrosCostoList).when(parametroGastoMocked).getCentrosCosto();
        })) {
      try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
          (mockManagerTransaction, context) -> {
            doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          })) {
        //then
        ActionForward actionForwardToAssert = parametrosGastosFiltroAction.executeAction(actionMappingMocked, parametrosGastosFormMocked, samWebApplicationMocked,
            samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
        if (action.equals("borrarCentroCosto") || action.equals("agregarCentroCosto")) {
          assertNull(actionForwardToAssert);
        } else {
          assertNotNull(actionForwardToAssert);
        }
      }
    }
  }

  @Test
  @DisplayName("Should catch exception")
  void shouldCatchException() throws Exception {
    //when
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("usuario")).thenReturn(usuarioMocked);
    when(usuarioMocked.getIdUser()).thenReturn("00");

    when(parametrosGastosFormMocked.isBack()).thenReturn(false);
    when(parametrosGastosFormMocked.getAccion()).thenReturn("alta");

    when(actionMappingMocked.findForward(anyString())).thenReturn(actionForwardMocked);
        //then
        ActionForward actionForwardToAssert = parametrosGastosFiltroAction.executeAction(actionMappingMocked, parametrosGastosFormMocked, samWebApplicationMocked,
            samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
          assertNotNull(actionForwardToAssert);
  }
}
