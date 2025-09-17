package com.learn.sort;

import com.learn.concurrent.ConcurrentMergeSort;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/3/3 10:14 下午
 */
@Slf4j
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
    public void testSelect() {
        init();
        print();
        for (int i = 0; i < datas.length; i++) {
            int k = i;
            for (int j = datas.length - 1; j > i; j--) {
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
    public void testInsert() {
        init();
        print();
        int j, tmp;
        for (int i = 1; i < datas.length; i++) {
            if (datas[i] < datas[i - 1]) {
                tmp = datas[i];
                for (j = i - 1; j >= 0 &&  datas[j] > tmp; j--) {
                    datas[j + 1] = datas[j];
                }
                datas[j + 1] = tmp;
            }
        }
        print();
    }


    @Test
    public void testShell() {
        init();
        print();
        int j, tmp;
        for (int gap = datas.length / 2; gap != 0; gap /= 2) {
            for (int i = gap; i < datas.length; i++) {
                if (datas[i] < datas[i - gap]) {
                    tmp = datas[i];
                    for (j = i - gap; j >=0 && tmp < datas[j] ; j -= gap) {
                        datas[j + gap] = datas[j];
                    }
                    datas[j + gap] = tmp;
                }
            }
        }
        print();
    }

    @Test
    public void testQsort() {
        init();
        print();
        qsort(datas, 0, datas.length - 1);
        print();
    }

    public void qsort(int[] data, int low, int high) {
        if (low < high) {
            int idx = partition(data, low, high);
            qsort(data, low, idx);
            qsort(data, idx + 1, high);
        }
    }

    public int partition(int[] data, int low, int high) {
        int i= low, j = high, pivot = data[i];
        while (i < j) {
            while (i < j && data[j] >= pivot) {
                j--;
            }
            data[i] = data[j];
            while (i < j && data[i] < pivot) {
                i++;
            }
            data[j] = data[i];
        }
        data[i] = pivot;
        return i;
    }

    @Test
    public void testMerge() {
        init();
        print();
        mergeSort(datas, 0, datas.length - 1);
        print();
    }

    public void mergeSort(int[] data, int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            mergeSort(data, low, mid);
            mergeSort(data, mid + 1, high);
            merge(data, low, mid, high);
        }
    }

    public void merge(int[] data, int low, int mid, int high) {
        int[] tmp = new int[high - low + 1];
        int i = low, j = mid + 1, k = 0;
        while (i <= mid && j <= high) {
            if (data[i] < data[j]) {
                tmp[k++] = data[i++];
            } else {
                tmp[k++] = data[j++];
            }
        }

        while (i <= mid) {
            tmp[k++] = data[i++];
        }
        while (j <= high) {
            tmp[k++] = data[j++];
        }

        for (int x = 0; x < tmp.length; x++) {
            data[low + x] = tmp[x];
        }
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
        head(datas);
        print();
    }

    public void head(int[] data) {
        for (int i = (data.length - 2) / 2; i >= 0; i--) {
            adjust(data, i, data.length);
        }
        for (int i = data.length - 1; i > 0 ; i--) {
            int tmp = data[i];
            data[i] = data[0];
            data[0] = tmp;
            adjust(data, 0, i);
        }
    }

    public void adjust(int[] data, int k, int length) {
        int tmp = data[k];
        for (int i = 2 * k + 1; i < length; i = 2 * i + 1) {
            if (i + 1 < length && data[i] < data[i + 1]) {
                i++;
            }
            if (tmp >= data[i]) {
                break;
            } else {
                data[k] = data[k = i];
            }
        }
        data[k] = tmp;
    }

    @Test
    public void testHeapInsert() {
        init();
        print();
        for (int i = (datas.length - 1) / 2; i >= 0; i--) {
            adjust(datas, i, datas.length);
        }
        print();
        datas[datas.length - 1] = Integer.MAX_VALUE;
        print();
        swim(datas, datas.length - 1);
        print();
    }

    public void swim(int[] data, int k) {
        int tmp = data[k];
        int parent = (k - 1) / 2;
        while (parent >= 0 && tmp > data[parent]) {
            data[k] = data[k = parent];
            if (parent != 0) {
                parent = (parent - 1) / 2;
            } else {
                break;
            }
        }
        data[k] = tmp;
    }

    @Test
    public void testHeapDelete() {
        init();
        print();
        for (int i = (datas.length -2) / 2; i >= 0; i--) {
            adjust(datas, i, datas.length);
        }
        print();
        datas[0] = datas[datas.length - 1];
        datas[datas.length - 1] = Integer.MIN_VALUE;
        adjust(datas, 0, datas.length - 1);
        print();
    }

    public void print() {
        for (int data : datas) {
            System.out.print(data + " ");
        }
        System.out.println();
    }

    @Test
    public void testSubset() {
        List<String> data = Lists.newArrayList("a", "b", "c");
        List<List<String>> res = new ArrayList<>();
        List<String> track = new LinkedList<>();
        subset(data, track, res, 0);
        res.forEach(item -> log.info("Subset: " + String.join(", ", item)));
    }

    /**
     * 标准子集
     *
     * @param data
     * @param res
     * @param track
     * @param start
     */
    public void subset(List<String> data, List<String> track, List<List<String>> res, int start) {
        res.add(new ArrayList<>(track));

        for (int i = start; i < data.size(); i++) {
            track.add(data.get(i));
            subset(data, track, res, i + 1);
            track.remove(track.size() - 1);
        }

    }

    @Test
    public void testCombine() {
        List<String> data = Lists.newArrayList("a", "b", "c");
        List<List<String>> res = new ArrayList<>();
        List<String> track = new LinkedList<>();
        combine(data, res, track, 2, 0);
        res.forEach(item -> log.info("Subset: " + String.join(", ", item)));
    }

    /**
     * 标准组合模板
     *
     * @param data
     * @param res
     * @param track
     * @param k
     * @param start
     */
    public void combine(List<String> data, List<List<String>> res, List<String> track, int k, int start) {
        if (k == track.size()) {
            res.add(new ArrayList<>(track));
        }

        for (int i = start; i < data.size(); i++) {
            track.add(data.get(i));
            combine(data, res, track, k, i + 1);
            track.remove(track.size() - 1);
        }
    }

    @Test
    public void testArrange() {
        List<String> data = Lists.newArrayList("a", "b", "c");
        Set<String> res = new HashSet<>();
        StringBuilder path = new StringBuilder();
        boolean[] visited = new boolean[data.size()];
        arrange(data, res, path, visited);
        res.forEach(item -> log.info("Subset: " + String.join(", ", item)));
    }

    /**
     * 标准排列
     *
     * @param data
     * @param res
     * @param track
     */
    public void arrange(List<String> data, Set<String> res, StringBuilder path, boolean[] visited) {
        if (data.size() == path.length()) {
            res.add(path.toString());
        }
        for (int i = 0; i < data.size(); i++) {
            if (visited[i]) {
                continue;
            }
            visited[i] = true;
            path.append(data.get(i));
            arrange(data, res, path, visited);
            path.deleteCharAt(path.length() - 1);
            visited[i] = false;
        }
    }
}
