import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChatFrame{
	
	public ChatFrame(String name) {	
	
	JFrame jf = new JFrame();
	jf.setTitle(name);
	jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	jf.setSize(350, 500);
	jf.setResizable(false);
	jf.setLocationRelativeTo(null);
	jf.setVisible(true);
	
	jf.setLayout(new BorderLayout());
	JPanel top = new JPanel();
	JPanel bottom = new JPanel();
	
	jf.add(top, BorderLayout.NORTH);
	jf.add(bottom, BorderLayout.SOUTH);
	
	top.setBackground(Color.red);
	bottom.setBackground(Color.blue);
	}
}
