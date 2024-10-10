package FlappyEnnemy.Game.Elements;

import javafx.scene.image.*;

public abstract class Elements {

    protected double x, y, r;
    protected Boolean collision;
    protected int health;
    protected String stringToImage;
    protected Image image;

    public Elements(double x, double y, double r, int health) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.health = health; // Vie perdu lorsque contact
    }

    public boolean isCollinding(Elements elements) {
        /*
         * Verifie si l'element est en collision avec un autre element
         * Les elements sont modelise par des cercles :
         *  Oui si la distance centre a centre est plus petite que la somme des rayons
         */
        double x2 = elements.getX() - this.getX();
        double y2 = elements.getY() - this.getY();
        double r2 = this.r * this.r;

        return r2 > x2 * x2 + y2 * y2;
    }

    /* Getters and Setters */

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return this.r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setCollision(Boolean collision) {
        this.collision = collision;
    }

    public boolean getCollision() {
        return this.collision;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return this.health;
    }

    public void setImage(String stringToImage) {
        this.stringToImage = stringToImage;
    }

    public Image getImage() {
        return this.image;
    }

}
