package net.m2d.blocks;

public class BlockGrass extends Block {

	public BlockGrass() {
		super(nextFreeID(), "Grass");
		this.setHardness(100);
	}

}
