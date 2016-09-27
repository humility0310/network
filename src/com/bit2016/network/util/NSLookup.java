package com.bit2016.network.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NSLookup {
	public static void main(String[] args) {
		try {
			String host = "www.naver.com";
			InetAddress[] inetAddresses = InetAddress.getAllByName(host);
			
			for(InetAddress inetAddress : inetAddresses){
				System.out.println(inetAddress.getHostAddress());
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
		
