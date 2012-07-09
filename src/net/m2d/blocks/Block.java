package net.m2d.blocks;

import java.util.Random;

import net.m2d.GUI.Stack;
import net.m2d.graphics.GraphUtil;
import net.m2d.main.Logger.Level;
import net.m2d.main.*;

import org.newdawn.slick.Image;


// TODO Añadir dureza, etc.
// TODO Más bloques


/**
 * 
 * @author Aritzh with help of Mojang
 *
 */
public class Block implements Drawable {

	public boolean draw = true;
	public static final int SIZE = 32;
	private Image tex;
	private String name;
	private static Logger logger = new Logger("Block", Level.ALL);

	public static final Block[] blocksList = new Block[4096];

	public static final Block air = new BlockAir(); // ID = 0
	public static final Block stone = new BlockStone(); // ID = 1
	public static final Block grass = new BlockGrass(); // ID = 2
	public static final Block dirt = new BlockDirt(); // ID = 3
	public static final Block wood = new BlockLog(); // ID = 4
	public static final Block leave = new BlockLeaves(); // ID = 5
	public static final Block obsidian = new BlockObsidian(); // ID = 6
	public static final Block bedrock = new BlockBedRock(); // ID = 7
	
	public static final Block blockTest = new BlockCracked(); // ID = 8
			
	public void addBlock(Block block, int id) {

		while (blocksList[id] != block) {
			if (blocksList[id] == null) {
				block.id = id;
				blocksList[id] = block;
			} else {
			logger.log("El id " + id + " esta ocupado por "
					+ blocksList[id] + ". Error al añadir " + this, Level.RELEASE_DEBUG);
			logger.log("Intentando usar el id: " + id++, Level.RELEASE_DEBUG);
			}
		}

	}

	/** ID of the block. */
	public int id;

	/** Indicates how many hits it takes to break a block. */
	protected int hardness;

	/** Indicates the blocks resistance to explosions. */
	protected float resistance;

	public double minX, minY, maxX, maxY;

	/**
	 * Determines how much velocity is maintained while moving on top of this
	 * block
	 */
	public float slipperiness;

	protected Block(int ID, String name) {
		this.slipperiness = 0.6F;
		this.name = name;
		tex = GraphUtil.getImgFromSheet("res/blocks.def", this.name.toLowerCase() + ".png");

		addBlock(this, ID);
		this.setBlockBounds(0.0F, 0.0F, 1.0F, 1.0F);
	}

	/**
	 * Sets the the blocks resistance to explosions. Returns the object for
	 * convenience in constructing.
	 */
	protected Block setResistance(float par1) {
		this.resistance = par1 * 3.0F;
		return this;
	}

	/**
	 * Sets how many hits it takes to break a block.
	 */
	protected Block setHardness(int hardness) {
		this.hardness = hardness;
		return this;
	}

	/**
	 * This method will make the hardness of the block equals to -1, and the
	 * block is indestructible.
	 */
	protected Block setBlockUnbreakable() {
		this.setHardness(-1);
		return this;
	}

	/**
	 * Returns the block hardness.
	 */
	public float getHardness() {
		return this.hardness;
	}

	/**
	 * Sets the bounds of the block. minX, minY, minZ, maxX, maxY, maxZ
	 */
	public void setBlockBounds(float minX, float minY, float maxX, float maxY) {
		this.minX = (double) minX;
		this.minY = (double) minY;
		this.maxX = (double) maxX;
		this.maxY = (double) maxY;
	}

	/**
	 * Returns if this block is collidable (only used by Fire). Args: x, y, z
	 */
	public boolean isCollidable() {
		return true;
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	public void updateTick(World par1World, int par2, int par3, int par4,
			Random par5Random) {
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which
	 * neighbor changed (coordinates passed are their own) Args: x, y, z,
	 * neighbor blockID
	 */
	public void onNeighborBlockChange(World par1World, int par2, int par3,
			int par4, int par5) {
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	public void onBlockAdded() {
	}

	/**
	 * Called whenever the block is removed.
	 */
	public void onBlockRemoved() {
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 * TODO Cambiar esto!
	 */
	public Stack drop() {
		return new Stack(this.id, 1);
	}

	/**
	 * Called when a block is placed using an item. Used often for taking the
	 * facing and figuring out how to position the item. Args: x, y, z, facing
	 */
	public void onBlockPlaced() {
	};

	public Block setName(String name) {
		this.name = name;
		return this;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * Metadata sensitive version of the default getHardness function.
	 * 
	 * @param meta
	 *            The block's current metatdata
	 * @return Block hardness
	 */
	public float getHardness(int meta) {
		return hardness;
	}

	public void draw(int x, int y) {
		draw(x, y, SIZE, SIZE);

	}

	@Deprecated
	public void draw() {
		draw(0,0);
		
	}

	public void draw(int x, int y, int width, int height) {
		if (tex != null && draw) {
			tex.draw(x, y, width, height);
		}
	}
	
	
	public static int nextFreeID(){
		int x = 0;
		for(Block b : blocksList){
			if(b == null){
				return x;
			}
			x++;
		}
		
		return -1;
	}

}
