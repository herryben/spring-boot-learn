package com.learn.backtrack;

import com.google.common.collect.Sets;
import org.apache.commons.collections.SetUtils;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        return new String[]{};
    }

    @Test
    public void testPermutation() {
        Assert.assertEquals(new String[]{"abc", "acb", "bac", "bca", "cab", "cba"}, permutation("abc"));
    }

    /**
     * TODO 17. 电话号码的字母组合
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
        letterCombinationsDfs(digits, 0, new StringBuilder(), res);
        return res;
    }

    public static final String[] keyboard = new String[]{"", "","abc","def","ghi","jkl","mno","pqrs","tuv","xyz"};
    public void letterCombinationsDfs(String digits, int level, StringBuilder path, List<String> res) {
        if (level == digits.length()) {
            if (path.length() != 0) {
                res.add(path.toString());
            }
            return;
        }
        // 取到该层对应的数字对应的英文字母
        String str = keyboard[Integer.parseInt(String.valueOf(digits.charAt(level)))];
        for (int i = 0; i < str.length(); i++) {
            // 做选择
            letterCombinationsDfs(digits, level + 1, path.append(str.charAt(i)), res);
            // 撤销选择
            path.deleteCharAt(path.length() - 1);
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
        return Lists.newArrayList();
    }

    @Test
    public void testSubsets() {

    }
}
