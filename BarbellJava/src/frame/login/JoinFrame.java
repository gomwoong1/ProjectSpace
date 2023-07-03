package frame.login;
// 회원가입 디자인 : 전우진 + 김지웅, 기능 : 전우진, 기타 기능 : (342줄) 김지웅
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.colorchooser.ColorChooserComponentFactory;
import javax.swing.filechooser.FileNameExtensionFilter;
import frame.db.dbOpen;
public class JoinFrame extends JFrame implements MouseListener, ActionListener, WindowListener, KeyListener{
	
	private JPanel panelCenter, panelSouth, panelUP, panelMiddle, panelDown;
	private JTextField tfId, tfHint, tfName, tfPhone, tfAddress;
	private JLabel lblLoginInfo, lblAdmin, lblJoin, lblImg;
	private String id, inputImg, phone, hint, address;
	private JPasswordField tfPassword, tfPsCheck;
	private JButton btnCancel, btnNext, btnOver;
	private String path, img, name;
	private Font mainFont, subFont;
	private JCheckBox agreeCheck;
	private ImageIcon profileImg;
	private Color skyblue;
	private AgreeFrame af;
	private Login login;
	private dbOpen db;
	
	public JoinFrame(String title) {
		setTitle(title);
		setResizable(false); //창 크기 조절 불가능하게 만들기
		setLocation(250, 150);
		setSize(340, 550);
		setLayout(new BorderLayout());
		addWindowListener(this);
		
		mainFont = new Font("210 맨발의청춘 L", Font.BOLD, 15); // 서브 정보
	    subFont = new Font("210 맨발의청춘 L", Font.PLAIN, 13); 
	    
		setCenter();
		setSouth();
		
		setVisible(true);
	}
	private void setCenter() {
		panelCenter = new JPanel(new GridLayout(3, 1));
		skyblue = new Color(189, 215, 238);
		
		// 회원가입 화면 첫번째 패널
		panelUP = new JPanel();
		panelUP.setLayout(null);
		panelUP.setBackground(skyblue);
		
		// 회원가입 화면 취소 버튼
		btnCancel = new JButton("취소");
		btnCancel.setFont(subFont);
		btnCancel.setContentAreaFilled(false);
		btnCancel.setBorderPainted(false);
		btnCancel.setBounds(10, 10, 35, 25);
		btnCancel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnCancel.addActionListener(this);
		panelUP.add(btnCancel);
		
		// 회원가입 화면 제목 출력
		lblJoin = new JLabel("회원가입");
		lblJoin.setBounds(120, 15, 100, 20);
		lblJoin.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 20)); 
		panelUP.add(lblJoin);
		
		// 회원가입 프로필 이미지 버튼
		img = "imges/Person.png";
		profileImg = new ImageIcon(img);
		lblImg = new JLabel(profileImg);
		lblImg.setBounds(110, 50, 100, 100);
		panelUP.add(lblImg);
		
		// 회원가입 화면 두번째 패널
		panelMiddle = new JPanel();
		panelMiddle.setLayout(null);
		panelMiddle.setBackground(skyblue);
		
		// 회원가입 화면 로그인 정보 라벨 출력
		lblLoginInfo = new JLabel("로그인 정보");
		lblLoginInfo.setFont(mainFont);
		lblLoginInfo.setBounds(15, 0, 150, 35);
		panelMiddle.add(lblLoginInfo);
		
		// 회원가입 화면 텍스트 필드(아이디) 출력
		tfId = new JTextField("아이디");
		tfId.setBounds(20, 38, 130, 29);
		tfId.setBorder(BorderFactory.createEmptyBorder());
		tfId.setFont(subFont);
		tfId.setFocusTraversalKeysEnabled(false);
		tfId.addActionListener(this);
		tfId.addMouseListener(this);
		tfId.addKeyListener(this);
		panelMiddle.add(tfId);
		
		// 회원가입 화면 텍스트 필드(비밀번호) 출력
		tfPassword = new JPasswordField("1111");
		tfPassword.setBounds(20, 78, 130, 29);
		tfPassword.setBorder(BorderFactory.createEmptyBorder());
		tfPassword.setFont(new Font("바탕체", 0, 14));
		tfPassword.setFocusTraversalKeysEnabled(false);
		tfPassword.addActionListener(this);
		tfPassword.addMouseListener(this);
		tfPassword.addKeyListener(this);
		panelMiddle.add(tfPassword);
		
		// 회원가입 화면 텍스트 필드(비밀번호 확인) 출력
		tfPsCheck = new JPasswordField("1111");
		tfPsCheck.setBounds(170, 78, 130, 29);
		tfPsCheck.setBorder(BorderFactory.createEmptyBorder());
		tfPsCheck.setFont(new Font("바탕체", 0, 14));
		tfPsCheck.setFocusTraversalKeysEnabled(false);
		tfPsCheck.addActionListener(this);
		tfPsCheck.addMouseListener(this);
		tfPsCheck.addKeyListener(this);
		panelMiddle.add(tfPsCheck);
		
		// 회원가입 화면 비밀번호 힌트 출력
		tfHint = new JTextField("비밀번호 재설정 힌트 답변(4-16자)");
		tfHint.setBounds(22, 119, 250, 30);
		tfHint.setBorder(BorderFactory.createEmptyBorder());
		tfHint.setFont(subFont);
		tfHint.setFocusTraversalKeysEnabled(false);
		tfHint.addActionListener(this);
		tfHint.addMouseListener(this);
		tfHint.addKeyListener(this);
		panelMiddle.add(tfHint);
		
		// 회원가입 화면 텍스트 필드 배경 이미지 출력
		ImageIcon imgId = new ImageIcon("imges/background_id.png");
		JLabel lblId = new JLabel(imgId);
		lblId.setBounds(13, 35, 148, 35);
		panelMiddle.add(lblId);
		
		ImageIcon imgPw = new ImageIcon("imges/background_id.png");
		JLabel lblPw = new JLabel(imgPw);
		lblPw.setBounds(13, 75, 148, 35);
		panelMiddle.add(lblPw);
		
		ImageIcon imgPwCh = new ImageIcon("imges/background_id.png");
		JLabel lblPwCh = new JLabel(imgPwCh);
		lblPwCh.setBounds(166, 75, 148, 35);
		panelMiddle.add(lblPwCh);
		
		ImageIcon imgHint = new ImageIcon("imges/background_address.png");
		JLabel lblHint = new JLabel(imgHint);
		lblHint.setBounds(13, 115, 300, 35);
		panelMiddle.add(lblHint);
		
		// 회원가입 화면 세번째 패널
		panelDown = new JPanel();
		panelDown.setLayout(null);
		panelDown.setBackground(Color.WHITE);
		
		// 회원가입 화면 개인정보 라벨 출력
		lblAdmin = new JLabel("개인 정보");
		lblAdmin.setFont(mainFont);
		lblAdmin.setBounds(15, 0, 150, 35);
		panelDown.add(lblAdmin);
		
		// 회원가입 화면 텍스트 필드(이름) 출력
		tfName = new JTextField("이름");
		tfName.setBounds(20, 38, 130, 29);
		tfName.setBorder(BorderFactory.createEmptyBorder());
		tfName.setFont(subFont);
		tfName.setFocusTraversalKeysEnabled(false);
		tfName.addActionListener(this);
		tfName.addMouseListener(this);
		tfName.addKeyListener(this);
		panelDown.add(tfName);
		
		// 회원가입 화면 텍스트 필드(이름) 출력
		tfPhone = new JTextField("전화번호");
		tfPhone.setBounds(20, 78, 130, 29);
		tfPhone.setBorder(BorderFactory.createEmptyBorder());
		tfPhone.setFont(subFont);
		tfPhone.setFocusTraversalKeysEnabled(false);
		tfPhone.addActionListener(this);
		tfPhone.addMouseListener(this);
		tfPhone.addKeyListener(this);
		panelDown.add(tfPhone);
		
		// 회원가입 화면 텍스트 필드(주소) 출력
		tfAddress = new JTextField("주소");
		tfAddress.setBounds(20, 119, 280, 29);
		tfAddress.setBorder(BorderFactory.createEmptyBorder());
		tfAddress.setFont(subFont);
		tfAddress.setFocusTraversalKeysEnabled(false);
		tfAddress.addActionListener(this);
		tfAddress.addMouseListener(this);
		tfAddress.addKeyListener(this);
		panelDown.add(tfAddress);
		
		panelCenter.add(panelUP);
		panelCenter.add(panelMiddle);
		panelCenter.add(panelDown);
		
		add(panelCenter);
		
		// 회원가입 화면 텍스트 필드 배경 이미지 출력
		ImageIcon imgName = new ImageIcon("imges/background_id.png");
		JLabel lblName = new JLabel(imgName);
		lblName.setBounds(13, 35, 150, 35);
		panelDown.add(lblName);
		
		ImageIcon imgPhone = new ImageIcon("imges/background_id.png");
		JLabel lblPhone = new JLabel(imgPhone);
		lblPhone.setBounds(13, 75, 150, 35);
		panelDown.add(lblPhone);
		
		ImageIcon imgAddress = new ImageIcon("imges/background_address.png");
		JLabel lblAddress = new JLabel(imgAddress);
		lblAddress.setBounds(15, 115, 300, 35);
		panelDown.add(lblAddress);
		
	}
	private void setSouth() {
		panelSouth = new JPanel();
		panelSouth.setLayout(null);
		panelSouth.setPreferredSize(new Dimension(340, 50));
		panelSouth.setBackground(Color.WHITE);
		
		// 회원가입 화면 체크박스 출력
		agreeCheck = new JCheckBox("약관에 동의합니다.(필수)");
		agreeCheck.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 13));
		agreeCheck.setBounds(5, 10, 170, 30);
		agreeCheck.setBackground(Color.WHITE);
		agreeCheck.addActionListener(this);
		panelSouth.add(agreeCheck);
		
		// 회원가입 화면 다음 버튼 이미지 출력
		ImageIcon buttonImg = new ImageIcon("imges/nextbutton.png");
		btnNext = new JButton(buttonImg);
		btnNext.setContentAreaFilled(false);
		btnNext.setBorderPainted(false);
		btnNext.setBounds(250, 0, 70, 50);
		btnNext.addActionListener(this);
		panelSouth.add(btnNext);
		
		add(panelSouth, BorderLayout.SOUTH);
	}
	
		@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnCancel) {
			Login login = new Login();
			login.setLocationRelativeTo(this);
			this.dispose();
		} else if(obj == agreeCheck) {
			if(agreeCheck.isSelected() == false) {
				JOptionPane.showMessageDialog(this, "약관을 읽어주세요.", "이용약관 동의 안내", JOptionPane.INFORMATION_MESSAGE);
				af = new AgreeFrame("이용약관");
				af.setLocationRelativeTo(this);
				this.dispose();
			}
		} else if(obj == btnNext || obj == tfId || obj == tfPassword || obj == tfPsCheck || obj == tfHint || obj == tfPhone || obj == tfAddress) {
			id = tfId.getText();
			char[] pw = tfPassword.getPassword();
			char[] pwch = tfPsCheck.getPassword();
			hint = tfHint.getText();
			name = tfName.getText();
			phone = tfPhone.getText();
			address = tfAddress.getText();
			
				if(id.equals("아이디") || pw.equals("비밀번호")
						|| pwch.equals("비밀번호 확인") || hint.equals("힌트 답변") 
						|| name.equals("이름") || phone.equals("전화번호")
						||address.equals("주소")
						
						|| id.equals("") || pw.equals("")  
						|| pwch.equals("") || hint.equals("") 
						|| name.equals("") || phone.equals("")
						|| address.equals("")) {
				 JOptionPane.showMessageDialog(this, "입력 정보를 다시 확인해주세요.");
				} else {
					char[] temp = pw;
					String resultpw = "";
					String resultpwch = "";
					
					for(char ch	: temp) {
						Character.toString(ch);
						resultpw += ""+ch+"";
					}
					
					temp = pwch;
					
					for(char ch	: temp) {
						Character.toString(ch);
						resultpwch += ""+ch+"";
					}
					if(resultpw.equals(resultpwch)) {
						if(resultpw.length() <= 18 && resultpw.length() >= 8 && resultpwch.length() <= 18 && resultpwch.length() >= 8) {
							if(phone.length() >= 10 && phone.length() <= 11) {
								if(hint.length() >= 4 && hint.length() <= 16) {
									if(name.length() <= 5) {
										try {
											File file = new File(img); // 현재 이미지 파일
											file.getName(); // 파일 이름만 추출
											File srcfile = new File("imges/" + tfId.getText() + ".png"); // 현재 아이디로 이미지 이름 변경
											
											Files.copy(file.toPath(), srcfile.toPath(), StandardCopyOption.REPLACE_EXISTING); // 이미지 복사
											
										} catch (IOException e1) {
											e1.printStackTrace();
										}
										// 김지웅 회원가입 DB 연결
										db = new dbOpen();
										db.infoInsert(tfId.getText(), tfName.getText(), tfPhone.getText(), tfAddress.getText(), tfPassword.getText(), tfHint.getText());
										JOptionPane.showMessageDialog(this, "회원가입이 완료 되었습니다");
										Login lg = new Login();
										lg.setLocationRelativeTo(this);
										this.dispose();
									} else {
										JOptionPane.showMessageDialog(this, "이름은 5자이내로 작성해주세요.");
									}
							
								} else {
									JOptionPane.showMessageDialog(this, "힌트는 4자리 이상 16자리 이하로 작성해주세요.");
								}
							} else {
								JOptionPane.showMessageDialog(this, "전화번호는 10자리 혹은 11자리입니다.");
							}
					} else {
						JOptionPane.showMessageDialog(this, "비밀번호는 8자리 이상 18자리 이하로 작성해주세요.");
					}
				} else {
					JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다");
				}
			}
			
		} 
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
		@Override
	public void mousePressed(MouseEvent e) {
		
		Object obj = e.getSource();
			if(obj == tfId) {
				IdCheckFrame idCheckFrame = new IdCheckFrame("아이디 중복 확인", this);
				idCheckFrame.setLocationRelativeTo(this); // 프레임 정가운데 출력
				this.dispose();
			} else if(obj == tfPassword) {
				tfPassword.setText("");
			} else if(obj == tfPsCheck) {
				tfPsCheck.setText("");
			} else if(obj == tfHint) {
				tfHint.setText("");
			} else if(obj == tfName) {
				tfName.setText("");
			} else if(obj == tfPhone) {
				tfPhone.setText("");
			} else if(obj == tfAddress) {
				tfAddress.setText("");
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
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_TAB) {
			Object obj = e.getSource();
			if(obj == tfId) {
				tfPassword.requestFocus();
				tfPassword.setText("");
			} else if(obj == tfPassword) {
				tfPsCheck.requestFocus();
				tfPsCheck.setText("");
			} else if(obj == tfPsCheck) {
				tfHint.requestFocus();
				tfHint.setText("");
			} else if(obj == tfHint) {
				tfName.requestFocus();
				tfName.setText("");
			} else if(obj == tfName) {
				tfPhone.requestFocus();
				tfPhone.setText("");
			} else if(obj == tfPhone) {
				tfAddress.requestFocus();
				tfAddress.setText("");
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public JTextField getTfId() {
		return tfId;
	}
	public JTextField getTfPassword() {
		return tfPassword;
	}
	public JTextField getTfHint() {
		return tfHint;
	}
	public JCheckBox getAgreeCheck() {
		return agreeCheck;
	}
}