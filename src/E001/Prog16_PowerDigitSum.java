package E001;

import java.math.BigInteger;

import base.EulerProg;

public class Prog16_PowerDigitSum implements EulerProg {
	public static void main(String[] args) {
		System.out.println(new Prog16_PowerDigitSum().run());
	}
	
	private long Power(long x) {
		BigInteger tmp = new BigInteger("2");
		tmp = tmp.pow(1000);
		char [] s = tmp.toString().toCharArray();
		long sum = 0;
		for(char c :s)
			sum += Character.getNumericValue(c);
		return sum;
	}

	@Override
	public String run() {
		return String.valueOf(Power(1000));
	}

}
