package utils.collection;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class CombinationTest {

	@Test
	public void testInteger() {
		// create an array of strings
        Integer[] nums = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8};

        // create a new array to hold these strings
        Combination<Integer> array = new Combination<Integer>(nums, 5);
        Iterator<List<Integer>> it = array.iterator();
        int cnt = 0;
        
        for(List<Integer> l : array) {
        	cnt++;
        }
        assertEquals(56, cnt);
	}
	
	@Test
	public void testInteger2() {
		// create an array of strings
        Integer[] nums = new Integer[]{1, 2, 3, 4, 5};

        // create a new array to hold these strings
        Combination<Integer> array = new Combination<Integer>(nums, 3);
        Iterator<List<Integer>> it = array.iterator();
        int cnt = 0;
        
        for(List<Integer> l : array) {
        	cnt++;
        }
        assertEquals(10, cnt);
	}
	
	@Test
	public void testString() {
		// create an array of strings
        String[] nums = new String[]{"aaa", "bbb", "ccc", "ddd", "eeee"};

        // create a new array to hold these strings
        Combination<String> array = new Combination<String>(nums, 3);
        Iterator<List<String>> it = array.iterator();
        int cnt = 0;
        
        for(List<String> l : array) {
        	cnt++;
        }
        assertEquals(10, cnt);
	}
}
