package com.bit2016.network.chat.copy;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	private static final int PORT = 6635;

	public static void main(String[] args) {
		//
		List<PrintWriter> listPrintWriter = new ArrayList<PrintWriter>();

		// create server socket
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket();

			// binding
			String localhost = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(localhost, PORT));
			consoleLog("binding[" + localhost + "]:" + PORT);

			while (true) {
				// wating for connection
				Socket socket = serverSocket.accept();

				Thread thread = new ChatServerThread(socket, listPrintWriter);
				thread.start();
			}

		} catch (IOException e) {
			consoleLog("error" + e);
		} finally {
			try {

				if (serverSocket.isClosed() == false && serverSocket != null) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static void consoleLog(String message) {
		System.out.println("[Chat Server]" + message);
	}
}
