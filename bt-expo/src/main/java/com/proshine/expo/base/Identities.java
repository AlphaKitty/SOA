package com.proshine.expo.base;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.UUID;

public class Identities {
    private static SecureRandom random = new SecureRandom();

    /**
     * 生成uuid，其中包含'-'
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成uuid，去掉'-'，用于作为数据库的id
     */
    public static String uuid2() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成36位的uuid
     */
    public static String uuid3() {
        long val = ByteBuffer.wrap(uuid2().getBytes()).getLong();
        return Long.toString(val, 36);
    }

    /**
     * 生成随机长整数
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

}