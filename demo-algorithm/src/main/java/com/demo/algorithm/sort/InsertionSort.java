package com.demo.algorithm.sort;

import com.demo.algorithm.util.SortHelper;

import java.util.Arrays;

/**
 * 插入排序
 * 8623
 * 1. 8直接不变
 * 2. 6和8比较，放到8前面：6823
 * 3. 2先和8比较放到8前面：6283，2再和6比较放到6前面：2683
 */
public class InsertionSort {

    public static void main(String[] args) {
        SortHelper.printlnTime(InsertionSort::sort);
    }

    /**
     * 优点：第二次循环可以提前结束
     * 缺点：一直都在不停的交换，交换比比较更加消耗性能
     */
    private static void sort() {
        int[] array = SortHelper.getRandomArray(10000);
        System.out.println("old = " + Arrays.toString(array));

        for (int i = 1; i < array.length; i++) {
            //注意这时候j才是主角
            for (int j = i; j > 0; j--) {
                if (array[j] < array[j - 1]) {
                    SortHelper.swap(array, j, j - 1);
                } else {// 如果不是就表示已经放在合适位置了，提前结束
                    break;
                }
            }
        }

        System.out.println("new = " + Arrays.toString(array));
    }

    /**
     * 8623
     * 1. 先把6复制一份，判断是否应该放在当前位置，不是6往前挪一位，再把8赋值给6的之前位置。
     *  再判断6是否应该在当前位置，是就赋值在第一位，因为之前向前挪一位了：6823
     * 2. 先把2复制一份，判断是否应该放在当前位置，不是2往前挪一位，8赋值给2之前位置。在判断2是否在当前位置，不是6赋值给8之前位置。
     *  在判断2是否在当前位置，是就赋值在第一位。2683
     * 3...
     */
    private static void sortV2() {

    }
}
