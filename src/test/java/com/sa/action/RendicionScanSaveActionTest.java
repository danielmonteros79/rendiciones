package com.sa.action;

import ar.com.bbva.web.impl.SAMWebClient;
import com.itextpdf.text.*;
import com.itextpdf.text.html.simpleparser.ChainedProperties;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.FiltrarAprobacionForm;
import com.sa.form.RendicionForm;
import com.sa.services.AprobacionesService;
import com.sa.services.RendicionesService;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.mock.MockHttpServletRequest;
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
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RendicionScanSaveActionTest {
    @Mock
    Log log;
    @Mock
    RendicionForm rf;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @Mock
    HttpServletResponse response;
    @Mock
    SAMWebClient client;
    @Mock
    PdfWriter mockPdfWriter;
    @Mock
    XMLWorkerHelper xmlWorkerHelperMock;
    @Mock
    Image imageMock;
    @InjectMocks
    RendicionScanSaveAction rendicionScanSaveAction;
    @InjectMocks
    RendicionScanSaveAction.MyImageFactory myImageFactory;
    @InjectMocks
    RendicionScanSaveAction.MyFontFactory myFontFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(HttpServletRequest request,RendicionForm form, ActionMapping mapping) throws Exception {
        when(response.getOutputStream()).thenReturn(new SimpleServletOutputStream());
        try (MockedConstruction<AprobacionesService> aprobacionesServiceMC = Mockito.mockConstruction(AprobacionesService.class, (mockAprobacionesService, context) -> {
        })) {
            try (MockedConstruction<RendicionesService> rendicionesServiceMC = mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
            })) {
                try (MockedStatic<PdfWriter> pdfWriterMockedStatic = mockStatic(PdfWriter.class)) {
                    pdfWriterMockedStatic.when(() -> PdfWriter.getInstance(any(),any())).thenReturn(mockPdfWriter);
                    doNothing().when(mockPdfWriter).flush();
                    doNothing().when(mockPdfWriter).close();
                    try (MockedStatic<XMLWorkerHelper> xmlWorkerHelperMockedStatic = mockStatic(XMLWorkerHelper.class)) {
                        xmlWorkerHelperMockedStatic.when(() -> XMLWorkerHelper.getInstance()).thenReturn(xmlWorkerHelperMock);
                        doNothing().when(xmlWorkerHelperMock).parseXHtml(any(),any(),any(),any(Charset.class));

                        ActionForward result = rendicionScanSaveAction.executeAction(mapping, form, null, client, request, response);
                        Assertions.assertEquals(null, result);
                    }
                }
            }
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action exception")
    void executeActionException(HttpServletRequest request,RendicionForm form, ActionMapping mapping) throws Exception {
                try (MockedStatic<PdfWriter> pdfWriterMockedStatic = mockStatic(PdfWriter.class)) {
                    pdfWriterMockedStatic.when(() -> PdfWriter.getInstance(any(),any())).thenReturn(mockPdfWriter);
                    doNothing().when(mockPdfWriter).flush();
                    doNothing().when(mockPdfWriter).close();
                    try (MockedStatic<XMLWorkerHelper> xmlWorkerHelperMockedStatic = mockStatic(XMLWorkerHelper.class)) {
                        xmlWorkerHelperMockedStatic.when(() -> XMLWorkerHelper.getInstance()).thenReturn(xmlWorkerHelperMock);
                        doNothing().when(xmlWorkerHelperMock).parseXHtml(any(),any(),any(),any(Charset.class));

                        assertThrows(Exception.class, () -> {
                            rendicionScanSaveAction.executeAction(mapping, form, null, client, request, response);
                        });
                    }
                }
    }

    @Test
    @DisplayName("Testeando get image")
    void getImage() throws Exception {
        try (MockedStatic<Image> imageMockedStatic = mockStatic(Image.class)) {
            imageMockedStatic.when(() -> Image.getInstance(anyString())).thenReturn(imageMock);

            Image image = myImageFactory.getImage("a/",new HashMap<>(),new ChainedProperties(),new Document());
            Assertions.assertEquals(imageMock, image);
        }

    }

    @Test
    @DisplayName("Testeando get image io exception")
    void getImageIOException() throws Exception {
        try (MockedStatic<Image> imageMockedStatic = mockStatic(Image.class)) {
            imageMockedStatic.when(() -> Image.getInstance(anyString())).thenThrow(new IOException(""));

            Image image = myImageFactory.getImage("a/",new HashMap<>(),new ChainedProperties(),new Document());
            Assertions.assertNull(image);
        }
    }

    @Test
    @DisplayName("Testeando get image document exception")
    void getImageDocumentException() throws Exception {
        try (MockedStatic<Image> imageMockedStatic = mockStatic(Image.class)) {
            imageMockedStatic.when(() -> Image.getInstance(anyString())).thenThrow(new IOException("IO processing error"));

            Image image = myImageFactory.getImage("a/",new HashMap<>(),new ChainedProperties(),new Document());
            Assertions.assertNull(image);
        }
    }

    @Test
    @DisplayName("Testeando get font")
    void getFont() throws Exception {
        Font font = myFontFactory.getFont("fontName","encoding",true,1,1, BaseColor.BLACK);
        assertAll(
                () -> Assertions.assertEquals("Times-Roman", font.getFamilyname()),
                () -> Assertions.assertEquals(1, font.getSize()),
                () -> Assertions.assertEquals(1, font.getStyle()),
                () -> Assertions.assertEquals(BaseColor.BLACK, font.getColor())
        );
    }

    @Test
    @DisplayName("Testeando is registered")
    void isRegistered() throws Exception {
        boolean result = myFontFactory.isRegistered("fontName");
        assertFalse(result);
    }

    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        RendicionForm form = new RendicionForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        ActionMapping mapping = new ActionMapping();

        session.setAttribute("usuario", usuario);

        request.setHttpSession(session);

        form.setNameFile("nameFile");
        form.setFileType("fileType");

        mapping.addForwardConfig(new ActionForward("success", "path1", false));


        return Stream.of(
                Arguments.of(request,form,mapping)
        );
    }
}

