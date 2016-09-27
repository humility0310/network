package com.bit2016.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
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

			socket.connect(new InetSocketAddress(connectAddress, 5000));
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			
			BufferedReader br =new BufferedReader(new InputStreamReader(is,"UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os), true);
			
			while(true){
				String data = br.readLine();
				if (data == null) {
					System.out.println("[server] closed by client");
					is.close();
					os.close();
					pw.close();
					break;
				}
				System.out.println("[server]received :" + data);
				pw.println(data);
			}
			
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
