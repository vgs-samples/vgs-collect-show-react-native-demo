package com.collectrndemo.simple.modules.util;

import org.json.JSONObject;
import org.json.JSONException;

public class JSONObjectUtil {

    public static String getValue(String bodyStr, String key) {
        try {
            JSONObject body = new JSONObject(bodyStr);
            if (body.has("json") && body.getJSONObject("json").has(key)) {
                return body.getJSONObject("json").getString(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

}