package utils.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class PermuteBase<T> implements Iterable<List<T>> {

	protected T[] v;
	protected int[] idxArray;
	protected Integer n, k, id​x;
	protected IterationOrder iteratorOrder;

	public PermuteBase() {
		this.v = null;
		this.idxArray = null;
		this.n = 0;
		this.k = 0;
		this.id​x = 0;
		this.iteratorOrder = IterationOrder.LEXICOGRAPHIC;

	}

	public PermuteBase(T[] v, Integer k) {
		this.v = v;
		this.n = v.length;
		this.k = k;
		this.id​x = k - 1;
		this.iteratorOrder = IterationOrder.LEXICOGRAPHIC;
		idxArray = new int[k];
	}

	public T get(int index) {
		return v[index];
	}

	class ArrayIterator implements Iterator<List<T>> {

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public List<T> next() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	/**
	 * Gets the size of the set from which combinations are drawn.
	 *
	 * @return the size of the universe.
	 */
	public int getN() {
		return this.v.length;
	}

	/**
	 * Gets the number of elements in each combination.
	 *
	 * @return the size of the subsets to be enumerated.
	 */
	public int getK() {
		return this.k;
	}

	public void set(int index, T value) {
		v[index] = value;
	}

	public int length() {
		return v.length;
	}

	public Iterator<List<T>> iterator() {
		return new ArrayIterator();
	}

	public void printPretty(List<T> v) {
		String s = "";
		for (T t : v)
			s += String.valueOf(t) + ",";
		System.out.println(s.substring(0, s.length() - 1));
	}

	public String toString(List<T> v) {
		String s = "";
		for (T t : v)
			s += String.valueOf(t);
		return s;
	}

	private enum IterationOrder {
		LEXICOGRAPHIC
	}
}
