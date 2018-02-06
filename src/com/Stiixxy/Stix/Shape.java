package com.Stiixxy.Stix;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Shape {
	
	private ArrayList<Point> points = new ArrayList<Point>();
	
	private int[] xpnts;
	private int[] ypnts;
	
	public void Fill(Graphics2D g) {
		if(points.size() > 0) {
			g.fillPolygon(xpnts, ypnts, points.size());
		}
	}
	
	public void Stroke(Graphics2D g) {
		if(points.size() > 0) {
			g.drawPolyline(xpnts, ypnts, points.size());
		}
	}
	
	public void AddPoints(Point p) {
		points.add(p);
		
		xpnts = new int[points.size()];
		ypnts = new int[points.size()];
		
		for(int i = 0; i < points.size(); i++) {
			xpnts[i] = points.get(i).x;
			ypnts[i] = points.get(i).y;
		}
		
	}
	
}
