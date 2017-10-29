package ProgF612_FriendNumbers;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.IntStream;

import base.EulerProgBase;
import utils.Library;
import utils.collection.Combination;
import utils.collection.Permutation;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	boolean isFriend(int p, int q) {
		// if(String.valueOf(p).length() != 3 && String.valueOf(q).length() != 3) return
		// false;
		int[] digits = new int[10];
		while (p > 0) {
			digits[p % 10]++;
			p /= 10;
		}
		while (q > 0) {
			if (digits[q % 10] > 0)
				return true;
			q /= 10;
		}
		return false;
	}

	boolean isCloseFriend(int p, int q, int n, int c) {
		// if(String.valueOf(p).length() != 3 && String.valueOf(q).length() != 3) return
		// false;
		int[] digitsp = new int[10];
		int[] digitsq = new int[10];
		while (p > 0) {
			digitsp[p % 10]++;
			p /= 10;
		}
		while (q > 0) {
			digitsq[q % 10]++;
			q /= 10;
		}
		if (digitsp[n] == digitsq[n] && c == digitsq[n])
			return true;
		return false;
	}

	boolean hasdigits(int p, int d) {
		while (p > 0) {
			if (p % 10 == d)
				return true;
			p /= 10;
		}
		return false;
	}

	boolean hasdigits(long p, int[] d) {
		boolean[] dig = new boolean[10];
		while (p > 0) {
			dig[(int) (p % 10L)] = true;
			p /= 10L;
		}
		for (int dd : d)
			if (!dig[dd])
				return false;
		return true;
	}

	boolean hasdigitscount(int p, int[] d, int c) {
		int[] dig = new int[10];
		while (p > 0) {
			dig[p % 10]++;
			p /= 10;
		}
		int sum = 0;
		for (int dd : d) {
			if (dig[dd] == 0)
				return false;
			sum += dig[dd];
		}
		return sum == c;
	}

	boolean countdigits(int p, int q, int count) {
		int digits[] = new int[10];
		while (p > 0) {
			digits[p % 10]++;
			p /= 10;
		}
		return digits[q] == count;
	}

	BigInteger countNumOfSpecificDigit(BigInteger ub) {
		BigInteger ret = BigInteger.ONE;
		ub = ub.divide(BigInteger.TEN);
		int i = 1;
		while (ub.compareTo(BigInteger.ONE) > 0) {
			ret = ret.multiply(BigInteger.valueOf(9)).add(BigInteger.TEN.pow(i));
			ub = ub.divide(BigInteger.TEN);
			i++;
		}
		return ret;
	}

	BigInteger countNumOfZeroDigit(BigInteger ub) {
		BigInteger ret = BigInteger.ZERO;
		ub = ub.divide(BigInteger.TEN);
		int i = 1;
		while (ub.compareTo(BigInteger.ONE) > 0) {
			ret = ret.multiply(BigInteger.valueOf(9)).add(BigInteger.TEN.pow(i).subtract(BigInteger.ONE));
			ub = ub.divide(BigInteger.TEN);
			i++;
		}
		return ret;
	}

	BigInteger Binomial(int n, int k) {
		return new BigInteger(Library.binomial(n, k).toString());
	}

	//找出N位數中,共有D位數字的共有幾個
	BigInteger getCommonDigits(int n, int d) {
		if (d > n)
			return BigInteger.ZERO;
		BigInteger r = BigInteger.ZERO;
		BigInteger g = getNonZeroDigits(n, d);
		BigInteger v = BigInteger.valueOf(10 - d);
		for (int i = 1; i < n-1; i++) {
			r = r.add(getNonZeroDigits(n-i,d).multiply(v.pow(i).multiply(Binomial(n, i))));
		}
		return r.add(g);
	}

	//找出N位數中,不是零的有幾個
	BigInteger getNonZeroDigits(int n, int d) {
		if (d > n)
			return BigInteger.ZERO;
		BigInteger[] dp = new BigInteger[n + 2];
		BigInteger[] dpv = new BigInteger[d + 2];
		dp[2] = BigInteger.valueOf(2).multiply(BigInteger.valueOf(2).pow(n - 1).subtract(BigInteger.ONE));
		dpv[2] = dp[2];
		for (int i = 3; i < dp.length; ++i)
			dp[i] = dp[i - 1].multiply(BigInteger.valueOf(2)).add(BigInteger.valueOf(2));
		for (int i = 3; i < dpv.length; ++i) {
			dpv[i] = BigInteger.valueOf(i).pow(n).subtract(BigInteger.valueOf(i));
			int k = 1;
			for (int j = i - 1; j >= 2; --j) {
				dpv[i] = dpv[i].subtract(Binomial(i, k).multiply(dpv[j]));
				k++;
			}
		}
		return dpv[d];
	}

	BigInteger getMixZeroDigits(int n, int d) {
		if (d > n)
			return BigInteger.ZERO;
		BigInteger noZeroCount = this.getNonZeroDigits(n, d);
		return noZeroCount;
	}

	//剩出小於10^N的數字中,共有D集合的數字有幾個
	//小於 10^3, 共有 12的有幾個
	void DumpSameDigitsTable(int nn) {
		System.out.println("=========================");
		for (int d = 2; d <= 9; ++d) {
			for (int n = 2; n <= nn; ++n) {
				System.out.print(getCommonDigits(n, d) + " ");
				//System.out.print(this.getNonZeroDigits(j, i) + " ");
			}
			System.out.println();
		}
		System.out.println("=========================");
	}
	
	void DumpZeroDigitsTable(int nn) {
		for (int d = 2; d <= 9; ++d) {
			for (int n = 2; n <= nn; ++n) {
				System.out.print(getMixZeroDigits(n, d) + " ");
			}
			System.out.println();
		}
	}

	BigInteger countNumOfSpecificDigit(int n, int count) {
		BigInteger[] dp = new BigInteger[n + 1];
		for (int i = 0; i < dp.length; ++i)
			dp[i] = BigInteger.ZERO;
		dp[2] = BigInteger.valueOf(2);
		BigInteger Eight = BigInteger.valueOf(8);
		int k = 2;
		for (int i = n; i > 2; --i) {
			dp[i] = dp[2].multiply(Eight.pow(n - k)).multiply(new BigInteger(Library.binomial(n, k).toString()));
			dp[2] = dp[2].add(BigInteger.valueOf(2)).multiply(BigInteger.valueOf(2)).subtract(BigInteger.valueOf(2));
			k++;
		}
		BigInteger ret = BigInteger.ZERO;

		for (int i = 2; i < dp.length; ++i)
			ret = ret.add(dp[i]);
		return ret;
	}

	//利用差集原理減去多餘計算的集合
	void getFriendNumber(int n) {
		BigInteger p = BigInteger.TEN.pow(n);
		BigInteger night = BigInteger.valueOf(9);
		BigInteger two = BigInteger.valueOf(2);
		BigInteger r = BigInteger.ZERO;
		BigInteger v = Library.binomialBig(countNumOfSpecificDigit(p), two);
		System.out.println("36585 = "+v);
		for (int i = 1; i < 10; ++i) {
			r = r.add(v);
			for (int j = 2; j <= i && j <= n; ++j) {
				BigInteger commonSet = Library.binomialBig(getCommonDigits(n, j), BigInteger.valueOf(j));
				commonSet = commonSet.multiply(Library.binomial(i, i));
				commonSet = commonSet.multiply(Library.factorial(j + 1).subtract(BigInteger.ONE));
				if (j % 2 == 0)
					r = r.subtract(commonSet);
				else
					r = r.add(commonSet);
			}
		}
		//System.out.println(r);
		System.out.println(r.add(Library.binomialBig(countNumOfZeroDigit(p), two)));
		BigInteger zzz = BigInteger.ZERO;
		for (int i = 1, j = 2; i < 9; ++i) {
			//System.out.print(ZeroPermutation(j) + "* ");
			for (int k = 1; k < n && k < 9-i; ++k) {
				//System.out.print(Library.binomial(10-i, k) + " ");
			}
			//System.out.println();
			if (j < n)
				j++;
		}
	}

	private int ZeroPermutation(int n) {
		return (int) Math.pow(2, n - 1) - 1;
	}

	@Override
	public String BruteForce() {
		//DumpZeroDigitsTable(18);
		//DumpSameDigitsTable(18);
		getFriendNumber(3);
		long sum = 0;
		/*for (int i = 0; i < 100000; i++) {
			if (this.hasdigitscount(i, new int[] { 0, 1 , 2}, 5)) {
				System.out.println(i);
				sum++;
			}
		}*/
		return String.valueOf("sum:" + sum);
	}

	@Override
	public String Smart() {
		long sum = 0;
		return String.valueOf(sum);
	}

}
