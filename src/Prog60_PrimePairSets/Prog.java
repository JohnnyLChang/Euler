package Prog60_PrimePairSets;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

import base.EulerProgBase;
import utils.Library;
import utils.Prime.Prime;
import utils.collection.Combination;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	private boolean isPrimePairSet(List<Integer> p) {
		Integer[] parr = new Integer[p.size()];
		parr = p.toArray(parr);
		Combination<Integer> array = new Combination<Integer>(parr, 2);
		Iterator<List<Integer>> it = array.iterator();
		int cnt = 0;
		for (List<Integer> l : array) {
			String case1 = l.get(0).toString() + l.get(1).toString();
			if (!Prime.isPrime(Integer.valueOf(case1)))
				return false;
			String case2 = l.get(1).toString() + l.get(0).toString();
			if (!Prime.isPrime(Integer.valueOf(case2)))
				return false;
		}
		return true;
	}

	// int[] primes = Library.listPrimes(30000);

	// this solution cannot work because the solution space is too large
	@Override
	public String BruteForce() {
		long sum = 0;
		Integer[] p = Arrays.stream(primes).boxed().toArray(Integer[]::new);
		Combination<Integer> array = new Combination<Integer>(p, 5);
		Iterator<List<Integer>> it = array.iterator();
		int cnt = 0;

		/*
		 * for (List<Integer> l : array) { if (isPrimePairSet(l)) { for (Integer prime :
		 * l) sum += prime; break; } }
		 */
		return String.valueOf(sum);
	}

	private static final int PRIME_LIMIT = 100000; // Arbitrary initial cutoff

	private int[] primes = Library.listPrimes(PRIME_LIMIT);

	// Memoization
	private BitSet isConcatPrimeKnown;
	private BitSet isConcatPrime;

	@Override
	public String Smart() {
		isConcatPrimeKnown = new BitSet(primes.length * primes.length);
		isConcatPrime = new BitSet(primes.length * primes.length);

		int sumLimit = PRIME_LIMIT;
		while (true) {
			int sum = findSetSum(new int[] {}, 5, sumLimit - 1);
			if (sum == -1) // No smaller sum found
				return Integer.toString(sumLimit);
			sumLimit = sum;
		}
	}

	/*
	 * Tries to find any suitable set and return its sum, or -1 if none is found. A
	 * set is suitable if it contains only primes, its size is 'targetSize', its sum
	 * is less than or equal to 'sumLimit', and each pair concatenates to a prime.
	 * 'prefix' is an array of ascending indices into the 'primes' array, which
	 * describes the set found so far. The function blindly assumes that each pair
	 * of primes in 'prefix' concatenates to a prime.
	 * 
	 * For example, findSetSum(new int[]{1, 3, 28}, 5, 10000) means "find the sum of
	 * any set where the set has size 5, consists of primes with the lowest elements
	 * being {3, 7, 109}, has sum 10000 or less, and has each pair concatenating to
	 * form a prime".
	 */
	private int findSetSum(int[] prefix, int targetSize, int sumLimit) {
		if (prefix.length == targetSize) {
			int sum = 0;
			for (int i : prefix)
				sum += primes[i];
			return sum;

		} else {
			int i;
			if (prefix.length == 0)
				i = 0;
			else
				i = prefix[prefix.length - 1] + 1;

			outer: for (; i < primes.length && primes[i] <= sumLimit; i++) {
				for (int j : prefix) {
					if (!isConcatPrime(i, j) || !isConcatPrime(j, i))
						continue outer;
				}

				int[] appended = Arrays.copyOf(prefix, prefix.length + 1);
				appended[appended.length - 1] = i;
				int sum = findSetSum(appended, targetSize, sumLimit - primes[i]);
				if (sum != -1)
					return sum;
			}
			return -1;
		}
	}

	// Tests whether parseInt(toString(x) + toString(y)) is prime.
	private boolean isConcatPrime(int x, int y) {
		int index = x * primes.length + y;
		if (isConcatPrimeKnown.get(index))
			return isConcatPrime.get(index);

		x = primes[x];
		y = primes[y];
		int mult = 1;
		for (int temp = y; temp != 0; temp /= 10)
			mult *= 10;

		boolean result = isPrime((long) x * mult + y);
		isConcatPrimeKnown.set(index);
		isConcatPrime.set(index, result);
		return result;
	}

	private boolean isPrime(long x) {
		if (x < 0)
			throw new IllegalArgumentException();
		else if (x == 0 || x == 1)
			return false;
		else {
			long end = Library.sqrt(x);
			for (int p : primes) {
				if (p > end)
					break;
				if (x % p == 0)
					return false;
			}
			for (long i = primes[primes.length - 1] + 2; i <= end; i += 2) {
				if (x % i == 0)
					return false;
			}
			return true;
		}
	}

}
