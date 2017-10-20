package Prog41_PandigitsPrime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import base.EulerProgBase;
import utils.Math.Euler;
import utils.Prime.Prime;
import utils.collection.Permutation;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	public Prog() {
		super();
		super.AddTasks(new Command() {
			public String name() {
				return "My sol";
			}

			public String execute() {
				return Prog.this.sol();
			}
		});
	}

	public String sol() {
		Integer[] v = IntStream.range(1, 10).boxed().toArray(Integer[]::new);
		Set<Integer> n = new HashSet<Integer>();
		Integer result = 0;
		for (int i = 9; i > 1; i--) {
			Permutation<Integer> a = new Permutation<Integer>(v, i);
			for (List<Integer> l : a) {
				StringBuilder b = new StringBuilder();
				l.forEach(b::append);
				int ll = Integer.valueOf(b.toString());
				if (Prime.isPrime(ll) && Euler.isPandigital(ll))
					result = ll;
			}
			if(result != 0)
				return String.valueOf(result);
		}
		return "";
	}

	@Override
	public String BruteForce() {
		ArrayList<Long> p = Prime.ESieve(10000000L);
		for (Long pp : p) {
			if (Euler.isPandigital(pp))
				return String.valueOf(pp);

		}
		return "";
	}

	@Override
	public String Smart() {
		for (int n = 9; n >= 1; n--) {
			int[] digits = new int[n];
			for (int i = 0; i < digits.length; i++)
				digits[i] = i + 1;

			int result = -1;
			do {
				if (utils.Library.isPrime(toInteger(digits)))
					result = toInteger(digits);
			} while (utils.Library.nextPermutation(digits));
			if (result != -1)
				return Integer.toString(result);
		}
		throw new RuntimeException("Not found");
	}

	private static int toInteger(int[] digits) {
		int result = 0;
		for (int x : digits)
			result = result * 10 + x;
		return result;
	}

}
