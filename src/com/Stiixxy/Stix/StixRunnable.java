package com.Stiixxy.Stix;

public abstract class StixRunnable implements Runnable{
	
	public static float deltaTime = 0;
	
	private Thread t;
	private boolean isRunning = false;
	public int fps = 0;
	
	int TARGET_FPS = 60;
	long OPTIMAL_TIME = 1000000000 / TARGET_FPS; 
	
	
	public void Close() {
		isRunning = false;
	}
	
	public void init() {
		t = new Thread(this);
		t.start();
	}
	
	public void run() {
		Start();
		
		long lastLoopTime = System.nanoTime();
		long lastFPSTime = 0;
		
		int fps = 0;
		
		isRunning = true;
		while(isRunning) {
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			
			lastLoopTime = now;
			deltaTime = ((float) updateLength / 1000000000f);
			
			lastFPSTime += updateLength;
			fps++;
			
			if(lastFPSTime > 1000000000) {
				this.fps = fps;
				lastFPSTime = 0;
				fps = 0;
			}

			PreUpdate();
			
			//update screen
			Update();
				
			PostUpdate();

			//Make sure each frame takes at least the optimal time
			//Taking into consideration how long this frame has taken
			try {
				Thread.sleep( (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
			} catch(IllegalArgumentException e) {
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		CloseFrame();
		
		
	}
	
	//Functions implemented by StixWindow
	abstract void CloseFrame();
	abstract void PreUpdate();
	abstract void PostUpdate();
	
	//Implemented by user, start and update are required,
	//Other events are not
	public abstract void Start();
	public abstract void Update();
	public void MouseClicked() {};
	public void MouseReleased() {};
	public void KeyPressed(char Key) {};
	public void KeyReleased(char Key) {};
}
