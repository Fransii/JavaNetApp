package com.franek;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Server {
	public static final int PORT = 50007;

	ServerSocket serv;
	Socket sock;
	List<ClientThread> socketList = new ArrayList<ClientThread>();

	public static List<String> usersList = new  ArrayList<String>();
	public static Map userInfo = new HashMap();


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

		while (true) {
			this.sock = serv.accept();

			ClientThread thread = new ClientThread(this.sock, socketList);
			thread.start();

			socketList.add(thread);
		}

		//System.out.println("Jest polaczenie: " + this.sock);
	}


}