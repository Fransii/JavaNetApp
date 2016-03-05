package com.franek;

import org.json.JSONObject;

import java.io.*;
import java.net.*;

public class Client {
	public static final int PORT = 50007;
	public static final String HOST = "127.0.0.1";

	public Socket sock;
	public BufferedReader klaw;
	public BufferedReader inp;
	public PrintWriter outp;


	public String nickName;

	//Clean chat v1.0.
	public void cleanChat()
	{
		for (int i = 0; i < 50; ++i) System.out.println();
	}

	// Send query of users list to server.
	public void getUsersList()
	{
		System.out.println("Here is list of Active users: ");
		JSONObject msg1 = new JSONObject();
		msg1.put("msg","!USERS");
		msg1.put("nickName",nickName);
		String msg1J = msg1.toString();
		this.outp.println(msg1J);
		this.outp.flush();
	}

	// Print help info.
	public void helpInfo()
	{
		System.out.println("List of commands:");
		System.out.println("1. !HELP - Show list of Commands.");
		System.out.println("2. !USERS - Show list of active users. ");
		System.out.println("3. !END - Close application");
		System.out.println();
	}

	//Connect to server.
	public void connect() throws IOException
	{
		sock = new Socket(HOST, PORT);
		System.out.println("Nawiazalem polaczenie: " + this.sock);
	}

	//Make buffered reader to read fom input and write to output.
	public void makeBufferedReader() throws IOException
	{
		this.inp = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
		this.klaw = new BufferedReader(new InputStreamReader(System.in));
		this.outp = new PrintWriter(this.sock.getOutputStream());
	}

	//Main communication between server and client.
	public void communication() throws IOException {
		String tekst = "";

		System.out.println("Hello!");
		System.out.println("For more information write: !HELP ");
		System.out.println();

		System.out.println("Whats your nickname ? :");
		this.nickName = this.klaw.readLine();
		cleanChat();
		System.out.println("Hello " + this.nickName + " !");

		getUsersList();

		do {
			JSONObject msg1 = new JSONObject();

			System.out.print("<Wysylamy (" + this.nickName + "):> ");
			String str = this.klaw.readLine();

			msg1.put("msg",str);
			msg1.put("nickName",nickName);

			String msg1J = msg1.toString();

			if(str.compareTo("!HELP") == 0)
			{
				helpInfo();
			}else if(str.compareTo("!USERS") == 0)
				{
					getUsersList();
				}
			else{
				tekst = str;
				this.outp.println(msg1J);
				this.outp.flush();
			}

			//Reading info from server.
			str = this.inp.readLine();
			if (str != "") {
				System.out.println("<Nadeszlo:> " + str + " od : " + this.sock.getInetAddress());
			}
			
		} while (tekst.compareTo("!END") != 0);
	}

	//Close connection to server, buffered reader&writer,  end application.
	public void closeConnection() throws IOException
	{
		System.out.println("Polaczenie zakończone !");
		System.out.println("Nacisnij ENTER ...");
		try{
			System.in.read();
		} catch (IOException e1) {}

		this.klaw.close();
		this.outp.close();
		this.sock.close();
	}

}