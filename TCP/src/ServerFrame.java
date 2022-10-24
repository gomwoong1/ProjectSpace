import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

//		BufferedReader in = null;
//		BufferedWriter out = null;
//		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//		out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
public class ServerFrame {
	public static void main(String[] args) {
		ServerSocket server = null;
		Socket socket = null;
		try {
			server = new ServerSocket(9005);
			while(true) {
				System.out.println("[ 클라이언트 접속 대기중 ]");
				socket = server.accept();
				
				String inMsg = in.readLine();
				System.out.println(" >> 클라이언트 : " + inMsg);
				
				if(inMsg.equalsIgnoreCase("bye")) { //대소문자 구분 X
					System.out.println("클라이언트가 종료되었습니다.");
					break;
				}
				
				String outMsg = sc.nextLine();
				out.write(outMsg + "\n");
				out.flush();
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
				socket.close();
				server.close();
				sc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class ServerThread extends Thread{
		
	}
}
