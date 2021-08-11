package com.ilife.happy.testjava.leetcode;

/**
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * 示例 1:
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 *
 * 示例 2:
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 *
 * 示例 3:
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *
 * 示例 4:
 * 输入: s = ""
 * 输出: 0
 */
public class lengthOfLongestSubstringSolution {

    public static void main(String[] args) {
        String s = "abc;&abcbb";
//        String s = "bbbbb";
//        String s = "pwwkew";
//        String s = "";
        lengthOfLongestSubstring(s);
    }

    public static int lengthOfLongestSubstring(String s) {
        // 记录字符上一次出现的位置
        int[] last = new int[128];
        for(int i = 0; i < 128; i++) {
            last[i] = -1;
        }
        int n = s.length();

        int res = 0;
        int start = 0; // 窗口开始位置
        for(int i = 0; i < n; i++) {
            int index = s.charAt(i);
            start = Math.max(start, last[index] + 1);
            res   = Math.max(res, i - start + 1);
            last[index] = i;
        }
        System.out.println("********res:" + res);
        return res;
    }

    public static int lengthOfLongestSubstring1(String s) {
        int length = 0;
        if (s == null || s.length() == 0)
            return 0;
        String targetS;
        for (int i = 0; i < s.length(); i++) {
            targetS = s.substring(i, i + 1);
        }


        int index = s.substring(1).indexOf(s.substring(0, 1));
        System.out.println("********index:" + index);
        return length;
    }
}
