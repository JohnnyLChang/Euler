package Prog50_ConsecutivePrimeSum;

import java.util.ArrayList;

import base.EulerProgBase;
import utils.Library;
import utils.Prime.Prime;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	public final int LIMIT = 1000000;
	boolean[] bprimes = new boolean[LIMIT];

	@Override
	public String BruteForce() {
		int MAX_SUM = 1000000;
		int currentSum = 0;
		int currentNumber = 6;

		while ((currentSum + currentNumber) < MAX_SUM) {
			if (Prime.isPrime(currentNumber)) {
				currentSum += currentNumber;
			}
			currentNumber++;
		}
		return String.valueOf(currentSum);
	}

	@Override
	public String Smart() {
		boolean[] isPrime = Library.listPrimality(LIMIT);
		int[] primes = Library.listPrimes(LIMIT);

		long maxSum = 0;
		int maxRun = -1;
		for (int i = 0; i < primes.length; i++) { // For each index of a starting prime number
			int sum = 0;
			for (int j = i; j < primes.length; j++) { // For each end index (inclusive)
				sum += primes[j];
				if (sum > LIMIT)
					break;
				else if (j - i > maxRun && sum > maxSum && isPrime[sum]) {
					maxSum = sum;
					maxRun = j - i;
				}
			}
		}
		return Long.toString(maxSum);
	}

}
