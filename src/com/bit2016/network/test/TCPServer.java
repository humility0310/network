package com.bit2016.network.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

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
			try {
				// 4.IOStream 받아오기
				InputStream inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();
				while (true) {
					// 5.데이터 읽기
					byte[] buffer = new byte[256];
					int readByteCount = inputStream.read(buffer);
					if (readByteCount == -1) {
						// 정상종료 (remotesocket이 정상적으로 소켓을 닫았다)
						System.out.println("[server] closed by client");
						break;
					}
					String data = new String(buffer, 0, readByteCount, "UTF-8");
					System.out.println("[server] recevied: " + data);

					// 6. 데이터 쓰기
					//outputStream.write(data.getBytes("UTF-8"));
					
				}
			} 
			catch (SocketException ex) {
				System.out.println("[server] abnomal closed by client");
			} 
			catch (IOException e) {
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
