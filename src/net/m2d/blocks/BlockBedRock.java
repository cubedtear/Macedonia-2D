package net.m2d.blocks;

public class BlockBedRock extends Block {

	public BlockBedRock(){
		super(nextFreeID(), "BedRock");
		this.setBlockUnbreakable();
	}
	@Override
	public boolean isCollidable() {
		return true;
	}
	
}
