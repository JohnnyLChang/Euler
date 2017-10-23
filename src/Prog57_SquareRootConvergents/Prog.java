package Prog57_SquareRootConvergents;

import java.math.BigInteger;

import base.EulerProgBase;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}
	
	// 1 + 1/2
	// 1 + 1/(2+1/2) = 1 + 1/ (2*2+1) / 2 =  1 + 2 / 5 = 7/5
	// 1 + 1/(2+1/(2+1/2)) = 1 + 1/(2+1/(5/2)) = 1 + 1/(2+2/5) = 1 + 1/ 12/5 = 17 / 12
	
	private void squareRoot(int depth) {
		long numerator = 1;
		long denominator = 2;
		for(int i=1;i<depth;++i) {
			numerator += denominator*2;
			numerator ^= denominator;
			denominator ^= numerator;
			numerator ^= denominator;
		}
		numerator += denominator;
		System.out.println(numerator + "/" + denominator);
	}
	
	final int LIMITS = 1000;
	@Override
	public String BruteForce() {
		long sum = 0;
		//squareRoot(3);
		BigInteger numerator = BigInteger.valueOf(1);
		BigInteger denominator = BigInteger.valueOf(2);
		for(int i=1;i<LIMITS;++i) {
			numerator = numerator.add(denominator.multiply(BigInteger.valueOf(2)));
			numerator =  numerator.xor(denominator);
			denominator =  denominator.xor(numerator);
			numerator =  numerator.xor(denominator);
			BigInteger tmpNum = numerator.add(denominator);
			if(tmpNum.toString().length() > denominator.toString().length()) {
				System.out.println(tmpNum.toString() + "/" + denominator.toString());
				sum++;
			}
		}
		return String.valueOf(sum);
	}

	@Override
	public String Smart() {
		long sum = 0;
		return String.valueOf(sum);
	}

}


