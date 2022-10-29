package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.datatransfer.ClipboardOwner;
import java.security.KeyStore.PrivateKeyEntry;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.xml.stream.events.Comment;

public class ranking extends JFrame{

	private Font mainFont;
	private Font subFont;
	private JTextArea firstrank;
	private JTextArea secondrank;
	private JTextArea thirdrank;
	

public ranking() {
	setTitle("Ranking");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(500,600);
	setResizable(false);
	setLocationRelativeTo(null);
	
	mainFont = new Font("ROKAF SLAB SERIF MEDIUM", Font.BOLD, 50);
	subFont = new Font("ROKAF SLAB SERIF MEDIUM", 0, 19);

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
	
	add(up, BorderLayout.NORTH);
	
}

public void setDown() {
	JPanel down = new JPanel();
	down.setPreferredSize(new Dimension(500,270));
	down.setBackground(Color.white);
	down.setLayout(null);
	
	JLabel man = new JLabel(new ImageIcon("image/man.png"));
	man.setBounds(55,50,136,148);
	down.add(man);
	
	JLabel cmt = new JLabel(new ImageIcon("image/cmt.png"));
	cmt.setBounds(155,75,304,120);
	down.add(cmt);
	
	add(down, BorderLayout.SOUTH);
	
}


}