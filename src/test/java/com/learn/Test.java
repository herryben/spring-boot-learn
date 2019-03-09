package com.learn;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Test {
    private int N = 5;
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

    @org.junit.Test
    public void testSelect(){
        init();
        print();
        for (int i = 0; i < datas.length; i++) {
            int k = i;
            for (int j = datas.length - 1; j > i ; --j) {
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

    @org.junit.Test
    public void testInsert() {
        init();
        print();
        int j;
        for (int i = 1; i < datas.length; i++) {
            int tmp = datas[i];
            for (j = i - 1; j >= 0 && tmp < datas[j] ; j--) {
                datas[j + 1] = datas[j];
            }
            datas[j + 1] = tmp;
        }
        print();
    }

    @org.junit.Test
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

    @org.junit.Test
    public void testQsort() {
        init();
        print();
        Qsort(datas, 0, datas.length - 1);
        print();
    }

    public void Qsort(int[] datas, int left, int right) {
        if (left < right) {
            int idx = partition(datas, left, right);
            Qsort(datas, left, idx - 1);
            Qsort(datas, idx + 1, right);
        }
    }

    public int partition(int[] datas, int low, int high) {
        int i = low, j = high, pivot = datas[low];
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

    @org.junit.Test
    public void testBinary(){
        int[] data = new int[]{0, 1, 2, 3, 4, 5};
        for (int i = 0; i < 10; i++) {
            System.out.println("res1 " + binarySearch(data, 0, data.length - 1, i));
            System.out.println("res2 " + binarySearch(data, i));
        }
    }

    public int binarySearch(int[] data, int low, int high, int target){
        int mid = (high + low) / 2;
        if (target == data[mid]) {
            return mid;
        }
        if (low < high) {
            if (target >= data[mid]) {
                return binarySearch(data, mid + 1, high, target);
            }
            if (target < data[mid]) {
                return binarySearch(data, low, mid - 1, target);
            }
        }
        return -1;
    }

    public int binarySearch(int[] data, int target){
        int low = 0, high = data.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (data[mid] == target) {
                return mid;
            }
            if (target < data[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    @org.junit.Test
    public void testInteger(){
        Integer i1 = 4;
        Integer i2 = 5;
        if (i1 > i2)
            System.out.print("haha");
    }

    @org.junit.Test
    public void testArray(){
        int N = 4;
        int[][] data = new int[][]{ {1, 2, 3 ,4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}};
        int total = N * N, idx = 1;
        while (total > 0) {
            for (int i = idx - 1; i <= N - idx; i++) {
                System.out.print(data[idx - 1][i] + " ");
                total--;
                if (total == 0) {
                    break;
                }
            }

            for (int i = idx; i <= N - idx - 1; i++) {
                System.out.print(data[i][N - idx] + " ");
                total--;
                if (total == 0) {
                    break;
                }
            }

            for (int i = N - idx; i > idx - 1; i--) {
                System.out.print(data[N - idx][i] + " ");
                total--;
                if (total == 0) {
                    break;
                }
            }

            for (int i = N - idx; i >= idx; i--) {
                System.out.print(data[i][idx - 1] + " ");
                total--;
                if (total == 0) {
                    break;
                }
            }

            idx++;
        }
    }

    @org.junit.Test
    public void testHeapsort() {
        init();
        print();
        heapSort(datas);
        print();
    }

    public void heapSort(int[] datas){
        for (int i = (datas.length - 2) / 2; i >= 0 ; i--) {
            adjust(datas, i, datas.length);
        }

        for (int i = datas.length - 1; i > 0; i--) {
            int tmp = datas[i];
            datas[i] = datas[0];
            datas[0] = tmp;
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

    @org.junit.Test
    public void testDeleteHeap(){
        init();
        print();
        for (int i = (datas.length - 2) / 2; i >= 0 ; i--) {
            adjust(datas, i, datas.length);
        }
        print();
        datas[0] =datas[datas.length - 1];
        datas[datas.length - 1] = Integer.MIN_VALUE;
        adjust(datas, 0, datas.length - 1);
        print();
    }

    @org.junit.Test
    public void testInsertHeap() {
        init();
        print();
        for (int i = (datas.length - 1) / 2; i >= 0 ; i--) {
            adjust(datas, i, datas.length);
        }
        int data = Integer.MAX_VALUE;
        datas[datas.length - 1] = data;
        print();
        int k = datas.length - 1;
        int parent = (k - 1) / 2;
        while (parent >= 0 && data > datas[parent]) {
            datas[k] = datas[k = parent];
            if (parent != 0) {
                parent = (parent - 1) / 2;
            }else {
                break;
            }
        }
        datas[k] = data;
        print();
    }
}
