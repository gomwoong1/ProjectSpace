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

	private String inputMsg;

	private void start() {
		Socket socket = null;
		BufferedReader in = null;
		
		try {
			socket = new Socket("localhost", 9005);
			
			String name = "user" + (int)(Math.random()*10);
			System.out.println("[서버와 연결되었습니다.]");
			
			gameFrame gf = new gameFrame(socket);
			
			Thread sendThread = new SendThread(socket, name, gf);
//			sendThread.start();
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(in != null) {
				for(int i = 0; i < 1; i++) {
					inputMsg = in.readLine();
					gf.setSeat(inputMsg);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
