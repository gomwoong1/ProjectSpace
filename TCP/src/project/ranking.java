package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.datatransfer.ClipboardOwner;
import java.security.KeyStore.PrivateKeyEntry;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.xml.stream.events.Comment;

public class ranking extends JFrame{

	private Font mainFont;
	private Font subFont;
	private Font inputFont;
	private JTextArea firstrank;
	private JTextArea secondrank;
	private JTextArea thirdrank;
	private JTextArea firstcmt;
	
public ranking() {
	setTitle("Ranking");
	setSize(500,600);
	setResizable(false);
	setLocationRelativeTo(null);
	
	mainFont = new Font("ROKAF SLAB SERIF MEDIUM", Font.BOLD, 50);
	subFont = new Font("ROKAF SLAB SERIF MEDIUM", 0, 19);
	inputFont = new Font("ROKAF SLAB SERIF MEDIUM", 0, 14);
	
	setUp();
	setDown();
	
	setVisible(true);
}

public void setUp() {
	JPanel up = new JPanel();
	up.setPreferredSize(new Dimension(500,330));
	up.setBackground(Color.white);
	up.setLayout(null);
	
	JLabel step = new JLabel(new ImageIcon("image/step.png"));
	step.setBounds(50,30,388,300);
	up.add(step);
	
	firstrank = new JTextArea("1등:",1,15);
	firstrank.setEditable(false);
	firstrank.setFont(inputFont);
	firstrank.setBounds(175, 150, 50, 20);
	firstrank.setBorder(BorderFactory.createEmptyBorder());
	up.add(firstrank);
	
	secondrank = new JTextArea("2등:",1,15);
	secondrank.setEditable(false);
	secondrank.setFont(inputFont);
	secondrank.setBounds(55, 220, 50, 20);
	secondrank.setBorder(BorderFactory.createEmptyBorder());
	up.add(secondrank);
	
	thirdrank = new JTextArea("3등:",1,15);
	thirdrank.setEditable(false);
	thirdrank.setFont(inputFont);
	thirdrank.setBounds(325, 220, 50, 20);
	thirdrank.setBorder(BorderFactory.createEmptyBorder());
	up.add(thirdrank);
	
	add(up, BorderLayout.NORTH);
	
}

public void setDown() {
	JPanel down = new JPanel();
	down.setPreferredSize(new Dimension(500,270));
	down.setBackground(Color.white);
	down.setLayout(null);
	
	JLabel man = new JLabel(new ImageIcon("image/man.png"));
	man.setBounds(30,65,136,148);
	down.add(man);
	
	JLabel cmt = new JLabel(new ImageIcon("image/cmt.png"));
	cmt.setBounds(145,95,304,120);
	down.add(cmt);
	
	firstcmt = new JTextArea("1등:",1,15);
	firstcmt.setEditable(false);
	firstcmt.setFont(inputFont);
	firstcmt.setBounds(215, 120, 200, 200);
	firstcmt.setBorder(BorderFactory.createEmptyBorder());
	down.add(firstcmt);
	
	
	add(down, BorderLayout.SOUTH);
	
}


}