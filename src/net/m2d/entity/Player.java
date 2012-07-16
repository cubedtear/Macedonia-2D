package net.m2d.entity;

import net.m2d.graphics.GraphUtil;
import net.m2d.main.Game;
import net.m2d.main.World;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

/**
 * @author Aritzh
 */
public class Player extends Entity {

    public Player(World world) {
        super(world);

        Image img = GraphUtil.getImgFromSheet("res/player.def", "plStop.png");
        super.setImg(img);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public Rectangle getRect() {
        return new Rectangle(getX(), getY(), width, height);
    }

    public void reset() {
        setX(-Game.translate_x + 10);
        setY(0);
    }

    public void move(int direccion) {
        super.move(direccion);

    }

    public int getDirection() {
        return lastDirection;
    }
}
