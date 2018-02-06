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

public abstract class StixWindow implements Runnable{
	
	public float deltaTime;
	public int FPS = 0;
	public int width, height;
	public int mouseX, mouseY;
	
	public boolean MousePressed = false;
	
	JFrame frame;
	Canvas canvas;
	BufferStrategy bs;
	Graphics2D g;
	
	Thread t;
	
	Random r = new Random();
	Vector<Character> pressedKeys = new Vector<Character>();
	Stack<AffineTransform> translations = new Stack<AffineTransform>();
	
	Color fillColor = Color.white;
	Color strokeColor = Color.white;
	
	int TARGET_FPS = 60;
	long OPTIMAL_TIME = 1000000000 / TARGET_FPS;   
	
	boolean isRunning = true;
	
	public StixWindow GetWindow() {
		return this;
	}
	
	public void FPS(int fps) {
		TARGET_FPS = fps;
		OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	}
	
	public void Close() {
		isRunning = false;
	}
	
	public void StrokeWeight(int t) {
		g.setStroke(new BasicStroke(t));
	}
	
	public void Fill(Color c) {
		fillColor = c;
	}
	
	public void Stroke(Color c) {
		strokeColor = c;
	}
	
	public void NoFill() {
		fillColor = null;
	}
	
	public void NoStroke() {
		strokeColor = null;
	}
	
	public void Push() {
		translations.push(new AffineTransform (g.getTransform()));
	}
	
	public void Pop() {
		if(translations.size() == 0) {
			System.out.println("ERROR: Tried to pop an translation while no translations were left");
			return;
		}
		g.setTransform(translations.pop());
	}
	
	public void Translate(int x, int y) {
		g.translate(x, y);
	}
	
	public void Rotate(double angle) {
		//We want to rotate in degrees, g uses pi, 1 pi is 180 degrees
		
		g.rotate(angle / 180 * Math.PI);
	}
	
	public void SeedRandom(long seed) {
		r.setSeed(seed);
	}
	
	public float Random() {
		return r.nextFloat();
	}
	
	public int Random(int x) {
		return r.nextInt(x);
	}
	
	public int Random(int x, int y) {
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
	
	public void Rect(int x, int y, int width, int height) {
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
	
	public void Ellipse(int x, int y, int width, int height) {
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
	
	public void Background(Color c) {
		if(frame == null) {
			System.out.println("ERROR: Tried to set background without creating a canvas first");
			return;
		}
		g.setColor(c);
		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
	}
	
	public void Title(String title) {
		if(frame == null) {
			System.out.println("ERROR: Tried to set title without creating a canvas first");
			return;
		}
		frame.setTitle(title);
	}
	
	public StixWindow() {
		t = new Thread(this);
		t.start();
	}
	
	public Graphics GetGraphics() {
		if(g == null) {
			System.out.println("ERROR: Tried to get graphics without creating a canvas first");
			return null;
		}
		return g;
	}
	
	public void CreateCanvas(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		frame = new JFrame();
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		frame.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		        isRunning = false;
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

	@Override
	public void run() {
		//Start method
		Start();
		
		long lastLoopTime = System.nanoTime();
		long lastFPSTime = 0;
		 
		int fps = 0;
		
		while(isRunning) {
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			
			lastLoopTime = now;
			deltaTime = ((float) updateLength / 1000000000f);
			
			//Update frame counter
			lastFPSTime += updateLength;
			fps++;
			
			if(lastFPSTime > 1000000000) {
				FPS = fps;
				lastFPSTime = 0;
				fps = 0;
			}
			
			//Do some behind the scene logic
			//Reset the translations
			AffineTransform t = new AffineTransform();
			if(g != null) g.setTransform(t);
			
			//Make sure that there are no push and pops left
			translations.clear();
			
			//Save current mouse position
			Point p = canvas.getMousePosition();
			if(p != null) {
				mouseX = p.x;
				mouseY = p.y;	
			}
						
			//Update the screen
			Update();
			if(bs != null) bs.show();
			
			//Make sure each frame takes at least the optimal time
			//Taking into consideration how long this frame has taken
			try {
				Thread.sleep( (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
			} catch(IllegalArgumentException e) {
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		if(frame != null) {
			frame.dispose();
		}
	}

	public abstract void Start();
	public abstract void Update();
	public void MouseClicked() {};
	public void MouseReleased() {};
	public void KeyPressed(char key) {};
	public void KeyReleased(char key) {};

}
