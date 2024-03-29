package com.learn.tree;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/10/5 5:05 下午
 * 208. 实现 Trie (前缀树)
 * https://leetcode.cn/problems/implement-trie-prefix-tree/description/?envType=study-plan-v2&envId=top-100-liked
 * Trie（发音类似 "try"）或者说 前缀树 是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。这一数据结构有相当多的应用情景，例如自动补完和拼写检查。
 *
 * 请你实现 Trie 类：
 *
 * Trie() 初始化前缀树对象。
 * void insert(String word) 向前缀树中插入字符串 word 。
 * boolean search(String word) 如果字符串 word 在前缀树中，返回 true（即，在检索之前已经插入）；否则，返回 false 。
 * boolean startsWith(String prefix) 如果之前已经插入的字符串 word 的前缀之一为 prefix ，返回 true ；否则，返回 false 。
 *
 *
 * 示例：
 *
 * 输入
 * ["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
 * [[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
 * 输出
 * [null, null, true, false, true, null, true]
 *
 * 解释
 * Trie trie = new Trie();
 * trie.insert("apple");
 * trie.search("apple");   // 返回 True
 * trie.search("app");     // 返回 False
 * trie.startsWith("app"); // 返回 True
 * trie.insert("app");
 * trie.search("app");     // 返回 True
 */
public class Trie {
    TrieNode root = new TrieNode();
    public Trie() {
    }

    public void insert(String word) {
        TrieNode cur = root;
        for (char ch : word.toCharArray()) {
            if (cur.get(ch) == null) {
                cur.put(ch, new TrieNode());
            }
            cur = cur.get(ch);
        }
        cur.end = true;
    }

    public boolean search(String word) {
        TrieNode node = searchNode(word);
        return node != null && node.end;
    }

    public boolean startsWith(String prefix) {
        return searchNode(prefix) != null;
    }

    private TrieNode searchNode(String word) {
        TrieNode cur = root;
        for (char ch : word.toCharArray()) {
            if ((cur = cur.get(ch)) == null) {
                return null;
            }
        }
        return cur;
    }

    public static class TrieNode {
        boolean end;
        TrieNode[] next = new TrieNode[26];

        TrieNode get(char ch) {
            return next[ch - 'a'];
        }

        void put(char ch, TrieNode node) {
            next[ch - 'a'] = node;
        }
    }

    @Test
    public void testTrie() {
        Trie trie = new Trie();
        trie.insert("apple");
        Assert.assertEquals(true, trie.search("apple"));   // 返回 True
        trie.search("app");     // 返回 False
        Assert.assertEquals(true, trie.startsWith("app")); // 返回 True
        trie.insert("app");
        Assert.assertEquals(true, trie.search("app"));     // 返回 True
    }
}
