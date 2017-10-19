package E001;

import base.EulerProg;

/*
https://en.wikipedia.org/wiki/Repeating_decimal

A unit fraction contains 1 in the numerator. The decimal representation of the unit fractions 
with denominators 2 to 10 are given:

    1/2	= 	0.5
    1/3	= 	0.(3)
    1/4	= 	0.25
    1/5	= 	0.2
    1/6	= 	0.1(6)
    1/7	= 	0.(142857)
    1/8	= 	0.125
    1/9	= 	0.(1)
    1/10	= 	0.1 

Where 0.1(6) means 0.166666..., and has a 1-digit recurring cycle. It can be seen that 1/7 has 
a 6-digit recurring cycle. Find the value of d < 1000 for which 1/d contains the longest 
recurring cycle in its decimal fraction part.

*/
public class Prog26_ReciprocalCycles implements EulerProg {
	private static final int LIMIT = 1000;  
	
	public static void main(String[] args) {
		System.out.println(new Prog26_ReciprocalCycles().run());
	}

	//find repeated suffixes
	public int recurringNum(int num) { 
        int[] arr = new int[num+1];  
        int index = 1;  
        int mod = 1;
        //執行條件:餘數不為零
        //沒有重覆:餘數不相同
        while(mod != 0 && arr[mod] == 0){  
             arr[mod]=index++;  
             mod = mod * 10 % num;  
        }  
        if (mod == 0){  
             return 0;  
        }  
        return index-arr[mod];  
    }
	
	@Override
	public String run() {
		int result = 0;  
        int longest = 0;  
        for (int i=2; i<LIMIT; i++){  
             int recurringNum = recurringNum(i);   
             if (recurringNum > longest){  
                  longest = recurringNum;  
                  result = i;  
             }  
        }  
		return String.valueOf(result);
	}
}
