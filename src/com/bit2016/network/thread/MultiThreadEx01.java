package com.bit2016.network.thread;

public class MultiThreadEx01 {

	public static void main(String[] args) {

		Thread digitThread = new DigitThread();
		Thread alphaThread = new AlphabetThread();
		digitThread.start();
		alphaThread.start();
		

	}
}
