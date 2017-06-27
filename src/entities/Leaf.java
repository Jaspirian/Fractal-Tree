package entities;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Leaf {
	
	Branch b;
	int length;
	
	public Leaf(int length, Branch b) {
		this.b = b;
		
		this.length = length;
	}
	
	public Branch getBranch() {
		return b;
	}
	
	public int getLength() {
		return length;
	}
	
	public Shape getShape() {
		return new Ellipse2D.Double(b.getOffsetEnd().getX1(), b.getOffsetEnd().getY1(), length/2, length);
	}

}
