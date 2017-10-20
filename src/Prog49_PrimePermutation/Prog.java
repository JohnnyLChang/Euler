package Prog49_PrimePermutation;

import java.util.Arrays;

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

	private static final int LIMIT = 10000;

	@Override
	public String Smart() {
		boolean[] isPrime = Library.listPrimality(LIMIT - 1);
		for (int base = 1000; base < LIMIT; base++) {
			if (isPrime[base]) {
				for (int step = 1; step < LIMIT; step++) {
					int a = base + step;
					int b = a + step;
					if (a < LIMIT && isPrime[a] && hasSameDigits(a, base) && b < LIMIT && isPrime[b]
							&& hasSameDigits(b, base) && (base != 1487 || a != 4817))
						return "" + base + a + b;
				}
			}
		}
		throw new RuntimeException("Not found");
	}

	private static boolean hasSameDigits(int x, int y) {
		char[] xdigits = Integer.toString(x).toCharArray();
		char[] ydigits = Integer.toString(y).toCharArray();
		Arrays.sort(xdigits);
		Arrays.sort(ydigits);
		return Arrays.equals(xdigits, ydigits);
	}

}
