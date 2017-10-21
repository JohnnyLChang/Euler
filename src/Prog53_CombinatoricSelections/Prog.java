package Prog53_CombinatoricSelections;

import java.math.BigInteger;

import base.EulerProgBase;
import utils.Library;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}
	
	@Override
	public String BruteForce() {
		BigInteger MILLION = BigInteger.TEN.pow(6);
		int count = 0;
		for (int n = 1; n <= 100; n++) {
			for (int r = 0; r <= n; r++) {
				if (Library.binomial(n, r).compareTo(MILLION) > 0)
					count++;
			}
		}
		return Integer.toString(count);
	}

	@Override
	public String Smart() {
		int result = 0;
		final int limit = 1000000;
		final int nlimit = 100;
		 
		int[][] pascalTriangle = new int[nlimit + 1][nlimit/2 + 1];
		for (int n = 0; n <= nlimit; n++) {
		    pascalTriangle[n][0] = 1;
		}
		 
		for (int n = 1; n <= nlimit; n++) {
		    for (int r = 1; r <= n/2; r++) {
		        pascalTriangle[n][r] = pascalTriangle[n - 1][r] + pascalTriangle[n - 1][r - 1];
		        if (r == n / 2 && n % 2 == 0)
		            pascalTriangle[n][r] += pascalTriangle[n - 1][r - 1];
		 
		        if (pascalTriangle[n][r] > limit) {
		            pascalTriangle[n][r] = limit;
		            result += n - 2 * r + 1;
		            break;
		        }
		    }
		}
		/*for(int i=0;i<=nlimit;++i) {
			for(int j=0;j<=nlimit/2;++j) {
				System.out.print(pascalTriangle[i][j] + " ");
			}
			System.out.println();
		}*/
		return String.valueOf(result);
	}

}
