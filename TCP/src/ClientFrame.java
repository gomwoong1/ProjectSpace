
public class ClientFrame {

	public static void main(String[] args) {
		ClientFrame cliFrame = new ClientFrame();
		cliFrame.start();
	}
	
	public void start() {
		Socket socket = null;
		BufferedReader in = null;
		try {
			socket = new Socket("localhost", 9005);
			System.out.println("[서버와 연결되었습니다.]");
			
			String name = "user" + (int)(Math.random()*10);
			Thread sendThread = new SendTherad(socket, name);
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

class SendThread extends Thread {
	Socket socket = null;
	String name;
	
	Scanner sc = new Scanner(System.in);
	
	public SendThread(Socket socket, String name) {
		this.socket = socket;
		this.name = name;
	}
	
	@Override
	public void run() {
		try {
			PrintStream out = new PrintStream(socket.getOutputStream());
			out.println(name);
			out.flush();
			
			while(true) {
				String outputMsg = sc.nextLine();
				out.println(outputMsg);
				out.flush();
				if("quit".equals(outputMsg)) break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}