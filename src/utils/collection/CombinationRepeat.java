package utils.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public class CombinationRepeat<T> extends PermuteBase<T> {
    
    public CombinationRepeat(T[] v, Integer k) {
        super(v, k);
        idxArray = new int[k];
		for(int i=0; i<k; ++i)
			idxArray[i] = 0;
    }

    class ArrayIterator implements Iterator<List<T>> {
        int cnt = 0;
        int pos = 0, curPos = 0;
        boolean bnext = true;
        
        public boolean hasNext() {
        	return bnext;
        }
        
        private void chkNext() {
        	bnext = false;
        	for(int i : idxArray) {
        		if(i < n - 1) {
        			bnext = true;
        			break;
        		}
        	}
        	
        	if(!bnext) return;
        	
        	pos = position();
    		idxArray[pos]++;
    		if(curPos != pos) {
    			curPos = pos;
    			for(int j=pos+1;j<k;++j)
    				idxArray[j] = idxArray[j-1];
    		}
    		cnt++;
        }
        
        public int position() {
        	for(int i=1; i <= k ; ++i) {
    			if (idxArray[k-i] < n-1)
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

    public Iterator<List<T>> iterator() {
        return new ArrayIterator();
    }
}