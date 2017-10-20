package Prog48_SelfPowers;

import java.math.BigInteger;

import base.EulerProgBase;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}
	
	@Override
	public String BruteForce() {
		BigInteger sum = BigInteger.valueOf(0);
		for(int i=1;i<=1000;i++) {
			sum = sum.add(BigInteger.valueOf(i).pow(i));
		}
		return String.valueOf(sum);
	}

/* The properties we want to exploit are
 * (a*b) % c = ((a % c) * (b % c) )% c
 * and
 * (a+b) % c = ((a % c) + (b % c) )% c.
 */
	@Override
	public String Smart() {
		long result = 0;
		long modulo = 10000000000L;
		 
		for (int i = 1; i <= 1000; i++) {
		    long temp = i;
		    for (int j = 1; j < i; j++) {
		        temp *= i;
		        if (temp >= Long.MAX_VALUE / 1000) {
		            temp %= modulo;
		        }
		    }
		 
		    result += temp;
		    result %= modulo;
		}
		return String.valueOf(result);
	}

}


