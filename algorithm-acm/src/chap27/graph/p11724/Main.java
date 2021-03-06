package chap27.graph.p11724;

import java.io.*;
import java.util.*;

/**
 * https://www.acmicpc.net/problem/11724
 *
 * @author zacconding
 * @Date 2018-01-28
 * @GitHub : https://github.com/zacscoding
 */
public class Main {

    public static int[][] adj;
    public static int m, n;
    public static boolean[] visited;

    public static void main(String[] args) throws IOException {
        Reader.init(System.in);
        m = Reader.nextInt();
        n = Reader.nextInt();
        adj = new int[m + 1][m + 1];
        visited = new boolean[m + 1];
        for (int i = 0; i < n; i++) {
            int a = Reader.nextInt();
            int b = Reader.nextInt();
            adj[a][b] = 1;
            adj[b][a] = 1;
        }
        int answer = 0;
        for (int i = 1; i <= m; i++) {
            if (!visited[i]) {
                answer++;
                dfs(i);
            }
        }
        System.out.println(answer);
        Reader.close();
    }

    public static void dfs(int start) {
        if (visited[start]) {
            return;
        }
        visited[start] = true;
        for (int i = 0; i < adj[start].length; i++) {
            if (adj[start][i] == 1 && !visited[i]) {
                dfs(i);
            }
        }
    }

    static class Reader {

        static BufferedReader reader;
        static StringTokenizer tokenizer;

        public static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
        }

        public static String nextLine() throws IOException {
            return reader.readLine();
        }

        public static String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public static void close() {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {

                }
            }
        }
    }
}
