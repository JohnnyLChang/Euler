package Prog68_Magic5_GonRing;

import java.util.List;
import java.util.stream.IntStream;

import base.EulerProgBase;
import utils.Library;
import utils.collection.Permutation;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	@Override
	public String BruteForce() {
		long sum = 0;
		Integer[] innernums = IntStream.range(1, 6).boxed().toArray(Integer[]::new);
		Integer[] outernums = IntStream.range(7, 11).boxed().toArray(Integer[]::new);
		Permutation<Integer> outerp = new Permutation<Integer>(outernums, 4);
		for (List<Integer> q : outerp) {
			Permutation<Integer> innerp = new Permutation<Integer>(innernums, 5);
			for (List<Integer> p : innerp) {
				if (6 + p.get(0) + p.get(1) == q.get(0) + p.get(1) + p.get(2)
						&& q.get(0) + p.get(1) + p.get(2) == q.get(1) + p.get(2) + p.get(3)
						&& q.get(1) + p.get(2) + p.get(3) == q.get(2) + p.get(3) + p.get(4)
						&& q.get(2) + p.get(3) + p.get(4) == q.get(3) + p.get(4) + p.get(0)) {
					System.out.print(""+6 + p.get(0) + p.get(1));
					System.out.print(""+q.get(0) + p.get(1) + p.get(2));
					System.out.print(""+q.get(1) + p.get(2) + p.get(3));
					System.out.print(""+q.get(2) + p.get(3) + p.get(4));
					System.out.print(""+q.get(3) + p.get(4) + p.get(0) + "\n");
				}
			}
		}
		System.out.println();
		return String.valueOf(sum);
	}

	@Override
	public String Smart() {
		int[] state = new int[10];
		for (int i = 0; i < state.length; i++)
			state[i] = i + 1;
		
		String max = null;
		do {
			int sum = state[0] + state[5] + state[6];
			if (   state[1] + state[6] + state[7] != sum
			    || state[2] + state[7] + state[8] != sum
			    || state[3] + state[8] + state[9] != sum
			    || state[4] + state[9] + state[5] != sum)
				continue;
			
			int minOuterIndex = -1;
			int minOuter = Integer.MAX_VALUE;
			for (int i = 0; i < 5; i++) {
				if (state[i] < minOuter) {
					minOuterIndex = i;
					minOuter = state[i];
				}
			}
			
			String s = "";
			for (int i = 0; i < 5; i++)
				s += "" + state[(minOuterIndex + i) % 5] + state[(minOuterIndex + i) % 5 + 5] + state[(minOuterIndex + i + 1) % 5 + 5];
			if (s.length() == 16 && (max == null || s.compareTo(max) > 0))
				max = s;
		} while (Library.nextPermutation(state));
		
		if (max == null)
			throw new AssertionError();
		return max;
	}

}
