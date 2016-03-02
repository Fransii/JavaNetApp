package com.franek;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Server {
	public static final int PORT = 50007;

	ServerSocket serv;
	Socket sock;
	List<ClientThread> socketList = new ArrayList<ClientThread>();

	public void makeServerSocket() throws IOException {
		// tworzenie gniazda serwerowego
		this.serv = new ServerSocket(PORT);
	}
	// TO DO
	public ServerSocket returnServerSocket() {
		return this.serv;
	}

	public void makeSocket() throws IOException {
		// oczekiwanie na polaczenie i tworzenie gniazda sieciowego
		System.out.println("Nasluchuje: " + this.serv);


		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						for (ClientThread clientThread : socketList) {
							synchronized (this) {
								String date = new Date().toString();
								clientThread.outp.println(date);
								clientThread.outp.flush();
								System.out.println("Sender: " + date);
							}
					}
					Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		while (true) {
			this.sock = serv.accept();
			synchronized (this) {
				ClientThread thread = new ClientThread(this.sock, socketList);
				thread.start();

				socketList.add(thread);
			}
		}
	}
}