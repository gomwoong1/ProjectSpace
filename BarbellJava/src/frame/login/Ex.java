package frame.login;
// 나중에 지우기
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Ex extends JFrame implements ActionListener{

	private Color skyblue;
	private JButton btnRecord;
	private JButton btnCancel;
	private JButton btnRead;
	private String id;
	private String name;
	
	public Ex(String title, String id, String name) {
		this.id = id;
		this.name = name;
		
		setTitle(title);
		setLocation(250, 150);
		setSize(400, 250);
		setLayout(new BorderLayout());
		setResizable(false);
		
		skyblue = new Color(189, 215, 238);
		
	    setNorth();
	    setCenter();

		setVisible(true);
	}
	
	
	private void setNorth() {
		JPanel panelNorth = new JPanel(new BorderLayout());
		panelNorth.setLayout(null);
		panelNorth.setBackground(skyblue);
		panelNorth.setPreferredSize(new Dimension(370, 40));
		
		btnCancel = new JButton("취소");
		btnCancel.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 13));
		btnCancel.setBounds(0, 5, 60, 30);
		btnCancel.setContentAreaFilled(false);
		btnCancel.setBorderPainted(false);
		//btnCancel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnCancel.addActionListener(this);
		panelNorth.add(btnCancel, BorderLayout.WEST);
		
		// 비밀번호 확인 텍스트 필드 배경 이미지 출력
		ImageIcon imgEx = new ImageIcon("imges/ExBack.png");
		JLabel lblEx = new JLabel(imgEx);
		lblEx.setBounds(-5, 0, 400, 50);
		panelNorth.add(lblEx);
				
		add(panelNorth, BorderLayout.NORTH);
	}


	private void setCenter() {
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(null);
		panelCenter.setBackground(skyblue);
		
		btnRecord = new JButton("운동 기록");
		btnRecord.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 17));
		btnRecord.setContentAreaFilled(false);
		btnRecord.setBorderPainted(false);
		//btnRecord.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnRecord.setBounds(25, 20, 140, 130);
		btnRecord.setForeground(Color.WHITE);
		btnRecord.addActionListener(this);
		panelCenter.add(btnRecord);
		
		btnRead = new JButton("기록 열람");
		btnRead.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 17));
		btnRead.setContentAreaFilled(false);
		btnRead.setBorderPainted(false);
		//btnCancel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnRead.setBounds(220, 20, 140, 130);
		btnRead.setForeground(Color.WHITE);
		btnRead.addActionListener(this);
		panelCenter.add(btnRead);
		
		ImageIcon imgBtn1 = new ImageIcon("imges/btnEx.png");
		JLabel lblBtn1 = new JLabel(imgBtn1);
		lblBtn1.setBounds(25, 20, 140, 130);
		panelCenter.add(lblBtn1);
				
		ImageIcon imgBtn2 = new ImageIcon("imges/btnEx.png");
		JLabel lblBtn2 = new JLabel(imgBtn2);
		lblBtn2.setBounds(220, 20, 140, 130);
		panelCenter.add(lblBtn2);
				
		ImageIcon imgEx = new ImageIcon("imges/ExBack.png");
		JLabel lblEx = new JLabel(imgEx);
		lblEx.setBounds(-5, 0, 400, 180);
		panelCenter.add(lblEx);
				
		add(panelCenter, BorderLayout.CENTER);
	}
	public static void main(String[] args) {
		Ex e = new Ex("ex", null, null);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnRecord) {
		Record rc = new Record("기록", id, name);
		} else if(obj == btnCancel) {
			this.dispose();
		} else if(obj == btnRead) {
			MyRoutine mr = new MyRoutine(id, name);
		}
		
	}
}
