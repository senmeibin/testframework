package com.bpms.core.utils;

import com.bpms.core.AppContext;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostUtils {
    /**
     * 取得本机地址
     *
     * @return IP地址
     */
    public static String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "UNKNOWN HOST";
        }
    }

    /**
     * 是否是本机访问？
     *
     * @return true：本机访问
     */
    public static boolean isLocalRequest() {
        String serverName = AppContext.getRequest().getServerName();

        if (StringUtils.equals(serverName, "localhost") || StringUtils.equals(serverName, "127.0.0.1")) {
            return true;
        }

        return false;
    }
}
