package com.bit2016.network.thread;

public class MultiThreadEx03 {

	public static void main(String[] args) {
		Thread digitThread = new DigitThread();
		Thread alphaThread = new AlphabetThread();
		Thread thread3 = new Thread(new UppercaseAlphabetRunnablempl());

		digitThread.start();
		alphaThread.start();
		thread3.start();
	}

}
