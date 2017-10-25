package Prog65_ConvergentsOfE;

import base.EulerProgBase;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	final int head = 2;

	int MathConstant(int n) {
		switch (n % 3) {
		case 2:
			return 2 * (n / 3 + 1);
		default:
			return 1;
		}
	}

	void dumpC(int n) {
		for (int i = 0; i <= n; ++i)
			System.out.print(MathConstant(i) + ",");
		System.out.println();
	}

	void Convergents(int n) {
		n--;
		int a0 = 2;
		int number = 1;
		int denominator = MathConstant(n);
		for (int i = n - 1; i > 0; --i) {
			int tmp = number;
			number = denominator;
			denominator = MathConstant(i) * denominator + tmp;
		}
		number = a0 * denominator + number;
		System.out.println("n:" + ++n + " " + number + "/" + denominator);
	}

	@Override
	public String BruteForce() {
		dumpC(10);
		long sum = 0;
		Convergents(1);
		Convergents(2);
		Convergents(3);
		// Convergents(4);
		Convergents(8);
		Convergents(9);
		Convergents(10);
		System.out.println();
		return String.valueOf(sum);
	}

	@Override
	public String Smart() {
		long sum = 0;
		return String.valueOf(sum);
	}

}
