package org.opensrp.stock.openlmis.util;

import java.lang.reflect.Type;

import org.joda.time.DateTime;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mysql.jdbc.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class Utils {

    public static int DEFAULT_FETCH_LIMIT = 1000;

    public static final Long getCurrentTime() {
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
