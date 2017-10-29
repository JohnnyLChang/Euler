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
			dig[(int)(p % 10L)] = true;
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
	
	BigInteger getCommonDigits(int n, int m) {
		BigInteger r= BigInteger.ZERO;
		BigInteger g = getSameDigits(n,m);
		BigInteger v = BigInteger.valueOf(10-n);
		for(int i = 1;i<m-1;i++) {
			//System.out.println("1:"+getSameDigits(n, m-i));
			//System.out.println("2:"+getSameDigits(n, m-i).multiply(Binomial(m, m-i)));
			r = r.add(v.pow(i).multiply(Binomial(m, m-i)).multiply(getSameDigits(n, m-i)));
		}
		return r.add(g);
	}
	
	BigInteger getSameDigits(int n, int m) {
		if(m < n) return BigInteger.ZERO;
		BigInteger[] dp = new BigInteger[m+2];
		BigInteger[] dpv = new BigInteger[n+2];
		dp[2] = BigInteger.valueOf(2).multiply( BigInteger.valueOf(2).pow(m-1).subtract(BigInteger.ONE));
		dpv[2] = dp[2];
		for(int i=3;i<dp.length;++i)
			dp[i] = dp[i-1].multiply(BigInteger.valueOf(2)).add(BigInteger.valueOf(2));
		for(int i=3;i<dpv.length;++i) {
			dpv[i] = BigInteger.valueOf(i).pow(m).subtract(BigInteger.valueOf(i));
			int k = 1;
			for(int j=i-1;j>=2;--j) {
				dpv[i] = dpv[i].subtract(Binomial(i,k).multiply(dpv[j]));
				k++;
			}
		}
		return dpv[n];
	}
	
	void DumpSameDigitsTable(int n) {
		for(int i=2;i<=n;++i) {
			for(int j=2;j<=n;++j) {
				//System.out.print(getSameDigits(i, j) + " ");
				System.out.print(getCommonDigits(i, j) + " ");
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
			dp[i] = dp[2].multiply(Eight.pow(n-k)).multiply(new BigInteger(Library.binomial(n, k).toString()));
			dp[2] = dp[2].add(BigInteger.valueOf(2)).multiply(BigInteger.valueOf(2)).subtract(BigInteger.valueOf(2));
			k++;
		}
		BigInteger ret = BigInteger.ZERO;

		for (int i = 2; i < dp.length; ++i) {
			System.out.println(dp[i]);
			ret = ret.add(dp[i]);
		}
		return ret;
	}
	
	void getFriendNumber(int n) {
		BigInteger p = BigInteger.TEN.pow(n);
		BigInteger night = BigInteger.valueOf(9);
		BigInteger two = BigInteger.valueOf(2);
		//System.out.println(countNumOfSpecificDigit(p));
		System.out.println(getCommonDigits(2, 2));
		System.out.println(Library.binomialBig(countNumOfSpecificDigit(p), two).multiply(night));
		//System.out.println(countNumOfZeroDigit(p));
		System.out.println(Library.binomialBig(countNumOfZeroDigit(p), two));
	}

	@Override
	public String BruteForce() {
		// System.out.println(countNumOfSpecificDigit(BigInteger.valueOf(100)));
		// System.out.println(countNumOfSpecificDigit(BigInteger.TEN.pow(18)));
		// System.out.println(countNumOfZeroDigit(BigInteger.valueOf(100)));
		// System.out.println(countNumOfZeroDigit(BigInteger.TEN.pow(18)));
		// System.out.println(countNumOfSpecificDigit(2, 2));
		// System.out.println(countNumOfSpecificDigit(3, 2));
		// System.out.println(countNumOfSpecificDigit(4, 2));
		// System.out.println(countNumOfSpecificDigit(6, 2));
		DumpSameDigitsTable(11);
		getFriendNumber(2);
		long sum = 0;
		for (long i = 0; i < (long)Math.pow(10, 9); ++i) {
			if (hasdigits(i, new int[] { 1,2,3 ,4})) {
				sum++;
				//System.out.println(i);
				}
		}
		System.out.println("hasdigits:" + sum);
		sum = 0;
		Integer[] num = IntStream.range(0, 10).boxed().toArray(Integer[]::new);
		Permutation<Integer> b10 = new Permutation<Integer>(num, 2);
		for (List<Integer> it : b10) {
			for (Integer i : it)
				if (i == 1) {
					sum++;
				}
		}
		System.out.println(sum);

		sum = 0;
		Integer[] nums = IntStream.range(1, 1000).boxed().toArray(Integer[]::new);
		Combination<Integer> binominals = new Combination<Integer>(nums, 2);
		for (List<Integer> it : binominals) {
			if (isFriend(it.get(0), it.get(1)) && hasdigits(it.get(0), 2) && hasdigits(it.get(1), 2)) {
				sum++;
			}
			// if(hasdigits(it.get(0), new int[] {1,2}) && hasdigits(it.get(1), new int[]
			// {1,2})) {
			// System.out.println(it.get(0).toString() + ":" +it.get(1).toString());
			// }
			// }
			// if(isCloseFriend(it.get(0), it.get(1), 1 , 1))
		}
		return String.valueOf(sum);
	}

	@Override
	public String Smart() {
		long sum = 0;
		return String.valueOf(sum);
	}

}
