package Prog69_TotientMaximum;

import base.EulerProgBase;
import utils.Library;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	final int LIMIT = 1000000;
	boolean[] primes;

	int countPhi(int n) {
		int sum = 1;
		int m = n;

		while (--m > 1) {
			if (!primes[m])
				sum++;
		}
		return sum;
	}

	@Override
	public String BruteForce() {
		int maxNumer = 0;
		int maxDenom = 1;
		//list all Totients by using ESieve mechanism
		int[] totients = Library.listTotients(LIMIT);
		for (int n = 1; n < totients.length; n++) {
			// 4/7 > 6/11 == 6*7 > 11*4
			if ((long) n * maxDenom > (long) maxNumer * totients[n]) {
				maxNumer = n;
				maxDenom = totients[n];
			}
		}
		return Integer.toString(maxNumer);
	}

	@Override
	public String Smart() {
		long sum = 0;
		for (int i = 100; i < LIMIT; ++i) {
			// System.out.println(i);
			// int[] totiens = Library.listTotients(i);
			// if(totiens.length > sum)
			// sum = totiens.length;
		}
		return String.valueOf(sum);
	}

}
