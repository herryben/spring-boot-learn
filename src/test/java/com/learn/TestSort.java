package com.learn;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class TestSort {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestSort.class);
    @Test
    public void testSelect() {
        int array[] = new int[10];
        int tmp;
        for (int i = 0; i < array.length; i++) {
            array[i] = (int)(1+Math.random()*100);
        }
        LOGGER.info("raw data {} ", array);
        LOGGER.info("====================");
        for (int i = 0; i < array.length; i++) {
            int k = i;
            for (int j = array.length - 1; j >  i; j--) {
                if (array[k] < array[j]) {
                    k = j;
                }
            }
            if (k != i) {
                tmp = array[i];
                array[i] = array[k];
                array[k] = tmp;
            }
        }
        LOGGER.info("sorted data {} ", array);
    }

    @Test
    public void testBubble() {
        int array[] = new int[10];
        int tmp;
        for (int i = 0; i < array.length - 1; i++) {
            array[i] = (int)(1+Math.random()*100);
        }
        LOGGER.info("raw data {} ", array);
        LOGGER.info("====================");
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                }
            }
        }
        LOGGER.info("sorted data {} ", array);
    }

    @Test
    public void testInsert() {
        int array[] = new int[10];
        int tmp, j;
        for (int i = 0; i < array.length - 1; i++) {
            array[i] = (int)(1+Math.random()*100);
        }
        LOGGER.info("raw data {} ", array);
        LOGGER.info("====================");
//        for (int i = 0; i < array.length ; i++) {
//            tmp = array[i];
//            for (j = i; j > 0 && tmp > array[j-1] ; j++) {
//                array[j] = array[j - 1];
//            }
//            array[j] = tmp;
//        }
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                tmp = array[i];
                for (j = i-1; j>=0 && tmp < array[j]; j--) {
                    array[j +1] = array[j];
                }
                array[j+1] = tmp;
            }
        }
        LOGGER.info("sorted data {} ", array);
    }

    @Test
    public void testQSort() {
        int array[] = new int[10];
        int tmp, j;
        for (int i = 0; i < array.length - 1; i++) {
            array[i] = (int)(1+Math.random()*100);
        }
        LOGGER.info("raw data {} ", array);
        LOGGER.info("====================");
        qsort(array, 0, array.length - 1);
        LOGGER.info("sorted data {} ", array);
    }

    public void qsort(int[] data, int left, int right) {
        if (left < right) {
            int idx = partition(data, left, right);
            qsort(data, left, idx - 1);
            qsort(data, idx + 1, right);
        }
    }

    public int partition(int[] data, int low, int high) {
        int pivot = data[low];
        int i = low, j = high;
        while (i < j) {
            while (i < j && data[j] >= pivot) {
                --j;
            }
            data[i] = data[j];
            while (i < j && data[i] < pivot) {
                ++i;
            }
            data[j] = data[i];
        }
        data[i] = pivot;
        return i;
    }

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
    public void testHeapsort() {
        init();
        print();
        heapSort(datas);
        print();
    }

    public void heapSort(int[] datas) {
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
            }else {
                datas[k] = datas[k = i];
            }
        }
        datas[k] = tmp;
    }

    @org.junit.Test
    public void testDeleteTop(){
        init();
        print();
        for (int i = datas.length - 1; i >= 0; i--) {
            adjust(datas, i, datas.length);
        }
        print();
        datas[0] = datas[datas.length - 1];
        datas[datas.length - 1] = Integer.MIN_VALUE;
        adjust(datas, 0, datas.length - 1);
        print();
    }

    @org.junit.Test
    public void testHeapInsert(){
        init();
        datas[N - 1] = 0;
        print();
        for (int i = datas.length - 1; i >= 0; i--) {
            adjust(datas, i, datas.length);
        }
        print();
        int data = Integer.MAX_VALUE;
        datas[datas.length - 1] = data;
        int k = datas.length - 1;
        int parent = (k - 1) / 2;
        while (parent >= 0 && data > datas[parent]) {
            datas[k] = datas[k = parent];
            if (parent != 0) {
                parent = (parent - 1) / 2;
            } else {
                break;
            }
        }
        datas[k] = data;
        print();
    }
}
