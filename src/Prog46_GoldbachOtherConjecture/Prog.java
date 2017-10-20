package Prog46_GoldbachOtherConjecture;

import java.util.ArrayList;

import base.EulerProgBase;
import utils.Prime.Prime;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}
	
	private boolean isTwiceSquare(long number) {
	    double squareTest = Math.sqrt(number/2);
	    return squareTest == ((int)squareTest);
	}
	
	@Override
	public String BruteForce() {
		Long[] primeList = Prime.ESieve(10000).toArray(new Long[0]);
		int result = 1;
        boolean notFound = true;
                    
        while(notFound){
            result += 2;
                                            
            int j = 0;
            notFound = false;
            while (result >= primeList[j]) {
                if(isTwiceSquare(result-primeList[j])){
                    notFound = true;
                    break;
                }
                j++;
            }                
        }
		return String.valueOf(result);
	}

	@Override
	public String Smart() {
		long sum = 0;
		return String.valueOf(sum);
	}

}


