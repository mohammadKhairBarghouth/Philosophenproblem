package Backend;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Philosopher implements Runnable {
	int index;
	
	//--> current state
	boolean isHungry;
	boolean isEating;
	boolean isPhilosophizing;
	
	//--> spoons
	int IndexOfRightSpoon;
	int IndexOfLeftSpoon;

	public Thread thread;

	Table table;
	Random r = new Random();

	public Philosopher(int index, Table table) {
		this.table = table;
		this.index = index;
		
		this.isHungry = false;
		this.isEating = false;
		this.isPhilosophizing = true;
		
		this.IndexOfRightSpoon = -1;
		this.IndexOfLeftSpoon = -1;
		
		this.thread = new Thread(this);
		this.thread.start();
	}

	@Override
	public void run() {
		System.out.println("run");
		while (true) {

			if (this.isPhilosophizing) {
				philosophize();
			} else if (this.isHungry) {
				lookForSpoons();
			} else if (this.isEating) {
				eat();
			}

		}
	}

	private void philosophize() {
		try {
			Thread.sleep(6000 + r.nextInt(8000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.isPhilosophizing = false;
		this.isHungry = true;
	}

	private void lookForSpoons() {
		//initially, the philosopher waits for the left spoon, after taking it he pauses, and lastly he waits for the right spoon
		if (table.spoons[index].isFree) { // leftSpoon
			this.IndexOfLeftSpoon = table.spoons[index].takeMe(this.index);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		} else if (table.spoons[index].indexOfPhilosopher == this.index) {
			int IndexOfrightSpoon = this.index + 1; 
			if (IndexOfrightSpoon == table.numberOfPhilosophers) {
				IndexOfrightSpoon = 0;
			}
			if (table.spoons[IndexOfrightSpoon].isFree) { // rightSpoon
				this.IndexOfRightSpoon = table.spoons[IndexOfrightSpoon].takeMe(index);
				//if he has both spoons, he can eat:
				this.isHungry = false;
				this.isEating = true;
			}
		}
	}

	public void draw(GraphicsContext g) {
		String text;
		if (this.isHungry) {
			g.setFill(Color.INDIANRED);
			text = "is hungry ";
		} else if (this.isEating) {
			g.setFill(Color.LIGHTGREEN);
			text = "is eating";
		} else {
			g.setFill(Color.CORNFLOWERBLUE);
			text = "is philosophizing";
		}
		int c = 550;
		if (this.isEating) {
			c = 500;
		}
		Point p = new Point(c, 300, Color.AQUAMARINE);
		p.rotate(table.centricPoint, index * (table.drawingAngle));
		g.fillOval(p.x, p.y, 50, 50);
		g.strokeText(this.index + "", p.x + 20, p.y + 30);
		g.strokeText(text + "", p.x - 10, p.y + 65);

		p = new Point(420, 300, Color.AQUAMARINE);
		p.rotate(table.centricPoint, index * table.drawingAngle);

		g.setFill(Color.LIGHTGREY);
		g.fillOval(p.x, p.y, 50, 50);

		p = new Point(420, 300, Color.AQUAMARINE);
		p.rotate(table.centricPoint, index * table.drawingAngle);

		g.setFill(Color.DARKGREY);
		g.fillOval(p.x + 10, p.y + 10, 30, 30);
	}

	private void eat() {
		// philosopher eats:
		try {
			Thread.sleep(5000 + r.nextInt(7000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// philosopher lets leftSpoon:
		table.spoons[IndexOfLeftSpoon].letMe();
		this.IndexOfLeftSpoon = -1;

		// philosopher take a break before letting the right spoon:
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// philosopher lets rightSpoon:
		table.spoons[IndexOfRightSpoon].letMe();
		this.IndexOfRightSpoon = -1;

		// tell the philosopher to philosophize:
		this.isEating = false;
		this.isPhilosophizing = true;
	}
}
