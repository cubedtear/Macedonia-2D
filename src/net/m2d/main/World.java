package net.m2d.main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import net.m2d.blocks.Block;
import net.m2d.blocks.InstanceBlock;
import net.m2d.entity.Player;
import net.m2d.main.Logger.Level;

import org.newdawn.slick.geom.Rectangle;

/**
 * 
 * @author Aritzh
 *
 */
public class World implements Drawable {

	public static int BWIDTH = 50, BHEIGHT = 15;
	private InstanceBlock[][] blocks;
	private Player player;
	private Logger logger = new Logger(this.getClass().getName(), Level.ALL);

	public World() {
		blocks = new InstanceBlock[BWIDTH][BHEIGHT];
		loadFromFile("base.txt");
		player = new Player(this);		
	}

	public InstanceBlock getAt(int x, int y) {
		try {
			return blocks[x][y];
		} catch (Exception e) {
			logger.log("Block index out of bounds", Level.DEBUG);
			logger.log("X: " + x + ", Y: " + y, Level.ERROR);
		}
		return null;
	}

	public void setAt(int x, int y, int ID) {
		try {
			blocks[x][y].setBlock(Block.blocksList[ID]);
		} catch (Exception e) {
			logger.log("Block index out of bounds", Level.ERROR);
			logger.log("X: " + x + ", Y: " + y, Level.ERROR);
		}
	}
	public void setAt(int x, int y, Block block){
		blocks[x][y].setBlock(block);
	}

	public void draw() {
		for (int x = 0; x < BWIDTH; x++)
			for (int y = 0; y < BHEIGHT; y++) {
				if(!(blocks[x][y].getBlock() == Block.air))
				blocks[x][y].draw();
			}

		if (player != null) {
			player.draw();
		}

	}

	public void setDraw(boolean draw) {
		for (int x = 0; x < BWIDTH; x++)
			for (int y = 0; y < BHEIGHT; y++)
				blocks[x][y].getBlock().draw = true;
	}

	public String save() {
		String file = "";
		for (int y = 0; y < BHEIGHT; y++) {
			for (int x = 0; x < BWIDTH; x++) {
				file = file + blocks[x][y].getBlock().id + ";";
			}
			file = file + ";";
		}
		
		file = file + "/" + player.getX() + ";" + player.getY() + ";" + player.getDirection();
		return file;
	}

	public void saveToFile(String path) {
		try {
			Writer out = new OutputStreamWriter(new FileOutputStream(path));
			out.write(save());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void load(String mundo) {
		int px = 0, py = 0, pdir = 0;
		try {
			String[] partes = new String[2];
			partes = mundo.split("/");
			px = Integer.valueOf(partes[1].split(";")[0]);
			py = Integer.valueOf(partes[1].split(";")[1]);
			pdir = Integer.valueOf(partes[1].split(";")[2]);
			

			int y = 0;
			for (String fila : partes[0].split(";;")) {
				int x = 0;
				for (String id : fila.split(";")) {
					blocks[x][y] = new InstanceBlock(Block.blocksList[Integer.valueOf(id)], x*Block.SIZE, y*Block.SIZE);
					x++;
				}
				y++;
			}
		} catch (Exception e) {
			for (int x = 0; x < BWIDTH; x++)
				for (int y = 0; y < BHEIGHT; y++)
					blocks[x][y] = new InstanceBlock(Block.stone, x*Block.SIZE, y*Block.SIZE);
		}
		if(player!= null){
			player.setX(px);
			player.setY(py);
			player.setLastDir(pdir);
		}
		for(int x = 0; x<BWIDTH; x++){
			for(int y = 0; y<BHEIGHT; y++){
				if(blocks[x][y] == null){
					blocks[x][y] = new InstanceBlock(Block.stone, x*Block.SIZE, y*Block.SIZE);
				}
			}
		}
	}

	public void loadFromFile(String path) {
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new DataInputStream(
					new FileInputStream(path))));
			load(br.readLine());
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object[] intersects(Rectangle rectangle) {
		Object[] ret = new Object[2];
		for (int x = 0; x < BWIDTH; x++)
			for (int y = 0; y < BHEIGHT; y++) {
				Block block = blocks[x][y].getBlock();
				Rectangle rect = blocks[x][y].getRect();
				if (block.isCollidable()) {

					Rectangle up = new Rectangle(rectangle.getX(), rectangle.getY() - 1, rectangle.getWidth(),1);
					Rectangle right = new Rectangle(rectangle.getX() + rectangle.getWidth(), rectangle.getY(), 1, rectangle.getHeight());
					Rectangle down = new Rectangle(rectangle.getX(), rectangle.getY() + rectangle.getHeight(), rectangle.getWidth() + 1, 1);		
					Rectangle left = new Rectangle(rectangle.getX() - 1, rectangle.getY(), 1, rectangle.getHeight() + 1);
					
					if (rect.intersects(up)) {
						ret[0] = Player.UP;
						ret[1] = blocks[x][y];
						return ret;
					}
					if (rect.intersects(right)) {
						ret[0] = Player.RIGHT;
						ret[1] = blocks[x][y];
						return ret;
					}
					if (rect.intersects(down)) {
						ret[0] = Player.DOWN;
						ret[1] = blocks[x][y];
						return ret;
					}
					if (rect.intersects(left)) {
						ret[0] = Player.LEFT;
						ret[1] = blocks[x][y];
						return ret;
					}
				}
			}
		ret[0] = -1;
		ret[1] = null;
		return ret;
	}

	public Player getPlayer() {
		return player;
	}

	public void update(float delta){
		player.update(delta);
	}
	
}
