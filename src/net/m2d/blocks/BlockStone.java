package net.m2d.blocks;

public class BlockStone extends Block {

	public BlockStone() {
		super(nextFreeID(), "Stone");
		this.setHardness(200);
	}
}
