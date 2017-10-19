package utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class jjjTest {

	@Test
	public void testSwapXOR() {
		Integer a = 1234;
		Integer b = 5678;
		jjj.swapAAS(a, b);
		a = a ^ b;
	    b = b ^ a;
	    a = a ^ b; 
		assertEquals(5678, a.intValue());
		assertEquals(1234, b.intValue());
	}

	@Test
	public void testSwapAAS() {
		Integer a = 1234;
		Integer b = 5678;
		jjj.swapAAS(a, b);
		a = a + b;
	    b = a - b;
	    a = a - b;
		assertEquals(5678, a.intValue());
		assertEquals(1234, b.intValue());
	}

}
