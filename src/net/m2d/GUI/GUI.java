package net.m2d.GUI;

import net.m2d.main.Drawable;

abstract class GUI implements Drawable {

    private Stack[] stacks;
    protected int slotAmount;

    public Stack[] getStacks() {
        return stacks;
    }

    public void setSlot(int index, Stack stack) {
        stacks[index] = stack;
    }

}
