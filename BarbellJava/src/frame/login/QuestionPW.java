// 비밀번호 확인폼
package frame.login;
//운동기구추가 김지웅, 기타 기능 : (197줄) 전우진
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import frame.board.Board2_PT;
import frame.board.BoardEdit;
import frame.board.BoardEdit_PT;
import frame.db.DBPT;
import frame.db.dbOpen;
import frame.main.Bill;
import frame.main.MainFrame;
import frame.main.Ticket;

public class QuestionPW extends JFrame implements ActionListener, WindowListener{
	
	private JButton submit;
	// 회원정보 수정 가능한지 확인차 id 디비에 있는 것으로 변경
	private String ID, enddate, namept, db_pw, bdID;
	private JPasswordField InputPW;
	private Ticket TK;
	private ChangeInfo CI;
	private Font mainFont, pwFont;
	private Color mainColor;
	private int ctrIndex, period;
	private Board2_PT PT;
	private ArrayList<String> alpt;
	private Board2_PT bdpt;
	private BoardEdit_PT bep;
	
	//이용권 구매 프레임의 주소와 구분자를 받아오는 생성자
	public QuestionPW(Ticket TK, int ctrIndex, String ID, int period) {
		this.TK = TK; //이용권 구매 프레임 주소 저장
		this.ID = ID;
		this.ctrIndex = ctrIndex; //구분자 값 저장
		this.period = period;
		
		// 전우진 윈도우 취소 버튼
		addWindowListener(this);
		setPanel(); //메인 패널 설정 생성자 호출
		setVisible(true);
	}
	
	//정보 수정 프레임의 주소와 구분자를 받아오는 생성자
	public QuestionPW(ChangeInfo CI, int ctrIndex, String ID) {
		this.CI = CI; //정보 수정 프레임 주소 저장
		this.ctrIndex = ctrIndex; //구분자 값 저장
		this.ID = ID;
		addWindowListener(this);
		setPanel(); //메인 패널 설정 생정자 호출
		setVisible(true);
	}
	
	//PT 게시판의 게시물 정보 출력 전 비밀번호 확인
	// 06/10 허유진 pt board 연결
	public QuestionPW(Board2_PT PT, int ctrIndex, String bdID,String namept, ArrayList<String> alpt, Board2_PT bdpt, String ID) {
		this.PT = PT; //정보 수정 프레임 주소 저장
		this.ctrIndex = ctrIndex; //구분자 값 저장
		this.bdID = bdID;
		this.namept = namept;
		this.alpt = alpt;
		this.bdpt = bdpt;
		this.ID = ID;
		
		addWindowListener(this);
		setPanel(); //메인 패널 설정 생정자 호출
		setVisible(true);
	}
	
	public QuestionPW(String ID, int period, String enddate) {
		this.ID = ID;
		this.period = period;
		this.enddate = enddate;
		
		addWindowListener(this);
		setPanel(); //메인 패널 설정 생성자 호출
		setVisible(true);
	}
	
	public void setPanel() {
		setTitle("확인");
		setBounds(100, 100, 230, 130);
		setResizable(false); //창 크기 조절 불가능하게 만들기		
		setLayout(new BorderLayout());
		
		mainFont = new Font("210 맨발의청춘 L", 0, 14); // 메인 및 서브 컬러 RGB값 담는 객체 생성
		pwFont = new Font("돋움", 0, 24);
		mainColor = new Color(189, 215, 238); //별도로 사용할 글꼴의 세부사항 설정
		
		JPanel mainPanel = new JPanel();
		add(mainPanel, BorderLayout.CENTER);
		mainPanel.setPreferredSize(new Dimension(230, 130)); //메인 패널 크기 230*130 지정
		mainPanel.setBackground(mainColor);
		mainPanel.setLayout(null);
		
		//안내말 텍스트라벨 붙이기
		JLabel Info = new JLabel("비밀번호를 입력해주세요.");
		Info.setBounds(32, 0, 150, 30);
		Info.setFont(mainFont);
		mainPanel.add(Info);

		//패스워드필드 배경이미지 붙이기
		JLabel pwFieldImg = new JLabel(new ImageIcon("imges/pwtextField.png"));
		pwFieldImg.setBounds(13, 27, 190, 30);
		mainPanel.add(pwFieldImg);

		//패스워드필드 붙이기
		InputPW = new JPasswordField(7);
		InputPW.setFont(pwFont);
		InputPW.setBorder(null);
		InputPW.setBounds(26, 30, 165, 25);
		mainPanel.add(InputPW);
		InputPW.addActionListener(this);
		
		//확인 버튼 추가하기
		submit = new JButton("확인");
		submit.setFont(mainFont);
		mainPanel.add(submit);
		submit.setBounds(26, 64, 165, 25);
		submit.setFocusPainted(false); // 포커스 표시 설정
		submit.setBorderPainted(false); // 버튼 테두리 표시 설정
		submit.setContentAreaFilled(false); // 버튼 영역 배경 표시 설정
		submit.addActionListener(this);
		
		//확인 버튼 배경 이미지 붙이기
		JLabel okBtn = new JLabel(new ImageIcon("imges/ok_btn.png"));
		okBtn.setBounds(82, 60, 50, 30);
		mainPanel.add(okBtn);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource(); // 어떤 컴포넌트로부터 이벤트가 발생했는지를 받아옴
		
		if(obj == submit || obj == InputPW) { //확인 버튼이나 패스워드필드로부터 이벤트가 발생한다면
			char[] temp = InputPW.getPassword();
			//패스워드필드의 값을 getPassword 메소드를 이용하여 임시 문자 배열에 저장.
			//메소드가 문자로 리턴되기 때문에 문자 배열을 사용
			String result = ""; //저장할 문자열 선언 및 초기화
			
			for(char ch	: temp) {
				Character.toString(ch);
				result += ""+ch+"";
			} //문자를 문자열로 변환
			
			if(ctrIndex == 3) { 
				String bdpw;
				dbOpen db = new dbOpen();
				bdpw = db.chBoardPW(bdID);
				
				if(bdpw.equals(result)) {
					bep = new BoardEdit_PT(PT.getAlpt(), ID, namept, bdpt);
					bep.setLocationRelativeTo(this);
					DBPT dbpt = new DBPT(bep);
					dbpt.DisplayCMT(alpt.get(0));
					this.dispose();
				}else {
					JOptionPane.showMessageDialog(this, "비밀번호가 틀립니다.",
							"글 비밀번호 오류", JOptionPane.WARNING_MESSAGE);
				}
			}
			else if(ctrIndex == 1 || ctrIndex == 2) {
				dbOpen db = new dbOpen();
				db_pw = db.checkPW(ID);
			
				if(result.equals(db_pw)){ //비밀번호와 입력받은 비밀번호가 일치한다면 
					if(ctrIndex == 1) { 
						dbOpen db2 = new dbOpen();
						db2.plusPeriod(ID, period, enddate);
						
						Bill bill = new Bill(TK, ID); 
						bill.setLocationRelativeTo(this);
						this.dispose();
						//생성자 매개변수로부터 전해받은 구분자를 통해 각기 다른 프로세스 실행
						//구분자가 1일 경우, 영수증 프레임을 출력해야 하기 때문에 영수증 프레임 생성자 호출
						//구분자가 2일 경우, 정보 수정만 마치면 되기에 다이얼로그 출력
					}else if(ctrIndex == 2){
						JOptionPane.showMessageDialog(this, "정보를 수정합니다.", "알림",
								JOptionPane.INFORMATION_MESSAGE);
						dbOpen db2 = new dbOpen();
						db2.chMemberInfo(ID, CI.getPwField(), CI.getPhoneField(), CI.getAddressField());
						// 전우진 5/31 확인 누르면 메인 프레임 생성
						MainFrame mf = new MainFrame(ID);
						mf.setLocationRelativeTo(CI);
						CI.dispose();
						this.dispose();
					}
				}else {
					JOptionPane.showMessageDialog(this, "비밀번호가 틀립니다.",
							"경고", JOptionPane.WARNING_MESSAGE);
				}
			InputPW.setText(""); //데이터 처리 완료 후 패스워드필드 비우기
			InputPW.requestFocus(); //계속 포커스 잡기
			}
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		//0609 김지웅 기능 재구현
		this.dispose();
		if(ctrIndex == 1) {
			JOptionPane.showMessageDialog(TK, "이용권 결제를 취소합니다.", "알림",
					JOptionPane.CANCEL_OPTION);
			TK.setEnabled(true);
			TK.setVisible(true);
			this.dispose();
		}
		else if(ctrIndex == 2) {
			JOptionPane.showMessageDialog(CI, "정보 수정을 취소합니다.", "알림",
					JOptionPane.CANCEL_OPTION);
			CI.setEnabled(true);
			CI.setVisible(true);
			this.dispose();
		}
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