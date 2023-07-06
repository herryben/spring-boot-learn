package com.learn.cache;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LRUCache {
    private final int capacity = 2;
    // 头是新的，尾是旧的
    LinkedList<Integer> list = new LinkedList<>();
    Map<Integer, Integer> map = new HashMap<>();

    public LRUCache() {
    }
//    public LRUCache(int capacity) {
//        this.capacity = capacity;
//    }

    public int get(int key) {
        if (capacity == 0) {
            return -1;
        }
        if (!map.containsKey(key)) {
            return -1;
        }
        int val = map.get(key);
        // 拿出来重新put一遍就达到了更新的目的
        put(key, val);
        return val;
    }

    public void put(int key, int value) {
        if (capacity == 0) {
            return;
        }
        if (map.containsKey(key)) {
            // 存在的话直接删除就行
            list.remove(Integer.valueOf(key));
        } else {
            // 不存在的话，判断容量是不是空
            if (capacity == map.size()) {
                // 删除队尾
                // 因为满了，所以map也需要删除
                map.remove(list.removeLast());
            }
        }
        // 不管哪种case，都必须进行这2种操作
        // 1.放入map
        // 2.放到队首
        map.put(key, value);
        list.addFirst(key);
    }

    @Test
    public void testLRUCache() {
        LRUCache lRUCache = new LRUCache();
        lRUCache.put(1, 1); // 缓存是 {1=1}
        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
        Assert.assertEquals(1, lRUCache.get(1));    // 返回 1
        lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        Assert.assertEquals(-1, lRUCache.get(2));    // 返回 -1 (未找到)
        lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        Assert.assertEquals(-1, lRUCache.get(1));    // 返回 -1 (未找到)
        Assert.assertEquals(3, lRUCache.get(3));    // 返回 3
        Assert.assertEquals(4, lRUCache.get(4));    // 返回 4
    }
}
