package net.m2d.GUI;

import net.m2d.blocks.Block;
import net.m2d.main.Drawable;
import net.m2d.main.Game;
import net.m2d.main.Logger;
import net.m2d.main.Logger.Level;

import org.newdawn.slick.Image;
import org.newdawn.slick.PackedSpriteSheet;
import org.newdawn.slick.SlickException;

// TH Añadir uso a la ToolBar

public class ToolBar implements Drawable{
	
	private int currTool;
	private Stack[] tools;
	Logger logger = new Logger("ToolBar", Level.ALL);
	private Image barTex, selTex;
	PackedSpriteSheet sh;

	@Override
	public void draw() {
		if(barTex!=null){
			barTex.draw((Game.WIDTH/2)-(barTex.getWidth()/2), Game.HEIGHT-barTex.getHeight());
			selTex.draw(((Game.WIDTH/2)-(barTex.getWidth()/2))-1 + currTool*20, Game.HEIGHT-selTex.getHeight());
			int x = 0;
			for(Stack s : tools){			
				s.draw((Game.WIDTH/2)-(barTex.getWidth()/2) + 3 + x * 20, Game.HEIGHT-barTex.getHeight() + (s.selected?2:3));
				x++;
			}
		}
	}
	
	private void getTexture() {
		
		String sheet = "res/gui.def";
		try {

			sh = new PackedSpriteSheet(sheet, Image.FILTER_NEAREST);
		} catch (SlickException e) {
			logger.log("Error al leer el PackedSpriteSheet:", Level.ERROR);
			logger.log(sheet, Level.ERROR);
			e.printStackTrace();
			System.exit(0);
		}
		barTex = sh.getSprite("toolbar");
		selTex = sh.getSprite("toolbar_sel");
	}


	public ToolBar() {
		tools = new Stack[10];
		getTexture();
		for(int x = 0; x<10; x++){
			tools[x] = new Stack(Block.air.id, 0);
		}
		tools[0].setSelected(true);
		
	}
	
	public void setDTool(int delta){
			tools[currTool].setSelected(false);
			if (currTool == 0 && delta == -1) currTool = 9;
			else if (currTool == 9 && delta == 1) currTool = 0;
			else currTool = (int) (currTool + delta);
			//toolSelRect.x = (hBarRect.x - 1) + currTool * 20;
			tools[currTool].setSelected(true);
	}
	
	public Stack getCurrTool(){
		return tools[currTool];
	}
	public void setCurrTool(Stack stack){
		tools[currTool] = stack;
	}
}
