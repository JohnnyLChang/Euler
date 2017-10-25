package Prog66_DiophantineEquation;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.EulerProgBase;
import utils.Library;
import utils.Library.Fraction;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	final int LIMIT = 1000;
	final int[] D = { 2, 3, 5, 6, 7 };

	/*
	 * https://zh.wikipedia.org/zh-tw/%E4%B8%9F%E7%95%AA%E5%9C%96%E6%96%B9%E7%A8%8B
	 * 
	 */
	@Override
	public String BruteForce() {
		long sum = 0;
		boolean finding = true;
		for (long d = 2; d <= 10; ++d) {
			if (Library.isSquare((int) d))
				continue;
			finding = true;
			System.out.println(d);
			long x = 2;
			while (finding) {
				for (long y = x - 1; y > 0; y--) {
					long diff = x * x - d * y * y;
					if (diff == 1) {
						if (sum < x) {
							sum = x;
							System.out.println(x + "^2-" + d + "*" + y + "^2=" + diff);
						}
						finding = false;
						break;
					}
					if (diff > 0)
						break;
				}
				if (++x == Integer.MAX_VALUE)
					break;
			}
		}
		return String.valueOf(sum);
	}

	@Override
	/*
	 * Based on this insane theorem: Suppose D > 1 is an integer,
	 * non-perfect-square.
	 * 
	 * Express sqrt(D) as the continued fraction (a0, a1, ..., a_{n-1}, (b0, b1,
	 * ..., b_{m-1})), where the sequence of b's is the periodic part.
	 * 
	 * Let p/q (in lowest terms) = (a0, a1, ..., a_{n-1}, b0, b1, ..., b_{m-2}).
	 * (This is a truncation of the continued fraction with only one period minus
	 * the last term.)
	 * 
	 * Then the minimum solution (x, y) for Pell's equation is given by: - (p, q) if
	 * m is even - (p^2 + D q^2, 2pq) if m is odd
	 */
	public String Smart() {
		// For more explanation, see
		// http://mathworld.wolfram.com/PellEquation.html
		int smallestD = 0;
		BigInteger largestX = BigInteger.ZERO;
		for (int n = 0; n <= 1000; n++) {
			BigInteger sqrt = BigInteger.valueOf((int) Math.sqrt(n));
			if (sqrt.pow(2).compareTo(BigInteger.valueOf(n)) != 0) {
				BigInteger m = BigInteger.ZERO;
				BigInteger d = BigInteger.ONE;
				BigInteger a = sqrt;
				BigInteger x = a, x0 = BigInteger.ONE, tempx = x0;
				BigInteger y = BigInteger.ONE, y0 = BigInteger.ZERO, tempy = y0;
				while (x.pow(2).subtract(y.pow(2).multiply(BigInteger.valueOf(n))).compareTo(BigInteger.ONE) != 0) {
					m = d.multiply(a).subtract(m);
					d = BigInteger.valueOf(n).subtract(m.pow(2)).divide(d);
					a = sqrt.add(m).divide(d);
					tempx = x0; // Save previous x & y (i.e. i - 1)
					tempy = y0;
					x0 = x; // Save current x & y (i.e. i)
					y0 = y;
					x = x.multiply(a).add(tempx); // Set next x & y (i.e. i + 1) using
					y = y.multiply(a).add(tempy); // continued fraction expansion
				}

				if (x.compareTo(largestX) > 0) {
					largestX = x;
					smallestD = n;
				}
			}
		}
		return Integer.toString(smallestD);
	}
}
