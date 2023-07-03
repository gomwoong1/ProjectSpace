package frame.login;
// 비밀번호재설정 전우진, 기타 기능 : (149줄) 김지웅
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import frame.db.dbOpen;
import frame.main.MainFrame;

public class PsCheckFrame extends JFrame implements MouseListener, ActionListener, WindowListener, KeyListener{

	private JPanel panelCenter;
	private JButton btnCancel, btnCheck, btnIdSearch;
	private JTextField tfId, tfHint;
	private Color gray;
	private Font mainFont;
	
	public PsCheckFrame(String title) {
		
		setTitle(title);
		setLocation(250, 150);
		setSize(362, 252);
		setLayout(new BorderLayout());
		setResizable(false);
		addWindowListener(this);
		
		mainFont = new Font("210 맨발의청춘 L", Font.PLAIN, 15); 
	    
		setBack();

		setVisible(true);
	}

	private void setBack() {
		panelCenter = new JPanel();
		panelCenter.setLayout(null);
		
		// 비밀번호 확인 취소 버튼 출력
		btnCancel = new JButton("취소");
		btnCancel.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 13));
		btnCancel.setContentAreaFilled(false);
		btnCancel.setBorderPainted(false);
		btnCancel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnCancel.setBounds(0, 5, 40, 30);
		btnCancel.addActionListener(this);
        panelCenter.add(btnCancel);
        
        // 비밀번호 확인 버튼 출력
        ImageIcon profileImg = new ImageIcon("imges/key_button.png");
		btnCheck = new JButton(profileImg);
		btnCheck.setContentAreaFilled(false);
		btnCheck.setBorderPainted(false);
		btnCheck.setBounds(140, 10, 70, 70);
		btnCheck.addActionListener(this);
		panelCenter.add(btnCheck);
		
        // 비밀번호 확인 텍스트 필드(아이디) 출력
        tfId = new JTextField("아이디");
		tfId.setFont(mainFont);
		tfId.setBounds(33, 94, 120, 30);
		tfId.setBorder(BorderFactory.createEmptyBorder());
		tfId.setFocusTraversalKeysEnabled(false);
		tfId.addActionListener(this);
		tfId.addMouseListener(this);
		tfId.addKeyListener(this);
		panelCenter.add(tfId);
        
		// 비밀번호 확인 텍스트 필드(힌트 답변) 출력
		tfHint = new JTextField("비밀번호 재설정 힌트 답변(4-16자)");
		tfHint.setFont(mainFont);
		tfHint.setBorder(BorderFactory.createEmptyBorder());
		tfHint.setBounds(33, 138, 250, 28);
		tfHint.addActionListener(this);
		tfHint.addMouseListener(this);
		panelCenter.add(tfHint);
		
		// 비밀번호 확인 아이디 찾기 라벨 출력
		btnIdSearch = new JButton("아이디를 잃어버리셨나요?");
		btnIdSearch.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 12));
		btnIdSearch.setContentAreaFilled(false);
		btnIdSearch.setBorderPainted(false);
		gray = new Color(100, 100, 100);
		btnIdSearch.setForeground(gray);
		btnIdSearch.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnIdSearch.setBounds(180, 170, 140, 30);
		btnIdSearch.addActionListener(this);
		panelCenter.add(btnIdSearch);
		
		// 비밀번호 확인 텍스트 필드 배경 이미지 출력
		ImageIcon imgId = new ImageIcon("imges/background_id.png");
		JLabel lblId = new JLabel(imgId);
		lblId.setBounds(25, 90, 150, 35);
		panelCenter.add(lblId);
		
		// 비밀번호 확인 텍스트 필드 배경 이미지 출력
		ImageIcon imgHint = new ImageIcon("imges/background_address.png");
		JLabel lblHint = new JLabel(imgHint);
		lblHint.setBounds(25, 135, 300, 35);
		panelCenter.add(lblHint);
		
        // 비밀번호 확인 백그라운드 이미지 붙이기
		ImageIcon background_img = new ImageIcon("imges/ps_background.png");
        JLabel background = new JLabel(background_img);
        background.setBounds(-7, -19, 362, 252);
        panelCenter.add(background);
        
        add(panelCenter, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnCancel) {
			Login lg = new Login();
			lg.setLocationRelativeTo(this);
			this.dispose();
			
		} else if(obj == btnIdSearch) {
			if(JOptionPane.showConfirmDialog(this, 
					"고객센터에 전화하시겠습니까?",
					"아이디 찾기",
					JOptionPane.YES_NO_OPTION
					) == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(this, "032-777-7777");
			}
		} else if(obj == btnCheck || obj == tfId || obj == tfHint) { 
			try {
				// 김지웅 비밀번호재설정 DB연결
				dbOpen temp = new dbOpen();
				temp.resetPW(this, tfId.getText(), tfHint.getText());
			} catch (Exception e2) {
				e2.printStackTrace();
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
		} else if(obj == tfHint) {
			tfHint.setText("");
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
			tfHint.requestFocus();
			tfHint.setText("");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}