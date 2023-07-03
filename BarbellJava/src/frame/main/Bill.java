package frame.main;
//영수증 김지웅
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Bill extends JFrame implements WindowListener{
	
	private Ticket TK;
	private Font mainFont, mainFont2, subFont;
	private String ID, storeName = "건강해GYM";
	private int i, total;
	private DecimalFormat priceFormat;
	
	public Bill(Ticket TK, String ID) {
		this.ID = ID;
		setTitle("Bill");
		this.TK = TK;
		total = TK.getTotal();
		setBounds(100, 100, 300, 310);
		setResizable(false); //창 크기 조절 불가능하게 만들기
		setLayout(new BorderLayout());
		priceFormat = new DecimalFormat("###,###"); //데이터 포맷 설정하는 객체 생성 및 양식 지정
		
		mainFont = new Font("210 맨발의청춘 L", Font.BOLD, 20); // 메인 및 서브 컬러 RGB값 담는 객체 생성
		mainFont2 = new Font("210 맨발의청춘 L", Font.BOLD, 18);
		subFont = new Font("210 맨발의청춘 L", 0, 15);
		
		setNorth(); //상단 패널 설정 생성자 호출
		setCenter(); //중앙 패널 설정 생성자 호출
		
		this.addWindowListener(this);
		
		setVisible(true);
	}

	private void setNorth() {
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.white);
		northPanel.setLayout(null);
		northPanel.setPreferredSize(new Dimension(300, 90)); //상단 패널 크기 300*90 지정
		add(northPanel, BorderLayout.NORTH);
		
		//점포 이름 출력 텍스트 라벨 붙이기
		JLabel title = new JLabel(storeName);
		title.setFont(mainFont);
		title.setBounds(10, 10, 180, 30);
		northPanel.add(title);
		
		//가격 텍스트 라벨 붙이기
		JLabel price = new JLabel(priceFormat.format(total));
		//티켓 구매 프레임으로부터 받아온 가격을 데이터 포맷을 설정하여 출력
		price.setFont(mainFont2);
		price.setBounds(10, 40, 120, 30);
		northPanel.add(price);
		
		//결제정보 텍스트라벨 붙이기
		JLabel info = new JLabel("결제정보");
		info.setFont(subFont);
		info.setBounds(220, 58, 60, 30);
		northPanel.add(info);
	}
	
	private void setCenter() {
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBackground(Color.WHITE);
		add(centerPanel, BorderLayout.CENTER);
		
		//좌측 패널 생성하고 붙이기
		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(100, 0));
		leftPanel.setLayout(null);
		leftPanel.setBackground(Color.WHITE);
		
		//우측 패널 생성하고 붙이기
		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(200, 0));
		rightPanel.setLayout(null);
		rightPanel.setBackground(Color.WHITE);
		
		centerPanel.add(leftPanel, BorderLayout.WEST);
		centerPanel.add(rightPanel, BorderLayout.EAST);
		
		//라벨에 출력될 텍스트를 문자열 배열로 생성
		String[] text = {"승인일시", "거래유형", "할부", "공급가액", 
				"부가세", "센터명"};
		JLabel[] textInfo = new JLabel[6]; // J라벨 객체 배열 생성
		int y = 0; //y 좌표값 변수 선언 및 초기화
		
		//J라벨을 붙이는 for문
		for(int i = 0; i < textInfo.length; i++, y+=30) {
			textInfo[i] = new JLabel(text[i]);
			textInfo[i].setFont(subFont);
			textInfo[i].setForeground(Color.gray);
			textInfo[i].setBounds(10, y, 105, 30);
			leftPanel.add(textInfo[i]);
		}
		
		//데이터 포맷을 설정하는 객체 생성
		SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date now = new Date(); //현재 시간 받아오기
		String nowTime = temp.format(now); // 미리 설정해둔 포맷을 기반으로 현재시간을 문지열에 저장

		int Tax = (int) ((TK.getTotal())*0.11); //부가세 계산 후 저장하는 변수
		
		//각 항목에 들어갈 값을 문자열 배열로 생성
		String[] valText = {nowTime, "승인", "일시불", priceFormat.format(total - Tax), 
				priceFormat.format(Tax), storeName};
		
		//J라벨 객체 배열 생성
		JLabel[] value = new JLabel[6];
		
		y = 0; //y값 좌표 초기화
		//for문을 이용하여 각각의 값을 붙이기
		for(i = 0; i < value.length; i++, y+=30) {
			value[i] = new JLabel(valText[i], JLabel.RIGHT);
			value[i].setFont(subFont);
			value[i].setBounds(0, y, 190, 30);
			rightPanel.add(value[i]);
		}
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		MainFrame mf = new MainFrame(ID);
		mf.setLocationRelativeTo(this);
		TK.dispose();
	}

	@Override
	public void windowClosed(WindowEvent e) {
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