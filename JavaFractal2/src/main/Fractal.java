package main;
import processing.core.*;

public class Fractal extends Canvas{
	PApplet main;
	double color = 0;
	int maxItt;
	float modulate = -50;
	float zoom = (float)1.2;
	float xOffset = 0;
	float yOffset = 0;
	
	Fractal(PApplet main, float[] ratio, int pxDensity, int maxItt) {
		super(main, ratio, pxDensity);
		this.main = main;
		this.maxItt = maxItt;
	}
	
	public void draw() {
		
		for (float x = -xLen; x < xLen; x++) {
			for (float y = -yLen; y < yLen; y++) {
				if (checkPx(translatePx( new float[] { x, y }))) {
					main.set((int)(x + xLen), (int)(y + yLen), main.color(0, 0, 0));
				} else {
					main.set((int)(x + xLen), (int)(y + yLen), main.color((int)color, 1000, 1000));
				}
			}
		}
		modulate ++;
	}
	
	private float[] translatePx(float[] pixel) {
		return new float[] {(zoom * (pixel[0] + xOffset)) / pxDensity, (zoom * (pixel[1] + yOffset)) / pxDensity};
	}
	
	private boolean checkPx(float[] pixel) {
		
		float[] nextItt = pixel;
		for (int i = 0; i < maxItt; i++) {
			
			nextItt = comPow(nextItt, 2);
			nextItt[0] += pixel[0];
			nextItt[1] += pixel[1];
			if (Math.pow(nextItt[0], 2) + Math.pow(nextItt[1], 2) >= pxDensity) {
                float colorNum = 60;
                i = (int)((i + Math.abs(modulate)) % colorNum);
                color = (colorRange * (i / colorNum));
				return false;
			}
		}
		return true;
	}
	
	private float[] comPow(float[] point, int power) { 
		
		if (power > 2) point = comPow(point, power - 1);
		
        return new float[] { (float)(Math.pow(point[0], 2) - Math.pow(point[1], 2)), 
        				(float)(point[0] * point[1] * 2)};
    }
	
	public void mouseHandler(float x, float y) {
		float[] coord = translatePx(new float[] {x -xLen, y -yLen});
		zoom /= 2;
		xOffset = coord[0] * pxDensity / zoom;
		yOffset = coord[1] * pxDensity / zoom;
		main.loop();
		System.out.println(coord[0] + " " + coord[1]);
	}
	
}


