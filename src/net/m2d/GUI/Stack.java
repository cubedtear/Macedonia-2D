package net.m2d.GUI;

import net.m2d.blocks.Block;
import net.m2d.blocks.InstanceBlock;
import net.m2d.main.Drawable;
import net.m2d.main.Game;
import org.lwjgl.input.Mouse;


/**
 * @author Aritzh
 */
public class Stack implements Drawable {

    private int id, amount;

    public boolean draw = true, selected = false;

    /**
     * @param id     ID of the block/item of the stack
     * @param amount Quantity of that block/item
     */
    public Stack(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void draw(int x, int y) {
        InstanceBlock bi = new InstanceBlock(Block.blocksList[getId()], x, y, Block.SIZE / 2, Block.SIZE / 2);
        bi.draw();
    }

    int getId() {
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
