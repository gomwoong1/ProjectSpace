package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class resultFrame extends JFrame implements ActionListener {
	private Socket socket;
	private String username; 
	private String yourname; 
	private String data;
	private int my_score;
	private int your_score;
	private String recv_username; 
	private Font nameFont;
	private Font scoreFont;
	private Color winColor;
	private Color loseColor;
	private JButton btnExit;
	
	public resultFrame(Socket socket, String useranme, int score) {
		this.socket = socket;
		this.username = useranme;
		my_score = score;
		
//		long pid = ProcessHandle.current().pid();
//		Thread sendThread = new SendThread(socket, this, Long.toString(pid)); // 전송
//		sendThread.start();
		
//		try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//		
//		receiveThread receive = new receiveThread(socket, this);
//		receive.startThread();
		
//		data = receive.getString();
//		
//		if (data.equals(Long.toString(pid))) {
////			resultFrame2 rf2 = new resultFrame2(null, useranme, score);
//			System.out.println("너가 다른 프레임으로 가라");
//		}
//		
//		System.out.println("최종적으로 받은 상대점수: " + data);
//		your_score = Integer.parseInt(data);
		
//		Thread sendThread2 = new SendThread(socket, this, username); // 전송
//		sendThread.start();
//		
//		receiveThread receive2 = new receiveThread(socket, this);
//		receive.startThread();
//		System.out.println("상대이름: " + data);
//		yourname = data;
		
//		System.out.println("상대 이름: " + yourname + " ,상대 점수: " + your_score);
		
		setTitle("result");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 프레임 닫으면 종료되게 설정
		setSize(800, 600);
		setResizable(false);  //크기 조절 불가능하게 만듦
		
		nameFont = new Font("ROKAF SLAB SERIF MEDIUM", 0, 25);
		scoreFont = new Font("ROKAF SLAB SERIF MEDIUM", Font.BOLD, 120);
		winColor = new Color(95, 131, 211);
		loseColor = new Color(255, 75, 75);
		
		setMain();
		
		setVisible(true);
	}

	private void setMain() {
		JPanel mainFrame = new JPanel();
		mainFrame.setBackground(Color.white);
		mainFrame.setLayout(null);
		
		JLabel winner = new JLabel(username);
		winner.setBounds(115, 140, 150, 150);
		winner.setFont(nameFont);
		winner.setHorizontalAlignment(JLabel.CENTER);
		mainFrame.add(winner);
		
		JLabel loser = new JLabel(recv_username);
		loser.setBounds(520, 140, 150, 150);
		loser.setFont(nameFont);
		loser.setHorizontalAlignment(JLabel.CENTER);
		mainFrame.add(loser);
		
		JLabel win_score = new JLabel("50");
		win_score.setBounds(120, 300, 170, 170);
		win_score.setFont(scoreFont);
		win_score.setForeground(winColor);
		mainFrame.add(win_score);
		
		JLabel lose_score = new JLabel("25");
		lose_score.setBounds(520, 300, 170, 170);
		lose_score.setFont(scoreFont);
		lose_score.setForeground(loseColor);
		mainFrame.add(lose_score);
		
		btnExit = new JButton(new ImageIcon("image/exitBtn.png"));
		btnExit.setBounds(670, 460, 89, 83);
		btnExit.setBorderPainted(false);
		btnExit.setContentAreaFilled(false);
		btnExit.addActionListener(this);
		btnExit.setFocusable(false);
		mainFrame.add(btnExit);
		
		JLabel crown = new JLabel(new ImageIcon("image/crown.png"));
		crown.setBounds(120, 75, 135, 105);
		mainFrame.add(crown);
		
		JLabel reaper = new JLabel(new ImageIcon("image/reaper.png"));
		reaper.setBounds(525, 50, 127, 123);
		mainFrame.add(reaper);
		
		add(mainFrame);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == btnExit) {
			mainMenu mf = new mainMenu();
			mf.setLocationRelativeTo(this);
			this.dispose();
		}
	}
}
