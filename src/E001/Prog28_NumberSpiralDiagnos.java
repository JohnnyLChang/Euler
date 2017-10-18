package E001;

public class Prog28_NumberSpiralDiagnos extends EulerProgBase {

	public static void main(String[] args) {
		System.out.println(new Prog28_NumberSpiralDiagnos().run());

	}
	/*
	 * Starting with the number 1 and moving to the right in a clockwise direction a
	 * 5 by 5 spiral is formed as follows:
	 * 
	 * 21 22 23 24 25 20 7 8 9 10 19 6 1 2 11 18 5 4 3 12 17 16 15 14 13
	 * 
	 * 
	 * It can be verified that the sum of the numbers on the diagonals is 101.
	 * 
	 * What is the sum of the numbers on the diagonals in a 1001 by 1001 spiral
	 * formed in the same way?
	 * 
	 * 0: 1
	 * 
	 * 2: 3 5 7 9
	 * 
	 * 4: 13 17 21 25
	 * 
	 * 6: 31
	 * 
	 */

	@Override
	public String run() {
		int n = 501; // 1001 = 2n-1
		int sum = 1;
		int v = 1;
		for (int i = 1; i < n; ++i) {
			for(int j=0;j<4;j++) {
				v += 2*i; 
				sum += v;
			}
		}
		return String.valueOf(sum);
	}

}
