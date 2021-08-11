package com.ilife.happy.testjava.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定两个数组，编写一个函数来计算它们的交集。
 *
 * 示例 1：
 * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
 * 输出：[2]
 *
 * 示例 2：
 * 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * 输出：[9,4]
 *
 * 说明：
 * 输出结果中的每个元素一定是唯一的。
 * 我们可以不考虑输出结果的顺序。
 */
public class IntersectionSolution {

    public static void main(String[] args) {
        int[] nums1 = {4, 7, 9, 7, 6, 7};
        int[] nums2 = {5, 0, 0, 6, 1, 6, 2, 2, 4};
        intersection(nums1, nums2);
//        System.out.println("------------------reverse(num) = " + reverse(num));
    }

    public static int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set1 = new HashSet<Integer>();
        Set<Integer> set2 = new HashSet<Integer>();
        if (nums1.length > nums2.length) {
            for (int i : nums1) {
                set1.add(i);
            }
            for (int i : nums2) {
                if (set1.contains(i)) {
                    set2.add(i);
                }
            }
        } else {
            for (int i : nums2) {
                set1.add(i);
            }
            for (int i : nums1) {
                if (set1.contains(i)) {
                    set2.add(i);
                }
            }
        }
        int[] res = new int[set2.size()];
        int index = 0;
        for (Integer i : set2) {
            res[index++] = i;
            System.out.println(i);
        }
        return res;
    }
}
