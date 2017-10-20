package utils.Prime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class Prime {
	
	public int NumberOfPrimeFacors(int number, int[] primeList) {
	    int nod = 0;
	    boolean pf;
	    int remain = number;
	 
	    for (int i = 0; i < primeList.length; i++) {
	        // In case there is a remainder this is a prime factor as well
	        // The exponent of that factor is 1
	        if (primeList[i] * primeList[i] > number) {
	            return ++nod;
	        }
	 
	        pf = false;
	        while (remain % primeList[i] == 0) {
	            pf = true;
	            remain = remain / primeList[i];
	        }
	        if (pf){
	            nod++;
	        }
	 
	        //If there is no remainder, return the count
	        if (remain == 1) {
	            return nod;
	        }
	    }
	    return nod;
	}

	public static List<Integer> primeFactors(int numbers) {
		int n = numbers;
		List<Integer> factors = new ArrayList<Integer>();
		for (int i = 2; i <= n / i; i++) {
			while (n % i == 0) {
				factors.add(i);
				n /= i;
			}
		}
		if (n > 1) {
			factors.add(n);
		}
		return factors;
	}
	
	public static Set<Integer> primeFactorsDistinct(int numbers) {
		int n = numbers;
		Set<Integer> factors = new HashSet<Integer>();
		for (int i = 2; i <= n / i; i++) {
			while (n % i == 0) {
				factors.add(i);
				n /= i;
			}
		}
		if (n > 1) {
			factors.add(n);
		}
		return factors;
	}

	/* return if a number is prime */
	public static boolean isPrime(int number) {
		return number > 2 && IntStream.rangeClosed(2, (int) Math.sqrt(number)).noneMatch(n -> (number % n == 0));
	}

	/* list all primes under n */
	public static ArrayList<Long> ESieve(long n) {
		ArrayList<Long> primelist = new ArrayList<Long>();
		boolean[] isPrime = new boolean[(int) (n + 1)];
		for (int i = 2; i <= n; i++) {
			isPrime[i] = true;
		}

		for (int factor = 2; factor * factor <= n; factor++) {
			if (isPrime[factor]) {
				for (int j = factor; factor * j <= n; j++) {
					isPrime[factor * j] = false;
				}
			}
		}

		int primes = 0;
		/*
		 * for (long i = n; i >= 1; i--) { if (isPrime[(int)i]) primelist.add(i); }
		 */

		for (long i = 1; i <= n; ++i) {
			if (isPrime[(int) i])
				primelist.add(i);
		}
		return primelist;
	}
	
	/* list all primes under n */
	public static ArrayList<Integer> ESieve(int n) {
		ArrayList<Integer> primelist = new ArrayList<Integer>();
		boolean[] isPrime = new boolean[(int) (n + 1)];
		for (int i = 2; i <= n; i++) {
			isPrime[i] = true;
		}

		for (int factor = 2; factor * factor <= n; factor++) {
			if (isPrime[factor]) {
				for (int j = factor; factor * j <= n; j++) {
					isPrime[factor * j] = false;
				}
			}
		}

		int primes = 0;
		/*
		 * for (long i = n; i >= 1; i--) { if (isPrime[(int)i]) primelist.add(i); }
		 */

		for (int i = 1; i <= n; ++i) {
			if (isPrime[(int) i])
				primelist.add(i);
		}
		return primelist;
	}

	public static int gcd(int a, int b) {
		int y, x;

		if (a > b) {
			x = a;
			y = b;
		} else {
			x = b;
			y = a;
		}

		while (x % y != 0) {
			int temp = x;
			x = y;
			y = temp % x;
		}

		return y;
	}
}
