package com.learn.window;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

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
            // 此时right已经是+1后的结果，所以right - left就是窗口字符串的长度
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
        Assert.assertEquals(true, checkInclusion("ab", "ab"));
        Assert.assertEquals(true, checkInclusion("ab", "eidbaooo"));
        Assert.assertEquals(false, checkInclusion("ab", "eidboaoo"));
    }

    /**
     * 438.找所有字母异位词
     * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
     * 异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
     * 示例1：
     * 输入: s = "cbaebabacd", p = "abc"
     * 输出: [0,6]
     * 解释:
     * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
     * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
     * 示例 2:
     * 输入: s = "abab", p = "ab"
     * 输出: [0,1,2]
     * 解释:
     * 起始索引等于 0 的子串是 "ab", 它是 "ab" 的异位词。
     * 起始索引等于 1 的子串是 "ba", 它是 "ab" 的异位词。
     * 起始索引等于 2 的子串是 "ab", 它是 "ab" 的异位词。
     *
     * @param s
     * @param p
     * @return
     */
    public List<Integer> findAnagrams(String s, String p) {
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        int left = 0, right = 0, valid = 0;
        for (char ch : p.toCharArray()) {
            need.compute(ch, (key, val) -> null == val ? 1 : ++val);
        }

        while (right < s.length()) {
            char ch = s.charAt(right);
            right++;
            if (need.containsKey(ch)) {
                window.compute(ch, (key, val) -> null == val ? 1 : ++val);
                if (need.get(ch).equals(window.get(ch))) {
                    valid++;
                }
            }
            while (right - left >= p.length()) {
                if (valid == need.size()) {
                    list.add(left);
                }
                char del = s.charAt(left);
                left++;
                if (need.containsKey(del)) {
                    if (need.get(del).equals(window.get(del))) {
                        valid--;
                    }
                    window.compute(del, (key, val) -> --val);
                }
            }
        }
        return list;
    }

    @Test
    public void testFindAnagrams() {
        Assert.assertEquals(true, Arrays.equals(new Integer[]{0, 6}, findAnagrams("cbaebabacd", "abc").toArray(new Integer[0])));
        Assert.assertEquals(true, Arrays.equals(new Integer[]{0, 1, 2}, findAnagrams("abab", "ab").toArray(new Integer[0])));
    }

    /**
     * 解题思路：
     * 滑动窗口的另一种写法（还可以用dffi计算做优化）
     *
     * @param s
     * @param p
     * @return
     */
    public List<Integer> findAnagrams2(String s, String p) {
        List<Integer> ans = new ArrayList<>();
        int sLen = s.length(), pLen = p.length();
        if (sLen < pLen) {
            return ans;
        }
        int[] sCount = new int[26];
        int[] pCount = new int[26];
        for (int i = 0; i < pLen; i++) {
            ++sCount[s.charAt(i) - 'a'];
            ++pCount[p.charAt(i) - 'a'];
        }
        if (Arrays.equals(sCount, pCount)) {
            ans.add(0);
        }

        for (int i = 0; i < sLen - pLen; i++) {
            ++sCount[s.charAt(i + pLen) - 'a'];
            --sCount[s.charAt(i) - 'a'];
            if (Arrays.equals(sCount, pCount)) {
                ans.add(i + 1);
            }
        }
        return ans;
    }

    @Test
    public void testFindAnagrams2() {
        Assert.assertEquals(true, Arrays.equals(new Integer[]{0, 6}, findAnagrams2("cbaebabacd", "abc").toArray(new Integer[0])));
        Assert.assertEquals(true, Arrays.equals(new Integer[]{0, 1, 2}, findAnagrams2("abab", "ab").toArray(new Integer[0])));
    }

    /**
     * 3. 无重复字符的最长子串
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     * https://leetcode.cn/problems/longest-substring-without-repeating-characters/
     * https://leetcode.cn/problems/longest-substring-without-repeating-characters/solutions/3982/hua-dong-chuang-kou-by-powcai/
     * 示例 1:
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     * 输入: s = "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     * 输入: s = "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     * 请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0, len = 0;
        while (right < s.length()) {
            char ch = s.charAt(right);
            right++;
            window.compute(ch, (key, val) -> val == null ? 1 : ++val);
            while (window.get(ch) > 1) {
                char del = s.charAt(left);
                left++;
                window.compute(del, (key, val) -> --val);
            }
            // 这里的才是没有重复的字符串
            len = Math.max(len, right - left);
        }
        return len;
    }

    @Test
    public void testLengthOfLongestSubstring() {
        Assert.assertEquals(3, lengthOfLongestSubstring("abcabcbb"));
        Assert.assertEquals(1, lengthOfLongestSubstring("bbbbb"));
        Assert.assertEquals(3, lengthOfLongestSubstring("pwwkew"));
    }

    /**
     * 数组法
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstringArray(String s) {
        int[] window = new int[256];
        int left = 0, right = 0, len = 0;
        while (right < s.length()) {
            char ch = s.charAt(right);
            right++;
            window[ch]++;
            while (window[ch] > 1) {
                char del = s.charAt(left);
                left++;
                window[del]--;
            }
            len = Math.max(len, right - left);
        }
        return len;
    }

    @Test
    public void testLengthOfLongestSubstringArray() {
        Assert.assertEquals(3, lengthOfLongestSubstringArray("abcabcbb"));
        Assert.assertEquals(1, lengthOfLongestSubstringArray("bbbbb"));
        Assert.assertEquals(3, lengthOfLongestSubstringArray("pwwkew"));
    }

    public int lengthOfLongestSubstringDp(String s) {
        int[][] dp = new int[s.length() + 1][s.length() + 1];

        return dp[1][s.length()];
    }

    @Test
    public void testLengthOfLongestSubstringDp() {
        Assert.assertEquals(3, lengthOfLongestSubstringDp("abcabcbb"));
        Assert.assertEquals(1, lengthOfLongestSubstringDp("bbbbb"));
        Assert.assertEquals(3, lengthOfLongestSubstringDp("pwwkew"));
    }

    /**
     * 30. 串联所有单词的子串
     * https://leetcode.cn/problems/substring-with-concatenation-of-all-words/
     * 给定一个字符串 s 和一个字符串数组 words。 words 中所有字符串 长度相同。
     * s 中的 串联子串 是指一个包含  words 中所有字符串以任意顺序排列连接起来的子串。
     * 例如，如果 words = ["ab","cd","ef"]， 那么 "abcdef"， "abefcd"，"cdabef"， "cdefab"，"efabcd"， 和 "efcdab" 都是串联子串。 "acdbef" 不是串联子串，因为他不是任何 words 排列的连接。
     * 返回所有串联字串在 s 中的开始索引。你可以以 任意顺序 返回答案。
     * 示例 1：
     * 输入：s = "barfoothefoobarman", words = ["foo","bar"]
     * 输出：[0,9]
     * 解释：因为 words.length == 2 同时 words[i].length == 3，连接的子字符串的长度必须为 6。
     * 子串 "barfoo" 开始位置是 0。它是 words 中以 ["bar","foo"] 顺序排列的连接。
     * 子串 "foobar" 开始位置是 9。它是 words 中以 ["foo","bar"] 顺序排列的连接。
     * 输出顺序无关紧要。返回 [9,0] 也是可以的。
     * 示例 2：
     * 输入：s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
     * 输出：[]
     * 解释：因为 words.length == 4 并且 words[i].length == 4，所以串联子串的长度必须为 16。
     * s 中没有子串长度为 16 并且等于 words 的任何顺序排列的连接。
     * 所以我们返回一个空数组。
     * 示例 3：
     * 输入：s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]
     * 输出：[6,9,12]
     * 解释：因为 words.length == 3 并且 words[i].length == 3，所以串联子串的长度必须为 9。
     * 子串 "foobarthe" 开始位置是 6。它是 words 中以 ["foo","bar","the"] 顺序排列的连接。
     * 子串 "barthefoo" 开始位置是 9。它是 words 中以 ["bar","the","foo"] 顺序排列的连接。
     * 子串 "thefoobar" 开始位置是 12。它是 wor
     *
     * @param s
     * @param words
     * @return
     */
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> ans = new ArrayList<>();
        int left = 0, right = 0;
        return ans;
    }

    @Test
    public void testFindSubstring() {
        Assert.assertEquals(true, Arrays.equals(new Integer[]{0, 9}, findSubstring("barfoothefoobarman", new String[]{"foo", "bar"}).toArray(new Integer[0])));
        Assert.assertEquals(true, Arrays.equals(new Integer[]{}, findSubstring("wordgoodgoodgoodbestword", new String[]{"word", "good", "best", "word"}).toArray(new Integer[0])));
        Assert.assertEquals(true, Arrays.equals(new Integer[]{0, 9}, findSubstring("barfoofoobarthefoobarman", new String[]{"bar", "foo", "the"}).toArray(new Integer[0])));
    }
}
