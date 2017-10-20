package Prog37_TruncatablePrimes;

import base.EulerProgBase;
import utils.Library;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	private static final int LIMIT = Library.pow(10, 7);

	private boolean[] isPrime = Library.listPrimality(LIMIT - 1);

	private boolean isCircularPrime(int n) {
		String s = Integer.toString(n);
		for (int i = 0; i < s.length(); i++) {
			if (!isPrime[Integer.parseInt(s.substring(0, i + 1))])
				return false;
			if (!isPrime[Integer.parseInt(s.substring(s.length() - 1 - i, s.length()))])
				return false;
		}
		return true;
	}

	@Override
	public String BruteForce() {
		System.out.println(isCircularPrime(3797));
		long sum = 0;
		int count = 0;
		for (int i = 10; i < isPrime.length; ++i) {
			if (isCircularPrime(i)) {
				count++;
				sum += i;
				if(count == 11)
					break;
			}
		}
		System.out.println(count);
		return String.valueOf(sum);
	}

	@Override
	public String Smart() {
		long sum = 0;
		return String.valueOf(sum);
	}

}
