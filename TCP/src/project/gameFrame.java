package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class gameFrame extends JFrame implements ActionListener{
	private Font mainFont;
	private Font subFont;
	private Font subFont2;
	private Color seatColor;
	private Color seatSelColor;
	private String jb_info;
	private Socket socket;
	private Font info_Font;
	private JButton[][] seat, seat2, seat3;
	
	public gameFrame(Socket socket) {
		this.socket = socket;
		setTitle("Ticket Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 프레임 닫으면 종료되게 설정
		setSize(800, 600);
		setResizable(false);  //크기 조절 불가능하게 만듦
		
		mainFont = new Font("ROKAF SLAB SERIF MEDIUM", Font.BOLD, 22); // 폰트 설정
		subFont = new Font("ROKAF SLAB SERIF MEDIUM", Font.BOLD, 18);
		subFont2 = new Font("ROKAF SLAB SERIF MEDIUM", Font.BOLD, 10);
		info_Font = new Font("ROKAF SLAB SERIF MEDIUM", 0, 0);
		
		seatColor = new Color(211,211,211); // 좌석 색 지정
		seatSelColor = new Color(255,215,0);

		setTop();
		setMain();
		
		setVisible(true);
		
		
		
		receiveThread receive = new receiveThread(socket, this);
		receive.startThread();
	}

	private void setTop() {
		JPanel top = new JPanel();
		top.setPreferredSize(new Dimension(60, 100));
		top.setBackground(Color.WHITE);
		top.setLayout(null);
		add(top, BorderLayout.NORTH);
		
		JLabel stageForm = new JLabel();
		stageForm.setOpaque(true); 
		stageForm.setBorder(new LineBorder(Color.BLACK, 25, true));
		stageForm.setBounds(215, 20, 350, 45);
		
		JLabel stage = new JLabel("S T A G E");
		stage.setBounds(340, 22, 350, 45);
		stage.setForeground(Color.WHITE);
		stage.setFont(mainFont);
		
		top.add(stage);
		top.add(stageForm);
	}

	private void setMain() {
		JPanel main = new JPanel();
		main.setLayout(null);
		main.setBackground(Color.WHITE);
		add(main, BorderLayout.CENTER);
		int x = 20, y = 25;
		
		JLabel A = new JLabel("A");
		JLabel B = new JLabel("B");
		JLabel C = new JLabel("C");
		
		A.setBounds(120, -15, 50, 50);
		A.setFont(subFont);
		A.setForeground(Color.gray);
		
		B.setBounds(385, -15, 50, 50);
		B.setFont(subFont);
		B.setForeground(Color.gray);

		C.setBounds(655, -15, 50, 50);
		C.setFont(subFont);
		C.setForeground(Color.gray);
		
		main.add(A);
		main.add(B);
		main.add(C);
		
		seat = new JButton[18][10];
		for(int i = 0; i < 18; i++) {
			for(int j = 0; j < 10; j++) {
				seat[i][j] = new JButton();
				seat[i][j].setBounds(x, y, 20, 20);
				seat[i][j].addActionListener(this);
				seat[i][j].setBorderPainted(false);
				seat[i][j].setText("0,"+ i+ "," + j);
				seat[i][j].setBackground(seatColor);
				seat[i][j].setFont(info_Font);
				main.add(seat[i][j]);
				x += 22;
			}
			x = 20;
			y += 22;
		}
		
		x = 280;
		y = 25;
		seat2 = new JButton[18][10];
		for(int i = 0; i < 18; i++) {
			for(int j = 0; j < 10; j++) {
				seat2[i][j] = new JButton();
				seat2[i][j].setBounds(x, y, 20, 20);
				seat2[i][j].addActionListener(this);
				seat2[i][j].setBorderPainted(false);
				seat2[i][j].setText("1,"+ i+ "," + j);
				seat2[i][j].setBackground(seatColor);
				seat2[i][j].setFont(info_Font);
				main.add(seat2[i][j]);
				x += 22;
			}
			x = 280;
			y += 22;
		}
		
		x = 550;
		y = 25;
		seat3 = new JButton[18][10];
		for(int i = 0; i < 18; i++) {
			for(int j = 0; j < 10; j++) {
				seat3[i][j] = new JButton();
				seat3[i][j].setBounds(x, y, 20, 20);
				seat3[i][j].addActionListener(this);
				seat3[i][j].setBorderPainted(false);
				seat3[i][j].setText("2,"+ i+ "," + j);
				seat3[i][j].setBackground(seatColor);
				seat3[i][j].setFont(info_Font);
				main.add(seat3[i][j]);
				x += 22;
			}
			x = 550;
			y += 22;
		}
		
		x = 248;
		y = -65;
		JLabel[] number = new JLabel[seat.length];
		
		for(int num = 0; num < 2; num++) {
			for(int i = 0; i < seat.length; i++) {
				number[i] = new JLabel(""+(i+1));
				number[i].setBounds(x, y, 20, 200);
				number[i].setHorizontalAlignment(JLabel.CENTER);
				number[i].setForeground(Color.gray);
				number[i].setFont(subFont2);
				main.add(number[i]);
				y += 22.5;
				}
			x = 514;
			y = -65;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource(); // 정보 받아서 구조체로 변경
		JButton jb = (JButton) obj; // 구조체를 jbutton으로 변환화여 jb 생성
		jb.setBackground(seatSelColor);  // 눌렀을때 색 변경
		jb.setEnabled(false); // 이후 변경 안되게 지정
		
		setData(jb.getText()); // 스트링으로 정보 만듬
		Thread sendThread = new SendThread(socket, this); // 전송
		sendThread.start();
	}
	
	public void setSeat(String btn_info) {   // ??? 이거는 모르겠음
		int seatName, row, col;
		seatName = Integer.parseInt(btn_info.substring(0, btn_info.indexOf(",")));
		row = Integer.parseInt(btn_info.substring(btn_info.indexOf(",")+1, btn_info.lastIndexOf(",")));
		col = Integer.parseInt(btn_info.substring( btn_info.lastIndexOf(",")+1));
		changeSeat(seatName, row, col);
	}
	
	public void setData(String btn_info) { 
		jb_info = btn_info;
	}
	
	public String getData() {
		return jb_info;
	}
	
	public void changeSeat(int name, int row, int col) {
		if(name == 0) {
			seat[row][col].setBackground(seatSelColor);
			seat[row][col].setEnabled(false);
		}if(name == 1) {
			seat2[row][col].setBackground(seatSelColor);
			seat2[row][col].setEnabled(false);
		}if(name == 2) {
			seat3[row][col].setBackground(seatSelColor);
			seat3[row][col].setEnabled(false);
		}
	}
	
	
}
