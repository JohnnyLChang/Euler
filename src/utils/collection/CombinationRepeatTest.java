package utils.collection;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class CombinationRepeatTest {

	@Test
	public void testCombinationRepeat() {
		// create an array of strings
        Integer[] nums = new Integer[]{1, 5, 10};

        // create a new array to hold these strings
        CombinationRepeat<Integer> array = new CombinationRepeat<Integer>(nums, 3);
        Iterator<List<Integer>> it = array.iterator();
        int cnt = 0;
        
        for(List<Integer> l : array) {
        	cnt++;
        }
        assertEquals(10, cnt);
	}

}
