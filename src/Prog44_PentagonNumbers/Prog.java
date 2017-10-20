package Prog44_PentagonNumbers;

import java.util.ArrayList;
import java.util.List;

import base.EulerProgBase;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	private int Pentagon(int n) {
		return (n*(3*n-1))/2;
	}

	private boolean isPentagonPair(int n, int m) {
		return isPentagon[n + m] && isPentagon[m - n];
	}

	boolean[] isPentagon;
	int n = 100000000;
	List<Integer> pentagon = new ArrayList<Integer>();

	@Override
	public String BruteForce() {
		int a=0, b=0;
		int p = 0, i = 1, diff = Integer.MAX_VALUE;
		isPentagon = new boolean[n];
		while ((p = Pentagon(i)) < n) {
			isPentagon[p] = true;
			pentagon.add(p);
			i++;
		}
		for (i = 1; i < pentagon.size()/2; ++i) {
			for (int j = i - 1; j >= 0; --j) {
				if (isPentagonPair(pentagon.get(j), pentagon.get(i))) {
					if(pentagon.get(i) - pentagon.get(j) < diff) {
						diff = pentagon.get(i) - pentagon.get(j);
						a = pentagon.get(i);
						b = pentagon.get(j);
					}
				}
			}
		}
		System.out.println(a);
		System.out.println(b);
		return String.valueOf(diff);
	}

	@Override
	public String Smart() {
		int result = 0;
        boolean notFound = true;            
        int i = 1;
        int k = 0;

        while (notFound) {
            i++;
            int n = i * (3 * i - 1) / 2;
            for (int j = i-1; j > 0; j--) {
                int m = j * (3 * j - 1) / 2;
                if (isPentagonal(n - m) && isPentagonal(n + m)) {
                    result = n-m;
                    k = j;
                    notFound = false;
                    break;
                }
            }
        }
		return String.valueOf(result);
	}
	
	 private boolean isPentagonal(int number) {
         double penTest = (Math.sqrt(1 + 24 * number) + 1.0) / 6.0;
         return penTest == ((int)penTest);
     }
}
