import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.glu.GLU;


public class Player {
	private float[] player = {0, 0, 20};
	private float playerVelocity[] = {0, 0, 0};
	private float dimensions[] = {10, 10, 30};
	private float[] horDeg = {10, 0};
	private float verDeg = 0;
	public static final int LEFT = 0, RIGHT = 1, FORWARD = 2, BACK = 3;
	public static final float GRAVITY = .5f;
	public static final float PLAYERHEIGHT = 40;
	public static final float PLAYERJUMP = 13;
	private boolean lastCollisionZ = true;
	private boolean lastCollisionHor = true;
	private float dev = 1; // 10/SPEED of player

	public Player() {
		this(0, 15, 20, 0, 0, 0);
	}

	public Player(float a, float b, float c, float d, float e, float f) {
		player[0] = a;
		player[1] = b;
		player[2] = c;
		playerVelocity[0] = d;
		playerVelocity[1] = e;
		playerVelocity[2] = f;
	}

	public void move(int constant) {
		switch (constant) {
		case 0:
			playerVelocity[1] += horDeg[0] / dev;
			playerVelocity[0] -= horDeg[1] / dev;
			break;
		case 1:
			playerVelocity[1] -= horDeg[0] / dev;
			playerVelocity[0] += horDeg[1] / dev;
			break;
		case 2:
			playerVelocity[0] += horDeg[0] / dev;
			playerVelocity[1] += horDeg[1] / dev;
			break;
		case 3:
			playerVelocity[0] -= horDeg[0] / dev;
			playerVelocity[1] -= horDeg[1] / dev;
			break;

		}
	}
	public void jump() {
		if (lastCollisionZ)
			playerVelocity[2] += PLAYERJUMP;
	}
	public void look() {
		GLU.gluLookAt(player[0], player[1], player[2] + PLAYERHEIGHT, player[0] + horDeg[0] * 10, player[1] + horDeg[1] * 10, player[2]+verDeg*100, 0, 0, 1);
	}

	public void rotatex(float deg) {
		//if(Math.abs(deg) < .5 && Math.abs(deg) > .01){
		float temp = horDeg[0];
		horDeg[0] = (float) (horDeg[0] * Math.cos(deg) - horDeg[1] * Math.sin(deg));
		horDeg[1] = (float) (temp * Math.sin(deg) + horDeg[1] * Math.cos(deg));
		//}
	}

	public void rotatey(float deg) {
		verDeg = verDeg - (float)Math.sin(deg);
	}

	public void physics(ArrayList<ScreenObj> s) {
		playerVelocity[2] -= GRAVITY;
		lastCollisionZ = false;

		for (ScreenObj a : s) {
			if (a.isZCollision(player, playerVelocity, dimensions) ) {
				playerVelocity[2] = 0;
				lastCollisionZ = true;
			}
			a.horCollision(player, playerVelocity, dimensions);
		}
		for (int i = 0; i < 3; i ++) {
			player[i] += playerVelocity[i];
		}


		//if you fell to death
		if (player[2] < -700) {
			for (int i = 0; i < 3; i ++) {
				player[i] = 0;
				playerVelocity[i] = 0;
			}
			player[0] = 0;
			player[1] = 15;
			player[2] = PLAYERHEIGHT + 1;
		}

		playerVelocity[0] = 0;
		playerVelocity[1] = 0;
	}
}
