package edu.eci.arsw.math;

public class PiThread extends Thread{
	private byte[] digits;
	private int start,count;
	private static int DigitsPerSum = 8;
	
	public PiThread(int start, int count) {
    	this.start=start;
    	this.count=count;
    }
	
	public void run() {
		calculate();
	}
	
	private void calculate() {
		digits = new byte[count];
        double sum = 0;

        for (int i = 0; i < count; i++) {
            if (i % DigitsPerSum == 0) {
                sum = 4 * PiDigits.sum(1, start)
                        - 2 * PiDigits.sum(4, start)
                        - PiDigits.sum(5, start)
                        - PiDigits.sum(6, start);

                start += DigitsPerSum;
            }

            sum = 16 * (sum - Math.floor(sum));
            digits[i] = (byte) sum;
        }
        
	}

	public byte[] getDigits() {
		return digits;
	}

	public void setDigits(byte[] digits) {
		this.digits = digits;
	}
}
