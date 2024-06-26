import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Frame extends JFrame implements MouseMotionListener {

	private ArrayList<DataPoint> data = new ArrayList<>();
	private ArrayList<Point> points = new ArrayList<>();
	
	public static void main(String[] args) {
		new Frame();
	}
	
	public Frame() {
		ML.findHyperparameters(data);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700, 700);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		populate();
		setVisible(true);
		addMouseMotionListener(this);
	}
	
	@Override
	public void paint(Graphics g) {		
		Color hit = Color.GREEN.darker().darker().darker().darker();
		Color nonHit = Color.RED.darker().darker().darker().darker();
		
		if(ML.SHOW_SVN) {
			for(double i = 0; i < ML.RESOLUTION; i++) {
				for(double j = 0; j < ML.RESOLUTION; j++) {
					double x = (i / ML.RESOLUTION - 0.5) * 2 - ML.RESOLUTION / getWidth() / 2;
					double y = (j / ML.RESOLUTION - 0.5) * 2 - ML.RESOLUTION / getHeight() / 2;
					g.setColor(ML.kernel(x, y) < f(x, y) ? hit : nonHit);
					g.fillRect((int) i * getWidth() / ML.RESOLUTION, (int) j * getHeight() / ML.RESOLUTION, getWidth() / ML.RESOLUTION, getHeight() / ML.RESOLUTION);
				}
			}
		}
		
		if(ML.SHOW_DATA) {
			for(DataPoint point : data) {
				g.setColor(point.hasTrait ? Color.GREEN : Color.RED);
				g.fillOval((int) (getWidth() * (point.cords.x + 1) / 2) - 2, (int) (getHeight() * (point.cords.y + 1) / 2) - 2, 4, 4);
			}
		}
		g.setColor(Color.GRAY);
		g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
		g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
	}
	
	private void populate() {
		points.add(new Point(0.2, 0, 0));
		
		for(int i = 0; i < 1000; i++) {
			DataPoint point = new DataPoint();
			point.cords = Point.random();
			
			for(Point p : points) {
				double y = point.cords.y * (point.cords.y > 0 ? 1 : 0.5);
				if(p.dist(point.cords.x, y) < 0.4) {
					point.hasTrait = true;
					break;
				}
			}
			data.add(point);
		}
	}
	
	private double f(double x, double y) {
		return ML.m * x + ML.m * y + ML.b;
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!ML.TOOLTIP_INFO) return;
		double x = ((e.getX() + ML.RESOLUTION / 2.0) / getWidth() - 0.5) * 2;
		double y = ((e.getY() + ML.RESOLUTION / 2.0) / getHeight() - 0.5) * 2;
		System.out.println(" (" + x + ", " + y + ") | f: " + f(x, y) + ", Kernel: " + ML.kernel(x, y));
	}
}
