package test;

import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		//String str = "baekjoon";
		String s = sc.next();
		int size = s.length();
		PriorityQueue<String> que = new PriorityQueue<>(size);		
		for(int i=0;i<size;i++) {
			que.add(s.substring(i));
		}		
		while(!que.isEmpty()) {
			System.out.println(que.poll());
		}
	}
}


