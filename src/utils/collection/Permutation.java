package utils.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Permutation<T> extends PermuteBase<T> {

	public Permutation(T[] v, Integer k) {
		super(v, k);
		idxArray = new int[k];
		for (int i = 0; i < k; ++i)
			idxArray[i] = i;
	}

	class ArrayIterator implements Iterator<List<T>> {
		int cnt = 0;
		boolean bnext = true;
		int tail = k - 1;

		public boolean hasNext() {
			return bnext;
		}

		private void chkNext() {
			bnext = false;
			for (int i = idxArray.length - 2; i >= 0; i--) {
				if (idxArray[i] < idxArray[i + 1]) {
					for (int j = idxArray.length - 1; j > i; j--) {
						if (idxArray[i] < idxArray[j]) {
							swap(i, j);
							reverse(i + 1, idxArray.length - 1);
							bnext = true;
							return;
						}
					}
				}
			}
		}

		private void swap(int l, int r) {
			idxArray[l] = idxArray[l] ^ idxArray[r];
			idxArray[r] = idxArray[r] ^ idxArray[l];
			idxArray[l] = idxArray[l] ^ idxArray[r];
		}

		private void reverse(int l, int r) {
			while (l < r)
				swap(l++, r--);
		}

		public List<T> next() {
			if (!this.hasNext())
				return null;

			List<T> ret = new ArrayList<>();
			for (int t : idxArray)
				ret.add(v[t]);

			chkNext();
			return ret;
		}
	}

	public Iterator<List<T>> iterator() {
		return new ArrayIterator();
	}
}