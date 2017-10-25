package Prog65_ConvergentsOfE;

import java.math.BigInteger;

import base.EulerProgBase;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	final int LIMIT = 100;

	long MathConstant(int n) {
		switch (n % 3) {
		case 0:
			return 2 * (n / 3);
		default:
			return 1;
		}
	}

	void dumpC(int n) {
		for (int i = 0; i <= n; ++i)
			System.out.print(MathConstant(i) + ",");
		System.out.println();
	}

	int Convergents(int n) {
		int a0 = 2;
		BigInteger number = BigInteger.ONE;
		BigInteger denominator = BigInteger.valueOf(MathConstant(n));
		for (int i = n - 1; i > 1; --i) {
			BigInteger tmp = number;
			number = denominator;
			denominator = BigInteger.valueOf(MathConstant(i)).multiply(denominator).add(tmp);
		}
		number = denominator.multiply(BigInteger.valueOf(a0)).add(number);

		int sum = 0;
		for (char c : number.toString().toCharArray())
			sum += Character.getNumericValue(c);
		return sum;
	}

	@Override
	public String BruteForce() {
		long sum = 0;
		Convergents(10);
		return String.valueOf(Convergents(100));
	}

	@Override
	public String Smart() {
		BigInteger n = BigInteger.ONE;
		BigInteger d = BigInteger.ZERO;
		for (int i = LIMIT-1; i >= 0; i--) {
			BigInteger temp = BigInteger.valueOf(continuedFractionTerm(i)).multiply(n).add(d);
			d = n;
			n = temp;
		}

		int sum = 0;
		while (!n.equals(BigInteger.ZERO)) {
			BigInteger[] divrem = n.divideAndRemainder(BigInteger.TEN);
			sum += divrem[1].intValue();
			n = divrem[0];
		}
		return Integer.toString(sum);
	}

	private static int continuedFractionTerm(int i) {
		if (i == 0)
			return 2;
		else if (i % 3 == 2)
			return i / 3 * 2 + 2;
		else
			return 1;
	}
}
