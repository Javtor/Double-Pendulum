package controller;

import javafx.scene.paint.Color;
import model.Pendulum;
import threads.TimeThread;

import java.awt.GraphicsConfigTemplate;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class MainView {
	
	public static final double PEND_W = 20;
	public static final double PEND_H = 20;
	
	public static final double PIXEL_SIZE = 20;

	private Pendulum pendulum;
	private TimeThread timeThread;

    @FXML
    private Canvas canvas;

    @FXML
    private TextField txtMass;

    @FXML
    private TextField txtGravity;

    @FXML
    private TextField txtLength;

    @FXML
    private TextField txtAngle;
    
    @FXML
    private TextField txtFriction;
    
    @FXML
    private TextField txtVelocity;

    @FXML
    private Button btnStart;
    
    @FXML
    private Button btnStop;

    @FXML
    void start(ActionEvent event) {
    	
    	stop(null);
    	initPendulum();
    	timeThread = new TimeThread(this);
    	timeThread.start();
    }

    @FXML
    void initialize() {
    	defaultParameters();
    	initPendulum();
    	drawPend(0, 0);    	
    	timeThread = new TimeThread(this);
    	
    }
    
    private void defaultParameters() {
		txtMass.setText("1");
		txtGravity.setText("9.81");
		txtLength.setText("1");
		txtAngle.setText("-85");
		txtFriction.setText("0.1");
		txtVelocity.setText("0");
	}

	public void drawPend() {
    	double x = pendulum.getX();
    	double y = -pendulum.getY();
    	drawPend(x, y);
    }
    
    public void drawPend(double x, double y) {
    	GraphicsContext gc = canvas.getGraphicsContext2D();
    	gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    	drawPend(gc, x, y);
    }
    
    private void drawPend(GraphicsContext gc, double rawX, double rawY) {
    	gc.setFill(Color.RED);

    	double width = canvas.getWidth()/2;
    	double height = canvas.getHeight()/2;
    	
    	rawX *= height/pendulum.getL();
    	rawY *= height/pendulum.getL();
    	
    	rawX += width;
    	rawY += height;
    	
    	double x = rawX - PEND_W/2;
    	double y = rawY - PEND_H/2;
    	
//    	//Eje y
//    	gc.strokeLine(width, 0, width, 2*height);
//    	//Eje x
//    	gc.strokeLine(0, height, width*2, height);
    	
    	
    	gc.setLineDashes(null);
    	gc.strokeLine(width, height*3/4, rawX, rawY+height*3/4);
    	
//    	System.out.println(Math.sqrt(Math.pow(width-rawX, 2)+Math.pow(-rawY, 2)));
    	
    	gc.setLineDashes(10);
    	gc.strokeLine(width, height+height*3/4, width, height+height*3/4);
    	
    	gc.fillOval(x, y+height*3/4, PEND_W, PEND_H);
    	
    }
    
    @FXML
    void stop(ActionEvent event) {
    	timeThread.setPlaying(false);
    }

	public void initPendulum() {
//		double height = canvas.getHeight()/2;
		try {
			double angle = Double.parseDouble(txtAngle.getText());
			double length = Double.parseDouble(txtLength.getText());
			double mass = Double.parseDouble(txtMass.getText());
			double gravity = Double.parseDouble(txtGravity.getText());
			double friction = Double.parseDouble(txtFriction.getText());
			double velocity = Double.parseDouble(txtVelocity.getText());
			
			pendulum = new Pendulum(angle, velocity, length, mass, gravity, friction);
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Check your inputs");

			alert.showAndWait();
		}
	}

	public void step(double deltaTime) {
		pendulum.step(deltaTime);
		
	}

}
