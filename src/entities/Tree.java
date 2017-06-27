package entities;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.Random;

public class Tree {
	
	private long seed;
	private Branch base;
	
	private int maxBranches;
	private int minBranches;
	private int maxWidth;
	private int maxLength;
	private double branchOccurrence;
	private double leafOccurrence;
	private double treeDensity;
	private int treeCurve;
	private Point startPoint;
	
	public Tree(int maxBranches, int minBranches, int maxWidth, int maxLength, double branchOccurrence, double leafOccurrence, double treeDensity, int treeCurve, Point startPoint) {
		this.setMaxBranches(maxBranches);
		this.setMinBranches(minBranches);
		this.maxWidth = maxWidth;
		this.maxLength = maxLength;
		this.setBranchOccurrence(branchOccurrence);
		this.setLeafOccurrence(leafOccurrence);
		this.setTreeDensity(treeDensity);
		this.setTreeCurve(treeCurve);
		this.startPoint = startPoint;
		
		Random r = new Random();
		setSeed(r.nextLong());
		
		setBase();
	}

	public int getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
		setBase();
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
		setBase();
	}

	public double getBranchOccurrence() {
		return branchOccurrence;
	}

	public void setBranchOccurrence(double branchOccurrence) {
		this.branchOccurrence = branchOccurrence;
	}

	public double getLeafOccurrence() {
		return leafOccurrence;
	}

	public void setLeafOccurrence(double leafOccurrence) {
		this.leafOccurrence = leafOccurrence;
	}

	public double getTreeDensity() {
		return treeDensity;
	}

	public void setTreeDensity(double treeDensity) {
		this.treeDensity = treeDensity;
	}

	public int getTreeCurve() {
		return treeCurve;
	}

	public void setTreeCurve(int treeCurve) {
		this.treeCurve = treeCurve;
	}

	public int getMaxBranches() {
		return maxBranches;
	}

	public void setMaxBranches(int maxBranches) {
		this.maxBranches = maxBranches;
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	public int getMinBranches() {
		return minBranches;
	}

	public void setMinBranches(int minBranches) {
		this.minBranches = minBranches;
	}
	
	public void setBase() {
		base = new Branch(new Line2D.Double(new Point(startPoint.x - maxWidth/2, startPoint.y - maxLength), new Point(startPoint.x + maxWidth/2, startPoint.y - maxLength)), null);
		base.setBase(new Line2D.Double(new Point(startPoint.x - maxWidth/2, startPoint.y), new Point(startPoint.x + maxWidth/2, startPoint.y)));
	}

	public Branch getBase() {
		return base;
	}

	public void setStartPoint(Point point) {
		this.startPoint = point;
		
		setBase();
		
	}

}
