package net.m2d.blocks;

public class BlockDirt extends Block {

	public BlockDirt() {
		super(nextFreeID(), "Dirt");
		this.setHardness(80);
	}

}
