package E001;

import java.util.LinkedHashSet;
import java.util.Set;

import base.EulerProg;

public class Prog23_NonAbundantSums implements EulerProg {
	public static void main(String[] args) {
		System.out.println("NonAbundantSums:" + new Prog23_NonAbundantSums().run());
	}

	@Override
	public String run() {
		return String.valueOf(AbundantSum());
	}

	public int AbundantSum(){
		Set<Integer> abundantNumSet = new LinkedHashSet<Integer>(); //sorted set of abundant numbers

        // find all abundant numbers from 13 to 28123, since 12 is the smallest
        // abundant number and greatest number that cannot be expressed as the
        // sum of two abundant numbers is less than 28123
        abundantNumSet.add(12);
        for (int i = 13; i < 28124; ++i) {
            if (isAbundant(i)) {
                abundantNumSet.add(i);
            }
        }

        int total = 276; // sigma 23, since 24 = 12 * 2
        for (int i = 25; i < 28124; ++i) {
            boolean isRep = false;

            for (Integer num : abundantNumSet) {
                // iterating through half of the numbers in the set is enough
                if (abundantNumSet.contains(i - num)) {
                    // the number can be represented as a sum of two
                    isRep = true;
                    break;
                }
                if (i < num) {
                    // the abundant number should less than a given number to
                    // be part of the numbers forming the sum
                    break;
                }
            }

            if (!isRep) {
                // the number cannot be represented as sum of two abundant
                // numbers
                total += i;
            }
        }
        return total;
	}
	
	private boolean isAbundant(int n) {
        int sum = 1;
        int end = (int) Math.sqrt(n);

        for (int i = 2; i <= end; ++i) {
            if (n % i == 0) {
                int quotient = n / i;

                if (quotient != i) {
                    sum += i + quotient; // add the factors
                } else {
                    // avoid adding twice - factor in case of perfect squares
                    sum += quotient;
                }
            }
        }

        if (sum > n) {
            return true;
        }

        return false;
    }
}
