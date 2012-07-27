package net.m2d.blocks;

import net.m2d.GUI.Stack;
import net.m2d.GUI.ToolBar;
import net.m2d.main.Drawable;
import net.m2d.main.Game;
import net.m2d.main.World;
import org.newdawn.slick.geom.Rectangle;


/**
 * @author Aritzh
 */
public class InstanceBlock implements Drawable {

    private Block block = null;
    private int x = 0, y = 0, width = Block.SIZE, height = Block.SIZE, broken = 0;
    private World world;


    public InstanceBlock(Block block, int x, int y, World world) {
        this.block = block;
        this.x = x;
        this.y = y;
        this.world = world;
    }

    public InstanceBlock(Block block, int x, int y, int w, int h) {
        this.block = block;
        this.x = x;
        this.y = y;
        this.height = h;
        this.width = w;
    }


    public Block getBlock() {
        return block;
    }

    public InstanceBlock setBlock(Block block) {
        this.block = block;
        this.broken = 0;
        return this;
    }

    public int getX() {
        return x;
    }

    public InstanceBlock setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public InstanceBlock setY(int y) {
        this.y = y;
        return this;
    }

    @Override
    public void draw() {

        block.draw(x, y, width, height);
    }

    public void smash() {
        InstanceBlock breakage = new InstanceBlock(Block.cracked, this.x + Game.translate_x, this.y - Game.translate_y, world);

        this.broken++;
        if (broken == block.hardness) {
            boolean done = false;
            ToolBar tb = world.getPlayer().getToolBar();
            int currPos = tb.getCurrToolPosition();
            int changedPositions = 0;
            while (!done || changedPositions >= 10) {
                if (tb.getCurrTool().getId() == block.drop().getId() || tb.getCurrTool().getId() == Block.air.id || tb.getCurrTool().getAmount() <= 0) {
                    tb.setCurrTool(new Stack(block.id, tb.getCurrTool().getAmount() + block.drop().getAmount()));
                    done = true;
                } else {
                    tb.setDTool(1);
                    changedPositions++;
                }
            }
            tb.setCurrToolPosition(currPos);
            this.block = Block.air;
            this.broken = 0;
        } else if (this.block != Block.air) {
            breakage.draw();
        }
    }

    public void resetSmash() {
        this.broken = 0;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }


}
