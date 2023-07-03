package frame.login;
//운동기록 전우진, 기타 기능 : (78, 340줄) 윤선호, (329줄) 김지웅
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import javax.swing.plaf.DimensionUIResource;

import frame.db.DB;
import frame.main.MainFrame;

public class Record extends JFrame implements ActionListener, WindowListener{

	private JPanel panelCenter, panelSouth, panelDate, panelName, panelWeight, panelTime, panelNum, panelSet;
	private JLabel lblDate, lblKg, lblTimes, lblNum, lblName, lblWeight, lblTime, lblSet, lblNums, lblSets;
	private JButton btnCheck, btnCancel, btnAdd;
	private JTextField tfWeight, tfDate, tfTime, tfSet;
	private Vector<String> vecCombo;
	private JComboBox<String> comboEx;
	private Color skyblue;
	private JTextField tfNum;
	private ArrayList<String> rcal;
	private String id, name;
	private DB db = new DB(null, null);

	public Vector<String> getVecCombo() {
		return vecCombo;
	}

	public Record(String title, String id, String name) {
		this.id = id;
		this.name = name;
		
		setTitle(title);
		setLocation(250, 150);
		setSize(510, 462);
		setLayout(new BorderLayout());
		setResizable(false);
		addWindowListener(this);
		skyblue = new Color(189, 215, 238);
		
	    setCenter();
	    setSouth();
	    
		setVisible(true);
	}
	
	private void setCenter() {
		panelCenter = new JPanel(new GridLayout(6, 1));
		panelDate = new JPanel();
		panelDate.setLayout(null);
		
		// 운동 등록 날짜 라벨
		lblDate = new JLabel("현재 날짜");
		lblDate.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 15));
		lblDate.setBounds(30, 25, 120, 30);
		panelDate.add(lblDate);
		
		//현재 날짜 구하기 윤선호
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String formatedNow = now.format(formatter);
		
		tfDate = new JTextField(formatedNow);
		tfDate.setBounds(150, 22, 140, 30);
		tfDate.setBorder(BorderFactory.createEmptyBorder());
		tfDate.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 14));
		tfDate.setEnabled(false);	  // 날짜 텍스트필드 활성화 막음	
		panelDate.add(tfDate);
		panelCenter.add(panelDate);
		
		panelName = new JPanel();
		panelName.setLayout(null);
		
		// 운동 등록 이름 라벨
		lblName = new JLabel("운동 이름");
		lblName.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 15));
		lblName.setBounds(30, 20, 120, 30);
		panelName.add(lblName);
		
		vecCombo = new Vector<String>();
	    vecCombo.add("기구를 선택해주세요");
	      
	    comboEx = new JComboBox<String>(vecCombo);
	    comboEx.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 15));
	    comboEx.setBorder(BorderFactory.createEmptyBorder(-2, -2, -2, -2));
	    comboEx.setBounds(150, 20, 170, 30);
	    comboEx.setBackground(Color.white);
	    db.MyCombo(this, id);
	    panelName.add(comboEx);
	    
	    // 운동 등록 추가 버튼 출력
	    btnAdd = new JButton("추가");
	    btnAdd.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 13));
	    btnAdd.setContentAreaFilled(false);
	    btnAdd.setBorderPainted(false);
	    btnAdd.setBounds(340, 20, 65, 30);
	    btnAdd.setForeground(Color.WHITE);
	    btnAdd.addActionListener(this);
	    panelName.add(btnAdd);
	 		
		panelCenter.add(panelName);
		
		panelWeight = new JPanel();
		panelWeight.setLayout(null);
		
		// 운동 등록 무게 라벨
		lblWeight = new JLabel("무게");
		lblWeight.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 15));
		lblWeight.setBounds(30, 20, 120, 30);
		panelWeight.add(lblWeight);
		
		tfWeight = new JTextField();
		tfWeight.setBounds(150, 19, 140, 30);
		tfWeight.setBorder(BorderFactory.createEmptyBorder());
		tfWeight.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 14));
		panelWeight.add(tfWeight);
		
		// 운동 등록 kg 라벨
		lblKg = new JLabel("kg");
		lblKg.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 15));
		lblKg.setBounds(300, 20, 50, 30);
		panelWeight.add(lblKg);
		panelCenter.add(panelWeight);
		
		panelTime = new JPanel();
		panelTime.setLayout(null);
		
		// 운동 등록 시간 라벨
		lblTime = new JLabel("운동 시간");
		lblTime.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 15));
		lblTime.setBounds(30, 20, 120, 30);
		panelTime.add(lblTime);
		
		tfTime = new JTextField();
		tfTime.setBounds(150, 19, 140, 30);
		tfTime.setBorder(BorderFactory.createEmptyBorder());
		tfTime.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 14));
		panelTime.add(tfTime);
		
		// 운동 등록 Times 라벨
		lblTimes = new JLabel("times");
		lblTimes.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 15));
		lblTimes.setBounds(300, 20, 50, 30);
		panelTime.add(lblTimes);
		panelCenter.add(panelTime);
		
		panelNum = new JPanel();
		panelNum.setLayout(null);
		
		// 운동 등록 횟수 라벨
		lblNum = new JLabel("횟수");
		lblNum.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 15));
		lblNum.setBounds(30, 20, 120, 30);
		panelNum.add(lblNum);
		
		tfNum = new JTextField();
		tfNum.setBounds(150, 16, 140, 30);
		tfNum.setBorder(BorderFactory.createEmptyBorder());
		tfNum.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 14));
		panelNum.add(tfNum);
		
		// 운동 등록 reps 라벨
		lblNums = new JLabel("reps");
		lblNums.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 15));
		lblNums.setBounds(300, 20, 50, 30);
		panelNum.add(lblNums);
		panelCenter.add(panelNum);
		
		panelSet = new JPanel();
		panelSet.setLayout(null);
		
		// 운동 등록 세트 수 라벨
		lblSet = new JLabel("세트 수");
		lblSet.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 15));
		lblSet.setBounds(30, 20, 120, 30);
		panelSet.add(lblSet);
		
		tfSet = new JTextField();
		tfSet.setBounds(150, 16, 140, 30);
		tfSet.setBorder(BorderFactory.createEmptyBorder());
		tfSet.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 14));
		panelSet.add(tfSet);
		
		// 운동 등록 set 라벨
		lblSets = new JLabel("set");
		lblSets.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 15));
		lblSets.setBounds(300, 20, 50, 30);
		panelSet.add(lblSets);
		panelCenter.add(panelSet);
		
		add(panelCenter, BorderLayout.CENTER);
		
		// 운동 등록 배경 이미지 출력
		ImageIcon imgBtn = new ImageIcon("imges/btnCancel.png");
		ImageIcon img = new ImageIcon("imges/record_img.png");
		ImageIcon imgCombo = new ImageIcon("imges/record_combo.png");
		ImageIcon imgCenter = new ImageIcon("imges/centerBackImg.png");
		
		JLabel lblAdd = new JLabel(imgBtn);
		lblAdd.setBounds(340, 20, 65, 30);
		panelName.add(lblAdd);

		JLabel lblDateImg = new JLabel(img);
		lblDateImg.setBounds(145, 20, 150, 35);
		panelDate.add(lblDateImg);
		
		JLabel lblCombo = new JLabel(imgCombo);
		lblCombo.setBounds(144, 16, 180, 35);
		panelName.add(lblCombo);
		
		JLabel lblWeightImg = new JLabel(img);
		lblWeightImg.setBounds(145, 16, 150, 35);
		panelWeight.add(lblWeightImg);
		
		JLabel lblTimeImg = new JLabel(img);
		lblTimeImg.setBounds(145, 16, 150, 35);
		panelTime.add(lblTimeImg);
		
		JLabel lbNumImg = new JLabel(img);
		lbNumImg.setBounds(145, 14, 150, 35);
		panelNum.add(lbNumImg);
		
		JLabel lblSetImg = new JLabel(img);
		lblSetImg.setBounds(145, 14, 150, 35);
		panelSet.add(lblSetImg);
		
		JLabel lblPanelDate = new JLabel(imgCenter);
		lblPanelDate.setBounds(0, 0, 510, 62);
		panelDate.add(lblPanelDate);
		
		JLabel lblPanelName = new JLabel(imgCenter);
		lblPanelName.setBounds(0, 0, 510, 62);
		panelName.add(lblPanelName);
		
		JLabel lblPanelWeight = new JLabel(imgCenter);
		lblPanelWeight.setBounds(0, 0, 510, 62);
		panelWeight.add(lblPanelWeight);
		
		JLabel lblPanelTime = new JLabel(imgCenter);
		lblPanelTime.setBounds(0, 0, 510, 62);
		panelTime.add(lblPanelTime);
		
		JLabel lblPanelNum = new JLabel(imgCenter);
		lblPanelNum.setBounds(0, 0, 510, 62);
		panelNum.add(lblPanelNum);
		
		JLabel lblPanelSet = new JLabel(imgCenter);
		lblPanelSet.setBounds(0, 0, 510, 62);
		panelSet.add(lblPanelSet);
		
	}

	private void setSouth() {
		panelSouth = new JPanel();
		panelSouth.setLayout(null);
		panelSouth.setPreferredSize(new Dimension(0, 60));
		
		// 운동 등록 버튼 출력
		btnCheck = new JButton("등록");
		btnCheck.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 13));
		btnCheck.setContentAreaFilled(false);
		btnCheck.setBorderPainted(false);
		btnCheck.setBounds(300, 10, 80, 40);
		btnCheck.setForeground(Color.WHITE);
		btnCheck.addActionListener(this);
		panelSouth.add(btnCheck);
				
		// 운동 등록 취소 버튼 출력
		btnCancel = new JButton("취소");
		btnCancel.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 13));
		btnCancel.setContentAreaFilled(false);
		btnCancel.setBorderPainted(false);
		btnCancel.setBounds(400, 10, 80, 40);
		btnCancel.setForeground(Color.WHITE);
		btnCancel.addActionListener(this);
		panelSouth.add(btnCancel);
		
		ImageIcon imgSouthAdd = new ImageIcon("imges/btnSouth.png");
		JLabel lblSouthAdd = new JLabel(imgSouthAdd);
		lblSouthAdd.setBounds(300, 10, 80, 40);
		panelSouth.add(lblSouthAdd);
		
		ImageIcon imgSouthCanel = new ImageIcon("imges/btnSouth.png");
		JLabel lblSouthCancel = new JLabel(imgSouthCanel);
		lblSouthCancel.setBounds(400, 10, 80, 40);
		panelSouth.add(lblSouthCancel);
		
		ImageIcon imgSouth = new ImageIcon("imges/southBackImg.png");
		JLabel lblSouth = new JLabel(imgSouth);
		lblSouth.setBounds(0, 0, 510, 90);
		panelSouth.add(lblSouth);
		
		add(panelSouth, BorderLayout.SOUTH);
		
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnAdd) {
			ExAddFrame exadd = new ExAddFrame("운동 등록", this, id, name);
			exadd.setLocationRelativeTo(this); // 프레임 정가운데 출력
			//0609 김지웅 프레임 모달창처리
			this.setEnabled(false);
			
		}else if(obj == btnCancel) {
			MainFrame mf = new MainFrame(id);
			mf.setLocationRelativeTo(this); 
			this.dispose();
		}else if(obj == btnCheck) {
			if(comboEx.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(this, "운동 기구를 선택해주세요.");
			} else {
			// 윤선호 운동기록 DB연결
			rcal = new ArrayList<String>();
			String ex_date = tfDate.getText();
			String ex_name = comboEx.getSelectedItem().toString();
			String ex_weight = tfWeight.getText();
			String ex_times = tfTime.getText();
			String ex_reps = tfNum.getText();
			String ex_set = tfSet.getText();
			
			rcal.add(ex_date);
			rcal.add(ex_name);
			rcal.add(ex_weight);
			rcal.add(ex_times);
			rcal.add(ex_reps);
			rcal.add(ex_set);
			
			DB db = new DB(null, null);
			db.EXInsert(id, name, ex_date, ex_name, ex_weight, ex_times, ex_reps, ex_set);
			MyRoutine mrt = new MyRoutine(id, name);
			mrt.setLocationRelativeTo(this); // 프레임 정가운데 출력
			db.EXRefresh(mrt, id);
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