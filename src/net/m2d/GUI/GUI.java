package net.m2d.GUI;

import net.m2d.main.Drawable;

public abstract class GUI implements Drawable {
	
	protected Stack[] stacks;
	protected int slotAmount;
	
	public Stack[] getStacks(){
		return stacks;
	}
	
	public void setSlot(int index, Stack stack){
		stacks[index] = stack;
	}

}
