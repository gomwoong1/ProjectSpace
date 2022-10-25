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

	private void start() {
		Socket socket = null;
		BufferedReader in = null;
		
		try {
			socket = new Socket("localhost", 9005);
			
			String name = "user" + (int)(Math.random()*10);
			System.out.println("[서버와 연결되었습니다.]");
			
			Thread sendThread = new SendThread(socket, name);
			sendThread.start();
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(in != null) {
				String inputMsg = in.readLine();
				if(("[" + name + "]님이 퇴장하셨습니다.").equals(inputMsg)) break;
				System.out.println("From:" + inputMsg);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
