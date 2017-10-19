package Prog32_PandigitalProducts;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import base.EulerProgBase;
import utils.collection.Combination;
import utils.collection.Permutation;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	@Override
	public String BruteForce() {
		long sum = 0;
		Integer[] v = IntStream.range(1, 10).boxed().toArray(Integer[]::new);
		Integer[] w = IntStream.range(1, 9).boxed().toArray(Integer[]::new);
		Set<Integer> n = new HashSet<Integer>();
		Permutation<Integer> a = new Permutation<Integer>(v, 9);
		for (List<Integer> l : a) {
			Combination<Integer> b = new Combination<Integer>(w, 2);
			for (List<Integer> m : b) {
				String s = a.toString(l);
				int x = Integer.valueOf(s.substring(0, m.get(0)));
				int y = Integer.valueOf(s.substring(m.get(0), m.get(1)));
				int z = Integer.valueOf(s.substring(m.get(1), s.length()));
				if (x * y == z) {
					if (!n.contains(z)) {
						n.add(z);
						sum += z;
					}
				}
			}
		}
		return String.valueOf(sum);
	}

	@Override
	public String Smart() {
		long sum = 0;
		for (int i = 1; i < 10000; i++) {
			if (hasPandigitalProduct(i))
				sum += i;
		}
		return String.valueOf(sum);
	}

	private static boolean hasPandigitalProduct(int n) {
		// Find and examine all factors of n
		for (int i = 1; i <= n; i++) {
			if (n % i == 0 && isPandigital("" + n + i + n / i))
				return true;
		}
		return false;
	}

	private static boolean isPandigital(String s) {
		if (s.length() != 9)
			return false;
		char[] temp = s.toCharArray();
		Arrays.sort(temp);
		return new String(temp).equals("123456789");
	}
}
