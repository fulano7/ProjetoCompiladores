package last;

import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.Color;

import robocode.*;
import robocode.util.*;

import java.awt.geom.*;

public class Smart extends AdvancedRobot {

	double previousEnergy = 100;
	int movementDirection = 1;
	int gunDirection = 1;
	int direction = 1;
	double bearingFromGun;
	double absoluteBearing;
	boolean aiming = false;
	double battleFieldHeight, battleFieldWidth;

	public void run() {
		setColors(Color.cyan, Color.cyan, Color.cyan);
		while (true) {
			if (!aiming) {
				turnGunRight(360);
			}
		}
	}

	double oldEnemyHeading = 0;
	double changeInEnergy = 0;

	public boolean isFromTeam(String name) {
		return name.contains("TeeHee");
	}

	double bulletPower, myX, myY, enemyX, enemyY, enemyHeading,
			enemuHeadingChane, enemyVelocity, enemyHeadingChange;
	double deltaTime, predictedX, predictedY;

	public void onScannedRobot(ScannedRobotEvent e) {
		if (!isFromTeam(e.getName())) {
			aiming = true;
			battleFieldHeight = getBattleFieldHeight();
			battleFieldWidth = getBattleFieldWidth();
			bulletPower = 3;// Math.min(3.0, getEnergy());
			myX = getX();
			myY = getY();

			absoluteBearing = getHeadingRadians() + e.getBearingRadians();
			enemyX = getX() + e.getDistance() * Math.sin(absoluteBearing);
			enemyY = getY() + e.getDistance() * Math.cos(absoluteBearing);
			enemyHeading = e.getHeadingRadians();
			enemyHeadingChange = enemyHeading - oldEnemyHeading;
			enemyVelocity = e.getVelocity();
			oldEnemyHeading = enemyHeading;

			deltaTime = 0;
			predictedX = enemyX;
			predictedY = enemyY;
			while ((++deltaTime) * (20.0 - 3.0 * bulletPower) < Point2D.Double
					.distance(myX, myY, predictedX, predictedY)) {
				predictedX += Math.sin(enemyHeading) * enemyVelocity;
				predictedY += Math.cos(enemyHeading) * enemyVelocity;
				enemyHeading += enemyHeadingChange;
				if (predictedX < 18.0 || predictedY < 18.0
						|| predictedX > battleFieldWidth - 18.0
						|| predictedY > battleFieldHeight - 18.0) {

					predictedX = Math.min(Math.max(18.0, predictedX),
							battleFieldWidth - 18.0);
					predictedY = Math.min(Math.max(18.0, predictedY),
							battleFieldHeight - 18.0);
					break;
				}

			}
			double theta = Utils.normalAbsoluteAngle(Math.atan2(predictedX
					- getX(), predictedY - getY()));

			setTurnRadarRightRadians(Utils.normalRelativeAngle(absoluteBearing
					- getRadarHeadingRadians()));
			setTurnGunRightRadians(Utils.normalRelativeAngle(theta
					- getGunHeadingRadians()));
			//fire(Math.min(500 / e.getDistance(), 3));
			fire(3);
			//
			double changeInEnergy = previousEnergy - e.getEnergy();
			setTurnRight(e.getBearing() + 90 - 30 * movementDirection);
			if (changeInEnergy > 0 && changeInEnergy <= 3) {
				movementDirection = -movementDirection;
				setAhead((e.getDistance() / 4 + 25) * movementDirection);

			}
			previousEnergy = e.getEnergy();
			//
			aiming = false;
			scan();
		}
	}

	public void onHitByBullet(HitByBulletEvent e) {
		setTurnRight(e.getBearing() + 90 - 30 * movementDirection);
		movementDirection = -movementDirection;
		setAhead((25) * movementDirection);
	}

}