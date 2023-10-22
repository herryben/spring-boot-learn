package com.learn.greedy;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/10/22 12:48 下午
 */
public class GreedySolution {
    /**
     * 字节面试题，求大佬们看看，数组A中给定可以使用的1~9的数，返回由A数组中的元素组成的小于n的最大数。
     * 例如A={1, 2, 4, 9}，x=2533，返回2499
     * 兄弟们这个题怎么做呀，或者给一下有没有力扣类似的题的链接
     * <p>
     * 作者：Damon
     * 链接：https://leetcode.cn/circle/discuss/fbhhev/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * 解题思路：
     * 1. 保证data从小到大有序
     * 2. 贪心策略尽可能往大增，增大失败则回退，说明回退位已经比目标小，后面直接使用最大位填充即可
     *
     * @param data
     * @param target
     * @return
     */
    public String getMinWithinTarget(char[] data, String target) {
        // 整体迭代步数
        int step = 0;
        // 迭代target的索引
        int idx = 0;
        // 迭代data的索引
        int left = 0;
        boolean flag = false;
        int n = target.length(), m = data.length;
        StringBuilder sb = new StringBuilder();
        while (step < n) {
            if (flag) {
                sb.append(data[m - 1]);
                step++;
            } else if (data[left] == target.charAt(idx)) {
                sb.append(data[left]);
                idx++;
                step++;
            } else if (data[left] < target.charAt(idx)) {
                // 尽可能扩大值
                left++;
            } else {
                // 扩大失败，回退
                flag = true;
                left--;
                // 说明当前是最小值，当前已经无法回退，还需要再往前回退一个
                if (target.charAt(idx) == data[0]) {
                    sb.deleteCharAt(sb.length() - 1);
                    step--;
                }
                sb.append(data[left]);
                step++;
            }
        }
        return sb.toString();
    }

    @Test
    public void testGetMinWithinTarget() {
        Assert.assertEquals("2499", getMinWithinTarget(new char[] {'1', '2', '4', '9'}, "2533"));
        Assert.assertEquals("9199", getMinWithinTarget(new char[] {'1', '9'}, "9911"));
    }
}
