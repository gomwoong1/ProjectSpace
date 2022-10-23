import javax.swing.JFrame;

public class MfFrame {

	public static void main(String[] args) {
		System.out.println("Hello World!");
		
		JFrame jf = new JFrame();
		jf.setTitle("Socket Test");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(300, 300);
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
	}

}
