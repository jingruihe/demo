package com.demo.algorithm.util;

import com.demo.algorithm.funcation.NullConsumer;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SortHelper {

    /**
     * 生成随机数组
     * @param size
     * @param start
     * @param end
     * @return
     */
    public static int[] getRandomArray(int size, int start, int end){
        return ThreadLocalRandom.current().ints(size, start, end).toArray();
    }

    public static int[] getRandomArray(int size){
        return ThreadLocalRandom.current().ints(size, 1, size).toArray();
    }

    /**
     * 打印耗时
     * @param consumer
     */
    public static void printlnTime(NullConsumer consumer){
        long start = System.currentTimeMillis();
        consumer.accept();
        System.out.println("耗时 = " + (System.currentTimeMillis() - start));
    }

    /**
     * 交换
     * @param array
     * @param i
     * @param minIndex
     */
    public static void swap(int[] array, int i, int minIndex) {
        int temp = array[i];
        array[i] = array[minIndex];
        array[minIndex] = temp;
    }

    public static void main(String[] args) {
        int[] randomArray = getRandomArray(20, 10, 40);
        System.out.println("randomArray = " + Arrays.toString(randomArray));
    }
}
