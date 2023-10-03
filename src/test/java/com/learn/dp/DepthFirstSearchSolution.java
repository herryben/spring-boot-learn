package com.learn.dp;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/10/2 11:36 上午
 */
public class DepthFirstSearchSolution {
    /**
     * 200. 岛屿数量
     * https://leetcode.cn/problems/number-of-islands/
     * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
     * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
     * 此外，你可以假设该网格的四条边均被水包围。
     * 示例 1：
     *
     * 输入：grid = [
     *   ["1","1","1","1","0"],
     *   ["1","1","0","1","0"],
     *   ["1","1","0","0","0"],
     *   ["0","0","0","0","0"]
     * ]
     * 输出：1
     * 示例 2：
     *
     * 输入：grid = [
     *   ["1","1","0","0","0"],
     *   ["1","1","0","0","0"],
     *   ["0","0","1","0","0"],
     *   ["0","0","0","1","1"]
     * ]
     * 输出：3
     * @param grid
     * @return
     * 解题思路：
     * dfs
     *
     */
    public int numIslands(char[][] grid) {
        if(grid == null || grid.length == 0) {
            return 0;
        }
        int land = 0;
        int nr = grid.length;
        int nc = grid[0].length;
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                if (grid[i][j] == '1') {
                    land++;
                    dfs(grid, i, j);
                }
            }
        }
        return land;
    }

    public void dfs(char[][] grid, int x, int y) {
        int nr = grid.length;
        int nc = grid[0].length;
        if (x >= nr || y >= nc || y < 0 || x < 0 || grid[x][y] == '0') {
            return;
        }
        grid[x][y] = '0';
        // 因为是深度优先，所以4个方向都需要遍历
        dfs(grid, x + 1, y);
        dfs(grid, x - 1, y);
        dfs(grid, x, y + 1);
        dfs(grid, x, y - 1);
    }

    @Test
    public void testNumIslands() {
        Assert.assertEquals(1, numIslands(new char[][]{
                {'1','0','1','1','1'},
                {'1','0','1','0','1'},
                {'1','1','1','0','1'},
        }));
        Assert.assertEquals(1, numIslands(new char[][]{
                {'1','1','1'},
                {'0','1','0'},
                {'1','1','1'},
        }));
        Assert.assertEquals(1, numIslands(new char[][]{
                {'1','1','1','1','0'},
                {'1','1','0','1','0'},
                {'1','1','0','0','0'},
                {'0','0','0','0','0'}
        }));
        Assert.assertEquals(3, numIslands(new char[][]{
                {'1','1','0','0','0'},
                {'1','1','0','0','0'},
                {'0','0','1','0','0'},
                {'0','0','0','1','1'}
        }));
    }

    public int numIslandsBfs(char[][] grid) {
        if(grid == null || grid.length == 0) {
            return 0;
        }
        int land = 0;
        int nr = grid.length;
        int nc = grid[0].length;

        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                int idx = i * nc + j;
                if (grid[i][j] == '1') {
                    land++;
                    Queue<Integer> queue = new ArrayDeque<>();
                    queue.offer(idx);
                    grid[i][j] = '0';

                    while (!queue.isEmpty()) {
                        int id = queue.poll();
                        int x = id / nc;
                        int y = id % nc;
                        if ((x + 1) < nr && grid[x + 1][y] == '1') {
                            grid[x + 1][y] = '0';
                            queue.add((x + 1) * nc + y);
                        }
                        if ((x - 1) >= 0 && grid[x - 1][y] == '1') {
                            grid[x - 1][y] = '0';
                            queue.add((x - 1) * nc + y);
                        }
                        if (y + 1 < nc && grid[x][y + 1] == '1') {
                            grid[x][y + 1] = '0';
                            queue.add(x * nc + y + 1);
                        }
                        if (y - 1 >= 0 && grid[x][y - 1] == '1') {
                            grid[x][y - 1] = '0';
                            queue.add(x * nc + y - 1);
                        }
                    }
                }
            }
        }
        return land;
    }

    @Test
    public void testNumIslandsBfs() {
        Assert.assertEquals(1, numIslandsBfs(new char[][]{
                {'1','0','1','1','1'},
                {'1','0','1','0','1'},
                {'1','1','1','0','1'},
        }));
        Assert.assertEquals(1, numIslandsBfs(new char[][]{
                {'1','1','1'},
                {'0','1','0'},
                {'1','1','1'},
        }));
        Assert.assertEquals(1, numIslandsBfs(new char[][]{
                {'1','1','1','1','0'},
                {'1','1','0','1','0'},
                {'1','1','0','0','0'},
                {'0','0','0','0','0'}
        }));
        Assert.assertEquals(3, numIslandsBfs(new char[][]{
                {'1','1','0','0','0'},
                {'1','1','0','0','0'},
                {'0','0','1','0','0'},
                {'0','0','0','1','1'}
        }));
    }

    /**
     * 解题思路：
     * 1. 并查集
     *  1.1 路径合并法(压缩路径)
     * 参考链接
     * https://www.bilibili.com/video/BV1Bh4y1k7fZ/?spm_id_from=333.337.search-card.all.click&vd_source=fd99fac0c39056616435dffbc4a754d4
     * @param grid
     * @return
     */
    public int numIslandsUnion(char[][] grid) {
        UnionFindCompress unionFind = new UnionFindCompress(grid);
        int nr = grid.length;
        int nc = grid[0].length;
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                if (grid[i][j] == '1') {
                    int idx = i * nc + j;
                    grid[i][j] = '0';
                    // 是需要向右下搜索即可
                    if (i + 1 < nr && grid[i + 1][j] == '1') {
                        unionFind.union(idx, (i + 1) * nc + j);
                    }
                    if (j + 1 < nc && grid[i][j + 1] == '1') {
                        unionFind.union(idx, i * nc + j + 1);
                    }
                }
            }
        }
        return unionFind.getCount();
    }

    static class UnionFindCompress {
        int[] parent;
        int count = 0;
        public UnionFindCompress(char[][] grid) {
            int nr = grid.length;
            int nc = grid[0].length;
            parent = new int[nr * nc];
            for (int i = 0; i < nr; i++) {
                for (int j = 0; j < nc; j++) {
                    if (grid[i][j] == '1') {
                        parent[i * nc + j] = i * nc + j;
                        count++;
                    }
                }
            }
        }

        public int find(int x) {
            // 在find中进行路径压缩
            return x == parent[x] ? x : (parent[x] = find(parent[x]));
        }

        public void union(int x, int y) {
            int px = find(x);
            int py = find(y);
            if (px == py) {
                return;
            }
            parent[px] = py;
            count--;
        }

        public int getCount() {
            return count;
        }
    }

    @Test
    public void testNumIslandsUnion() {
        Assert.assertEquals(1, numIslandsUnion(new char[][]{
                {'1','1','1','1','0'},
                {'1','1','0','1','0'},
                {'1','1','0','0','0'},
                {'0','0','0','0','0'}
        }));
        Assert.assertEquals(3, numIslandsUnion(new char[][]{
                {'1','1','0','0','0'},
                {'1','1','0','0','0'},
                {'0','0','1','0','0'},
                {'0','0','0','1','1'}
        }));
    }

    /**
     * 解题思路：
     * 1. 并查集
     *  1.1 按秩压缩法
     * @param grid
     * @return
     */
    public int numIslandsUnionRank(char[][] grid) {
        UnionFindRank unionFind = new UnionFindRank(grid);
        int nr = grid.length;
        int nc = grid[0].length;
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                if (grid[i][j] == '1') {
                    int idx = i * nc + j;
                    grid[i][j] = '0';
                    if (i + 1 < nr && grid[i + 1][j] == '1') {
                        unionFind.union(idx, (i + 1) * nc + j);
                    }
                    if (j + 1 < nc && grid[i][j + 1] == '1') {
                        unionFind.union(idx, i * nc + j + 1);
                    }
                }
            }
        }
        return unionFind.getCount();
    }

    static class UnionFindRank {
        int[] parent;
        int[] rank;
        int count = 0;
        public UnionFindRank(char[][] grid) {
            int nr = grid.length;
            int nc = grid[0].length;
            parent = new int[nr * nc];
            rank = new int[nr * nc];
            for (int i = 0; i < nr; i++) {
                for (int j = 0; j < nc; j++) {
                    if (grid[i][j] == '1') {
                        parent[i * nc + j] = i * nc + j;
                        count++;
                    }
                }
            }
        }

        public int find(int x) {
            return x == parent[x] ? x : find(parent[x]);
        }

        public void union(int x, int y) {
            x = find(x);
            y = find(y);
            if (x == y) {
                return;
            }
            if (x > y) {
                int tmp = x;
                x = y;
                y = tmp;
            }
            parent[x] = y;
            if (rank[x] == rank[y]) {
                rank[y]++;
            }
            count--;
        }

        public int getCount() {
            return count;
        }
    }

    @Test
    public void testNumIslandsUnionRank() {
        Assert.assertEquals(1, numIslandsUnionRank(new char[][]{
                {'1','1','1','1','0'},
                {'1','1','0','1','0'},
                {'1','1','0','0','0'},
                {'0','0','0','0','0'}
        }));
        Assert.assertEquals(3, numIslandsUnionRank(new char[][]{
                {'1','1','0','0','0'},
                {'1','1','0','0','0'},
                {'0','0','1','0','0'},
                {'0','0','0','1','1'}
        }));
    }

    /**
     * 994. 腐烂的橘子
     * https://leetcode.cn/problems/rotting-oranges/description/?envType=study-plan-v2&envId=top-100-liked
     * 在给定的 m x n 网格 grid 中，每个单元格可以有以下三个值之一：
     *
     * 值 0 代表空单元格；
     * 值 1 代表新鲜橘子；
     * 值 2 代表腐烂的橘子。
     * 每分钟，腐烂的橘子 周围 4 个方向上相邻 的新鲜橘子都会腐烂。
     *
     * 返回 直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回 -1 。
     * 示例 1：
     * 输入：grid = [[2,1,1],[1,1,0],[0,1,1]]
     * 输出：4
     * 示例 2：
     *
     * 输入：grid = [[2,1,1],[0,1,1],[1,0,1]]
     * 输出：-1
     * 解释：左下角的橘子（第 2 行， 第 0 列）永远不会腐烂，因为腐烂只会发生在 4 个正向上。
     * 示例 3：
     *
     * 输入：grid = [[0,2]]
     * 输出：0
     * 解释：因为 0 分钟时已经没有新鲜橘子了，所以答案就是 0 。
     * 解题思路：
     * 1.多源bfs
     * @param grid
     * @return
     */
    public int orangesRotting(int[][] grid) {
        int step = 0;
        int flash = 0;
        int nr = grid.length;
        int nc = grid[0].length;
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(i * nc + j);
                }

                if (grid[i][j] == 1) {
                    flash++;
                }
            }
        }

        // 最后一个节点在队列中，但是已经不符合规则，所以会多加1
        while (flash > 0 && !queue.isEmpty()) {
            step++;
            int sz = queue.size();
            for (int i = 0; i < sz; i++) {
                int idx = queue.poll();
                int x = idx / nc;
                int y = idx % nc;
                int[] dx = new int[]{1, -1, 0, 0};
                int[] dy = new int[]{0, 0, 1, -1};
                for (int j = 0; j < 4; j++) {
                    int nx = x + dx[j];
                    int ny = y + dy[j];
                    if (0 <= nx && nx < nr && 0 <= ny && ny < nc && grid[nx][ny] == 1) {
                        flash--;
                        grid[nx][ny] = 2;
                        queue.offer(nx * nc + ny);
                    }
                }
            }
        }
        if (flash > 0) {
            return -1;
        }
        return step;
    }

    @Test
    public void testOrangesRotting() {
        Assert.assertEquals(2, orangesRotting(new int[][]{
                {1},
                {2},
                {1},
                {1},
        }));
        Assert.assertEquals(4, orangesRotting(new int[][]{
                {2, 1, 1},
                {1, 1, 0},
                {0, 1, 1}
        }));
        Assert.assertEquals(-1, orangesRotting(new int[][]{
                {2, 1, 1},
                {0, 1, 1},
                {1, 0, 1}
        }));
        Assert.assertEquals(0, orangesRotting(new int[][]{
                {0, 2},
        }));
    }
}
