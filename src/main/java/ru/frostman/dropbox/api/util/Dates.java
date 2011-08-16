package ru.frostman.dropbox.api.util;

import ru.frostman.dropbox.api.thr.DropboxDateFormatException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author slukjanov aka Frostman
 */
public class Dates {
    // smth like Sat, 21 Aug 2010 22:31:20 +0000
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);

    public synchronized static Date parse(String date) {
        try {
            return DATE_FORMAT.parse(date);
        } catch (Exception e) {
            throw new DropboxDateFormatException("Exception while parse date", e);
        }
    }

    public synchronized static String format(Date date) {
        try {
            return DATE_FORMAT.format(date);
        } catch (Exception e) {
            throw new DropboxDateFormatException("Exception while formatting date", e);
        }
    }
}
