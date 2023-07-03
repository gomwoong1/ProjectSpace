package frame.main;
// 메모장 디자인 : 윤선호 + 전우진, 기능 : 윤선호 ,기타: 허유진(디자인 약간 수정)
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.plaf.DimensionUIResource;

import frame.db.DB;

public class MemoFrame extends JFrame implements ActionListener {

	private JPanel panelNorth;
	private JPanel panelCenter;
	private JPanel panelSouth;
	private JLabel lblDate;
	private JTextArea taMemo;
	private JButton btnSave;
	private JButton btnClear;
	private JButton btnExit;
	private JScrollPane sp;
	
	Calendar today = Calendar.getInstance();
	
	public static int calYear;
	public static int calMonth;
	public static int calDayOfMon;
	private JLabel todayLab;
	private Calendarmain cm;
	private String btn_num;
	private String str;
	private MainFrame mf;
	private String id;
	private Calendarmain calendarmain;
	
	private FileWriter fw;
	private DB db = new DB(null, this);
	private String memo_date = null;
	private String memo_data = null;
	private String today_date = null;
	private Color skyblue;
	private Color Blue;
	
	public String getToday_date() {
		return today_date;
	}

	public MemoFrame(String title, Calendarmain calendarmain, String id, String day) {
		this.calendarmain = calendarmain;
		this.id = id;
		
		DB db = new DB(null, this);
		
		setTitle(title);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(250, 150);
		setSize(300, 300);
		setLayout(new BorderLayout());
		setLocationRelativeTo(calendarmain);
		setResizable(false);
		System.out.println(btn_num);
		skyblue = new Color(189, 215, 238);
		Blue = new Color(70, 96, 147);
		
		setNorth();
		setCenter();
		setSouth();
		db.showMemo(id, day, this);
		setVisible(true);
	}

	private void setNorth() {
		//각 날짜별로 메모장에 날짜 출력 2022-05-26 윤선호
		today_date = calendarmain.getLabel_year().getText() + calendarmain.getLabel_month().getText() + calendarmain.getStr() +"일";	
		todayLab = new JLabel(calendarmain.getLabel_year().getText() + calendarmain.getLabel_month().getText() + calendarmain.getStr() +"일");
		todayLab.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 15));
		panelNorth = new JPanel();
		panelNorth.setLayout(null);
		panelNorth.setPreferredSize(new DimensionUIResource(50,35));
		panelNorth.setBackground(skyblue);
		todayLab.setBounds(65, 11, 200, 20);
		panelNorth.add(todayLab);
		add(panelNorth, BorderLayout.NORTH);
	}

	private void setCenter() {
		panelCenter = new JPanel();
		panelCenter.setBackground(skyblue);
		taMemo = new JTextArea(11, 20);
		taMemo.setLineWrap(true);
		taMemo.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 13));
		panelCenter.add(taMemo);
		
		sp = new JScrollPane(taMemo, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelCenter.add(sp);
		
		add(panelCenter, BorderLayout.CENTER);
	}

	private void setSouth() {
		panelSouth = new JPanel();
		panelSouth.setBackground(skyblue);
		
		btnSave = new JButton("Save");
		btnSave.setBackground(Blue);
		btnSave.setForeground(Color.white);
		btnSave.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 11));
		btnSave.addActionListener(this);
		
		btnClear = new JButton("Clear");
		btnClear.setBackground(Blue);
		btnClear.setForeground(Color.white);
		btnClear.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 11));
		btnClear.addActionListener(this);
		
		btnExit = new JButton("Exit");
		btnExit.setBackground(Blue);
		btnExit.setForeground(Color.white);
		btnExit.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 11));
		btnExit.addActionListener(this);
		
		panelSouth.add(btnSave);
		panelSouth.add(btnClear);
		panelSouth.add(btnExit);
		
		add(panelSouth, BorderLayout.SOUTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		//내용 삭제
		if(obj == btnClear) {
			taMemo.setText("");
			memo_date = calendarmain.getLabel_year().getText() + calendarmain.getLabel_month().getText() + calendarmain.getStr() +"일";
			db = new DB(null, this);
			db.DelMemo(memo_date, id);
		} 
		//종료버튼
		else if(obj == btnExit) {
			this.dispose();
		//메모한 내용 DB에 저장
		}else if(obj == btnSave) {
			
			//메모장 DB에 로그인한 사람의 ID, 선택한 날짜, 메모장 내용 전달
			memo_date = calendarmain.getLabel_year().getText() + calendarmain.getLabel_month().getText() + calendarmain.getStr() +"일";
			memo_data = taMemo.getText();
			db = new DB(null, this);
			db.InsertMemo(memo_date, memo_data, id);
		}
	}

	public String getMemo_date() {
		return memo_date;
	}

	public String getMemo_data() {
		return memo_data;
	}

	public JTextArea getTaMemo() {
		return taMemo;
	}
	
}
