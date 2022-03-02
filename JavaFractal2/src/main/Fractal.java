package main;
import processing.core.*;

public class Fractal extends Canvas {
	PApplet main;
	int maxItt;
	float modulate = 0;
	float zoom = (float)2;
	float preXOffset = 0;
	float xOffset = 0;
	float yOffset = 0;
	float[][][] saveState = new float[(int)ratio[0] * pxDensity][(int)ratio[1] * pxDensity][3];
	public float colorNum = 10;
	boolean found = false;
	float[] juliaVal = new float[] {0, 0};

	Fractal(PApplet main, float[] ratio, int pxDensity, int maxItt) {
		super(main, ratio, pxDensity);
		this.main = main;
		this.maxItt = maxItt;
	}
	
	public void draw() {
		if(!found) {
			for (float x = -xLen; x < xLen; x++) {
				for (float y = -yLen; y < yLen; y++) {
					saveState[(int)(x + xLen)][(int)(y + yLen)] = checkPx(new float[] {x, y});
				}
			}
			smooth();
			found = true;
		} else {
			for (float x = -xLen; x < xLen; x++) {
				for (float y = -yLen; y < yLen; y++) {
					main.set((int)(x + xLen), (int)(y+yLen), main.color((saveState[(int)(x + xLen)][(int)(y + yLen)][0] + modulate) % colorRange, 
								saveState[(int)(x + xLen)][(int)(y + yLen)][1], 
								saveState[(int)(x + xLen)][(int)(y + yLen)][2]));
				}
			}
			modulate+= 0.5;
		}
	}
	
	private float[] translatePx(float[] pixel) {
		return new float[] {(zoom * (pixel[0] + xOffset)) / pxDensity, (zoom * (pixel[1] + yOffset)) / pxDensity};
	}
	
	
	private float[] checkPx(float[] pixel) {
		pixel = translatePx(pixel);
		float[] nextItt = pixel;
		for (int i = 0; i < maxItt; i++) {
			nextItt = Complex.mut(pixel, Complex.cos(nextItt));
			if (Math.pow(nextItt[0], 2) + Math.pow(nextItt[1], 2) >= pxDensity) {
				return getColor(i);
			}
		}
		return new float[] {0, 0, 0};
	}
	
	
	private float[] getColor(float itterations) {
        return new float[] {(colorRange * (itterations % colorNum) / colorNum), colorRange, colorRange};
	}
	
	private void smooth() {
		float[][] tempState = new float[(int)xLen * 2][(int)yLen * 2];
		for (int x = 0; x < 2 * xLen; x++)for (int y = 0; y < 2 * yLen; y++) tempState[x][y] = saveState[x][y][0];
		
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
				saveState[x][y][0] = color / numColor;
			}
		}
	}
	
	private void dither() {
		
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


