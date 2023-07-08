package com.learn.prefix;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/7/7 8:37 下午
 */
public class PrefixSum {
    @Test
    public void testNumArray() {
        NumArray numArray = new NumArray(new int[] {-2, 0, 3, -5, 2, -1});
        Assert.assertEquals(1, numArray.sumRange(0, 2)); // return 1 ((-2) + 0 + 3)
        Assert.assertEquals(-1, numArray.sumRange(2, 5)); // return -1 (3 + (-5) + 2 + (-1))
        Assert.assertEquals(-3, numArray.sumRange(0, 5)); // return -3 ((-2) + 0 + 3 + (-5) + 2 + (-1))
    }

    @Test
    public void testNumMatrix() {
        NumMatrix numMatrix = new NumMatrix(new int[][]{{3,0,1,4,2},{5,6,3,2,1},{1,2,0,1,5},{4,1,0,1,7},{1,0,3,0,5}});
        Assert.assertEquals(8, numMatrix.sumRegion(2, 1, 4, 3)); // return 8 (红色矩形框的元素总和)
        Assert.assertEquals(11, numMatrix.sumRegion(1, 1, 2, 2)); // return 11 (绿色矩形框的元素总和)
        Assert.assertEquals(12, numMatrix.sumRegion(1, 2, 2, 4)); // return 12 (蓝色矩形框的元素总和)
    }
}
