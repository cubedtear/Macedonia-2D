package net.m2d.entity;

import net.m2d.graphics.GraphUtil;
import net.m2d.main.Logger.Level;
import net.m2d.main.*;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

/**
 * @author Aritzh
 */
public class Player extends Entity {

	Image img;
	private boolean floor = false;
	private boolean gravity;
	private Logger logger = new Logger("Player", Level.ALL);

	public Player(World world) {
		super(world);
		
		img = GraphUtil.getImgFromSheet("res/player.def", "plStop.png");
		super.setImg(img);
	}

	@Override
	public void update(float delta) {
		Object[] coll = world.intersects(getRect());
		int collision = -1;
		if(coll[0] instanceof Integer){
			collision = (Integer) coll[0];
		} else {
		}
		switch (collision){
		
		case DOWN:
			this.floor = true;
			break;
		case UP:
			if(this.dy < 0) this.dy = 0;
			break;
		case LEFT:
			if(this.dx < 0) this.dx = 0;
			break;
		case RIGHT:
			if(this.dx > 0) this.dx = 0;
			break;	
		}
		if (collision != DOWN){
			this.floor = false;
//			if(collision != -1){
//				logger.log(String.valueOf(collision), Level.DEBUG);
//			}
		}
		
		gravity(delta);
		super.update(delta);

	}

	@Override
	public Rectangle getRect() {
		return new Rectangle(getX(), getY(), width, height);
	}

	public void reset() {
		setX(-Game.translate_x);
		setY(0);
	}

	private void gravity(float delta) {

		if (!floor) {
			this.dy += 0.05f;
			gravity = true;
		} else {
			if (gravity) {
				this.dy = 0;
				gravity = false;
			}
			// y -= 0.01;
		}

	}

	public void move(int direccion) {
		switch (direccion) {
		case LEFT:
			this.setX(this.getX() - 1);
			setLastDir(LEFT);
			break;
		case RIGHT:
			this.setX(this.getX() + 1);
			setLastDir(RIGHT);
			break;
		case UP:
			if (floor)
				this.dy -= 0.75f;
			break;
		}
	}

	public int getDirection() {
		return lastDirection;
	}
}
