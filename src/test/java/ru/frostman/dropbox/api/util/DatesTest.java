package ru.frostman.dropbox.api.util;

import org.junit.Test;

import java.util.Date;

/**
 * @author slukjanov aka Frostman
 */
public class DatesTest {

    @Test
    public void simpleTest() {
        String sourceString = "Sat, 21 Aug 2010 22:31:20 +0000";
        Date date = Dates.parse(sourceString);
        System.out.println(date);

        Date t = new Date(110, 8, 21, 22, 31, 20);
        System.out.println(Dates.format(t));
    }

}
