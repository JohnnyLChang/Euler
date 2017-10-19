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
		idxArray[k - 1] = this.id​x - 1;
	}

	class ArrayIterator implements Iterator<List<T>> {
		int cnt = 0;

		public boolean hasNext() {
			for (int i = k - 1; i >= 0; ++i) {
				if(idxArray[i] != n - k)
					return true;
			}
			return false;
		}

		public int position() {
			for (int i = 1; i <= k; ++i) {
				if (idxArray[k - i] < n - i)
					return k - i;
			}
			throw new NoSuchElementException("no element");
		}

		public List<T> next() {
			if (!this.hasNext())
				return null;
			int pos = position();
			idxArray[pos]++;
			cnt++;
			for (int j = pos + 1; j < k; ++j)
				idxArray[j] = idxArray[j - 1] + 1;

			List<T> ret = new ArrayList<>();
			for (int t : idxArray)
				ret.add(v[t]);

			return ret;
		}
	}

	public Iterator<List<T>> iterator() {
		return new ArrayIterator();
	}
}