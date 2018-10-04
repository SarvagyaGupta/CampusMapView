package hw8;

/**
 * MyPoint, p, is an immutable representation of the nodes in the campus MyGraph
 * 
 * @author Sarvagya Gupta
 */
public class MyPoint implements Comparable<MyPoint> {
	// Representation Invariant:
	// 		x != null && y != null
	
	// Abstraction Function:
	//		AF(this):
	//			X Coordinate: this.getX()
	//			Y Coordinate: this.getY()
	
	/** Stores the x coordinate of this MyPoint */
	private final Double x;
	/** Stores the y coordinate of this MyPoint */
	private final Double y;
	
	/**
	 * Consrtucts the MyPoint with the given x and y coordinates
	 * 
	 * @param x The x coordinate of the MyPoint
	 * @param y The y coordinate of the MyPoint
	 */
	public MyPoint(Double x, Double y) {
		this.x = x;
		this.y = y;
		checkRep();
	}
	
	/**
	 * @return The x coordinate of the MyPoint
	 */
	public Double getX() {
		return x;
	}

	/**
	 * @return The y coordinate of the MyPoint
	 */
	public Double getY() {
		return y;
	}

	/**
	 * Compares the MyPoint by the x coordinate first, and then the y coordinate
	 * to break any ties
	 * 
	 * @param other The MyPoint to which this has to be compared
	 * @return An Integer less than 0 if this &lt; other, 0 if this = other, or
	 * 			greater than 0 if this &gt; other
	 */
	@Override
	public int compareTo(MyPoint other) {
		int val = getX().compareTo(other.getX());
		if (val == 0) {
			return getY().compareTo(other.getY());
		}
		return val;
	}
	
	/**
	 * @param obj The object to which this has to be compared to.
	 * @return Returns true if obj instanceof MyPoint
	 * 			&amp;&amp; this.getX().equals(obj.getX()) 
	 * 			&amp;&amp; this.getY().equals(obj.getY()), 
	 * 			false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MyPoint))
			return false;
		MyPoint other = (MyPoint) obj;
		return getX().equals(other.getX()) && getY().equals(other.getY());
	}

	/**
	 * @return Returns the hashcode of this
	 */
	@Override
	public int hashCode() {
		return getX().hashCode() * 19 + getY().hashCode();
	}
	
	/**
	 * Asserts the invarint of the MyPoint class
	 */
	private void checkRep() {
		assert (x != null && y != null);
	}
	
	@Override
	public String toString() {
		return "(" + round(getX()) + ", " + round(getY()) + ")";
	}
	
	/**
	 * Rounds the number, val, to the nearest integer
	 * 
	 * @param val The number to be rounded
	 * @return The rounded number
	 */
	private static long round(double val) {
		return Math.round(val);
	}
}
