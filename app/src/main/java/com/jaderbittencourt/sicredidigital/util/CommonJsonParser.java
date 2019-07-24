package com.jaderbittencourt.sicredidigital.util;

import com.google.gson.Gson;

public class CommonJsonParser {
    public static <T> T jsonToObject(String stringToParse, Class<T> type) {
        return new Gson().fromJson(stringToParse, type);
    }

    public static <T> String objectToJson(T objectToParse) {
        Gson gson = new Gson();
        return gson.toJson(objectToParse);
    }

}
