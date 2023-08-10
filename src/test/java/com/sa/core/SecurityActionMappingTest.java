package com.sa.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SecurityActionMappingTest {

  @InjectMocks
  SecurityActionMapping securityActionMapping;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetApplicationZone() throws Exception {
    //then
    Field zoneSetPrivate = SecurityActionMapping.class.getDeclaredField("zone");
    zoneSetPrivate.setAccessible(true);
    zoneSetPrivate.set(securityActionMapping, "zone");

    String zoneValueToAssert = securityActionMapping.getApplicationZone();
    assertEquals("zone", zoneValueToAssert);
  }

  @Test
  void testSetApplicationZone() throws Exception {
    //then
    securityActionMapping.setApplicationZone("newZone");

    Field zoneGetPrivate = SecurityActionMapping.class.getDeclaredField("zone");
    zoneGetPrivate.setAccessible(true);
    String zoneToAssert = (String) zoneGetPrivate.get(securityActionMapping);

    assertEquals("newZone", zoneToAssert);
  }
}
