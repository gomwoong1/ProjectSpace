package frame.login;
// 운동기구추가 디자인 : 전우진, 기능 : 윤선호, 기타 기능 : (132줄) 김지웅
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

import frame.db.DB;
import frame.db.dbOpen;

public class ExAddFrame extends JFrame implements ActionListener, MouseListener, WindowListener {
	
	private JPanel panelCenter;
	private JButton btnCancel, btnCheck, btnIdSearch;
	private JTextField tfName, tfHint;
	private Color gray;
	private Font mainFont;
	private JLabel lblAdd;
	private JButton btnAdd;
	private Record record;
	private String id, name;
	private DB db = new DB(null, null);
	
	public ExAddFrame(String title, Record record, String id, String name) {
		this.record = record;
		this.id = id;
		this.name = name;
		
		setTitle(title);
		setLocation(250, 150);
		setSize(400, 230);
		setLayout(new BorderLayout());
		setResizable(false);
		addWindowListener(this);
		
		mainFont = new Font("210 맨발의청춘 L", Font.PLAIN, 16); 
	    
		setCenter();

		setVisible(true);
	}

	private void setCenter() {
		panelCenter = new JPanel();
		panelCenter.setLayout(null);

        lblAdd = new JLabel("운동 이름");
        lblAdd.setFont(mainFont);
        lblAdd.setBounds(60, 61, 120, 30);
		panelCenter.add(lblAdd);
		
        // 운동 이름 추가 프레임 텍스트 필드(운동이름) 출력
        tfName = new JTextField("운동 이름");
		tfName.setFont(mainFont);
		tfName.setBounds(145, 62, 195, 30);
		tfName.setBorder(BorderFactory.createEmptyBorder());
		tfName.setFocusTraversalKeysEnabled(false);
		tfName.addMouseListener(this);
		tfName.addActionListener(this);
		panelCenter.add(tfName);
		
		// 운동 이름 추가 프레임 추가 버튼 출력
		btnAdd = new JButton("추가");
		btnAdd.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 13));
		btnAdd.setContentAreaFilled(false);
		btnAdd.setBorderPainted(false);
		btnAdd.setBounds(105, 120, 70, 30);
		btnAdd.setForeground(Color.WHITE);
		btnAdd.addActionListener(this);
		panelCenter.add(btnAdd);
		
		// 운동 이름 추가 프레임 취소 버튼 출력
		btnCancel = new JButton("취소");
		btnCancel.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 13));
		btnCancel.setContentAreaFilled(false);
		btnCancel.setBorderPainted(false);
		btnCancel.setBounds(205, 120, 70, 30);
		btnCancel.setForeground(Color.WHITE);
		btnCancel.addActionListener(this);
		panelCenter.add(btnCancel);
		
		// 운동 이름 추가 프레임 텍스트 필드 배경 이미지 출력
		ImageIcon imgId = new ImageIcon("imges/ExAdd.png");
		JLabel lblId = new JLabel(imgId);
		lblId.setBounds(140, 58, 205, 35);
		panelCenter.add(lblId);
		
		// 운동 이름 추가 프레임 버튼 배경 이미지 출력
		ImageIcon imgAdd = new ImageIcon("imges/btnadd.png");
		JLabel lblAdd = new JLabel(imgAdd);
		lblAdd.setBounds(100, 120, 80, 30);
		panelCenter.add(lblAdd);
		
		// 운동 이름 추가 프레임 버튼 배경 이미지 출력
		ImageIcon imgCancel = new ImageIcon("imges/btnadd.png");
		JLabel lblCancel = new JLabel(imgCancel);
		lblCancel.setBounds(200, 120, 80, 30);
		panelCenter.add(lblCancel);
		
        // 운동 이름 추가 프레임 백그라운드 이미지 붙이기
		ImageIcon background_img = new ImageIcon("imges/idcheck_back.png");
        JLabel background = new JLabel(background_img);
        background.setBounds(-8, -35, 400, 230);
        panelCenter.add(background);
        
        add(panelCenter, BorderLayout.CENTER);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnCancel) {
			//0609 김지웅 모달창처리 해제
			record.setEnabled(true);
			record.setVisible(true);
			this.dispose();
		}else if(obj == tfName || obj == btnAdd) {
			if(tfName.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "운동 이름을 작성해주세요.");
			}else {
				record.getVecCombo().removeAllElements();
				record.setEnabled(true);
				record.setVisible(true);
				db.AddMyRt(record, id, tfName.getText());
				db.MyCombo(record, id);
				System.out.println(tfName.getText());
				this.dispose();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		tfName.setText("");
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
}

