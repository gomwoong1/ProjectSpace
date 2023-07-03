package frame.main;
// 메인프레임 디자인 : 윤선호 + 전우진 + 김지웅, 기능 : 윤선호 + 전우진
import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import frame.board.Board;
import frame.board.Board2_PT;
import frame.db.dbOpen;
import frame.login.ChangeInfo;
import frame.login.Login;
import frame.login.MyRoutine;
import frame.login.Record;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MainFrame extends JFrame implements Runnable, ActionListener, WindowListener {

	private JPanel panelNorth, panelSouth;
	private JLabel lbl_icon, lbl_member, lbl_lastday, lblDay, lblTime, lbl_day, lbl_coment;
	private JButton btn_logout, btn_modify, btn_buy, btn_board1, btn_board2, btn_cal;
	private String id; //로그인 한 계정의 id, name을 받아옴.
	private static String name;

	private dbOpen db;
	private String enddate;
	private JButton btn_record;
	private JButton btn_read;
	private JPanel panelTime;
	private JPanel panelInfo;
	private JPanel panelPhrase;
	private Font mainFont = new Font("210 맨발의청춘 L", Font.BOLD, 16);
	private Color mainColor = new Color(189, 215, 238);
	private JButton btn_storeInfo;
	
	public MainFrame(String id) {
		this.id = id;
		
		setTitle("메인 화면");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocation(300,200);
		setSize(720, 650);
		setLayout(new BorderLayout());
		
		setInfo();
		setMenu();

		db = new dbOpen();
		db.pullInfoMain(id, lbl_member);

		db = new dbOpen();
		db.plusPeriodDate(id, lbl_day, enddate);
		
		this.addWindowListener(this);
		setLocationRelativeTo(null); //화면 가운데에 보여줌
		setResizable(false); // 화면 크기 조절 못하게 해줌
		setVisible(true);
		
	}
	public MainFrame(String id, String name, String enddate) {
		this.id = id;
		this.name = name;
		this.enddate = enddate;
		
		setTitle("메인 화면");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocation(300,200);
		setSize(720, 650);
		setLayout(new BorderLayout());
		
		setInfo();
		setMenu();

		db = new dbOpen();
		db.pullInfoMain(id, lbl_member);

		db = new dbOpen();
		db.plusPeriodDate(id, lbl_day, enddate);
		
		this.addWindowListener(this);
		setLocationRelativeTo(null); //화면 가운데에 보여줌
		setResizable(false); // 화면 크기 조절 못하게 해줌
		setVisible(true);
		
	}
	
	private void setInfo() {
		panelNorth = new JPanel();
		panelNorth.setPreferredSize(new Dimension(800, 290));
		panelNorth.setLayout(new BorderLayout());
		add(panelNorth, BorderLayout.NORTH);
		
		JPanel panelLeft = new JPanel();
		panelLeft.setBackground(new Color(189, 215, 238));
		panelLeft.setPreferredSize(new Dimension(280, 290));
		panelNorth.add(panelLeft, BorderLayout.WEST);

		// 프로필 이미지
		ImageIcon icn = new ImageIcon("imges/" + id + ".png");
		Image img = icn.getImage();
		Image changeImg = img.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
		ImageIcon changeimgicon = new ImageIcon(changeImg);
		
		lbl_icon = new JLabel(changeimgicon);
		lbl_icon.setPreferredSize(new Dimension(300, 300));
		panelLeft.add(lbl_icon);

		JPanel panelRight = new JPanel();
		panelRight.setBackground(mainColor);
		panelRight.setLayout(new BorderLayout());
		panelRight.setPreferredSize(new Dimension(520, 290));
		panelNorth.add(panelRight, BorderLayout.CENTER);
		
		panelTime = new JPanel();
		panelTime.setBackground(mainColor);
		panelTime.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelRight.add(panelTime, BorderLayout.NORTH);
		
		//윤선호 시간이 흐르는 시계를 사용하기 위한 Thread
		Thread thread = new Thread(this);
		thread.start(); // start를 하면 run()메소드에서 스레드 실행 시작
		
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy년 MM월 dd일");
		Calendar calendar = Calendar.getInstance();
		Date dateObj = calendar.getTime();
		String formattedDate = dtf.format(dateObj);
		
		//오늘 날짜를 보여준다.
		lblDay = new JLabel(formattedDate);
		Font font3 = new Font("210 맨발의청춘 L", Font.BOLD, 16);
		lblDay.setFont(font3);
		panelTime.add(lblDay);
		
		lblTime = new JLabel("12:35:05");
		lblTime.setFont(font3);
		panelTime.add(lblTime);
		
		//로그아웃 버튼
		ImageIcon logoutimg = new ImageIcon("imges/outbutton.png");
		btn_logout = new JButton(logoutimg);
		btn_logout.setPreferredSize(new Dimension(40, 40));
		btn_logout.setBorderPainted(false);
		btn_logout.setContentAreaFilled(false);
		btn_logout.addActionListener(this);
		panelTime.add(btn_logout);
		
		panelInfo = new JPanel();
		panelInfo.setBackground(mainColor);
		panelInfo.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelRight.add(panelInfo, BorderLayout.CENTER);

		// 회원님!
		lbl_member = new JLabel(name + " 회원님!");
		lbl_member.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 30));
		lbl_member.setBounds(20, 30, 300, 40);
		lbl_member.setHorizontalAlignment(JLabel.LEFT);
		lbl_member.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 265));
		panelInfo.add(lbl_member);

		// 님 만료일까지
		lbl_lastday = new JLabel("만료일까지 ");
		lbl_lastday.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 22));
		panelInfo.add(lbl_lastday);
		
		// 로그인한 회원의 만료일 수
		lbl_day = new JLabel();
		lbl_day.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 22));
		lbl_day.setBounds(130, 70, 60, 40);
		lbl_day.setOpaque(true);
		lbl_day.setForeground(Color.RED);
		lbl_day.setBackground(mainColor);
		panelInfo.add(lbl_day);

		lbl_coment = new JLabel("일 남았습니다.");
		lbl_coment.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 22));
		lbl_coment.setBounds(210, 70, 150, 40);
		panelInfo.add(lbl_coment);
		
		panelPhrase = new JPanel();
		panelPhrase.setBackground(mainColor);
		panelPhrase.setPreferredSize(new Dimension(520, 100));
		panelPhrase.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelRight.add(panelPhrase, BorderLayout.SOUTH);
		
		String random[] = {"오늘 들 무게를 내일로 미루지 마라.", "일어나 하체 해야지", "이거 못들면 죽는거야", 
							"복근 없으면 치팅데이는 없다", "바다갈 준비 안할거야?", "Light Weight", 
							"너도 3대 500 될 수 있어", "너가 먹은 치킨이 불쌍하지 않아?"};
		
		Random rd = new Random();
		JLabel ta = new JLabel(random[rd.nextInt(8)]);
		Font font_ta = new Font("210 맨발의청춘 L", Font.ROMAN_BASELINE, 28);
		ta.setFont(font_ta);
		ta.setForeground(Color.RED);
		ta.setBounds(20, 110, 400, 170);
		ta.setOpaque(true); // 라벨은 투명해서 불투명도를 true로 설정
		ta.setHorizontalAlignment(JLabel.CENTER);
		panelPhrase.add(ta);
	}
	
	private void setMenu() {
		panelSouth = new JPanel();
		panelSouth.setLayout(null);
		panelSouth.setBackground(mainColor);
		panelSouth.setPreferredSize(new DimensionUIResource(100, 100));
		
		//백그라운드 이미지 크기 및 위치 지정
		JLabel bg_label = new JLabel(new ImageIcon("imges/icon_tray.png"));
		bg_label.setBounds(30, 20, 650, 282);
		
		//센터정보 아이콘 및 버튼
		JLabel storeIcon = new JLabel(new ImageIcon("imges/storeinfoImg.png"));
		storeIcon.setBounds(75, 40, 80, 80);
		btn_storeInfo = new JButton();
		btn_storeInfo.setContentAreaFilled(false);
		btn_storeInfo.setBorderPainted(false);
		btn_storeInfo.setBounds(75, 40, 80, 80);
		btn_storeInfo.addActionListener(this);
		JLabel storeTxLbl = new JLabel("센터 정보");
		storeTxLbl.setBounds(85, 100, 80, 80);
		storeTxLbl.setFont(mainFont);
		
		//자유게시판 이동 이미지버튼
		JLabel comm_label = new JLabel(new ImageIcon("imges/comm_community.png"));
		comm_label.setBounds(225, 40, 80, 80);
		btn_board1 = new JButton();
		btn_board1.setContentAreaFilled(false);
		btn_board1.setBorderPainted(false);
		btn_board1.setBounds(225, 40, 80, 80);
		btn_board1.addActionListener(this);
		JLabel comuTxLbl = new JLabel("자유 게시판");
		comuTxLbl.setBounds(225, 100, 130, 80);
		comuTxLbl.setFont(mainFont);
		
		//PT 게시판 이동 이미지버튼
		JLabel pt_label = new JLabel(new ImageIcon("imges/health_community.png"));
		pt_label.setBounds(385, 40, 80, 80);
		btn_board2 = new JButton();
		btn_board2.setContentAreaFilled(false);
		btn_board2.setBorderPainted(false);
		btn_board2.setBounds(395, 40, 80, 80);
		btn_board2.addActionListener(this);
		JLabel ptComuTxLbl = new JLabel("PT 게시판");
		ptComuTxLbl.setBounds(390, 100, 130, 80);
		ptComuTxLbl.setFont(mainFont);
		
		//회원정보 수정 이미지 버튼
		JLabel edit_label = new JLabel(new ImageIcon("imges/edit_info.png"));
		edit_label.setBounds(550, 40, 80, 80);
		btn_modify = new JButton();
		btn_modify.setContentAreaFilled(false);
		btn_modify.setBorderPainted(false);
		btn_modify.setBounds(550, 40, 80, 80);
		btn_modify.addActionListener(this);
		JLabel memInfoTxLbl = new JLabel("회원정보 수정");
		memInfoTxLbl.setBounds(545, 100, 100, 80);
		memInfoTxLbl.setFont(mainFont);
		
		//이용권 구매 이미지버튼
		JLabel buy_label = new JLabel(new ImageIcon("imges/buy_ticket.png"));
		buy_label.setBounds(75, 180, 80, 80);
		btn_buy = new JButton();
		btn_buy.setContentAreaFilled(false);
		btn_buy.setBorderPainted(false);
		btn_buy.setBounds(75, 180, 80, 80);
		btn_buy.addActionListener(this);
		JLabel ticketTxLbl = new JLabel("이용권 구매");
		ticketTxLbl.setBounds(75, 240, 80, 80);
		ticketTxLbl.setFont(mainFont);
		
		//운동 기록 작성 프레임 바로가기 이미지 및 버튼
		JLabel writeRecord_lbl = new JLabel(new ImageIcon("imges/writeRecord.png"));
		writeRecord_lbl.setBounds(225, 180, 80, 80);
		btn_record = new JButton();
		btn_record.setContentAreaFilled(false);
		btn_record.setBorderPainted(false);
		btn_record.setBounds(225, 180, 80, 80);
		btn_record.addActionListener(this);
		JLabel wrRecordTxLbl = new JLabel("운동기록");
		wrRecordTxLbl.setBounds(235, 240, 80, 80);
		wrRecordTxLbl.setFont(mainFont);
		
		//운동 기록 보기 프레임 바로가기 이미지 및 버튼
		JLabel record_label = new JLabel(new ImageIcon("imges/readRecord.png"));
		record_label.setBounds(385, 180, 80, 80);
		btn_read = new JButton("");
		btn_read.setContentAreaFilled(false);
		btn_read.setBorderPainted(false);
		btn_read.setBounds(385, 180, 80, 80);
		btn_read.addActionListener(this);
		JLabel rdRecordTxLbl = new JLabel("운동 기록지");
		rdRecordTxLbl.setBounds(388, 240, 80, 80);
		rdRecordTxLbl.setFont(mainFont);
		
		//달력 화면 이동 이미지버튼
		JLabel cal_label = new JLabel(new ImageIcon("imges/calender.png"));
		cal_label.setBounds(550, 180, 80, 80);
		btn_cal = new JButton();
		btn_cal.setContentAreaFilled(false);
		btn_cal.setBorderPainted(false);
		btn_cal.setBounds(550, 180, 80, 80);
		btn_cal.addActionListener(this);
		JLabel calTxLbl = new JLabel("캘린더/메모");
		calTxLbl.setBounds(550, 240, 100, 80);
		calTxLbl.setFont(mainFont);
		
		panelSouth.add(storeIcon);
		panelSouth.add(edit_label);
		panelSouth.add(buy_label);
		panelSouth.add(comm_label);
		panelSouth.add(pt_label);
		panelSouth.add(writeRecord_lbl);
		panelSouth.add(record_label);
		panelSouth.add(cal_label);
		
		panelSouth.add(btn_storeInfo);
		panelSouth.add(btn_modify);
		panelSouth.add(btn_buy);
		panelSouth.add(btn_board1);
		panelSouth.add(btn_board2);
		panelSouth.add(btn_record);
		panelSouth.add(btn_read);
		panelSouth.add(btn_cal);
		
		panelSouth.add(storeTxLbl);
		panelSouth.add(comuTxLbl);
		panelSouth.add(ptComuTxLbl);
		panelSouth.add(memInfoTxLbl);
		panelSouth.add(ticketTxLbl);
		panelSouth.add(wrRecordTxLbl);
		panelSouth.add(rdRecordTxLbl);
		panelSouth.add(calTxLbl);

		panelSouth.add(bg_label);
		add(panelSouth, BorderLayout.CENTER);
	}

	@Override
	//쓰레드 실행 시작
	public void run() {
		//시간이 흐르게 해준다
		while(true) {
			String currentTime = LocalTime.now().toString();
			currentTime = currentTime.split("[.]")[0];
			//repaint();
			try {
				Thread.sleep(1000);
				lblTime.setText(currentTime);
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		//윤선호 이용권 구매 버튼을 누르면 이용권 구매 화면이 뜬다.
		if(obj == btn_buy) {
			Ticket tk = new Ticket(this, id);
			tk.setLocationRelativeTo(this);
			this.dispose(); //이용권 구매 프레임 호출시 메인프레임 종료
		}
		else if(obj == btn_storeInfo) {
			//김지웅 센터정보 프레임 생성 및 호출 버튼 생성
			StoreInfo si = new StoreInfo(id);
			si.setLocationRelativeTo(this);
			this.dispose();
		}
		
		else if(obj == btn_board1) {
			// 전우진 자유게시판 생성시 메인 꺼짐
			//윤선호 자유게시판과 메인프레임 연결
			Board bd = new Board(id, name);
			System.out.println(name);
			bd.setLocationRelativeTo(this);
			this.dispose();
		}
		else if(obj == btn_board2) {
			//윤선호 PT 게시판과 메인프레임 연결
			//허유진 이름 연결
			Board2_PT pt = new Board2_PT(this, id,name);
			pt.setLocationRelativeTo(this);
			this.dispose();
			
		}else if(obj == btn_cal) {
			//전우진 자유게시판 생성시 메인 꺼짐
			Calendarmain cm = new Calendarmain(this, id);
			cm.setLocationRelativeTo(this);
			this.dispose();
			
		}else if(obj == btn_modify) {
			//전우진 자유게시판 생성시 메인 꺼짐
			ChangeInfo ci = new ChangeInfo(this, id);
			ci.setLocationRelativeTo(this);
			this.dispose();
			
		}else if(obj == btn_logout) {
			Login login = new Login();
			login.setLocationRelativeTo(this);
			this.dispose();
		} else if(obj == btn_record) {
			Record rc = new Record("기록", id, name);
			rc.setLocationRelativeTo(this); // 프레임 정가운데 출력
			this.dispose();
		} else if(obj == btn_read) {
			MyRoutine mrt = new MyRoutine(id, name);
			mrt.setLocationRelativeTo(this); // 프레임 정가운데 출력
			this.dispose();
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	//윈도우 x 버튼을 눌렀을때 그 프레임만 닫기
	@Override
	public void windowClosing(WindowEvent e) {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}