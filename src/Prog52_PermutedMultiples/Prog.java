package Prog52_PermutedMultiples;

import java.util.ArrayList;
import java.util.Arrays;

import base.EulerProgBase;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}
	
	private static boolean hasSameDigits(int x, int y) {
		char[] xdigits = Integer.toString(x).toCharArray();
		char[] ydigits = Integer.toString(y).toCharArray();
		Arrays.sort(xdigits);
		Arrays.sort(ydigits);
		return Arrays.equals(xdigits, ydigits);
	}
	
	@Override
	public String BruteForce() {
		long sum = 0;
		boolean notfound = true;
		int n = 125874;
		while(notfound) {
			n++;
			notfound = false;
			for(int i=1;i<=6;++i) {
				if(!hasSameDigits(n, n*i))
					notfound = true;
			}
		}
		return String.valueOf(n);
	}

	@Override
	public String Smart() {
		long sum = 0;
		return String.valueOf(sum);
	}

}


