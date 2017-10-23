package Prog58_SpiralPrimes;

import java.util.ArrayList;

import base.EulerProgBase;
import utils.Library;
import utils.Prime.Prime;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	@Override
	public String BruteForce() {
		int noOfPrimes = 3;
		int sl = 2;
		int c = 9;
		 
		while( ((double)noOfPrimes)/(2*sl+1) > 0.10){
		    sl += 2;
		    for(int i = 0; i < 3; i++){
		        c += sl;
		        if(Prime.isPrime(c)) noOfPrimes++;
		    }
		    c+= sl;
		}
		return String.valueOf(sl+1);
	}

	@Override
	public String Smart() {
		long sum = 0;
		return String.valueOf(sum);
	}

}
