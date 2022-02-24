package main;
import processing.core.*;

public class Main extends PApplet{
	Canvas fractal = new Fractal(this, new float[] {2, 1}, 900);
	
	public static void main(String[] args) {
		PApplet.main("main.Main");
	}
	
	public void settings() { fractal.settings(); }
	public void setup() { fractal.setup(); }
	public void draw() { fractal.draw(); }
	
}
