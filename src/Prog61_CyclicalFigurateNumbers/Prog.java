package Prog61_CyclicalFigurateNumbers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import base.EulerProgBase;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	private int figurate(int s, int n) {
		switch (s) {
		case 3:
			return n * (n + 1) / 2;
		case 4:
			return n * n;
		case 5:
			return n * (3 * n - 1) / 2;
		case 6:
			return n * (2 * n - 1) / 2;
		case 7:
			return n * (5 * n - 3) / 2;
		case 8:
			return n * (3 * n - 2);
		}
		return 0;
	}

	@Override
	public String BruteForce() {
		long sum = 0;
		int n = 0, j = 0;
		Map[] maps = new HashMap[6];
		for (int i = 0; i < maps.length; ++i)
			maps[i] = new HashMap();

		for (int i = 3; i < 9; ++i) {
			while ((n = figurate(i, j)) < 999)
				j++;

			while ((n = figurate(i, j)) < 10000) {
				maps[i - 3].put(n / 100, n % 100);
				j++;
			}
		}

		return String.valueOf(sum);
	}

	private Set<Integer>[][] numbers;

	@Override
	public String Smart() {
		// Build table of numbers
		numbers = new Set[9][100];
		for (int i = 0; i < numbers.length; i++) {
			for (int j = 0; j < numbers[i].length; j++)
				numbers[i][j] = new HashSet<>();
		}
		for (int sides = 3; sides <= 8; sides++) {
			for (int n = 1;; n++) {
				int num = figurateNumber(sides, n);
				if (num >= 10000)
					break;
				if (num >= 1000)
					numbers[sides][num / 100].add(num);
			}
		}

		// Do search
		for (int i = 10; i < 100; i++) {
			for (int num : numbers[3][i]) {
				int temp = findSolutionSum(num, num, 1 << 3, num);
				if (temp != -1)
					return Integer.toString(temp);
			}
		}
		throw new AssertionError("No solution");
	}

	// Note: sidesUsed is a bit set
	private int findSolutionSum(int begin, int current, int sidesUsed, int sum) {
		if (sidesUsed == 0x1F8) {
			if (current % 100 == begin / 100)
				return sum;

		} else {
			for (int sides = 4; sides <= 8; sides++) {
				if (((sidesUsed >>> sides) & 1) != 0)
					continue;
				for (int num : numbers[sides][current % 100]) {
					int temp = findSolutionSum(begin, num, sidesUsed | (1 << sides), sum + num);
					if (temp != -1)
						return temp;
				}
			}
		}
		return -1;
	}

	private static int figurateNumber(int sides, int n) {
		return n * ((sides - 2) * n - (sides - 4)) / 2;
	}

}
