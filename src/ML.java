import java.util.ArrayList;

public class ML {
	
	public static double m = 0, b = 0.3;
	
	// Optional: Also use a parameterized kernel function
	// public static double k = 0;
	
	// TODO: Find a suitable kernel function
	public static double kernel(double x, double y) {
		return Math.pow(new Point(x, y, 0).dist(0, 0), 2);
	}
	
	// TODO: Find the hyperparameters
	public static void findHyperparameters(ArrayList<DataPoint> data) {
		
	}
	
	// TODO: Implement the Cross Validation algorithm
	public static double crossValidate(int n, ArrayList<DataPoint> data) {
		return 0;
	}
	
	// Configuration for the visuals
	public static final int RESOLUTION = 100;
	public static final boolean TOOLTIP_INFO = true;
	public static final boolean SHOW_DATA = true;
	public static final boolean SHOW_SVN = false;
	
}