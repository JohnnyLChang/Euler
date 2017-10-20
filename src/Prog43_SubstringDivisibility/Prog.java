package Prog43_SubstringDivisibility;

import base.EulerProgBase;
import utils.Library;
import utils.Math.Euler;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	int[] denominators = { 2, 3, 5, 7, 11, 13, 17 };

	private boolean isSubstringDivisible(int[] digits) {
		for (int i = 0; i < denominators.length; ++i) {
			if (Euler.toInteger(digits, i + 1, 3) % denominators[i] != 0)
				return false;
		}
		return true;
	}

	@Override
	public String BruteForce() {
		long sum = 0;
		int n = 10;
		int[] digits = new int[n];
		for (int i = 0; i < digits.length; i++)
			digits[i] = i;

		int result = -1;
		do {
			if (isSubstringDivisible(digits))
				sum += Euler.toInteger(digits);
		} while (utils.Library.nextPermutation(digits));
		return String.valueOf(sum);
	}

	private static int[] DIVISIBILITY_TESTS = {2, 3, 5, 7, 11, 13, 17};  // First 7 primes
	
	@Override
	public String Smart() {
		long sum = 0;
		int[] digits = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		outer: do {
			for (int i = 0; i < DIVISIBILITY_TESTS.length; i++) {
				if (toInteger(digits, i + 1, 3) % DIVISIBILITY_TESTS[i] != 0)
					continue outer;
			}
			sum += toInteger(digits, 0, digits.length);
		} while (Library.nextPermutation(digits));
		return Long.toString(sum);
	}

	private static long toInteger(int[] digits, int off, int len) {
		long result = 0;
		for (int i = off; i < off + len; i++)
			result = result * 10 + digits[i];
		return result;
	}
}
