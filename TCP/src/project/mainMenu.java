package project;

import javax.swing.JFrame;

public class mainMenu extends JFrame {
	public mainMenu() {
		setTitle("mainMenu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 프레임 닫으면 종료되게 설정
		setSize(800, 600);
		setResizable(false);  //크기 조절 불가능하게 만듦
		setLocationRelativeTo(null);   //화면 중앙에 프레임이 뜨도록 설정
		
		setVisible(true);
	}
}
