package com.learn;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class CompletionServiceDemo {
    @Test
    public void testCompletionService() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(5);
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(service);
        List<Future<Integer>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            futureList.add(completionService.submit(new Task1(i + 1)));
        }
        for (int i = 0; i < 10; i++) {
            Integer result = completionService.take().get();
            System.out.println("任务i=="+result+"完成!"+new Date());
        }
    }
}

class Task1 implements Callable<Integer> {
    Integer i;
    public Task1(Integer i) {
        super();
        this.i = i;
    }

    @Override
    public Integer call() throws Exception {
        if (i > 5) {
            Thread.sleep(5000);
        }else {
            Thread.sleep(1000);
        }
        System.out.println("线程："+Thread.currentThread().getName()+"任务i="+i+",执行完成！");
        return i;
    }
}
