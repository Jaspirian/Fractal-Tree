package panels;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import entities.Branch;
import entities.Leaf;
import entities.Tree;
import entities.Wind;
import random.OpenSimplexNoise;

public class TreePane extends JPanel {

	private ArrayList<Branch> branches = new ArrayList<Branch>();
	private ArrayList<Leaf> leaves = new ArrayList<Leaf>();
	
	private Tree tree;
	private Wind wind;
	private SettingsPane settings;
	
	long seed = 3902923;
	OpenSimplexNoise windNoise = new OpenSimplexNoise(seed);
	OpenSimplexNoise noise = new OpenSimplexNoise(seed);
	
	public TreePane(Tree tree, Wind wind) {
		this.setPreferredSize(new Dimension(700,550));
		
		this.addMouseMotionListener(new MouseOver(this));
		
		this.tree = tree;
		this.wind = wind;
		
		tree.setStartPoint(new Point(this.getPreferredSize().width/2, this.getPreferredSize().height));
		
		growBranch(tree.getBase(), 0);
	}
	
	public void setSettings(SettingsPane pane) {
		this.settings = pane;
	}
	
	public void growBranch(Branch b, int angle) {
		//get values of previous branch
			Line2D newLine = new Line2D.Double();
			newLine.setLine(b.getBase().getP1(), b.getEnd().getP1());
			int length = (int)b.lineLength(newLine);
			length -= 4;
			int width = (int)b.lineLength(b.getEnd());
			width -= 1;
			
			//Get origin
			Point2D origin;
			if(angle > 90) { 
				origin = b.getEnd().getP1();
			} else {
				origin = b.getEnd().getP2();
			}
			
			Line2D newEnd = new Line2D.Double(new Point((int)origin.getX() - width, (int)origin.getY() - length), new Point((int)origin.getX(), (int)origin.getY() - length));
			
			//rotate lines
			AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(angle), origin.getX(), origin.getY());
			newEnd = new Line2D.Double(at.transform(newEnd.getP1(), null), at.transform(newEnd.getP2(), null));
			
			//make polygon
			Branch newBranch = new Branch(new Line2D.Double(newEnd.getP1(), newEnd.getP2()), b);
			
			//handle previous stuff
			b.addOffshoot(newBranch);
			
			//save
			branches.add(newBranch);
			
			//add leaf
			if(length < 30) if(noise.eval(length, 2) + 1 < tree.getLeafOccurrence() * 2) leaves.add(new Leaf(20, newBranch));
			
			//random things
			double dieChance = 1 - tree.getBranchOccurrence();
			Random r = new Random();
				//check number of branches from trunk, die the closer you are to the max it can support
			for(int i=0; i<newBranch.getPoly().npoints; i++) {
				if(newBranch.getPrevious(0) < tree.getMaxBranches()) {
					dieChance = 0;
				} else {
					dieChance = (1 - tree.getBranchOccurrence() + ((newBranch.getPrevious(0))) / (double)(tree.getMaxBranches() - tree.getMinBranches())/4);	
				}
			}

			int angle1 = angle + (int) (((noise.eval(newBranch.getPrevious(0), 0) - .5) * tree.getTreeCurve()));
//			System.out.println("angle1 = " + angle1 + " = " + angle + " + " + "( " + (noise.eval(newBranch.getPrevious(0), 0) + .5) + " at " + newBranch.getPrevious(0) + " * " + angleVar + ")" );
			int angle2 = angle + (int) (((noise.eval(newBranch.getPrevious(0), 1) + .5) * tree.getTreeCurve()));
//			System.out.println("angle2 = " + angle2 + " = " + angle + " + " + (int) ((noise.eval(newBranch.getPrevious(0), 1) + .5) * angleVar));
			int angle3 = angle + (int) (((noise.eval(newBranch.getPrevious(0), 2)) * tree.getTreeCurve()));
//			System.out.println("angle3 = " + angle3 + " = " + angle + " + " + (int) ((noise.eval(newBranch.getPrevious(0), 2) + .5) * angleVar));
			
			//do another
			if(r.nextDouble() > dieChance) growBranch(newBranch, angle1);
			if(r.nextDouble() > dieChance) growBranch(newBranch, angle2);
			//Possible 3rd
			if(r.nextDouble() > dieChance && r.nextDouble() > .95) {
				growBranch(newBranch, angle3);
			}
	}
	
	public void reGrow() {
		for(int i=branches.size()-1; i>=0; i--) {
			branches.remove(i);
		}
		
		for(int i=leaves.size()-1; i>=0; i--) {
			leaves.remove(i);
		}
		
		Random r = new Random();
		noise = new OpenSimplexNoise(r.nextLong());
		growBranch(tree.getBase(), 0);
		
		this.repaint();
		
	}
	
	public ArrayList<Branch> getBranches() {
		return branches;
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        
        Point mouse = new Point(MouseInfo.getPointerInfo().getLocation().x - this.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y - this.getLocationOnScreen().y);
        Branch hovered = null;
        for(Branch branch : branches) {
        	
        	int offset = (int) (wind.getSpeed() / 50 * branch.getPrevious(0));
        	branch.setOffset(offset);
        	
        	g2d.fill(branch.getPoly());
        	
        	if(branch.getPoly().contains(mouse)) {
        		hovered = branch;
        	}
        	
        	settings.setWindSpeed(wind.getSpeed());
	    }
        
        g2d.setColor(Color.GREEN);
        double windSpeed = wind.getSpeed();
        if (windSpeed > 90) windSpeed = 90;
        if (windSpeed < -90) windSpeed = -90;
        for(Leaf leaf : leaves) {
        	g2d.fill(AffineTransform.getRotateInstance(Math.toRadians(-windSpeed), leaf.getBranch().getOffsetEnd().getX1(), leaf.getBranch().getOffsetEnd().getY1()).createTransformedShape(leaf.getShape()));
        }
        
		g2d.setColor(Color.BLUE);
		if(hovered != null) g2d.fill(hovered.getPoly());
        
	}
	
	public class MouseOver implements MouseMotionListener {

		private TreePane pane;
		public MouseOver(TreePane pane) {
			this.pane = pane;
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			for(Branch b : branches) {
				if(b.getPoly().contains(e.getPoint())) {
				}
			}
			pane.repaint();
		}
	}

	public void startWind() {
		Timer timer = new Timer(50, new windTimer(this));
		
		timer.start();
		
	}
	
	public class windTimer implements ActionListener {
		
		private TreePane pane;
		
		public windTimer(TreePane pane) {
			this.pane = pane;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			wind.nextSpeed();
		
			pane.repaint();
		}
		
	}
}