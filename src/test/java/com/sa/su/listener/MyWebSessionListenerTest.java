package com.sa.su.listener;

import ar.com.itrsa.sam.IContext;
import ar.com.itrsa.sam.IServiceAccessManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MyWebSessionListenerTest {

  @Mock
  HttpSessionEvent httpSessionEventMocked;
  @Mock
  HttpSession httpSessionMocked;
  @Mock
  ServletContext servletContextMocked;
  @Mock
  IServiceAccessManager iServiceAccessManagerMocked;
  @Mock
  IContext iContextMocked;
  @InjectMocks
  MyWebSessionListener myWebSessionListener;

  public static Stream<Arguments> sessionDestroyedSource() {
    //given
    boolean liberarRecursos = true;


    return Stream.of(
        Arguments.of(liberarRecursos),
        Arguments.of(!liberarRecursos)
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("sessionDestroyedSource")
  @DisplayName("Should destroy the session")
  void shouldDestroyTheSession(boolean liberarRecursos) {
    //when
    when(httpSessionEventMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("INVOCAR_LIBERAR_RECURSOS")).thenReturn(liberarRecursos);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    when(servletContextMocked.getAttribute("SAM")).thenReturn(getSAM(liberarRecursos));
    when(httpSessionMocked.getAttribute("SAM_CONTEXT")).thenReturn(iContextMocked);
    //then
    myWebSessionListener.sessionDestroyed(httpSessionEventMocked);
    assertNotNull(httpSessionEventMocked.getSession());
  }

  private IServiceAccessManager getSAM(boolean sam) {
    return !sam ? iServiceAccessManagerMocked : null;
  }

  @Test
  @DisplayName("Should catch an exception")
  void shouldCatchAnException() {
    //when
    when(httpSessionEventMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("INVOCAR_LIBERAR_RECURSOS")).thenReturn("true");
    //then
    myWebSessionListener.sessionDestroyed(httpSessionEventMocked);
    assertNotNull(httpSessionEventMocked.getSession());
  }
}
