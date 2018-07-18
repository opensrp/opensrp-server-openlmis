package org.opensrp.stock.openlmis.util;

import java.util.Calendar;

public class Utils {

    public static int DEFAULT_FETCH_LIMIT = 1000;

    public static final Long getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }
}
