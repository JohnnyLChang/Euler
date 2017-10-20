package Prog40_ChampernowneConstant;

import base.EulerProgBase;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	private int nDigits(int n, int off) {
		char[] s = String.valueOf(n).toCharArray();
		return Character.getNumericValue(s[off]);
	}

	private int N(int n) {
		if (n < 10)
			return n;

		int step = 0, start = 0;
		int tmp;
		while ((tmp = (int) ((step + 1) * 9 * Math.pow(10, step))) < n) {
			step++;
			n -= tmp;
		}
		int p = (int) (Math.pow(10, step)) + (n - 1) / (step + 1);
		return nDigits(p, (n - 1) % (step + 1));
	}

	@Override
	public String BruteForce() {
		int sum = 1;
		for (int i = 0; i < 7; ++i) {
			sum *= N((int) Math.pow(10, i));
		}
		return String.valueOf(sum);
	}

	//build a string to get the offset it not very smart
	@Override
	public String Smart() {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < 1000000; i++)
			sb.append(i);

		int prod = 1;
		for (int i = 0; i <= 6; i++)
			prod *= sb.charAt(utils.Library.pow(10, i) - 1) - '0';
		return Integer.toString(prod);
	}

}
