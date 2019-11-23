package com.proshine.base.common.utils;

import java.util.Locale;

/**
 * 项目运行所在操作系统类型检测工具
 */
public final class OsCheck {
    // cached result of OS detection
    private static OSType detectedOS;

    /**
     * detect the operating system from the os.name System property and cache
     * the result
     */
    public static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((OS.contains("mac")) || (OS.contains("darwin"))) {
                detectedOS = OSType.MacOS;
            } else if (OS.contains("win")) {
                detectedOS = OSType.Windows;
            } else if (OS.contains("nux")) {
                detectedOS = OSType.Linux;
            } else {
                detectedOS = OSType.Other;
            }
        }
        return detectedOS;
    }

    /**
     * types of Operating Systems
     */
    public enum OSType {
        Windows,
        MacOS,
        Linux,
        Other
    }
}
