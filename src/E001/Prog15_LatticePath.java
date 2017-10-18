package E001;

import base.EulerProg;

public class Prog15_LatticePath implements EulerProg {
	public static void main(String[] args) {
		System.out.println(new Prog15_LatticePath().run());
	}
	

	
	public long LatticePaths(long x, long y) {
		long top = x+y;
		long sum = 1;
		for(long i = top; i > (top-y) ; --i) {
			if(i%2 == 0)
				sum = sum * 2;
			else
				sum = sum * i;
		}
		for(long i = y/2; i > 0 ; --i)
			sum = sum / i;
		return sum;
	}
	
	@Override
	public String run() {
		return String.valueOf(LatticePaths(20,20));
	}

}
