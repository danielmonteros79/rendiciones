package com.sa.services;

import ar.com.bbva.web.impl.SAMWebClient;

//import com.sa.action.RendicionAvisoAction;
import com.sa.entities.Cupones;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.ImagenesForm;
import com.sa.form.RendicionAvisoForm;
import com.sa.util.CaratulaTemplate;
import org.apache.commons.logging.Log;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CaratulaServiceTest {
    @InjectMocks
    CaratulaService caratulaService;

    @Spy
    SAMWebClient samWebClient;

    @Mock
    File imgBarcode;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @ParameterizedTest
    @MethodSource("generarCaratulaTemplateSource")
    @DisplayName("Testeando generar caratula template")
    void generarCaratulaTemplate(RendicionAvisoForm frm, String[] iduAdea, List<Gastos> gastos, String html) {
        String result = caratulaService.generarCaratulaTemplate(frm, iduAdea, gastos);
        Assertions.assertEquals(html, result);
    }

    @ParameterizedTest
    @MethodSource("getDescripComprobanteSource")
    @DisplayName("Testeando get descrip comprobante")
    void getDescripComprobante(String comprobante, String resp) {
        String result = caratulaService.getDescripComprobante(comprobante);
        Assertions.assertEquals(resp, result);
    }

    @Test
    @Disabled("No se puede mockear el valor de imgBarcode")
    void testCreateBarcodeImg() throws ServiceException {
        File result = caratulaService.createBarcodeImg("pathTemp", "codigoBarra");
        Assertions.assertEquals(new File(getClass().getResource("/com/sa/services/PleaseReplaceMeWithTestFile.txt").getFile()), result);
    }

    // ------ Sources ------

    private static Stream<Arguments> generarCaratulaTemplateSource() {
        RendicionAvisoForm frm = new RendicionAvisoForm();
        String[] iduAdea = new String[] {"idu", "adea"};
        List<Gastos> gastos = new ArrayList<>();

        Rendicion rendicion = new Rendicion();
        rendicion.setId(1);
        rendicion.setMotivo("motivo");
        rendicion.setFechaDesde(new Date());
        rendicion.setFechaHasta(new Date());
        rendicion.setDescripcion("descripcion");

        Usuario usuario = new Usuario("1","perfil","nombre",1,"sector",new ArrayList<>());

        frm.setRendicion(rendicion);
        frm.setUsuario(usuario);

        Gastos gastos1 = new Gastos();
        gastos1.setDescGasto("descGasto1");
        gastos1.setMonto("1");
        gastos1.setFechagastos("fechagastos1");
        gastos1.setComprobante("FACTU");
        gastos1.setTarjeta("N");

        Gastos gastos2 = new Gastos();
        gastos2.setDescGasto("descGasto2");
        gastos2.setMonto("2");
        gastos2.setFechagastos("fechagastos2");
        gastos2.setComprobante("MAIL-");
        gastos2.setTarjeta("TARJ CORP");

        Gastos gastos3 = new Gastos();
        gastos3.setDescGasto("descGasto3");
        gastos3.setMonto("3");
        gastos3.setFechagastos("fechagastos3");
        gastos3.setComprobante("SCOMP");
        gastos3.setTarjeta("TARJ CORP");

        Gastos gastos4 = new Gastos();
        gastos4.setDescGasto("descGasto4");
        gastos4.setMonto("4");
        gastos4.setFechagastos("fechagastos4");
        gastos4.setComprobante("TICK-");
        gastos4.setTarjeta("TARJ CORP");

        Gastos gastos5 = new Gastos();
        gastos5.setDescGasto("descGasto5");
        gastos5.setMonto("5");
        gastos5.setFechagastos("fechagastos5");
        gastos5.setComprobante("FOBL");
        gastos5.setTarjeta("TARJ CORP");

        gastos.addAll(Arrays.asList(gastos1, gastos2, gastos3, gastos4, gastos5));

        // Modifico el string html para comparar
        String html = CaratulaTemplate.CARATULA_HTML;
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaHoy = new Date();
        html = html.replace(CaratulaTemplate.REPLACE_RENDICION, String.valueOf(frm.getRendicion().getId()));
        html = html.replace(CaratulaTemplate.REPLACE_USUARIO, frm.getUsuario().getIdUser() + " - " + frm.getUsuario().getNombre());
        html = html.replace(CaratulaTemplate.REPLACE_CCOSTOS, String.valueOf(frm.getUsuario().getCcostos()));
        html = html.replace(CaratulaTemplate.REPLACE_MOTIVO, frm.getRendicion().getMotivo());
        html = html.replace(CaratulaTemplate.REPLACE_FDESDE, f.format(frm.getRendicion().getFechaDesde()));
        html = html.replace(CaratulaTemplate.REPLACE_FHASTA, f.format(frm.getRendicion().getFechaHasta()));
        html = html.replace(CaratulaTemplate.REPLACE_DESCRIPCION, frm.getRendicion().getDescripcion());
        html = html.replace(CaratulaTemplate.REPLACE_FECHAHOY, f.format(fechaHoy));

        String trGastos = "";
        for (Gastos g : gastos) {
            String comp = "";
            if (g.getComprobante().equals("FACTU")) {
                comp = "FACTURA";
            } else if (g.getComprobante().equals("MAIL-")) {
                comp = "MAIL";
            } else if (g.getComprobante().equals("SCOMP")) {
                comp = "SIN COMPROBANTE";
            } else if (g.getComprobante().equals("TICK-")) {
                comp = "TICKET";
            } else if (g.getComprobante().equals("FOBL")) {
                comp = "FACTURA OBLIGATORIA";
            }

            trGastos += "<tr>";

            trGastos += "<td>" + g.getDescGasto() + "</td>";
            trGastos += "<td style='text-align:right;'>$" + g.getMonto() + "</td>";
            trGastos += "<td style='text-align:center;'>" + g.getFechagastos() + "</td>";
            trGastos += "<td>" + comp + "</td>";
            trGastos += "<td>" + (g.getTarjeta().equals("N") ? "EFECTIVO" : "TARJ CORP") + "</td>";

            trGastos += "</tr>";
        }
        html = html.replace(CaratulaTemplate.REPLACE_GASTOS, trGastos);

        return Stream.of(
                Arguments.of(frm, iduAdea, gastos,html)
        );
    }

    private static Stream<Arguments> getDescripComprobanteSource(){
        String comprobante1 = "FACTU";
        String resp1 = "FACTURA";

        String comprobante2 = "MAIL-";
        String resp2 = "MAIL";

        String comprobante3 = "SCOMP";
        String resp3 = "SIN COMPROBANTE";

        String comprobante4 = "TICK-";
        String resp4 = "TICKET";

        String comprobante5 = "FOBL";
        String resp5 = "FACTURA OBLIGATORIA";

        String comprobante6 = "OTRO";
        String resp6 = "OTRO";

        return Stream.of(
                Arguments.of(comprobante1, resp1),
                Arguments.of(comprobante2, resp2),
                Arguments.of(comprobante3, resp3),
                Arguments.of(comprobante4, resp4),
                Arguments.of(comprobante5, resp5),
                Arguments.of(comprobante6, resp6)
        );

    }
}