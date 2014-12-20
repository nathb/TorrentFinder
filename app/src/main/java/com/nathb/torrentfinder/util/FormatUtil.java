package com.nathb.torrentfinder.util;

public class FormatUtil {

    public static String getFormattedNumber(int number) {
        final StringBuilder builder = new StringBuilder();
        if (number < 10) {
            builder.append("0");
        }
        builder.append(number);
        return builder.toString();
    }
}
