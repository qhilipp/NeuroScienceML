import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Frame extends JFrame implements MouseMotionListener, KeyListener {

	private ArrayList<DataPoint> data = new ArrayList<>();
	private ArrayList<Point> points = new ArrayList<>();
	private int block = 0;
	
	public static void main(String[] args) {
		new Frame();
	}
	
	public Frame() {
		printStats();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700, 700);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		populate();
		setVisible(true);
		addMouseMotionListener(this);
		addKeyListener(this);
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
		} else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		
		g.setColor(Color.GRAY);
		g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
		g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
		
		if(ML.SHOW_DATA) {
			if(ML.HIGHLIGHT_TEST_DATA) {
				for(DataPoint point : getTestData(block)) {
					if(!ML.MONOTONE) g.setColor(Color.GRAY);
					drawPoint(g, point, true);
					g.fillOval((int) (getWidth() * (point.cords.x + 1) / 2) - 4, (int) (getHeight() * (point.cords.y + 1) / 2) - 4, 8, 8);
				}
			}
			for(DataPoint point : data) {
				if(!ML.MONOTONE) g.setColor(point.hasTrait ? Color.GREEN : Color.RED);
				drawPoint(g, point, false);
			}
		}
		
		g.setColor(Color.GRAY);
		g.drawString("Block: " + block, 5, 25);
	}
	
	private void drawPoint(Graphics g, DataPoint p, boolean outline) {
		int s = (int) (ML.kernel(p.cords.x, p.cords.y) * ML.POINT_SCALE) + (outline ? 7 : 3);
		System.out.println(s);
		g.fillOval((int) ((getWidth() * (p.cords.x + 1) - s) / 2), (int) ((getHeight() * (p.cords.y + 1) - s) / 2), s, s);
	}
	
	private void printStats() {
		System.out.println("<==============================>");
		double sum = 0;
		for(int i = 0; i < ML.BLOCKS; i++) sum += printStats(i);
		System.out.println("Avg Cross Validation: " + sum / ML.BLOCKS);
		System.out.println("<==============================>\n");
	}
	
	private double printStats(int i) {
		long startTime = System.currentTimeMillis();
		ML.findHyperparameters(getTrainingData(i));
		long duration = System.currentTimeMillis() - startTime;
		double validation = ML.crossValidate(getTrainingData(i), getTestData(i));
		
		System.out.println("Cross Validation (" + i + "): " + validation);
		System.out.println("Duration: " + duration + "ms");
		System.out.println("-------------");
		
		return validation;
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
	
	private ArrayList<DataPoint> getTestData(int block) {
		ArrayList<DataPoint> testData = new ArrayList<>();
		for(int i = data.size() / ML.BLOCKS * block; i < data.size() / ML.BLOCKS * (block + 1); i++) {
			testData.add(data.get(i));
		}
		return testData;
	}
	
	private ArrayList<DataPoint> getTrainingData(int block) {
		ArrayList<DataPoint> trainingData = new ArrayList<>(data);
		for(DataPoint testPoint : getTestData(block)) trainingData.remove(testPoint);
		return trainingData;
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

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) block = (block + 1) % ML.BLOCKS;
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) block = (block - 1 + ML.BLOCKS) % ML.BLOCKS;
		else return;
		invalidate();
		repaint();
		printStats(block);
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
