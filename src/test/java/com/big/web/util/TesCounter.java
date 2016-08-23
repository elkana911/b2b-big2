package com.big.web.util;

public class TesCounter {
	
	public static void main(String[] args) {
		Counter counter = new Counter();
		
		for (int i = 0; i < 5; i++){
			counter.increment();
			
			System.out.println(counter.read());
		}
	}
}
