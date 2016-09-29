package com.bit2016.network.chat.copy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Socket socket = null;
		String message = "";
		String name = "";
		try {
			socket = new Socket();

			String connectAddress;
//			 System.out.print("아이피를 입력하세요 :");
//			 connectAddress = sc.nextLine();

			socket.connect(new InetSocketAddress("192.168.1.19", 6635));
			System.out.println("접속완료");
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, "UTF-8"), true);

			if ("".equals(name)) {
				System.out.print("닉네임을 입력해 주세요>>");
				name = sc.nextLine();
				System.out.println("[" + name + "]님 환영합니다.");
				pw.println("JOIN:" + name);
			}
			while (true) {
				Thread thread = new ChatClientThread(socket);
				thread.start();
				if ("Quit".equals(message)) {
					System.out.println("");
					break;
				}
				message = sc.nextLine();
				pw.println(message);

			}

		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket.isClosed() == true && socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
