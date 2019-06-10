package chap.basic.p2018;

import java.io.*;
import java.util.*;

/**
 * https://www.acmicpc.net/problem/2018
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Reader.init(System.in);
        int N = Reader.nextInt();
        int answer = 1;

        for (int i = 1; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int psum = (i * (j + 1)) + j * (j + 1) / 2;
                if (psum == N) {
                    answer++;
                } else if (psum > N) {
                    break;
                }
            }
        }

        System.out.println(answer);
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
