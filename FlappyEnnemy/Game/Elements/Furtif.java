package FlappyEnnemy.Game.Elements;

import java.util.Random;
import javafx.scene.image.*;

public class Furtif extends Hero {

    private Random random;
    private double vy, maxY, minY;

    public Furtif(double x, double y, double r) {
        super(x, y, r, 0, 8, 10);
        random = new Random();
        /* Amplitude de 50 pixels */
        maxY = y + 50;
        minY = y - 50;
        vy = (100 + random.nextDouble(101)); // La vitesse vertical est aleatoire entre [100:200] pixels/s
        this.stringToImage = "/Img/Furtif.png";
        this.image = new Image(this.stringToImage, this.r, this.r, false, false);
    }

    @Override
    protected void newPos(double deltaTime, double bgSpeed) {
        /* Les heros furtifs se deplace en x avec le background et un mouvement periodic en y (haut-bas) */
        maxPos();
        this.y += vy * deltaTime;
        this.x -= bgSpeed * deltaTime;
    }

    private void maxPos() {
        /*
         * Lorsque le hero furtif atteint le haut de l'amplitude, la vitesse change vers le bas
         * Vis versa pour le bas de l'amplitude
         */
        if (this.y >= maxY || this.y <= minY) {
            vy = -vy;
        }
    }

}
