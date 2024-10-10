package FlappyEnnemy.Game.Elements;

import javafx.scene.image.*;

public class CorpsACorps extends Hero {

    public CorpsACorps(double x, double y, double r) {
        super(x, y, r, 100, 5, 0);
        this.stringToImage = "/Img/corpsAcorps.png";
        this.image = new Image(this.stringToImage, this.r, this.r, false, false);
    }

    @Override
    protected void newPos(double deltaTime, double bgSpeed) {
        /* Les corps a corps sont immobile en y et bougent avec le background en y */
        this.x -= bgSpeed * deltaTime;
    }

}
