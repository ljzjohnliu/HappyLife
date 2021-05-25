package com.ilife.happy.testjava;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class TestContinue {

    public static void main(String[] args) {
//        long start1 = System.currentTimeMillis();
//        System.out.println("-------------start1--------------" + start1);
//        for (int i = 0; i < 100; i++) {
//            getS2();
//        }
//        long end1 = System.currentTimeMillis();
//        System.out.println("-------------end1--------------" + end1);
//        System.out.println("-------------time cost is --------------" + (end1 - start1) + ", getS2() = " + getS2());

        formatPower(0);
        formatPower(286);
        formatPower(9854);
        formatPower(1245786);
        formatPower(9654128);
    }

    public static String formateHH(String str) {
        return str;
    }

    public static String formatPower(int powerValue) {
        if (powerValue < 10000) {
            return String.valueOf(powerValue);
        } else {
            float f = (float)powerValue/10000;
            System.out.println("-------------ffff--------------" + String.format("%.1f", f));
            return String.format("%.2f", f);
        }
    }

    public static String getS1() {
        NumberFormat format = NumberFormat.getPercentInstance();
        //小数最大保留2位
        format.setMaximumFractionDigits(2);
        String str = format.format(0.6);
        return str;
//        System.out.println(str);
    }

    public static String getS2() {
        double result = 0.6;
        DecimalFormat df = new DecimalFormat("0%");
        String r = df.format(result);
//        System.out.println(r);
        return r;
    }

    public static void testII() {
        List<String> images = new ArrayList<>();
        images.add("AAAA");
        images.add("BBBB");
        images.add("CCCC");
        images.add("DDDD");
        images.add("EEEE");

        System.out.println("main images 1 is = " + images.get(1));
        for (int i = 0; i < images.size(); i = i++) {
            if (i == 1)
                continue;
            System.out.println("main images " + i + " is = " + images.get(i));
        }
        int i = 0;
        i = i++;
        System.out.println("main  i is = " + i);
    }
}
