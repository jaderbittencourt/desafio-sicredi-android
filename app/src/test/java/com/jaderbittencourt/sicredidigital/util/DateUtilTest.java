package com.jaderbittencourt.sicredidigital.util;

import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DateUtilTest {

    @Test
    public void shouldReturnEmptyString() {
        assertEquals("", DateUtil.getFormatedDate(null));
    }

    @Test
    public void shouldReturnAStringDate() {
        assertEquals("31/12/1969 21:00", DateUtil.getFormatedDate(0L));
    }

    @Test
    public void shouldReturnTheCurrentDate() {
        Long timestamp = new Timestamp(System.currentTimeMillis()).getTime();
        assertNotNull(DateUtil.getFormatedDate(timestamp));
    }
}
