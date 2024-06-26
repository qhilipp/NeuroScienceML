import java.util.ArrayList;

public class ML {

	public static final int RESOLUTION = 100;
	
	// TODO: Hier eine passende Kernel Function auswählen
	public static double kernel(double x, double y) {
		return new Point(x, y, 0).dist(0.4, -0.5);
	}
	
	// TODO: Hier die Hyperplane Function auswählen
	public static double f(double x, double y) {
		double m = 1;
		double b = 0;
//		return m * x + m * y + b;
		return 0.3;
	}
	
	// TODO: Hier den Cross Validation Algorithmus implementieren
	public static double crossValidate(int n, ArrayList<DataPoint> data) {
		return 0;
	}
	
}