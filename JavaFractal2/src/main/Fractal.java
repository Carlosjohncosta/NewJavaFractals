package main;
import processing.core.*;

public class Fractal extends Canvas{
	PApplet main;
	double color = 0;
	int maxItt;
	float modulate = 0;
	float zoom = (float)1.2;
	float preXOffset = 0;
	float xOffset = 0;
	float yOffset = 0;
	float[][] saveState = new float[(int)ratio[0] * pxDensity][(int)ratio[1] * pxDensity];
	public float colorNum = 30;
	boolean found = false;
	boolean firstRun = true;
	
	Fractal(PApplet main, float[] ratio, int pxDensity, int maxItt) {
		super(main, ratio, pxDensity);
		this.main = main;
		this.maxItt = maxItt;
		for (int x = 0; x < 2 * xLen; x++) {
			for (int y = 0; y < 2 * yLen; y++) {
				saveState[x][y] = -1;
			}
		}
	}
	
	public void draw() {
		if(!found) {
			for (float x = -xLen; x < xLen; x++) {
				for (float y = -yLen; y < yLen; y++) {
					saveState[(int)(x + xLen)][(int)(y + yLen)] = checkPx(new float[] { x, y });
				}
			}
			found = true;
			firstRun = false;
		} else {
			for (float x = -xLen; x < xLen; x++) {
				for (float y = -yLen; y < yLen; y++) {
					if (saveState[(int)(x + xLen)][(int)(y + yLen)] < 0) {
						main.set((int)(x + xLen), (int)(y + yLen), main.color(0, 0, 0));
					} else {
						main.set((int)(x + xLen), (int)(y + yLen), 
								main.color(getColor(saveState[(int)(x + xLen)][(int)(y + yLen)]), 1000, 1000));
					}
				}
			}
			modulate+= 1;
		}
	}
	
	private float[] translatePx(float[] pixel) {
		return new float[] {(zoom * (pixel[0] + xOffset)) / pxDensity, (zoom * (pixel[1] + yOffset)) / pxDensity};
	}
	
	private float checkPx(float[] pixel) {
		pixel = translatePx(pixel);
		float[] nextItt = pixel;
		for (int i = 0; i < maxItt; i++) {
			nextItt = comPow(nextItt, 2);
			nextItt[0] += pixel[0];
			nextItt[1] += pixel[1];
			if (Math.pow(nextItt[0], 2) + Math.pow(nextItt[1], 2) >= pxDensity) {
				return i;
			}
		}
		return -1;
	}
	
	private float getColor(float itterations) {
        return (colorRange * ((int)(itterations + Math.abs(modulate)) % colorNum) / colorNum);
	}
	
	private float[] comPow(float[] point, int power) { 
		
		if (power > 2) point = comPow(point, power - 1);
		
        return new float[] { (float)(Math.pow(point[0], 2) - Math.pow(point[1], 2)), 
        				(float)(Math.abs(point[0]) * Math.abs(point[1]) * modulate)};
    }
	
	public void mouseHandler(float x, float y) {
		float[] coord = translatePx(new float[] {x -xLen, y -yLen});
		zoom /= 2;
		xOffset = coord[0] * pxDensity / zoom;
		yOffset = coord[1] * pxDensity / zoom;
		found = false;
	}
	
}


