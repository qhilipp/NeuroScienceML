import java.util.ArrayList;

public class ML {
	
	public static double m = 0, b = 1;
	public static final int BLOCKS = 5; 
	
	// Optional: Also use a parameterized kernel function
	// public static double k = 0;
	
	// TODO: Implement the decision function for the SVN algorithm
	public static boolean hasTrait(double x, double y) {
		return false;
	}
	
	// TODO: Implement the hyperplane function
	public static double hyperPlane(double x, double y, double z) {
		return 0;
	}
	
	// TODO: Find a suitable kernel function
	public static double kernel(double x, double y) {
		return 2 - Math.pow(new Point(x, y, 0).dist(0, 0), 2);
	}
	
	// TODO: Find the hyperparameters, you might want to enable SHOW_SVN for this
	public static void findHyperparameters(ArrayList<DataPoint> data) {
		
	}
	
	// TODO: Implement the Cross Validation algorithm
	public static double crossValidate(ArrayList<DataPoint> trainingData, ArrayList<DataPoint> testData) {
		return 0;
	}
	
	// Configuration for the visuals
	public static final int SIZE = 700;
	public static final int RESOLUTION = 100;
	public static final boolean TOOLTIP_INFO = false;
	public static final boolean SHOW_DATA = true;
	public static final boolean SHOW_SVN = true;
	public static final boolean HIGHLIGHT_TEST_DATA = false;
	public static final double POINT_SCALE = 0.0;
	public static final boolean MONOTONE = false;
	
}