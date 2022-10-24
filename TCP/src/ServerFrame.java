import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerFrame {
	
	public static void main(String[] args) {
		
		ServerFrame serverFrame = new ServerFrame();
		serverFrame.start();
	}
	
	public void start() {
		ServerSocket server = null;
		Socket socket = null;
		try {
			server = new ServerSocket(9005);
			while(true) {
				System.out.println("[클라이언트 접속 대기중]");
				socket = server.accept();
				
				ServerThread reciveThread = new ServerThread(socket);
				reciveThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("[서버 종료]");
				socket.close();
				server.close();
			} catch (IOException e) {
				System.out.println("[서버 통신 에러]");
				e.printStackTrace();
			}
		}
	}
}

class ServerThread extends Thread{
	static List<PrintWriter> list = Collections.synchronizedList(new ArrayList<PrintWriter>());
	
	Socket socket = null;
	BufferedReader in = null;
	PrintWriter out = null;
	
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
			name = in.readLine();
			System.out.println("[" + name + "님 새연결 생성");
			sendAll("[" + name + "]님이 들어오셨습니다.");
			
			while(in != null) {
				String inputMsg = in.readLine();
				if("quit".equals(inputMsg))	break;
				sendAll(name + ">>" + inputMsg);
			}
		} catch (IOException e) {
			System.out.println("[" + name + " 접속끊김]");
		} finally {
			sendAll("[" + name + "]님이 나가셨습니다.");
			list.remove(out);
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("[" + name + "연결종료]");
	}
	
	private void sendAll (String s) {
		for (PrintWriter out: list) {
			out.println(s);
			out.flush();
		}
	}
}

