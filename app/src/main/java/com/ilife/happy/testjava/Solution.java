package com.ilife.happy.testjava;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static void main(String[] args) {

//        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
//        int[] nums = {5, 4, -1, 7, 8};
//        int[] nums = {31, -41, 59, 26, -53, 58, 97, -93, -23, 84};
//        int[] nums = {2, -1, 1, 1};
//        int[] nums = {-1, 1, 2, 1};
//        int[] nums = {3, -2, -3, -3, 1, 3, 0};
//        int[] nums = {2, -3, 1, 3, -3, 2, 2, 1};
//        int[] nums = {-1, 6, 7, -7, -2, -6, -1, 3, 4, 2, 6, -3, -8, -1, 7};
        int[] nums = { -2, -1, -3};
        maxSubArray3(nums);
        maxSubArray2(nums);
    }

    public static int maxSubArray3(int[] nums) {
        int res = nums[0];
        int sum = 0;
        for (int num : nums) {
            if (sum > 0)
                sum += num;
            else
                sum = num;
            res = Math.max(res, sum);
        }
        System.out.println("最终求和最大的值是 res :" + res);
        return res;
    }

    public static int maxSubArray2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int result = nums[0];
        int eResult;
        for (int i = 0; i < nums.length; i++) {
            eResult = nums[i];
            if (eResult > result) {
                result = eResult;
            }
            for (int j = i + 1; j < nums.length; j++) {
                eResult += nums[j];
                if (eResult > result) {
                    result = eResult;
                }
            }

        }
        System.out.println("最终求和最大的值是 result :" + result);
        return result;
    }

    public static int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int result = nums[0];
        int pos = 0;
        List<Integer> posArray = new ArrayList<Integer>() {
        };
        List<Integer> resultArray = new ArrayList<Integer>() {
        };
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= result) {
                result = nums[i];
//                pos = i;
                posArray.add(i);
            }
        }
//        System.out.println("最大元素坐标 posArray : " + posArray + ", posArray: " + posArray.size());
        for (int i = 0; i < posArray.size(); i++) {
//            System.out.println("最大元素坐标 : " + posArray.get(i) + ", 最大元素值: " + result);
            //找出了最大元素坐标
            int sum = 0;
            int beforeMore = 0;
            result = nums[posArray.get(i)];
            for (int j = posArray.get(i); j >= 0; j--) {
                sum += nums[j];
//                System.out.println("上半段 j : " + j + ", sum: " + sum);
                if (sum > result) {
                    beforeMore = sum - nums[posArray.get(i)];
                    result = sum;
                }
            }

            sum = 0;
            result = nums[posArray.get(i)];
            int afterMore = 0;
            for (int j = posArray.get(i); j <= nums.length - 1; j++) {
                sum += nums[j];
//                System.out.println("下半段 j : " + j + ", sum: " + sum);
                if (sum > result) {
                    afterMore = sum - nums[posArray.get(i)];
                    result = sum;
                }
            }

            System.out.println("result : " + result + ", beforeMore = " + beforeMore + ", afterMore = " + afterMore);
            System.out.println("求和最大值是 result : " + (nums[posArray.get(i)] + beforeMore + afterMore));
            resultArray.add(nums[posArray.get(i)] + beforeMore + afterMore);
        }

        int endResult = resultArray.get(0);
        for (int i = 0; i < resultArray.size(); i++) {
            if (resultArray.get(i) > endResult) {
                endResult = resultArray.get(i);
            }
        }
        System.out.println("最终求和最大的值是 endResult : " + endResult);
        return endResult;
    }
}
