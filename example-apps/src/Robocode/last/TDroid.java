package last;

import java.awt.Color;

import robocode.*;
import robocode.util.Utils;

public class TDroid extends TeamRobot implements Droid {
	Enemys enemys;
	boolean peek; // Don't turn if there's a robot there
	double moveAmount;
	int direction;

	public void run() {
		setColors(Color.cyan, Color.cyan, Color.cyan, Color.cyan, Color.cyan);
		enemys = new Enemys();
		moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
		peek = false;
		turnLeft(getHeading() % 90);
		ahead(moveAmount);
		peek = true;
		turnGunRight(90);
		turnRight(90);
		direction = 1;

		while (true) {
			ahead(moveAmount * direction);
			int k  = enemys.getFirstVivo();
			turnGunRightRadians(getBearingInRadians(enemys.enemys[k].x, enemys.enemys[k].y));
			fire(1);
			fire(1);
			fire(1);
			turnRight(90);
		}
	}

	public void onMessageReceived(MessageEvent e) {
		Msg msg = (Msg) e.getMessage();
		if (msg.getMensagem() == Msg.FOGO) {
			direction = -direction;
			enemys.refresh(msg.getNome(), msg.getX(), msg.getY());
			turnGunRightRadians(getBearingInRadians(msg.getX(), msg.getY()));
			fire(3);
		} else if (msg.getMensagem() == Msg.MORREU) {
			enemys.morreu(msg.getNome());
		} else if (msg.getMensagem() == Msg.FODEO) {
		/*	while(true){
				int k  = enemys.getFirstVivo();
	//			stop();
				fire(3);
			}
		*/
		}
	}

	public double getBearingInRadians(double x, double y) {
		double theta = Utils.normalAbsoluteAngle(Math.atan2(x - getX(), y
				- getY()));
		return (Utils.normalRelativeAngle(theta - getGunHeadingRadians()));
	}

	public void onHitRobot(HitRobotEvent e) {
		if (e.getBearing() > -90 && e.getBearing() < 90) {
			back(100);
		} else {
			ahead(100);
		}
	}

	public void onHitByBulet(HitByBulletEvent e) {
		direction *= (-1);
	}
}

class Enemy {
	double x, y;
	String nome;
	boolean vivo;

	Enemy(String nome, double x, double y) {
		this.x = x;
		this.y = y;
		this.vivo = true;
		this.nome = nome;
	}

}

class Enemys {
	Enemy[] enemys;
	int adicionados;
	int mortos;

	public Enemys() {
		this.enemys = new Enemy[5];
		adicionados = 0;
		mortos = 0;
	}

	public void refresh(String nome, double x, double y) {
		if (adicionados < 5) {
			if (!Adicionou(nome)) {
				enemys[adicionados] = new Enemy(nome, x, y);
				adicionados++;
			}
		}
		int k = getIndexOf(nome);
		enemys[k].x = x;
		enemys[k].y = y;
	}

	public void morreu(String nome) {
		enemys[getIndexOf(nome)].vivo = false;
		mortos++;
	}

	public int getFirstVivo() {
		for (int i = 0; i < adicionados; i++) {
			if (enemys[i].vivo) {
				return i;
			}
		}
		return 0;
	}

	private int getIndexOf(String nome) {// pega o indice de um robo existente
		for (int i = 0; i < adicionados; i++) {
			if (enemys[i].nome.equals(nome)) {
				return i;
			}
		}
		return 0;
	}

	public boolean Adicionou(String nome) {// verifica se um rovo existe no
											// banco
		boolean adicionou = false;
		if (adicionados < 5) {
			for (int i = 0; i < adicionados && !adicionou; i++) {
				if (enemys[i].nome.equals(nome)) {
					adicionou = true;
				}
			}
		}
		return adicionou;
	}

}