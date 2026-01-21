package edu.sdr.electronics.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAgentParser {

    public static String getBrowser(String userAgent) {
        if (userAgent == null) return "Unknown";
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) return "Internet Explorer";
        if (userAgent.contains("Edge")) return "Edge";
        if (userAgent.contains("Chrome")) return "Chrome";
        if (userAgent.contains("Firefox")) return "Firefox";
        if (userAgent.contains("Safari")) return "Safari";
        return "Unknown";
    }

    public static String getOperatingSystem(String userAgent) {
        if (userAgent == null) return "Unknown";
        if (userAgent.contains("Windows")) return "Windows";
        if (userAgent.contains("Mac")) return "Mac OS";
        if (userAgent.contains("Linux")) return "Linux";
        if (userAgent.contains("Android")) return "Android";
        if (userAgent.contains("iPhone") || userAgent.contains("iPad")) return "iOS";
        return "Unknown";
    }
}
