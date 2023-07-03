package frame.main;
// 달력 윤선호
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalFrame extends JFrame{

	private JPanel panelNorth;
	private JButton btnToday;
	private JLabel lblToday;
	private JLabel lblTime;
	private JPanel panelCenter;
	private JPanel panel1;
	private JPanel panel2;
	private JButton btnPreyear;
	private JButton btnPreMonth;
	private JButton btnNextyear;
	private JButton btnNextMonth;
	private JLabel lblYearMonth;
	private String Weekday_Name[] = { "SUN", "MON", "TUE", "WED", "THR", "FRI", "SAT" };
	
	private JButton[] weekdayBtn;
	private JButton btnDate;
	private JButton[] day;
	public CalFrame(String title) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(250, 150);
		setSize(600, 400);
		setLayout(new BorderLayout());
		
		setNorth();
		setCenter();
		
		setVisible(true);
	}

	private void setNorth() {
		panelNorth = new JPanel(new BorderLayout());
		
		btnToday = new JButton("Today");
		lblToday = new JLabel("2022/05/15");
		lblTime = new JLabel("PM 1:03:34");
		
		panelNorth.add(btnToday, BorderLayout.WEST);
		panelNorth.add(lblToday, BorderLayout.CENTER);
		panelNorth.add(lblTime, BorderLayout.EAST);
		
		add(panelNorth, BorderLayout.NORTH);
	}

	private void setCenter() {
		panelCenter = new JPanel(new BorderLayout());
		
		panel1 = new JPanel();
		btnPreyear = new JButton("<<");
		btnPreMonth = new JButton("<");
		lblYearMonth = new JLabel("2022 / 5");
		btnNextyear = new JButton(">");
		btnNextMonth = new JButton(">>");
		
		panel1.add(btnPreyear);
		panel1.add(btnPreMonth);
		panel1.add(lblYearMonth);
		panel1.add(btnNextyear);
		panel1.add(btnNextMonth);
		
		panelCenter.add(panel1, BorderLayout.NORTH);
		
		panel2 = new JPanel(new GridLayout(6, 7));
		weekdayBtn = new JButton[7];
		day = new JButton[36];
		for(int i=0; i<7; i++ ) {
			weekdayBtn[i] = new JButton(Weekday_Name[i]);
			weekdayBtn[i].setBackground(Color.LIGHT_GRAY);
			panel2.add(weekdayBtn[i]);
		}
		
		for (int i = 1; i <36; i++) {
			 day[i] = new JButton();
			 day[i].setBackground(Color.WHITE);
			 panel2.add(day[i]);
			
		}
		panelCenter.add(panel2, BorderLayout.CENTER);
		
		add(panelCenter, BorderLayout.CENTER);
	}
}
