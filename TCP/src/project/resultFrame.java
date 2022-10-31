package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class resultFrame extends JFrame implements ActionListener {
	private String username, recv_username; 
	private Font nameFont, scoreFont;
	private Color winColor, loseColor;
	private JButton btnExit;
	private JLabel winner, loser, win_score, lose_score;
	
	public resultFrame() {
		
		setTitle("result");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setResizable(false);
		
		nameFont = new Font("ROKAF SLAB SERIF MEDIUM", 0, 25);
		scoreFont = new Font("ROKAF SLAB SERIF MEDIUM", Font.BOLD, 120);
		winColor = new Color(95, 131, 211);
		loseColor = new Color(255, 75, 75);
		
		setMain();
		
		String[] tempArr = new String[2];
		String winnerName = null, loserName = null;
		int max = 0, min = 999;
		
		DB db = new DB();
		tempArr = db.gameResult();
		
		for(int i = 0; i < 2; i++) {
			if (max < Integer.parseInt(tempArr[i].substring(tempArr[i].lastIndexOf(",")+1))) {
				winner.setText(tempArr[i].substring(0, tempArr[i].indexOf(",")));
				max = Integer.parseInt(tempArr[i].substring(tempArr[i].lastIndexOf(",")+1));
			}
			
			if (min > Integer.parseInt(tempArr[i].substring(tempArr[i].lastIndexOf(",")+1))) {
				loser.setText(tempArr[i].substring(0, tempArr[i].indexOf(",")));
				min = Integer.parseInt(tempArr[i].substring(tempArr[i].lastIndexOf(",")+1));
			}
			
		}
		win_score.setText(Integer.toString(max));
		lose_score.setText(Integer.toString(min));
		
		setVisible(true);
	}

	private void setMain() {
		JPanel mainFrame = new JPanel();
		mainFrame.setBackground(Color.white);
		mainFrame.setLayout(null);
		
		winner = new JLabel();
		winner.setBounds(115, 140, 150, 150);
		winner.setFont(nameFont);
		winner.setHorizontalAlignment(JLabel.CENTER);
		mainFrame.add(winner);
		
		loser = new JLabel();
		loser.setBounds(520, 140, 150, 150);
		loser.setFont(nameFont);
		loser.setHorizontalAlignment(JLabel.CENTER);
		mainFrame.add(loser);
		
		win_score = new JLabel();
		win_score.setBounds(120, 300, 170, 170);
		win_score.setFont(scoreFont);
		win_score.setForeground(winColor);
		mainFrame.add(win_score);
		
		lose_score = new JLabel();
		lose_score.setBounds(520, 300, 170, 170);
		lose_score.setFont(scoreFont);
		lose_score.setForeground(loseColor);
		mainFrame.add(lose_score);
		
		btnExit = new JButton(new ImageIcon("image/exitBtn.png"));
		btnExit.setBounds(670, 460, 89, 83);
		btnExit.setBorderPainted(false);
		btnExit.setContentAreaFilled(false);
		btnExit.addActionListener(this);
		btnExit.setFocusable(false);
		mainFrame.add(btnExit);
		
		JLabel crown = new JLabel(new ImageIcon("image/crown.png"));
		crown.setBounds(120, 75, 135, 105);
		mainFrame.add(crown);
		
		JLabel reaper = new JLabel(new ImageIcon("image/reaper.png"));
		reaper.setBounds(525, 50, 127, 123);
		mainFrame.add(reaper);
		
		add(mainFrame);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == btnExit) {
			mainMenu mf = new mainMenu();
			mf.setLocationRelativeTo(this);
			this.dispose();
		}
	}
}
