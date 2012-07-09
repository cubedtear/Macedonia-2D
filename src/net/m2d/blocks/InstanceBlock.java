package net.m2d.blocks;

import org.newdawn.slick.geom.Rectangle;

import net.m2d.main.Drawable;


/**
 * 
 * @author Aritzh
 *
 */
public class InstanceBlock implements Drawable{

	Block block = null;
	private int x = 0, y = 0, width = Block.SIZE, height = Block.SIZE, broken = 0;


	public InstanceBlock(Block block, int x, int y) {
		this.block = block;
		this.x = x;
		this.y = y;
	}
	
	public InstanceBlock(Block block, int x, int y, int w, int h) {
		this.block = block;
		this.x = x;
		this.y = y;
		this.height = h;
		this.width = w;
	}
	
	
	public Block getBlock(){
		return block;
	}
	
	public InstanceBlock setBlock(Block block){
		this.block = block;
		this.broken = 0;
		return this;
	}

	public int getX() {
		return x;
	}

	public InstanceBlock setX(int x) {
		this.x = x;
		return this;
	}

	public int getY() {
		return y;
	}

	public InstanceBlock setY(int y) {
		this.y = y;
		return this;
	}

	@Override
	public void draw() {
		
		block.draw(x, y, width, height);
	}
	public void smash() {
				InstanceBlock breakage = new InstanceBlock(Block.blockTest, x, y);
				
				this.broken++;
				if(broken == block.hardness){
					this.block = Block.air;
					this.broken = 0;
					
				} else if (this.block != Block.air){
					breakage.draw();
				}
	}
	
	public void resetSmash(){
		this.broken = 0;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y, width, height);
	}
	
	
	
	
	
}
