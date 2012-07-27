package net.m2d.main;

import net.m2d.GUI.Stack;
import net.m2d.GUI.ToolBar;
import net.m2d.blocks.Block;
import net.m2d.blocks.InstanceBlock;
import net.m2d.entity.Player;
import net.m2d.main.Game.State;
import net.m2d.main.Logger.Level;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;


/**
 * @author Aritzh
 */
class InputHandler {

    private static boolean[] keys = new boolean[256];
    private static final int LEFT = 0, RIGHT = 1;
    private static int blockx, blocky, button;

    private static World world;
    private static InstanceBlock block;
    private static final Logger logger = new Logger("Input", Level.ALL);
    private static boolean pressed;

    public static void mouse(World world) {

        InputHandler.world = world;
        getBlock();

        if (Mouse.isButtonDown(LEFT)) {

            block.smash();
        } else {
            block.resetSmash();
        }

        while (Mouse.next()) {

            getBlock();

            if (Mouse.getEventDWheel() > 0) {
                // Rueda arriba
                world.getPlayer().getToolBar().setDTool(-1);
            } else if (Mouse.getEventDWheel() < 0) {
                // Rueda abajo
                world.getPlayer().getToolBar().setDTool(1);
            }

            if (button == LEFT && pressed) {
                leftPressed();
            }
            if (button == LEFT && !pressed) {
                leftReleased();

            }
            if (button == RIGHT && pressed) {
                rightPressed();
            }
            if (button == RIGHT && !pressed) {
                rightReleased();
            }

        }
    }

    public static boolean[] keyboard(World world) {
        InputHandler.world = world;
        while (Keyboard.next()) {

            keys[Keyboard.getEventKey()] = Keyboard.getEventKeyState();

            if (keys[Keyboard.KEY_R]) {
                //Display.sync(60);
                world.getPlayer().reset();
            }

            if (keys[Keyboard.KEY_C])
                world.loadFromFile("save.txt");
            if (keys[Keyboard.KEY_G])
                world.saveToFile("save.txt");

            if (keys[Keyboard.KEY_ESCAPE])
                if (Game.state == State.GAME)
                    Game.state = State.MENU;
                else Game.close = true;

            if (keys[Keyboard.KEY_P])
                Game.frameLimit = !Game.frameLimit;
        }
        if (keys[Keyboard.KEY_W])
            world.getPlayer().move(Player.UP);
        if (keys[Keyboard.KEY_M]) {
            logger.log("CurrTool Amount" + world.getPlayer().getToolBar().getCurrTool().getAmount(), Level.DEBUG);
        }

        if (keys[Keyboard.KEY_A]) {
            if (Game.state == State.GAME) {
                world.getPlayer().move(Player.LEFT);
            }
        } else {
            world.getPlayer().move(Player.OTHER);
        }
        if (keys[Keyboard.KEY_D]) {
            if (Game.state == State.GAME) {
                world.getPlayer().move(Player.RIGHT);
            }
        } else {
            world.getPlayer().move(Player.OTHER);
        }
        return keys;
    }

    private static void leftPressed() {
        logger.log("X: " + Mouse.getX(), Level.DEBUG);
        logger.log("Y: " + Mouse.getY(), Level.DEBUG);
        block.getBlock().leftPressed();
    }

    private static void leftReleased() {
        block.getBlock().leftReleased();
    }

    private static void rightPressed() {
        block.getBlock().rightPressed();

//        if (Block.blocksList[block.getBlock().id + 1] != null) {
//            world.setAt(blockx, blocky, block.getBlock().id + 1);
//        } else {
//            world.setAt(blockx, blocky, 0);
//        }

        if (block.getBlock() == Block.air) {
            ToolBar tb = world.getPlayer().getToolBar();
            if (tb.getCurrTool().getAmount() > 0) {
                block.setBlock(Block.blocksList[tb.getCurrTool().getId()]);
                tb.setCurrTool(new Stack(tb.getCurrTool().getId(), tb.getCurrTool().getAmount() - 1));
            }
        }
    }

    private static void rightReleased() {
        block.getBlock().rightReleased();
    }

    private static void getBlock() {
        int x = Mouse.getX() - Game.translate_x;
        int y = Mouse.getY() - Game.translate_y;
        pressed = Mouse.getEventButtonState();
        button = Mouse.getEventButton();

        blockx = x / Block.SIZE;
        blocky = (Game.HEIGHT - y) / Block.SIZE;
        block = world.getAt(blockx, blocky);

        if (block == null) {
            block = new InstanceBlock(Block.air, -100, -100, world);
        }
    }

}
