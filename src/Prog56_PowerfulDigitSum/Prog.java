package Prog56_PowerfulDigitSum;

import java.math.BigInteger;

import base.EulerProgBase;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}
	
	private int sumDigits(String num) {
		char[] digits = num.toCharArray();
		int sum = 0;
		for(char c : digits)
			sum += Character.getNumericValue(c);
		return sum;
	}
	
	@Override
	public String BruteForce() {
		long sum = 0;
		int maxDigits = 0;
		for(int i=0;i<100;++i) {
			for(int j=0;j<100;++j) {
				BigInteger t = BigInteger.valueOf((long)i).pow(j);
				System.out.println(t.toString());
				int tmp = sumDigits(t.toString());
				if(tmp > sum) {
					sum = tmp;
				}
							
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


