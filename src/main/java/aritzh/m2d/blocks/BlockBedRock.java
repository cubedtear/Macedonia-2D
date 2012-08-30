package aritzh.m2d.blocks;

public class BlockBedRock extends Block {

    public BlockBedRock() {
        super("BedRock");
        this.setBlockUnbreakable();
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

}
