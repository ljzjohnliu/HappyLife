package com.ilife.happy.testjava.leetcode;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 给定一个整数数组 nums和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那两个整数，并返回它们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 * 你可以按任意顺序返回答案。
 */
public class TwoNumsSolution {

    public static void main(String[] args) {

        int[] nums = {2, 7, 11, 15};
        int target = 9;
        twoSum(nums, target);
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] indexs = new int[2];
        HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (hash.containsKey(nums[i])) {
                indexs[0] = i;
                indexs[1] = hash.get(nums[i]);
                System.out.println("--组合的第1个元素:" + indexs[0] + ", 第2个元素:" + indexs[1]);
                return indexs;
            }
            hash.put(target - nums[i], i);
        }
        System.out.println("组合的第1个元素:" + indexs[0] + ", 第2个元素:" + indexs[1]);
        return indexs;
    }

    public static int[] twoSum1(int[] nums, int target) {
        int[] posArray = new int[2];
        for (int i = 0; i < nums.length; i++) {
            posArray[0] = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    posArray[1] = j;
                    System.out.println("********组合的第1个元素:" + posArray[0] + ", 第2个元素:" + posArray[1]);
                    return posArray;
                }
            }
        }
        System.out.println("组合的第1个元素:" + posArray[0] + ", 第2个元素:" + posArray[1]);
        return posArray;
    }
}
