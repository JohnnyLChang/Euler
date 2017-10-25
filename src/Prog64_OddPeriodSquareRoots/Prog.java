package Prog64_OddPeriodSquareRoots;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import base.EulerProgBase;
import utils.Library;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	final int LIMIT = 10000;

	/*
	 * https://en.wikipedia.org/wiki/Methods_of_computing_square_roots#Continued_fraction_expansion
	 * Continued fraction expansion
	 * 
	 */
	
	int getPeriod(int n) {
		int pd = 0;
		int m = 0, d = 1;
		int a0 = (int) (Math.sqrt(n));
		int a = a0;
		while (a != 2 * a0) {
			m = a * d - m;
			d = (n - m * m) / d;
			a = (int) (((int) Math.sqrt(n) + m) / d);
			pd++;
		}
		return pd;
	}

	@Override
	public String BruteForce() {
		long sum = 0;
		for (int i = 2; i < LIMIT; ++i) {
			String fractions = "";
			int y = (int)Math.sqrt(i);
			if ( y*y == i)
				continue;
			if (getPeriod(i) % 2 == 1)
				sum++;
		}
		return String.valueOf(sum);
	}

	@Override
	public String Smart() {
		int count = 0;
		for (int i = 1; i <= 10000; i++) {
			if (!Library.isSquare(i) && getSqrtContinuedFractionPeriod(i) % 2 == 1)
				count++;
		}
		return Integer.toString(count);
	}

	// Returns the period of the continued fraction of sqrt(n)
	private static int getSqrtContinuedFractionPeriod(int n) {
		Map<QuadraticSurd, Integer> seen = new HashMap<>();
		QuadraticSurd val = new QuadraticSurd(BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.valueOf(n));
		do {
			seen.put(val, seen.size());
			val = val.subtract(new QuadraticSurd(val.floor(), BigInteger.ZERO, BigInteger.ONE, val.d)).reciprocal();
		} while (!seen.containsKey(val));
		return seen.size() - seen.get(val);
	}

	// Represents (a + b * sqrt(d)) / c. d must not be a perfect square.
	private static final class QuadraticSurd {

		public final BigInteger a, b, c, d;

		public QuadraticSurd(BigInteger a, BigInteger b, BigInteger c, BigInteger d) {
			if (c.signum() == 0)
				throw new IllegalArgumentException();

			// Simplify
			if (c.signum() == -1) {
				a = a.negate();
				b = b.negate();
				c = c.negate();
			}
			BigInteger gcd = a.gcd(b).gcd(c);
			if (!gcd.equals(BigInteger.ONE)) {
				a = a.divide(gcd);
				b = b.divide(gcd);
				c = c.divide(gcd);
			}

			this.a = a;
			this.b = b;
			this.c = c;
			this.d = d;
		}

		public QuadraticSurd subtract(QuadraticSurd other) {
			if (!d.equals(other.d))
				throw new IllegalArgumentException();
			return new QuadraticSurd(a.multiply(other.c).subtract(other.a.multiply(c)),
					b.multiply(other.c).subtract(other.b.multiply(c)), c.multiply(other.c), d);
		}

		public QuadraticSurd reciprocal() {
			return new QuadraticSurd(a.multiply(c).negate(), b.multiply(c),
					b.multiply(b).multiply(d).subtract(a.multiply(a)), d);
		}

		public BigInteger floor() {
			BigInteger temp = Library.sqrt(b.multiply(b).multiply(d));
			if (b.signum() == -1)
				temp = temp.add(BigInteger.ONE).negate();
			temp = temp.add(a);
			if (temp.signum() == -1)
				temp = temp.subtract(c.subtract(BigInteger.ONE));
			return temp.divide(c);
		}

		public boolean equals(Object obj) {
			if (!(obj instanceof QuadraticSurd))
				return false;
			else {
				QuadraticSurd other = (QuadraticSurd) obj;
				return a.equals(other.a) && b.equals(other.b) && c.equals(other.c) && d.equals(other.d);
			}
		}

		public int hashCode() {
			return a.hashCode() + b.hashCode() + c.hashCode() + d.hashCode();
		}

		public String toString() {
			return String.format("(%d + %d*sqrt(%d)) / %d", a, b, d, c);
		}

	}
}
