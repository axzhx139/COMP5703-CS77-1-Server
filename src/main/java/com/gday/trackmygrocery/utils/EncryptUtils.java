package com.gday.trackmygrocery.utils;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class EncryptUtils {
    public static String EncryptString(String str) {
        Date date = new Date();
        String sha256hex = Hashing.sha256()
                .hashString(str, StandardCharsets.UTF_8)
                .toString();
        return sha256hex;
    }
}
