package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;

// 서버 - 클라이언트가 접속할 때마다 새로운 스레드 생성함
class ServerThread extends Thread{
	static List<PrintWriter> list = Collections.synchronizedList(new ArrayList<PrintWriter>());
	Socket socket = null;
	BufferedReader in = null;
	PrintWriter out = null;
	
	// 클라이언트 소켓의 입출력 버퍼를 저장하는 생성자라고 보면 됨.
	public ServerThread(Socket socket) {
		this.socket = socket;
		
		try {
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			list.add(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		String name = "";
		try {
//			name = in.readLine();
//			System.out.println("log: [" + name + "]님 소켓 생성");
//			sendAll("[" + name + "]님이 입장하셨습니다.");
//			
			while(in != null) {
				for(int i = 0; i < 1; i++) {
					String btn_info = in.readLine();
					sendAll(btn_info);
				}
			}
		} catch (IOException e) {
			System.out.println("log: [" + name + "님 접속이 끊어졌습니다.]");
		} finally {
//			sendAll("[" + name + "]님이 퇴장하셨습니다.");
			list.remove(out);
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		System.out.println("log: [" + name + "연결종료]");
	}
	// 서버가 접속한 클라이언트에게 모두 메시지를 보낼 때 사용하는 메서드
	// 리스트인 이유는 클라이언트가 여러 개 일 수 있기 때문
	private void sendAll (String s) {
		for (PrintWriter out: list) {
			out.println(s);
			out.flush();
		}
	}
}


class SendThread extends Thread{
	Socket socket = null;
	String name;
	private gameFrame gf;
	
	Scanner sc = new Scanner(System.in);
	
	public SendThread(Socket socket, gameFrame gf) {
		this.socket = socket;
		this.gf = gf;
	}
	
	public SendThread(Socket socket, String name, gameFrame gf) {
		this.gf = gf;
		this.socket = socket;
		this.name = name;
	}
	
	@Override
	public void run() {
		try {
			PrintStream out = new PrintStream(socket.getOutputStream());
//			out.println(name);
//			out.flush();
			
			String outputMsg = gf.getData();
			out.println(outputMsg);
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
}