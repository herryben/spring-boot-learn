package com.learn.window;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class WindowSolution {
    /**
     * 滑动窗口模板
     *
     * @param str
     * @param target
     */
    public void slidingWindow(String str, String target) {
        Map<Character, Integer> window = new HashMap<>();
        Map<Character, Integer> need = new HashMap<>();
        for (char ch : target.toCharArray()) {
            need.compute(ch, (key, val) -> val == null ? 1 : ++val);
        }
        int left = 0, right = 0, valid = 0;
        while (right < str.length()) {
            // ch是将要移入窗口的字符串
            char ch = str.charAt(right);
            // 右移窗口
            right++;
            // 进行窗口内的一系列更新
            valid++;
            // 判断窗口左侧是否需要收缩
            while (valid == need.size()) {
                // d是将移出窗口的字符
                char d = str.charAt(left);
                // 左移窗口
                left++;
                // 进行窗口内的一些列更新
            }
        }
    }

    /**
     * 76. 最小覆盖子串
     * https://leetcode.cn/problems/minimum-window-substring/description/
     * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
     * 注意：
     * 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
     * 如果 s 中存在这样的子串，我们保证它是唯一的答案
     * 示例 1：
     * 输入：s = "ADOBECODEBANC", t = "ABC"
     * 输出："BANC"
     * 解释：最小覆盖子串 "BANC" 包含来自字符串 t 的 'A'、'B' 和 'C'。
     * 示例 2：
     * 输入：s = "a", t = "a"
     * 输出："a"
     * 示例 3:
     * 输入: s = "a", t = "aa"
     * 输出: ""
     * 解释: t 中两个字符 'a' 均应包含在 s 的子串中，
     * 因此没有符合条件的子字符串，返回空字符串。
     * 解释：整个字符串 s 是最小覆盖子串。
     *
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        Map<Character, Integer> window = new HashMap<>();
        Map<Character, Integer> need = new HashMap<>();
        for (char ch : t.toCharArray()) {
            need.compute(ch, (key, val) -> val == null ? 1 : ++val);
        }
        // valid是符合条件的个数
        int valid = 0, left = 0, right = 0, start = 0, len = Integer.MAX_VALUE;
        while (right < s.length()) {
            char ch = s.charAt(right);
            right++;
            if (need.containsKey(ch)) {
                // 说明目标字符串需要当前字符
                window.compute(ch, (key, val) -> val == null ? 1 : ++val);
                if (window.get(ch).equals(need.get(ch))) {
                    // 如果窗口的该字符数符合了需要的字符字符数，则更新合法数
                    valid++;
                }
            }
            // 判断左侧窗口是否需要收缩
            while (valid == need.size()) {
                // 如果新的区间小于之前的记录则更新区间
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }
                // 记录要移出的字符串
                char d = s.charAt(left);
                left++;
                if (need.containsKey(d)) {
                    if (need.get(d).equals(window.get(d))) {
                        valid--;
                    }
                    window.compute(d, (key, val) -> --val);
                }
            }
        }
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }

    @Test
    public void testMinWindow() {
        Assert.assertEquals("BANC", minWindow("ADOBECODEBANC", "ABC"));
        Assert.assertEquals("a", minWindow("a", "a"));
        Assert.assertEquals("", minWindow("a", "aa"));
    }

    /**
     * 剑指 Offer 38. 字符串的排列
     * https://leetcode.cn/problems/zi-fu-chuan-de-pai-lie-lcof/
     * 示例:
     * 输入：s = "abc"
     * 输出：["abc","acb","bac","bca","cab","cba"]
     *
     * @param s
     * @return
     */
    public String[] permutation(String s) {
        return new String[]{};
    }

    @Test
    public void testPermutation() {
        Assert.assertEquals(new String[]{"abc", "acb", "bac", "bca", "cab", "cba"}, permutation("abc"));
    }

    /**
     * 567. 字符串的排列
     * https://leetcode.cn/problems/permutation-in-string/
     * 给你两个字符串 s1 和 s2 ，写一个函数来判断 s2 是否包含 s1 的排列。如果是，返回 true ；否则，返回 false 。
     * 换句话说，s1 的排列之一是 s2 的 子串 。
     * 示例 1：
     * 输入：s1 = "ab" s2 = "eidbaooo"
     * 输出：true
     * 解释：s2 包含 s1 的排列之一 ("ba").
     * 示例 2：
     * 输入：s1= "ab" s2 = "eidboaoo"
     * 输出：false
     * 解题思路：
     * @param s1
     * @param s2
     * @return
     */
    public boolean checkInclusion(String s1, String s2) {
        Map<Character, Integer> window = new HashMap<>();
        Map<Character, Integer> need = new HashMap<>();
        for (char ch : s1.toCharArray()) {
            need.compute(ch, (key, val) -> val == null ? 1 : ++val);
        }
        int left = 0, right = 0, valid = 0;
        while (right < s2.length()) {
            char ch = s2.charAt(right);
            right++;
            if (need.containsKey(ch)) {
                window.compute(ch , (key, val) -> val == null ? 1 : ++val);
                if (need.get(ch).equals(window.get(ch))) {
                    valid++;
                }
            }

            while (right - left >= s1.length()) {
                if (valid == need.size()) {
                    return true;
                }
                char del = s2.charAt(left);
                left++;
                if (need.containsKey(del)) {
                    if (need.get(del).equals(window.get(del))) {
                        valid--;
                    }
                    window.compute(del, (key, val) -> --val);
                }
            }
        }
        return false;
    }

    @Test
    public void testCheckInclusion() {
//        Assert.assertEquals(true, checkInclusion("ab", "eidbaooo"));
        Assert.assertEquals(false, checkInclusion("ab", "eidboaoo"));
    }
}
