package com.learn.sort;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@Slf4j
public class TestSort {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestSort.class);
    private final int N = 7;
    private final int[] datas = new int[N];

    @Test
    public void testSelect() {
        int[] array = new int[10];
        int tmp;
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (1 + Math.random() * 100);
        }
        LOGGER.info("raw data {} ", array);
        LOGGER.info("====================");
        for (int i = 0; i < array.length; i++) {
            int k = i;
            for (int j = array.length - 1; j > i; j--) {
                if (array[k] > array[j]) {
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
        int[] array = new int[10];
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
        int[] array = new int[10];
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
                for (j = i - 1; j >= 0 && tmp < array[j]; j--) {//j--会执行到-1
                    array[j + 1] = array[j];
                }
                array[j + 1] = tmp;
            }
        }
        LOGGER.info("sorted data {} ", array);
    }

    public void qsort(int[] data, int left, int right) {
        if (left < right) {
            int idx = partition(data, left, right);
            qsort(data, left, idx - 1);
            qsort(data, idx + 1, right);
        }
    }

    @Test
    public void testShell() {
        int[] array = new int[10];
        int j;
        for (int i = 0; i < array.length - 1; i++) {
            array[i] = (int) (1 + Math.random() * 100);
        }
        LOGGER.info("raw data {} ", array);
        for (int gap = array.length / 2; gap != 0; gap = gap / 2) {
            for (int i = gap; i < array.length; i++) {
                int tmp = array[i];
                for (j = i - gap; j >= 0 && tmp < array[j]; j = j - gap) {
                    array[j + gap] = array[j];
                }
                array[j + gap] = tmp;
            }
        }
        LOGGER.info("sorted data {} ", array);
    }

    /**
     * 挖坑填坑法
     * https://blog.csdn.net/qq_45608306/article/details/112714681
     */
    @Test
    public void testQSort() {
        int[] array = new int[10];
        int tmp, j;
        for (int i = 0; i < array.length - 1; i++) {
            array[i] = (int) (1 + Math.random() * 100);
        }
        LOGGER.info("raw data {} ", array);
        LOGGER.info("====================");
        qsort(array, 0, array.length - 1);
        LOGGER.info("sorted data {} ", array);
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

    @Test
    public void testQsortTwentyLines() {
        int[] array = new int[5];
        int tmp, j;
        for (int i = 0; i < array.length - 1; i++) {
            array[i] = (int) (1 + Math.random() * 100);
        }
        LOGGER.info("raw data {} ", array);
        LOGGER.info("====================");
        qsortTwentyLines(array, 0, array.length - 1);
        LOGGER.info("sorted data {} ", array);
    }

    /**
     * 10行递归法
     * @param data
     * @param low
     * @param high
     * @return
     */
    public void qsortTwentyLines(int[] data, int low, int high) {
        if (low >= high) {
            return;
        }
        int i = low, j = high;
        int pivot = data[low];
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
        qsortTwentyLines(data, low, i - 1);
        qsortTwentyLines(data, i + 1, high);
    }

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
        // (k-1)/2是父节点 从第一个父节点开始调整 把最大的冒在堆顶
        for (int i = (datas.length - 2) / 2; i >= 0 ; i--) {
            adjust(datas, i, datas.length);
        }
        // 从最后一个节点开始和堆顶不停互换
        for (int i = datas.length - 1; i > 0; i--) {
            int tmp = datas[i];
            datas[i] = datas[0];
            datas[0] = tmp;
            adjust(datas, 0, i);
        }
    }

    public void adjust(int[] datas, int k, int length){
        int tmp = datas[k];
        // 2k => 父节点 2k+1 => 左孩子 2k+2=> 右孩子
        for (int i = 2 * k + 1; i < length; i = 2 * i + 1) {
            // 右孩子没有超出范围(此时才有比较的意义，否则只有左孩子一个)并且左孩子小于右孩子
            if (i + 1 < length && datas[i] < datas[i + 1]) {
                i++;
            }
            if (tmp >= datas[i]) {
                // 父节点大于等于两个孩子中最大的，增调整完毕
                break;
            } else {
                // 否则，从当前节点开始继续调整
                // 父节点等于当前最大的子节点
                datas[k] = datas[k = i];
            }
        }
        // 最后剩下的就是父节点的位置
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
        // 这个是从下往上遍历
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

    @org.junit.Test
    public void testMerge(){
        init();
        print();
        mergeSort(datas, 0, datas.length - 1);
        print();
    }

    /**
     * 左右都是闭区间 [low, high]
     * @param datas
     * @param low
     * @param high
     */
    public void mergeSort(int[] datas, int low, int high){
        int mid = (low + high) / 2;
        if (low < high) {
            mergeSort(datas, low, mid);
            mergeSort(datas, mid + 1, high);
            merge(datas, low, mid, high);
        }
    }

    public void merge(int[] datas, int low, int mid, int high){
        // 这里总共有多少个
        int[] tmp = new int[high - low + 1];
        int i = low, j = mid + 1;
        int k = 0;
        // 谁小选谁
        while (i <= mid && j <= high) {
            if (datas[i] <= datas[j]) {
                tmp[k++] = datas[i++];
            } else {
                tmp[k++] = datas[j++];
            }
        }
        // 谁没选完走完剩余
        while (i <= mid) {
            tmp[k++] = datas[i++];
        }
        while (j <= high) {
            tmp[k++] = datas[j++];
        }
        // 排好的数据写会数据里面
        for (int x = 0; x < tmp.length; x++) {
            datas[low + x] = tmp[x];
        }
    }

    /**
     * 本质是从尾到头，随机交换
     */
    @Test
    public void shuffle() {
        init();
        print();
        Random random = new Random();
        for (int i = datas.length; i > 0; i--) {
            // nextInt范围是[0, i)所以是倒序迭代
            int j = random.nextInt(i);
            int tmp = datas[j];
            datas[j] = datas[i - 1];
            datas[i - 1] = tmp;
        }
        print();
    }

    @Test
    public void testBinarySearch() {
        int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i = 0; i < 15; i++) {
            log.info("testBinarySearch i={} index={}", i, binarySearch(data, i));
        }
    }

    /**
     * https://mp.weixin.qq.com/s/JgJ0jh2SJd6grQSPnN6Jww
     * https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247485044&idx=1&sn=e6b95782141c17abe206bfe2323a4226&chksm=9bd7f87caca0716aa5add0ddddce0bfe06f1f878aafb35113644ebf0cf0bfe51659da1c1b733&scene=21#wechat_redirect
     * 标准二分模板（闭区间）
     *
     * @param datas
     * @param target
     * @return
     */
    public int binarySearch(int[] datas, int target) {
        // 这里使用闭区间[2,3]
        int low = 0, high = datas.length - 1;
        // 这里的<=和上面的闭区间对应
        while (low <= high) {
            int mid = (low + high) / 2;
            if (target > datas[mid]) {
                low = mid + 1;
            } else if (target < datas[mid]) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    @Test
    public void testLeftBound() {
        int[] data = {1, 2, 3, 5, 5, 5, 5, 8, 9};
        for (int i = 0; i < 20; i++) {
            log.info("leftBound i={} index={}", i, leftBound(data, i));
        }
    }

    /**
     * 标准leftBound模板
     * 含义是小于target的数据有几个
     */
    public int leftBound(int[] datas, int target) {
        // 这里使用左闭右开区间[2,3)
        int low = 0, high = datas.length;
        // 和上面的开区间对应
        while (low < high) {
            int mid = (low + high) / 2;
            if (datas[mid] > target) {
                // 缩小右边界，开区间
                high = mid;
            } else if (datas[mid] < target) {
                // 缩小左边界，闭区间
                low = mid + 1;
            } else {
                // 缩小右边界，开区间
                high = mid;
            }
        }
        return low;
    }

    @Test
    public void testRightBound() {
        int[] data = {1, 2, 3, 5, 5, 5, 5, 8, 9};
        for (int i = 0; i < 20; i++) {
            log.info("rightBound i={} index={}", i, rightBound(data, i));
        }
    }

    /**
     * 标准rightBound模板
     *
     * @param datas
     * @param target
     * @return
     */
    public int rightBound(int[] datas, int target) {
        // 这里使用左闭右开区间[2,3)
        int low = 0, high = datas.length;
        while (low < high) {
            int mid = (low + high) / 2;
            if (datas[mid] > target) {
                // 缩小右边界，开区间
                high = mid;
            } else if (datas[mid] < target) {
                // 缩小左边界，闭区间
                low = mid + 1;
            } else {
                // 缩小左边界，闭区间
                low = mid + 1;
            }
        }
        // 因为datas[mid] == target时low = mid + 1，所以low多走了一步
        return low - 1;
    }
}
