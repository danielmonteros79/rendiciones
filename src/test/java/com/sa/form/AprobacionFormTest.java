package test.java.com.sa.form;

import com.sa.form.AprobacionForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;

class AprobacionFormTest {

    AprobacionForm aprobacionFormReal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        aprobacionFormReal = new AprobacionForm();
    }

    @Test
    @DisplayName("Testeando set y get motivo rechazo")
    void getMotivoRechazo() {
        aprobacionFormReal.setMotivoRechazo("Motivo de rechazo");
        String motivoRechazo = aprobacionFormReal.getMotivoRechazo();

        assertAll(
                () -> assertNotNull(motivoRechazo),
                () -> assertEquals("Motivo de rechazo", motivoRechazo)
        );
    }

    @Test
    @DisplayName("Testeando set y get id")
    void getId() {
        aprobacionFormReal.setId(1);
        Integer id = aprobacionFormReal.getId();

        assertAll(
                () -> assertNotNull(id),
                () -> assertEquals(1, id)
        );
    }


    @Test
    @DisplayName("Testeando set y get estado")
    void getEstado() {
        aprobacionFormReal.setEstado("Estado");
        String estado = aprobacionFormReal.getEstado();

        assertAll(
                () -> assertNotNull(estado),
                () -> assertEquals("Estado", estado)
        );
    }

    @Test
    @DisplayName("Testeando set y get check")
    void getCheck() {
        aprobacionFormReal.setCheck("Check");
        String check = aprobacionFormReal.getCheck();

        assertAll(
                () -> assertNotNull(check),
                () -> assertEquals("Check", check)
        );
    }

    @Test
    @DisplayName("Testeando set y get glg")
    void getGlg() {
        aprobacionFormReal.setGlg("glg");
        String glg = aprobacionFormReal.getGlg();

        assertAll(
                () -> assertNotNull(glg),
                () -> assertEquals("glg", glg)
        );
    }


    @Test
    @DisplayName("Testeando set y get cmbo motivo")
    void getCmboMotivo() {
        aprobacionFormReal.setCmboMotivo("cmboMotivo");
        String cmboMotivo = aprobacionFormReal.getCmboMotivo();

        assertAll(
                () -> assertNotNull(cmboMotivo),
                () -> assertEquals("cmboMotivo", cmboMotivo)
        );
    }

}