package frame.login;
//이용약관 전우진

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
public class AgreeFrame extends JFrame implements ActionListener, WindowListener {
	private JPanel panelNorth, panelCenter, panelUp, panelMiddle, panelDown, panelupup, panelmdmd, paneldwdw;
	private JLabel lblAgree, lblFirst, lblSecond, lblThird;
	private JTextArea taFirst, taSecond, taThird;
	private Font mainFont, subFont, lblInfoFont;
	private JButton btnCancel, btnAgree;
	private Color a;
	private JScrollPane sp;
	private JoinFrame joinFrame;
	
	public AgreeFrame(String title) {
		setTitle(title);
		setSize(350, 500);
		setResizable(false);
		setLayout(new BorderLayout());
		addWindowListener(this);
		
		mainFont = new Font("210 맨발의청춘 L", Font.BOLD, 20); // 메인 제목
	    subFont = new Font("210 맨발의청춘 L", Font.PLAIN, 13); 
	    lblInfoFont = new Font("210 맨발의청춘 L", Font.BOLD, 13); // 각 장의 제목
	    
		setNorth();
		setCenter();
		
		setVisible(true);
	}
	private void setNorth() {
		panelNorth = new JPanel();
		a = new Color(189, 215, 238);
		panelNorth.setBackground(a);
		panelNorth.setLayout(null);
		panelNorth.setPreferredSize(new Dimension(100, 50));
		
		// 이용약관 화면 취소 버튼
		btnCancel = new JButton("취소");
		btnCancel.setFont(subFont);
		btnCancel.setContentAreaFilled(false);
		btnCancel.setBorderPainted(false);
		btnCancel.setBounds(0, 10, 70, 30);
		btnCancel.addActionListener(this);
		
		// 이용약관 화면 라벨(이용약관) 출력
		lblAgree = new JLabel("이용약관");
		lblAgree.setBounds(130, 15, 100, 20);
		lblAgree.setFont(mainFont);  // 글씨 크기
		
		// 이용약관 화면 확인 버튼
		btnAgree = new JButton("확인");
		btnAgree.setFont(subFont);
		btnAgree.setContentAreaFilled(false);
		btnAgree.setBorderPainted(false);
		btnAgree.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnAgree.setBounds(280, 10, 40, 30);
		btnAgree.addActionListener(this);
				
		panelNorth.add(btnCancel);
		panelNorth.add(lblAgree);
		panelNorth.add(btnAgree);
		
		add(panelNorth, BorderLayout.NORTH);
	}
	private void setCenter() {
		panelCenter = new JPanel(new GridLayout(3, 1));
		panelCenter.setBackground(Color.WHITE);
		
		// 이용약관 화면 그리드 첫번째 패널
		panelUp = new JPanel();
		panelUp.setLayout(null);
		panelUp.setBackground(Color.WHITE);
		panelUp.setBounds(0, 0, 330, 150);
		
		// 이용약관 화면 제 1장 라벨 출력
		lblFirst = new JLabel("제 1장 서비스 이용약관");
		lblFirst.setBounds(10, 10, 130, 20);
		lblFirst.setFont(lblInfoFont);
		panelUp.add(lblFirst);
		
		// 이용약관 화면 첫번째 패널 안에 패널
		panelupup = new JPanel();
		panelupup.setBackground(Color.WHITE);
		panelupup.setBounds(4, 28, 330, 150);
		taFirst = new JTextArea(
				"제 1조 본 약관은 (주)건강해GYM를 통해 제공하는 가맹점의 매장 내 각종 서비스 제공 "
				+ "및 서비스 이용에 필요한 모바일 애플리케이션, 홈페이지를 운영함에 있어 회사와 회원간의 권리, "
				+ "의무 및 책임사항과 절차 등을 정하기 위해 만들어졌습니다." + "\n"
				+ "회원으로 가입하여 서비스의 이용을 희망하는 자는 약관의 내용을 숙지한 후 동의함을 표시하고, "
				+ "회사가 제시하는 소정의 회원가입 양식에 관련사항을 기재하여 회원가입을 신청하여야 합니다.", 5, 33);
		taFirst.setLineWrap(true);
		taFirst.setEditable(false);
		
		// 이용약관 화면 스크롤팬 출력
		sp = new JScrollPane(taFirst, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		panelupup.add(sp);
		panelUp.add(panelupup);
		
		// 이용약관 화면 그리드 두번째 패널
		panelMiddle = new JPanel();
		panelMiddle.setLayout(null);
		panelMiddle.setBackground(Color.WHITE);
		panelMiddle.setBounds(0, 0, 330, 150);
		
		// 이용약관 화면 제 2장 라벨 출력
		lblSecond = new JLabel("제 2장 개인정보 이용약관");
		lblSecond.setBounds(10, 10, 200, 20);
		lblSecond.setFont(lblInfoFont);
		panelMiddle.add(lblSecond);
				
		// 이용약관 화면 두번째 패널 안에 패널
		panelmdmd = new JPanel();
		panelmdmd.setBackground(Color.WHITE);
		panelmdmd.setBounds(4, 28, 330, 150);
		taSecond = new JTextArea(
				"가. 회사는 회원가입, 상담을 위해 아래와 같이 최소한의 개인정보를 수집하고 있습니다. " + "\n"
				+ "- 필수항목 : 이름, 전화번호, 주소 " +"\n"
				+ "나.서비스 이용과정에서 아래와 같은 정보들이 생성되어 수집될 수 있습니다. " +"\n"
				+ "-사용 기록 : 회원의 서비스 이용에 대한 통계 " +"\n"
				+ "다. 개인정보의 처리 및 보유기간은 다음과 같습니다. " + "\n"
				+ "- 기간 : 이용계약에 따른 회원자격이 유지되는 기간동안", 5, 33);
		taSecond.setLineWrap(true);
		taSecond.setEditable(false);
		
		// 이용약관 화면 스크롤팬 출력
		sp = new JScrollPane(taSecond, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelmdmd.add(sp);
		panelMiddle.add(panelmdmd);
		
		// 이용약관 화면 그리드 세번째 패널
		panelDown = new JPanel();
		panelDown.setLayout(null);
		panelDown.setBackground(Color.WHITE);
		panelDown.setBounds(0, 0, 330, 150);
		
		// 이용약관 화면 제 3장 라벨 출력
		lblThird = new JLabel("제 3장 전자금융거래 이용약관");
		lblThird.setBounds(10, 10, 200, 20);
		lblThird.setFont(lblInfoFont);
		panelDown.add(lblThird);
		
		// 이용약관 화면 세번째 패널 안에 패널
		paneldwdw = new JPanel();
		paneldwdw.setBackground(Color.WHITE);
		paneldwdw.setBounds(4, 28, 330, 150);
		taThird = new JTextArea(
				"1. 이 전자금융 이용 약관은 서비스의 제공자인 건강해GYM과 건강해GYM이 제공하는 전자금융서비스를 이용하고자 하는 "
				+ "이용자인 고객간에 전자금융서비스 이용에 관한 제반 사항을 정함을 목적으로 합니다."
				+ "이 약관에서 정하지 아니한 사항은 전자금융거래법 및 관계법령, 전자금융거래 기본약관, 기타 관련 약관 및 규약을 적용합니다.", 5, 33);
		taThird.setLineWrap(true);
		taThird.setEditable(false);
		
		// 이용약관 화면 스크롤팬 출력
		sp = new JScrollPane(taThird, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		paneldwdw.add(sp);
		panelDown.add(paneldwdw);
		
		panelCenter.add(panelUp);
		panelCenter.add(panelMiddle);
		panelCenter.add(panelDown);
		
		add(panelCenter, BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnCancel) {
			Login lg = new Login();
			lg.setLocationRelativeTo(this);
			this.dispose();
		} 
		if(obj == btnAgree) {
			if(JOptionPane.showConfirmDialog(this, 
					"다 읽으셨습니까?",
					"이용약관 동의 안내",
					JOptionPane.YES_NO_OPTION
					) == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(this, "동의 되었습니다.", "이용약관 동의 안내", JOptionPane.INFORMATION_MESSAGE);
				joinFrame = new JoinFrame("회원가입");
				joinFrame.setLocationRelativeTo(this);
				JCheckBox ch = joinFrame.getAgreeCheck();
				ch.setSelected(true);
				this.dispose();
			}	
		}
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
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