package utils.Prime;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Prime {
	/* return if a number is prime*/
	public static boolean isPrime(int number) {
	    return number > 2
	      && IntStream.rangeClosed(2, (int) Math.sqrt(number))
	      .noneMatch(n -> (number % n == 0));
	}
	
	/* list all primes under n */
	public static ArrayList<Long> ESieve(long n) {
		ArrayList<Long> primelist = new ArrayList<Long>();
        boolean[] isPrime = new boolean[(int) (n+1)];
        for (int i = 2; i <= n; i++) {
            isPrime[i] = true;
        }

        for (int factor = 2; factor*factor <= n; factor++) {
            if (isPrime[factor]) {
                for (int j = factor; factor*j <= n; j++) {
                    isPrime[factor*j] = false;
                }
            }
        }

        int primes = 0;
        /*for (long i = n; i >= 1; i--) {
            if (isPrime[(int)i])
            	primelist.add(i);
        }*/
        
        for (long i = 1; i <= n; ++i) {
            if (isPrime[(int)i])
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
