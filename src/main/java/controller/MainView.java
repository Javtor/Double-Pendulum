package controller;

import javafx.scene.paint.Color;
import model.DoublePendulum;
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
	
	public static final double PEND_W = 15;
	public static final double PEND_H = 15;
	
	public static final double PIXEL_SIZE = 20;

	private DoublePendulum doublePendulum;
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
    private TextField txtMass2;

    @FXML
    private TextField txtLength2;

    @FXML
    private TextField txtAngle2;

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
    	drawPend();    	
    	timeThread = new TimeThread(this);
    	
    }
    
    private void defaultParameters() {
		txtMass.setText("1");
		txtMass2.setText("2");
		txtGravity.setText("9.81");
		txtLength.setText("1");
		txtLength2.setText("2");
		txtAngle.setText("60");
		txtAngle2.setText("-30");
		txtFriction.setText("0.1");
		txtVelocity.setText("0");
	}

	public void drawPend() {
    	double x1 = doublePendulum.getX1();
    	double y1 = -doublePendulum.getY1();
    	double x2 = doublePendulum.getX2();
    	double y2 = -doublePendulum.getY2();
    	drawPend(x1, y1, x2, y2);
    }
    
    public void drawPend(double x1, double y1, double x2, double y2) {
    	GraphicsContext gc = canvas.getGraphicsContext2D();
    	gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    	drawPend(gc, x1, y1, x2, y2);
    }
    
    private void drawPend(GraphicsContext gc, double rawX1, double rawY1, double rawX2, double rawY2) {
    	gc.setFill(Color.RED);

    	double width = canvas.getWidth()/2;
    	double height = canvas.getHeight()/2;
    	
    	rawX1 *= (height/2)/doublePendulum.getL1();
    	rawY1 *= (height/2)/doublePendulum.getL1();
    	
    	rawX2 *= (height/2)/doublePendulum.getL1();
    	rawY2 *= (height/2)/doublePendulum.getL1();
    	
    	rawX1 += width;
    	rawX2 += width;
//    	rawY += height;
    	
    	double x1 = rawX1 - PEND_W/2;
    	double y1 = rawY1 - PEND_H/2;
    	
    	double x2 = rawX2 - PEND_W/2;
    	double y2 = rawY2 - PEND_H/2;
    	
//    	//Eje y
//    	gc.strokeLine(width, 0, width, 2*height);
//    	//Eje x
//    	gc.strokeLine(0, height, width*2, height);
    	
    	
    	gc.setLineDashes(null);
    	gc.strokeLine(width, height*3/4, rawX1, rawY1+height*3/4);
    	
    	gc.strokeLine(rawX1, rawY1+height*3/4, rawX2, rawY2+height*3/4);
    	
//    	System.out.println(Math.sqrt(Math.pow(width-rawX, 2)+Math.pow(-rawY, 2)));
    	
    	
    	gc.fillOval(x1, y1+height*3/4, PEND_W, PEND_H);
    	gc.setFill(Color.BLUE);
    	gc.fillOval(x2, y2+height*3/4, PEND_W, PEND_H);
    	
    }
    
    @FXML
    void stop(ActionEvent event) {
    	timeThread.setPlaying(false);
    }

	public void initPendulum() {
//		double height = canvas.getHeight()/2;
		try {
			double angle = Double.parseDouble(txtAngle.getText());
			double angle2 = Double.parseDouble(txtAngle2.getText());
			double length = Double.parseDouble(txtLength.getText());
			double length2 = Double.parseDouble(txtLength2.getText());
			double mass = Double.parseDouble(txtMass.getText());
			double mass2 = Double.parseDouble(txtMass2.getText());
			double gravity = Double.parseDouble(txtGravity.getText());
			double friction = Double.parseDouble(txtFriction.getText());
			double velocity = Double.parseDouble(txtVelocity.getText());
			
			doublePendulum = new DoublePendulum(angle, velocity, length, mass, 
					angle2, velocity, length2, mass2, 
					gravity, friction);
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Check your inputs");

			alert.showAndWait();
		}
	}

	public void step(double deltaTime) {
		doublePendulum.step(deltaTime);
		
	}
	

}
