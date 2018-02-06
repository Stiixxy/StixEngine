package com.Stiixxy.Stix;

public class Vector2D {
	
	//TODO: Add vector.dist
	
	public float x, y;
	
	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D() {
		this.x = 0;
		this.y = 0;
	}
	
	public void add(Vector2D other) {
		this.x += other.x;
		this.y += other.y;
	}
	
	public void mult(float num) {
		this.x *= num;
		this.y *= num;
	}
	
	public void sub(Vector2D other) {
		this.x -= other.x;
		this.y -= other.y;
	}
	
	public float mag() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public float dist(Vector2D other) {
		Vector2D newVec = Vector2D.sub(this, other);
		return newVec.mag();
	}
	
	public void normalise() {
		float magni = mag();
		this.x /= magni;
		this.y /= magni;
	}
	
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
	
	public static Vector2D Add(Vector2D v1, Vector2D v2) {
		Vector2D copy = v1.copy();
		copy.add(v2);
		return copy;
	}
	
	public static Vector2D Mult(Vector2D v, float num) {
		Vector2D copy = v.copy();
		copy.mult(num);
		return copy;
	}
	
	public static Vector2D sub(Vector2D v1, Vector2D v2) {
		Vector2D copy = v1.copy();
		copy.sub(v2);
		return copy;
	}
	
	public static Vector2D Normalise(Vector2D v) {
		Vector2D copy = v.copy();
		copy.normalise();
		return copy;
	}
}
