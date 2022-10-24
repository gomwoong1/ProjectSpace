package make;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JTextArea;

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
			ChatFrame cf = new ChatFrame("name");
			
			JTextArea ta = cf.getTa();
			ta.append("[서버와 연결되었습니다.]");
			
			Thread sendThread = new SendThread(socket, name);
			sendThread.start();
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(in != null) {
				String inputMsg = in.readLine();
				if(("[" + name + "]님이 나가셨습니다.").equals(inputMsg)) break;
				System.out.println("From:" + inputMsg);
			}
		} catch (IOException e) {
			System.out.println("[서버 접속끊김]");
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("[서버 연결종료]");
	}
	
}
