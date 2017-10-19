package utils.collection;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class PermutationTest {

	@Test
	public void testPermuNumbers() {

		// create an array of strings
		Integer[] nums = new Integer[] { 1, 2, 3, 4, 5 };

		// create a new array to hold these strings
		Permutation<Integer> array = new Permutation<Integer>(nums, 3);
		Iterator<List<Integer>> it = array.iterator();
		int cnt = 0;

		for (List<Integer> l : array) {
			array.printPretty(l);
			cnt++;
		}
		assertEquals(6, cnt);
	}
	
	@Test
	public void testPermuNumbers2() {

		// create an array of strings
		Integer[] nums = new Integer[] { 1, 2, 3, 4, 5 };

		// create a new array to hold these strings
		Permutation<Integer> array = new Permutation<Integer>(nums, 4);
		Iterator<List<Integer>> it = array.iterator();
		int cnt = 0;

		for (List<Integer> l : array) {
			array.printPretty(l);
			cnt++;
		}
		assertEquals(24, cnt);
	}

}
