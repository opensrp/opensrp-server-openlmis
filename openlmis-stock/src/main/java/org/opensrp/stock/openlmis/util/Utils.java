package org.opensrp.stock.openlmis.util;

import com.google.gson.*;
import com.mysql.jdbc.StringUtils;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;

public class Utils {

    public static final int DEFAULT_FETCH_LIMIT = 1000;

    public static final String SYNC_SERVER_VERSION = "sync_server_version";

    public static final String COMMODITY_TYPE = "CommodityType";

    public static final String COMMODITY_TYPES = "commodity_types";

    public static final String LOTS = "lots";

    public static final String ORDERABLES = "orderables";

    public static final String PROGRAM_ORDERABLES = "program_orderables";

    public static final String PROGRAM = "Program";

    public static final String PROGRAMS = "programs";

    public static final String REASON = "Reason";

    public static final String REASONS = "reasons";

    public static  final String TRADE_ITEM = "TradeItem";

    public final static String TRADE_ITEMS = "trade_items";

    public static Long getCurrentTime() {
        return System.nanoTime();
    }

    public static class DateTimeTypeConverter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {

        @Override
        public DateTime deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {
            return new DateTime(json.getAsString());
        }

        @Override
        public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
    }

    public static Long getLongFilter(String filter, HttpServletRequest req) {
        String strval = getStringFilter(filter, req);
        return strval == null ? null : Long.parseLong(strval);
    }

    public static String getStringFilter(String filter, HttpServletRequest req) {
        return StringUtils.isEmptyOrWhitespaceOnly(req.getParameter(filter)) ? null : req.getParameter(filter);
    }
}
