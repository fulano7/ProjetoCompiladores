package last;

import java.awt.Color;

import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.ScannedRobotEvent;

public class Blind extends AdvancedRobot {
	double previousEnergy = 100;
	int movementDirection = 1;
	int gunDirection = 1;

	public void run() {
		setColors(Color.cyan, Color.cyan, Color.cyan);
		while (true) {
			turnGunRight(360);// scan
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		if (!isFromTeam(e.getName())) {
			setTurnRight(e.getBearing() + 90 - 30 * movementDirection);
			double changeInEnergy = previousEnergy - e.getEnergy();
			if (changeInEnergy > 0 && changeInEnergy <= 3) {
				movementDirection = -movementDirection;
				setAhead((e.getDistance() / 4 + 25) * movementDirection);
			}
			gunDirection = -gunDirection;
			setTurnGunRight(360 * gunDirection);
			//fire(Math.min(400 / e.getDistance(), 3));
			fire(3);
			previousEnergy = e.getEnergy();
		}
	}

	public boolean isFromTeam(String name) {
		return name.contains("TeeHee");
	}


}
