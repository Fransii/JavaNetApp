package com.franek;

import java.io.IOException;

public class ServerTest {


	@SuppressWarnings("deprecation")
	public void run() {

	}

	public static void main(String args[]) throws IOException {
		try {

			Server server = new Server();
			while (true) {
				try {
					server.makeServerSocket();
					server.makeSocket();
				} catch (IOException e) {}
			}
		} catch (NullPointerException e) {
			System.out.println("DUPA");
		}
	}

}