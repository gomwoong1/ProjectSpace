package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private Object jb_info;

	public gameFrame() {
		setTitle("Ticket Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 프레임 닫으면 종료되게 설정
		setSize(800, 600);
		setResizable(false);  //크기 조절 불가능하게 만듦
		setLocationRelativeTo(null);   //화면 중앙에 프레임이 뜨도록 설정
		
		mainFont = new Font("ROKAF SLAB SERIF MEDIUM", Font.BOLD, 22);
		subFont = new Font("ROKAF SLAB SERIF MEDIUM", Font.BOLD, 18);
		subFont2 = new Font("ROKAF SLAB SERIF MEDIUM", Font.BOLD, 10);
		
		seatColor = new Color(211,211,211);
		seatSelColor = new Color(255,215,0);

		setTop();
		setMain();
		setVisible(true);
	}

	private void setTop() {
		JPanel top = new JPanel();
		top.setPreferredSize(new Dimension(60, 100));
		top.setBackground(Color.white);
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
		main.setBackground(Color.white);
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
		
		JButton[][] seat = new JButton[18][10];
		for(int i = 0; i < 18; i++) {
			for(int j = 0; j < 10; j++) {
				seat[i][j] = new JButton();
				seat[i][j].setBounds(x, y, 20, 20);
				seat[i][j].addActionListener(this);
				seat[i][j].setBorderPainted(false);
				seat[i][j].setBackground(seatColor);
				main.add(seat[i][j]);
				x += 22;
			}
			x = 20;
			y += 22;
		}
		
		x = 280;
		y = 25;
		JButton[][] seat2 = new JButton[18][10];
		for(int i = 0; i < 18; i++) {
			for(int j = 0; j < 10; j++) {
				seat[i][j] = new JButton();
				seat[i][j].setBounds(x, y, 20, 20);
				seat[i][j].addActionListener(this);
				seat[i][j].setBorderPainted(false);
				seat[i][j].setBackground(seatColor);
				main.add(seat[i][j]);
				x += 22;
			}
			x = 280;
			y += 22;
		}
		
		x = 550;
		y = 25;
		JButton[][] seat3 = new JButton[18][10];
		for(int i = 0; i < 18; i++) {
			for(int j = 0; j < 10; j++) {
				seat[i][j] = new JButton();
				seat[i][j].setBounds(x, y, 20, 20);
				seat[i][j].addActionListener(this);
				seat[i][j].setBorderPainted(false);
				seat[i][j].setBackground(seatColor);
				main.add(seat[i][j]);
				x += 22;
			}
			x = 550;
			y += 22;
		}
		
		x = 248;
		y = -65;
		JLabel[] number = new JLabel[seat.length];
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
		JLabel[] number2 = new JLabel[seat.length];
		for(int i = 0; i < seat.length; i++) {
			number2[i] = new JLabel(""+(i+1));
			number2[i].setBounds(x, y, 20, 200);
			number2[i].setHorizontalAlignment(JLabel.CENTER);
			number2[i].setForeground(Color.gray);
			number2[i].setFont(subFont2);
			main.add(number2[i]);
			y += 22.5;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		setData(obj);
		JButton jb = (JButton) obj;
		jb.setBackground(seatSelColor);
		jb.setEnabled(false);
	}
	
	public void setSeat(Object obj) {
		JButton jb = (JButton) obj;
		jb.setBackground(Color.red);
		jb.setEnabled(false);
	}
	
	public void setData(Object obj) {
		jb_info = obj;
	}
	
	public Object getData() {
		return jb_info;
	}
}
