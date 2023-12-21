package org.example;

public class Utils {
    public static String intFormatPad(int value, int padding) {
        StringBuilder intval = new StringBuilder(Integer.toString(value));

        while (intval.length() % padding != 0) {
            intval.insert(0, "0");
        }

        return intval.toString();
    }

    public static String makeTime(int timestamp) {
        return Utils.intFormatPad(9 + timestamp / 60, 2) + ":" + Utils.intFormatPad(timestamp % 60, 2);
    }
}
