package com.learn.sort;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/3/3 10:14 下午
 */
public class SortPractise {
    private int N = 10;
    private int[] datas = new int[N];
    public void init(){
        for (int i = 0; i < datas.length; i++) {
            datas[i] = (int)(Math.random() * 100 + 1);
        }
    }

    public void print(){
        for (int data: datas) {
            System.out.print(data + " ");
        }
        System.out.println();
    }
}
