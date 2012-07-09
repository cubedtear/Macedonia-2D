package net.m2d.entity;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import net.m2d.main.*;

/**
 * 
 * @author Aritzh
 *
 */

public abstract class Entity implements Drawable {

	public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;

	protected int width, height, lastDirection = RIGHT;
	protected float dx, dy;
	protected boolean floor, draw;
	
	protected Image img;
	protected World world;
	
	private int x, y;

	public Entity(World world) {
		this.world = world;
	}

	public void draw() {
		if (lastDirection == LEFT){
			img.draw(getX()+img.getWidth(), getY(), -img.getWidth(), img.getHeight());
		} else {
			img.draw(getX(), getY(), img.getWidth(), img.getHeight());
		}
	}

	public void update(float delta) {
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.x = (int) (x + dx);
		this.y = (int) (y + dy);

	}

	public Rectangle getRect() {
		return new Rectangle(getX(), getY(), width, height);
	}

	public boolean intersects(Rectangle rect) {
		return rect.intersects(getRect());
	}
	
	public void setLastDir(int dir){
		lastDirection = dir;
	}

	public void setImg(Image img) {
		this.img = img;

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
