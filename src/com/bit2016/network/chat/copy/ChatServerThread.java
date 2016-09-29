package com.bit2016.network.chat.copy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class ChatServerThread extends Thread {
	private Socket socket;
	private String name;
	private String message;

	private List<PrintWriter> listPrintWriter;

	public ChatServerThread(Socket socket, List<PrintWriter> listPrintWriter) {
		this.socket = socket;
		this.listPrintWriter = listPrintWriter;
	}

	@Override
	public void run() {

		try {
			// 1 print Remote Socket Address
			InetSocketAddress remoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			ChatServer.consoleLog("Connected by Client[" + remoteSocketAddress.getAddress() + ":"
					+ remoteSocketAddress.getPort() + "]");

			// 2 create stream
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			// 3 processing...
			while (true) {
				String line = br.readLine();
				if (line == null) {
					// doQuit();
					break;
				}
				String[] tockens = line.split(":");
				if ("JOIN".equals(tockens[0])) {
					doJoin(tockens[1], pw);
					} else if ("MESSAGE".equals(tockens[0])) {
					doMessage(tockens[1], pw);
				} else if ("Quit".equals(tockens[0])) {
					doQuit(name, pw);
					break;
				}
			}

		} catch (UnsupportedEncodingException e) {
			ChatServer.consoleLog("error" + e);
		} catch (IOException e) {
			ChatServer.consoleLog("error" + e);
		} finally {
			try {
				if (socket.isClosed() == false && socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				ChatServer.consoleLog("error" + e);
			}
		}
	}

	private void doQuit(String name, PrintWriter printWriter) {
		// name
		this.name = name;

		// broadcasting..
		String message = name + "님이 퇴장하였습니다";
		broadcastMessage(message);
		deletePrintWriter(printWriter);
	}

	private void doMessage(String message, PrintWriter printWriter) {

		broadcastMessage(message);
	}

	private void doJoin(String name, PrintWriter printWriter) {
		// name
		this.name = name;

		// broadcasting..
		String message = name + "님이 입장하였습니다";
		broadcastMessage(message);

		// add PrintWriter
		addPrintWriter(printWriter);

		// ack
//		printWriter.println("Join: OK");
	}

	private void addPrintWriter(PrintWriter printWriter) {
		synchronized (listPrintWriter) {
			listPrintWriter.add(printWriter);
		}
	}

	private void deletePrintWriter(PrintWriter printWriter) {
		synchronized (listPrintWriter) {
			listPrintWriter.remove(printWriter);
		}
	}

	private void broadcastMessage(String message) {
		synchronized (listPrintWriter) {
			for (PrintWriter printWriter : listPrintWriter) {
				printWriter.println(message);
			}
		}
	}

}