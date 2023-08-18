package com.sa.action.parametros;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroExceptuado;
import com.sa.form.parametros.ParametrosExceptuadosFiltroForm;
import com.sa.services.ParametrosService;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpSession;
import org.apache.struts.mock.MockServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParametrosExceptuadosFiltroActionTest {

    @Mock
    HttpServletResponse httpServletResponseMocked;
    @Mock
    HttpServletRequest httpServletRequestMocked;
    @Mock
    PrintWriter printWriterMocked;
    @InjectMocks
    ParametrosExceptuadosFiltroAction parametrosExceptuadosFiltroAction;

    public static Stream<Arguments> executeActionSource() {
        //given
        ActionMapping actionMapping = new ActionMapping();
        SAMWebApplication samWebApplication = new SAMWebApplication();
        HttpSession httpSession = new MockHttpSession();
        SAMWebClient samWebClient = new SAMWebClient();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletRequest request2 = new MockHttpServletRequest();
        List<ParametroExceptuado> exceptuados = new ArrayList<>();
        ParametrosExceptuadosFiltroForm parametrosExceptuadosFiltroForm = new ParametrosExceptuadosFiltroForm();
        Usuario userWorking = new Usuario("1", "user", "user", 1, "user", new ArrayList<>());

        request.addParameter("action", "filtrar");
        request.addParameter("marca", "marca");
        request.addParameter("motivoUsuario", "motivoUsuario");


        request2.addParameter("action", "action");
        request2.addParameter("marca", "marca");
        request2.addParameter("motivoUsuario", "motivoUsuario");

        actionMapping.addForwardConfig(new ActionForward("success", "path1", false));
        actionMapping.addForwardConfig(new ActionForward("exceptuados", "path1", false));

        return Stream.of(
            Arguments.of(actionMapping, parametrosExceptuadosFiltroForm, samWebApplication, samWebClient, request, exceptuados, userWorking),
            Arguments.of(actionMapping, parametrosExceptuadosFiltroForm, samWebApplication, samWebClient, request2, exceptuados, userWorking)
                        );
    }

    public static Stream<Arguments> executeActionExceptionSource() {
        //given
        ActionMapping actionMapping = new ActionMapping();
        SAMWebApplication samWebApplication = new SAMWebApplication();
        HttpSession httpSession = new MockHttpSession();
        SAMWebClient samWebClient = new SAMWebClient();
        MockHttpServletRequest request = new MockHttpServletRequest();
        List<Usuario> delegados = new ArrayList<>();
        ServletContext servletContext = new MockServletContext();

        ParametrosExceptuadosFiltroForm parametrosExceptuadosFiltroForm = new ParametrosExceptuadosFiltroForm();
        parametrosExceptuadosFiltroForm.setExceptuadoFiltro("filtro");
        parametrosExceptuadosFiltroForm.setMotivoUsuario("motivo");

        ParametrosExceptuadosFiltroForm parametrosExceptuadosFiltroFormFiltroNull = new ParametrosExceptuadosFiltroForm();
        parametrosExceptuadosFiltroFormFiltroNull.setExceptuadoFiltro(null);
        parametrosExceptuadosFiltroFormFiltroNull.setMotivoUsuario("motivo");

        ParametroExceptuado parametroExceptuado = new ParametroExceptuado();
        parametroExceptuado.setEstado("");
        parametroExceptuado.setTipo("");
        parametroExceptuado.setHasta(new Date());
        parametroExceptuado.setDesde(new Date());
        parametroExceptuado.setDescripcionNombre("");
        parametroExceptuado.setMotivoUsuario("");

        List<ParametroExceptuado> parametroExceptuadoList = new ArrayList<>();
        parametroExceptuadoList.add(parametroExceptuado);

        Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
        Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
        delegados.add(usuario2);
        delegados.add(usuario3);
        Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", delegados);

        httpSession.setAttribute("usuario", usuario);

        request.setHttpSession(httpSession);
        request.addParameter("accion", "");
        request.addParameter("codMotivo", "MOTIVOMOTIVO");

        actionMapping.addForwardConfig(new ActionForward("success", "path1", false));

        samWebClient.setSession(httpSession);
        samWebClient.setLoginOk(true);
        samWebClient.setId("55");
        samWebClient.setAttribute("usuario", usuario);

        samWebApplication.setContext(servletContext);
        samWebApplication.setClientClass("");
        samWebApplication.setAttribute("usuario", usuario);

        return Stream.of(
            Arguments.of(actionMapping, samWebApplication, samWebClient, request, parametrosExceptuadosFiltroForm, usuario, parametroExceptuadoList),
            Arguments.of(actionMapping, samWebApplication, samWebClient, request, parametrosExceptuadosFiltroFormFiltroNull, usuario, parametroExceptuadoList)
                        );
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Should determine what action execute")
    void shouldDetermineWhatActionExecute(ActionMapping actionMapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient, MockHttpServletRequest request, List<ParametroExceptuado> exceptuados, Usuario userWorking) throws Exception {
        //when
        parametrosExceptuadosFiltroAction.setSessionUserWorking(userWorking);
        try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class,
            (mockParametrosService, context) -> {
                when(mockParametrosService.getExceptuado(any(), any(), any())).thenReturn(exceptuados);
                when(mockParametrosService.getMsgAviso()).thenReturn("Message");
            })) {
            //then
            ActionForward actionForwardToAssert = parametrosExceptuadosFiltroAction.executeAction(actionMapping, form, samApplication, samClient, request,
                httpServletResponseMocked);
            assertNotNull(actionForwardToAssert);
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionExceptionSource")
    @DisplayName("Should catch an Exception")
    void shouldCatchAnException(ActionMapping actionMapping, SAMWebApplication samApplication, SAMWebClient samClient, MockHttpServletRequest request,
                                ParametrosExceptuadosFiltroForm parametrosExceptuadosFiltroForm, Usuario usuario,
                                List<ParametroExceptuado> parametroExceptuadoList) throws Exception {
        //when
        doThrow(new NullPointerException()).when(httpServletRequestMocked).getParameter("action");
        when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
        //then
        ActionForward actionForwardToAssert = parametrosExceptuadosFiltroAction.executeAction(actionMapping, parametrosExceptuadosFiltroForm, samApplication, samClient,
            httpServletRequestMocked, httpServletResponseMocked);
        assertNull(actionForwardToAssert);
    }
}
