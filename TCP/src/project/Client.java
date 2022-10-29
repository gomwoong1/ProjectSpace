package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client extends Thread {
	private String btn_info;
	private Socket socket = null;
	private gameFrame gf;

	public Client(Socket socket, gameFrame gf) {
		this.socket = socket;
		this.gf = gf;
	}
	
	@Override
	public void start() {
		BufferedReader in = null;
		
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(in != null) {
				for(int i = 0; i < 1; i++) {
					btn_info = in.readLine();
					gf.setSeat(btn_info);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
