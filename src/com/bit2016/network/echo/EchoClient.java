package com.bit2016.network.echo;

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
import java.net.SocketException;
import java.util.Scanner;

public class EchoClient {

	public static void main(String[] args) {
		Socket socket = null;
		Scanner sc = new Scanner(System.in);
		try {

			String connectAddress;
			System.out.print("아이피를 입력하세요 :");
			connectAddress = sc.nextLine();
			socket = new Socket();
			String data = null;

			socket.connect(new InetSocketAddress("192.168.0.69", 5000));
			System.out.println("접속완료");
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os), true);

			while (true) {
				System.out.print("(exit=종료)>>");
				data = sc.nextLine();
				pw.println(data);
				data = br.readLine();
				System.out.println("[server]received :" + data);
				if (data.equals("exit")) {
					System.out.println("접속을 종료하였습니다.");
					break;
				}
			}

		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		sc.close();
	}
}
