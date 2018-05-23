package com.framgia.anhnt.vmusic.utils;

import java.util.Iterator;
import java.util.Map;

public class TrackUtil {
    public static String makeUrl(String url, Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder(url);
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        int i = 1;
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (i == 1) {
                stringBuilder.append(entry.getKey()).append(entry.getValue());
            } else {
                stringBuilder.append("&")
                        .append(entry.getKey())
                        .append("=")
                        .append(entry.getValue());
            }
            iterator.remove();
            i++;
        }
        return stringBuilder.toString();
    }
}
