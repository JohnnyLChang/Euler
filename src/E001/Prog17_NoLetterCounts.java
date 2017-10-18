package E001;

import base.EulerProg;

public class Prog17_NoLetterCounts implements EulerProg {
	public static void main(String[] args) {
		System.out.println(new Prog17_NoLetterCounts().run());
	}
	
	 
	 public String run() {
	  int sum = 0;
	  for (int i = 1; i <= 1000; i++)
	   sum += toEnglish(i).length();
	  return Integer.toString(sum);
	 }
	 
	 
	 private static String[] ONES = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};  // Requires 0 <= n <= 9
	 private static String[] TEENS = {"ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
	 private static String[] TENS = {"twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
	 
	 
	 // Requires 0 <= n <= 99999
	 private static String toEnglish(int n) {
	  if (n < 0 || n > 99999)
	   throw new IllegalArgumentException();
	  
	  if (n < 100)
	   return tens(n);
	  else {
	   String big = "";
	   if (n >= 1000)
	    big += tens(n / 1000) + "thousand";
	   if (n / 100 % 10 != 0)
	    big += ONES[n / 100 % 10] + "hundred";
	   
	   return big + (n % 100 != 0 ? "and" + tens(n % 100) : "");
	  }
	 }
	 
	 
	 // Requires 0 <= n <= 99
	 private static String tens(int n) {
	  if (n < 10)
	   return ONES[n];
	  else if (n < 20)  // Teens
	   return TEENS[n - 10];
	  else
	   return TENS[n / 10 - 2] + (n % 10 != 0 ? ONES[n % 10] : "");
	 }

}
