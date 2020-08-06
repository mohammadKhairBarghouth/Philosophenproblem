package Backend;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Spoon {
	int indexOfPhilosopher, ownIndex;  //if spoon is free the indexOfPhilosopher equals -1
	volatile boolean isFree;		   /*this boolean object is a fundamental object on it's value the behavior of the philosophers while they are hungry depends:
	 									if the value is "true", the philosopher takes the spoon, which enables him to eat*/

	Table table;
	
	public Spoon(int index, Table table) {
		this.ownIndex = index;
		this.table = table;
		
		this.indexOfPhilosopher = -1;
		this.isFree = true;
	}
	
	//--> when the philosopher call this method, he takes the spoon
	public int takeMe(int index) {  
		isFree = false;
		this.indexOfPhilosopher = index;
		return this.ownIndex;
	}
	
	//--> when the philosopher call this method, he lets the spoon
	public void letMe()  {
		isFree = true;
		this.indexOfPhilosopher = -1;
	}
	
	public void draw(GraphicsContext g) {
		if(this.isFree) {
			g.setFill(Color.LAWNGREEN);
		}else {
			g.setFill(Color.RED);
		}
		Point p1 = new Point(460,325,null);
		p1.rotate(new Point(325,325,null), ownIndex * (table.drawingAngle)-(table.drawingAngle/2));
		
		Point p2 = new Point(420,325,null);
		p2.rotate(new Point(325,325,null), ownIndex * (table.drawingAngle)-(table.drawingAngle/2));
		
		g.fillOval(p2.x-5, p2.y-5, 10, 10);
		g.strokeLine(p1.x, p1.y, p2.x, p2.y);
		String text;
		if(this.indexOfPhilosopher == -1) {
			text = "is not used";
		}else {
			text = this.indexOfPhilosopher +"";
		}
		g.strokeText(text, p1.x, p1.y);
	}
}
