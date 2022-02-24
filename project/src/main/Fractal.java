package main;
import processing.core.*;

public class Fractal extends Canvas{
	PApplet main;
	
	double color = 0;
	
	Fractal(PApplet main, float[] ratio, int pxDensity) {
		super(main, ratio, pxDensity);
		this.main = main;
	}
	
	public void draw() {
		main.translate((int)xLen, (int)yLen);
		
		for (float x = -xLen; x < xLen; x++) {
			for (float y = -yLen; y < yLen; y++) {
				if (checkPx(translatePx( new float[] { x, y } ))) {
					main.set((int)(x + xLen), (int)(y + yLen), main.color(0, 0, 0));
				} else {
					main.set((int)(x + xLen), (int)(y + yLen), main.color((int)color, 1000, 1000));
				}
			}
		}
		
		main.noLoop();
	}
	
	public float[] translatePx(float[] pixel) {
		return new float[] {(2 * pixel[0]) / pxDensity, (2 * pixel[1]) / pxDensity};
	}
	
	public boolean checkPx(float[] pixel) {
		
		int powRatio = (int)Math.pow(pxDensity, 2);
		color = 0;
		float[] nextItt = pixel;
		for (int i = 0; i < 1000; i++) {
			
			//BELOW IS CAUSING ISSUES, MOVE TO DRAW LOOP AND RECORD MAX ITT.
			//BELOW IS CAUSING ISSUES, MOVE TO DRAW LOOP AND RECORD MAX ITT.
			//BELOW IS CAUSING ISSUES, MOVE TO DRAW LOOP AND RECORD MAX ITT.
			//BELOW IS CAUSING ISSUES, MOVE TO DRAW LOOP AND RECORD MAX ITT.
			nextItt = comPow(nextItt);
			nextItt[0] += pixel[0];
			nextItt[1] += pixel[1];
			if (Math.pow(nextItt[0], 2) + Math.pow(nextItt[1], 2) >= powRatio) {
				int colorLoop = i; //bellow is used to calculate color of pixel.
                float colorNum = 1000 / 30;
                while (colorLoop > 1000 / colorNum) {
                    colorLoop -= colorNum;
                }
                color = 100 * (colorLoop / (1000 / colorNum));
				return false;
			}
		}
		return true;
	}
	
	public float[] comPow(float[] point) { 
		double real = point[0];
        double imaginary = point[1];
        return new float[] { (float)(Math.pow(real, 2) - Math.pow(imaginary, 2)), 
        				(float)(2 * real * imaginary)};
    }
}
