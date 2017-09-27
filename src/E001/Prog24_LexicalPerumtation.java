package E001;

class Father {
	int x = 100;
}

public class Prog24_LexicalPerumtation extends Father implements EulerProg {
	public static void main(String[] args) {
		System.out.println("NonAbundantSums:" + new Prog24_LexicalPerumtation().run());
	}

	@Override
	public String run() {
		// System.out.println(super.x);
		boolean ret = true;
		int[] elem = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int t = 1;
		do {
			if (t == 1000000) {
				for (int x : elem) {
					System.out.print(x);
				}
				System.out.println();
			}
			t++;
			ret = nextPermutation(elem);
		} while (ret);
		return null;
	}

	private void swap(int[] elem, int i, int j) {
		// ystem.out.printf("%d %d\n", elem[i], elem[j]);
		if (elem[i] != elem[j]) {
			elem[i] ^= elem[j];
			elem[j] ^= elem[i];
			elem[i] ^= elem[j];
		}
		// System.out.printf("%d %d\n", elem[i], elem[j]);
	}

	public boolean nextPermutation(int[] nums) {
		int i = nums.length - 2;
		while (i >= 0 && nums[i + 1] <= nums[i]) {
			i--;
		}
		if (i >= 0) {
			int j = nums.length - 1;
			while (j >= 0 && nums[j] <= nums[i]) {
				j--;
			}
			swap(nums, i, j);
		}
		reverse(nums, i + 1);
		if (i < 0)
			return false;
		return true;
	}

	private void reverse(int[] nums, int start) {
		int i = start, j = nums.length - 1;
		while (i < j) {
			swap(nums, i, j);
			i++;
			j--;
		}
	}
}
