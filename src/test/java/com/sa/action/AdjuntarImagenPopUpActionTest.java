/*package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.RendicionAvisoForm;
import com.sa.manager.ManagerTransaction;
import com.sa.services.AprobacionesService;
import com.sa.services.CaratulaService;
import com.sa.services.RendicionesService;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class AdjuntarImagenPopUpActionTest {

  @Mock
  ServletOutputStream servletOutputStreamMocked;
  @Mock
  File fileMocked;
  @Mock
  List<Rendicion> rendicionListMocked;
  @Mock
  Rendicion rendicionMocked;
  @Mock
  Usuario usuarioMocked;
  @Mock
  RendicionAvisoForm rendicionAvisoFormMocked;
  @Mock
  ActionForward actionForwardMocked;
  @Mock
  ActionMapping actionMappingMocked;
  @Mock
  SAMWebApplication samWebApplicationMocked;
  @Mock
  SAMWebClient samWebClientMocked;
  @Mock
  HttpServletRequest httpServletRequestMocked;
  @Mock
  HttpServletResponse httpServletResponseMocked;
  @Mock
  HttpSession httpSessionMocked;
  @Mock
  ServletContext servletContextMocked;
  //@InjectMocks
  //AdjuntarImagenPopUpAction adjuntarImagenPopUpAction;

  public static Stream<Arguments> executeActionCaratulaSource() {
    //given
    String caratula = "caratula";

    return Stream.of(
        Arguments.of("", ""),
        Arguments.of(caratula, "soy;un;test"),
        Arguments.of(caratula, null)
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    Usuario usuario = new Usuario("", "", "", 1, "", new ArrayList<>());
    adjuntarImagenPopUpAction.setSessionUserWorking(usuario);
  }

  @Test
  @DisplayName("Should execute action in happy trail")
  void shouldExecuteActionInHappyTrail() throws Exception {
    //given
    Rendicion rendicion = new Rendicion();
    List<Rendicion> rendicionList = new ArrayList<>();
    rendicionList.add(rendicion);

    //when
    when(rendicionAvisoFormMocked.getAccion()).thenReturn("");

    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    when(httpSessionMocked.getAttribute("userWorking")).thenReturn(usuarioMocked);
    when(servletContextMocked.getAttribute("rendicion.aviso.rename.archivo")).thenReturn("");

    when(usuarioMocked.getIdUser()).thenReturn("");
    when(rendicionAvisoFormMocked.getRendicion()).thenReturn(rendicionMocked);
    when(rendicionMocked.getId()).thenReturn(0);
    when(rendicionMocked.getUsuarioRendicion()).thenReturn("");

    when(actionMappingMocked.findForward(anyString())).thenReturn(actionForwardMocked);

    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturnList()).thenReturn(rendicionList);
        })) {
      //then
      ActionForward actionForwardToAssert = adjuntarImagenPopUpAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked, samWebApplicationMocked,
          samWebClientMocked,
          httpServletRequestMocked, httpServletResponseMocked);
      assertNotNull(actionForwardToAssert);
    }
  }

  @Test
  @DisplayName("Should execute when gastolist is empty")
  void shouldExecuteActionWhenGastoListIsEmpty() throws Exception {
    //when
    when(rendicionAvisoFormMocked.getAccion()).thenReturn("");

    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    when(httpSessionMocked.getAttribute("userWorking")).thenReturn(usuarioMocked);
    when(servletContextMocked.getAttribute("rendicion.aviso.rename.archivo")).thenReturn("");

    when(usuarioMocked.getIdUser()).thenReturn("");
    when(rendicionAvisoFormMocked.getRendicion()).thenReturn(rendicionMocked);
    when(rendicionMocked.getId()).thenReturn(0);
    when(rendicionMocked.getUsuarioRendicion()).thenReturn("");

    when(actionMappingMocked.findForward(anyString())).thenReturn(actionForwardMocked);

    try (MockedConstruction<RendicionesService> rendicionesServiceMC = Mockito.mockConstruction(RendicionesService.class,
        (mockRendicionesService, context) -> {
          doReturn(rendicionListMocked).when(mockRendicionesService).obtenerListadoRendiciones(anyString(), anyString(), eq(null), eq(null), eq(null));
          doReturn(rendicionMocked).when(rendicionListMocked).get(0);
        })) {
      //then
      ActionForward actionForwardToAssert = adjuntarImagenPopUpAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked, samWebApplicationMocked,
          samWebClientMocked,
          httpServletRequestMocked, httpServletResponseMocked);
      assertNotNull(actionForwardToAssert);
    }
  }

  @ParameterizedTest
  @MethodSource("executeActionCaratulaSource")
  @DisplayName("Should execute action caratula")
  void shouldExecuteActionCaratula(String caratula, String obtenerIdu) throws Exception {
    //given
    Rendicion rendicion = new Rendicion();
    List<Rendicion> rendicionList = new ArrayList<>();
    rendicionList.add(rendicion);
    File file = new File("src/test/resources/imagen.jpg");

    //when
    when(rendicionAvisoFormMocked.getAccion()).thenReturn("caratula");
    when(rendicionAvisoFormMocked.getRendicion()).thenReturn(rendicionMocked);
    when(rendicionMocked.getId()).thenReturn(0);
    when(rendicionMocked.getUsuarioRendicion()).thenReturn("");
    when(rendicionMocked.getAdea()).thenReturn(caratula);
    when(rendicionMocked.getIdu()).thenReturn("idu");
    when(rendicionMocked.getUsuarioRendicion()).thenReturn("");

    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    when(httpSessionMocked.getAttribute("userWorking")).thenReturn(usuarioMocked);
    when(servletContextMocked.getAttribute("rendicion.aviso.rename.archivo")).thenReturn("");
    when(servletContextMocked.getAttribute("rendicion.image.caratula")).thenReturn("src/test/resources/img.png");
    when(servletContextMocked.getAttribute("rendicion.image.idu")).thenReturn("idu");

    when(usuarioMocked.getIdUser()).thenReturn("");
    when(httpServletResponseMocked.getOutputStream()).thenReturn(servletOutputStreamMocked);
    when(fileMocked.delete()).thenReturn(false);

    when(actionMappingMocked.findForward(anyString())).thenReturn(actionForwardMocked);

    try (MockedConstruction<AprobacionesService> aprobacionesServiceMC = Mockito.mockConstruction(AprobacionesService.class,
        (mockAprobacionesService, context) -> {
          when(mockAprobacionesService.obtenerIDU(any(), anyString())).thenReturn(obtenerIdu);
        })) {

      try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
          (mockManagerTransaction, context) -> {
            doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
            when(mockManagerTransaction.getDataReturnList()).thenReturn(rendicionList);
          })) {
        try (MockedConstruction<CaratulaService> caratulaServiceMC = Mockito.mockConstruction(CaratulaService.class,
            (mockCaratulaService, context) -> {
              when(mockCaratulaService.createBarcodeImg(anyString(), anyString())).thenReturn(file);
              when(mockCaratulaService.generarCaratulaTemplate(any(RendicionAvisoForm.class), any(String[].class), anyList())).thenReturn("image");
            })) {
          //then
          ActionForward actionForwardToAssert = adjuntarImagenPopUpAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked, samWebApplicationMocked,
              samWebClientMocked,
              httpServletRequestMocked, httpServletResponseMocked);
          assertNull(actionForwardToAssert);
        }
      }
    }
  }

  @Test
  @DisplayName("Should catch exception")
  void shouldCatchException() throws Exception {
    //given
    Rendicion rendicion = new Rendicion();
    List<Rendicion> rendicionList = new ArrayList<>();
    rendicionList.add(rendicion);

    //when
    when(rendicionAvisoFormMocked.getAccion()).thenReturn("caratula");
    when(rendicionAvisoFormMocked.getRendicion()).thenReturn(rendicionMocked);
    when(rendicionMocked.getId()).thenReturn(0);
    when(rendicionMocked.getUsuarioRendicion()).thenReturn("");
    when(rendicionMocked.getAdea()).thenReturn("");
    when(rendicionMocked.getIdu()).thenReturn("idu");
    when(rendicionMocked.getUsuarioRendicion()).thenReturn("");

    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    when(httpSessionMocked.getAttribute("userWorking")).thenReturn(usuarioMocked);
    when(servletContextMocked.getAttribute("rendicion.aviso.rename.archivo")).thenReturn("");
    when(servletContextMocked.getAttribute("rendicion.image.caratula")).thenReturn("src/test/resources/img.png");
    when(servletContextMocked.getAttribute("rendicion.image.idu")).thenReturn("idu");

    when(usuarioMocked.getIdUser()).thenReturn("");
    when(httpServletResponseMocked.getOutputStream()).thenReturn(servletOutputStreamMocked);
    when(fileMocked.delete()).thenReturn(false);

    when(actionMappingMocked.findForward(anyString())).thenReturn(actionForwardMocked);

    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturnList()).thenReturn(rendicionList);
        })) {
      //then
      assertThrows(Exception.class, () -> {
        adjuntarImagenPopUpAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked, samWebApplicationMocked,
            samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
      }, "Did not throw Exception");
    }
  }
}
*/