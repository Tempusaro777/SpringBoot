package com.example.demo.utils;

import java.util.regex.Pattern;

public class Common {
    //工具类无需创建对象来访问静态方法（Static method）
    //所以将constructor变为私有，使得在外部无法构建一个Common实例
    private Common() {}

    // private static int counter;

    // //静态代码块用于初始化静态资源，例如
    // static {
    //     // 静态代码块，用于初始化counter变量
    //     counter = 0;
    // }

    // public static void test() {
    //     System.out.println(counter);
    // }

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.]+@[A-Za-z0-9+_.]+$");

    public static boolean isValidEmail(String emString) {
        if (emString == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(emString).matches();
    }
}
