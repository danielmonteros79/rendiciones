package com.sa.action;

import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Journal;
import com.sa.form.JournalForm;
import com.sa.services.AprobacionesService;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.util.TokenProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoadJournalActionTest {
    @Mock
    Log log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @InjectMocks
    LoadJournalAction loadJournalAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(HttpServletRequest request, ActionMapping mapping, List<Journal> journal, String message,JournalForm form) throws Exception {
        try(MockedConstruction<AprobacionesService> mock = Mockito.mockConstruction(AprobacionesService.class, (mockM, context) -> {
            when(mockM.getJournal(anyString())).thenReturn(journal);
            when(mockM.getMsg()).thenReturn(message);
        })) {
            ActionForward result = loadJournalAction.executeAction(mapping, form, null, null, request, null);
            assertAll(
                    () -> assertEquals("success",result.getName()),
                    () -> assertEquals("message",request.getAttribute("message")),
                    () -> assertEquals(journal,request.getAttribute("journal")),
                    () -> assertEquals("codigo",form.getIdRendicion())
            );
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action exception")
    void executeActionException(HttpServletRequest request, ActionMapping mapping, List<Journal> journal, String message,JournalForm form) throws Exception {
        try(MockedConstruction<AprobacionesService> mock = Mockito.mockConstruction(AprobacionesService.class, (mockM, context) -> {
            when(mockM.getJournal(anyString())).thenThrow(new TransactionException("TransactionException",new Throwable("TransactionException")));
        })) {
            ActionForward result = loadJournalAction.executeAction(mapping, form, null, null, request, null);
            assertAll(
                    () -> assertEquals("success",result.getName()),
                    () -> assertEquals("ERROR: TransactionException",request.getAttribute("message")),
                    () -> assertEquals("codigo",form.getIdRendicion())
            );
        }
    }

    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        JournalForm frm = new JournalForm();
        ActionMapping mapping = new ActionMapping();
        List<Journal> journal = new ArrayList<>();
        String message = "message";

        request.addParameter("codigo","codigo");

        mapping.addForwardConfig(new ActionForward("success", "path1", false));

        return Stream.of(
                Arguments.of(request,mapping,journal,message,frm)
        );
    }
}
