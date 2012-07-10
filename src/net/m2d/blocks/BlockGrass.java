package net.m2d.blocks;

class BlockGrass extends Block {

    public BlockGrass() {
        super(nextFreeID(), "Grass");
        this.setHardness(100);
    }

}
