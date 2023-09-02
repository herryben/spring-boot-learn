package com.learn.tree;

import com.learn.Utils.Utils;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/7/9 1:57 下午
 */
public class TreeSolution {
    @Test
    public void testUtils() {
        TreeNode root = Utils.buildBinaryTree(new Integer[]{3,5,1,6,2,0,8,null,null,7,4});
        Utils.printBinaryTree(root);
        Assert.assertEquals(true, Utils.isBinaryTreeArrayEqual(root, new Integer[]{3,5,1,6,2,0,8,null,null,7,4}));
    }
    /**
     * 剑指 Offer 68 - II. 二叉树的最近公共祖先
     * https://leetcode.cn/problems/er-cha-shu-de-zui-jin-gong-gong-zu-xian-lcof/description/
     * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
     *
     * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
     *
     * 例如，给定如下二叉树:  root = [3,5,1,6,2,0,8,null,null,7,4]
     *
     *
     *
     *
     *
     * 示例 1:
     *
     * 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
     * 输出: 3
     * 解释: 节点 5 和节点 1 的最近公共祖先是节点 3。
     * 示例 2:
     *
     * 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
     * 输出: 5
     * 解释: 节点 5 和节点 4 的最近公共祖先是节点 5。因为根据定义最近公共祖先节点可以为节点本身。
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || p == null || q == null) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        return root;
    }

    /**
     * 617. 合并二叉树
     * https://leetcode.cn/problems/merge-two-binary-trees/description/
     * 给你两棵二叉树： root1 和 root2 。
     *
     * 想象一下，当你将其中一棵覆盖到另一棵之上时，两棵树上的一些节点将会重叠（而另一些不会）。你需要将这两棵树合并成一棵新二叉树。合并的规则是：如果两个节点重叠，那么将这两个节点的值相加作为合并后节点的新值；否则，不为 null 的节点将直接作为新二叉树的节点。
     *
     * 返回合并后的二叉树。
     *
     * 注意: 合并过程必须从两个树的根节点开始。
     *
     *
     *
     * 示例 1：
     *
     *
     * 输入：root1 = [1,3,2,5], root2 = [2,1,3,null,4,null,7]
     * 输出：[3,4,5,5,4,null,7]
     * 示例 2：
     *
     * 输入：root1 = [1], root2 = [1,2]
     * 输出：[2,2]
     * 解题思路：都合并到root1上面
     * @param root1
     * @param root2
     * @return
     */
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }
        root1.val += root2.val;
        root1.left = mergeTrees(root1.left, root2.left);
        root1.right = mergeTrees(root1.right, root2.right);
        return root1;
    }

    @Test
    public void testMergeTrees() {
        Assert.assertEquals(true, Utils.isBinaryTreeArrayEqual(mergeTrees(Utils.buildBinaryTree(new Integer[]{1,3,2,5}), Utils.buildBinaryTree(new Integer[]{2,1,3,null,4,null,7})), new Integer[]{3,4,5,5,4,null,7}));
        Assert.assertEquals(true, Utils.isBinaryTreeArrayEqual(mergeTrees(Utils.buildBinaryTree(new Integer[]{1}), Utils.buildBinaryTree(new Integer[]{1,2})), new Integer[]{2,2}));
    }

    int ans;
    public int diameterOfBinaryTree(TreeNode root) {
        ans = 0;
        deth(root);
        return ans;
    }

    public int deth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int maxL = deth(root.left);
        int maxR = deth(root.right);
        ans = Math.max(ans, maxL + maxR);
        return Math.max(maxL, maxR) + 1;
    }

    @Test
    public void testDiameterOfBinaryTree() {
        Assert.assertEquals(3, diameterOfBinaryTree(Utils.buildBinaryTree(new Integer[]{1,2,3,4,5})));
        Assert.assertEquals(1, diameterOfBinaryTree(Utils.buildBinaryTree(new Integer[]{1,2})));
    }

    /**
     * 111. 二叉树的最小深度
     * https://leetcode.cn/problems/minimum-depth-of-binary-tree/description/
     * 给定一个二叉树，找出其最小深度。
     *
     * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
     *
     * 说明：叶子节点是指没有子节点的节点。
     *
     *
     *
     * 示例 1：
     *
     *
     * 输入：root = [3,9,20,null,null,15,7]
     * 输出：2
     * 示例 2：
     *
     * 输入：root = [2,null,3,null,4,null,5,null,6]
     * 输出：5
     * 解题思路：就是层级遍历
     * @param root
     * @return
     */
    public int minDepth(TreeNode root) {
        int depth = 0;
        if (root == null) {
            return depth;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            depth++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left == null && node.right == null) {
                    return depth;
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return depth;
    }

    @Test
    public void testMinDepth() {
        Assert.assertEquals(2, minDepth(Utils.buildBinaryTree(new Integer[] {3,9,20,null,null,15,7})));
        Assert.assertEquals(5, minDepth(Utils.buildBinaryTree(new Integer[] {2,null,3,null,4,null,5,null,6})));
    }

    /**
     * 94. 二叉树的中序遍历
     * https://leetcode.cn/problems/binary-tree-inorder-traversal/
     * 给定一个二叉树的根节点 root ，返回 它的 中序 遍历 。
     *
     *
     *
     * 示例 1：
     *
     *
     * 输入：root = [1,null,2,3]
     * 输出：[1,3,2]
     * 示例 2：
     *
     * 输入：root = []
     * 输出：[]
     * 示例 3：
     *
     * 输入：root = [1]
     * 输出：[1]
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderTraversal(root, result);
        return result;
    }

    public void inorderTraversal(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }
        inorderTraversal(root.left, result);
        result.add(root.val);
        inorderTraversal(root.right, result);
    }

    @Test
    public void testInorderTraversal() {
        Assert.assertEquals(true, CollectionUtils.isEqualCollection(inorderTraversal(Utils.buildBinaryTree(new Integer[]{1,null,2,3})), Lists.newArrayList(1,3,2)));
        Assert.assertEquals(true, CollectionUtils.isEqualCollection(inorderTraversal(Utils.buildBinaryTree(new Integer[]{})), Lists.newArrayList()));
        Assert.assertEquals(true, CollectionUtils.isEqualCollection(inorderTraversal(Utils.buildBinaryTree(new Integer[]{1})), Lists.newArrayList(1)));
    }

    /**
     * 101. 对称二叉树
     * 给你一个二叉树的根节点 root ， 检查它是否轴对称。
     *
     *
     *
     * 示例 1：
     *
     *
     * 输入：root = [1,2,2,3,4,4,3]
     * 输出：true
     * 示例 2：
     *
     *
     * 输入：root = [1,2,2,null,3,null,3]
     * 输出：false
     * 解题思路：把一棵树传入2遍判断是否对称
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        return isSymmetric(root, root);
    }

    public boolean isSymmetric(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return true;
        }
        if (root1 == null || root2 == null) {
            return false;
        }
        return root1.val == root2.val && isSymmetric(root1.left, root2.right) && isSymmetric(root1.right, root2.left);
    }

    @Test
    public void testIsSymmetric() {
        Assert.assertEquals(true, isSymmetric(Utils.buildBinaryTree(new Integer[]{1,2,2,3,4,4,3})));
        Assert.assertEquals(false, isSymmetric(Utils.buildBinaryTree(new Integer[]{1,2,2,null,3,null,3})));
    }

    /**
     * 114. 二叉树展开为链表
     * https://leetcode.cn/problems/flatten-binary-tree-to-linked-list/description/
     * 给你二叉树的根结点 root ，请你将它展开为一个单链表：
     *
     * 展开后的单链表应该同样使用 TreeNode ，其中 right 子指针指向链表中下一个结点，而左子指针始终为 null 。
     * 展开后的单链表应该与二叉树 先序遍历 顺序相同。
     *
     *
     * 示例 1：
     *
     *
     * 输入：root = [1,2,5,3,4,null,6]
     * 输出：[1,null,2,null,3,null,4,null,5,null,6]
     * 示例 2：
     *
     * 输入：root = []
     * 输出：[]
     * 示例 3：
     *
     * 输入：root = [0]
     * 输出：[0]
     * @param root
     */
    public TreeNode flatten(TreeNode root) {
        List<TreeNode> list = new ArrayList<TreeNode>();
        preOrder(root, list);
        for (int i = 1; i < list.size(); i++) {
            TreeNode pre = list.get(i - 1);
            TreeNode cur = list.get(i);
            pre.right = cur;
            pre.left = null;
        }
        return root;
    }

    public void preOrder(TreeNode root, List<TreeNode> list) {
        if (root == null) {
            return;
        }
        list.add(root);
        preOrder(root.left, list);
        preOrder(root.right, list);
    }

    @Test
    public void testFlatten() {
        Assert.assertEquals(true, Utils.isBinaryTreeArrayEqual(flatten(Utils.buildBinaryTree(new Integer[]{1,2,5,3,4,null,6})), new Integer[]{1,null,2,null,3,null,4,null,5,null,6}));
        Assert.assertEquals(true, Utils.isBinaryTreeArrayEqual(flatten(Utils.buildBinaryTree(new Integer[]{})), new Integer[]{}));
        Assert.assertEquals(true, Utils.isBinaryTreeArrayEqual(flatten(Utils.buildBinaryTree(new Integer[]{0})), new Integer[]{0}));
    }

    /**
     * 98. 验证二叉搜索树
     * https://leetcode.cn/problems/validate-binary-search-tree/description/
     * 给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。
     *
     * 有效 二叉搜索树定义如下：
     *
     * 节点的左子树只包含 小于 当前节点的数。
     * 节点的右子树只包含 大于 当前节点的数。
     * 所有左子树和右子树自身必须也是二叉搜索树。
     *
     *
     * 示例 1：
     *
     *
     * 输入：root = [2,1,3]
     * 输出：true
     * 示例 2：
     *
     *
     * 输入：root = [5,1,4,null,null,3,6]
     * 输出：false
     * 解释：根节点的值是 5 ，但是右子节点的值是 4 。
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (root.left != null && root.val <= root.left.val) {
            return false;
        }
        if (root.right != null && root.val >= root.right.val) {
            return false;
        }
        return isValidBST(root.left) && isValidBST(root.right);
    }

    @Test
    public void testIsValidBST() {
        Assert.assertEquals(true, isValidBST(Utils.buildBinaryTree(new Integer[]{2,1,3})));
        Assert.assertEquals(false, isValidBST(Utils.buildBinaryTree(new Integer[]{5,1,4,null,null,3,6})));
    }

    /**
     * 105. 从前序与中序遍历序列构造二叉树
     * https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/
     * 给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历， inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。
     *
     *
     *
     * 示例 1:
     *
     *
     * 输入: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
     * 输出: [3,9,20,null,null,15,7]
     * 示例 2:
     *
     * 输入: preorder = [-1], inorder = [-1]
     * 输出: [-1]
     * 解题思路：
     * 1.用map缓存inorder的位置
     * 2.模拟preorder
     * @param preorder
     * @param inorder
     * @return
     */
    int pos = 0;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        pos = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return inorder(preorder, map, 0, inorder.length);
     }

     public TreeNode inorder(int[] preorder, Map<Integer, Integer> map, int left, int right) {
         if (left == right) {
             return null;
         }
         int rootVal = preorder[pos++];
         int rootIndex = map.get(rootVal);
         TreeNode root = new TreeNode(rootVal);
         root.left = inorder(preorder, map, left, rootIndex);
         root.right = inorder(preorder, map, rootIndex + 1, right);
         return root;
     }

    @Test
    public void testBuildTree() {
        Assert.assertEquals(true, Utils.isBinaryTreeArrayEqual(buildTree(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7}), new Integer[]{3, 9, 20, null, null, 15, 7}));
        Assert.assertEquals(true, Utils.isBinaryTreeArrayEqual(buildTree(new int[]{-1}, new int[]{-1}), new Integer[]{-1}));
    }

    /**
     * 226. 翻转二叉树
     * https://leetcode.cn/problems/invert-binary-tree/
     * 给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。
     *
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return root;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

    @Test
    public void testInvertTree() {
        Assert.assertEquals(true, Utils.isBinaryTreeArrayEqual(invertTree(Utils.buildBinaryTree(new Integer[]{4, 2, 7, 1, 3, 6, 9})), new Integer[]{4, 7, 2, 9, 6, 3, 1}));
        Assert.assertEquals(true, Utils.isBinaryTreeArrayEqual(invertTree(Utils.buildBinaryTree(new Integer[]{})), new Integer[]{}));
        Assert.assertEquals(true, Utils.isBinaryTreeArrayEqual(invertTree(Utils.buildBinaryTree(new Integer[]{2, 1, 3})), new Integer[]{2, 3, 1}));
    }

    /**
     * 337. 打家劫舍 III
     * https://leetcode.cn/problems/house-robber-iii/description/
     * 小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为 root 。
     * 除了 root 之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。 如果 两个直接相连的房子在同一天晚上被打劫 ，房屋将自动报警。
     * 给定二叉树的 root 。返回 在不触动警报的情况下 ，小偷能够盗取的最高金额 。
     *
     * @param root
     * @return
     */
    public int rob(TreeNode root) {
        int[] ans = search(root);
        return Math.max(ans[0], ans[1]);
    }

    public int[] search(TreeNode root) {
        int[] rob = new int[2];
        if (root == null) {
            return rob;
        }
        int[] left = search(root.left);
        int[] right = search(root.right);
        rob[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        rob[1] = root.val + left[0] + right[0];
        return rob;
    }

    @Test
    public void testRob() {
        Assert.assertEquals(7, rob(Utils.buildBinaryTree(new Integer[]{3, 2, 3, null, 3, null, 1})));
        Assert.assertEquals(9, rob(Utils.buildBinaryTree(new Integer[]{3, 4, 5, 1, 3, null, 1})));
    }
}
