package aritzh.m2d.entity;

import aritzh.m2d.blocks.InstanceBlock;
import aritzh.m2d.main.Drawable;
import aritzh.m2d.main.Logger;
import aritzh.m2d.main.Logger.Level;
import aritzh.m2d.main.World;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

/**
 * @author Aritzh
 */

public abstract class Entity implements Drawable {

    public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3, OTHER = 4;

    int width, height;
    int lastDirection = RIGHT;
    float dx, dy;
    protected boolean floor, gravity;

    private Image img;
    World world;

    private int x, y;
    private static final int X_VEL = 1, Y_VEL = 2;

    private boolean[] stopDir = new boolean[5];

    protected Logger logger = new Logger(this.getClass().getSimpleName(), Level.ALL);

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
        delta /= 5;
        this.x += dx * delta;
        this.y += dy * delta;

        InstanceBlock[] coll = world.intersects(getRect());
        if (coll[UP] != null) {
            if (this.dy < 0) this.dy = 0;
            stopDir[UP] = true;
            coll[UP].getBlock().playerCollided();
        } else stopDir[UP] = false;

        if (coll[DOWN] != null) {
            this.floor = true;
            stopDir[DOWN] = true;
            coll[DOWN].getBlock().playerCollided();
        } else {
            this.floor = false;
            stopDir[DOWN] = false;
        }
        if (coll[RIGHT] != null) {
            if (this.dx > 0) this.dx = 0;
            stopDir[RIGHT] = true;
            coll[RIGHT].getBlock().playerCollided();
        } else stopDir[RIGHT] = false;

        if (coll[LEFT] != null) {
            if (this.dx < 0) this.dx = 0;
            stopDir[LEFT] = true;
            coll[LEFT].getBlock().playerCollided();
        }  else stopDir[LEFT] = false;
        dx = 0;

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

    public Rectangle getRect() {
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

    void move(int direccion) {
        if(!stopDir[direccion]){
            switch (direccion) {
                case LEFT:
                    this.dx = -1;
                    setLastDir(LEFT);
                    break;
                case RIGHT:
                    this.dx = X_VEL;
                    setLastDir(RIGHT);
                    break;
                case UP:
                    if (floor)
                        this.dy = -Y_VEL;
                    break;
                case OTHER:
                    //this.dx = 0;

            }
        }
    }

    public void setY(int y) {
        this.y = y;
    }


}
