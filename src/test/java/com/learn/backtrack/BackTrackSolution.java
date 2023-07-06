package com.learn.backtrack;

import com.google.common.collect.Sets;
import com.learn.Utils.Utils;
import org.apache.commons.collections.SetUtils;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class BackTrackSolution {
    /**
     * TODO 剑指 Offer 38. 字符串的排列
     * https://leetcode.cn/problems/zi-fu-chuan-de-pai-lie-lcof/
     * 示例:
     * 输入：s = "abc"
     * 输出：["abc","acb","bac","bca","cab","cba"]
     *
     * @param s
     * @return
     */
    public String[] permutation(String s) {
        List<String> res = new ArrayList<>();
        Set<Character> visited = new HashSet<>();
        permutationBackTrack(s, 0, new StringBuilder(), visited, res);
        return res.toArray(new String[0]);
    }

    public void permutationBackTrack(String s, int level, StringBuilder path, Set<Character> visited, List<String> res) {
        if (s.length() == level) {
            if (path.length() != 0) {
                res.add(path.toString());
            }
            return;
        }

        for (int i = 0; i < s.length(); i++) {
            if (!visited.contains(s.charAt(i))) {
                visited.add(s.charAt(i));
                permutationBackTrack(s, level + 1, path.append(s.charAt(i)), visited, res);
                visited.remove(s.charAt(i));
                path.deleteCharAt(path.length() - 1);
            }
        }
    }

    /**
     * TODO 31. 下一个排列
     * https://leetcode.cn/problems/next-permutation/description/
     * 整数数组的一个 排列  就是将其所有成员以序列或线性顺序排列。
     * 例如，arr = [1,2,3] ，以下这些都可以视作 arr 的排列：[1,2,3]、[1,3,2]、[3,1,2]、[2,3,1] 。
     * 整数数组的 下一个排列 是指其整数的下一个字典序更大的排列。更正式地，如果数组的所有排列根据其字典顺序从小到大排列在一个容器中，那么数组的 下一个排列 就是在这个有序容器中排在它后面的那个排列。如果不存在下一个更大的排列，那么这个数组必须重排为字典序最小的排列（即，其元素按升序排列）。
     * 例如，arr = [1,2,3] 的下一个排列是 [1,3,2] 。
     * 类似地，arr = [2,3,1] 的下一个排列是 [3,1,2] 。
     * 而 arr = [3,2,1] 的下一个排列是 [1,2,3] ，因为 [3,2,1] 不存在一个字典序更大的排列。
     * 给你一个整数数组 nums ，找出 nums 的下一个排列。
     * 必须 原地 修改，只允许使用额外常数空间。
     * 示例 1：
     *  输入：nums = [1,2,3]
     *  输出：[1,3,2]
     * 示例 2：
     *  输入：nums = [3,2,1]
     *  输出：[1,2,3]
     * 示例 3：
     *  输入：nums = [1,1,5]
     *  输出：[1,5,1]
     *
     * @param nums
     */
    public int[] nextPermutation(int[] nums) {
        return nums;
    }

    @Test
    public void testNextPermutation() {
        Assert.assertEquals(true, Arrays.equals(new int[]{1, 3, 2}, nextPermutation(new int[]{1, 2, 3})));
        Assert.assertEquals(true, Arrays.equals(new int[]{1, 2, 3}, nextPermutation(new int[]{3, 2, 1})));
        Assert.assertEquals(true, Arrays.equals(new int[]{1, 5, 1}, nextPermutation(new int[]{1, 1, 5})));
    }

    public static final String[] keyboard = new String[]{"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    @Test
    public void testPermutation() {
        Assert.assertEquals(true, SetUtils.isEqualSet(Sets.newHashSet("aba", "aab", "baa"), Sets.newHashSet(permutation("aab"))));
        Assert.assertEquals(true, SetUtils.isEqualSet(Sets.newHashSet(), Sets.newHashSet(permutation(""))));
        Assert.assertEquals(true, SetUtils.isEqualSet(Sets.newHashSet("abc", "acb", "bac", "bca", "cab", "cba"), Sets.newHashSet(permutation("abc"))));
    }

    /**
     * 17. 电话号码的字母组合
     * https://leetcode.cn/problems/letter-combinations-of-a-phone-number/description/
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
     * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
     * 示例 1：
     * 输入：digits = "23"
     * 输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
     * 示例 2：
     * 输入：digits = ""
     * 输出：[]
     * 示例 3：
     * 输入：digits = "2"
     * 输出：["a","b","c"]
     *
     * @param digits
     * @return
     */
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        letterCombinationsDfs(digits, res, 0, new StringBuilder());
        return res;
    }

    public void letterCombinationsDfs(String digits, List<String> res, int level, StringBuilder track) {
        if (level == digits.length()) {
            if (track.length() != 0) {
                res.add(track.toString());
            }
            return;
        }
        String letter = keyboard[Integer.parseInt(String.valueOf(digits.charAt(level)))];
        for (char ch: letter.toCharArray()) {
            track.append(ch);
            letterCombinationsDfs(digits, res, level + 1, track);
            track.deleteCharAt(track.length() - 1);
        }
    }

    @Test
    public void testLetterCombinations() {
        Assert.assertEquals(true, SetUtils.isEqualSet(Sets.newHashSet("ad","ae","af","bd","be","bf","cd","ce","cf"), Sets.newHashSet(letterCombinations("23"))));
        Assert.assertEquals(true, SetUtils.isEqualSet(Sets.newHashSet(), Sets.newHashSet(letterCombinations(""))));
        Assert.assertEquals(true, SetUtils.isEqualSet(Sets.newHashSet("a", "b", "c"), Sets.newHashSet(letterCombinations("2"))));
    }

    /**
     * TODO 78. 子集
     * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
     * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
     * 示例 1：
     * 输入：nums = [1,2,3]
     * 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
     * 示例 2：
     * 输入：nums = [0]
     * 输出：[[],[0]]
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = Lists.newArrayList();
        List<Integer> track = new LinkedList<>();
        subsets(nums, 0, res, track);
        return res;
    }

    public void subsets(int[] nums, int start, List<List<Integer>> res, List<Integer> track) {
        res.add(new ArrayList<>(track));
        for (int i = start; i < nums.length; i++) {
            track.add(nums[i]);
            subsets(nums, i + 1, res, track);
            track.remove(track.size() - 1);
        }
    }

    @Test
    public void testSubsets() {
        Assert.assertEquals(true, SetUtils.isEqualSet(Sets.newHashSet(
                Lists.newArrayList(), Lists.newArrayList(1), Lists.newArrayList(2),
                Lists.newArrayList(1, 2), Lists.newArrayList(3), Lists.newArrayList(1, 3),
                Lists.newArrayList(2, 3), Lists.newArrayList(1, 2, 3)), Sets.newHashSet(subsets(new int[] {1,2,3}))));
        Assert.assertEquals(true, SetUtils.isEqualSet(Sets.newHashSet(
                Lists.newArrayList(), Lists.newArrayList(0)), Sets.newHashSet(subsets(new int[] {0}))));
    }
}
