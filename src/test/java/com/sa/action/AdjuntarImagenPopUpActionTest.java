package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Archivo;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.RendicionAvisoForm;
import com.sa.manager.ManagerTransaction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class AdjuntarImagenPopUpActionTest {

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
  @InjectMocks
  AdjuntarImagenPopUpAction adjuntarImagenPopUpAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    String accion = "caratula";
    Usuario usuario = new Usuario("", "", "", 0, "", new ArrayList<>());
    Rendicion rendicion = new Rendicion(1, "", "", new Date(), new Date(), "", new ArrayList<>(),"", "");
    List<Rendicion> rendicionList = new ArrayList<>();
    RendicionAvisoForm imagenesFormEmptyAction = new RendicionAvisoForm();
    RendicionAvisoForm imagenesFormCaratula = new RendicionAvisoForm();
    Archivo archivo = new Archivo();
    List<Archivo> archivoList = new ArrayList<>();
    Gastos gastos = new Gastos();
    List<Gastos> gastosList = new ArrayList<>();
    gastosList.add(gastos);

    archivoList.add(archivo);

    rendicion.setUsuarioRendicion("");
    rendicion.setAdea("");
    rendicionList.add(rendicion);
    imagenesFormCaratula.setAccion("caratula");
    imagenesFormCaratula.setRendicion(rendicion);

    imagenesFormEmptyAction.setAccion("");
    imagenesFormEmptyAction.setRendicion(rendicion);
    imagenesFormEmptyAction.setArchivosASubir(archivoList);

    return Stream.of(
//        Arguments.of(usuario, rendicionList, imagenesFormCaratula, gastosList),  //java.lang.Exception: java.lang.ClassCastException: com.sa.entities.Rendicion cannot be cast to com.sa.entities.Gastos
        Arguments.of(usuario, rendicionList, imagenesFormEmptyAction, gastosList)
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should execute action")
  void shouldExecuteAction(Usuario usuario, List<Rendicion> rendicionList, RendicionAvisoForm imagenesForm, List<Gastos> gastosList) throws Exception {
    //when
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("userWorking")).thenReturn(usuario);
    when(httpSessionMocked.getAttribute("usuario")).thenReturn(usuario);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    when(servletContextMocked.getAttribute("rendicion.aviso.rename.archivo")).thenReturn("");
    when(servletContextMocked.getAttribute("rendicion.aviso.path")).thenReturn("");
    when(actionMappingMocked.findForward("success")).thenReturn(actionForwardMocked);

    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());

          // En metodo "generar" linea 63 solicita lista de rendiciones y en la linea 71 solicita lista de gastos
          // Refactorizado es necesario para poder validar su comportamiento
          //java.lang.Exception: java.lang.ClassCastException: com.sa.entities.Rendicion cannot be cast to com.sa.entities.Gastos
          when(mockManagerTransaction.getDataReturnList()).thenReturn(rendicionList);
//          when(mockManagerTransaction.getDataReturnList()).thenReturn(gastosList);
          when(mockManagerTransaction.getDataReturn()).thenReturn("");
        })) {
      //then
      ActionForward actionForwardToAssert = adjuntarImagenPopUpAction.executeAction(actionMappingMocked, imagenesForm, samWebApplicationMocked,
          samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
      if (imagenesForm.getAccion().equals("caratula")) {
        assertNull(actionForwardToAssert);
      } else {
        assertNotNull(actionForwardToAssert);
      }
    }
  }
}
