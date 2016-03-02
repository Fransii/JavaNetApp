package com.franek;

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
	String tekst = "";
	public void connect() throws IOException {
		// nawiazanie polaczenia z serwerem
		sock = new Socket(HOST, PORT);
		System.out.println("Nawiazalem polaczenie: " + this.sock);
	}

	public void makeBufferedReader() throws IOException {
		// tworzenie strumieni danych pobieranych z klawiatury i dostarczanych
		// do socketu
		this.inp = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
		this.klaw = new BufferedReader(new InputStreamReader(System.in));
		this.outp = new PrintWriter(this.sock.getOutputStream());
	}

	public void communication() throws IOException {


		System.out.println("Whats your nickname ? :");
		this.nickName = this.klaw.readLine();

		do {
			// komunikacja - czytanie danych z klawiatury i przekazywanie ich do
			// strumienia

			Thread sender = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						String str = null;
						try {
							str = klaw.readLine();
							outp.println(str);
							outp.flush();
							System.out.println("<Wysyłamy:> " + str);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
			sender.start();


			Thread reader = new Thread(new Runnable() {
				@Override
				public void run() {
					String str = null;
					try {
						while ((str = inp.readLine()) != null) {
							System.out.println("<Nadeszlo:> " + str);
							tekst = str;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			});
			reader.start();
			break;
		} while (tekst.compareTo("END") != 0);
	}

	public void closeConnection() throws IOException {
		// zamykanie polaczenia
		System.out.println("Polaczenie zakończone !");
		System.out.println("Nacisnij ENTER ...");
		// try{ System.in.read(); } catch (IOException e1) {}
		this.klaw.close();
		this.outp.close();
		this.sock.close();
	}

}