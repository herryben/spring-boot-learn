package com.learn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}
