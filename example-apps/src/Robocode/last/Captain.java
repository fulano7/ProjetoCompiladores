package last;

import java.awt.Color;
import java.io.IOException;

import robocode.AdvancedRobot;
import robocode.BulletHitEvent;
import robocode.Condition;
import robocode.CustomEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.TurnCompleteCondition;

import java.io.IOException;

import robocode.*;

public class Captain extends TeamRobot {
	// avoiding walls
	private static final double DOUBLE_PI = (Math.PI * 2);
	private static final double HALF_PI = (Math.PI / 2);
	private static final double WALL_AVOID_INTERVAL = 10;
	private static final double WALL_AVOID_FACTORS = 20;
	private static final double WALL_AVOID_DISTANCE = (WALL_AVOID_INTERVAL * WALL_AVOID_FACTORS);
	double fieldHeight;
	double fieldWidth;
	double centerX = (fieldWidth / 2);
	double centerY = (fieldHeight / 2);
	double x, y, currentHeading, desiredY, desiredX, desiredBearing,
			distanceToWall;
	// fim avoiding walls
	// modos de movimento
	int direction = 1;
	final int SNAKE = 0;
	final int AVOIDINGWALL = 1;
	final int ATACKING = 2;
	final int BATTLE = 3;
	final int START = 4;
	int onda = 120;
	int modo = SNAKE;
	int dir = onda;
	double myX = 0;
	double myY = 0;
	double absoluteBearing = 0;
	double enemyX = 0;
	double enemyY = 0;

	public void run() {
		fieldHeight = getBattleFieldHeight();
		fieldWidth = getBattleFieldWidth();
		centerX = (fieldWidth / 2);
		centerY = (fieldHeight / 2);
		setColors(Color.cyan, Color.cyan, Color.cyan);
		setAdjustGunForRobotTurn(true);
		while (true) {
			switch (modo) {
			case SNAKE:
				setAhead(getDistanceRemaining() * direction);
				while (!nearWall()) {
					waitFor(new TurnCompleteCondition(this));
					setAhead(300 * direction);
					setTurnGunRight(360);
					setTurnLeft(dir);
					dir = -dir;
				}
				modo = AVOIDINGWALL;
				break;
			case AVOIDINGWALL:
				avooidWall();
				setAhead(100 * direction);
				execute();
				modo = SNAKE;
				break;
			}
		}

	}

	public boolean nearWall() {
		return (((getY() < WALL_AVOID_DISTANCE) || ((getBattleFieldHeight() - getY()) < WALL_AVOID_DISTANCE)) || ((getX() < WALL_AVOID_DISTANCE) || ((getBattleFieldHeight() - getX()) < WALL_AVOID_DISTANCE)));
	}

	public boolean veryNearWall() {
		return (((getY() < WALL_AVOID_DISTANCE / 2) || ((getBattleFieldHeight() - getY()) < WALL_AVOID_DISTANCE / 2)) || ((getX() < WALL_AVOID_DISTANCE / 2) || ((getBattleFieldHeight() - getX()) < WALL_AVOID_DISTANCE / 2)));
	}

	public void OnRobotDeath(RobotDeathEvent e) {
		if (!isFromTeam(e.getName())) {
			try {
				broadcastMessage(new Msg(Msg.MORREU, e.getName(), 0, 0));
			} catch (IOException e1) {
			}
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		if (!isFromTeam(e.getName())) {
			fire(3 - 2 * (e.getDistance() / 1000));
			myX = getX();
			myY = getY();
			absoluteBearing = getHeadingRadians() + e.getBearingRadians();
			enemyX = getX() + e.getDistance() * Math.sin(absoluteBearing);
			enemyY = getY() + e.getDistance() * Math.cos(absoluteBearing);
			try {
				broadcastMessage(new Msg(Msg.FOGO, e.getName(), enemyX, enemyY));
			} catch (IOException e1) {

			}
		}
	}

	public void onHitByBullet(HitByBulletEvent e) {

		if (!nearWall()) {
			reverse();
		}
		if (getEnergy() <= 10) {
			try {
				broadcastMessage(new Msg(Msg.FODEO, "", 0.0, 0.0));
			} catch (IOException e1) {

			}
		}

	}

	public void onHitRobot(HitRobotEvent e) {
		if (!isFromTeam(e.getName())) {
			ahead(-direction * 15);
		} else {
			fire(3);
		}
		reverse();
		if (getEnergy() <= 10) {
			try {
				broadcastMessage(new Msg(Msg.FODEO, "", 0.0, 0.0));
			} catch (IOException e1) {

			}
		}
	}

	public void onHitWall(HitWallEvent e) {
		reverse();
		if (getEnergy() <= 10) {
			try {
				broadcastMessage(new Msg(Msg.FODEO, "", 0.0, 0.0));
			} catch (IOException e1) {

			}
		}
	}

	public boolean isFromTeam(String name) {
		return name.contains("TeeHee");
	}

	public void reverse() {
		direction = -direction;
		setTurnRight(0);
		setAhead(direction * 50);
		turnGunRight(getGunHeading() - (getHeading() + 90 - 90 * direction));
	}

	// avoidwalls
	private void avooidWall() {
		setTurnRightRadiansOptimal(adjustHeadingForWalls(0));
	}

	private double adjustHeadingForWalls(double heading) {
		currentHeading = getRelativeHeadingRadians();
		x = getX();
		y = getY();
		desiredY = centerY;
		desiredX = centerX;
		desiredBearing = calculateBearingToXYRadians(x, y, currentHeading,
				desiredX, desiredY);
		distanceToWall = Math.min(Math.min(x, (fieldWidth - x)),
				Math.min(y, (fieldHeight - y)));
		int wallFactor = (int) Math.min((distanceToWall / WALL_AVOID_INTERVAL),
				WALL_AVOID_FACTORS);
		return ((((WALL_AVOID_FACTORS - wallFactor) * desiredBearing) + (wallFactor * heading)) / WALL_AVOID_FACTORS);
	}

	public double getRelativeHeadingRadians() {

		return getHeadingRadians();
	}

	public void goBack() {
		direction = -direction;
		setAhead(getDistanceRemaining() * direction);
		turnRadarLeft(-(90 - 90 * direction) + getRadarHeading() - getHeading());
		setTurnRight(180);
		execute();

	}

	public void setTurnLeftRadiansOptimal(double angle) {
		double turn = normalizeRelativeAngleRadians(angle);
		if (Math.abs(turn) > HALF_PI) {
			// goBack();
			if (turn < 0) {
				turn = (HALF_PI + (turn % HALF_PI));
			} else if (turn > 0) {
				turn = -(HALF_PI - (turn % HALF_PI));
			}
		}
		setTurnLeftRadians(turn);
	}

	public void setTurnRightRadiansOptimal(double angle) {
		double turn = normalizeRelativeAngleRadians(angle);
		if (Math.abs(turn) > HALF_PI) {
			// goBack();
			if (turn < 0) {
				turn = (HALF_PI + (turn % HALF_PI));
			} else if (turn > 0) {
				turn = -(HALF_PI - (turn % HALF_PI));
			}
		}
		setTurnRightRadians(turn);
	}

	public double calculateBearingToXYRadians(double sourceX, double sourceY,
			double sourceHeading, double targetX, double targetY) {
		return normalizeRelativeAngleRadians(Math.atan2((targetX - sourceX),
				(targetY - sourceY)) - sourceHeading);
	}

	public double normalizeAbsoluteAngleRadians(double angle) {
		if (angle < 0) {
			return (DOUBLE_PI + (angle % DOUBLE_PI));
		} else {
			return (angle % DOUBLE_PI);
		}
	}

	public static double normalizeRelativeAngleRadians(double angle) {
		double trimmedAngle = (angle % DOUBLE_PI);
		if (trimmedAngle > Math.PI) {
			return -(Math.PI - (trimmedAngle % Math.PI));
		} else if (trimmedAngle < -Math.PI) {
			return (Math.PI + (trimmedAngle % Math.PI));
		} else {
			return trimmedAngle;
		}
	}

	// fim avoidWalls

}