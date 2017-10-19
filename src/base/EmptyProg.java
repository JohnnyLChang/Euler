package base;

public class EmptyProg extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new EmptyProg().run());
	}
	
	@Override
	public String BruteForce() {
		long sum = 0;
		return String.valueOf(sum);
	}

	@Override
	public String Smart() {
		long sum = 0;
		return String.valueOf(sum);
	}

}


