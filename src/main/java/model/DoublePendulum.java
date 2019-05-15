package model;

public class DoublePendulum {

	private double theta1;
	private double dTheta1;
	/**
	 * Length 1
	 */
	private double L1;
	/**
	 * Mass 1
	 */
	private double m1;

	private double theta2;
	private double dTheta2;
	/**
	 * Length
	 */
	private double L2;
	/**
	 * Mass
	 */
	private double m2;

	/**
	 * Gravity
	 */
	private double g;
	/**
	 * Friction
	 */
	private double k;

	public DoublePendulum(double theta1, double dTheta1, double l1, double m1, double theta2, double dTheta2, double l2,
			double m2, double g, double k) {
		super();
		setTheta1(theta1);
		setdTheta1(dTheta1);
		L1 = l1;
		this.m1 = m1;
		setTheta2(theta2);
		setdTheta2(dTheta2);
		L2 = l2;
		this.m2 = m2;
		this.g = g;
		this.k = k;
	}

	public double getTheta1() {
		return theta1;
	}

	public void setTheta1(double theta1) {
		this.theta1 = Math.toRadians(theta1);
	}

	public double getdTheta1() {
		return dTheta1;
	}

	public void setdTheta1(double dTheta1) {
		this.dTheta1 = Math.toRadians(dTheta1);
	}

	public double getL1() {
		return L1;
	}

	public void setL1(double l1) {
		L1 = l1;
	}

	public double getM1() {
		return m1;
	}

	public void setM1(double m1) {
		this.m1 = m1;
	}

	public double getTheta2() {
		return theta2;
	}

	public void setTheta2(double theta2) {
		this.theta2 = Math.toRadians(theta2);
	}

	public double getdTheta2() {
		return dTheta2;
	}

	public void setdTheta2(double dTheta2) {
		this.dTheta2 = Math.toRadians(dTheta2);
	}

	public double getL2() {
		return L2;
	}

	public void setL2(double l2) {
		L2 = l2;
	}

	public double getM2() {
		return m2;
	}

	public void setM2(double m2) {
		this.m2 = m2;
	}

	public double getG() {
		return g;
	}

	public void setG(double g) {
		this.g = g;
	}

	public double getK() {
		return k;
	}

	public void setK(double k) {
		this.k = k;
	}

	public void step(double dt) {
		theta1 += dTheta1 * dt;
		// dTheta1 += (-(g/L1)*Math.sin(theta1)-k*dTheta1)*dt;
		dTheta1 += (Math.cos(theta1-theta2)*(g*Math.sin(theta2)/L1-(dTheta1*dTheta1)*Math.sin(theta1-theta2))-
				(L2*(((m1/m2)+1)*g*Math.sin(theta1)/L2+(dTheta2*dTheta2)*Math.sin(theta1-theta2))/L1))
				/((m1/m2)+Math.sin(theta1-theta2)*Math.sin(theta1-theta2)) * dt;
		theta2 += dTheta2 * dt;
//		dTheta2 += (-(g / L2) * Math.sin(theta2) - k * dTheta2) * dt;
		dTheta2 += ((Math.cos(theta1-theta2)*(((m1/m2)+1)*g*Math.sin(theta1)/L2+(dTheta2*dTheta2)*Math.sin(theta1-theta2))
				-(((m1/m2)+1)*L1*(g*Math.sin(theta2)
						/L1-(dTheta1*dTheta1)*Math.sin(theta1-theta2))/L2))/((m1/m2)+Math.sin(theta1-theta2)*Math.sin(theta1-theta2)))
				* dt;

	}

	public double getX1() {
		return L1 * Math.sin(theta1);
	}

	public double getY1() {
//		return L*(1-Math.cos(theta));;
		return -L1 * Math.cos(theta1);
//		return 0;
	}

	public double getX2() {
		return getX1() + L2 * Math.sin(theta2);
//		return getX1(); 
	}

	public double getY2() {
		return getY1() - L2 * Math.cos(theta2);
//		return getY1()-L2;
//		return 0;
	}

}
