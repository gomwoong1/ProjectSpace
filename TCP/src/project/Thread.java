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
import java.util.Random;

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
			
			if(list.size() == 2) {
				ServerRandomThread random = new ServerRandomThread();
				random.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			while(in != null) {
				for(int i = 0; i < 1; i++) {
					String btn_info = in.readLine();
					sendAll(btn_info);
				}
			}
		} catch (IOException e) {
			System.out.println("[log: 접속이 끊어졌습니다.]");
		} finally {
			list.remove(out);
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 서버가 접속한 클라이언트에게 모두 메시지를 보낼 때 사용하는 메서드
	// 리스트인 이유는 클라이언트가 여러 개 일 수 있기 때문
	private void sendAll (String s) {
		for (PrintWriter out: list) {
			out.println(s);
			out.flush();
		}
	}

	public static List<PrintWriter> getList() {
		return list;
	}
}

class ServerRandomThread extends Thread{
	PrintWriter out = null;
	Socket socket;
	
	public ServerRandomThread() {
	}
	
	public void run() {
		int row, col;
		
		while(true) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					Random random = new Random();
					row = random.nextInt(18);
					col = random.nextInt(10);
					
					String str = (i + "," + row + "," + col);
					sendRandom(str);
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}
	
	private void sendRandom (String str) {
		for (PrintWriter out: ServerThread.list) {
			out.println(str);
			out.flush();
		}
	}
}

class SendThread extends Thread{
	Socket socket = null;
	private gameFrame gf;
	
	public SendThread(Socket socket, gameFrame gf) {
		this.socket = socket;
		this.gf = gf;
	}
	
	@Override
	public void run() {
		try {
			PrintStream out = new PrintStream(socket.getOutputStream());
			
			String btn_info = gf.getData();
			out.println(btn_info);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
}

class receiveThread extends Thread{
	private Socket socket;
	private String btn_info;
	private gameFrame gf;
	
	public receiveThread(Socket socekt, gameFrame gf) {
		this.socket = socekt;
		this.gf = gf;
		this.start();
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