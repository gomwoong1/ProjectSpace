package frame.board;
//PT게시판 글쓰기 수정 디자인 : 허유진, 기능 : 허유진 + 윤선호

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import frame.db.DB;
import frame.db.DBPT;

//비밀 번호 창이 널이면 메서지 창 뜨게 하기
public class BoardWrite_PT2 extends JFrame implements ActionListener, WindowListener{
	private Font mainFont;
	private JTextArea tacomment;
	private JScrollPane spcomment;
	private JButton btnsend;
	private Color skyblue;
	private ArrayList<String> alpt;
	private JTextField[] TxField;
	private String ID, namept;
	private JLabel lbl1, lbl2, lbl3, lbl4, lblpw;
	private Board2_PT bdpt;
	private JPasswordField pw;
	
	public BoardWrite_PT2(ArrayList<String> alpt, String ID, String namept, Board2_PT bdpt) {
		this.alpt = alpt;
		this.ID = ID;
		this.namept = namept;
		this.bdpt = bdpt;
		
		setTitle("PT게시판 글 수정");
		
		setLocation(300, 200);
		setSize(450, 535);
		setLayout(new BorderLayout());
		skyblue = new Color(189, 215, 238);
		mainFont = new Font("210 맨발의청춘 L", 0, 16);
		setResizable(false); 
		addWindowListener(this);
		setNorth();
		setCenter();
		
		setSouth();
		
		setVisible(true);
	}


	public BoardWrite_PT2(String string) {
		// TODO Auto-generated constructor stub
	}


	private void setNorth() {
		JPanel NorthPanel = new JPanel();
	      NorthPanel.setPreferredSize(new Dimension(300, 170));
	      NorthPanel.setLayout(null);
	      NorthPanel.setBackground(skyblue);
	      
	      String[] name = {"제목", "작성일자", "작성자"}; 
	      
	      JLabel[] TxValue = new JLabel[3];
	   
	      int x = 20;
	      int y = 10;
	      
	      for(int i = 0; i < TxValue.length; i++, y+=40) {
	         TxValue[i] = new JLabel(name[i]);
	         TxValue[i].setBounds(x, y, 100, 25);
	         TxValue[i].setFont(mainFont);
	         NorthPanel.add(TxValue[i]);
	      }
	      
	      
	       TxField = new JTextField[3];
	       
	       lblpw = new JLabel("비밀번호");
		      lblpw.setBounds(x, 130, 100, 25);
		      lblpw.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 15));
		      NorthPanel.add(lblpw);
	      
	      x = 100;
	      y = 10;
	      
	      for(int i = 0; i < TxField.length; i++, y+=40) {
	         TxField[i] = new JTextField(15);
	         TxField[i].setBorder(BorderFactory.createEmptyBorder());
	         TxField[i].setBounds(x, y, 310, 20);
	         TxField[i].addActionListener(this);
	         TxField[i].setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 12));
	         NorthPanel.add(TxField[i]);
	      }
	      TxField[0].setText(alpt.get(1));
	      
	      TxField[1].setEditable(false);
	      TxField[1].setBackground(Color.white);
	      TxField[1].setText(alpt.get(3));
	      
	      TxField[2].setEditable(false);
	      TxField[2].setBackground(Color.white);
	      TxField[2].setText(alpt.get(2));
	      
	      pw = new JPasswordField(10);
	      pw.setBounds(x, 130, 310, 20);
	      pw.setBorder(BorderFactory.createEmptyBorder());
	      NorthPanel.add(pw);
	      
	      ImageIcon img1 = new ImageIcon("imges/textimage_edit.png");
			lbl1 = new JLabel(img1);
			lbl2 = new JLabel(img1);
			lbl3 = new JLabel(img1);
			lbl4 = new JLabel(img1);
		      
		      JLabel[] lblimg  ={lbl1 , lbl2 , lbl3,lbl4};
		      
		      x = 90;
		      y = 1;
		      
		      for(int i = 0; i < lblimg.length; i++, y+=40) {
		    	  lblimg[i].setBounds(x, y, 330, 40);
		    	  NorthPanel.add(lblimg[i]);
		      }
	      
	      add(NorthPanel, BorderLayout.NORTH);
		
	}

	private void setCenter() {
		JPanel TextPanel = new JPanel();
		TextPanel.setBackground(skyblue);
	      
		tacomment =new  JTextArea(16,30);
		tacomment.setLineWrap(true);
		tacomment.setText(alpt.get(4));
		tacomment.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 13));
		spcomment = new JScrollPane(tacomment, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		TextPanel.add(spcomment);
		
	      add(TextPanel, BorderLayout.CENTER);
		
	}
	
	
	//수정버튼 
	private void setSouth() {
		JPanel SouthPanel = new JPanel();
		SouthPanel.setBackground(skyblue);
		SouthPanel.setPreferredSize(new Dimension(300, 50));
		SouthPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,10,0));
		btnsend = new JButton(new ImageIcon("imges/edit2.png"));
		btnsend.setBorderPainted(false);
		btnsend.setContentAreaFilled(false);
		btnsend.addActionListener(this);
		SouthPanel.add(btnsend);
		
		add(SouthPanel, BorderLayout.SOUTH);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		// 수정 버튼 이벤트	
			if (obj == pw || obj == btnsend) {
				char[] temp = pw.getPassword();
				String result = "";
				
				for(char ch	: temp) {
					Character.toString(ch);
					result += ""+ch+"";
				} 
				if(result.equals("")){
					JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요");
					pw.requestFocus();	}
				else if(result.length() > 4|| result.length() == 3||result.length() ==2||result.length()==1){
						JOptionPane.showMessageDialog(this,"비밀번호는 4글자입니다." ,"알림", JOptionPane.WARNING_MESSAGE);
						}else {
				
							DBPT dbpt = new DBPT(null);
							dbpt.BDUpdate(alpt.get(0),TxField[0].getText(), TxField[2].getText(), TxField[1].getText(), result, tacomment.getText());
							dbpt.PtRefresh(bdpt,ID);
							dispose();
						}
			}

		}		

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosing(WindowEvent e) {
		
		this.dispose();
		
	}


	@Override
	public void windowClosed(WindowEvent e) {
	
		
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