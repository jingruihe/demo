package com.demo.algorithm.sort;

import com.demo.algorithm.util.SortHelper;

import java.util.Arrays;

/**
 * 选择排序算法
 * 8623
 * 1. 先最小的数字，排到第一位：2863
 * 2. 位数移动到第二位，第二位之后最小的数字，放在第二位：2386
 * 3. 位数移动到第三位...
 */
public class SelectionSort {


    public static void main(String[] args) {
        SortHelper.printlnTime(SelectionSort::sort);
    }

    private static void sort() {
        int[] array = SortHelper.getRandomArray(10000);
        System.out.println("old = " + Arrays.toString(array));

        for (int i = 0; i < array.length; i++) {
            //寻找[i,n)区间里最小值
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            SortHelper.swap(array, i, minIndex);
        }

        System.out.println("new = " + Arrays.toString(array));
    }


}
