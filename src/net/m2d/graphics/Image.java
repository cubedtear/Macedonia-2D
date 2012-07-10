package net.m2d.graphics;

import net.m2d.main.Drawable;
import org.newdawn.slick.SlickException;

public class Image implements Drawable {

    private org.newdawn.slick.Image img;

    @Override
    public void draw() {
        img.draw(0, 0);

    }

    public void draw(int x, int y) {
        img.draw(x, y);
    }

    public Image(String path) {
        try {
            img = new org.newdawn.slick.Image(path);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}
