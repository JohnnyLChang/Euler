package Prog36_DoublebasePalindromes;

import base.EulerProgBase;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}
	
	private boolean isPalindrome(String p) {
		char[] s = String.valueOf(p).toCharArray();
		for(int i=0;i<s.length/2;++i) {
			if(s[i] != s[s.length - i - 1])
				return false;
		}
		return true;
	}
	
	private boolean Palindromes10(int n) {
		return isPalindrome(String.valueOf(n));
	}
	
	private String base2(int n) {
		String ret = "";
		do {
			ret += n%2;
			n = n/2;
		}while(n>0);
		return ret;
	}
	
	private boolean Palindromes2(int n) {
		return isPalindrome(String.valueOf(base2(n)));
	}
	
	@Override
	public String BruteForce() {
		long sum = 0;
		for(int i=1;i<1000000;++i) {
			if(Palindromes10(i) && Palindromes2(i))
				sum += i;
		}
		return String.valueOf(sum);
	}

	@Override
	public String Smart() {
		long sum = 0;
		return String.valueOf(sum);
	}

}


