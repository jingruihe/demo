package com.jingrui.demo.sort;

import com.jingrui.demo.util.Utils;

import java.util.Arrays;

public class DemoSort {


    public static void main(String[] args) {
        int[] source = Utils.randomArray(10, 1, 10);
        int[] target = selectSort(source);
        System.out.println(Arrays.toString(target));
    }

    /**
     * 选择排序.
     * 1. 从待排序序列中，找到关键字最小的元素；
     * 2. 如果最小元素不是待排序序列的第一个元素，将其和第一个元素互换；
     * 3. 从余下的 N - 1 个元素中，找出关键字最小的元素，重复1 2步，直到排序结束
     *
     * @return
     */
    public static int[] selectSort(int[] source){

    }
}
