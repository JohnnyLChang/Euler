package E001;

import java.math.BigInteger;

public class Prog25_1000DigitsFib extends Father implements EulerProg {
	public static void main(String[] args) {
		System.out.println(new Prog25_1000DigitsFib().run());
	}

	@Override
	public String run() {
		BigInteger f1 = new BigInteger("1");
		BigInteger f2 = new BigInteger("1");
		int idx = 2;
		while(f2.toString().length() < 1000) {
			BigInteger t = f2; 
			f2 = f2.add(f1);
			f1 = t;
			idx++;
		}
		return String.valueOf(idx);
	}
}
