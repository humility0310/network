package com.bit2016.network.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	private static final int PORT = 5000;

	public static void main(String[] args) {

		// 1.서버소켓생성
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket();

			// 2.바인딩 = 소켓의 소켓주소 | (IP+Port)을 바인딩(붙임)한다
			InetAddress inetAddress = InetAddress.getLocalHost();
			String hostAddress = inetAddress.getHostAddress();

			serverSocket.bind(new InetSocketAddress(hostAddress, PORT));
			System.out.println("[server] bindeing " + hostAddress + ":" + PORT);

			// 3. accept(클라이언트로부터 연결요청 대기)
			Socket socket = serverSocket.accept();// block
			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			InetAddress inetRemoteHostAddress = inetSocketAddress.getAddress();
			String remoteHostAddress = inetRemoteHostAddress.getHostAddress();
			// String remoteHostName = inetSocketAddress.getHostName();
			int remoteHostPort = inetSocketAddress.getPort();
			System.out.println("[server] connected by client[" + inetRemoteHostAddress + ":" + remoteHostPort + "]");

			// 4.데이터통신

			// 5.자원정리(소켓닫기)
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
