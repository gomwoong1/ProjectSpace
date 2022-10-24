package make;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatFrame extends JFrame implements ActionListener{
	
	private JPanel Center;
	private JTextArea ta;
	private JTextField tf;
	private JButton btn;
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;
	
	public JTextArea getTa() {
		return ta;
	}

	public ChatFrame(String title) {	
		
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //프레임 닫으면 종료되게 설정
		setSize(350, 500);
		setResizable(false);  //크기 조절 불가능하게 만듦
		setLocationRelativeTo(null);   //화면 중앙에 프레임이 뜨도록 설정

		setCenter(); //화면 중앙 설정 메소드
		
		setSouth(); //화면 하단 설정 메소드
		
		setVisible(true);
	
		setSocket();
	}

	private void setCenter() {
		Center = new JPanel();
		Center.setLayout(new BorderLayout());
		
		ta = new JTextArea(7, 20);
		ta.setLineWrap(true);
		ta.setEditable(false);
		JScrollPane sp = new JScrollPane(ta,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		Center.add(sp);
		
		add(Center, BorderLayout.CENTER);
	}
	
	private void setSouth() {
		JPanel bottom = new JPanel();
		
		tf = new JTextField(20);
		tf.addActionListener(this);
		bottom.add(tf);
		
		btn = new JButton("전송");
		btn.addActionListener(this);
		bottom.add(btn);
		
		Center.add(bottom, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		
		if(object == btn || object == tf) {
			String msg = tf.getText();
			
			try {
				out.write(msg + "\n");
				out.flush();
				
				ta.append(msg + "\n");
				tf.setText("");
				tf.requestFocus();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void setSocket() {
		try {
			socket = new Socket("localhost", 9005);
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			while(true) {
				String inMessage = in.readLine();
				ta.append(" >> 서버 : " + inMessage + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
