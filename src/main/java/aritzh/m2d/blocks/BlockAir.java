package aritzh.m2d.blocks;

public class BlockAir extends Block {

    public BlockAir() {
        super("Air");
        this.setBlockUnbreakable();
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

}
