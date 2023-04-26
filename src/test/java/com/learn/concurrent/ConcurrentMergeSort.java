package com.learn.concurrent;

import java.util.concurrent.RecursiveAction;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/4/26 3:27 下午
 */
public class ConcurrentMergeSort {

    public static class MergeSortTask extends RecursiveAction {
        private int[] data;
        private int left;
        private int right;

        public MergeSortTask(int[] data, int left, int right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        @Override
        protected void compute() {
            if (left < right) {
                int middle = left + (right - left) / 2;
                MergeSortTask leftTask = new MergeSortTask(data, left, middle);
                MergeSortTask rightTask = new MergeSortTask(data, middle + 1, right);
                leftTask.fork();
                rightTask.fork();
                leftTask.join();
                rightTask.join();
                merge(data, left, middle, right);
            }
        }

        private void merge(int[] data, int left, int middle, int right) {
            int[] tmp = new int[right - left + 1];
            int i = left, j = middle + 1, k = 0;
            while (i <= middle && j <= right) {
                if (data[i] <= data[j]) {
                    tmp[k++] = data[i++];
                } else {
                    tmp[k++] = data[j++];
                }
            }

            while (i <= middle) {
                tmp[k++] = data[i++];
            }

            while (j <= right) {
                tmp[k++] = data[j++];
            }

            for (int x = 0; x < tmp.length; x++) {
                data[left + x] = tmp[x];
            }
        }
    }
}
