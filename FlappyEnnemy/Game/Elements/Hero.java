package FlappyEnnemy.Game.Elements;

import javafx.scene.canvas.GraphicsContext;

public abstract class Hero extends Elements {

    private int killCoins, contactCoins;

    public Hero(double x, double y, double r, int health, int killCoins, int contactCoins) {
        super(x, y, r, health);
        this.killCoins = killCoins; // Nombre de pieces gagné lorsque tué
        this.contactCoins = contactCoins; // Nombre de pieces perdu lorsque contact
    }

    /* Le deplacement de chaque hero est propre au type de hero */
    protected abstract void newPos(double deltaTime, double bgSpeed);

    public void update(GraphicsContext context, double deltaTime, double bgSpeed) {
        /* Update la position du hero et le dessine sur le canvas */
        newPos(deltaTime, bgSpeed);
        context.drawImage(this.image, this.x, this.y);
    }

    public boolean isKilled(double y, double x) {
        /* Verifie si le tir touche la hit box du hero et si le hero est devant l'ennemy (pour eviter les tirs par derriere) */
        double uHitBox = getY() + getR();
        double lHitBox = getY() - getR();
        if (y >= lHitBox && y <= uHitBox && x < this.x) {
            return true;
        }
        return false;
    }

    /* Getters and Setters */

    public void setKillCoins(int killCoins) {
        this.killCoins = killCoins;
    }

    public int getKillCoins() {
        return this.killCoins;
    }

    public void setContactCoins(int contactCoins) {
        this.contactCoins = contactCoins;
    }

    public int getContactCoins() {
        return this.contactCoins;
    }

}
