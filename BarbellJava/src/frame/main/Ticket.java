package frame.main;
// 이용권 구매 김지웅, 기타 기능 : (48줄) 윤선호
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import frame.login.QuestionPW;

public class Ticket extends JFrame implements ActionListener, ItemListener, WindowListener {

	private Font mainFont, subFont, TicketFont, priceFont;
	private Color def, def2;
	private int total, period;
	private JLabel Select, PriceMonitor;
	private JButton payment, cancleBtn;
	private JComboBox<String> priceList;
	private DecimalFormat priceFormat;
	private MainFrame mf;
	private String ID;

	public int getTotal() {
		return total;
	}
//201945012 윤선호 MainFrame 연결
	public Ticket(MainFrame mf, String ID) {
		this.mf = mf;
		this.ID = ID;
		setTitle("이용권 구매하기");
		setBounds(95, 100, 350, 350);
		setResizable(false); //창 크기 조절 불가능하게 만들기
		setLayout(new BorderLayout());
		
		priceFormat = new DecimalFormat("###,###"); //데이터 포맷 설정하는 객체 생성 및 양식 지정
		def = new Color(189, 215, 238); // 메인 및 서브 컬러 RGB값 담는 객체 생성
		def2 = new Color(250, 80, 80);
		mainFont = new Font("210 맨발의청춘 L", Font.BOLD, 18); //별도로 사용할 글꼴의 세부사항 설정
		subFont = new Font("210 맨발의청춘 L", 0, 14);
		TicketFont = new Font("210 맨발의청춘 L", Font.BOLD, 21);
		priceFont = new Font("210 맨발의 청춘 L", Font.BOLD, 32);
		
		setNorth(); //상단 패널 설정하는 생성자 호출
		setCenter(); //중앙 패널 설정하는 생성자 호출
		
		setVisible(true);
	}

	private void setNorth() {
		JPanel NorthPanel = new JPanel();
		add(NorthPanel, BorderLayout.NORTH); //상단 패널 생성하고 상단 보더에 패널 붙임
		NorthPanel.setPreferredSize(new Dimension(300, 70)); //상단패널 크기 300*70으로 설정
		NorthPanel.setBackground(def); //상단 패널 배경색상 메인컬러로 설정
		NorthPanel.setLayout(null); //상단 패널 레이아웃 관리자를 null로 지정
		
		//이용권 구매 텍스트 출력
		JLabel titleText = new JLabel("이용권 구매"); // 텍스트를 담는 라벨 생성
		titleText.setFont(mainFont); // 텍스트에 메인 폰트 적용
		titleText.setBounds(122, 12, 150, 50); //위치 및 크기 지정
		NorthPanel.add(titleText); // 상단 패널에 텍스트라벨 붙이기
		
		//이용권 구매 백그라운드 이미지 붙이기
		JLabel title = new JLabel(new ImageIcon("imges/ticket_lbl.png")); //J라벨 이용하여 이미지 붙이기
		title.setBounds(85, 10, 160, 50);
		NorthPanel.add(title);
	}
	
	private void setCenter() {
		JPanel CenterPanel = new JPanel();
		CenterPanel.setPreferredSize(new Dimension(300, 280)); //중앙 패널 생성하고 300*200크기 지정
		add(CenterPanel, BorderLayout.CENTER); // 중앙 패널을 중앙에 붙임
		CenterPanel.setBackground(def); //중앙 패널 배경색상 지정
		CenterPanel.setLayout(null);

		String[] period = {"이용기간 선택", "1일 이용권", "7일 이용권", "1달 이용권", "3달 이용권", 
				"6달 이용권", "1년 이용권"}; //콤보박스에 사용할 문자열 배열
		
		priceList = new JComboBox<String>(period); //콤보박스 생성
		priceList.setFont(subFont);
		priceList.setBackground(Color.WHITE); //콤보박스 배경색상 흰색으로 지정
		priceList.setBounds(46, 191, 120, 28); //콤보박스 디자인을 위한 크기 및 이미지 지정
		priceList.setBorder(BorderFactory.createEmptyBorder(-2,-2,-2,-2)); 
		//콤보박스 테두리를 없애기 위해 테두리 조정
		CenterPanel.add(priceList); //콤보박스 중앙 패널에 붙이기
		priceList.addItemListener(this); //콤보박스에 아이템 리스너 추가
		
		//콤보박스 테두리 붙이기
		JLabel comboImg = new JLabel(new ImageIcon("imges/textField.png"));
		comboImg.setBounds(30, 190, 150, 30);
		CenterPanel.add(comboImg);
		
		//이용기간 텍스트 라벨 붙여넣기
		Select = new JLabel("이용권");
		Select.setFont(TicketFont);
		Select.setBounds(115, 35, 150, 30);
		CenterPanel.add(Select);

		//금액 텍스트 라벨 붙여넣기
		PriceMonitor = new JLabel("0");
		PriceMonitor.setFont(priceFont);
		PriceMonitor.setBounds(160, 80, 150, 30);
		CenterPanel.add(PriceMonitor);

		//원 텍스트 라벨 붙여넣기
		JLabel PriceMonitor2 = new JLabel("원");
		PriceMonitor2.setFont(TicketFont);
		PriceMonitor2.setBounds(190, 85, 150, 30);
		CenterPanel.add(PriceMonitor2);
		
		//이용권 이미지 붙여넣기
		JLabel ticketImg = new JLabel(new ImageIcon("imges/ticket.png"));
		ticketImg.setBounds(17, 0, 300, 150);
		CenterPanel.add(ticketImg);

		//지불버튼 이미지 출력
		JLabel payImg = new JLabel(new ImageIcon(("imges/paymentBtn.png")));
		payImg.setBounds(170, 170, 75, 75);
		CenterPanel.add(payImg);
		
		//취소버튼 이미지 출력
		JLabel canImg = new JLabel(new ImageIcon(("imges/cancleBtn.png")));
		canImg.setBounds(230, 170, 75, 75);
		CenterPanel.add(canImg);
		
		//배경에 스타일 이미지 붙여넣기
		JLabel style_lbl = new JLabel(new ImageIcon("imges/ticket_style1.png"));
		style_lbl.setBounds(5, 130, 320, 150);
		CenterPanel.add(style_lbl);
		
		//결제 버튼 추가하기
		payment = new JButton();
		payment.setBounds(180, 180, 55, 55);
		payment.setBorderPainted(false); //버튼 테두리 표시 설정
		payment.setContentAreaFilled(false); //버튼 영역 배경 표시 설정
		payment.setFocusPainted(false); //포커스 표시 설정
		CenterPanel.add(payment);
		payment.addActionListener(this);
		
		//취소버튼 추가하기
		cancleBtn = new JButton();
		cancleBtn.setBounds(240, 180, 55, 55);
		cancleBtn.setBorderPainted(false);
		cancleBtn.setContentAreaFilled(false);
		cancleBtn.setFocusPainted(false);
		CenterPanel.add(cancleBtn);
		cancleBtn.addActionListener(this);
		
		//윈도우 리스너 추가
		this.addWindowListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource(); //액션이 발생한 컴포넌트를 받아옴
		
		if(obj == cancleBtn) {
			JOptionPane.showMessageDialog(this, "이용권 결제를 취소합니다.", "알림",
					JOptionPane.CANCEL_OPTION); //취소 버튼이 눌렸다면 다이얼로그 출력
			MainFrame mf = new MainFrame(ID);
			mf.setLocationRelativeTo(this);
			this.dispose(); //해당 프레임만 종료시킴
		}
	
		else if(obj == payment) {
				if(total > 0) {
					QuestionPW password = new QuestionPW(this, 1, ID, period);
					//이용권을 선택하고 결제버튼 누르면 비밀번호 확인 프레임 호출
					password.setLocationRelativeTo(this);
					this.setEnabled(false);
					//모달창 대신 비활성화로 처리
				}else {
					JOptionPane.showMessageDialog(this, "이용권을 선택해주세요!",
						"경고", JOptionPane.WARNING_MESSAGE);
				}//이용권 선택하지 않았을 경우 안내 다이얼로그 출력
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object obj = (String)e.getItem(); //콤보박스로부터 어떤 아이템이 선택됐는지를 받아옴
	
		if(!obj.equals("이용기간 선택")) { //이용권 선택시 디자인 정렬을 위한 if문
			PriceMonitor.setHorizontalAlignment(PriceMonitor.RIGHT);
			PriceMonitor.setBounds(28, 80, 150, 30);
			Select.setHorizontalAlignment(Select.RIGHT); //텍스트라벨 오른쪽정렬
			Select.setBounds(38, 35, 150, 30);
			PriceMonitor.setForeground(def2); //텍스트에 색상 지정
		}
		if(obj.equals("이용기간 선택")) { //이용권 미선택시 디자인 정렬을 위한 if문
			total = 0;
			Select.setText("이용권");
			PriceMonitor.setBounds(28, 80, 150, 30);
			Select.setBounds(25, 35, 150, 30);
			PriceMonitor.setText("0");
			PriceMonitor.setForeground(Color.BLACK);
		} //콤보박스로부터 받아온 값에 따라 데이터를 처리하는 부분
		else if(obj.equals("1일 이용권")) {
			Select.setText("1일 이용권");
			total = 10000;
			period = 1;
		}
		else if(obj.equals("7일 이용권")) {
			Select.setText("7일 이용권");
			total = 50000;
			period = 7;
		}
		else if(obj.equals("1달 이용권")) {
			Select.setText("1달 이용권");
			total = 80000;
			period = 30;
		}
		else if(obj.equals("3달 이용권")) {
			Select.setText("3달 이용권");
			total = 200000;
			period = 90;
		}
		else if(obj.equals("6달 이용권")) {
			Select.setText("6달 이용권");
			total = 340000;
			period = 180;
		}
		else if(obj.equals("1년 이용권")) {
			Select.setText("1년 이용권");
			total = 400000;
			period = 365;
		}
		PriceMonitor.setText(priceFormat.format(total)); //설정한 데이터 포맷대로 라벨에 출력
	
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void windowClosing(WindowEvent e) {
		JOptionPane.showMessageDialog(this, "이용권 결제를 취소합니다.", "알림",
				JOptionPane.CANCEL_OPTION);
		MainFrame mf = new MainFrame(ID);
		mf.setLocationRelativeTo(this);
		this.dispose();		
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