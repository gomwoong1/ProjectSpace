package project;

import java.awt.Color;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class gameStart extends JFrame{
	private Font mainFont;
	private JLabel count;
	private int cd_val;
	private gameStart gs = this;
	static int state;

	public gameStart(int state) {
		this.state += state;
		mainFont = new Font("ROKAF SLAB SERIF MEDIUM", Font.BOLD, 70);
		
		setTitle("get Reday");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 프레임 닫으면 종료되게 설정
		setSize(800, 600);
		setResizable(false);  //크기 조절 불가능하게 만듦
	
		setMain();
		
		setVisible(true);
		
		ServerThread ser = new ServerThread();
		state = ser.getListSize();
		System.out.println("접속 클라이언트 수 : " + this.state);
		
//		while(state != 2) {
//			//리스트 두 개 될 때까지 잡아둠.
//			System.out.println("test");
//		}
		
		countDown();
	}

	private void setMain() {
		JPanel mainFrame = new JPanel();
		mainFrame.setBackground(Color.white);
		mainFrame.setLayout(null);
		
		count = new JLabel("대기중...");
		count.setFont(mainFont);
		count.setBackground(Color.blue);
		count.setBounds(275, 225, 400, 100);
		mainFrame.add(count);
	
		add(mainFrame);
	}
	
	private void countDown() {
		cd_val = 5;
		Timer timer = new Timer();
		
		TimerTask task = new TimerTask() {
			public void run() {
				if(cd_val > 0) {
					count.setBounds(365, 225, 400, 100);
					count.setText(Integer.toString(cd_val));
					cd_val--;
				}
				else {
					timer.cancel();
					gs.dispose();
					//여기에 게임 프레임 호출
				}
			}
		};
		
		timer.schedule(task, 1000, 1000);
	}
}
