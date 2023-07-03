package frame.main;
//달력(달력에 날짜 누르면 메모장에 그 날짜 출력) 윤선호
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import frame.db.DB;

class Calendarmain extends JFrame implements ActionListener, MouseListener, WindowListener{
	
	Container container = getContentPane();
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	JPanel panel3 = new JPanel();
	
	JButton buttonBefore = new JButton(" << ");
	JButton buttonAfter = new JButton(" >> ");
	
	JLabel label_year = new JLabel("0000");
	JLabel label1 = new JLabel();
	JLabel label_month = new JLabel("00");
	JLabel label2 = new JLabel();
	
	JButton[] buttons = new JButton[49];
	String[] dayNames = {"일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"};
	
	CalendarFunction cf = new CalendarFunction();
	
	private Date nowtoday;
	private JLabel lblToday;
	private JButton check_button;
	private Calendarmain Calendarmain;
	private String id;
	private MemoFrame memo;
	private MainFrame Mf;
	private String str;
	private DB db = new DB(null, null);
	
	public Calendarmain(MainFrame Mf, String id) {
		this.id = id;
		this.Mf = Mf;
		
		setTitle("달력");
		setSize(800, 600);
		setLocation(250, 150);
		
		init();
		this.addWindowListener(this);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	private void init() {
		 container.setLayout(new BorderLayout());
		 container.add("North", panel1);
		 container.add("Center", panel2);
		 
		 SimpleDateFormat today = new SimpleDateFormat("yyyy년 MM월 dd일");
		 nowtoday = new Date();
		 String nowDate = today.format(nowtoday);
		 lblToday = new JLabel(nowDate);
		 
		 panel1.setLayout(new FlowLayout());
		 
		 panel1.add(buttonBefore);
		 
		 panel1.add(label_year);
		 panel1.add(label1);
		 
		 panel1.add(label_month);
		 panel1.add(label2);
		 
		 panel1.add(buttonAfter);
		 panel1.setBackground(new Color(189, 215, 238));
		 
		 Font font = new Font("210 맨발의청춘 L", Font.BOLD, 30);
		 buttonBefore.addActionListener(this);
		 buttonBefore.setBackground(Color.LIGHT_GRAY);
		 
		 buttonAfter.addActionListener(this);
		 buttonAfter.setBackground(Color.LIGHT_GRAY);
		 
		 label_year.setFont(font);
		 label_month.setFont(font);
		 label1.setFont(font);
		 label2.setFont(font);
		 
		 label_year.setText(cf.getCalYearText());
		 label_month.setText(cf.getCalMonthText());
		 System.out.println(cf.getCalYearText());
		 System.out.println(cf.getCalMonthText());
		 
		 panel2.setLayout(new GridLayout(7, 7));
		 
		 for(int i = 0; i < buttons.length; i++) {
			 buttons[i] = new JButton();
			 buttons[i].setBackground(Color.white);
			 panel2.add(buttons[i]);
			 buttons[i].setFont(new Font("210 맨발의 청춘 L", Font.BOLD, 16));
			 buttons[i].addActionListener(this);
			 buttons[i].addMouseListener(this);
			 
			 if(i < 7) {
				 buttons[i].setText(dayNames[i]); 
				 buttons[i].setBackground(Color.GRAY);
				 buttons[i].setForeground(Color.white);
			 }
			 
		 }
		 cf.setButtons(buttons);
		 cf.calSet();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int gap = 0;
		str = e.getActionCommand();
		System.out.println(str);
		if(e.getSource() == buttonAfter) {				// 1달 후
			gap = 1;
		} else if(e.getSource() == buttonBefore ) {		// 1달 전
			gap = -1;
		} else if(str != null) {
			//2022-05-29 윤선호 해당 id, 날짜에 맞는 메모장을 보여준다.
			//db = new DB(null);
			String day = null;
			day = label_year.getText() + label_month.getText() + getStr() + "일";
			
			System.out.println(day);
			memo = new MemoFrame("메모 프레임", this, id, day);
		}
		
		cf.allInit(gap);
		label_year.setText(cf.getCalYearText()); //년도 갱신
		label_month.setText(cf.getCalMonthText()); // 월 갱신		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		//날짜 버튼을 두번 클릭하면 배경색이 초록색으로 바뀐다.
		//이렇게 해서 헬스장 출석을 했는지 확인
		if(e.getClickCount() == 2) {
			check_button.setBackground(Color.green);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
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
		MainFrame mf = new MainFrame(id);
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

	public JLabel getLabel_year() {
		return label_year;
	}

	public JLabel getLabel_month() {
		return label_month;
	}

	public String getStr() {
		return str;
	}	
}

 