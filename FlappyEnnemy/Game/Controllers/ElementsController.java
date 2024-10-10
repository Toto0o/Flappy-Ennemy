package FlappyEnnemy.Game.Controllers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import FlappyEnnemy.Game.Elements.*;

public class ElementsController {

    /* Control les elements du jeu */

    private Random random;
    private Enemy enemy;
    private ArrayList<Hero> heroOnScene;
    private ArrayList<Coin> coinOnScene;

    public ElementsController() {
        this.enemy = new Enemy(); //Nouveau personnage (ennemy)
        heroOnScene = new ArrayList<Hero>(); //Les hero sur scene
        coinOnScene = new ArrayList<Coin>(); //Les pieces sur scene
        random = new Random(); //Pour generer aleatoirement
    }

    public void allMoves(double deltaTime, GraphicsContext context, ImageView bg1, ImageView bg2) {
        /* Update les elements sur scene */
        context.clearRect(0, 0, 640, 400);
        enemyMove(context, deltaTime);
        coinMove(context, deltaTime);
        heroMove(context, deltaTime);
        bgMove(bg1, bg2, deltaTime);
    }

    /* Control du personnage ("ennemy") */

    public boolean isAlive() {
        return this.enemy.getAlive();
    }

    public void initializeEnnemy(GraphicsContext context) {
        /* Dessine le personnage sur le canvas */
        context.drawImage(this.enemy.getImage(), this.enemy.getX(), this.enemy.getY());
    }


    public void shoot() {
        /* 
         * Verifie pour le cooldown de 1 sec depuis le dernier tir 
         * Si il peut tirer, on vérifie si un héro est atteint par le tir
         * Si oui, le personnage gagne les pièce du héro selon le type et le hero est retiré de la scene
         */
        if (this.enemy.canShoot()) {
            Iterator<Hero> itHero = heroOnScene.iterator();
            while (itHero.hasNext()) {
                Hero hero = itHero.next();
                if (hero.isKilled(this.enemy.getY(), this.enemy.getX())) {
                    this.enemy.kill(hero.getKillCoins());
                    itHero.remove();
                    break;
                }
            }
        }
    }
    public boolean isShooting() {
        /* Verifie si le personnage peut tirer */
        if (this.enemy.getShooting()) {
            this.enemy.shootImage();
            this.enemy.setShooting(false);
            return true;
        }
        return false;
    }

    public void setShooting(boolean shoot) {
        this.enemy.setShooting(shoot);
    }

    public void enemyNeutralImage() {
        this.enemy.neutralImage();
    }

    public void jump() {
        /* Fait sauter le personnage */
        this.enemy.jump();
    }

    public void jumpFrames(int frame) {
        /* Affiche la bonne image selon le jump frame rate */
        this.enemy.jumpFrames(frame);
    }

    public void pickedCoin() {
        /* Le personnage rammase une pièce */
        this.enemy.pickedCoin();
    }

    public void testCollisions() {
        /* Verifie s'il y a une collision du personnage avec un hero ou une piece */
        Iterator<Hero> itHero = heroOnScene.iterator();
        while (itHero.hasNext()) {
            Hero hero = itHero.next();
            if (this.enemy.isCollinding(hero)) {
                /* Si oui avec hero, l'ennemy perds des pièce et/ou de la vie */
                this.enemy.colliding(hero.getContactCoins(), hero.getHealth());
                itHero.remove();
                break;
            }
        }

        Iterator<Coin> itCoin = coinOnScene.iterator();
        while (itCoin.hasNext()) {
            Coin coin = itCoin.next();
            if (this.enemy.isCollinding(coin)) {
                /* Si oui avec piece, l'ennemy rammasse une piece */
                pickedCoin();
                itCoin.remove();
                break;
            }
        }
    }

    public void enemyMove(GraphicsContext context, double deltaTime) {
        /* Update la position de l'ennemy */
        this.enemy.update(context, deltaTime);
    }

    public int getCoins() {
        /* Pour afficher les pieces ramassées */
        return this.enemy.getCoins();
    }

    public int getHealth() {
        /* Pour afficher la vie du personnage */
        return this.enemy.getHealth();
    }

    /* Control des heros */

    public void addHero() {
        /*
         * Ajoute un nouveau hero (random type) sur la scene
         * Le hero a un rayon aleatoire de 10-45 pixels
         * La position en y en aleatoire sur la scene
         * La position en x est à droite hors de la scene
         */
        double r = 10 + random.nextDouble(36);
        double y = r + random.nextDouble(401 - r);
        double x = 640 + r;

        switch (random.nextInt(3)) {
            case 0:
                heroOnScene.add(new Tank(x, y, r));
                break;
            case 1:
                heroOnScene.add(new Furtif(x, y, r));
                break;
            case 2:
                heroOnScene.add(new CorpsACorps(x, y, r));
                break;
        }
    }

    public void heroMove(GraphicsContext context, double deltaTime) {
        /* Update la position de tous les heros qui sont sur scene */
        Iterator<Hero> itHero = heroOnScene.iterator();
        while (itHero.hasNext()) {
            Hero hero = itHero.next();
            hero.update(context, deltaTime, bgSpeed());
        }
    }

    /* Control du background (pour donner l'illusion du mouvement) */

    public double bgSpeed() {
        /* La vitesse du background est celle du personnage */
        return this.enemy.getVX();
    }

    public void bgMove(ImageView bg1, ImageView bg2, double deltaTime) {
        /* Deplacer le background vers la gauche; deux images se suivent */
        double deltaPos = deltaTime * bgSpeed();
        bg1.setX(bg1.getX() - deltaPos);
        bg2.setX(bg2.getX() - deltaPos);

        /* Si une des deux images de background est completement a gauce, la replacer juste derriere l'autre image */
        if (bg1.getX() <= 0) {
            bg2.setX(bg1.getX() + 640);
        }
        if (bg2.getX() <= 0) {
            bg1.setX(bg2.getX() + 640);
        }
    }

    /* Control des piece */

    public void addCoin() {
        /* Ajoute une nouvelle piece sur la scene */
        coinOnScene.add(new Coin());
    }

    public void coinMove(GraphicsContext context, double deltaTime) {
        /* Update la position de chaque piece sur scene */
        Iterator<Coin> itCoin = coinOnScene.iterator();
        while (itCoin.hasNext()) {
            Coin coin = itCoin.next();
            coin.update(context, deltaTime, bgSpeed());
        }
    }

    public void outOfBoundElement() {
        /* Retire les elements completement a gauche (hors de la vue de la scene) */
        /* Pour les hero */
        Iterator<Hero> itHero = heroOnScene.iterator();
        while (itHero.hasNext()) {
            Hero hero = itHero.next();
            if (hero.getX() <= -50) {
                itHero.remove();
            }
        }
        /* Pour les pieces */
        Iterator<Coin> itCoin = coinOnScene.iterator();
        while (itCoin.hasNext()) {
            Coin coin = itCoin.next();
            if (coin.getX() <= -50) {
                itCoin.remove();
            }
        }
    }

    
}
