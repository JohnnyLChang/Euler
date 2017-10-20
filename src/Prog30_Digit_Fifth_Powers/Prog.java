/* 
 * Solution to Project Euler problem 30
 * Copyright (c) Project Johnny. All rights reserved.
 * 
 */

package Prog30_Digit_Fifth_Powers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import base.EulerProgBase;
import utils.collection.CombinationRepeat;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	private boolean tokenCompare(String s, long v) {
		String vstr = String.valueOf(v);
		if (s.length() != vstr.length())
			return false;

		ArrayList<Character> setS = new ArrayList<Character>();
		ArrayList<Character> setV = new ArrayList<Character>();
		for (char c : s.toCharArray())
			setS.add(c);
		for (char c : vstr.toCharArray())
			setV.add(c);
		setS.sort((a, b) -> a.compareTo(b));
		setV.sort((a, b) -> a.compareTo(b));
		return setS.equals(setV);
	}

	@Override
	public String BruteForce() {
		long sum = 0;
		Integer[] v = IntStream.range(0, 10).boxed().toArray(Integer[]::new);

		for (int i = 4; i <= 6; ++i) {
			CombinationRepeat<Integer> a = new CombinationRepeat<Integer>(v, i);
			for (List<Integer> l : a) {
				long tmp = 0;
				for (Integer x : l) {
					tmp += (long) Math.pow(x, 5);
				}
				if (tokenCompare(a.toString(l), tmp)) {
					sum += tmp;
				}
			}
		}
		return String.valueOf(sum);
	}

	@Override
	public String Smart() {
		// As stated in the problem, 1 = 1^5 is excluded.
		// If a number has at least n >= 7 digits, then even if every digit is 9,
		// n * 9^5 is still less than the number (which is at least 10^n).
		// 6 digit number. 6*95 = 354294. So 355000, reduce the solution space
		int sum = 0;
		for (int i = 2; i < 355000; i++) {
			if (i == fifthPowerDigitSum(i))
				sum += i;
		}
		return Integer.toString(sum);
	}

	private static int fifthPowerDigitSum(int x) {
		int sum = 0;
		while (x != 0) {
			int y = x % 10;
			sum += y * y * y * y * y;
			x /= 10;
		}
		return sum;
	}

}
