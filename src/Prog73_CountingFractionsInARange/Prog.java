package Prog73_CountingFractionsInARange;

import java.util.ArrayList;

import base.EulerProgBase;
import utils.Library;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	ArrayList<String> set = new ArrayList<String>();

	@Override
	// How many fractions lie between 1/3 and 1/2 in the sorted set of
	// reduced proper fractions for d ≤ 12,000?
	public String BruteForce() {
		long sum = 0;
		for (int d = 2; d < 12000; ++d) {
			int n1 = d * 1 / 3;
			if (n1 % 3 == 0)
				n1++;

			int n2 = d * 1 / 2;
			if (n2 % 2 == 0)
				n2--;

			for (int n = n1; n <= n2; ++n) {
				if (Library.gcd(n, d) == 1) {
					sum++;
				}
			}
		}
		return String.valueOf(sum);
	}

	@Override
	public String Smart() {
		String ret = Integer.toString(sternBrocotCount(1, 3, 1, 2));
		return ret;
	}

	// Counts the number of reduced fractions n/d such that leftN/leftD < n/d <
	// rightN/rightD and d <= 12000.
	// leftN/leftD and rightN/rightD must be adjacent in the Stern-Brocot tree at
	// some point in the generation process.
	private int sternBrocotCount(int leftN, int leftD, int rightN, int rightD) {
		int n = leftN + rightN;
		int d = leftD + rightD;
		if (d > 12000)
			return 0;
		else
			return 1 + sternBrocotCount(leftN, leftD, n, d) + sternBrocotCount(n, d, rightN, rightD);
	}

}
