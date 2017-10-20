package base;

import java.util.ArrayList;

public abstract class EulerProgBase implements EulerProg {
	private long startTime;
	protected String name;
	ArrayList<Command> tasks;

	public interface Command {
		public String name();

		public String execute();
	}

	public void AddTasks(Command e) {
		tasks.add(e);
	}

	public EulerProgBase() {
		tasks = new ArrayList<Command>();
		this.name = this.getClass().getPackage().getName();
		tasks.add(new Command() {
			public String name() {
				return "Brute Force";
			}

			public String execute() {
				return EulerProgBase.this.BruteForce();
			}
		});
		tasks.add(new Command() {
			public String name() {
				return "Smart";
			}

			public String execute() {
				return EulerProgBase.this.Smart();
			}
		});

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
		for (Command c : tasks) {
			printline();
			System.out.println(c.name());
			start();
			String ret1 = c.execute();
			elapse();
			System.out.println("Solutoin:" + ret1);
		}
		return "";
	}
}
