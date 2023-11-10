package com.example.sisteminformasikliniik.transfer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;

public class GsonProvider {
    private static GsonProvider instance;

    private Gson gson;

    private GsonProvider(Gson gson) {
        this.gson = gson;
    }

    public static GsonProvider getInstance() {
        if (instance == null) {
//            instance = new GsonProvider(new GsonBuilder()
//                    .serializeNulls()
//                    .setLenient()
//                    .create());

            instance = new GsonProvider(new GsonBuilder()
                    .serializeNulls()
                    .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                    .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                    .registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer())
                    .registerTypeAdapter(DateTimeZone.class, new DateTimeZoneDeserializer())
                    .registerTypeAdapter(DateTime.class, new DateTimeDeserializer())
                    .registerTypeAdapter(DateTime.class, new DateTimeSerializer())
                    .setLenient()
                    .create());
        }
        return instance;
    }

    public Gson getGson() {
        return gson;
    }

    private static final class LocalDateSerializer implements JsonSerializer<LocalDate> {
        private LocalDateSerializer() {
        }

        @Override
        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            if (date != null) {
                return new JsonPrimitive(date.toString(DateTimeFormat.forPattern("yyyy-MM-dd")));
            } else {
                return null;
            }
        }
    }

    private static final class LocalDateDeserializer implements JsonDeserializer<LocalDate> {
        private LocalDateDeserializer() {
        }

        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            String value = json.getAsString();
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
            return LocalDate.parse(value, dateTimeFormatter);
        }

    }

    private static final class LocalTimeDeserializer implements JsonDeserializer<LocalTime> {

        private LocalTimeDeserializer() {
        }

        @Override
        public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            String value = json.getAsString();
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("HH:mm:ss");
            return LocalTime.parse(value, dateTimeFormatter);
        }

    }

    private static final class DateTimeDeserializer implements JsonDeserializer<DateTime> {

        private DateTimeDeserializer() {
        }

        @Override
        public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            String value = json.getAsString();
            return !StringUtils.isEmpty(value) ? DateTime.parse(value) : null;
        }

    }

    private static final class DateTimeSerializer implements JsonSerializer<DateTime> {

        private DateTimeSerializer() {
        }

        @Override
        public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
            return src != null ? new JsonPrimitive(src.toString()) : null;
        }
    }

    private static final class DateTimeZoneDeserializer implements JsonDeserializer<DateTimeZone> {

        private DateTimeZoneDeserializer() {
        }

        @Override
        public DateTimeZone deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            String value = json.getAsString();
            return DateTimeZone.forID(value);
        }

    }


}


