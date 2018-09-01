package com.learn;

import com.github.rholder.retry.*;
import com.google.common.base.Predicates;
import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.TimeLimiter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCommon {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestCommon.class);
    @Test
    public void testTimeLimiter() throws Exception{
        TimeLimiter timeLimiter = new SimpleTimeLimiter();
        LOGGER.info("===== {}", timeLimiter.callWithTimeout(() -> {
            TimeUnit.SECONDS.sleep(3);
            return 1L;
        }, 2, TimeUnit.SECONDS, false));
    }
    @Test
    public void testAttemptTimeLimiter() throws Exception{
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfException()
                .retryIfResult(Predicates.equalTo(false))
                .withAttemptTimeLimiter(AttemptTimeLimiters.fixedTimeLimit(1, TimeUnit.SECONDS))
                .withStopStrategy(StopStrategies.stopAfterAttempt(5))
                .withWaitStrategy(WaitStrategies.incrementingWait(1, TimeUnit.SECONDS, 3, TimeUnit.SECONDS))
                .build();
        LOGGER.info("=========start==========");
        LOGGER.info("========result======= {}", retryer.call(() -> {
            LOGGER.info("=========do sth===========");
            if (1 == 1) {
                throw new RuntimeException();
            }
            return true;
        }));
    }
}
