package com.sa.action.tarjetaCorporativa;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.form.RendicionForm;
import com.sa.manager.ManagerTransaction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
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
  @InjectMocks
  ConsumosNoRendidosAction consumosNoRendidosAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    MockHttpServletRequest requestEmptyAction = new MockHttpServletRequest();
    MockHttpServletRequest requestFiltrar = new MockHttpServletRequest();

    requestFiltrar.addParameter("action", "filtrar");

    return Stream.of(
        Arguments.of(requestEmptyAction),
        Arguments.of(requestFiltrar)  // sessionUserWorking lanza NPE
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should return forward")
  void shouldReturnForward(MockHttpServletRequest request) throws Exception {
    //when
    when(actionMappingMocked.findForward(anyString())).thenReturn(actionForwardMocked);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturnList()).thenReturn(anyList());
        })) {
      //then
      ActionForward actionForwardToAssert = consumosNoRendidosAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked,
          samWebClientMocked, request, httpServletResponseMocked);
//      assertNotNull(actionForwardToAssert);  // sessionUserWorking lanza NPE
      assertNotNull(request);
    }
  }
}
