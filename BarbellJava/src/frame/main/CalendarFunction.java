package frame.main;
//달력 윤선호
import java.awt.Color;
import java.util.Calendar;

import javax.swing.JButton;
public class CalendarFunction {
	JButton[] buttons;
	Calendar cal = Calendar.getInstance();
	int year, month;
	
	public CalendarFunction() {
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
	}
	public void setButtons(JButton[] buttons) {
		this.buttons = buttons;
	}
	
	public String getCalYearText() {
		return "    " + year + "년 ";
	}
	public String getCalMonthText() {
		return "  " + month + "월" + "    ";
	}
	// 버튼에 날짜 출력
	public void calSet() {
		// calendar 객체 날짜 1일 설정
		cal.set(year, month-1, 1);
		
		// 그 달의 1일 요일 수
		int firstDay = cal.get(Calendar.DAY_OF_WEEK);
		// 요일 수 1일 시작, 배열 0부터 시작
		firstDay--;
		
		for(int i = 1; i <= cal.getActualMaximum(cal.DATE); i++) {
			// buttons[0] ~ [6] : 일 ~ 토
			// buttons[7] ~     : 날짜 출력
			buttons[6 + firstDay + i].setText(String.valueOf(i));
			//System.out.println(String.valueOf(i));
		}		
		buttons[0].setBackground(new Color(230, 50, 50));
		buttons[6].setBackground(new Color(50, 80, 230));
	}
	// 달력 새로운 년 월 출력
	public void allInit(int gap) {
		// 버튼에 있는 날짜 지우기
		for(int i =7; i < buttons.length; i++) {
			buttons[i].setText("");
		}
		month += gap;
		if(month <= 0) {
			year--;
			month = 12;
		} else if(month >= 13) {
			year++;
			month = 1;
		}
		calSet();
	}		
}