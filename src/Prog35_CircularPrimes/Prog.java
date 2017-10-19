package Prog35_CircularPrimes;

import base.EulerProgBase;
import utils.*;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	private static final int LIMIT = Library.pow(10, 6);

	private boolean[] isPrime = Library.listPrimality(LIMIT - 1);

	private boolean isCircularPrime(int n) {
		String s = Integer.toString(n);
		for (int i = 0; i < s.length(); i++) {
			if (!isPrime[Integer.parseInt(s.substring(i) + s.substring(0, i))])
				return false;
		}
		return true;
	}

	// There are thirteen such primes below
	// 100: 2, 3, 5, 7, 11, 13, 17, 31, 37, 71, 73, 79, and 97.
	@Override
	public String BruteForce() {
		int count = 0;
		for (int i = 0; i < isPrime.length; i++) {
			if (isCircularPrime(i))
				count++;
		}
		return Integer.toString(count);
	}

	@Override
	public String Smart() {
		long sum = 0;
		return String.valueOf(sum);
	}

}
