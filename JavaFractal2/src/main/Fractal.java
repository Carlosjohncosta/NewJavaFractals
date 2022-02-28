package main;
import processing.core.*;

public class Fractal extends Canvas{
	PApplet main;
	double color = 0;
	int maxItt;
	float modulate = 0;
	float zoom = (float)50;
	float preXOffset = 0;
	float xOffset = 0;
	float yOffset = 0;
	float[][] saveState = new float[(int)ratio[0] * pxDensity][(int)ratio[1] * pxDensity];
	public float colorNum = 30;
	boolean found = false;
	
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
			//smooth();
			found = true;
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
	
	/*private float checkPx(float[] pixel) {
		pixel = translatePx(pixel);
		float[] nextItt = pixel;
		for (int i = 0; i < maxItt; i++) {
			nextItt = Complex.pow(nextItt, 2);
			nextItt = Complex.add(nextItt, pixel);
			if (Math.pow(nextItt[0], 2) + Math.pow(nextItt[1], 2) >= pxDensity) {
				return i;
			}
		}
		return -1;
	}*/
	
	private float checkPx(float[] pixel) {
		pixel = translatePx(pixel);
		float[] nextItt = pixel;
		for (int i = 0; i < maxItt; i++) {
			nextItt = Complex.mut(nextItt, Complex.sin(nextItt));
			if (Math.pow(nextItt[0], 2) + Math.pow(nextItt[1], 2) >= pxDensity) {
				return i;
			}
		}
		return -1;
	}
	
	private float getColor(float itterations) {
        return (colorRange * ((int)(itterations + Math.abs(modulate)) % colorNum) / colorNum);
	}
	
	private void smooth() {
		float[][] tempState = new float[(int)xLen * 2][(int)yLen * 2];
		for (int x = 0; x < 2 * xLen; x++)for (int y = 0; y < 2 * yLen; y++) tempState[x][y] = saveState[x][y];
		
		for (int x = 0; x < 2 * xLen; x++) {
			for (int y = 0; y < 2 * yLen; y++) {
				float color = 0;
				int numColor = 0;
				for (int x2 = -1; x2 <= 1; x2++) {
					for (int y2 = -1; y2 <= 1; y2++) {
						if (x + x2 < 0 || x + x2 > (2 * xLen) - 1 || y + y2 < 0 || y + y2 > (2 * yLen) - 1) continue;
						numColor++;
						color += tempState[x + x2][y + y2];
					}
				}
				saveState[x][y] = color / numColor;
			}
		}
	}
	
	public void mouseHandler(float x, float y) {
		float[] coord = translatePx(new float[] {x -xLen, y -yLen});
		zoom /= 2;
		xOffset = coord[0] * pxDensity / zoom;
		yOffset = coord[1] * pxDensity / zoom;
		found = false;
	}
	
	private int getRandom(int min, int max) { return (int)Math.floor(Math.random() * (max - min)) + min;}
	
}


