package E001;

import java.util.List;
import java.util.ArrayList;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.function.Function;

/*


Let d(n) be defined as the sum of proper divisors of n (numbers less than n which divide evenly into n).
If d(a) = b and d(b) = a, where a ≠ b, then a and b are an amicable pair and each of a and b are called amicable numbers.

For example, the proper divisors of 220 are 1, 2, 4, 5, 10, 11, 20, 22, 44, 55 and 110; therefore d(220) = 284. The proper divisors of 284 are 1, 2, 4, 71 and 142; so d(284) = 220.

Evaluate the sum of all the amicable numbers under 10000.
 
 */
public class Prog21_AmicableNum implements EulerProg {
	public static void main(String[] args) {
		System.out.println(new Prog21_AmicableNum().run());
	}
	
	@Override
	public String run() {
		return String.valueOf(Amicable(10000));
	}

	
	private List<Integer> Divisors(long x) {
		List<Integer> t = new ArrayList<Integer>();
		t.add(1);
		for(int i = 2 ; i < Math.sqrt(x) ; ++i) {
			if( x%i == 0) {
		           t.add(i);
		           if(x/i!=i) {
		        	   t.add((int) x/i);
		           }
			}
		}
		return t;
	}
	
	private long Amicable(long t) {
		long sum = 0;

		for(long i=t ; i > 10 ; --i) {
			List<Integer> x = Divisors(i);
			long y = x.stream().mapToInt(Integer::intValue).sum();
			if(y == i) continue;
			List<Integer> z = Divisors(y);
			long a = z.stream().mapToInt(Integer::intValue).sum();
			if (a == i)
				sum += i;
		}
		
		return sum;
	}

}