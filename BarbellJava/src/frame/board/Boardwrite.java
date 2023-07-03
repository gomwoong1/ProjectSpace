package frame.board;
//자유게시판 글쓰기 디자인 : 허유진, 기능 : 윤선호
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.DimensionUIResource;

import frame.db.DB;

public class Boardwrite extends JFrame implements ActionListener, WindowListener{

	private JPanel PanelUp, PanelDown,PanelCenter,panel1,panel2,panel1UP,panel1Center,panel1Down;
	private JTextArea tacomment;	
	private JButton btnSend;
	private JLabel lbltitle,lblWriteday,lblselection,lblWriter,lblwriter;
	private JTextField tftitle,tfWriter,tfWriteday;	
	private Vector<String> vecCombo;
	private JComboBox<String> comboselection;
	private Color skyblue;
	private JScrollPane spcomment;
	private String id, name;
	private Board bd;
	private ImageIcon imgwriter;
	
	public Boardwrite(String title, String id, String name, Board bd) {
		this.id = id;
		this.name = name;
		this.bd = bd;
		
		setTitle(title);
		
		setLocation(300, 300);
		setSize(440, 580);
		
		setLayout(new BorderLayout());
		skyblue = new Color(189, 215, 238);
		
		PanelUP();
		PanelCenter();
		PanelDown();
		
		setResizable(false);
		addWindowListener(this);
		setVisible(true);
	}

	private void PanelUP() {
		PanelUp = new JPanel();
		PanelUp.setLayout(new BorderLayout());
			
		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(4,1));
		
		panel1UP=new JPanel();
		panel1UP.setLayout(null);
		panel1UP.setPreferredSize(new DimensionUIResource(100, 70));
		panel1UP.setBackground(skyblue);
		
		lbltitle = new JLabel("제목");
		lbltitle.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 18));
		lbltitle.setLocation(20, 20);
		lbltitle.setSize(40, 20);
		panel1UP.add(lbltitle);
		
		//제목 텍스트 필드
		tftitle = new JTextField(25);
		tftitle.setLocation(110, 20);
		tftitle.setSize(280, 20);
		tftitle.setBorder(BorderFactory.createEmptyBorder());
		tftitle.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 14));
		panel1UP.add(tftitle);
		
		// 제목 텍스트 배경
		ImageIcon imgtitle = new ImageIcon("imges/writefield.png");
		JLabel lbltitle = new JLabel(imgtitle);
		lbltitle.setBounds(98, 15, 330, 30);
		panel1UP.add(lbltitle);
				
		panel1.add(panel1UP);//제목
		
		panel1Center = new JPanel();
		panel1Center.setLayout(null);
		panel1Center.setPreferredSize(new DimensionUIResource(100, 70));
		panel1Center.setBackground(skyblue);
		
		lblWriteday = new JLabel("작성일자");
		lblWriteday.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 18));
		lblWriteday.setLocation(20, 20);
		lblWriteday.setSize(150, 20);
		panel1Center.add(lblWriteday);
		
		//현재 날짜 구하기 0603 윤선호 추가
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String formatedNow = now.format(formatter);
		
		tfWriteday = new JTextField(formatedNow);
		tfWriteday.setLocation(110, 20);
		tfWriteday.setSize(280, 20);
		tfWriteday.setBackground(Color.white);
		tfWriteday.setEditable(false);
		tfWriteday.setBorder(BorderFactory.createEmptyBorder());
		tfWriteday.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 14));
		panel1Center.add(tfWriteday);
		
		ImageIcon imgwriteday = new ImageIcon("imges/writefield.png");
		JLabel lblwriteday = new JLabel(imgwriteday);
		lblwriteday.setBounds(98, 15, 330, 30);
		panel1Center.add(lblwriteday);
		
		panel1.add(panel1Center);//작성일자
		
		panel1Down = new JPanel();
		panel1Down.setLayout(null);
		panel1Down.setPreferredSize(new DimensionUIResource(100, 70));
		panel1Down.setBackground(skyblue);
		
		lblWriter = new JLabel("작성자");
		lblWriter.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 18));
		lblWriter.setLocation(20, 20);
		lblWriter.setSize(150, 20);
		panel1Down.add(lblWriter);
		
		//로그인 한사람의 이름을 바로 붙여줌 0603 윤선호 
		tfWriter = new JTextField(name);
		tfWriter.setLocation(110, 20);
		tfWriter.setSize(280, 20);
		tfWriter.setBackground(Color.white);
		tfWriter.setEditable(false);
		tfWriter.setBorder(BorderFactory.createEmptyBorder());
		tfWriter.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 14));
		panel1Down.add(tfWriter);
		
		imgwriter = new ImageIcon("imges/writefield.png");
		lblwriter = new JLabel(imgwriter);
		lblwriter.setBounds(98, 15, 330, 30);
		panel1Down.add(lblwriter);
		
		panel1.add(panel1Down);//작성자
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));
		panel2.setBackground(skyblue);
		lblselection = new JLabel("카테고리");
		lblselection.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 18));
		panel2.add(lblselection);
		
		vecCombo = new Vector<String>();
	    vecCombo.add("운동");
	    vecCombo.add("나눔");
	      
	    comboselection = new JComboBox<String>(vecCombo);
	    comboselection.setPreferredSize(new Dimension(100, 30));
	    comboselection.setBackground(Color.white);
	      
		panel2.add(comboselection);//콤보박스
		panel1.add(panel2);
		
		PanelUp.add(panel1);
		add(PanelUp, BorderLayout.NORTH);
	}
	
	private void PanelCenter() {
		PanelCenter = new JPanel();
		PanelCenter.setBackground(skyblue);
		
		tacomment = new JTextArea(12,30);
		tacomment.setLineWrap(true);
		tacomment.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 13));
		
		spcomment = new JScrollPane(tacomment, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		PanelCenter.add(spcomment);
		add(PanelCenter, BorderLayout.CENTER);
	}
	
	private void PanelDown() {
		PanelDown = new JPanel();
		PanelDown.setBackground(skyblue);
		
		PanelDown.setPreferredSize(new Dimension(300, 50));
		PanelDown.setLayout(new FlowLayout(FlowLayout.RIGHT,5,0));
		
		btnSend = new JButton(new ImageIcon("imges/upload.png"));
		btnSend.setBorderPainted(false);
		btnSend.setContentAreaFilled(false);
		btnSend.addActionListener(this);
		
		PanelDown.add(btnSend);
		add(PanelDown, BorderLayout.SOUTH);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		//2022-05-26 윤선호 게시글 추가
		if(obj == btnSend) {
			DB db = new DB(null, null);
			//게시물 하나씩 추가할때 마다 글 하나씩 추가
			db.BDInsert(tftitle.getText(), tfWriteday.getText(), tfWriter.getText(), comboselection.getSelectedItem().toString(), tacomment.getText(), id);
			db.TableRefresh(bd);
			this.dispose();
		}
		
	}
	
	

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		//bd.getModel().fireTableDataChanged();
		this.dispose();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		//Board bd = new Board(id, name);
		//bd.getModel().fireTableDataChanged();
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


