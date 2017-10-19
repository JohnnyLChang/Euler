/* 
 * Solution to Project Euler problem 34
 * Copyright (c) Project Johnny. All rights reserved.
 * 
 * Digit factorials
 * Problem 34

 * 145 is a curious number, as 1! + 4! + 5! = 1 + 24 + 120 = 145.
 * Find the sum of all numbers which are equal to the sum of the factorial of their digits.
 * Note: as 1! = 1 and 2! = 2 are not sums they are not included.

 */
package Prog34_DigitFactorials;

import base.EulerProgBase;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	@Override
	public String BruteForce() {
		// As stated in the problem, 1 = 1! and 2 = 2! are excluded.
		// If a number has at least n >= 8 digits, then even if every digit is 9,
		// n * 9! is still less than the number (which is at least 10^n).
		int sum = 0;
		for (int i = 3; i < 10000000; i++) {
			if (i == factorialDigitSum(i))
				sum += i;
		}
		return Integer.toString(sum);
	}

	// Hard-coded values for factorial(0), factorial(1), ..., factorial(9)
	private static int[] FACTORIAL = { 1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880 };

	private static int factorialDigitSum(int x) {
		int sum = 0;
		while (x != 0) {
			sum += FACTORIAL[x % 10];
			x /= 10;
		}
		return sum;
	}

	@Override
	public String Smart() {
		long sum = 0;
		return String.valueOf(sum);
	}

}
