package utils.Math;

import java.math.BigInteger;

public class Math {
	public static BigInteger recfact(long start, long n) {
	    long i;
	    if (n <= 16) { 
	    	BigInteger r = BigInteger.valueOf(start);
	        for (i = start + 1; i < start + n; i++)
	        	r  = r.multiply(BigInteger.valueOf(i));
	        return r;
	    }
	    i = n / 2;
	    return recfact(start, i).multiply(recfact(start + i, n - i));
	}
	
	public static BigInteger factorial(long n) { return recfact(1, n); }
}
