package ru.sergey310872.util;

import jakarta.servlet.http.HttpServletRequest;

public class DeviceDetector {
    public static DeviceInfo detectDevice(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        DeviceInfo info = new DeviceInfo();

        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();

            // Определение ОС
            if (userAgent.contains("windows")) info.setOs("Windows");
            else if (userAgent.contains("mac os")) info.setOs("Mac OS");
            else if (userAgent.contains("android")) info.setOs("Android");
            else if (userAgent.contains("iphone") || userAgent.contains("ipad")) info.setOs("iOS");
            else if (userAgent.contains("linux")) info.setOs("Linux");

            // Определение браузера
            if (userAgent.contains("chrome") && !userAgent.contains("edg")) info.setBrowser("Chrome");
            else if (userAgent.contains("firefox")) info.setBrowser("Firefox");
            else if (userAgent.contains("safari") && !userAgent.contains("chrome")) info.setBrowser("Safari");
            else if (userAgent.contains("edg")) info.setBrowser("Edge");

            // Определение типа устройства
            if (userAgent.contains("mobile") ||
                    userAgent.contains("android") && userAgent.contains("mobile")) {
                info.setDeviceType("mobile");
            } else if (userAgent.contains("tablet") ||
                    userAgent.contains("ipad") ||
                    (userAgent.contains("android") && !userAgent.contains("mobile"))) {
                info.setDeviceType("tablet");
            } else {
                info.setDeviceType("desktop");
            }
        }

        return info;
    }

    public static class DeviceInfo {
        private String os;
        private String browser;
        private String deviceType;

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getBrowser() {
            return browser;
        }

        public void setBrowser(String browser) {
            this.browser = browser;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }
// getters and setters
    }
}
