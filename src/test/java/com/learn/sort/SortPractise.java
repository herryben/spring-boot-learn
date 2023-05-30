package com.learn.sort;

import com.learn.concurrent.ConcurrentMergeSort;
import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/3/3 10:14 下午
 */
public class SortPractise {
    private final int N = 10;
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
        quickSort(datas, 0, datas.length - 1);
        print();
    }

    public void quickSort(int[] nums, int left, int right) {
        if (left < right) {
            int idx = partition(nums, left, right);
            quickSort(nums, left, idx - 1);
            quickSort(nums, idx + 1, right);
        }
    }

    public int partition(int[] nums, int left, int right) {
        int idx = left + (right - left) / 2, i = left, j = right;
        int pivot = nums[idx];
        nums[idx] = nums[i];
        nums[i] = pivot;
        while (i < j) {
            while (i < j && datas[j] >= pivot) {
                j--;
            }
            datas[i] = datas[j];
            while (i < j && datas[i] < pivot) {
                i++;
            }
            datas[j] = datas[i];
        }
        nums[i] = pivot;
        return i;
    }

    @Test
    public void testMerge() {
        init();
        print();

        print();
    }

    @SneakyThrows
    @Test
    public void testConcurrentMergeSort() {
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        init();
        print();
        Future future = forkJoinPool.submit(new ConcurrentMergeSort.MergeSortTask(datas, 0, datas.length - 1));
        future.get();
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
        for (int i = (datas.length - 2) / 2; i >= 0; i--) {
            adjust(datas, i, datas.length);
        }
        print();
        datas[0] = datas[datas.length - 1];
        datas[datas.length - 1] = Integer.MIN_VALUE;
        adjust(datas, 0, datas.length - 1);
        print();
    }

    public void adjust(int[] datas, int k, int length) {
        int tmp = datas[k];
        for (int i = 2*k+1; i < length; i=2*i+1) {
            if (i + 1 < length && datas[i] < datas[i+1]) {
                i++;
            }
            if (tmp > datas[i]) {
                break;
            } else {
                datas[k] = datas[k=i];
            }
        }
        datas[k] = tmp;
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
