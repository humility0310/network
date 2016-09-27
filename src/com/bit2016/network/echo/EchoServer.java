package com.bit2016.network.echo;

import java.io.BufferedReader;
//인풋스트림.임풋스트림리더,버퍼드인풋스트림,프린트라이터
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

public class EchoServer {
	private static final int PORT = 5000;

	public static void main(String[] args) {
		// 1.서버소켓생성
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket();

			InetAddress inetAddress = InetAddress.getLocalHost();
			String hostAddress = inetAddress.getHostAddress();

			serverSocket.bind(new InetSocketAddress(inetAddress, PORT));
			System.out.println("[server] bindeing " + hostAddress + ":" + PORT);

			Socket socket = serverSocket.accept();
			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			InetAddress inetRemoteHostAddress = inetSocketAddress.getAddress();
			String remoteHostAddress = inetRemoteHostAddress.getHostAddress();
			int remoteHostPort = inetSocketAddress.getPort();
			String remoteHostName = inetSocketAddress.getHostName();
			System.out.println("[server] connected by client[ " + remoteHostName + " 님이 접속하였습니다 =" + remoteHostAddress
					+ ":" + remoteHostPort + "]");

			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

			
			while (true) {
				String data = br.readLine();
				if (data == null) {
					System.out.println("[server] closed by client");
					is.close();
					os.close();
					pw.close();
					break;
				}
				
				System.out.println("[client]received :" + data);
				pw.println(data);

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
