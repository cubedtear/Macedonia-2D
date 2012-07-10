package net.m2d.blocks;

public class BlockLeaves extends Block {

    BlockLeaves() {
        super(nextFreeID(), "Leaves");
        this.setHardness(100);
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

}
