package Prog44_PentagonNumbers;

import java.util.ArrayList;
import java.util.List;

import base.EulerProgBase;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	private int Pentagon(int n) {
		return (n * (3 * n - 1)) / 2;
	}

	private boolean isPentagonPair(int n, int m) {
		return isPentagon[n + m] && isPentagon[m - n];
	}

	boolean[] isPentagon;
	int n = 8000000;
	List<Integer> pentagon = new ArrayList<Integer>();

	@Override
	public String BruteForce() {
		int a = 0, b = 0;
		int p = 0, i = 1, diff = 0;
		boolean notfound = true;
		//isPentagon = new boolean[n];
		i=1;
		while (notfound) {
			i++;			
			int m = (i * (3 * i - 1)) / 2;
			for (int j = i - 1; j > 0; --j) {
				int n = (j * (3 * j - 1)) / 2;
				if (isPentagonal(n + m) && isPentagonal(m - n)) {
					diff = m-n;
					notfound = false;
					break;
				}
			}
		}
		return String.valueOf(diff);
	}

	@Override
	public String Smart() {
		int result = 0;
		boolean notFound = true;
		int i = 1;
		int k = 0;

		while (notFound) {
			i++;
			int n = i * (3 * i - 1) / 2;
			for (int j = i - 1; j > 0; j--) {
				int m = j * (3 * j - 1) / 2;
				if (isPentagonal(n - m) && isPentagonal(n + m)) {
					result = n - m;
					k = j;
					notFound = false;
					break;
				}
			}
		}
		return String.valueOf(result);
	}

	private boolean isPentagonal(int number) {
		double penTest = (Math.sqrt(1 + 24 * number) + 1.0) / 6.0;
		return penTest == ((int) penTest);
	}
}
