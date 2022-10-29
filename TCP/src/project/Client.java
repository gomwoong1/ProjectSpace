package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
	private int port;
	private String ip;
	private String btn_info;

	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
		this.start();
	}
	
	private void start() {
		Socket socket = null;
		BufferedReader in = null;
		
		try {
			socket = new Socket(ip, port);
			System.out.println("[서버와 연결되었습니다.]");
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(in != null) {
				for(int i = 0; i < 1; i++) {
					btn_info = in.readLine();
					System.out.println(btn_info);
//					gf.setSeat(btn_info);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
