package edu.eci.arsw.math;

import java.awt.List;
import java.util.ArrayList;

///  <summary>
///  An implementation of the Bailey-Borwein-Plouffe formula for calculating hexadecimal
///  digits of pi.
///  https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula
///  *** Translated from C# code: https://github.com/mmoroney/DigitsOfPi ***
///  </summary>
public class PiDigits {

    private static int DigitsPerSum = 8;
    private static double Epsilon = 1e-17;

    
    /**
     * Returns a range of hexadecimal digits of pi.
     * @param start The starting location of the range.
     * @param count The number of digits to return
     * @return An array containing the hexadecimal digits.
     */
    public static byte[] getDigits(int start, int count) {
        if (start < 0) {
            throw new RuntimeException("Invalid Interval");
        }

        if (count < 0) {
            throw new RuntimeException("Invalid Interval");
        }

        byte[] digits = new byte[count];
        double sum = 0;

        for (int i = 0; i < count; i++) {
            if (i % DigitsPerSum == 0) {
                sum = 4 * sum(1, start)
                        - 2 * sum(4, start)
                        - sum(5, start)
                        - sum(6, start);

                start += DigitsPerSum;
            }

            sum = 16 * (sum - Math.floor(sum));
            digits[i] = (byte) sum;
        }

        return digits;
    }
    
    /**
     * Returns a range of hexadecimal digits of pi.
     * @param start The starting location of the range.
     * @param count The number of digits to return
     * @param N number of threads between which the solution is to be parallelized.
     * @return An array containing the hexadecimal digits.
     */
    public static byte[] getDigits(int start, int count, int N) {
        if (start < 0) {
            throw new RuntimeException("Invalid Interval");
        }

        if (count < 0) {
            throw new RuntimeException("Invalid Interval");
        }
        ArrayList<PiThread> hilos= new ArrayList<PiThread>();
        PiThread pt;
        
        int dis= count-start;
        int l = (int) Math.ceil((double)count / (double)N);
        
        for (int i=0; i<N;i++) {
        	//fin= ((count/N)*(i+1))+start;
        	//fin = comienzo + l;
        	if ((i+1)*l>count && (count-(i*l)>0)) {
        		pt = new PiThread((i*l)+start,((count-(i*l))));
        	}
        	else {
        		pt = new PiThread((i*l)+start,l);
        	}
        	
        	hilos.add(pt);
        	pt.start();
        	//comienzo=fin+1;
        }
        

        for (PiThread pit:hilos) {
        	try {
				pit.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        
        byte[] digits = new byte[count];
        int k=0;
        for (PiThread pit:hilos) {
        	//System.out.println(digits.length+" digitos");
        	//System.out.println(Main.bytesToHex(pit.getDigits()));
        	
        	//System.arraycopy(pit.getDigits(),0,digits,k,pit.getDigits().length);
        	//k=k+pit.getDigits().length;
        	if (hilos.get(0).equals(pit)) {
        		digits=pit.getDigits();
        	}
        	else {
        		digits=ConcatenarArray(digits,pit.getDigits());
        	}
        	
        }

        return digits;
    }
    
    
    public static byte[] ConcatenarArray(byte[] o1, byte[] o2)
	{
		byte[] ret = new byte[o1.length + o2.length];
 
		System.arraycopy(o1, 0, ret, 0, o1.length);
		System.arraycopy(o2, 0, ret, o1.length, o2.length);
 
		return ret;
	}
    
    
    /// <summary>
    /// Returns the sum of 16^(n - k)/(8 * k + m) from 0 to k.
    /// </summary>
    /// <param name="m"></param>
    /// <param name="n"></param>
    /// <returns></returns>
    static double sum(int m, int n) {
        double sum = 0;
        int d = m;
        int power = n;

        while (true) {
            double term;

            if (power > 0) {
                term = (double) hexExponentModulo(power, d) / d;
            } else {
                term = Math.pow(16, power) / d;
                if (term < Epsilon) {
                    break;
                }
            }

            sum += term;
            power--;
            d += 8;
        }

        return sum;
    }

    /// <summary>
    /// Return 16^p mod m.
    /// </summary>
    /// <param name="p"></param>
    /// <param name="m"></param>
    /// <returns></returns>
    private static int hexExponentModulo(int p, int m) {
        int power = 1;
        while (power * 2 <= p) {
            power *= 2;
        }

        int result = 1;

        while (power > 0) {
            if (p >= power) {
                result *= 16;
                result %= m;
                p -= power;
            }

            power /= 2;

            if (power > 0) {
                result *= result;
                result %= m;
            }
        }

        return result;
    }

}
