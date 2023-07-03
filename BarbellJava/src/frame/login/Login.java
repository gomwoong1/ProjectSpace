package frame.login;
//로그인 디자인 : 전우진 + 김지웅 + 허유진, 기능 : 전우진, 기타 기능 : (47, 235줄) 김지웅, (331줄) 윤선호
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import frame.db.dbOpen;
import frame.main.MainFrame;

public class Login extends JFrame implements ActionListener, MouseListener, WindowListener, KeyListener{
	
	private JPanel panelLeft, panelRight, panelUp, panelMiddle, panelDown, panelWord;
	private JButton btnLogin, btnCall, btnJoin, btnSearch;
	private JTextField tfId, tfJoinId, tfJoinPw;
	private Font mainFont, subFont, southFont;
	private JLabel lblWelcome, lblGym;
	private JPasswordField tfpw;
	private PsCheckFrame ps;
	private AgreeFrame af;
	private MainFrame mf;
	private JoinFrame jf;
	
	private String result;
	private dbOpen dbconn;
	
	//06.01 김지웅 메인메서드 생성
	public static void main(String[] args) {
		Login startProgram = new Login();
	}
	
	public Login() {
		setTitle("로그인 화면");
		
		setSize(780, 480);
		setLayout(new BorderLayout());
		setResizable(false);
		addWindowListener(this);
		setLocationRelativeTo(null);
		
		mainFont = new Font("210 맨발의청춘 L", Font.BOLD, 22); // 메인 제목
	    subFont = new Font("210 맨발의청춘 L", Font.PLAIN, 13); 
	    southFont = new Font("210 맨발의청춘 L", Font.PLAIN, 14); // 오른쪽 하단
		
	    PanelLeft();
	    PanelRight();
		
		setVisible(true);
	}

	private void PanelLeft() {
		JPanel panelLeft= new JPanel();
		panelLeft.setLayout(null);
		panelLeft.setPreferredSize(new Dimension(300, 50));
		panelLeft.setBackground(Color.WHITE);
		
		// 로그인 화면 메인 이미지 출력
		ImageIcon loginImg = new ImageIcon("imges/loginimg.png");
		JLabel lblLogin = new JLabel(loginImg);
		lblLogin.setBounds(28, 100, 240, 240);
		panelLeft.add(lblLogin);
		
		add(panelLeft, BorderLayout.WEST);
	}

	private void PanelRight() {
		panelRight = new JPanel();
	
		panelRight.setLayout(new GridLayout(3,1));
		
		Login();
		
		add(panelRight, BorderLayout.CENTER);
		
	}

	private void Login() {
		
		Color skyblue = new Color(189, 215, 238);
		// 로그인 화면 오른쪽 패널 첫번째 부분
		panelUp = new JPanel();
		panelUp.setBackground(skyblue);
		panelUp.setLayout(new BorderLayout());
		
		// 로그인 화면 글씨
		panelWord = new JPanel();
		panelWord.setBackground(skyblue);
		lblWelcome = new JLabel("Welcome");
		lblGym = new JLabel(" to 건강해GYM!");
		      
		lblWelcome.setBackground(skyblue);
		lblWelcome.setForeground(Color.BLACK);
		lblGym.setBackground(skyblue);
		lblGym.setForeground(Color.WHITE);
		lblWelcome.setFont(mainFont);
		lblGym.setFont(mainFont);
		
		panelWord.add(lblWelcome);
		panelWord.add(lblGym);
		
		panelUp.add(panelWord,BorderLayout.SOUTH);
		panelRight.add(panelUp);
		
		// 로그인 화면 오른쪽 패널 두번째 부분
		panelMiddle = new JPanel();
		panelMiddle.setBackground(skyblue);
		panelMiddle.setLayout(null);
		panelMiddle.setPreferredSize(new Dimension(100, 50));
		
		// 로그인 화면 텍스트 필드(아이디) 출력
		tfId = new JTextField("아이디", 20);
		tfId.setFocusTraversalKeysEnabled(false);
		tfId.addKeyListener(this);
		tfId.setFont(subFont);
		tfId.setBounds(98, 24, 180, 22);
		tfId.setBorder(BorderFactory.createEmptyBorder()); // 텍스트 필드 테두리 없애기
		tfId.addActionListener(this);
		tfId.addMouseListener(this);
		panelMiddle.add(tfId);
		
		// 로그인 화면 텍스트 필드 배경이미지 출력
		ImageIcon imgId = new ImageIcon("imges/background_loginid.png");
		JLabel lblId = new JLabel(imgId);
		lblId.setBounds(88, 19, 200, 30);
		panelMiddle.add(lblId);
		
		// 로그인 화면 텍스트 필드(비밀번호) 출력
		tfpw = new JPasswordField("비밀번호", 20);
		tfpw.setFont(new Font("바탕체", 0, 13));
		tfpw.setBounds(98, 64, 180, 22);
		tfpw.setBorder(BorderFactory.createEmptyBorder());
		tfpw.addActionListener(this);
		tfpw.addMouseListener(this);
		panelMiddle.add(tfpw);
		
		// 로그인 화면 텍스트 필드 배경이미지 출력
		ImageIcon imgPw = new ImageIcon("imges/background_loginid.png");
		JLabel lblPw = new JLabel(imgPw);
		lblPw.setBounds(88, 59, 200, 30);
		panelMiddle.add(lblPw);
		
		// 로그인 화면 로그인 잠금 버튼 이미지 출력
		ImageIcon loginimg2 = new ImageIcon("imges/lock.png");
		btnLogin = new JButton(loginimg2);
		btnLogin.setBounds(300, 15, 80, 80);
		btnLogin.setBorderPainted(false);
		btnLogin.setContentAreaFilled(false);
		btnLogin.addActionListener(this);
		panelMiddle.add(btnLogin);
		
		// 로그인 화면 직원 호출 출력
		btnCall = new JButton("회원탈퇴");
		btnCall.setBounds(70, 105, 90, 30);
		Color a = new Color(130, 130, 130);
		btnCall.setForeground(a);
		btnCall.setFont(southFont);
		btnCall.setBorderPainted(false);
		btnCall.setContentAreaFilled(false);
		btnCall.addActionListener(this);
		panelMiddle.add(btnCall);
		
		// 로그인 화면 회원 가입 출력
		btnJoin = new JButton("회원 가입");
		btnJoin.setBounds(170, 105, 90, 30);
		btnJoin.setForeground(Color.BLACK);
		btnJoin.setFont(southFont);
		btnJoin.setBorderPainted(false);
		btnJoin.setContentAreaFilled(false);
		btnJoin.addActionListener(this);
		panelMiddle.add(btnJoin);
		
		// 로그인 화면 비밀번호 찾기 출력
		btnSearch = new JButton("비밀번호 찾기");
		btnSearch.setBounds(270, 105, 120, 30);
		btnSearch.setForeground(Color.BLACK);
		btnSearch.setFont(southFont);
		btnSearch.setBorderPainted(false);
		btnSearch.setContentAreaFilled(false);
		btnSearch.addActionListener(this);
		panelMiddle.add(btnSearch);
		
		panelRight.add(panelMiddle);
		
		// 로그인 화면 그리드 세번째 패널(공백)
		panelDown = new JPanel();
		panelDown.setBackground(skyblue);
		panelRight.add(panelDown);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
	
		if(obj == btnCall) {
			JOptionPane.showMessageDialog(this, "고객센터 전화번호는 032-777-7777 입니다.", "고객센터 안내", JOptionPane.INFORMATION_MESSAGE);
		} else if(obj == btnJoin) {
			af = new AgreeFrame("이용약관");
			af.setLocationRelativeTo(this);
			this.dispose();
		} else if(obj == btnSearch) {
			ps = new PsCheckFrame("비밀번호 찾기");
			ps.setLocationRelativeTo(this);
			this.dispose();
		} else if(obj == btnLogin || obj == tfId || obj == tfpw) {
			try {
				char[] tempPw = tfpw.getPassword();
				result = "";
				
				for(char ch	: tempPw) {
					Character.toString(ch);
					result += ""+ch+"";
				}
				
				// 김지웅 로그인 DB연결
				dbconn = new dbOpen();
				dbconn.loginSelect(this, tfId.getText(), result);

			} catch (Exception e2) {
				if(tfId.getText().equals("") || result.equals(""))
					JOptionPane.showMessageDialog(this, "아이디/비밀번호를 입력해주세요.", "로그인 오류", JOptionPane.ERROR_MESSAGE);
				else {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(this, "아이디/비밀번호를 확인해주세요.", "로그인 오류", JOptionPane.ERROR_MESSAGE);
				}
			}
			}
			
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		Object obj = e.getSource();	
		if(obj == tfId) {
			tfId.setText("");
		} else if(obj == tfpw) {
			tfpw.setText("");
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
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

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	//202945012 윤선호
	//아이디 입력 후 TAB키 입력하면 비밀번호 입력 칸으로 이동
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_TAB) {
			tfpw.requestFocus();
			tfpw.setText("");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}