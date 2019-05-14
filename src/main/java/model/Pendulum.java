package model;

public class Pendulum {
	
	private double theta;
	private double dTheta;
	/**
	 * Length
	 */
	private double L;
	/**
	 * Mass
	 */
	private double m;
	/**
	 * Gravity
	 */
	private double g;
	/**
	 * Friction
	 */
	private double k;
	
	public Pendulum(double theta, double dTheta, double l, double m, double g, double k) {
		super();
		setTheta(theta);
		this.dTheta = dTheta;
		L = l;
		this.m = m;
		this.g = g;
		this.k = k;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = Math.toRadians(theta);
	}

	public double getDTheta() {
		return dTheta;
	}

	public void setDTheta(double dTheta) {
		this.dTheta = dTheta;
	}
	
	public void step(double dt) {
		theta += dTheta*dt;
		dTheta += (-(g/L)*Math.sin(theta)-k*dTheta)*dt;
//		dTheta += (-(g/L)*theta-k*dTheta)*dt;
	}
	
	public double getX() {
		return L*Math.sin(theta); 
	}
	
	public double getY() {
		return L*(1-Math.cos(theta));
//		return 0;
	}

	public double getL() {
		return L;
	}

	public void setL(double l) {
		L = l;
	}

	public double getM() {
		return m;
	}

	public void setM(double m) {
		this.m = m;
	}

	
}
