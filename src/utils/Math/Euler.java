package utils.Math;

import java.math.BigInteger;
import java.util.Arrays;

public class Euler {
	public static BigInteger recfact(long start, long n) {
		long i;
		if (n <= 16) {
			BigInteger r = BigInteger.valueOf(start);
			for (i = start + 1; i < start + n; i++)
				r = r.multiply(BigInteger.valueOf(i));
			return r;
		}
		i = n / 2;
		return recfact(start, i).multiply(recfact(start + i, n - i));
	}

	public static boolean hasSameDigits(int x, int y) {
		char[] xdigits = Integer.toString(x).toCharArray();
		char[] ydigits = Integer.toString(y).toCharArray();
		Arrays.sort(xdigits);
		Arrays.sort(ydigits);
		return Arrays.equals(xdigits, ydigits);
	}
	
	public static boolean isPandigital(int n) {
		return isPandigital(new Long(n));
	}

	public static long toInteger(int[] digits) {
		long result = 0;
		for (int x : digits)
			result = result * 10 + x;
		return result;
	}

	public static long toInteger(int[] digits, int s, int l) {
		long result = 0;
		for (int x = s; x < s + l; ++x)
			result = result * 10 + digits[x];
		return result;
	}

	public static int alphabeticalIdx(String s) {
		int sum = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c < 'A' || c > 'Z')
				throw new IllegalArgumentException();
			sum += c - 'A' + 1;
		}
		return sum;
	}

	public static boolean isPandigital(Long n) {
		int digits = 0;
		int count = 0;
		int tmp;

		while (n > 0) {
			tmp = digits;
			// The minus one is there to make 1 fill the first bit and so on
			digits = digits | 1 << (int) ((n % 10) - 1);

			// Check to see if the same digit is found multiple times
			if (tmp == digits) {
				return false;
			}

			count++;
			n /= 10;
		}

		return digits == (1 << count) - 1;
	}

	public static BigInteger factorial(long n) {
		return recfact(1, n);
	}
}
