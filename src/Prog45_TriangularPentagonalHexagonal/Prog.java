package Prog45_TriangularPentagonalHexagonal;

import base.EulerProgBase;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	private boolean isPentagonal(long number) {
		double penTest = (Math.sqrt(1 + 24 * number) + 1.0) / 6.0;
		return penTest == ((int) penTest);
	}

	private boolean isTriangular(long number) {
		double penTest = (Math.sqrt(1 + 8 * number) - 1.0) / 2.0;
		return penTest == ((int) penTest);
	}

	private boolean isHexagonal(long number) {
		double penTest = (Math.sqrt(1 + 8 * number) + 1.0) / 4.0;
		return penTest == ((int) penTest);
	}

	@Override
	public String BruteForce() {
		// System.out.print(this.isHexagonal(40755));
		// System.out.print(this.isTriangular(40755));
		// System.out.print(this.isPentagonal(40755));
		long sum = 0;
		boolean notfound = true;
		long i = 143;
		while (notfound) {
			i++;
			long n = i * (2 * i - 1);
			if (isTriangular(n) && isPentagonal(n)) {
				sum = n;
				notfound = false;
			}
		}
		return String.valueOf(sum);
	}

	@Override
	public String Smart() {
		long sum = 0;
		boolean notfound = true;
		long i = 143;
		while (notfound) {
			i++;
			long n = i * (2 * i - 1);
			if (isPentagonal(n)) {
				sum = n;
				notfound = false;
			}
		}
		return String.valueOf(sum);
	}

}
