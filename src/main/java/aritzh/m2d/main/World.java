
package aritzh.m2d.main;

import aritzh.m2d.blocks.Block;
import aritzh.m2d.blocks.InstanceBlock;
import aritzh.m2d.entity.Entity;
import aritzh.m2d.entity.Player;
import aritzh.m2d.main.Logger.Level;
import org.newdawn.slick.geom.Rectangle;

import java.io.*;

/**
 * @author Aritzh
 */
public class World implements Drawable {

    private static final int BWIDTH = 50;
    private static final int BHEIGHT = 15;
    private InstanceBlock[][] blocks;
    private Player player;
    private Entity[] entities;
    private final Logger logger = new Logger(this.getClass().getSimpleName(), Level.ALL);
    private int lastErrorX, lastErrorY;

    public World() {
        entities = new Entity[100];
        blocks = new InstanceBlock[BWIDTH][BHEIGHT];
        loadFromFile("base.txt");
        player = new Player(this);
        entities[0] = player;
    }

    public InstanceBlock getAt(int x, int y) {
        try {
            return blocks[x][y];
        } catch (IndexOutOfBoundsException e) {
//                logger.log("Block index out of bounds", Level.ERROR);
//                logger.log("X: " + x + ", Y: " + y, Level.ERROR);
        }
        return null;
    }

    public void setAt(int x, int y, int ID) {
        try {
            blocks[x][y].setBlock(Block.blocksList[ID]);
        } catch (IndexOutOfBoundsException e) {
//                logger.log("Block index out of bounds", Level.ERROR);
//                logger.log("X: " + x + ", Y: " + y, Level.ERROR);
        }
    }

    public void draw() {
        try{
            for (int x = 0; x < BWIDTH; x++)
                for (int y = 0; y < BHEIGHT; y++) {
                    if (!(blocks[x][y].getBlock() == Block.air))
                        blocks[x][y].draw();
                }

            if (player != null) {
                player.draw();
            }
        } catch (NullPointerException e){
            logger.log("NullPointerException in World.intersects(Rectangle)", Level.DEBUG);
            logger.log("Stack:", Level.DEBUG);
            logger.log(e.getMessage(), Level.DEBUG);
        }

    }

    String save() {
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

    void load(String mundo) {
        int px = 0, py = 0, pdir = 0;
        try {
            String[] partes;
            partes = mundo.split("/");
            px = Integer.valueOf(partes[1].split(";")[0]);
            py = Integer.valueOf(partes[1].split(";")[1]);
            pdir = Integer.valueOf(partes[1].split(";")[2]);


            int y = 0;
            for (String fila : partes[0].split(";;")) {
                int x = 0;
                for (String id : fila.split(";")) {
                    blocks[x][y] = new InstanceBlock(Block.blocksList[Integer.valueOf(id)], x * Block.SIZE, y * Block.SIZE, this);
                    x++;
                }
                y++;
            }
        } catch (Exception e) {
            for (int x = 0; x < BWIDTH; x++)
                for (int y = 0; y < BHEIGHT; y++)
                    blocks[x][y] = new InstanceBlock(Block.stone, x * Block.SIZE, y * Block.SIZE, this);
        }
        if (player != null) {
            player.setX(px);
            player.setY(py);
            player.setLastDir(pdir);
        }
        for (int x = 0; x < BWIDTH; x++) {
            for (int y = 0; y < BHEIGHT; y++) {
                if (blocks[x][y] == null) {
                    blocks[x][y] = new InstanceBlock(Block.stone, x * Block.SIZE, y * Block.SIZE, this);
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

    public InstanceBlock[] intersects(Rectangle rectangle) {
        InstanceBlock[] ret = new InstanceBlock[4];
        try{
            for (int x = 0; x < BWIDTH; x++){
                for (int y = 0; y < BHEIGHT; y++) {
                    Block block = blocks[x][y].getBlock();
                    Rectangle rect = blocks[x][y].getRect();
                    if (block.isCollidable()) {

                        Rectangle up = new Rectangle(rectangle.getX(), rectangle.getY() - 2, rectangle.getWidth(), 1);
                        Rectangle right = new Rectangle(rectangle.getX() + rectangle.getWidth() + 1, rectangle.getY(), 1, rectangle.getHeight());
                        Rectangle down = new Rectangle(rectangle.getX(), rectangle.getY() + rectangle.getHeight() + 1, rectangle.getWidth(), 1);
                        Rectangle left = new Rectangle(rectangle.getX() - 2, rectangle.getY(), 1, rectangle.getHeight());

                        if (rect.intersects(up)) {
                            ret[Player.UP] = blocks[x][y];
                        }
                        if (rect.intersects(right)) {
                            ret[Player.RIGHT] = blocks[x][y];
                        }
                        if (rect.intersects(down)) {
                            ret[Player.DOWN] = blocks[x][y];
                        }
                        if (rect.intersects(left)) {
                            ret[Player.LEFT] = blocks[x][y];
                        }
                    }
                }
            }
        } catch (NullPointerException e){
            logger.log("NullPointerException in World.intersects(Rectangle)", Level.DEBUG);
            logger.log("Stack:", Level.DEBUG);
            logger.log(e.getMessage(), Level.DEBUG);
        }
        return ret;
    }

    public boolean intersects(int x, int y, Rectangle rectangle){
        try{
            Rectangle up = new Rectangle(rectangle.getX(), rectangle.getY() - 2, rectangle.getWidth(), 1);
            Rectangle right = new Rectangle(rectangle.getX() + rectangle.getWidth() + 1, rectangle.getY(), 1, rectangle.getHeight());
            Rectangle down = new Rectangle(rectangle.getX(), rectangle.getY() + rectangle.getHeight() + 1, rectangle.getWidth(), 1);
            Rectangle left = new Rectangle(rectangle.getX() - 2, rectangle.getY(), 1, rectangle.getHeight());
            Rectangle rect = blocks[x][y].getRect();

            return (rect.intersects(up) || rect.intersects(right) || rect.intersects(down) || rect.intersects(left));
        } catch (NullPointerException e){
            logger.log("NullPointerException in World.intersects(int, int, Rectangle)", Level.DEBUG);
            logger.log("Stack:", Level.DEBUG);
            logger.log(e.getMessage(), Level.DEBUG);
        }
        return false;
    }

    public Player getPlayer() {
        return player;
    }

    public void update(float delta) {
        player.update(delta);
    }

    public Entity[] getEntities(){
        return entities;
    }

}
