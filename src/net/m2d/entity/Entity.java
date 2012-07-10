package net.m2d.entity;

import net.m2d.main.Drawable;
import net.m2d.main.World;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

/**
 * @author Aritzh
 */

public abstract class Entity implements Drawable {

    public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;

    int width;
    int height;
    int lastDirection = RIGHT;
    float dx;
    float dy;
    protected boolean floor, draw;

    private Image img;
    World world;

    private int x, y;

    Entity(World world) {
        this.world = world;
    }

    public void draw() {
        if (lastDirection == LEFT) {
            img.draw(getX() + img.getWidth(), getY(), -img.getWidth(), img.getHeight());
        } else {
            img.draw(getX(), getY(), img.getWidth(), img.getHeight());
        }
    }

    void update(float delta) {
        this.width = img.getWidth();
        this.height = img.getHeight();
        this.x = (int) (x + dx);
        this.y = (int) (y + dy);

    }

    Rectangle getRect() {
        return new Rectangle(getX(), getY(), width, height);
    }

    public boolean intersects(Rectangle rect) {
        return rect.intersects(getRect());
    }

    public void setLastDir(int dir) {
        lastDirection = dir;
    }

    void setImg(Image img) {
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
