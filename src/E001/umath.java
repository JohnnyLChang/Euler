package E001;

public class umath {
	public static long lcm(long n, long m) {
		return m * n / gcd(m, n);
	}
	
	public static long gcd(long n, long m) {
        return n == 0 ? m : gcd(n, m % n);
	}
}
