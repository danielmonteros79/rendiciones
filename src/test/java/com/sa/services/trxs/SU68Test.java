package com.sa.services.trxs;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import ar.org.bbva.util.DateUtils;
import com.sa.entities.Cupones;
import com.sa.entities.parametros.Resumen;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneOffset;

import java.util.*;

import java.util.stream.Stream;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SU68Test {

    @Test
    void testConstructor() throws Exception {
        SU68 actualSu68 = new SU68();
        actualSu68.hardcodear(new HashMap<>());
        assertTrue(actualSu68.getDataReturnList().isEmpty());
    }

    @Test
    void testConstructor2() {
        assertTrue((new SU68()).getDataReturnList().isEmpty());
    }

    @Test
    void testExecuteTrx() throws TransactionException {
        SU68 su68 = new SU68();
        SAMWebClient client = new SAMWebClient();
        assertThrows(TransactionException.class, () -> su68.executeTrx(client, new HashMap<>()));
    }

    @Test
    void testExecuteTrx2() throws TransactionException {
        SU68 su68 = new SU68();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "pantalla", (Object) "resumen");
        parametersExecute.put((String) "lista", null);
        parametersExecute.put((String) "montoMin", null);
        parametersExecute.put((String) "moneda", null);
        assertThrows(TransactionException.class, () -> su68.executeTrx(client, parametersExecute));
    }

    @Test
    void testSetInfoResumenes() throws ParseException {
        SU68 su68 = new SU68();

        String str = StringUtils.repeat("1",103) + "00020230101" + StringUtils.repeat("1",127) + "01/01/2023" + StringUtils.repeat("1",20);

        Resumen resumenParams = new Resumen();
        su68.setInfoResumenes(str, resumenParams);

        assertAll(
                () -> assertEquals("111111111111111,10", resumenParams.getMonto()),
                () -> assertEquals("111111111111111111111111111111", resumenParams.getEstablecimiento()),
                () -> assertEquals("111111111111", resumenParams.getEstado()),
                () -> assertEquals("111", resumenParams.getMoneda()),
                () -> assertEquals("111111111111", resumenParams.getCupon()),
                () -> assertEquals("1111111111111111", resumenParams.getIdRendicion()),
                () -> assertNotNull( resumenParams.getFecha()),
                () -> assertNotNull( resumenParams.getFechaDebito())
        );
    }

    @Test
    void testSetInfoCupon() {
        String str = StringUtils.repeat("1",250);

        SU68 su68 = new SU68();
        Cupones cupon = new Cupones();

        su68.setInfoCupon(str, cupon);

        assertAll(
                () -> assertNotNull(cupon.getTipo()),
                () -> assertNotNull(cupon.getNroCupon()),
                () -> assertNotNull(cupon.getNroTarjeta()),
                () -> assertNotNull(cupon.getNroCliente()),
                () -> assertNotNull(cupon.getFechaPresentacion()),
                () -> assertNotNull(cupon.getFechaCierre()),
                () -> assertNotNull(cupon.getMoneda()),
                () -> assertNotNull(cupon.getDisponible()),
                () -> assertNotNull(cupon.getEstablecimiento()),
                () -> assertNotNull(cupon.getTipoConsumo()),
                () -> assertNotNull(cupon.getMarcaFacturado()),
                () -> assertNotNull(cupon.getNroCuponCredito()),
                () -> assertNotNull(cupon.getLiquidacionCredito()),
                () -> assertNotNull(cupon.getNroCuponDebito()),
                () -> assertNotNull(cupon.getLiquidacionDebito()),
                () -> assertNotNull(cupon.getLiquidacionNeto()),
                () -> assertNotNull(cupon.getTipoMovimiento()),
                () -> assertNotNull(cupon.getCodAdmin()),
                () -> assertNotNull(cupon.getCodigoAutorizacion()),
                () -> assertNotNull(cupon.getCuentaCredito())
        );
    }

    @ParameterizedTest
    @MethodSource("mapDatasource")
    @DisplayName("Testeando mapData")
    void testMapData(Map<String, Object> parametersExecute) throws Exception {
        SU68 su68 = new SU68();
        su68.mapData(parametersExecute);

        assertNotNull(parametersExecute);
    }

    // ------ Sources ------


    private static Stream<Arguments> mapDatasource() {
        Map<String, Object> parametersExecute = new HashMap<>();
        Map<String, Object> parametersExecute2 = new HashMap<>();
        Map<String, Object> parametersExecute3 = new HashMap<>();

        List lista = new ArrayList();
        String str = StringUtils.repeat("1",103) + "00020230101" + StringUtils.repeat("1",127) + "01/01/2023" + StringUtils.repeat("1",20);

        DynaProperty[] dynaProperties = new DynaProperty[1];
        dynaProperties[0] = new DynaProperty("lista", String.class);

        BasicDynaBean bean = new BasicDynaBean(new BasicDynaClass("", null, dynaProperties));
        bean.set("lista",str);

        lista.add(bean);

        parametersExecute.put("pantalla","resumen");
        parametersExecute.put("lista",lista);

        parametersExecute2.put("pantalla","resumen");

        parametersExecute3.put("pantalla","pantalla");
        parametersExecute3.put("lista",lista);
        parametersExecute3.put("montoMin","");
        parametersExecute3.put("moneda","moneda");

        return Stream.of(
                Arguments.of(parametersExecute),
                Arguments.of(parametersExecute2),
                Arguments.of(parametersExecute3)
        );
    }


}

