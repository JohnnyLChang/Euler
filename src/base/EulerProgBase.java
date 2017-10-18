package base;

public abstract class EulerProgBase implements EulerProg {
	private long startTime;
	protected String name;

	public EulerProgBase() {
		this.name = this.getClass().getPackage().getName();
		info();
	}
	public void info() {
		System.out.println(name);
	}
	
	private void start() {
		startTime = System.nanoTime();
	}

	private void elapse() {
		long elapsed = (System.nanoTime() - startTime) / 1000;
		System.out.println(elapsed + " micro seconds");
	}
	
	private void printline() {
		System.out.println("=================================");
	}
	
	public String BruteForce() {
		return "Brute Force result";
	}
	
	public String Smart() {
		return "Smart result";
	}
	
	@Override
	public String run() {
		printline();
		System.out.println("Brute force solution");
		start();
		String ret1 = BruteForce();
		elapse();
		System.out.println("Solutoin:"+ret1);
		printline();
		System.out.println("Smart solution");
		start();
		String ret2 = Smart();
		elapse();
		System.out.println("Solutoin:"+ret2);
		printline();
		return "";
	}
}
