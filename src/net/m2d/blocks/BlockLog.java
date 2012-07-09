package net.m2d.blocks;


public class BlockLog extends Block {

	public BlockLog() {
		super(nextFreeID(), "Log");
		this.setHardness(120);
	}
	
	@Override
	public boolean isCollidable() {
		return false;
	}

}
