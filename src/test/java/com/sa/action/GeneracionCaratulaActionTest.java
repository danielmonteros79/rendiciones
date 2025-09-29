package com.sa.action;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.sa.entities.Usuario;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.apache.struts.mock.MockHttpSession;
import org.apache.struts.util.TokenProcessor;
import org.displaytag.filter.SimpleServletOutputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GeneracionCaratulaActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @Mock
    HttpServletResponse response;
    @Mock
    PdfWriter pdfWriterMock;
    @Mock
    Image imageMock;
    @InjectMocks
    GeneracionCaratulaAction generacionCaratulaAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(HttpServletRequest request) throws Exception {
        when(response.getOutputStream()).thenReturn(new SimpleServletOutputStream());
        try (MockedStatic<PdfWriter> pdfWriterMockedStatic = Mockito.mockStatic(PdfWriter.class)) {
            pdfWriterMockedStatic.when(() -> PdfWriter.getInstance(any(), any())).thenReturn(pdfWriterMock);
            try (MockedStatic<Image> imageMockedStatic = mockStatic(Image.class)) {
                imageMockedStatic.when(() -> Image.getInstance(anyString())).thenReturn(imageMock);
                ActionForward result = generacionCaratulaAction.executeAction(null, null, null, null, request, response);
                assertAll(
                        () -> assertEquals(null, result)
                );
            }
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action exception")
    void executeActionException(HttpServletRequest request) throws Exception {
        try (MockedStatic<PdfWriter> pdfWriterMockedStatic = Mockito.mockStatic(PdfWriter.class)) {
            pdfWriterMockedStatic.when(() -> PdfWriter.getInstance(any(), any())).thenReturn(pdfWriterMock);
            try (MockedStatic<Image> imageMockedStatic = mockStatic(Image.class)) {
                imageMockedStatic.when(() -> Image.getInstance(anyString())).thenReturn(imageMock);

                assertThrows(Exception.class, () -> {
                    generacionCaratulaAction.executeAction(null, null, null, null, request, response);
                });
            }
        }
    }

    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());

        session.setAttribute("usuario", usuario);
        session.setAttribute("templateCaratula", "templateCaratula");
        request.setHttpSession(session);

        return Stream.of(
                Arguments.of(request)
        );
    }


}