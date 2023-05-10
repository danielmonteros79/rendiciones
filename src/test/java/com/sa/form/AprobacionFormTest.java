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
    @DisplayName("Testeando get motivo rechazo")
    void getMotivoRechazo() {
        aprobacionFormReal.setMotivoRechazo("Motivo de rechazo");
        String motivoRechazo = aprobacionFormReal.getMotivoRechazo();

        assertAll(
                () -> assertNotNull(motivoRechazo),
                () -> assertEquals("Motivo de rechazo", motivoRechazo)
        );
    }

    @Test
    void setMotivoRechazo() {
    }

    @Test
    void getId() {
    }

    @Test
    void setId() {
    }

    @Test
    void getEstado() {
    }

    @Test
    void setEstado() {
    }

    @Test
    void getCheck() {
    }

    @Test
    void setCheck() {
    }

    @Test
    void setGlg() {
    }

    @Test
    void getGlg() {
    }

    @Test
    void getCmboMotivo() {
    }

    @Test
    void setCmboMotivo() {
    }
}