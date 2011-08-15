package ru.frostman.dropbox.api.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author slukjanov aka Frostman
 */
public class Dates {
    // Sat, 21 Aug 2010 22:31:20 +0000
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);

    public synchronized static Date parse(String date) {
        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            //todo impl
            throw new RuntimeException(e);
        }
    }

    public synchronized static String format(Date date){
        return DATE_FORMAT.format(date);
    }
}
