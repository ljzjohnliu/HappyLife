package com.ilife.happy.testjava.leetcode;

import java.util.HashMap;

/**
 * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
 *
 * 如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。
 *
 * 假设环境不允许存储 64 位整数（有符号或无符号）。
 *
 * 示例 1：
 * 输入：x = 123
 * 输出：321
 *
 * 示例 2：
 * 输入：x = -123
 * 输出：-321
 *
 * 示例 3：
 * 输入：x = 120
 * 输出：21
 *
 * 示例 4：
 * 输入：x = 0
 * 输出：0
 *
 * 提示：
 * -231 <= x <= 231 - 1
 */
public class ReverseSolution {

    public static void main(String[] args) {
//        9646324351
        int num = 1534236469;
        System.out.println("------------------reverse(num) = " + reverse(num));
    }

    public static int reverse(int x) {
        String xStr = String.valueOf(Math.abs(x));
        StringBuffer buffer = new StringBuffer(xStr).reverse();
        try {
            if (x >= 0) {
                return Integer.parseInt(buffer.toString());
            } else {
                return Integer.parseInt(buffer.toString())*(-1);
            }
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
