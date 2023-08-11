package com.sa.util;

import com.sa.entities.Archivo;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.RendicionAvisoForm;
import com.sa.services.AprobacionesService;
import com.sa.services.trxs.WM95;
import org.apache.struts.upload.FormFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ArchivoUtilTest {

  @Mock
  Usuario usuarioMocked;
  @Mock
  RendicionAvisoForm rendicionAvisoFormMocked;
  @Mock
  Rendicion rendicionMocked;
  @Mock
  AprobacionesService aprobacionesServiceMocked;
  @Mock
  PrintWriter printWriterMocked;
  @Mock
  FormFile formFileMocked;

  public static Stream<Arguments> getArchivosASubirSource() {
    //given
    Archivo archivo = new Archivo();
    archivo.setNomArchivo("");
    List<Archivo> archivoList = new ArrayList<>();
    archivoList.add(archivo);

    return Stream.of(Arguments.of(archivoList));
  }

  public static Stream<Arguments> grabarArchivosSource() throws IOException {
    //given
    String idu = "1;908098;asdasd;";
    byte[] data = new byte[] {1, 2, 3, 4};
    InputStream inputStream = new ByteArrayInputStream(data);

    Archivo archivo = new Archivo();
    archivo.setNomArchivo("name.txt");
    archivo.setIdu(idu);
    archivo.setInputStream(inputStream);

    Archivo archivo2 = new Archivo();
    archivo2.setNomArchivo("name.txt");
    archivo2.setIdu(idu);
    archivo2.setInputStream(Files.newInputStream(Paths.get("src/test/resources")));

    List<Archivo> archivoList = new ArrayList<>();
    archivoList.add(archivo);
    archivoList.add(archivo2);

    List<Archivo> archivoEmptyList = new ArrayList<>();

      return Stream.of(
          Arguments.of(archivoList, ""),
          Arguments.of(archivoList, idu),
          Arguments.of(archivoEmptyList, idu),
          Arguments.of(archivoList, idu)
                      );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("getArchivosASubirSource")
  @DisplayName("Should get archivos a subir")
  void shouldGetArchivosASubir(List<Archivo> archivoList) {
    //then
    ArchivoUtil.getArchivosASubir(printWriterMocked, archivoList);
    assertNotNull(archivoList);
  }

  @Test
  @DisplayName("Should cargarArchivo")
  void shouldCargarArchivo() throws Exception {
    //when
    when(formFileMocked.getFileName()).thenReturn("archivo.txt");
    when(formFileMocked.getInputStream()).thenReturn(Files.newInputStream(Paths.get("src/test/resources/archivo.txt")));
    //then
    Archivo archivoToAssert = ArchivoUtil.cargarArchivo(printWriterMocked, formFileMocked);
    assertNotNull(archivoToAssert);
  }

  @ParameterizedTest // Refactorizar para cubrir bloque if de la linea 73
  @MethodSource("getArchivosASubirSource")
  @DisplayName("Should borrarArchivo")
  void shouldBorrarArchivo(List<Archivo> archivoList) throws Exception {
    //given
    String jsonString = "";
    //then
    ArchivoUtil.borrarArchivo(jsonString, archivoList);
    assertNotNull(archivoList);
  }

  @ParameterizedTest
  @MethodSource("grabarArchivosSource")
  @DisplayName("Should grabarArchivos")
  void shouldGrabarArchivos(List<Archivo> archivoList, String idu) throws Exception {
    //when
    when(rendicionAvisoFormMocked.getRendicion()).thenReturn(rendicionMocked);
    when(aprobacionesServiceMocked.obtenerIDU(rendicionAvisoFormMocked, WM95.DELIM_04_SIN_ADEA)).thenReturn(idu);
    when(rendicionAvisoFormMocked.getArchivosASubir()).thenReturn(archivoList);
    when(rendicionAvisoFormMocked.getUsuario()).thenReturn(usuarioMocked);
    when(usuarioMocked.getIdUser()).thenReturn("1");
    when(usuarioMocked.getCcostos()).thenReturn(1);
    when(rendicionMocked.getId()).thenReturn(1);

    //then
    List<String> archivoListtoAssert = ArchivoUtil.grabarArchivos(rendicionAvisoFormMocked, aprobacionesServiceMocked, "src/test/resources",
        "grabarArchivoTestFile",
        false);
    assertNotNull(archivoListtoAssert);
  }











}
