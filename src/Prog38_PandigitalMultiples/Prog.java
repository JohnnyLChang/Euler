package Prog38_PandigitalMultiples;

import base.EulerProgBase;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}
	
	boolean[] pandigits;
	int pancount;
	
	private int isPandigits(int n) {
		while(n > 0) {
			if(pandigits[n%10])
				return -1;
			pandigits[n%10] = true;
			pancount++;
			n /= 10;
		}
		if(pancount < 9)
			return 0;
		if(pancount == 9)
			return 1;
		return 0;
	}
	
	
	@Override
	public String BruteForce() {
		long sum = 0;
		boolean run = true;
		int i = 9;
		while(run) {
			pandigits = new boolean[10];
			pandigits[0] = true;
			pancount = 0;
			int ret = -1, n=0;
			while((ret = isPandigits(i*n)) == 0) {
				n++;
			}
			if(ret == 1) {
				if(n == 1)
					break;
				sum = i;
				for(int j=1;j<=n;++j)
					System.out.println("i:"+i+"*"+j+"="+i*j);
			}
			i++;
		}
		return String.valueOf(sum);
	}

	private long concat(long a, long b) {
        long c = b;
        while (c > 0) {
            a *= 10;
            c /= 10;
        }
        return a + b;
    }

    private boolean isPandigital(long n) {
        int digits = 0;
        int count = 0;
        int tmp;

        while (n > 0) {
            tmp = digits;
            //The minus one is there to make 1 fill the first bit and so on
            digits = digits | 1 << (int)((n % 10) - 1); 

          //Check to see if the same digit is found multiple times
            if (tmp == digits) { 
                return false;
            }

            count++;
            n /= 10;
        }

        return digits == (1 << count) - 1;
    }

	@Override
	public String Smart() {
		long result = 0;
        for (long i = 9387; i >= 9234; i--) {
            result = concat(i, 2*i);
            if(isPandigital(result)){                    
                break;
            }
        }
		return String.valueOf(result);
	}

}


