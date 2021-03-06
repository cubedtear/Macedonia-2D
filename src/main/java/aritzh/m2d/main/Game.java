package aritzh.m2d.main;

import aritzh.m2d.blocks.Block;
import aritzh.m2d.blocks.BlockOut;
import aritzh.m2d.graphics.Image;
import aritzh.m2d.main.Logger.Level;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glClear;

/**
 * The main class of the game. Here is where everything happens
 *
 * @author Aritzh
 */
public class Game {

    public static final int WIDTH = 640, HEIGHT = 480;

    public static State state = State.GAME;
    public static boolean close = false, frameLimit = false;

    /**
     * The movements on the x and y axes
     */
    public static int translate_x = 0, translate_y = 0;
    private static float delta;

    private World world;
    private static UnicodeFont font;

    /**
     * Array of objects that should be drawn
     */
    private Drawable[] drawGame = new Drawable[100], drawMenu = new Drawable[100], drawHUD = new Drawable[100];

    /**
     * FPS and timing related variable
     */
    private long lastFrame, lastFPS, fps, fpp;
    private Image background;

    private final Logger logger = new Logger(this.getClass().getSimpleName(), Level.ALL);

    public Game() {
        initOpenGL();
        inicializar();
        mainLoop();
    }


    /**
     * Initializes the OpenGL display with the given dimensions ({@value #WIDTH}, {@value #HEIGHT})
     */
    private void initOpenGL() {
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setTitle("M2D Pre-Alpha");
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        GL11.glLoadIdentity();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, WIDTH, HEIGHT, 0, -1, 1);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    /**
     * Initialize the game objects as well as the textures, while showing a square (for the time being)
     */
    private void inicializar() {

        logger.log("Iniciando...", Level.COMMENTS);
        Block.init();
//        Texture texture = null;
//        try {
//            Cursor c = CursorLoader.get().getCursor("/res/cursor.png", 0, 0);
//            Mouse.setNativeCursor(c);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("/res/sprites/blocks/dirt.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (texture != null) {
//            texture.bind();
//        }

        GL11.glColor3f(1.0f, 1.0f, 1.0f);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0f, 0f);
        GL11.glVertex2d(10, 10);
        GL11.glTexCoord2f(1f, 0f);
        GL11.glVertex2d(WIDTH - 10, 10);
        GL11.glTexCoord2f(1f, 1f);
        GL11.glVertex2d(WIDTH - 10, HEIGHT - 10);
        GL11.glTexCoord2f(0f, 1f);
        GL11.glVertex2d(10, HEIGHT - 10);
        GL11.glEnd();

        Display.update();

        lastFPS = (System.nanoTime() / 1000000);

        world = new World();
        drawGame[1] = world;
        drawHUD[0] = world.getPlayer().getToolBar();
        background = new Image("res/background.png");
        drawMenu[0] = new Image("res/menu_back.png");
        new BlockOut();
        initFonts();
    }


    @SuppressWarnings("unchecked")
    private void initFonts() {
        Font awtFont = new Font("Arial", Font.PLAIN, 16);
        font = new UnicodeFont(awtFont);

        font.getEffects().add(new ColorEffect(java.awt.Color.white));
        font.addAsciiGlyphs();
        try {
            font.loadGlyphs();
        } catch (SlickException ignored) {

        }
    }

    private void mainLoop() {

        while (!Display.isCloseRequested() && !close) {
            handleInput();
            updateDisplay();
            draw();
        }
        logger.log("Saliendo...", Level.COMMENTS);
        Display.destroy();
        System.exit(0);
    }

    private void handleInput() {
        InputHandler.update(world);
    }

    private void updateDisplay() {
        getDelta();
        world.update(delta);
        updateFPS();
        Display.update();
        translate_y = world.getPlayer().getY() - Display.getHeight() / 2;
        translate_x = -world.getPlayer().getX() + Display.getWidth() / 2;
        if (frameLimit)
            Display.sync(100);

    }

    private void draw() {

        glClear(GL11.GL_COLOR_BUFFER_BIT);

        GL11.glPushMatrix();


        GL11.glTranslatef(translate_x / 2, -translate_y / 2, 0);
        // 50% Parallax scrolling
        background.draw();
        GL11.glPopMatrix();

        GL11.glPushMatrix();

        // 100% Parallax scrolling
        GL11.glTranslatef(translate_x, -translate_y, 0);

        if (state == State.GAME) {

            for (Drawable d : drawGame) {
                if (d != null)
                    d.draw();
            }
            GL11.glPopMatrix();
            font.drawString(0, 0, String.valueOf(fps));
            for (Drawable d : drawHUD) {
                if (d != null) {
                    d.draw();
                }
            }

        } else {
            for (Drawable d : drawMenu) {
                if (d != null)
                    d.draw();
            }
        }


    }

    public static void main(String[] args) {
        new Game();
    }

    /**
     * Updates the variable "delta" with the amount of time has passed since last frame
     */
    void getDelta() {
        long time = (System.nanoTime() / 1000000);
        float delta = time - lastFrame;
        lastFrame = time;
        Game.delta = delta;
    }

    /**
     * Updates the {@link #fps} variable
     */
    void updateFPS() {
        if ((System.nanoTime() / 1000000) - lastFPS > 1000) {
            Display.setTitle("FPS: " + fpp);
            fps = fpp;
            fpp = 0; // reset the FPS counter
            lastFPS += 1000; // add one second
        }
        fpp++;
    }

    public enum State {
        MENU, GAME
    }

    public static UnicodeFont getFont() {
        return font;
    }

}
