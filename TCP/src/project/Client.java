package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
	private int port;
	private String ip;
	private String btn_info;
	private Socket socket = null;

	public Client(Socket socket) {
		this.socket = socket;
		this.start();
	}
	
	private void start() {
		BufferedReader in = null;
		
		gameFrame gf = new gameFrame(socket);
		
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(in != null) {
				for(int i = 0; i < 1; i++) {
					btn_info = in.readLine();
					System.out.println(btn_info);
					gf.setSeat(btn_info);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
