package com.learn.backtrack;

import com.google.common.collect.Sets;
import com.learn.Utils.Utils;
import org.apache.commons.collections.SetUtils;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

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
        // 就是普通组合的变种
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
        // 子集的主要特点就是无条件加入结果
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

    /**
     * https://leetcode.cn/problems/permutations/
     * 46. 全排列
     * 示例 1：
     *
     * 输入：nums = [1,2,3]
     * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
     * 示例 2：
     *
     * 输入：nums = [0,1]
     * 输出：[[0,1],[1,0]]
     * 示例 3：
     *
     * 输入：nums = [1]
     * 输出：[[1]]
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = Lists.newArrayList();
        permute(nums, res, new LinkedList<>());
        return res;
    }

    public void permute(int[] nums, List<List<Integer>> res, List<Integer> track) {
        if(track.size() == nums.length) {
            res.add(new ArrayList<>(track));
        }
        for (int i = 0; i < nums.length; i++) {
            if (track.contains(nums[i])) {
                continue;
            }
            track.add(nums[i]);
            permute(nums, res, track);
            track.remove(track.size() - 1);
        }
    }

    @Test
    public void testPermute() {
        Assert.assertEquals(true, SetUtils.isEqualSet(Sets.newHashSet(
                Lists.newArrayList(1,2,3), Lists.newArrayList(1,3,2), Lists.newArrayList(2,1,3),
                Lists.newArrayList(2,3,1), Lists.newArrayList(3,1,2) ,Lists.newArrayList(3,2,1)), Sets.newHashSet(permute(new int[] {1,2,3}))));
        Assert.assertEquals(true, SetUtils.isEqualSet(Sets.newHashSet(
                Lists.newArrayList(0, 1), Lists.newArrayList(1, 0)), Sets.newHashSet(permute(new int[] {0, 1}))));
        Assert.assertEquals(true, SetUtils.isEqualSet(Sets.newHashSet(
                Lists.newArrayList(1).stream().map(Lists::newArrayList).collect(Collectors.toList())), Sets.newHashSet(permute(new int[] {1}))));
    }

    /**
     * 22. 括号生成
     * https://leetcode.cn/problems/generate-parentheses/description/
     * 示例 1：
     *
     * 输入：n = 3
     * 输出：["((()))","(()())","(())()","()(())","()()()"]
     * 示例 2：
     *
     * 输入：n = 1
     * 输出：["()"]
     * 解题思路：
     * 1.有条件回溯
     * 2.右括号增长速度不能大于左括号
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        generateParenthesis(result, 0, 0, n, new StringBuilder());
        return result;
    }

    public void generateParenthesis(List<String> res, int open, int close, int n, StringBuilder track) {
        if (track.length() == 2 * n) {
            res.add(track.toString());
        }
        if (open < n) {
            track.append("(");
            generateParenthesis(res, open + 1, close, n, track);
            track.deleteCharAt(track.length() - 1);
        }
        if (close < open) {
            track.append(")");
            generateParenthesis(res, open, close + 1, n, track);
            track.deleteCharAt(track.length() - 1);
        }
    }

    @Test
    public void testGenerateParenthesis() {
        Assert.assertEquals(true, SetUtils.isEqualSet(Sets.newHashSet("((()))","(()())","(())()","()(())","()()()"), Sets.newHashSet(generateParenthesis(3))));
        Assert.assertEquals(true, SetUtils.isEqualSet(Sets.newHashSet("()"), Sets.newHashSet(generateParenthesis(1))));
    }

    /**
     * 39. 组合总和
     * https://leetcode.cn/problems/combination-sum/description/
     * 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，找出 candidates 中可以使数字和为目标数 target 的 所有 不同组合 ，并以列表形式返回。你可以按 任意顺序 返回这些组合。
     *
     * candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。
     *
     * 对于给定的输入，保证和为 target 的不同组合数少于 150 个。
     * 示例 1：
     *
     * 输入：candidates = [2,3,6,7], target = 7
     * 输出：[[2,2,3],[7]]
     * 解释：
     * 2 和 3 可以形成一组候选，2 + 2 + 3 = 7 。注意 2 可以使用多次。
     * 7 也是一个候选， 7 = 7 。
     * 仅有这两种组合。
     * 示例 2：
     *
     * 输入: candidates = [2,3,5], target = 8
     * 输出: [[2,2,2,2],[2,3,3],[3,5]]
     * 示例 3：
     *
     * 输入: candidates = [2], target = 1
     * 输出: []
     * 解题思路：组合的求和变种
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        combinationSum(candidates, 0, 0, target, result, new LinkedList<>());
//        dfs(result, candidates, 0, new LinkedList<>(), target, 0);
        return result;
    }

    public void combinationSum(int[] candidates, int sum, int start, int target, List<List<Integer>> result, List<Integer> track) {
        if (sum == target) {
            result.add(new ArrayList<>(track));
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if(sum + candidates[i] > target) {
                return;
            }
            track.add(candidates[i]);
            // 这里和传统组合的区别是不需要迭代i
            combinationSum(candidates, sum + candidates[i], i, target, result, track);
            track.remove(track.size() - 1);
        }
    }

    public void dfs(List<List<Integer>> res, int[] candidates, int tmp, List<Integer> path, int target, int start){
        if(tmp == target){
            res.add(path);
            return;
        }
        for(int i =start ;i < candidates.length; i++){
            if(tmp + candidates[i] > target){
                return;
            }
            path.add(candidates[i]);
            dfs(res, candidates, tmp + candidates[i], new ArrayList<>(path), target, i);
            path.remove(path.size() - 1);
        }
    }

    @Test
    public void testCombination() {
        Assert.assertEquals(true, SetUtils.isEqualSet(Sets.newHashSet(
                Lists.newArrayList(2,2,3), Lists.newArrayList(7)), Sets.newHashSet(combinationSum(new int[] {2,3,6,7}, 7))));
        Assert.assertEquals(true, SetUtils.isEqualSet(Sets.newHashSet(
                Lists.newArrayList(2, 2, 2, 2), Lists.newArrayList(2, 3, 3), Lists.newArrayList(3, 5)), Sets.newHashSet(combinationSum(new int[] {2,3,5}, 8))));
        Assert.assertEquals(true, SetUtils.isEqualSet(Sets.newHashSet(), Sets.newHashSet(combinationSum(new int[] {2}, 1))));
    }

    /**
     * https://leetcode.cn/problems/word-search/
     * 79. 单词搜索
     * 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
     * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
     * 示例 1：
     * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
     * 输出：true
     * 示例 2：
     * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
     * 输出：true
     * 示例 3：
     * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
     * 输出：false
     * 解题思路：
     * 从每个位置开始
     * 以是否访问过为状态进行回溯探测
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {
        boolean[][] isVisited = new boolean[board.length][board[0].length];
        // 棋盘类题固定套路，从每个格子开始不停地回溯
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (backTrack(board, i, j, word, 0, isVisited)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean backTrack(char[][] board, int x, int y, String word, int step, boolean[][] isVisited) {
        if (step == word.length()) {
            return true;
        }
        if (x < 0 || y < 0 || x >= board.length || y >= board[0].length) {
            return false;
        }
        if (isVisited[x][y]) {
            return false;
        }
        if (board[x][y] != word.charAt(step)) {
            return false;
        }
        isVisited[x][y] = true;
        boolean ans = backTrack(board, x + 1, y, word, step + 1, isVisited)
                || backTrack(board, x - 1, y, word, step + 1, isVisited)
                || backTrack(board, x, y + 1, word, step + 1, isVisited)
                || backTrack(board, x, y - 1, word, step + 1, isVisited);
        isVisited[x][y] = false;
        return ans;
    }

    @Test
    public void testExist() {
        Assert.assertEquals(true, exist(new char[][]{
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        }, "ABCCED"));
        Assert.assertEquals(true, exist(new char[][]{
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        }, "SEE"));
        Assert.assertEquals(false, exist(new char[][]{
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        }, "ABCB"));
    }
}
