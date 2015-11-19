package com.mobica.airscannerws.core;

import com.google.common.base.Strings;

/**
 * Created by woos on 2015-11-19.
 */
public class TokenParser {
    private static final String KEY_PARAM = "key=";

    public static String parseToken(String token) {
        if (Strings.isNullOrEmpty(token)) {
            return null;
        }

        if (token.startsWith(KEY_PARAM)) {
            int semicInd = token.indexOf(";");
            if (semicInd < 0) {
                semicInd = token.length();
            }

            return token.substring(KEY_PARAM.length(), semicInd);
        }

        return null;
    }
}
