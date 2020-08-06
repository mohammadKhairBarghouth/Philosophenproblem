package Backend;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Point {
	public double x, y;
	public Color color;
	public Point(double x, double y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public void rotate(Point cp, double rotation) {
		double lengthx = this.x - cp.x;
		double lengthy =  this.y - cp.y;
		double length = Math.sqrt((lengthx * lengthx)+(lengthy*lengthy));
		this.x = cp.x+(Math.cos(Math.toRadians(rotation))*length);
		this.y = cp.y-(Math.sin(Math.toRadians(rotation))*length);
	}
	
	public double getAngle (Point cp) {
		double x = this.x - cp.x;
		double y =  this.y - cp.y;
		double angle = Math.abs(Math.toDegrees(Math.atan(y/x)));
		if(x < 0 && y < 0) {
			angle = 90 - angle;
			angle+=90;
			System.out.println("angle"+ 90);
		}else if( x < 0 && y > 0) {
			angle+=180;
			System.out.println("angle"+ 180);
		}else if(x > 0 && y >0) {
			angle = 90 - angle;
			angle+=270;
			System.out.println("angle"+ 270);
		}
		return angle;
	}
	
	public void getDefault(Point cp) {
		System.out.println("getDefault");
		double lengthx = this.x - cp.x;
		double lengthy =  this.y - cp.y;
		double length = Math.sqrt((lengthx * lengthx)+(lengthy*lengthy));
		this.x = cp.x + length;
		this.y = cp.y;
	}
	
	public double charOf(double y) {
		return (y > 0) ? 1 : -1;
	}
	
	public void draw(Pane canvas) {
		Circle circle = new Circle(this.x,this.y,40);
		if(color != null) {
			circle.setFill(color);
		}
		canvas.getChildren().add(circle);
		System.out.println("dd");
	}
	
}
	