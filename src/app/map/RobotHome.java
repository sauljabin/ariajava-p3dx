package app.map;

public class RobotHome {
	private int mmX;
	private int mmY;
	private int degreeAngle;

	public int getMmX() {
		return mmX;
	}

	public void setMmX(int mmX) {
		this.mmX = mmX;
	}

	public int getMmY() {
		return mmY;
	}

	public void setMmY(int mmY) {
		this.mmY = mmY;
	}

	public int getDegreeAngle() {
		return degreeAngle;
	}

	public void setDegreeAngle(int degreeAngle) {
		this.degreeAngle = degreeAngle;
	}

	public RobotHome(int mmX, int mmY, int degreeAngle) {
		this.mmX = mmX;
		this.mmY = mmY;
		this.degreeAngle = degreeAngle;
	}

	public RobotHome() {
	}

	@Override
	public String toString() {
		return String.format("RobotHome [mmX=%s, mmY=%s, degreeAngle=%s]", mmX, mmY, degreeAngle);
	}

}
