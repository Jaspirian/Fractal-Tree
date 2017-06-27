package random;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SimplexTest extends JFrame {

	private JPanel contentPane;
	private OpenSimplexNoise noise = new OpenSimplexNoise(123456);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimplexTest frame = new SimplexTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SimplexTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(2400, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTestPanel panel = new JTestPanel();
		contentPane.add(panel);
	}
	
	public class JTestPanel extends JPanel {
		
		public JTestPanel() {
			
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			for(int i=0; i<40; i++) {
				g.drawOval(i*10, (int)(noise.eval(i, 0) * 10) + 10, 5, 5);
				g.drawOval(i*10, (int)(noise.eval(i, 1) * 10) + 40, 5, 5);
				g.drawOval(i*10, (int)(noise.eval(i, 2) * 10) + 70, 5, 5);
				g.drawOval(i*10, (int)(noise.eval(i, 3) * 10) + 100, 5, 5);
				System.out.println(noise.eval(i, 0));
			}
		}
		
	}

}
