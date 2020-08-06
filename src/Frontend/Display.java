package Frontend;


import Backend.Table;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Display extends Application {
	private Stage mainWindow;
	private Table table;
	int oldValueOfSlider = 5;

	@Override
	public void start(Stage arg0) throws Exception {
		mainWindow = arg0;
		
		//--> initializing painting operators:
		Pane canvasContainer = new Pane();
		Canvas canvas = new Canvas(650, 700);
		GraphicsContext g = canvas.getGraphicsContext2D();	
		canvasContainer.getChildren().add(canvas);
		
		Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> draw(g)));
		tl.setCycleCount(Timeline.INDEFINITE);
		
		//--> initializing control operators
		
		
		Slider slider = new Slider();  //this slider enables the users to control the number of the philosophers
		slider.setValue(5);
		slider.setMinWidth(300);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMax(10);
		slider.setMin(5);
		
		table = new Table((int) slider.getValue());
		table.wakePhilosophers(tl);
		
		Label numberOfPhilosopherLabel = new Label("5"); //this label shows the number of current number of philosophers being set with the slider

		slider.valueProperty().addListener(e -> numberOfPhilosopherLabel.setText((int)(slider.getValue())+""));
		
		Button closeButton = new Button("Close");
		closeButton.setOnAction(e -> closingOperation(tl));

		Button pauseButton = new Button("Stop");
		pauseButton.setOnAction(e -> stopPhilosophers(tl));

		Button wakeButton = new Button("Start");
		wakeButton.setOnAction(e -> startPhilosophers(tl, slider));

		GridPane controlLayout = new GridPane();
		controlLayout.getChildren().addAll(closeButton, pauseButton, wakeButton,slider,numberOfPhilosopherLabel);

		GridPane.setConstraints(closeButton, 3, 0);
		GridPane.setConstraints(pauseButton, 0, 0);
		GridPane.setConstraints(wakeButton, 0, 1);
		GridPane.setConstraints(numberOfPhilosopherLabel, 1, 0);
	 	GridPane.setConstraints(slider, 2, 0);
		GridPane.setMargin(pauseButton,new Insets(5,5,5,5));
		GridPane.setMargin(wakeButton,new Insets(-5,5,5,5));
		
		controlLayout.setVgap(4);
		controlLayout.setHgap(4);
		
		pauseButton.setPrefWidth(70);
		wakeButton.setPrefWidth(70);
		
		numberOfPhilosopherLabel.setTranslateX(80);
		
		slider.setTranslateX(100);
		slider.setTranslateY(10);
		
		closeButton.setTranslateX(200);
		
		//--> collecting both, control and painting operators, in a one single main layout
		BorderPane mainLayout = new BorderPane();
		mainLayout.setCenter(canvasContainer);
		mainLayout.setBottom(controlLayout);
		
		//--> output
		Scene scene = new Scene(mainLayout);
		
		mainWindow.setWidth(680);
		mainWindow.setScene(scene);
		mainWindow.show();
		
		mainWindow.setOnCloseRequest(e->{
			e.consume();
			closingOperation(tl);
		});
		

	}
	
	//--> this method closes the program in an appropriate way(before closing the program, threads get stopped )
	private void closingOperation(Timeline tl) { 
		mainWindow.close();
		stopPhilosophers(tl);
	}
	
	//--> killing the threads of the philosophers
	private void stopPhilosophers(Timeline tl) {
		tl.stop();
		for (int i = 0; i < table.numberOfPhilosophers; i++) {
			table.philosophers[i].thread.stop();
		}
	}
	
	//--> initializing the philosopher threads again
	private void startPhilosophers(Timeline tl, Slider slider) {		
		stopPhilosophers(tl);
		table = null;
		table = new Table((int) slider.getValue());
		table.wakePhilosophers(tl);
	}
	
	//--> this method draws all objects to the screen
	private void draw(GraphicsContext g) {
		g.clearRect(0, 0, 650, 700);
		table.draw(g);
		for (int i = 0; i < table.numberOfPhilosophers; i++) {
			table.philosophers[i].draw(g);
		}
		for (int i = 0; i < table.numberOfPhilosophers; i++) {
			table.spoons[i].draw(g);
		}
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
