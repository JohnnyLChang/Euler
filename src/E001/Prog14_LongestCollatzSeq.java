package E001;

import E001.EulerProg;

public class Prog14_LongestCollatzSeq implements EulerProg {
	public static void main(String[] args) {
		System.out.println(new Prog14_LongestCollatzSeq().run());
	}
			  

	private long CollatzSeq(long x) {
		if(x%2 == 0)
			return x/2;
		else
			return 3*x+1;
	}
	
	private long nextCollatzSeq(long x) {
		if(x%3 != 1)
			return x*2;
		else
			return x/3;
	}
	
	private int CountCollatzSeq(long x) {
		int count = 1;
		while((x = CollatzSeq(x)) != 1) {
			++count;
		}
		return ++count;
	}
	
	private void PrintCollatzSeq(long x) {
		String t = String.valueOf(x) + "->";
		while((x = CollatzSeq(x)) != 1) {
			t += String.valueOf(x) + "->";
		}
		t += String.valueOf(x) + "1";
		System.out.println(t);
	}
	
	private void PrintCollatzSeqTill(long upper) {
		long x  = 1;
		String t = String.valueOf(x) + "->";
		x = x*2;
		t += String.valueOf(x) + "->";
		while((x = nextCollatzSeq(x)) < upper) {
			t += String.valueOf(x) + "->";
		}
		System.out.println(t);
	}
	
	private long FindLongestCollatzSeq(long x) {
		long max = 0;
		long y = 0;
		for(long i = x ; i > 1 ; i--) {
			long ret = CountCollatzSeq(i);
			if (ret > max) {max = ret; y=i;}
		}
		//PrintCollatzSeqTill(y);		
		PrintCollatzSeq(y);
		return y;
	}
	
	@Override
	public String run() {
		return String.valueOf(FindLongestCollatzSeq(1000000));
	}
	
}