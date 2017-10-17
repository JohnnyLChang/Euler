package E001;

public abstract class EulerProgBase implements EulerProg {
	private long startTime;

	public void start() {
		startTime = System.nanoTime();
	}

	public void elapse() {
		long elapsed = (System.nanoTime() - startTime) / 1000;
		System.out.println(elapsed + " micro seconds");
	}
}
