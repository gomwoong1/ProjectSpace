package frame.board;
//자유게시판 댓글 디자인 : 허유진, 기능 : 윤선호

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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.DimensionUIResource;

import frame.db.DB;
import frame.login.QuestionPW;


public class BoardEdit extends JFrame implements ActionListener, WindowListener{
	private Font mainFont;
	private Color skyblue;
	private JTextArea ta_write, ta_comment;
	private JScrollPane write_sp,comment_sp;
	private JPanel panel_write,panel_button,panel_cmtlabel,panel_comment,panel_commnet;
	private JButton btn_edit,btn_delete,btn_comment;
	private JLabel lblcomment;
	private JTextField tfcomment;
	private ArrayList<String> al;
	private int bdi;
	private Board bd;
	private Boardwrite2 bw2;
	private String id,name;
	private JTextField[] TxField;
	private ImageIcon img1;
	private JLabel lbl1,lbl2,lbl3,lbl4;//이미지 붙이는거라 1,2,3,4로 지정

	
	public BoardEdit(ArrayList<String> al, String id, String name, Board bd) {
		this.al = al;
		this.id = id;
		this.name = name;
		this.bd = bd;
		
		setTitle("게시물 댓글 및 삭제");
		setLocation(200, 200);
		setSize(450, 643);
		
		setLayout(new BorderLayout());
		
		skyblue = new Color(189, 215, 238);
		mainFont = new Font("210 맨발의청춘 L", 0, 16);
		
		addWindowListener(this);
		setNorth();
		setCenter();
		
		setSouth();
		setResizable(false); 
		setVisible(true);
	}


	private void setNorth() {
		JPanel NorthPanel = new JPanel();
	      NorthPanel.setPreferredSize(new Dimension(300, 150));
	      NorthPanel.setLayout(null);
	      NorthPanel.setPreferredSize(new DimensionUIResource(100,160));
	      NorthPanel.setBackground(skyblue);
	      
	      String[] name = {"제목", "작성자", "작성일자", "카테고리"}; 
	      
	      JLabel[] TxValue = new JLabel[4];
	   
	      int x = 20;
	      int y = 10;
	      
	      for(int i = 0; i < TxValue.length; i++, y+=40) {
	         TxValue[i] = new JLabel(name[i]);
	         TxValue[i].setBounds(x, y, 100, 25);
	         TxValue[i].setFont(mainFont);
	         NorthPanel.add(TxValue[i]);
	      }
	      
  
	      TxField = new JTextField[4];//tf 텍스트
	      
	      x = 100;
	      y = 10;
	      
	      for(int i = 0; i < TxField.length; i++, y+=40) {
	         TxField[i] =  new JTextField(15);
	         TxField[i].setBounds(x, y, 310, 20);
	         TxField[i].setBorder(BorderFactory.createEmptyBorder());
	         TxField[i].setText(al.get(i+1));
	         TxField[i].setBackground(Color.white);
	         //0602 윤선호 편집 textfield 편집 불가
	         TxField[i].setEditable(false);
	         TxField[i].setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 12));
	         NorthPanel.add(TxField[i]);
	      }
	      
	    img1 = new ImageIcon("imges/textimage_edit.png");
		lbl1 = new JLabel(img1);
		lbl2 = new JLabel(img1);
		lbl3 = new JLabel(img1);
		lbl4 = new JLabel(img1);
	      
	    JLabel[] lblimg  ={lbl1 , lbl2 , lbl3, lbl4};
	      
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
	    TextPanel.setLayout(new BorderLayout()); 
		
	    //글쓰기
		panel_write = new JPanel();
		panel_write.setBackground(skyblue);
		ta_write =new  JTextArea(8,30);
		ta_write.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 13));
		ta_write.setLineWrap(true);
		ta_write.setEditable(false);//편집 불가
		ta_write.setText(al.get(5));
		write_sp = new JScrollPane(ta_write, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel_write.add(write_sp);
		TextPanel.add(panel_write,BorderLayout.CENTER);
		
		
		panel_button = new JPanel();
		panel_button.setBackground(skyblue);
		panel_button.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		//수정버튼
		btn_edit = new JButton(new ImageIcon("imges/edit2.png"));
		btn_edit.addActionListener(this);
		btn_edit.setBorderPainted(false);
		btn_edit.setContentAreaFilled(false);

		panel_button.add(btn_edit);
		
		//삭제버튼
		btn_delete = new JButton(new ImageIcon("imges/delete2.png"));
		btn_delete.setBorderPainted(false);
		btn_delete.setContentAreaFilled(false);
		btn_delete.addActionListener(this);

		panel_button.add(btn_delete);
		TextPanel.add(panel_button, BorderLayout.NORTH);
		
	    add(TextPanel, BorderLayout.CENTER);
		
	}
	
	private void setSouth() {
		JPanel SouthPanel = new JPanel();
		SouthPanel.setLayout(new BorderLayout());
		
		panel_cmtlabel = new JPanel();
		panel_cmtlabel.setBackground(skyblue);
		panel_cmtlabel.setLayout(new FlowLayout(FlowLayout.LEFT,20,7));
		lblcomment = new JLabel("댓글");
		lblcomment.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 13));
		panel_cmtlabel.add(lblcomment);
		SouthPanel.add(panel_cmtlabel, BorderLayout.NORTH);
		
		//댓글 목록 TextArea
		panel_comment = new JPanel();
		panel_comment.setBackground(skyblue);
		ta_comment = new JTextArea(8,30);
		ta_comment.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 13));
		ta_comment.setLineWrap(true);
		ta_comment.setEditable(false);//편집 불가
		
		comment_sp = new JScrollPane(ta_comment, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		panel_comment.add(comment_sp);
		SouthPanel.add(panel_comment, BorderLayout.CENTER);
		
		
		//마지막 부분 답글 텍스트 있는 부분!
		panel_commnet = new JPanel();
		panel_commnet.setLayout(null);
		panel_commnet.setPreferredSize(new DimensionUIResource(100, 70));
		panel_commnet.setBackground(skyblue);
		
		//댓글 입력 TextField
		tfcomment = new JTextField(20);
		tfcomment.setBounds(15, 20, 300, 20);
		tfcomment.setBorder(BorderFactory.createEmptyBorder());
		tfcomment.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 14));
		//엔터 입력하면 댓글이 추가된다.
		tfcomment.addActionListener(this);
		panel_commnet.add(tfcomment);
		
		//답글 이미지
		ImageIcon imgcomment = new ImageIcon("imges/textimage_edit.png");
		JLabel lblcomment = new JLabel(imgcomment);
		lblcomment.setBounds(8, 3, 330, 50);
		panel_commnet.add(lblcomment);
		
		//댓글 버튼
		btn_comment = new JButton(new ImageIcon("imges/comment2_3.png"));	
		btn_comment.setBounds(330, 8, 100, 40);
		btn_comment.setBorderPainted(false);
		btn_comment.setContentAreaFilled(false);
		btn_comment.addActionListener(this);
		
		panel_commnet.add(btn_comment);
		SouthPanel.add(panel_commnet, BorderLayout.SOUTH);
		
		
		SouthPanel.setBackground(skyblue);
		add(SouthPanel, BorderLayout.SOUTH);
	}

	//2022-05-27 윤선호 선택한 게시물에 대한 댓글 불러오기
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == tfcomment || obj == btn_comment) {
			ta_comment.append(tfcomment.getText() + "\n");
			ta_comment.setText("");
			
			//현재 선택한 게시물의 글번호를 가져옴
			bdi = Integer.parseInt(al.get(0).toString());
			System.out.println(bdi);
		
			DB db = new DB(this, null);
			System.out.println(al.get(1));
			
			
			db.BDCMT(al.get(0), tfcomment.getText(), name);
			tfcomment.setText("");
			db.DisplayCMT(al.get(0));
			
		//2022-05-28 19:36 윤선호 수정 버튼 누르면 게시물 수정창이 뜬다.
		}else if(obj == btn_edit) {
			//2022-06-03 윤선호
			//로그인 한 사람의 id와 이 글을 작성한 사람의 아이디가 다르면 수정, 삭제 불가
			if(!name.equals(al.get(2))) {
				System.out.println(al.get(2) + "이름 다름");
				JOptionPane.showMessageDialog(this, "게시물 수정 불가", "경고 메시지", JOptionPane.WARNING_MESSAGE);
				
			}else if(name.equals(al.get(2))) {
				System.out.println("이름이 같음");
				bw2 = new Boardwrite2(al, id, name, bd);
				bw2.setLocationRelativeTo(this);
				this.dispose();
			}
			
		//2022-05-28 20:50 윤선호 게시물 삭제기능
		}else if(obj == btn_delete) {
			if(!name.equals(al.get(2))) {
				JOptionPane.showMessageDialog(this, "게시물 삭제 불가", "경고 메시지", JOptionPane.WARNING_MESSAGE);
			}else if(name.equals(al.get(2))) {
					DB db = new DB(null, null);
					
					db.DeleteBD(al.get(0));
					db.TableRefresh(bd);
					
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
		this.dispose();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}


	public JTextArea getta_comment() {
		return ta_comment;
	}
	
}