package Prog30_Digit_Fifth_Powers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import base.EulerProgBase;
import utils.collection.CombinationRepeat;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}
	
	private boolean tokenCompare(String s, long v) {
		String vstr = String.valueOf(v);
		if(s.length() != vstr.length()) return false;
		
		
		ArrayList<Character> setS = new ArrayList<Character>();
		ArrayList<Character> setV = new ArrayList<Character>();
		for(char c : s.toCharArray())
		    setS.add(c);
		for(char c : vstr.toCharArray())
		    setV.add(c);
		setS.sort((a, b) -> a.compareTo(b));
		setV.sort((a, b) -> a.compareTo(b));
		return setS.equals(setV);
	}
	
	@Override
	public String BruteForce() {
		long sum = 0;
		Integer[] v = IntStream.range(0, 10).boxed().toArray( Integer[]::new );
		CombinationRepeat<Integer> a = new CombinationRepeat<Integer>(v, 5);
        for(List<Integer> l : a) {
        	long tmp = 0;
        	for(Integer x : l) {
        		tmp += (long) Math.pow(x, 5);
        	}
        	if(tokenCompare(a.toString(l), tmp)) {
        		a.printPretty(l);
        		System.out.println(tmp);
        		sum += tmp;
        	}
        }
        CombinationRepeat<Integer> b = new CombinationRepeat<Integer>(v, 6);
        for(List<Integer> l : b) {
        	long tmp = 0;
        	for(Integer x : l) {
        		tmp += (long) Math.pow(x, 5);
        	}
        	if(tokenCompare(b.toString(l), tmp)) {
        		a.printPretty(l);
        		System.out.println(tmp);
        		sum += tmp;
        	}
        }
        CombinationRepeat<Integer> c = new CombinationRepeat<Integer>(v, 4);
        for(List<Integer> l : c) {
        	long tmp = 0;
        	for(Integer x : l) {
        		tmp += (long) Math.pow(x, 5);
        	}
        	if(tokenCompare(a.toString(l), tmp)) {
        		a.printPretty(l);
        		System.out.println(tmp);
        		sum += tmp;
        	}
        }
		return String.valueOf(sum);
	}

	@Override
	public String Smart() {
		long sum = 0;
		return String.valueOf(sum);
	}

}
