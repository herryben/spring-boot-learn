package com.learn.window;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
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
                char del = str.charAt(left);
                // 左移窗口
                left++;
                // 进行窗口内的一些列更新
                if (need.containsKey(del)) {
                    if (need.get(del).equals(window.get(del))) {
                        valid--;
                    }
                    window.compute(del, (key, val) -> --val);
                }
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
        // 长度为p，不是s
        for (int i = 0; i < pLen; i++) {
            ++sCount[s.charAt(i) - 'a'];
            ++pCount[p.charAt(i) - 'a'];
        }
        if (Arrays.equals(sCount, pCount)) {
            ans.add(0);
        }

        for (int i = 0; i < sLen - pLen; i++) {
            // 移动窗口
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
            // 只要有重复的就缩小敞口
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

    /**
     * dp[i][]: s[0..i]的无重复字符的最长子串长度
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstringDp(String s) {
        int[] dp = new int[s.length()];
        int len = s.length();
        dp[0] = 1;
        for (int i = 1; i < len; i++) {
            // 看重复的字符在i前面还是后面
            int idx = s.indexOf(s.charAt(i), i - dp[i - 1]);
            if (idx < i) {
                // 在前面则长度未idx到i
                dp[i] = i - idx;
            } else {
                // 在后面则长度+1
                dp[i] = dp[i - 1] + 1;
            }
        }
        return Arrays.stream(dp).max().getAsInt();
    }

    @Test
    public void testLengthOfLongestSubstringDp() {
        Assert.assertEquals(3, lengthOfLongestSubstringDp("abcab"));
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
     * 解题思路：以len * words.length为窗口一直滑动
     * @param s
     * @param words
     * @return
     */
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> ans = new ArrayList<>();
        int len = words[0].length();
        if (s.length() < len * words.length) {
            return ans;
        }
        Map<String, Integer> need = new HashMap<>();
        for (String word : words) {
            need.compute(word, (key, val) -> val == null ? 1 : ++val);
        }
        for (int i = len * words.length - 1; i < s.length(); i++) {
            Map<String, Integer> window = new HashMap<>();
            int valid = 0;
            for (int j = i - len * words.length + 1; j <= i; j += len) {
                String str = s.substring(j, j + len);
                if (need.containsKey(str)) {
                    window.compute(str, (key, val) -> val == null ? 1 : ++val);
                    if (need.get(str).equals(window.get(str))) {
                        valid++;
                    }
                    if (valid == need.keySet().size()) {
                        ans.add(i - len * words.length + 1);
                    }
                }
            }
        }
        return ans;
    }

    @Test
    public void testFindSubstring() {
        Assert.assertEquals(true, Arrays.equals(new Integer[]{1}, findSubstring("ababaab", new String[]{"ab", "ba", "ba"}).toArray(new Integer[0])));
        Assert.assertEquals(true, Arrays.equals(new Integer[]{13}, findSubstring("lingmindraboofooowingdingbarrwingmonkeypoundcake", new String[]{"fooo", "barr", "wing", "ding", "wing"}).toArray(new Integer[0])));
        Assert.assertEquals(true, Arrays.equals(new Integer[]{8}, findSubstring("wordgoodgoodgoodbestword", new String[]{"word", "good", "best", "good"}).toArray(new Integer[0])));
        Assert.assertEquals(true, Arrays.equals(new Integer[]{0, 9}, findSubstring("barfoothefoobarman", new String[]{"foo", "bar"}).toArray(new Integer[0])));
        Assert.assertEquals(true, Arrays.equals(new Integer[]{}, findSubstring("wordgoodgoodgoodbestword", new String[]{"word", "good", "best", "word"}).toArray(new Integer[0])));
        Assert.assertEquals(true, Arrays.equals(new Integer[]{6, 9, 12}, findSubstring("barfoofoobarthefoobarman", new String[]{"bar", "foo", "the"}).toArray(new Integer[0])));
    }

    /**
     * 解题思路：常规滑动窗口+字符串长度内逐一尝试
     *
     * @param s
     * @param words
     * @return
     */
    public List<Integer> findSubstringSliding(String s, String[] words) {
        List<Integer> ans = new ArrayList<>();
        int wordLen = words[0].length();
        if (s.length() < wordLen * words.length) {
            return ans;
        }
        Map<String, Integer> need = new HashMap<>();
        for (String word : words) {
            need.compute(word, (key, val) -> val == null ? 1 : ++val);
        }
        for (int start = 0; start < wordLen; start++) {
            int left = start, right = start, valid = 0;
            Map<String, Integer> window = new HashMap<>();
            while (right < s.length()) {
                String str = right + wordLen < s.length() ? s.substring(right, right + wordLen) : s.substring(right);
                right += wordLen;
                if (need.containsKey(str)) {
                    window.compute(str, (key, val) -> val == null ? 1 : ++val);
                    if (need.get(str).equals(window.get(str))) {
                        valid++;
                    }
                }
                while (right - left >= words.length * wordLen) {
                    if (right - left == words.length * wordLen && valid == need.size()) {
                        ans.add(left);
                    }
                    String del = left + wordLen < s.length() ? s.substring(left, left + wordLen) : s.substring(left);
                    left += wordLen;
                    if (need.containsKey(del)) {
                        if (need.get(del).equals(window.get(del))) {
                            valid--;
                        }
                        window.compute(del, (key, val) -> --val);
                    }
                }
            }
        }
        return ans;
    }

    @Test
    public void findSubstringSliding() {
        Assert.assertEquals(true, Arrays.equals(new Integer[]{1}, findSubstringSliding("ababaab", new String[]{"ab", "ba", "ba"}).toArray(new Integer[0])));
        Assert.assertEquals(true, Arrays.equals(new Integer[]{13}, findSubstringSliding("lingmindraboofooowingdingbarrwingmonkeypoundcake", new String[]{"fooo", "barr", "wing", "ding", "wing"}).toArray(new Integer[0])));
        Assert.assertEquals(true, Arrays.equals(new Integer[]{8}, findSubstringSliding("wordgoodgoodgoodbestword", new String[]{"word", "good", "best", "good"}).toArray(new Integer[0])));
        Assert.assertEquals(true, Arrays.equals(new Integer[]{0, 9}, findSubstringSliding("barfoothefoobarman", new String[]{"foo", "bar"}).toArray(new Integer[0])));
        Assert.assertEquals(true, Arrays.equals(new Integer[]{}, findSubstringSliding("wordgoodgoodgoodbestword", new String[]{"word", "good", "best", "word"}).toArray(new Integer[0])));
        Assert.assertEquals(true, Arrays.equals(new Integer[]{6, 9, 12}, findSubstringSliding("barfoofoobarthefoobarman", new String[]{"bar", "foo", "the"}).toArray(new Integer[0])));
    }

    /**
     * 239. 滑动窗口最大值
     * https://leetcode.cn/problems/sliding-window-maximum/
     * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
     * 返回 滑动窗口中的最大值 。
     * 示例 1：
     * 输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
     * 输出：[3,3,5,5,6,7]
     * 解释：
     * 滑动窗口的位置                最大值
     * ---------------               -----
     * [1  3  -1] -3  5  3  6  7      3
     * 1 [3  -1  -3] 5  3  6  7       3
     * 1  3 [-1  -3  5] 3  6  7       5
     * 1  3  -1 [-3  5  3] 6  7       5
     * 1  3  -1  -3 [5  3  6] 7       6
     * 1  3  -1  -3  5 [3  6  7]      7
     * 示例 2：
     * 输入：nums = [1], k = 1
     * 输出：[1]
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] ans = new int[nums.length - k + 1];
        Deque<Integer> queue = new LinkedList<>();
        for (int i = 0; i < k; i++) {
            while (!queue.isEmpty() && nums[queue.peekLast()] < nums[i]) {
                queue.pollLast();
            }
            queue.offerLast(i);
        }
        ans[0] = nums[queue.peekFirst()];
        for (int i = k; i < nums.length; i++) {
            while (!queue.isEmpty() && i - queue.peekFirst() + 1 > k) {
                queue.pollFirst();
            }
            while (!queue.isEmpty() && nums[queue.peekLast()] < nums[i]) {
                queue.pollLast();
            }
            queue.offerLast(i);
            ans[i - k + 1] = nums[queue.peekFirst()];
        }
        return ans;
    }

    @Test
    public void testMaxSlidingWindow() {
        Assert.assertEquals(true, Arrays.equals(new int[]{3, 3, 5, 5, 6, 7}, maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3)));
        Assert.assertEquals(true, Arrays.equals(new int[]{1}, maxSlidingWindow(new int[]{1}, 1)));
    }

    /**
     * 632. 最小区间
     * https://leetcode.cn/problems/smallest-range-covering-elements-from-k-lists/description/
     * 你有 k 个 非递减排列 的整数列表。找到一个 最小 区间，使得 k 个列表中的每个列表至少有一个数包含在其中。
     * 我们定义如果 b-a < d-c 或者在 b-a == d-c 时 a < c，则区间 [a,b] 比 [c,d] 小。
     * 示例 1：
     * 输入：nums = [[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
     * 输出：[20,24]
     * 解释：
     * 列表 1：[4, 10, 15, 24, 26]，24 在区间 [20,24] 中。
     * 列表 2：[0, 9, 12, 20]，20 在区间 [20,24] 中。
     * 列表 3：[5, 18, 22, 30]，22 在区间 [20,24] 中。
     * 示例 2：
     * 输入：nums = [[1,2,3],[1,2,3],[1,2,3]]
     * 输出：[1,1]
     * 解题思路：
     * 用Map indices计算每个数字在每个队列中出现的频率
     * 在用滑动窗口在xMin..xMax之间寻找满足区间数字在所有列表中都出现的最小区间
     * @param nums
     * @return
     */
    public int[] smallestRange(List<List<Integer>> nums) {
        int size = nums.size();
        Map<Integer, List<Integer>> indices = new HashMap<Integer, List<Integer>>();
        int xMin = Integer.MAX_VALUE, xMax = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            for (int x : nums.get(i)) {
                List<Integer> list = indices.getOrDefault(x, new ArrayList<Integer>());
                list.add(i);
                indices.put(x, list);
                xMin = Math.min(xMin, x);
                xMax = Math.max(xMax, x);
            }
        }

        int[] freq = new int[size];
        int inside = 0;
        int left = xMin, right = xMin - 1;
        int bestLeft = xMin, bestRight = xMax;

        while (right < xMax) {
            right++;
            if (indices.containsKey(right)) {
                for (int x : indices.get(right)) {
                    freq[x]++;
                    if (freq[x] == 1) {
                        inside++;
                    }
                }
                while (inside == size) {
                    if (right - left < bestRight - bestLeft) {
                        bestLeft = left;
                        bestRight = right;
                    }
                    if (indices.containsKey(left)) {
                        for (int x : indices.get(left)) {
                            freq[x]--;
                            if (freq[x] == 0) {
                                inside--;
                            }
                        }
                    }
                    left++;
                }
            }
        }

        return new int[]{bestLeft, bestRight};
    }

    @Test
    public void testSmallestRange() {
        List<List<Integer>> list1 = new ArrayList<>();
        list1.add(Lists.newArrayList(4, 10, 15, 24, 26));
        list1.add(Lists.newArrayList(0, 9, 12, 20));
        list1.add(Lists.newArrayList(5, 18, 22, 30));
        Assert.assertEquals(true, Arrays.equals(new int[]{20, 24}, smallestRange(list1)));
        List<List<Integer>> list2 = new ArrayList<>();
        list2.add(Lists.newArrayList(1, 2, 3));
        list2.add(Lists.newArrayList(1, 2, 3));
        list2.add(Lists.newArrayList(1, 2, 3));
        Assert.assertEquals(true, Arrays.equals(new int[]{1, 1}, smallestRange(list2)));
    }

    /**
     * 209. 长度最小的子数组
     * https://leetcode.cn/problems/minimum-size-subarray-sum/
     * 给定一个含有 n 个正整数的数组和一个正整数 target 。
     * 找出该数组中满足其和 ≥ target 的长度最小的 连续子数组 [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。如果不存在符合条件的子数组，返回 0 。
     * 示例 1：
     * 输入：target = 7, nums = [2,3,1,2,4,3]
     * 输出：2
     * 解释：子数组 [4,3] 是该条件下的长度最小的子数组。
     * 示例 2：
     * 输入：target = 4, nums = [1,4,4]
     * 输出：1
     * 示例 3：
     * 输入：target = 11, nums = [1,1,1,1,1,1,1,1]
     * 输出：0
     *
     * @param target
     * @param nums
     * @return
     */
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0 , right = 0, len = Integer.MAX_VALUE, sum = 0;
        while (right < nums.length) {
            int rNum = nums[right];
            sum += rNum;
            right++;
            while (left < nums.length && sum >= target) {
                len = Math.min(len, right - left);
                sum -= nums[left];
                left++;
            }
        }
        return len == Integer.MAX_VALUE ? 0 : len;
    }

    @Test
    public void testMinSubArrayLen() {
        Assert.assertEquals(2, minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3}));
        Assert.assertEquals(1, minSubArrayLen(4, new int[]{1, 4, 4}));
        Assert.assertEquals(0, minSubArrayLen(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1}));
        // TODO 这个case研究
        Assert.assertEquals(0, minSubArrayLen(1, new int[]{0, 0, 1, 1, 1, 1, 1, 1}));
    }

    /**
     * 解题思路：前缀和+二分查找
     *
     * @param target
     * @param nums
     * @return
     */
    public int minSubArrayLenPrefixSumBinary(int target, int[] nums) {
        int[] prefixSum = new int[nums.length + 1];
        for (int i = 1; i <= nums.length; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i - 1];
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 1; i <= nums.length; i++) {
            // 算出 nums[0..idx-1] - nums[0..i] >= target 即:i..idx的和
            // 这里用的是前缀和的索引，所以公式是sum[i, j] = preSum[j] - preSum[i - 1];
            // target = prefixSum[idx](sum) - prefixSum[i - 1]
            int sum = target + prefixSum[i - 1];
            int idx = minSubArrayLenPrefixSumBinaryLowBound(prefixSum, sum);
            if (idx > 0) {
                ans = Math.min(ans, idx - i + 1);
            }
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }

    private int minSubArrayLenPrefixSumBinaryLowBound(int[] data, int target) {
        int left = 0, right = data.length - 1;
        while (left < right) {
            int mid = (left + right) >> 1;
            if (data[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return data[left] >= target ? left : -1;
    }

    @Test
    public void testMinSubArrayLenPrefixSumBinary() {
        Assert.assertEquals(1, minSubArrayLenPrefixSumBinary(7, new int[]{1, 1, 1, 1, 7}));
        Assert.assertEquals(2, minSubArrayLenPrefixSumBinary(7, new int[]{2, 3, 1, 2, 4, 3}));
        Assert.assertEquals(1, minSubArrayLenPrefixSumBinary(4, new int[]{1, 4, 4}));
        Assert.assertEquals(0, minSubArrayLenPrefixSumBinary(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1}));
        Assert.assertEquals(0, minSubArrayLenPrefixSumBinary(0, new int[]{1, 1, 1, 1, 1, 1, 1, 1}));
    }

    /**
     * 1004. 最大连续1的个数 III
     * https://leetcode.cn/problems/max-consecutive-ones-iii/description/
     * 提示
     * 中等
     * 663
     * 相关企业
     * 给定一个二进制数组 nums 和一个整数 k，如果可以翻转最多 k 个 0 ，则返回 数组中连续 1 的最大个数 。
     * <p>
     * <p>
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [1,1,1,0,0,0,1,1,1,1,0], K = 2
     * 输出：6
     * 解释：[1,1,1,0,0,1,1,1,1,1,1]
     * 粗体数字从 0 翻转到 1，最长的子数组长度为 6。
     * 示例 2：
     * <p>
     * 输入：nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], K = 3
     * 输出：10
     * 解释：[0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1]
     * 粗体数字从 0 翻转到 1，最长的子数组长度为 10。
     * <p>
     * <p>
     * 提示：
     * <p>
     * 1 <= nums.length <= 105
     * nums[i] 不是 0 就是 1
     * 0 <= k <= nums.length
     *
     * @param nums
     * @param k
     * 解题思路：
     * 1. 回溯法
     * 时间复杂度：2的0个数次方，一定超时
     * @return
     */
    int ans;

    public int longestOnes(int[] nums, int k) {
        ans = 0;
        dfs(nums, 0, 0, k);
        return ans;
    }

    public int dfs(int[] num, int curAns, int cur, int k) {
        for (int i = cur; i < num.length; i++) {
            if (num[i] == 1) {
                if (i > 0 && num[i - 1] == 1) {
                    curAns++;
                    ans = Math.max(ans, curAns);
                } else {
                    curAns = 1;
                }
            } else {
                if (k > 0) {
                    num[i] = 1;
                    if (i > 0 && num[i - 1] == 1) {
                        k--;
                        dfs(num, curAns + 1, i + 1, k);
                    } else {
                        k--;
                        dfs(num, 1, i + 1, k);
                    }
                    k++;
                    num[i] = 0;
                }
            }
        }
        return curAns;
    }

    @Test
    public void testLongestOnes() {
        Assert.assertEquals(6, longestOnes(new int[]{1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0}, 2));
        Assert.assertEquals(10, longestOnes(new int[]{0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1}, 3));
        Assert.assertEquals(10, longestOnes(new int[]{0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1}, 3));
    }

    /**
     * 解题思路：
     * 1. 滑动窗口
     * 1.1 收缩条件：窗口内的最多k个0
     *
     * @param nums
     * @param k
     * @return
     */
    public int longestOnesWindows(int[] nums, int k) {
        int left = 0, right = 0, zCount = 0, maxCount = Integer.MIN_VALUE;
        while (right < nums.length) {
            int num = nums[right++];
            if (num == 0) {
                zCount++;
            }
            while (zCount > k) {
                if (nums[left++] == 0) {
                    zCount--;
                }
            }

            maxCount = Math.max(maxCount, right - left);
        }
        return maxCount;
    }

    @Test
    public void testLongestOnesWindows() {
        Assert.assertEquals(6, longestOnesWindows(new int[]{1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0}, 2));
        Assert.assertEquals(10, longestOnesWindows(new int[]{0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1}, 3));
        Assert.assertEquals(322, longestOnesWindows(new int[]{1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1}, 144));
    }

    /**
     * 解题思路：
     * 1. 动态规划
     * 1.1 dp[i][j]:前一个数字，最大翻转j次连续1的个数
     * dp[i][j] = dp[i-1][j] + 1 or dp[i-1][j+1] + 1
     * 2.时间复杂度是o(nk) 空间复杂度是o(nk)，内存会超出限制
     *
     * @param nums
     * @param k
     * @return
     */
    public int longestOnesDp(int[] nums, int k) {
        int ans = Integer.MIN_VALUE;
        int[][] dp = new int[nums.length + 1][k + 1];
        // base case
        // i==0的都为0
        // j==0的需要在迭代中计算
        // i，j越界都为默认值0
        for (int i = 1; i <= nums.length; i++) {
            for (int j = 0; j <= k; j++) {
                if (nums[i - 1] == 1) {
                    dp[i][j] = dp[i - 1][j] + 1;
                } else {
                    // 越界则为默认值0
                    dp[i][j] = j == 0 ? 0 : dp[i - 1][j - 1] + 1;
                }

                ans = Math.max(ans, dp[i][j]);
            }
        }
        return ans;
    }

    @Test
    public void testLongestOnesDp() {
        Assert.assertEquals(6, longestOnesDp(new int[]{1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0}, 2));
        Assert.assertEquals(10, longestOnesDp(new int[]{0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1}, 3));
        Assert.assertEquals(322, longestOnesDp(new int[]{1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1}, 144));
    }

    /**
     * 解题思路：
     * 1. 动态规划-空间状态压缩
     * 这里依赖的dp[i - 1][j]和dp[i - 1][j - 1]2个状态，所以需要「滚动数组」优化
     * 2.时间复杂度是o(nk) 空间复杂度是o(2n)，时间会超出限制
     *
     * @param nums
     * @param k
     * @return
     */
    public int longestOnesDpCompress(int[] nums, int k) {
        int ans = Integer.MIN_VALUE;
        // 之所以是2个维度，因为
        int[][] dp = new int[2][k + 1];
        for (int i = 1; i <= nums.length; i++) {
            for (int j = 0; j <= k; j++) {
                if (nums[i - 1] == 1) {
                    dp[i & 1][j] = dp[(i - 1) & 1][j] + 1;
                } else {
                    // 越界则为默认值0
                    dp[i & 1][j] = j == 0 ? 0 : dp[(i - 1) & 1][j - 1] + 1;
                }

                ans = Math.max(ans, dp[i & 1][j]);
            }
        }
        return ans;
    }

    @Test
    public void testLongestOnesDpCompress() {
        Assert.assertEquals(6, longestOnesDpCompress(new int[]{1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0}, 2));
        Assert.assertEquals(10, longestOnesDpCompress(new int[]{0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1}, 3));
        Assert.assertEquals(322, longestOnesDpCompress(new int[]{1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1}, 144));
    }

    /**
     * 解题思路：
     * 1.使用二分法+前缀和
     * 1.1 因为数组都是0和1所以前缀和单调递增决定了可以用二分法
     * 1.2 原数组0、1取反（1-nums[i]），问题就变成了查找1的个数
     * 1.2.1 公式 k = nums[right + 1] - nums[left];
     * 1.3 前缀和公式sum[i,j] = preSum[j + 1] - preSum[i];
     * 2. 从头到尾枚举右边界，查找合法的左边界，ans找出长度最长的左右边界
     * 2.1 因为是求最长，所以left越小越好，所以用lowerBound();
     *
     * @param nums
     * @param k
     * @return
     */
    public int longestOnesBinary(int[] nums, int k) {
        int ans = Integer.MIN_VALUE;
        int[] preSum = new int[nums.length + 1];
        for (int i = 1; i < preSum.length; i++) {
            preSum[i] = preSum[i - 1] + (1 - nums[i - 1]);
        }

        for (int right = 0; right < nums.length; right++) {
            int left = lowerBound(preSum, preSum[right + 1] - k);
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }

    public int lowerBound(int[] nums, int target) {
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = (right + left) / 2;
            if (nums[mid] == target) {
                right = mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    @Test
    public void testLongestOnesBinary() {
        Assert.assertEquals(6, longestOnesBinary(new int[]{1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0}, 2));
        Assert.assertEquals(10, longestOnesBinary(new int[]{0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1}, 3));
        Assert.assertEquals(322, longestOnesBinary(new int[]{1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1}, 144));
    }
}
