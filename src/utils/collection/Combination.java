package utils.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

public class Combination<T> implements Iterable<List<T>> {
    private final T[] v;
    private final int[] idxArray;
    private final Integer n, k, id​x;
    private final IterationOrder iteratorOrder;
    
    public Combination(T[] v, Integer k) {
        this.v = v;
        this.n = v.length;
        this.k = k;
        this.id​x = k - 1;
        this.iteratorOrder = IterationOrder.LEXICOGRAPHIC;
        idxArray = new int[k];
		for(int i=0; i<k; ++i)
			idxArray[i] = i;
		idxArray[k-1] = this.id​x - 1;
    }

    class ArrayIterator implements Iterator<List<T>> {
        int cnt = 0;

        public boolean hasNext() {
        	return idxArray[0] < n - k;
        }
        
        public int position() {
        	for(int i=1; i <= k ; ++i) {
    			if (idxArray[k-i] < n - i)
    				return k-i;
    		}
    		return -1;
        }

        public List<T> next() {
        	if(!this.hasNext())
    			return null;
    		int pos = position();
    		idxArray[pos]++;
    		cnt++;
    		for(int j=pos+1; j<k ; ++j)
    			idxArray[j] = idxArray[j - 1] + 1;
    		
    		List<T> ret = new ArrayList<>();
    		for(int t : idxArray)
    			ret.add(v[t]);
    		
    		return ret;
        }
    }

    public T get(int index) {
        return v[index];
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
		for( T t: v)
			 s += String.valueOf(t)+",";
		System.out.println(s.substring(0, s.length() - 1));
	}
    
    private enum IterationOrder {
        LEXICOGRAPHIC
    }
}