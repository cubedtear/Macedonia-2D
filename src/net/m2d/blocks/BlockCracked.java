package net.m2d.blocks;

class BlockCracked extends Block {

    public BlockCracked() {
        super(nextFreeID(), "Cracked");
        this.setHardness(900);
    }

}
