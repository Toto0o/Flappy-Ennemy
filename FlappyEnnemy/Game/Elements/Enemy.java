package FlappyEnnemy.Game.Elements;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;

public final class Enemy extends Elements {

    private double vx, vy, g;
    private int coins;
    private boolean alive, shooting;
    private double lastTime = 0;
    private double cooldown;
    private Image[] jumpImages; // Images du saut (animation of 3 frames)
    private Image shootImage, neutralImage; // Image du tir et l'image neutre 

    public Enemy() {
        super(290, 200, 30, 100);
        this.vx = 120;
        this.vy = 0;
        this.g = 500;
        this.health = 100;
        this.coins = 0;
        this.collision = false;
        this.stringToImage = "/Img/enemy.png";
        this.neutralImage = new Image(this.stringToImage, this.r + 10, this.r + 10, false, false);
        this.image = this.neutralImage;
        this.jumpImages = new Image[] {
            new Image("/Img/enemy1.png", this.r + 10, this.r + 10, false, false), 
            new Image("/Img/enemy2.png", this.r + 10, this.r + 10, false, false), 
            new Image("/Img/enemy3.png", this.r + 10, this.r + 10, false, false)};
        this.shootImage = new Image("/Img/shoot.png", this.r + 10, this.r + 10, false, false);
        this.alive = true;
        this.shooting = false;
        cooldown = 1;
    }

    public void update(GraphicsContext context, double deltaTime) {
        /* Update la position et dessine l'ennemy sur le canvas */
        newPos(deltaTime);
        context.drawImage(this.image, this.x, this.y);
    }

    public void newPos(double deltaTime) {
        /* Update la position en fonction du temps depuis la derniere frame */
        this.vy += deltaTime * g;
        maxVY(); //Verifie si la vitesse n'est pas au dessus du cap

        /* Si le personnage touche les bords, il rebondit dans l'autre direction */
        double newY = (this.y + deltaTime * this.vy);
        if (newY <= 0 || (newY + this.r) >= 400) {
            this.vy = -this.vy;
        } 
        else {
            this.y = newY;
            maxVY();
        }
 
    }

    public boolean canShoot() {
        /*
         * Calcul du cooldown depuis le dernier tir
         * Cooldown = 1 sec
         * Image cooldown = 1 sec
         */
        double now = System.nanoTime();
        cooldown -= (now - lastTime) * 1e-9;
        if (cooldown < 0) {
            cooldown = 1;
            lastTime = now;
            this.image = this.shootImage;
            return true;
        }

        lastTime = now;
        return false;
    }

    public void kill(int coins) {
        /* Si l'ennemy reussi son tir, il gagne des piece */
        this.coins += coins;
    }

    public void colliding(int coins, int health) {
        /* Si collision avec un hero ou une piece */
        this.coins -= coins;

        /* Le nombre minimal de piece est a 0 */
        if (this.coins < 0) {
            this.coins = 0;
        }

        /* Si la vie du personnage est a 0 ou moins, le personnage n'est plus en vie */
        this.health -= health;
        if (this.health <= 0) {
            this.alive = false;
        }
    }

    public void jump() {
        /* Un saut change la vitesse en y a 300 vers le haut */
        this.vy = -300;
    }

    public void pickedCoin() {
        /*
         * +1 une piece :
         * La gravite augmente de 15
         * La vitesse en x augmente de 10
         */
        this.coins += 1;
        this.g += 15;
        this.vx += 10;
    }

    private void maxVY() {
        /* La vitesse maximum en y vers le bas est caped a 300 */
        if (this.vy > 300) {
            this.vy = 300;
        }
    }

    public void jumpFrames(int frame) {
        switch (frame) {
            case 0 :
                this.image = jumpImages[0];
                break;
            case 1 :
                this.image = jumpImages[1];
                break;
            case 2 :
                this.image = jumpImages[2];
                break;
            case 3 :
                this.image = this.neutralImage;
        }
            
    }

    public void shootImage() {
        this.image = this.shootImage;
    }

    public void neutralImage() {
        this.image = neutralImage;
    }

    /* Getters and setters */

    public double getVX() {
        return this.vx;
    }

    public double getVY() {
        return this.vy;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getCoins() {
        return this.coins;
    }

    public boolean getAlive() {
        return this.alive;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public boolean getShooting() {
        return this.shooting;
    }

   

}
