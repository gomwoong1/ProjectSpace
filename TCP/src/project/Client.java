package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
	
	public static void main(String[] args) {
		Client client = new Client();
		client.start();
	}

	private String btn_info;

	private void start() {
		Socket socket = null;
		BufferedReader in = null;
		
		try {
			socket = new Socket("localhost", 9005);
			System.out.println("[서버와 연결되었습니다.]");
			
			gameFrame gf = new gameFrame(socket);
			
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
