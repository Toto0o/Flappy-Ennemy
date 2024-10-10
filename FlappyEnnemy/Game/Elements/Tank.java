package FlappyEnnemy.Game.Elements;

import java.util.Random;
import javafx.scene.image.*;

public class Tank extends Hero {

    private Random random;
    private double cooldown = 0;

    public Tank(double x, double y, double r) {
        super(x, y, r, 50, 7, 0);
        random = new Random();
        this.stringToImage = "/Img/Tank.png";
        this.image = new Image(this.stringToImage, this.r, this.r, false, false);
    }

    @Override
    public void newPos(double deltaTime, double bgSpeed) {
        /*
         * Les hero tank se teleportent dans un carre de [30-30] pixels periodicement toutes les 0.5s
         * Si le hero est outOfBound, on force un deplacement vers la scene
         * Le hero bouge avec le background pour eviter le surplace
         */
        if (canMove(deltaTime)) {
            if (this.x >= 640) {
                this.x -= random.nextDouble(61);
            } else {
                this.x += 30 - random.nextDouble(61);
            }
            if (this.y <= 0 || this.y >= 400) {
                this.y -= random.nextDouble(61);
            } else {
                this.y += 30 - random.nextDouble(61);
            }
        }
        this.x -= bgSpeed * deltaTime; //Le hero se deplace avec le backgournd peu import le cooldown
    }

    private boolean canMove(double deltaTime) {
        /* Verifie pour le cooldown de 0.5s entre chaque mouvement aleatoire */
        cooldown -= deltaTime;
        if (cooldown <= 0) {
            cooldown = 0.5;
            return true;
        }
        return false;
    }
}
