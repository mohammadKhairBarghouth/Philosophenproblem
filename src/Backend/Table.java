package Backend;

import java.util.Random;

import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Table {
	public Philosopher[] philosophers;  //all philosophers are saved in this array
	public Spoon[] spoons;				//all spoons are saved in this array
	public Point centricPoint;			/*the centric point is positioned in the center of the screen and all objects are
	 drawn objects are positioned in dependency on this point(for example they are rotated around this point*/
	public int numberOfPhilosophers;	
	public double drawingAngle;			//the angle the philosophers have to use in order to rotate themselves around the centric point probably
	
	Random r = new Random();
	public Table(int numberOfPhilosophers) {
		this.centricPoint = new Point(300,300,null);
		this.numberOfPhilosophers = numberOfPhilosophers;
		this.drawingAngle = 360/numberOfPhilosophers;
	}
	
	public void wakePhilosophers(Timeline tl ) {
		spoons = new Spoon[numberOfPhilosophers];
		philosophers = new Philosopher[numberOfPhilosophers];
		//init Spoons
		for(int i = 0; i < spoons.length; i++) {
			spoons[i] = new Spoon(i,this);
		}
		//init philosophers
		for(int i = 0; i < philosophers.length; i++) {
			philosophers[i] = new Philosopher(i, this);
			
		}
		//play timeline in order to draw the objects in the GUI;
		tl.play();
	}
	
	//every object draws itself based on the content of it´s method "draw()"
	public void draw(GraphicsContext g) {
		g.setFill(Color.GREENYELLOW);
		g.fillOval(150,150,350,350);
	}
	
	//is not used 
	public void drawInConsole() {
		while(true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for(int i = 0; i < spoons.length; i++) {
				System.out.print(" s["+spoons[i].indexOfPhilosopher+"] o");
				
			}
			System.out.println();
			for(int i = 0; i < philosophers.length; i++) {
				char s = ' ';
				if(philosophers[i].isPhilosophizing) {
					s='p'; 
				}else if(philosophers[i].isHungry) {
					s='h';
				}else {
					s= 'e';
				}
				System.out.print("     p"+philosophers[i].index+s+"");		
			}
			System.out.println();
		}
	}
}
