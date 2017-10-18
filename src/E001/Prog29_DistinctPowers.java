package E001;

import java.math.BigInteger;
import java.util.LinkedHashSet; 

public class Prog29_DistinctPowers extends EulerProgBase {

	public static void main(String[] args) {
		System.out.println(new Prog29_DistinctPowers().run());
	}

	@Override
	public String run() {
		int a = 100;
		int b = 100;
		LinkedHashSet<BigInteger> lhs = new LinkedHashSet<BigInteger>();  
		for(int i=2 ; i<= a ; ++i) {
			BigInteger x = BigInteger.valueOf(i);
			for(int j=2 ; j<= b ; ++j) {
				lhs.add(x.pow(j));
			}
		}
		return String.valueOf(lhs.size());
	}
	
	public String SmartRun() {
		return "";
	}

}
