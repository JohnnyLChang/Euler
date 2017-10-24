package Prog63_PowerfulDigitsCount;

import java.math.BigInteger;

import base.EulerProgBase;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	@Override
	public String BruteForce() {
		int count = 0;
		for (int n = 1; n <= 100; n++) {
			for (int k = 1; k <= 100; k++) {
				if (BigInteger.valueOf(n).pow(k).toString().length() == k)
					count++;
			}
		}
		return Integer.toString(count);
	}

	@Override
	public String Smart() {
		long sum = 0;
		return String.valueOf(sum);
	}

}
