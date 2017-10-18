package E001;

import java.math.BigInteger;

import base.EulerProg;

public class Prog20_FactorialDigitalSum implements EulerProg {
	public static void main(String[] args) {
		System.out.println(new Prog20_FactorialDigitalSum().run());
	}

	public String run() {
		BigInteger tmp = new BigInteger("1");
		for(int i = 1 ; i <= 100 ; i++) {
			tmp = tmp.multiply(new BigInteger(String.valueOf(i)));
		}
		long sum = 0;
		for(char c : tmp.toString().toCharArray()) {
			sum += Character.getNumericValue(c);
		}
		return String.valueOf(sum);
	}
}
