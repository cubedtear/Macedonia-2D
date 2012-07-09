package net.m2d.main;

import net.m2d.blocks.Block;
import net.m2d.blocks.InstanceBlock;
import net.m2d.entity.Player;
import net.m2d.main.Game.State;
import net.m2d.main.Logger.Level;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;


/**
 * 
 * @author Aritzh
 *
 */
public class InputHandler {
	
	private static boolean[] keys = new boolean[256];
	private static boolean state;
	private static final int LEFT = 0, RIGHT = 1;
	private static final boolean PRESS = true, RELEASE = false;
	private static int x, y, blockx, blocky, button;
	
	private static World world;
	private static InstanceBlock block;
	private static Logger logger = new Logger("Input",Level.ALL);	

	public static void mouse(World world) {
		
		InputHandler.world = world;
		getBlock();
		
		if(Mouse.isButtonDown(LEFT)){
			
			block.smash();
		} else {
			block.resetSmash();
		}

		while (Mouse.next()) {
			
			getBlock();
			
			if(Mouse.getEventDWheel()>0){
				// Rueda arriba
				Game.getToolBar().setDTool(-1);
			} else if (Mouse.getEventDWheel()<0){
				// Rueda abajo
				Game.getToolBar().setDTool(1);
			}

			if (button == LEFT && state == PRESS) {
				leftPressed();
			}
			if (button == LEFT && state == RELEASE) {
				leftReleased();

			}
			if (button == RIGHT && state == PRESS) {
				rightPressed();
			}
			if (button == RIGHT && state == RELEASE) {
				rightReleased();
			}

		}
	}

	public static boolean[] keyboard(World world) {
		InputHandler.world = world;
		while (Keyboard.next()) {

			if (Keyboard.getEventKeyState())
				keys[Keyboard.getEventKey()] = true;
			else
				keys[Keyboard.getEventKey()] = false;

		

		if (keys[Keyboard.KEY_R]) {
			Display.sync(60);
			world.getPlayer().reset();
		}

		if (keys[Keyboard.KEY_C])
			world.loadFromFile("save.txt");
		if (keys[Keyboard.KEY_G])
			world.saveToFile("save.txt");

		if (keys[Keyboard.KEY_ESCAPE])
			if(Game.state == State.GAME) Game.state = State.MENU;
			else Game.close = true;
		if (keys[Keyboard.KEY_P])
			
			if(Game.frameLimit) Game.frameLimit = false;
			else Game.frameLimit = true;
		}
		if (keys[Keyboard.KEY_W]) {
			world.getPlayer().move(Player.UP);
		}
		if (keys[Keyboard.KEY_A]) {
			if(Game.state == State.GAME){
				world.getPlayer().move(Player.LEFT);
				Game.translate_x++;
			}
		}
		if (keys[Keyboard.KEY_D]) {
			if(Game.state == State.GAME){
				world.getPlayer().move(Player.RIGHT);
				Game.translate_x--;
			}
		}
		return keys;
	}

	private static void leftPressed() {

	}

	private static void leftReleased() {

	}

	private static void rightPressed() {
		logger.log("Clicked at:", Level.DEBUG);
		logger.log("X: " + x + " Y: " + y, Level.DEBUG);
		logger.log("Block (" + blockx + ", " + blocky + ") "
				+ block.getBlock().getName() + "(" + block.getBlock().id + ")", Level.DEBUG);
		logger.log("Solid: " + block.getBlock().isCollidable(), Level.DEBUG);

		if (Block.blocksList[block.getBlock().id + 1] != null) {
			world.setAt((int) blockx, (int) blocky, block.getBlock().id + 1);
		} else {
			world.setAt((int) blockx, (int) blocky, 0);
		}
	}

	private static void rightReleased() {

	}
	
	private static void getBlock(){
		x = Mouse.getX() - Game.translate_x;
		y = Mouse.getY() + Game.translate_y;
		state = Mouse.getEventButtonState();
		button = Mouse.getEventButton();

		blockx = x / Block.SIZE;
		blocky = (Game.HEIGHT - y) / Block.SIZE;
		block = world.getAt((int) blockx, (int) blocky);
		
		if(block == null){
			block = new InstanceBlock(Block.air, -100, -100);
		}
	}

}
