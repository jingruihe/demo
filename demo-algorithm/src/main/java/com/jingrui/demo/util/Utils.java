package com.jingrui.demo.util;
import java.util.Arrays;
import java.util.Random;

public class Utils {

    /**
     * 生成随机数
     *
     * @param length
     * @param min
     * @param max
     * @return
     */
    public static final int[] randomArray(int length, int min, int max) {
        int[] arr = new int[length];

        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            arr[i] = rand.nextInt(max - min + 1) + min;
        }
        return arr;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(randomArray(10, 10, 20)));
    }
}

