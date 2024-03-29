package com.learn.backtrack;

import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.SetUtils;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class BackTrackSolution {
    /**
     * 剑指 Offer 38. 字符串的排列
     * https://leetcode.cn/problems/zi-fu-chuan-de-pai-lie-lcof/
     * 示例:
     * 输入：s = "abc"
     * 输出：["abc","acb","bac","bca","cab","cba"]
     * 解题思路：就是普通的排列
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
     * 31. 下一个排列
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
     * 解题思路：
     * https://leetcode.cn/problems/next-permutation/solutions/80560/xia-yi-ge-pai-lie-suan-fa-xiang-jie-si-lu-tui-dao-/
     * 算法过程
     * 标准的 “下一个排列” 算法可以描述为：
     * 1.从后向前 查找第一个 相邻升序 的元素对 (i,j)，满足 A[i] < A[j]。此时 [j,end) 必然是降序
     * 2.在 [j,end) 从后向前 查找第一个满足 A[i] < A[k] 的 k。A[i]、A[k] 分别就是上文所说的「小数」、「大数」
     * 3.将 A[i] 与 A[k] 交换
     * 4.可以断定这时 [j,end) 必然是降序，逆置 [j,end)，使其升序
     * 5.如果在步骤 1 找不到符合的相邻元素对，说明当前 [begin,end) 为一个降序顺序，则直接跳到步骤 4
     * 该方法支持数据重复，且在 C++ STL 中被采用。
     *
     * 1. 从后向前第一个降式逆置就是升式,所以要找到从后向前的第一个升式
     * 2. 又要让这个逆置后的升式尽可能地小，所以要找升式前一个数的第一大的数，并交换
     * 3. 把交换后的降式逆置为升式
     * @param nums
     */
    public int[] nextPermutation(int[] nums) {
        // i是前面一个数，j是后面一个数，k是第一个比i大的数
        int i = nums.length - 2, j = nums.length - 1, k = nums.length - 1;
        // 1. 从后向前第一个降式逆置就是升式,所以要找到从后向前的第一个升式
        // 寻找第一个相邻升序的元素对 (i,j)
        while (i >= 0 && nums[i] >= nums[j]) {
            i--;
            j--;
        }
        // 2. 又要让这个逆置后的升式尽可能地小，所以要找升式前一个数的第一大的数，并交换
        // 如果找到了i,下一步就是找到第一个比他大的k并交换
        if (i >= 0) {
            while (nums[i] >= nums[k]) {
                k--;
            }
            int tmp = nums[i];
            nums[i] = nums[k];
            nums[k] = tmp;
        }
        // 3. 把交换后的降式逆置为升式
        // [j,end)开始逆置
        for (int left = j, right = nums.length - 1; left < right; left++, right--) {
            int tmp = nums[left];
            nums[left] = nums[right];
            nums[right] = tmp;
        }
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
     * 解题思路：
     * 1.就是普通组合的变种
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
     * 78. 子集
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
     *  2.1就是让左括号增长快于右括号
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
        // 让左括号增长快于右括号
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
//        combinationSum(candidates, 0, 0, target, result, new LinkedList<>());
        dfs(result, candidates, 0, new LinkedList<>(), target, 0);
        return result;
    }

    public void combinationSum(int[] candidates, int sum, int start, int target, List<List<Integer>> result, List<Integer> track) {
        if (sum == target) {
            result.add(new ArrayList<>(track));
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if (sum + candidates[i] > target) {
                return;
            }
            track.add(candidates[i]);
            // 数字可以选多次，所以这里和传统组合的区别是不需要迭代i
            combinationSum(candidates, sum + candidates[i], i, target, result, track);
            track.remove(track.size() - 1);
        }
    }

    /**
     * 解题思路：
     * 1. 有条件回溯
     * 2. 普通组合的变种
     *
     * @param res
     * @param candidates
     * @param sum
     * @param path
     * @param target
     * @param start
     */
    public void dfs(List<List<Integer>> res, int[] candidates, int sum, List<Integer> path, int target, int start) {
        if (sum == target) {
            res.add(path);
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if (sum + candidates[i] > target) {
                return;
            }
            path.add(candidates[i]);
            // 数字可以选多次，所以这里和传统组合的区别是不需要迭代i
            dfs(res, candidates, sum + candidates[i], new ArrayList<>(path), target, i);
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
     * 输入：board = [["A","B","C","E"],
     *              ["S","F","C","S"],
     *              ["A","D","E","E"]], word = "ABCCED"
     * 输出：true
     * 示例 2：
     * 输入：board = [["A","B","C","E"],
     *              ["S","F","C","S"],
     *              ["A","D","E","E"]], word = "SEE"
     * 输出：true
     * 示例 3：
     * 输入：board = [["A","B","C","E"],
     *              ["S","F","C","S"],
     *              ["A","D","E","E"]], word = "ABCB"
     * 输出：false
     * 解题思路：
     * 1.棋盘类题目固定套路：从每个位置开始
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
        // 上一层是+1，所以需要一进来就判断
        if (step == word.length()) {
            return true;
        }
        // 越界 false
        if (x < 0 || y < 0 || x >= board.length || y >= board[0].length) {
            return false;
        }
        // 访问过 false
        if (isVisited[x][y]) {
            return false;
        }
        // 不是目标字符 false
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

    /**
     * 494. 目标和
     * https://leetcode.cn/problems/target-sum/
     * 给你一个整数数组 nums 和一个整数 target 。
     *
     * 向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
     *
     * 例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。
     * 返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
     * 示例 1：
     *
     * 输入：nums = [1,1,1,1,1], target = 3
     * 输出：5
     * 解释：一共有 5 种方法让最终目标和为 3 。
     * -1 + 1 + 1 + 1 + 1 = 3
     * +1 - 1 + 1 + 1 + 1 = 3
     * +1 + 1 - 1 + 1 + 1 = 3
     * +1 + 1 + 1 - 1 + 1 = 3
     * +1 + 1 + 1 + 1 - 1 = 3
     *
     * 示例 2：
     * 输入：nums = [1], target = 1
     * 输出：1
     * 解题思路：
     * 1.
     * @param nums
     * @param target
     * @return
     */
    public int findTargetSumWays(int[] nums, int target) {
        return findTargetSumWays(nums, target, 0, 0, 0);
    }

    public int findTargetSumWays(int[] nums, int target, int total, int level, int ans) {
        // 一开始终止条件，等于目标和 且 层级用完
        if (total == target && level == nums.length) {
            return ans + 1;
        }
        // 终止条件，当前层级已经用完
        if (level >= nums.length) {
            return 0;
        }
        return findTargetSumWays(nums, target, total + nums[level], level + 1, ans)
                + findTargetSumWays(nums, target, total - nums[level], level + 1, ans);
    }

    @Test
    public void testFindTargetSumWays() {
        Assert.assertEquals(5, findTargetSumWays(new int[] {1,1,1,1,1}, 3));
        Assert.assertEquals(1, findTargetSumWays(new int[]{1},1));
    }

    /**
     * 131. 分割回文串
     * https://leetcode.cn/problems/palindrome-partitioning/?envType=study-plan-v2&envId=top-100-liked
     * 给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是 回文串 。返回 s 所有可能的分割方案。
     * <p>
     * 回文串 是正着读和反着读都一样的字符串。
     * 示例 1：
     * <p>
     * 输入：s = "aab"
     * 输出：[["a","a","b"],["aa","b"]]
     * 示例 2：
     * 解题思路：
     * 1.dp存储回文状态
     * 1.1 dp[i][j] = charAt(i) == charAt(j) && dp[i+1][j-1] (i < j)
     * 1.2 dp[i][j] = ture (i >= j)
     * 2.进行标准排列变形
     * 输入：s = "a"
     *
     * @param s
     * @return
     */
    public List<List<String>> partition(String s) {
        List<List<String>> res = Lists.newArrayList();
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], true);
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                dp[i][j] = s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1];
            }
        }

        backTrack(s, res, n, dp, 0, new ArrayList<>());
        return res;
    }

    public void backTrack(String s, List<List<String>> res, int n, boolean[][] dp, int i, List<String> track) {
        if (i == n) {
            res.add(new ArrayList<>(track));
            return;
        }
        for (int j = i; j < n; j++) {
            if (dp[i][j]) {
                track.add(s.substring(i, j + 1));
                backTrack(s, res, n, dp, j + 1, track);
                track.remove(track.size() - 1);
            }
        }
    }

    @Test
    public void testPartition() {
        List<List<String>> list1 = new ArrayList<>();
        list1.add(Lists.newArrayList("a", "a", "b"));
        list1.add(Lists.newArrayList("aa", "b"));
        Assert.assertEquals(true, CollectionUtils.isEqualCollection(partition("aab"), list1));
        List<List<String>> list2 = new ArrayList<>();
        list2.add(Lists.newArrayList("a"));
        Assert.assertEquals(true, CollectionUtils.isEqualCollection(partition("a"), list2));
    }

    /**
     * 51. N 皇后
     * https://leetcode.cn/problems/n-queens/description/?envType=study-plan-v2&envId=top-100-liked
     * 按照国际象棋的规则，皇后可以攻击与之处在同一行或同一列或同一斜线上的棋子。
     * <p>
     * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
     * <p>
     * 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
     * <p>
     * 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
     * <p>
     * <p>
     * <p>
     * 示例 1：
     * <p>
     * <p>
     * 输入：n = 4
     * 输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
     * 解释：如上图所示，4 皇后问题存在两个不同的解法。
     * 示例 2：
     * <p>
     * 输入：n = 1
     * 输出：[["Q"]]
     *
     * @param n
     * @return
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        int[] queue = new int[n];
        Set<Integer> cols = new HashSet<>();
        Set<Integer> d1 = new HashSet<>();
        Set<Integer> d2 = new HashSet<>();
        backTrack(res, 0, n, queue, cols, d1, d2);
        return res;
    }

    void backTrack(List<List<String>> res, int row, int n, int[] queue, Set<Integer> cols, Set<Integer> d1, Set<Integer> d2) {
        if (row == n) {
            res.add(generate(queue, n));
        }
        for (int i = 0; i < n; i++) {
            int dia1 = row - i;
            int dia2 = row + i;
            if (!cols.contains(i) && !d1.contains(dia1) && !d2.contains(dia2)) {
                cols.add(i);
                d1.add(dia1);
                d2.add(dia2);
                queue[row] = i;
                backTrack(res, row + 1, n, queue, cols, d1, d2);
                cols.remove(i);
                d1.remove(dia1);
                d2.remove(dia2);
                queue[row] = -1;
            }
        }
    }

    List<String> generate(int[] queue, int n) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            char[] ch = new char[n];
            Arrays.fill(ch, '.');
            ch[queue[i]] = 'Q';
            res.add(new String(ch));
        }
        return res;
    }

    @Test
    public void testSolveNQueens() {
        Assert.assertEquals(true, CollectionUtils.isEqualCollection(solveNQueens(4), Lists.newArrayList(Lists.newArrayList(".Q..", "...Q", "Q...", "..Q."), Lists.newArrayList("..Q.", "Q...", "...Q", ".Q.."))));
    }
}
