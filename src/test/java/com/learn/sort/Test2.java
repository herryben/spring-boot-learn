package com.learn.sort;

import org.junit.Test;

public class Test2 {
    private int N = 50;
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

    @Test
    public void testSelect(){
        init();
        print();
        for (int i = 0; i < datas.length; i++) {
            int k = i;
            for (int j = datas.length - 1 ;j > i ; j--) {
                if (datas[j] < datas[k]) {
                    k = j;
                }
            }
            if (k != i) {
                int tmp = datas[k];
                datas[k] = datas[i];
                datas[i] = tmp;
            }
        }
        print();
    }

    @Test
    public void testInset(){
        init();
        print();
        int j;
        for (int i = 1; i < datas.length; i++) {
            int tmp = datas[i];
            for (j = i - 1; j >=0 && tmp < datas[j]; j--) {
                datas[j + 1] = datas[j];
            }
            datas[j + 1] = tmp;
        }
        print();
    }

    @Test
    public void testBubble(){
        init();
        print();
        for (int i = 0; i < datas.length; i++) {
            for (int j = 0; j < datas.length - i - 1; j++) {
                if (datas[j] > datas[j + 1]) {
                    int tmp = datas[j];
                    datas[j] = datas[j + 1];
                    datas[j + 1] = tmp;
                }
            }
        }
        print();
    }

    @Test
    public void testQsort(){
        init();
        print();
        Qsort(datas, 0, datas.length - 1);
        print();
    }

    public void Qsort(int[] datas, int left, int right){
        if (left < right) {
            int idx = paration(datas, left, right);
            Qsort(datas, left, idx - 1);
            Qsort(datas, idx + 1, right);
        }
    }

    public int paration(int[] datas, int low, int high){
        int i= low, j = high, pivot = datas[low];
        while (i < j) {
            while (i < j && pivot <= datas[j]) {
                j--;
            }
            datas[i] = datas[j];
            while (i < j && pivot > datas[i]) {
                i++;
            }
            datas[j] = datas[i];
        }
        datas[i] = pivot;
        return i;
    }

    @Test
    public void testHeap(){
        init();
        print();
        heapSort(datas);
        print();
    }

    public void heapSort(int[] datas){
        for (int i = (datas.length - 2) / 2; i >= 0 ; i--) {
            adjust(datas, i, datas.length);
        }
        for (int i = datas.length - 1; i > 0 ; i--) {
            int tmp = datas[0];
            datas[0]= datas[i];
            datas[i] = tmp;
            adjust(datas, 0, i);
        }
    }

    public void adjust(int[] datas, int k, int length){
        int tmp = datas[k];
        for (int i = 2 * k + 1; i < length; i = 2 * i + 1) {
            if (i + 1 < length && datas[i] < datas[i + 1]) {
                i++;
            }
            if (tmp >= datas[i]) {
                break;
            } else {
                datas[k] = datas[k = i];
            }
        }
        datas[k] = tmp;
    }
}
