package FlappyEnnemy.Game.Elements;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;

public class Coin extends Elements {

    public Coin() {
        super(660, 0, 25, 0);
        Random random = new Random();
        double y = random.nextDouble(380); //La position en y est aleatoire (sur la scene)
        this.y = y;
        this.stringToImage = "/Img/coin.png";
        this.image = new Image(this.stringToImage, this.r, this.r, false, false);
    }

    public void update(GraphicsContext context, double deltaTime, double bgSpeed) {
        /*
         * Les piece se deplace avec le background en x et sont immobile en y
         * Update la position et dessine la piece sur le canvas
         */
        this.x -= deltaTime * bgSpeed;
        context.drawImage(this.image, this.x, this.y);
    }
}
