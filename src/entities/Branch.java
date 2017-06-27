package entities;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Branch {
	
	private Line2D defaultEnd = new Line2D.Double();
	private Line2D offsetEnd = new Line2D.Double();
	private Line2D base = new Line2D.Double();
	private Branch baseBranch;
	private ArrayList<Branch> offshoots = new ArrayList<Branch>();
	
	private int xOffset;
	
	public Branch(Line2D end, Branch prevBranch) {
		baseBranch = prevBranch;
		setEnd(end);
		setOffsetEnd(end);
	}
	
	public int getPrevious(int soFar) {
		if(baseBranch != null) {
			return(baseBranch.getPrevious(soFar + 1));
		} else {
			return soFar;
		}
	}
	
	public int getOffsets(int soFar) {
		if(baseBranch != null) {
			return(baseBranch.getOffsets(soFar + xOffset));
		} else {
			return soFar;
		}
	}
	
	public void setOffset(int offset) {
		this.xOffset = offset;
		
		setOffsetEnd(new Line2D.Double(new Point2D.Double(defaultEnd.getX1() + offset, defaultEnd.getY1()), new Point2D.Double(defaultEnd.getX2() + offset, defaultEnd.getY2())));
	}
	
	public ArrayList<Branch> getOffshoots() {
		return offshoots;
	}

	public void addOffshoot(Branch b) {
		offshoots.add(b);
		if(baseBranch != null) baseBranch.addOffshoot(b);
	}
	
	public Polygon getPoly() {
		return new Polygon(new int[]{(int)offsetEnd.getX1(), (int)offsetEnd.getX2(), (int)getBase().getX2(), (int)getBase().getX1()}, new int[]{(int)offsetEnd.getY1(), (int)offsetEnd.getY2(), (int)getBase().getY2(), (int)getBase().getY1()}, 4);
	}
	
	public Line2D getBase() {
		if(baseBranch != null) base = baseBranch.getOffsetEnd();
		return base;
	}
	
	public void setBase(Line2D.Double newBase) {
		base = newBase;
	}
	
	public Line2D getEnd() {
		return defaultEnd;
	}
	
	public Line2D getOffsetEnd() {
		return offsetEnd;
	}
	
	public void setOffsetEnd(Line2D offEnd) {
		this.offsetEnd = offEnd;
	}
	
	public void setEnd(Line2D end) {
		this.defaultEnd = end;
	}
	
	public double lineLength(Line2D line) {
		return Math.abs(Math.hypot(line.getX1()-line.getX2(), line.getY1()-line.getY2()));
	}
	
	public Point getMidpoint(Point2D one, Point2D two) {
		return new Point((int)(one.getX() + two.getX())/2, (int)(one.getY() + two.getY())/2);
	}
	
	public Point getAnchor() {
		return getMidpoint(base.getP1(), base.getP2());
	}
	
}
