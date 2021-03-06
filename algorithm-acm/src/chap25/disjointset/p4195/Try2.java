package chap25.disjointset.p4195;
/*

https://www.acmicpc.net/problem/4195

이름 : 인덱스 (0부터 순차)
-> idx로 접근
-> union시 집합의 크기를 기록

 */

import java.io.*;
import java.util.*;

public class Try2 {	
	public static int count;
	public static Map<String,Integer> network;
	public static int[] parent;
	
	public static void main(String[] args) throws Exception {		
		Reader.init(System.in);
		int cases = Reader.nextInt();
		
		while(cases-- > 0) {
			int count = Reader.nextInt();
			//초기화
			network = new HashMap<>(2*count,0.999999f);
			parent = new int[2*count];
			int idx = 0;
			
			for(int i=0; i<2*count; i++) {
				parent[i] = -1;
			}
			
			for(int i=0; i<count; i++) {				
				String f1 = Reader.next();
				String f2 = Reader.next();
				Integer p1 = network.get(f1);
				Integer p2 = network.get(f2);
				
				if(p1 == null) {
					p1 = idx++;
					network.put(f1, p1);					
				}
				
				if(p2 == null) {
					p2 = idx++;				
					network.put(f2, p2);
				}
				
				union(p1,p2);				
				System.out.println(-1*parent[find(p1)]);
			}
		}		
	}
	
	
	public static int find(int u) {
		if(parent[u] < 0)
			return u;	
		return parent[u] = find(parent[u]);
	}
	
	public static void union(int u,int v) {
		u = find(u);
		v = find(v);
		
		if(u == v)
			return;
		
		//집합 크기 합치기
		parent[u] += parent[v];
		parent[v] = u;		
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
			while(tokenizer ==null || !tokenizer.hasMoreTokens()) {
				tokenizer = new StringTokenizer(reader.readLine());
			}
			return tokenizer.nextToken();			
		}
		
		public static int nextInt() throws IOException {
			return Integer.parseInt( next() );
		}				
	}
}
