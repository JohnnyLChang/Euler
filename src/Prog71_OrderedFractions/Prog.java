package Prog71_OrderedFractions;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import base.EulerProgBase;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	private int gcd(int n, int d) {
		if (n > d) {
			n ^= d;
			d ^= n;
			n ^= d;
		}

		while (d != 0) {
			int t = d;
			d = n % d;
			n = t;
		}
		return n;
	}

	class ReducedFraction {
		int number;
		int denominator;

		public ReducedFraction(int n, int d) {
			number = n;
			denominator = d;
		}
	}

	SortedMap<Double, ReducedFraction> sortFract = new TreeMap<>();

	final int LIMIT = 1000000;

	@Override
	public String BruteForce() {
		long sum = 0;
		for (int i = 2; i <= LIMIT; ++i) {
			/*
			 * for(int j=i-1;j>0;--j) { int gcn = gcd(j, i); int n = j/gcn; int d = i/gcn;
			 * sortFract.put((double)n/d, new ReducedFraction(n,d)); }
			 */
		}
		Map.Entry<Double, ReducedFraction> prev = null;
		for (Map.Entry<Double, ReducedFraction> entry : sortFract.entrySet()) {
			if (entry.getValue().denominator == 7) {
				sum = entry.getValue().number;
				System.out.println(sum);
			}
		}
		return String.valueOf(sum);
	}

	@Override
	//test the boundary near 3/7	
	public String Smart() {
		int maxN = 0;
		int maxD = 1;
		for (int d = 2; d <= 1000000; d++) {
			int n = d * 3 / 7;
			if (d % 7 == 0)
				n--;
			if ((long) n * maxD > (long) maxN * d) {
				maxN = n;
				maxD = d;
			}
		}
		return Integer.toString(maxN);
	}

}
