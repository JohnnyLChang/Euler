package utils;

public class jjj {
	public static void swapXOR(Integer a, Integer b) {
		// In place swap using XOR 
	    a = a ^ b;
	    b = b ^ a;
	    a = a ^ b; 
	}
	
	public static void swapAAS(Integer a, Integer b) {
	    // In place swap using addition and subtraction 
	    a = a + b;
	    b = a - b;
	    a = a - b;
	}
}
