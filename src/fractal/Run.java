package fractal;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.geom.Line2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import entities.Branch;
import entities.Tree;
import entities.Wind;
import net.miginfocom.swing.MigLayout;
import panels.SettingsPane;
import panels.TreePane;

public class Run extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Run frame = new Run();
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
	public Run() {
		super("Fractal Tree");
        setBounds(100, 50, 1200, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        getContentPane().setLayout(new MigLayout("", "[200][grow]", "[grow]"));

        Wind wind = new Wind(50, 395205892, false);
        Tree tree = new Tree(8, 3, 12, 60, .9, .95, 0000, 40, new Point(0,0));
        
        TreePane treePane = new TreePane(tree, wind);
        treePane.setBackground(Color.WHITE);
        getContentPane().add(treePane, "grow, cell 1 0");
        
        treePane.startWind();
        
        SettingsPane settingsPane = new SettingsPane(wind, tree, treePane);
        getContentPane().add(settingsPane, "cell 0 0,grow");
        
        treePane.setSettings(settingsPane);
	}

}
