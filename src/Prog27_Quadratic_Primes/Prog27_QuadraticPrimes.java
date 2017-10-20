package Prog27_Quadratic_Primes;

import java.util.ArrayList;

import base.EulerProgBase;
import utils.Prime.Prime;

/*
Euler discovered the remarkable quadratic formula:

n^2+n+41

It turns out that the formula will produce 40 primes for the consecutive integer values 0≤n≤39
. However, when n=40,40^2+40+41=40(40+1)+41 is divisible by 41, and certainly 
when n=41,41^2+41+41

is clearly divisible by 41.

The incredible formula n2−79n+1601
was discovered, which produces 80 primes for the consecutive values 0≤n≤79

. The product of the coefficients, −79 and 1601, is −126479.

Considering quadratics of the form:

    n2+an+b, where |a|<1000 and |b|≤1000

where |n| is the modulus/absolute value of n
e.g. |11|=11 and |−4|=4

Find the product of the coefficients, a
and b, for the quadratic expression that produces the maximum number of primes for consecutive 
values of n, starting with n=0.
*/
public class Prog27_QuadraticPrimes extends EulerProgBase {

	public static void main(String[] args) {
		System.out.println(new Prog27_QuadraticPrimes().run());
	}

	public String BruteForce() {
		// String.valueOf(this.quadPrimes(1, 41));
		// return String.valueOf(this.quadPrimes(1, 41));
		long longest = 0;
		int a = 0, b = 0;
		for (int i = -1000; i <= 1000; ++i) {
			for (int j = -1000; j <= 1000; ++j) {
				int ret = quadPrimes(i, j);
				if (ret > longest) {
					a = i;
					b = j;
					longest = ret;
				}
			}
		}
		return String.valueOf(longest) + "a:" + a + "b:" + b + "a*b=" + a * b;
	}

	/*test only primes*/
	public String Smart() {
		long longest = 0;
		int a = 0, b = 0;
		ArrayList<Long> p = Prime.ESieve(1000L);
		for (int i = -1000; i <= 1000; ++i) {
			for (long j :p) {
				for(int k = 0;k<2;k++) {
					int sign = (k == 0) ? 1 : -1;
					int ret = quadPrimes(i, (int)j*sign);
					if (ret > longest) {
						a = i;
						b = (int)j;
						longest = ret;
					}	
				}
			}
		}
		return String.valueOf(longest) + "a:" + a + "b:" + b + "a*b=" + a * b;
	}

	private int quadPrimes(int a, int b) {
		boolean bPrime = true;
		int n = -1;
		do {
			n++;
			int v = n * n + a * n + b;
			bPrime = Prime.isPrime(v);
		} while (bPrime);
		return n - 1;
	}

}
