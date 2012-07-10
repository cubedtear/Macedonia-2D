package net.m2d.blocks;

class BlockStone extends Block {

    public BlockStone() {
        super(nextFreeID(), "Stone");
        this.setHardness(200);
    }
}
