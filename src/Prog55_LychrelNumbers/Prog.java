package Prog55_LychrelNumbers;

import java.math.BigInteger;

import base.EulerProgBase;
import utils.Library;
import utils.Math.Euler;

//47 + 74 = 121

/* 349 took three iterations 
 * 349 + 943 = 1292,
 * 1292 + 2921 = 4213
 * 4213 + 3124 = 7337
 */
public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	final int LIMIT = 10000;
	
	//Long is still too short for checking Lychrel Number
	private boolean isLychrelNum(int n) {
		long num = n;
		while (true) {
				Long revi = Euler.reverseNum((Long)num);
				num += revi;
				if (num < revi)
					break;
				else if(Library.isPalindrome(String.valueOf(num))) {
					return false;
				}
		}
		return true;
	}

	@Override
	public String BruteForce() {
		long sum = 0;
		for (int i = 0; i < LIMIT; ++i) {
			if(isLychrel(i)) {
				sum++;
			}
		}
		return String.valueOf(sum);
	}

	@Override
	public String Smart() {
		int count = 0;
		for (int i = 0; i < LIMIT; i++) {
			if (isLychrel(i)) {
				count++;
			}
		}
		return Integer.toString(count);
	}
	
	
	private static boolean isLychrel(int n) {
		BigInteger temp = BigInteger.valueOf(n);
		for (int i = 0; i < 49; i++) {
			temp = temp.add(new BigInteger(Library.reverse(temp.toString())));
			if (Library.isPalindrome(temp.toString()))
				return false;
		}
		return true;
}

}
