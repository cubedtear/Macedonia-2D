package net.m2d.GUI;

import net.m2d.blocks.Block;
import net.m2d.blocks.InstanceBlock;
import net.m2d.main.Drawable;
import net.m2d.main.Game;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;


/**
 * @author Aritzh
 */
public class Stack implements Drawable {

    private int id, amount;
    UnicodeFont font;

    public boolean draw = true, selected = false;

    /**
     * @param id     ID of the block/item of the stack
     * @param amount Quantity of that block/item
     */
    public Stack(int id, int amount) {
        this.id = id;
        this.amount = amount;
        Font awtFont = new Font("Times New Roman", Font.BOLD, 12);
        font = new UnicodeFont(awtFont);

        font.getEffects().add(new ColorEffect(java.awt.Color.white));
        font.addAsciiGlyphs();
        try {
            font.loadGlyphs();
        } catch (SlickException ignored) {

        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void draw(int x, int y) {
        InstanceBlock bi = new InstanceBlock(Block.blocksList[getId()], x, y, Block.SIZE / 2, Block.SIZE / 2);
        bi.draw();
        font.drawString((x + Block.SIZE / 2) - font.getWidth(String.valueOf(amount)), (y + Block.SIZE / 2) - font.getHeight(String.valueOf(amount)), String.valueOf(amount), Color.white);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void draw() {
        draw(Mouse.getX() - Block.SIZE / 2, Game.HEIGHT - Mouse.getY() - Block.SIZE / 2);
    }


}
