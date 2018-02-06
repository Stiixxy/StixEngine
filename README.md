# StixEngine

## What is StixEngine
StixEngine is an engine I have been working on in my spare time, my goal is to make it easier to make simple sketches in Java. Like what the Javascript P5 library does. I wanted to make it easy to use yet usefull. It will probably be far from optimal and might contain a ton of bugs. Please create an issue if you found a bug.

## Authors
Stijn Veenman (http://stijnveenman.com/)

## Basic setup
Download the lastest release from the [Releases](https://github.com/Stiixxy/StixEngine/tree/master/Releases) folder, add the jar file as an external JAR file to your java project.
The engine is now included. To start using the engine create any class and make it extend either `StixWindow` or `StixRunnable`. Where all classes that extend StixWindow can be able to create and draw on a canvas. And all classes that extend StixRunnable will be able to be started. Both types of classes will **have** to have the function `Start()` and `Update()`. As the names suggest start will be called before the sketch starts running and update will be called every frame. Then create a new object of your class in the main and call the `init()` function of that object. This will start the sketch. 

## Basic code example
Here follows a basic code example where a red ellipse with a black border is drawn on a white background.
```Java
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
		
		StrokeWeight(2);
		Stroke(Color.BLACK);
		Fill(Color.red);

        Ellipse(10, 10, 100, 100);
	}
	
	public static void main(String[] args) {
		new StixWindowTest().init();
	}
	
}

```

## Known bugs
`Currently none`