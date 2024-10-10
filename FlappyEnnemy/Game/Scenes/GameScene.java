package FlappyEnnemy.Game.Scenes;

import FlappyEnnemy.Game.Controllers.*;

import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;

public class GameScene {
    /* Controlleurs */
    private ElementsController elementController;
    private SceneController sceneController;
    /* AnimationTimer */
    private AnimationTimer gameAnimation;
    /* Conteneurs */
    private BorderPane root;
    private Pane gamePane;
    private HBox menuHBox;
    private VBox pauseVBox, gameOverVBox;
    private Scene scene;
    private Canvas canvas;
    private GraphicsContext context;
    /* Background */
    private ImageView bg1, bg2;
    /* Boutons pour menu et gameOver */
    private Button pauseButton, resumeButton, menuButton, restartButton, gameOverRestart, gameOverMenu;
    /* Text pour menu et gameOver */
    private Text timerText, coinsText, healthText, gameOverText;
    /* String pour Text menu */
    private String coinString = "Coins : ", healthString = "Health : ";
    /* Pour demarrer la partie */
    private boolean start;
    /* Pour mettre sur pause */
    private boolean pause;
    /* Pour l'animation du jump */
    private boolean jump;
    private int jumpFrameCount;
    private long jumpStartTime;

    public GameScene(ElementsController elementController, SceneController sceneController) {
        this.sceneController = sceneController;
        this.elementController = elementController;

        root = new BorderPane();
        scene = new Scene(root, 640, 440);
        bg1 = new ImageView(new Image("Img/bg.png"));
        bg2 = new ImageView(new Image("Img/bg.png"));
        gamePane = new Pane();
        canvas = new Canvas(640, 400);
        menuHBox = new HBox();
        pauseVBox = new VBox();
        gameOverVBox = new VBox();

        timerText = new Text("0:0");
        healthText = new Text(healthString + this.elementController.getHealth());
        coinsText = new Text(coinString + this.elementController.getCoins());
        gameOverText = new Text("GAME OVER");

        pauseButton = new Button("Pause");
        resumeButton = new Button("Resume");
        menuButton = new Button("Menu");
        gameOverRestart = new Button("Restart");
        gameOverMenu = new Button("Menu");
        restartButton = new Button("Restart");

        this.start = false;
        
        this.pause = true;

        this.jump = false;
        this.jumpFrameCount = 0;
    }

    public void setScene() {
        /* Initialisation de la scene de jeu */
        root.setCenter(gamePane);
        root.setBottom(menuHBox);
        gamePane.getChildren().addAll(bg1,bg2,canvas);

        /* 
         * Initialisation du background 
         * La deuxieme image commence hors de la scene a droite et suis la premiere image
         */
        bg1.setX(0); 
        bg2.setX(640);

        /* Pour dessiner les elements */
        context = canvas.getGraphicsContext2D();

        /* Initialiser le personnage */
        this.elementController.initializeEnnemy(context);

        /* Affichage des boutons lorsque sur pause */
        pauseVBox.setStyle("-fx-border-width: 2px; -fx-border-color: Black; -fx-background-color: DarkSeaGreen");
        pauseVBox.setAlignment(Pos.CENTER);
        pauseVBox.setMinSize(150, 150);
        pauseVBox.setSpacing(10);
        pauseVBox.getChildren().addAll(resumeButton);
        pauseVBox.setLayoutX(255);
        pauseVBox.setLayoutY(125);
        
        /* Menu */
        menuHBox.setMinHeight(40);
        menuHBox.setStyle("-fx-border-width: 3px; -fx-border-color: DarkSeaGreen; -fx-background-color: LightBlue;");
        menuHBox.setAlignment(Pos.CENTER);
        menuHBox.setSpacing(10);        
        menuHBox.getChildren().addAll(pauseButton, timerText, coinsText, healthText);
        timerText.setWrappingWidth(28); // Pour eviter de deplacer tous les elements du menu
        timerText.setTextAlignment(TextAlignment.CENTER);

        /* Affichage du gameOver */
        gameOverVBox.setAlignment(Pos.CENTER);
        gameOverVBox.setSpacing(30);
        gameOverVBox.setMinSize(200, 150);
        gameOverVBox.setLayoutX(200);
        gameOverVBox.setLayoutY(125);
        gameOverVBox.getChildren().add(gameOverText);
        gameOverText.setFont(new Font("Copperplate Gothic Bold", 40));
        gameOverVBox.getChildren().addAll(gameOverRestart, gameOverMenu);

        /* Animation de la scene */
        this.gameAnimation = new AnimationTimer() {

            private double lastTime = 0;
            private double three = 0;
            private double two = 0;
            private double deltaTime;
            private double timer;
            private double jumpFrameRate = 6*1e-9;
            private double shootImageCooldown = 0;

            @Override
            public void handle(long now) {
                
                /* Si le jeu n'est pas sur pause */
                if (!pause) {

                    if (lastTime == 0) {
                        lastTime = now;
                        return;
                    }

                    /* Interval de temps entre la frame courrant et la precedente */
                    deltaTime = (now - lastTime) * 1e-9;


                    /* Verifie si le personnage est encore vivant */
                    if (!elementController.isAlive()) {
                        /* Sauvegarder le score et arreter la partie si le personnage est mort*/
                        gameOver();
                    }

                    /* Incrementation des timer */
                    timer += deltaTime; //Update le timer
                    two += deltaTime; // 2 secondes pour ajouter une piece
                    three += deltaTime; // 3 secondes pour ajouter un hero

                    if (jump) {
                        jumpFrameCount = (int) ((now - jumpStartTime) * jumpFrameRate)%4;
                        if (jumpFrameCount == 3) {
                            elementController.jumpFrames(jumpFrameCount);
                            jump = false;
                        }
                        else if (jumpFrameCount == 0) {
                            elementController.jumpFrames(jumpFrameCount);
                        }
                        else {
                            elementController.jumpFrames(jumpFrameCount);
                        }
                    }
                    /* Ajoute une piece si 2 sec depuis la derniere fois */
                    if (Math.floor(two) % 2 == 0 && Math.floor(two) != 0) {
                        elementController.addCoin();
                        two = 0;
                    }

                    /* Ajoute un hero si 3 sec depuis la derniere fois */
                    if (Math.floor(three) % 3 == 0 && Math.floor(three) != 0) {
                        elementController.addHero();
                        three = 0;
                    }

                    /* Si le hero tir */
                    if (elementController.isShooting()) {
                        elementController.shoot();
                        shootImageCooldown = 0.2;
                    }

                    if (shootImageCooldown > 0) {
                        if ((shootImageCooldown -= deltaTime) < 0) {
                            elementController.enemyNeutralImage();
                        }
                        
                    }

                    /* Verifie pour les collisions */
                    elementController.testCollisions();

                    /* Verifie pour les elements hors de la scene */
                    elementController.outOfBoundElement();

                    /* Update les elements text du menu */
                    updateTimer(timer);
                    updateCoin();
                    updateHalth();

                    /* update la poisition de tous les elements sur la scene */
                    elementController.allMoves(deltaTime, context, bg1, bg2);
                } 
                lastTime = now;
            }
        };

        /* Event handlers du control du personnage */
        scene.setOnKeyPressed((sceneEvent) -> {
            if (!this.start) {
                /* Appuyer sur une touche pour demarrer la partie */
                this.start = true;
                this.pause = false;
                this.gameAnimation.start();
            }
            if (sceneEvent.getCode() == KeyCode.E) {
                /* Fait tirer l'ennemy quand 'E' est appuye */
                this.elementController.setShooting(true);
            }
            if (sceneEvent.getCode() == KeyCode.SPACE) {
                /* Fait sauter l'ennemy quand 'space' est appuye */
                this.elementController.jump();
                this.jumpStartTime = System.nanoTime();
                this.jump = true;
            }
        });

        /* Event handlers pour les boutons menu */
        pauseButton.setOnMouseClicked((pauseEvent) -> {
            /* Mets le jeu sur pause et affiche le menu de pause */
            this.pause = true;
            menuHBox.getChildren().remove(pauseButton);
            pauseVBox.getChildren().addAll(restartButton, menuButton);
            gamePane.getChildren().add(pauseVBox);
        });

        resumeButton.setOnMouseClicked((resumeEvent) -> {
            /* Repars la partie et enleve le menu de pause */
            this.pause = false;
            pauseVBox.getChildren().removeAll(restartButton, menuButton);
            gamePane.getChildren().remove(pauseVBox);
            menuHBox.getChildren().add(0, pauseButton);
            canvas.requestFocus();
        });

        /* Event handlers pour les boutons menu */
        menuButton.setOnMouseClicked((menuEvent) -> {
            /* Mets la scene menu */
            this.sceneController.newScene('M'); 
        });

        restartButton.setOnMouseClicked((restartEvent) -> {
            /* Nouvelle scene de jeu pour recommencer la partie */
            this.sceneController.newScene('G');
        });

        /* Event handlers des boutons GameOver */
        gameOverRestart.setOnMouseClicked((restartEvent) -> {
            this.sceneController.newScene('G');
        });
        gameOverMenu.setOnMouseClicked((menuEvent) -> {
            this.sceneController.newScene('M');
        });

        canvas.requestFocus(); //Afin d'avoir les events handler relié au mouvement du personnage (sinon les boutons menu on la priorite)
    }


    /* Update les elements Text du menu */

    private void updateTimer(double timerDouble) {
        /* Affiche le temps sous la forme min:sec */
        int min = (int) timerDouble/60;
        int sec = (int) Math.round(timerDouble - min*60);
        this.timerText.setText(min + ":" + sec);
    }

    private void updateCoin() {
        /* Affiche le nombre de piece */
        this.coinsText.setText(this.coinString + elementController.getCoins());
    }

    private void updateHalth() {
        /* Affiche la vie du personnage */
        this.healthText.setText(this.healthString + elementController.getHealth());
    }


    /* Game Over Gamescene setting */

    private void gameOver() {
        /*
         * Si le personnage est mort, sauvegarder le score et afficher GAME OVER
         * Ajouter les boutons pour aller au menu ou recommencer
         */
        this.gameAnimation.stop();
        this.sceneController.writeScore(this.elementController.getCoins());
        canvas.setOpacity(0.3);
        bg1.setOpacity(0.3);
        bg2.setOpacity(0.3);
        gamePane.getChildren().add(gameOverVBox);
        menuHBox.getChildren().remove(pauseButton);
    }


    /* Getters & Setters */

    public GraphicsContext getContext() {
        return context;
    }

    public ImageView getBG1() {
        return bg1;
    }

    public ImageView getBG2() {
        return bg2;
    }

    public boolean canStart() {
        return this.start;
    }

    public Scene getScene() {
        return scene;
    }

}
