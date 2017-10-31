package ProgF612_FriendNumbers;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.IntStream;

import base.EulerProgBase;
import utils.Library;
import utils.collection.Combination;
import utils.collection.Permutation;

public class Prog extends EulerProgBase {
	final BigInteger night = BigInteger.valueOf(9);
	final BigInteger two = BigInteger.valueOf(2);

	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	boolean isFriend(int p, int q) {
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

	boolean isFriendOnly(int p, int q, int d) {
		return isFriendOnly(p, q, new int[] { d });
	}

	boolean isFriendOnly(int p, int q, int[] dd) {
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
		for (int d : dd) {
			if (digitsp[d] == 0 || digitsq[d] == 0) {
				return false;
			}
		}
		return true;
	}// if(hasdigits(it.get(0), new int[] {1,2}) && hasdigits(it.get(1), new int[]
		// {1,2})) {
		// System.out.println(it.get(0).toString() + ":" +it.get(1).toString());
		// }
		// }
		// if(isCloseFriend(it.get(0), it.get(1), 1 , 1))

	boolean hasdigits(int p, int d) {
		while (p > 0) {
			if (p % 10 == d)
				return true;
			p /= 10;
		}
		return false;
	}

	boolean hasdigits(long p, Integer[] d) {
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

	BigInteger Binomial(int n, int k) {
		return new BigInteger(Library.binomial(n, k).toString());
	}

	// fn = 9 * f(n-1) + 10 ^ n-1
	BigInteger funcF_numWithZero(BigInteger ub) {
		if (ub.compareTo(BigInteger.TEN) == 0)
			return BigInteger.ONE;
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

	// 找出N位數中,不是零的有幾個
	BigInteger funcG_numWith1toD_Only(int n, int d) {
		if (d > n)
			return BigInteger.ZERO;
		BigInteger[] dp = new BigInteger[n + 2];
		BigInteger[] dpv = new BigInteger[d + 2];
		dp[1] = BigInteger.ONE;
		dpv[1] = BigInteger.ONE;
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

	// 找出N位數中,共有D位數字的共有幾個
	BigInteger funcH_numWithCommon1toD(int n, int d) {
		if (d > n)
			return BigInteger.ZERO;
		BigInteger r = BigInteger.ZERO;
		BigInteger g = funcG_numWith1toD_Only(n, d);
		BigInteger v = BigInteger.valueOf(10 - d);
		for (int i = 1; i < n; i++) {
			BigInteger a = Binomial(n, i);
			BigInteger b = v.pow(i);
			BigInteger c = funcG_numWith1toD_Only(n - i, d);
			r = r.add(c.multiply(b.multiply(a)));
		}
		return r.add(g);
	}

	public BigInteger funcZ_numWithZeroAnd1toD(int n, int d) {
		if (d == 2) {
			BigInteger t = BigInteger.ZERO;
			for (int i = 2; i <= n; ++i) {
				t = t.add(funcG_numWith1toD_Only(i, d).subtract(funcG_numWith1toD_Only(i - 1, d))
						.subtract(funcG_numWith1toD_Only(i - 1, d - 1)));
			}
			return t;
		} else if (n >= d)
			return funcG_numWith1toD_Only(n, d).subtract(funcG_numWith1toD_Only(n - 1, d))
					.subtract(funcG_numWith1toD_Only(n - 1, d - 1));
		return BigInteger.ZERO;
	}

	// 剩出小於10^N的數字中,共有D集合的數字有幾個
	// 小於 10^3, 共有 12的有幾個
	void DumpSameDigitsTable(int nn) {
		System.out.println("============With Common Digits=============");
		for (int d = 1; d <= 9; ++d) {
			for (int n = 1; n <= nn; ++n) {
				System.out.print(funcH_numWithCommon1toD(n, d) + " ");
				// System.out.print(this.getNonZeroDigits(j, i) + " ");
			}
			System.out.println();
		}
		System.out.println("=========================");
	}

	void DumpSameDigitsWithZeroTable(int nn) {
		System.out.println("==========With Common Zero Digits===========");
		for (int d = 1; d <= 9; ++d) {
			for (int n = 1; n <= nn; ++n) {
				System.out.print(funcH_numWithCommon1toD(n, d) + " ");
			}
			System.out.println();
		}
		System.out.println("=========================");
	}

	// 列出小於N的數字集合數量
	void DumpNonZeroDigitsTable(int nn) {
		System.out.println("===========Non Zero Digits==============");
		for (int d = 1; d <= 9; ++d) {
			for (int n = 1; n <= nn; ++n) {
				System.out.print(funcG_numWith1toD_Only(n, d) + " ");
			}
			System.out.println();
		}
		System.out.println("=========================");
	}

	void DumpZeroDigitsTable(int nn) {
		System.out.println("=============Zero Digits==============");
		BigInteger tmp = BigInteger.ZERO;
		for (int d = 2; d <= 9; ++d) {
			for (int n = 2; n <= nn; ++n) {
				System.out.printf("%s ", funcZ_numWithZeroAnd1toD(n, d));
			}
			System.out.println();
		}
		System.out.println("=========================");
	}

	// 利用差集原理減去多餘計算的集合
	BigInteger getFriendNumber(int n) {
		BigInteger r = BigInteger.ZERO;
		BigInteger v = Library.binomialBig(funcH_numWithCommon1toD(n, 1), two);
		int i = 1;
		for (; i < 10; ++i) {
			r = r.add(v);
			for (int j = 2; j <= i && j <= n; ++j) {
				BigInteger commonSet = Library.binomialBig(funcH_numWithCommon1toD(n, j), two);
				commonSet = commonSet.multiply(Library.binomial(i - 1, j - 1));
				if (j % 2 == 0)
					r = r.subtract(commonSet);
				else
					r = r.add(commonSet);
			}
		}
		return r.add(getFriendNumberZeroOnly(n));
	}

	BigInteger funcZZ_numWithZero(BigInteger ub) {
		BigInteger ret = BigInteger.ZERO;
		int i = 1;
		while (ub.compareTo(BigInteger.ONE) > 0) {
			ret = ret.multiply(BigInteger.valueOf(9)).add(BigInteger.TEN.pow(i).subtract(BigInteger.ONE));
			ub = ub.subtract(BigInteger.ONE);
			i++;
		}
		return ret;
	}

	BigInteger getFriendNumberZeroOnly(int n) {
		//funcZZZ_numWithZeroAnd1toD
		BigInteger allZero = Library.binomialBig(this.funcZZ_numWithZero(BigInteger.valueOf(n)), two);
		BigInteger withNonZeroFriend = BigInteger.ZERO;
		for(int i=1;i<=n-1 && i<10;i++) {
			if(i%2 == 0)
				withNonZeroFriend = withNonZeroFriend.subtract(Library.binomialBig(funcZZZ_numWithZeroAnd1toD(n, i), two).multiply(Library.binomial(9, i)));
			else
				withNonZeroFriend = withNonZeroFriend.add(Library.binomialBig(funcZZZ_numWithZeroAnd1toD(n, i), two).multiply(Library.binomial(9, i)));
		}	
		return allZero.subtract(withNonZeroFriend);
	}

	public BigInteger funcZZZ_numWithZeroAnd1toD(int n, int d) {
		BigInteger all = BigInteger.ZERO;
		BigInteger noZero = BigInteger.ZERO;
		// all = this.fun
		for (int i = 1; i <= n; ++i) {
			noZero = funcH_numWithCommon1toD(n - i + 1, d).subtract(funcH_numWithCommon1toD(n - i + 1, d + 1))
					.add(noZero);
		}
		all = this.funcH_numWithCommon1toD(n, d);
		return all.subtract(noZero);
	}

	void DumpNoZeroWithTable(int nn) {
		System.out.println("=========DumpDwithZeroWithTable==========");
		BigInteger tmp = BigInteger.ZERO;
		for (int d = 1; d < 10; ++d) {
			for (int n = 1; n <= nn; ++n) {
				System.out.printf("%s ", funcZZZ_numWithZeroAnd1toD(n, d));
			}
			System.out.println();
		}
		System.out.println("=========================");
	}

	@Override
	public String BruteForce() {
		//getFriendNumberZeroOnly(3);
		// DumpNoZeroWithTable(18);
		// DumpNonZeroDigitsTable(18);
		// DumpSameDigitsTable(18);
		for (int i = 3; i <= 18; ++i)
			System.out.println(getFriendNumber(i));
		long sum = 0;
		for (int i = 1000; i < 10000; i++) {
			if (!this.hasdigits(i, new Integer[] { 0 }) && this.hasdigits(i, new Integer[] { 1 })) {
				// System.out.println(i);
				sum++;
			}
		}
		// Debug();
		return String.valueOf(getFriendNumber(18).mod(new BigInteger("1000267129")));
	}

	private void Debug() {
		long sum = 0;
		Integer[] nums = IntStream.range(1, 1000).boxed().toArray(Integer[]::new);
		Combination<Integer> binominals = new Combination<Integer>(nums, 2);
		for (List<Integer> it : binominals) {
			// if (isFriendOnly(it.get(0), it.get(1), 0)) {
			// System.out.printf("%s %s\n", it.get(0), it.get(1));
			// if (this.isFriendOnly(it.get(0), it.get(1), new int[] { 1, 2, 0 })) {
			if (this.isFriend(it.get(0), it.get(1))) {
				sum++;
				// System.out.printf("%s, %s\n", it.get(0), it.get(1));
			}
			// }
		}
		System.out.println("Debug" + sum);
	}

	private void Debug2() {
		long sum = 0;
		for (int m = 2; m < 10; ++m) {
			for (int n = 2; n < 10; ++n) {
				sum = 0;
				if (n >= m) {
					for (int i = 0; i < (int) Math.pow(10, n); i++) {
						if (this.hasdigits(i, IntStream.range(0, m).boxed().toArray(Integer[]::new))) {
							// System.out.println(i);
							sum++;
						}
					}
				}
				System.out.printf("%d ", sum);
			}
			System.out.println();
		}

	}

	@Override
	public String Smart() {
		long sum = 0;
		return String.valueOf(sum);
	}

}
