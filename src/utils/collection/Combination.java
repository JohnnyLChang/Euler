package utils.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public class Combination<T> extends PermuteBase<T> {
    
    public Combination(T[] v, Integer k) {
        super(v, k);
        super.idxArray = new int[k];
		for(int i=0; i<k; ++i)
			idxArray[i] = i;
    }

    class ArrayIterator implements Iterator<List<T>> {
        int cnt = 0, pos = 0;
        boolean bnext = true;

        public boolean hasNext() {
        	return bnext;
        }
        
        private void chkNext() {
        	bnext = idxArray[0] < n - k;
        	if(bnext) {
        		pos = position();
        		idxArray[pos]++;
        		cnt++;
        		for(int j=pos+1; j<k ; ++j)
        			idxArray[j] = idxArray[j - 1] + 1;
        	}
        }
        
        public int position() {
        	for(int i=1; i <= k ; ++i) {
    			if (idxArray[k-i] < n - i)
    				return k-i;
    		}
			throw new NoSuchElementException("no element");
        }

        public List<T> next() {
        	if(!this.hasNext())
    			return null;
    		
    		List<T> ret = new ArrayList<>();
    		for(int t : idxArray)
    			ret.add(v[t]);
    		
    		chkNext();
    		return ret;
        }
    }

	@Override
	public Iterator iterator() {
		return new ArrayIterator();
	}
}