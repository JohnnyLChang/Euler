package utils.test;

import java.util.ArrayList;
import java.util.List;

public class Combination<T> {
	private int n, k, idx;
	public int cnt;
	private T[] v;
	private int[] idxArray;
	
	public Combination(T[] v, int k) {
		this.n = v.length;
		this.k = k;
		this.v = v;
		idx = k - 1;
		cnt = 0;
		idxArray = new int[k];
		for(int i=0; i<idx; ++i)
			idxArray[i] = i;
		idxArray[idx] = idx - 1;
	}
	
	public boolean hasNext() {
		return idxArray[0] < k - 1;
	}
	
	public int position() {
		for(int i=1; i <= k ; ++i) {
			if (idxArray[k-i] < n - i)
				return k-i;
		}
		return -1;
	}
	
	public List<T> next(){
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
	
	public void printPretty(List<T> v) {
		String s = "";
		for( T t: v)
			 s += String.valueOf(t)+",";
		System.out.println(s.substring(0, s.length() - 1));
	}
	
	public static void main(String[] args) {
		String[] s = {"a", "b" , "c", "d", "e"};
		Combination<String> test = new Combination<String>(s, 3);
		while(test.hasNext())
			test.printPretty(test.next());
		System.out.println(test.cnt);
	}

}
