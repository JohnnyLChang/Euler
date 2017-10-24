package Prog62_CubicPermutations;

import java.util.HashMap;
import java.util.Map;

import base.EulerProgBase;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	public class Cube{
		public long N;
		public int count;
		public Cube(long n, int c) {
			this.N = n;
			this.count = c;
		}
	}

	private Map<Long, Cube> cubics = new HashMap<Long, Cube>();

	private long makeSmallestPerm(long n) {
		long k = n;
		int[] digits = new int[10];
		long retVal = 0;
		while (k > 0) {
			digits[(int) (k % 10)]++;
			k /= 10;
		}

		for (int i = 9; i >= 0; i--) {
			for (int j = 0; j < digits[i]; j++) {
				retVal = retVal * 10 + i;
			}
		}

		return retVal;
	}

	@Override
	public String BruteForce() {
		long sum = 0;
		long n = 1000;
		while (true) {
			// generate smallest permutation and add to cubes
			long perm = this.makeSmallestPerm(n * n * n);
			if (!cubics.containsKey(perm)) {
				cubics.put(perm, new Cube(n, 1));
			}
			// check if the count is 5
			else{
				if(++cubics.get(perm).count == 5) {
					n = cubics.get(perm).N;
					break;
				}
			}
				
			n++;
		}
		return String.valueOf(n*n*n);
	}

	@Override
	public String Smart() {
		long sum = 0;
		return String.valueOf(sum);
	}

}
