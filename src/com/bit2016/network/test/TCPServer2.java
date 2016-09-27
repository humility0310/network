package com.bit2016.network.test;//버퍼드리더 개행단위 읽기

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer2 {
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
			try {
				// 4.IOStream 받아오기
				InputStream inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();

				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));// 인풋스트링에
																												// 연결
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
				while (true) {
					// 5.데이터 읽기
					String data = br.readLine();
					if (data == null) {
						System.out.println("[server] closed by client");
						break;
					}

					System.out.println("[server]received :" + data);
					// 6. 데이터 쓰기
					pw.println(data);
					// pw.print(data+"\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					// 5.자원정리(소켓닫기)
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

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
