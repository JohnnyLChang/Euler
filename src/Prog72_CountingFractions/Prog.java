package Prog72_CountingFractions;

import base.EulerProgBase;
import utils.Library;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	private static final int LIMIT = Library.pow(10, 6);

	@Override
	public String BruteForce() {
		long sum = 0;
		int[] totients = Library.listTotients(LIMIT);
		for (int i = 2; i < totients.length; i++)
			sum += totients[i];
		return Long.toString(sum);
	}

	@Override
	public String Smart() {
		long sum = 0;
		return String.valueOf(sum);
	}

}
