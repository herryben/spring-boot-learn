package com.learn.sort;

import org.junit.Test;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/3/3 10:14 下午
 */
public class SortPractise {
    private final int N = 5;
    private final int[] datas = new int[N];

    public void init() {
        for (int i = 0; i < datas.length; i++) {
            datas[i] = (int) (Math.random() * 100 + 1);
        }
    }

    @Test
    public void testBubble() {
        init();
        print();
        print();
    }

    @Test
    public void testSelect() {
        init();
        print();
        print();
    }

    @Test
    public void testInsert() {
        init();
        print();
        print();
    }


    @Test
    public void testShell() {
        init();
        print();
        print();
    }

    @Test
    public void testQsort() {
        init();
        print();
        print();
    }

    @Test
    public void testMerge() {
        init();
        print();
        print();
    }

    @Test
    public void testHeapsort() {
        init();
        print();
        print();
    }

    @Test
    public void testDeleteTop() {
        init();
        print();
        print();
    }

    @Test
    public void testHeapInsert() {
        init();
        print();
        print();
    }

    public void print() {
        for (int data : datas) {
            System.out.print(data + " ");
        }
        System.out.println();
    }
}
