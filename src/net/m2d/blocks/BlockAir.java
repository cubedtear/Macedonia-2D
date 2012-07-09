package net.m2d.blocks;

public class BlockAir extends Block {

	public BlockAir() {
		super(nextFreeID(), "Air");
		this.setBlockUnbreakable();
	}

	@Override
	public boolean isCollidable() {
		return false;
	}

}
