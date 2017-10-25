package Prog74_DigitFactorialChains;

import java.util.HashSet;
import java.util.Set;

import base.EulerProgBase;
import utils.Library;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	@Override
	public String BruteForce() {
		long sum = 0;
		return String.valueOf(sum);
	}

	private static final int LIMIT = Library.pow(10, 6);
	
	@Override
	public String Smart() {
		int count = 0;
		for (int i = 0; i < LIMIT; i++) {
			if (getChainLength(i) == 60)
				count++;
		}
		return Integer.toString(count);
	}

	private static int getChainLength(int n) {
		Set<Integer> seen = new HashSet<>();
		while (true) {
			if (!seen.add(n))
				return seen.size();
			n = factorialize(n);
		}
	}

	// Hard-coded values for factorial(0), factorial(1), ..., factorial(9)
	private static int[] FACTORIAL = { 1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880 };

	private static int factorialize(int n) {
		int sum = 0;
		for (; n != 0; n /= 10)
			sum += FACTORIAL[n % 10];
		return sum;
	}
}
