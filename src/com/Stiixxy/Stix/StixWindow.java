package com.Stiixxy.Stix;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JFrame;

public abstract class StixWindow extends StixRunnable {
	
	
	public static int width = 0, height = 0;
	public static int mouseX = 0, mouseY = 0;
	
	public static boolean MousePressed = false;
	
	private static JFrame frame;
	private static Canvas canvas;
	private static BufferStrategy bs;
	private static Graphics2D g;
	
	private static Random r;
	private static Vector<Character> pressedKeys = new Vector<Character>();
	private static Stack<AffineTransform> translations = new Stack<AffineTransform>();
	
	private static Color fillColor = Color.white;
	private static Color strokeColor = Color.white;
	
	private static Shape currentShape = null;
	
	public void BeginShape() {
		if(currentShape != null) {
			System.out.println("ERROR: You are already creating a shape. Can't create 2 shapes at the same time");
			return;
		}
		currentShape = new Shape();
	}
	
	public void EndShape() {
		if(currentShape == null) {
			System.out.println("ERROR: You haven't started creating a shape.");
			return;
		}
		if(fillColor != null) {
			g.setColor(fillColor);
			currentShape.Fill(g);
		}
		if(strokeColor != null) {
			g.setColor(strokeColor);
			currentShape.Stroke(g);
		}
		currentShape = null;
	}
	
	public static void Fill(Color c) {
		fillColor = c;
	}
	
	public static void NoFill() {
		fillColor = null;
	}
	
	public static void Stroke(Color c) {
		strokeColor = c;
	}
	
	public static void NoStroke() {
		strokeColor = null;
	}
	
	public static void Push() {
		translations.push(new AffineTransform (g.getTransform()));
	}
	
	public static void Pop() {
		if(translations.size() == 0) {
			System.out.println("ERROR: Tried to pop an translation while no translations were left");
			return;
		}
		g.setTransform(translations.pop());
	}
	
	
	public static void Background(Color c) {
		if(frame == null) {
			System.out.println("ERROR: Tried to set background without creating a canvas");
			return;
		}
		g.setColor(c);
		g.fillRect(0, 0, width, height);
	}
	
	public void FPS(int fps) {
		TARGET_FPS = fps;
		OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	}
	
	public static void StrokeWeight(int t) {
		g.setStroke(new BasicStroke(t));
	}
	
	public static Graphics GetGraphics() {
		if(g == null) {
			System.out.println("ERROR: Tried to get graphics without creating a canvas first");
			return null;
		}
		return g;
	}
	
	public static void Title(String title) {
		if(frame == null) {
			System.out.println("ERROR: Tried to set title without creating a canvas first");
			return;
		}
		frame.setTitle(title);
	}
	
	public static void Translate(int x, int y) {
		g.translate(x, y);
	}
	
	public static void Rotate(double angle) {
		//We want to rotate in degrees, g uses pi, 1 pi is 180 degrees
		
		g.rotate(angle / 180 * Math.PI);
	}
	
	public static void SeedRandom(long seed) {
		r.setSeed(seed);
	}
	
	public static float Random() {
		return r.nextFloat();
	}
	
	public static int Random(int x) {
		return r.nextInt(x);
	}
	
	public static int Random(int x, int y) {
		return r.nextInt(y - x) + x;
	}
	
	public boolean IsKeyPressed(char c) {
		if(pressedKeys.contains(c)) {
			return true;
		}else {
			return false;
		}
	}
	
	public float Map(float value, float begin, float end, float firstValue, float lastValue) {
		
		float diff = end - begin;
		float perc = value / diff;
		diff = lastValue - firstValue;
		float finalValue = perc * diff;
		finalValue += firstValue;
		
		return finalValue;
	}
	
	public void Point(int x, int y) {
		if(g == null) {
			System.out.println("ERROR: Tried to draw a point without creating a canvas first");
			return;
		}
		if(currentShape != null) {
			currentShape.AddPoints(new Point(x,y));
			return;
		}
		if(strokeColor != null) {
			g.setColor(strokeColor);
			g.drawLine(x, y, x, y);
		}
	}
	
	public void Line(int x1, int y1, int x2, int y2) {
		if(g == null) {
			System.out.println("ERROR: Tried to draw a line without creating a canvas first");
			return;
		}
		if(strokeColor != null) {
			g.setColor(strokeColor);
			g.drawLine(x1, y1, x2, y2);
		}
	}
	
	public void Triangle(Vector2D v1, Vector2D v2, Vector2D v3) {
		if(g == null) {
			System.out.println("ERROR: Tried to draw a triangle without creating a canvas first");
			return;
		}
		int[] xPnts = {(int) v1.x, (int) v2.x, (int) v3.x};
		int[] yPnts = {(int) v1.y, (int) v2.y, (int) v3.y};
		if(fillColor != null) {
			g.setColor(fillColor);
			g.fillPolygon(xPnts, yPnts, 3);
		}
		if(strokeColor != null) {
			g.setColor(strokeColor);
			g.drawPolygon(xPnts, yPnts, 3);
		}
	}
	
	public static boolean IsFocused() {
		if(frame == null) {
			System.out.println("ERROR: Tried to get focused without creating a canvas first");
			return false;
		}
		return frame.isFocused();
	}
	
	public static void Rect(int x, int y, int width, int height) {
		if(g == null) {
			System.out.println("ERROR: Tried to draw a rect without creating a canvas first");
			return;
		}
		if(fillColor != null) {
			g.setColor(fillColor);
			g.fillRect(x, y, width, height);
		}
		if(strokeColor != null) {
			g.setColor(strokeColor);
			g.drawRect(x, y, width, height);
		}
	}
	
	public static void Ellipse(int x, int y, int width, int height) {
		if(g == null) {
			System.out.println("ERROR: Tried to draw a ellipse without creating a canvas first");
			return;
		}
		if(fillColor != null) {
			g.setColor(fillColor);
			g.fillOval(x, y, width, height);
		}
		if(strokeColor != null) {
			g.setColor(strokeColor);
			g.drawOval(x, y, width, height);
		}
	}
	
	public void CreateCanvas(int w, int h) {
		width = w;
		height = h;
		
		frame = new JFrame();
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Close();
			}
		});
		
		Dimension d = frame.getSize();
		canvas = new Canvas();
		canvas.setPreferredSize(d);
		canvas.setMaximumSize(d);
		canvas.setMinimumSize(d);
		
		canvas.addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
			
			public void mousePressed(MouseEvent e) {
				MousePressed = true;
				MouseClicked();
			}

			public void mouseReleased(MouseEvent e) {
				MousePressed = false;
				MouseReleased();
			}
		});
		
		canvas.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			
			public void keyPressed(KeyEvent e) {
				pressedKeys.add(e.getKeyChar());
				KeyPressed(e.getKeyChar());
			}
			
			public void keyReleased(KeyEvent e) {
				pressedKeys.remove((Character) e.getKeyChar());	
				KeyReleased(e.getKeyChar());
			}
			
		});
		
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();
		g = (Graphics2D) bs.getDrawGraphics();
		
	}
	
	void CloseFrame() {
		if(frame != null) {
			frame.dispose();
		}
	}
	
	void PreUpdate() {
		//called before the runnable is updated
		
		//Reset all translations
		AffineTransform t = new AffineTransform();
		if(g != null) g.setTransform(t);
		
		//Clear all translations
		translations.clear();
		
		//Set mouse position
		Point p = canvas.getMousePosition();
		if(p != null) {
			mouseX = p.x;
			mouseY = p.y;
		}
	}
	
	void PostUpdate() {
		//Show the screen
		if(bs != null) bs.show();
		
		if(currentShape != null) {
			System.out.print("WARMING: Unfinished shape left, clearing shape");
			currentShape = null;
		}
	}
	
}
