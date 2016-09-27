package com.bit2016.network.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

	public static void main(String[] args) {
		try {
			InetAddress inetAdress = InetAddress.getLocalHost();

			String hostName = inetAdress.getHostName();
			String hostAdress = inetAdress.getHostAddress();

			System.out.println(hostName + ":" + hostAdress);

			byte[] addresses = inetAdress.getAddress();

			for (byte address : addresses) {
				System.out.print(address & 0x000000ff);
				System.out.print(".");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
