package frame.main;
// 센터 정보 김지웅
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class StoreInfo extends JFrame implements WindowListener {
	
	private Color mainColor, subColor;
	private Font titleFont, infoFont;
	private JLabel[] titleLbl, infoLbl;
	private String ID;

	public StoreInfo(String ID){
		this.ID = ID;
		
		setTitle("센터 정보");
		setBounds(95, 100, 350, 500);
		setResizable(false); //창 크기 조절 불가능하게 만들기
		setLayout(new BorderLayout());
		mainColor = new Color(189,215,238);
		subColor = new Color(189, 189, 189);
		titleFont = new Font("210 맨발의청춘 L", 0, 18);
		infoFont = new Font("210 맨발의청춘 L", 0, 16);
		
		setNorthPanel();
		setCenterPanel();
		
		this.addWindowListener(this);
		
		setVisible(true);
	}
	
	private void setNorthPanel() {
		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.WHITE);
		southPanel.setPreferredSize(new Dimension(350, 140));
		
		JLabel imglbl = new JLabel(new ImageIcon("imges/InfoStoreImg.png"));
		imglbl.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		southPanel.add(imglbl);
		
		add(southPanel, BorderLayout.NORTH);
	}
	
	private void setCenterPanel() {
		JPanel CenterPanel = new JPanel();
		CenterPanel.setLayout(new BorderLayout());
		add(CenterPanel, BorderLayout.CENTER);
		
		JPanel TitlePanel = new JPanel();
		TitlePanel.setPreferredSize(new Dimension(105, 370));
		TitlePanel.setBackground(mainColor);
		TitlePanel.setBorder(new LineBorder(subColor));
		CenterPanel.add(TitlePanel, BorderLayout.WEST);
		
		String[] titleText = {"센터이름", "영업시간", "센터위치", "문의하기"};
		titleLbl = new JLabel[4];
		
		for(int i = 0; i < titleText.length; i++) {
			titleLbl[i] = new JLabel(titleText[i]);
			titleLbl[i].setFont(titleFont);
			TitlePanel.add(titleLbl[i]);
			titleLbl[i].setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		}
		
		JPanel InfoPanel = new JPanel();
		InfoPanel.setBackground(Color.WHITE);
		InfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		InfoPanel.setBorder(new LineBorder(subColor));
		CenterPanel.add(InfoPanel, BorderLayout.CENTER);
		
		String[] infoText = {"건강해GYM", "월~일요일 00:00 ~ 24:00 ",
				"인천 미추홀구 인하로 777길 2층", "032-777-7777"};
		infoLbl = new JLabel[4];
		
		for(int i = 0; i < infoText.length; i++) {
			infoLbl[i] = new JLabel(infoText[i]);
			infoLbl[i].setFont(infoFont);
			InfoPanel.add(infoLbl[i]);
			infoLbl[i].setBorder(BorderFactory.createEmptyBorder(17, 5, 0, 0));
		}
		
		JLabel QR = new JLabel(new ImageIcon("imges/chatQr.jpg"));
		QR.setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 0));
		InfoPanel.add(QR);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
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
