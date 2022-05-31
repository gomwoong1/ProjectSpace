package frame.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import frame.db.dbOpen;

public class IdCheckFrame extends JFrame implements WindowListener, ActionListener, MouseListener, KeyListener {

	private Font mainFont;
	private JPanel panelCenter;
	private JButton btnCancel, btnCheck, btnOver;
	private JTextField tfId;
	private dbOpen db;
	private JoinFrame jf;
	private String id;
	
	public IdCheckFrame(String title, JoinFrame joinFrame) {
		this.jf = joinFrame;
		
		setTitle(title);
		setLocation(250, 150);
		setSize(360, 120);
		setLayout(new BorderLayout());
		setResizable(false);
		addWindowListener(this);
		addKeyListener(this);
		mainFont = new Font("210 맨발의청춘 L", Font.PLAIN, 13); 
	    
		setBack();

		setVisible(true);
		
	}

	private void setBack() {
		panelCenter = new JPanel();
		panelCenter.setLayout(null);
		
		// 비밀번호 확인 취소 버튼 출력
//		btnCancel = new JButton("취소");
//		btnCancel.setFont(mainFont);
//		btnCancel.setContentAreaFilled(false);
//		btnCancel.setBorderPainted(false);
//		btnCancel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
//		btnCancel.setBounds(0, 5, 40, 30);
//		btnCancel.addActionListener(this);
				
//		panelCenter.add(btnCancel);
		        
//		// 아이디 확인 버튼 출력
//		btnCheck = new JButton("확인");
//		btnCheck.setFont(mainFont);
//		btnCheck.setContentAreaFilled(false);
//		btnCheck.setBorderPainted(false);
//		btnCheck.setBounds(135, 73, 70, 30);
//				
//		btnCheck.addActionListener(this);
//				
//		panelCenter.add(btnCheck);
				
		// 아이디 확인 텍스트 필드(아이디) 출력
		tfId = new JTextField("아이디");
		tfId.setFont(mainFont);
		tfId.setBounds(45, 30, 135, 28);
		tfId.setBorder(BorderFactory.createEmptyBorder());
				
		tfId.addMouseListener(this);
				
		panelCenter.add(tfId);
		        
		// 아이디 확인 텍스트 필드 배경 이미지 출력
		ImageIcon imgId = new ImageIcon("imges/background_id.png");
		JLabel lblId = new JLabel(imgId);
		lblId.setBounds(40, 25, 148, 35);
		panelCenter.add(lblId);
				
		// 아이디 화면 아이디 중복 검색 버튼 출력
		btnOver = new JButton("중복확인");
		btnOver.setBounds(210, 28, 90, 29);
		btnOver.setContentAreaFilled(false);
		btnOver.setBorderPainted(false);
		btnOver.setForeground(Color.WHITE);
		btnOver.setFont(mainFont);
		btnOver.setBorder(BorderFactory.createEmptyBorder(-5, -5, -5, -5));
		btnOver.addActionListener(this);
		
		panelCenter.add(btnOver);

		// 아이디 화면 아이디 중복 배경 이미지 출력
		ImageIcon imgover = new ImageIcon("imges/overlap.png");
		JLabel lblover = new JLabel(imgover);
		lblover.setBounds(200, 25, 110, 35);
		
		panelCenter.add(lblover);
	
		// 아이디 확인 백그라운드 이미지 붙이기
		ImageIcon background_img = new ImageIcon("imges/idcheckback.png");
        JLabel background = new JLabel(background_img);
        background.setBounds(-7, -22, 360, 150);
        panelCenter.add(background);
        
        add(panelCenter, BorderLayout.CENTER);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnCancel) {
			this.dispose();
//		}else if(obj == btnCheck) {
//			id = tfId.getText();
//			if(id.equals("") || id.equals("아이디")) {
//				JOptionPane.showMessageDialog(this, "아이디를 입력해주세요.");
//			} else {
//				JTextField jfid = joinFrame.getTfId();
//				jfid.setText(id);
//				this.dispose();
//			}
			
		} else if(obj == btnOver) {
			id = tfId.getText();
			if(id.equals("") || id.equals("아이디")) {
				JOptionPane.showMessageDialog(this, "아이디를 입력해주세요.");
			} else {
				if(id.length() >= 4 && id.length() <= 16) {
					db = new dbOpen();
					db.checkID(this, id, tfId);
					jf = new JoinFrame("회원가입");
					jf.setLocationRelativeTo(null);
					JCheckBox ch = jf.getAgreeCheck();
					ch.setSelected(true);
					JTextField jfid = jf.getTfId();
					jfid.setText(id);
					this.dispose();
				} else {
					JOptionPane.showMessageDialog(this, "아이디는 4자리 이상 16자리 이하로 작성해주세요.");
				} 
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

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Object obj = e.getSource();
		if(obj == tfId) {
			tfId.setText("");
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
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
