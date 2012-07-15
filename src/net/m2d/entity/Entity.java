package net.m2d.entity;

import net.m2d.blocks.InstanceBlock;
import net.m2d.main.Drawable;
import net.m2d.main.World;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

/**
 * @author Aritzh
 */

public abstract class Entity implements Drawable {

    public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;

    int width, height;
    int lastDirection = RIGHT;
    float dx, dy;
    protected boolean floor, draw, gravity;

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
        this.x += dx;
        this.y += dy;

        InstanceBlock[] coll = world.intersects(getRect());
        if (coll[UP] != null) {
            if (this.dy < 0) this.dy = 0;
        }
        if (coll[DOWN] != null) {
            this.floor = true;
        } else {
            this.floor = false;
        }
        if (coll[RIGHT] != null) {
            if (this.dx > 0) this.dx = 0;
        }
        if (coll[LEFT] != null) {
            if (this.dx < 0) this.dx = 0;
        }

        gravity(delta);

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

    Rectangle getRect() {
        return new Rectangle(getX(), getY(), width, height);
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
