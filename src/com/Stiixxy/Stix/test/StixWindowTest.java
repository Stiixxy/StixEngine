package com.Stiixxy.Stix.test;

import java.awt.Color;

import com.Stiixxy.Stix.*;

public class StixWindowTest extends StixWindow{
	
	@Override
	public void Start() {
		CreateCanvas(400, 400);
		
	}
	
	
	@Override
	public void Update() {
		Background(Color.white);
		
		StrokeWeight(3);
		Stroke(Color.BLACK);
		Fill(Color.red);
		
		
		Rect(10 , 10, 100, 100);
		
		
		
	}
	
	public static void main(String[] args) {
		new StixWindowTest().init();
	}
	
}
