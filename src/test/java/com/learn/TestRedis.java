package com.learn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
public class TestRedis {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestRedis.class);
    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    private Jedis conn = new Jedis("localhost");
    @Test
    public void testString() throws Exception{
        LOGGER.info("Jedis String {}", conn.mset("bit key", "3", "4"));
    }

    @Test
    public void testList()  throws Exception {
        LOGGER.info("Jedis List {} ", conn.blpop(60, "list"));
    }

    @Test
    public void testSet() throws Exception {
        LOGGER.info("Jedis Set sadd {} ", conn.sadd("name", "xiaoming"));
        LOGGER.info("Jedis Set sadd {} ", conn.sadd("name", "xiaoming"));
        LOGGER.info("Jedis Set sadd {} ", conn.sadd("name", "xiaohei"));
        LOGGER.info("Jedis Set smembers {} ", conn.smembers("name"));
        LOGGER.info("Jedis Set sismembers {} ", conn.sismember("name", "xiaoming"));
        LOGGER.info("Jedis Set srem {} ", conn.srem("name", "xiaoming"));
    }

    @Test
    public void testHash() throws Exception {
        Map<String, String> map = new HashMap();
        map.put("name", "jerry");
        map.put("age", "18");
        LOGGER.info("Jedis Hash hset {} ", conn.hkeys("hash key"));

    }

    @Test
    public void testZset() throws Exception {
//        LOGGER.info("Jedis Zset zadd {} ", conn.zadd("zset", 2.1, "xiaoming"));
        conn.set("user_name_1", "tom");
        conn.set("user_name_2", "jerry");
//        conn.debug();
    }

    @Test
    public void testSort() throws Exception {
//        conn.rpush("sort-input", "23", "15", "110", "7");
//        conn.hset("d-7", "field", "5");
//        conn.hset("d-15", "field", "1");
//        conn.hset("d-23", "field", "9");
//        conn.hset("d-110", "field", "3");
        SortingParams params = new SortingParams();
        params.by("d-*->field");
        params.get("d-*->field");
        LOGGER.info("Jedis {} ", conn.sort("sort-input", params));
    }

    @Test
    public void testTransation() throws Exception {
        RedisConnection con = redisConnectionFactory.getConnection();
        con.openPipeline();
        con.zAdd("bzset".getBytes(), 2, "xiaoming".getBytes());
        con.zAdd("bzset".getBytes(), 2, "xiaoli".getBytes());
        con.closePipeline();
    }

    @Test
    public void testAutoBox() throws Exception {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f  = 321;
        Long g = 3L;

        System.out.println(c == d);//true
        System.out.println(e == f);//false
        System.out.println(c == (a + b));//true
        System.out.println(c.equals(a + b));//true
        System.out.println(g == (a + b));//true
        System.out.println(g.equals((long) (a + b)));//false
    }
}
