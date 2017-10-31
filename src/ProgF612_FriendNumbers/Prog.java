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

	//Count the number with Zero
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

	//Count the numbers with 1~D only
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
				dpv[i] = dpv[i].subtract(Library.binomial(i, k).multiply(dpv[j]));
				k++;
			}
		}
		return dpv[d];
	}

	//Count the numbers with common 1~D
	BigInteger funcH_numWithCommon1toD(int n, int d) {
		if (d > n)
			return BigInteger.ZERO;
		BigInteger r = BigInteger.ZERO;
		BigInteger g = funcG_numWith1toD_Only(n, d);
		BigInteger v = BigInteger.valueOf(10 - d);
		for (int i = 1; i < n; i++) {
			BigInteger a = Library.binomial(n, i);
			BigInteger b = v.pow(i);
			BigInteger c = funcG_numWith1toD_Only(n - i, d);
			r = r.add(c.multiply(b.multiply(a)));
		}
		return r.add(g);
	}

	/*
	 * Count the number with only 0 and 1~D​
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
	}*/
	
	//Count the numbers with Zero
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

	//Count the numbers with common 0 and 1~D
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



	//Count the friend number from 1~9 and plus friend number with only Zero
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
	
	//Count the friend numbers with 0 only, no other friend digits
	BigInteger getFriendNumberZeroOnly(int n) {
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

	@Override
	public String BruteForce() {
		return String.valueOf(getFriendNumber(18).mod(new BigInteger("1000267129")));
	}
	
	@Override
	public String Smart() {
		long sum = 0;
		return String.valueOf(sum);
	}

}
