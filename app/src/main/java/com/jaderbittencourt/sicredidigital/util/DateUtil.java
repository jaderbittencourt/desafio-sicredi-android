package com.jaderbittencourt.sicredidigital.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static final String DATE_API = "dd/MM/yyyy HH:mm";

    public static String getFormatedDate(Long timestamp) {
        if (timestamp != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date(timestamp));
            return new SimpleDateFormat(DATE_API, Locale.getDefault()).format(c.getTime());
        } else {
            return "";
        }
    }

}
