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

	//List of all sockets
	List<ClientThread> socketList = new ArrayList<ClientThread>();

	//List of users nicknames
	public static List<String> usersList = new  ArrayList<String>();

	//List of tuples: nickname : socket
	public static Map userInfo = new HashMap();

	// Make ServerSocket
	public void makeServerSocket() throws IOException
	{
		this.serv = new ServerSocket(PORT);
	}

	// TODO return server socket
	public ServerSocket returnServerSocket()
	{
		return this.serv;
	}

	// Wait for client connection
	// After connected to server - make socket
	public void makeSocket() throws IOException
	{
		System.out.println("Nasluchuje: " + this.serv);

		while (true) {
			this.sock = serv.accept();

			ClientThread thread = new ClientThread(this.sock, socketList);
			thread.start();

			socketList.add(thread);
		}
	}


}