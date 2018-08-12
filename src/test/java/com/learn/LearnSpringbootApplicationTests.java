package com.learn;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnSpringbootApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(LearnSpringbootApplicationTests.class);

	@Test
	public void contextLoads() {
        List<Integer> list = Lists.newArrayList();
        for (int i = 0; i < 54; i++) {
            list.add(i);
        }
        List<List<Integer>> cache = Lists.partition(list, 10);
        for (List<Integer> tmpList: cache) {
            for (final Integer i: tmpList) {
                LOGGER.info("this is {}", i);
            }
            LOGGER.info("===============");
        }
    }
}
