package aritzh.m2d.graphics;

import aritzh.m2d.main.Logger;
import aritzh.m2d.main.Logger.Level;
import org.newdawn.slick.Color;
import org.newdawn.slick.PackedSpriteSheet;
import org.newdawn.slick.SlickException;

/**
 * @author Aritzh
 */

public class GraphUtil {

    private static final Logger logger = new Logger("GraphUtil", Level.ALL);

    public static org.newdawn.slick.Image getImgFromSheet(String sheet, String name) {
        PackedSpriteSheet sh;
        try {
            sh = new PackedSpriteSheet(sheet, Color.magenta);
            return sh.getSprite(name);
        } catch (SlickException e) {
            logger.log("Error al leer el PackedSpriteSheet:", Level.ERROR);
            logger.log(sheet, Level.ERROR);
            e.printStackTrace();
            System.exit(0);
        }

        return null;


    }


}
