public class Point {
	public double x, y, z;
	
	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double dist(double x, double y) {
		return dist(x, y, z);
	}
	
	public double dist(double x, double y, double z) {
		return Math.sqrt((this.x - x) * (this.x - x) + (this.y - y) * (this.y - y) + (this.z - z) * (this.z - z));
	}
	
	public static Point random() {
		return new Point((Math.random() - 0.5) * 2, (Math.random() - 0.5) * 2, 0);
	}
}