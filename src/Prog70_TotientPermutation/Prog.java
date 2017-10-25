package Prog70_TotientPermutation;

import java.util.Arrays;

import base.EulerProgBase;
import utils.Library;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	boolean hasSameDigits(int x, int y) {
		char[] xdigits = Integer.toString(x).toCharArray();
		char[] ydigits = Integer.toString(y).toCharArray();
		Arrays.sort(xdigits);
		Arrays.sort(ydigits);
		return Arrays.equals(xdigits, ydigits);
	}

	final int LIMIT = 10000000;

	@Override
	public String BruteForce() {
		int minNumber = 1;
		int minDenom = 0;
		// list all Totients by using ESieve mechanism
		int[] totients = Library.listTotients(LIMIT-1);
		for (int n = 2; n < totients.length; n++) {
			int tt = totients[n];
			if ((long) n * minDenom < (long) minNumber * tt && hasSameDigits(n, tt)) {
				minNumber = n;
				minDenom = tt;
			}
		}
		return Integer.toString(minNumber);
	}

	@Override
	public String Smart() {
		int minNumer = 1; // Initially infinity
		int minDenom = 0;
		int[] totients = Library.listTotients(LIMIT - 1);
		for (int n = 2; n < totients.length; n++) {
			int tot = totients[n];
			if ((long) n * minDenom < (long) minNumer * tot && hasSameDigits(n, tot)) {
				minNumer = n;
				minDenom = tot;
			}
		}
		if (minDenom == 0)
			throw new RuntimeException("Not found");
		return Integer.toString(minNumer);
	}

}
