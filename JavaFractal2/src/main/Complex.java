package main;

public final class Complex {
	private Complex() {}
	
	
	static float[] add(float[] comNum, float[] comNum2) {
		return new float[] {comNum[0] + comNum2[0], comNum[1] + comNum2[1]};
	}
	
	static float[] sub(float[] comNum, float[] comNum2) {
		return new float[] {comNum[0] - comNum2[0], comNum[1] - comNum2[1]};
	}
	
	static float[] mut(float[] comNum, float[] comNum2) {
		return new float[] { (comNum[0] * comNum2[0]) - (comNum[1] * comNum2[1]), 
				 			 (comNum[0] * comNum2[1]) + (comNum[1] * comNum2[0])};
	}
	
	static float[] mut(float[] comNum, float num) {
		return new float[] {comNum[0] * num, comNum[1] * num};
	}
	
	static float[] div(float[] comNum, float[] comNum2) {
		return new float[] {((comNum[0] * comNum2[0]) + (comNum[1] * comNum2[1])) / (comNum2[0] * comNum2[1]),
							((comNum2[1] * comNum2[0]) - (comNum[0] * comNum2[1])) / (comNum2[0] * comNum2[1])};
	}
	
	static float[] cos(float[] comNum) {
		return new float[] {(float)Math.cos(comNum[0]) * (float)Math.cosh(comNum[1]),
							(float)Math.sin(comNum[0]) * (float)Math.sinh(comNum[1])};
	}
	
	static float[] sin(float[] comNum) {
		return new float[] {(float)Math.sin(comNum[0]) * (float)Math.cosh(comNum[1]), 
		(float)Math.cos(comNum[0]) * (float)Math.sinh(comNum[1])};
	}
	
	static float[] pow(float[] comNum, int power) { 
		
		if (power > 2) comNum = pow(comNum, power - 1);
		
        return new float[] { (float)(Math.pow(comNum[0], 2) - Math.pow(comNum[1], 2)), 
        				(float)(comNum[0] * comNum[1] * 2)};
    }
	
	static float[] sqrt(float[] comNum) {
		float absVal = (float)Math.sqrt(Math.pow(comNum[0], 2) + Math.pow(comNum[1], 2));
		return new float[] {(float)Math.sqrt((absVal + comNum[0]) / 2), 
							(float)Math.sqrt((absVal - comNum[0]) / 2)};
	}
}
