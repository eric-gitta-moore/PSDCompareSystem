package com.example.newcompare.common.utils;




public class ThreadLocalUtil {
    private static final ThreadLocal<String> tl = new ThreadLocal<>();

    public static void saveUser(String  uuid){
        tl.set(uuid);
    }

    public static String getUuid(){
        return tl.get();
    }

    public static void removeUuid(){
        tl.remove();
    }
}
