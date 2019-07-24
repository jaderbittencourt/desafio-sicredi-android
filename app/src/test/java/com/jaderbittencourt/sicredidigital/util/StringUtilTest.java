package com.jaderbittencourt.sicredidigital.util;

import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class StringUtilTest {

    @Test
    public void shouldReturnFalseForNonBlankStrnig() {
        assertFalse(StringUtil.isBlank("a"));
    }

    @Test
    public void shouldReturnTrueForNull() {
        assertTrue(StringUtil.isBlank(null));
    }

    @Test
    public void shouldReturnTrueForNEmptyString() {
        assertTrue(StringUtil.isBlank(""));
    }

    @Test
    public void shouldReturnTrueForNEmptySpaces() {
        assertTrue(StringUtil.isBlank("  "));
    }

    @Test
    public void shouldReturnFalseForInvalidEmail() {
        assertFalse(StringUtil.isValidEmail("a"));
    }

    @Test
    public void shouldReturnFalseForNullEmail() {
        assertFalse(StringUtil.isValidEmail(null));
    }

    @Test
    public void shouldReturnFalseForEmptyEmail() {
        assertFalse(StringUtil.isValidEmail(""));
    }

    @Test
    public void shouldReturnFalseForEmptySpacesEmail() {
        assertFalse(StringUtil.isValidEmail("   "));
    }

    @Test
    public void shouldReturnTrueForValidEmailFormat() {
        assertTrue(StringUtil.isValidEmail("a@b.com"));
    }
}
