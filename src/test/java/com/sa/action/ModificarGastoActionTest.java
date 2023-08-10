package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.ComboGasto;
import com.sa.entities.ComboOpcion2;
import com.sa.entities.Usuario;
import com.sa.form.RendicionDetalleForm;
import com.sa.manager.ManagerTransaction;
import com.sa.services.PagosService;
import com.sa.services.RendicionesService;
import com.sa.util.ParamsConstants;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.ServletContext;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class ModificarGastoActionTest {

  @Mock
  Usuario usuarioMocked;
  @Mock
  ActionMapping actionMappingMocked;
  @Mock
  ActionForward actionForwardMocked;
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
  @Mock
  HttpSession httpSessionMocked;
  @InjectMocks
  ModificarGastoAction modificarGastoAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    String codigo = "1";
    ComboOpcion2 comboOpcion2 = new ComboOpcion2();
    List<ComboOpcion2> comboOpcion2List = new ArrayList<>();
    comboOpcion2List.add(comboOpcion2);

    return Stream.of(Arguments.of(codigo, comboOpcion2List)); //java.lang.ClassCastException: com.sa.entities.ComboOpcion2 cannot be cast to com.sa.entities.ComboGasto
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Disabled("Pendiente de revision. Refactorizar para poder testear")
  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should execute the action")
  void shouldExecuteTheAction(String codigo, List<ComboOpcion2> comboOpcion2List) throws Exception {
    //when
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("userWorking")).thenReturn(usuarioMocked);
    when(httpSessionMocked.getAttribute("usuario")).thenReturn(usuarioMocked);
    when(usuarioMocked.getIdUser()).thenReturn("1");
    when(actionMappingMocked.findForward("success")).thenReturn(actionForwardMocked);
    when(httpServletRequestMocked.getParameter("codigo")).thenReturn(codigo);
    when(httpServletRequestMocked.getAttribute("idRendicion")).thenReturn("1");

    // En metodo "cargarCombos" lineas 81 y 86 solicita lista de ComboOpcion2 y en la linea 94 solicita lista de ComboGasto
    // Refactorizado es necesario para poder validar su comportamiento
    //java.lang.ClassCastException: com.sa.entities.ComboOpcion2 cannot be cast to com.sa.entities.ComboGasto
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturnList()).thenReturn(comboOpcion2List);
          when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
        })) {
        //then
        ActionForward actionForwardToAssert = modificarGastoAction.executeAction(actionMappingMocked, rendicionDetalleFormMocked, samWebApplicationMocked,
            samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
        assertNull(actionForwardToAssert);
    }
  }
}
