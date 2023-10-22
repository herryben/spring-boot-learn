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
     *
     * 作者：Damon
     * 链接：https://leetcode.cn/circle/discuss/fbhhev/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param data
     * @param target
     * @return
     */
    public String getMinWithinTarget(char[] data, String target) {

    }

    @Test
    public void testGetMinWithinTarget() {
        Assert.assertEquals("2499", getMinWithinTarget(new char[] {'1', '2', '4', '9'}, "2533"));
        Assert.assertEquals("9199", getMinWithinTarget(new char[] {'1', '9'}, "9911"));
    }
}
