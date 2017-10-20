package Prog47_DistinctPrimesFactors;

import java.util.Set;

import base.EulerProgBase;
import utils.Prime.Prime;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}
	
	@Override
	public String BruteForce() {
		primeList = Prime.ESieve(10000).toArray(new Integer[0]);
		boolean notfound = true;
		int i = 647;
		int conti = 0;
		while(notfound) {
			if(NumberOfPrimeFacors(i) == 4) {
				conti++;
				if(conti == 4)
					break;
			}
			else
				conti = 0;
			++i;
		}
		return String.valueOf(i-3);
	}

	Integer[] primeList;
	
	private int NumberOfPrimeFacors(int number) {
	    int nod = 0;
	    boolean pf;
	    int remain = number;
	 
	    for (int i = 0; i < primeList.length; i++) {
	        // In case there is a remainder this is a prime factor as well
	        // The exponent of that factor is 1
	        if (primeList[i] * primeList[i] > number) {
	            return ++nod;
	        }
	 
	        pf = false;
	        while (remain % primeList[i] == 0) {
	            pf = true;
	            remain = remain / primeList[i];
	        }
	        if (pf){
	            nod++;
	        }
	 
	        //If there is no remainder, return the count
	        if (remain == 1) {
	            return nod;
	        }
	    }
	    return nod;
	}
	
	@Override
	public String Smart() {
		primeList = Prime.ESieve(10000).toArray(new Integer[0]);

        int targetpf = 4;
        int targetConsec = 4;
        int consec = 1;
        int result = 2 * 3 * 5 *7;

        while (consec < targetConsec) {
            result++;

            if (NumberOfPrimeFacors(result) >= targetpf) {
                consec++;
            } else {
                consec = 0;
            }
        }              
		return String.valueOf(result - 3);
	}

}


