package main;
import processing.core.*;


public abstract class Canvas {
	
	float[] ratio;
	int pxDensity;
	float xLen;
	float yLen;
	int colorRange = 100;
	PApplet main;
	
	public Canvas(PApplet main, float[] ratio, int pxDensity) {
		this.ratio = ratio;
		this.pxDensity = pxDensity;
		this.xLen = (pxDensity * ratio[0]) / 2;
		this.yLen = (pxDensity * ratio[1]) / 2;
		this.main = main;
	}
	
	public void settings() {
		main.size((int)(pxDensity * ratio[0]), (int)(pxDensity * ratio[1]));
	}
	
	public void setup() {
		main.colorMode(PApplet.HSB, colorRange);
		main.background(0, 0, colorRange);
		main.frameRate(100000);
	}
	
	abstract void mouseHandler(float x, float y);
	abstract void draw();
}
