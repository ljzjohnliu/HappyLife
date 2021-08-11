package com.ilife.happy.testjava.leetcode;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 给定两个数组，编写一个函数来计算它们的交集。
 *
 * 示例 1：
 * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
 * 输出：[2,2]
 *
 * 示例 2:
 * 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * 输出：[4,9]
 *
 * 说明：
 * 输出结果中每个元素出现的次数，应与元素在两个数组中出现次数的最小值一致。
 * 我们可以不考虑输出结果的顺序。
 * 进阶：
 *
 * 如果给定的数组已经排好序呢？你将如何优化你的算法？
 * 如果 nums1 的大小比 nums2 小很多，哪种方法更优？
 * 如果 nums2 的元素存储在磁盘上，内存是有限的，并且你不能一次加载所有的元素到内存中，你该怎么办？
 */
public class IntersectionSolution2 {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String[] args) {
//        int[] nums1 = {3, 1, 1, 2};
//        int[] nums2 = {1, 1};
        int[] nums1 = {4, 9, 5};
        int[] nums2 = {9, 4, 9, 8, 4};
//        int[] nums1 = {4, 7, 9, 7, 6, 7};
//        int[] nums2 = {5, 0, 0, 6, 1, 6, 2, 2, 4};
//        intersection(nums1, nums2);
        intersect2(nums1, nums2);
    }

    public static int[] intersection(int[] nums1, int[] nums2) {
        List<Integer> numList1 = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        for (int i : nums1) {
            numList1.add(Integer.valueOf(i));
        }
        for (int i : nums2) {
            System.out.println("--------i = " +  i + ", --- numList1 size = " + numList1.size());
            if (numList1.contains(i)) {
                list.add(i);
                numList1.remove(Integer.valueOf(i));
            }
        }
        int[] res = new int[list.size()];
        int index = 0;
        for (Integer i : list) {
            res[index++] = i;
            System.out.println(i);
        }
        return res;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int[] intersect2(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            return intersect2(nums2, nums1);
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int num: nums1) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        int[] result = new int[nums1.length];
        int index = 0;
        for (int num: nums2) {
            int count = map.getOrDefault(num, 0);
            if (count > 0) {
                result[index++] = num;
                count--;
                if (count > 0) {
                    map.put(num, count);
                } else {
                    map.remove(num);
                }
            }
        }
        return Arrays.copyOfRange(result, 0, index);
    }

    public static int[] intersection3(int[] nums1, int[] nums2) {
        List<Integer> numList1 = new ArrayList<>();
        int[] res = new int[nums1.length];
        int index = 0;
        for (int i : nums1) {
            numList1.add(Integer.valueOf(i));
        }
        for (int i : nums2) {
            if (numList1.contains(i)) {
                res[index++] = i;
                numList1.remove(Integer.valueOf(i));
            }
        }
        return Arrays.copyOfRange(res, 0, index);
    }
}
