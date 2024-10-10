package FlappyEnnemy.Game.Controllers;

import javafx.stage.Stage;
import javafx.scene.*;
import FlappyEnnemy.Game.Scenes.*;

public class SceneController {

    /* Control des scenes */

    private Stage primaryStage;
    private ElementsController elementController;
    private GameScene gameScene;
    private MenuScene menuScene;
    private HighScoreScene highScoreScene;
    private Scene scene;

    public SceneController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.highScoreScene = new HighScoreScene(this);
    }

    public void start() {
        /* Default : commence sur la scene menu */
        newScene('M');
        this.primaryStage.setTitle("Flappy Ennemy - by Antoine Tessier");
        primaryStage.show();
    }

    public void newScene(char scene) {
        /* 
         * Pour changer de scene :
         *  - M : scene menu
         *  - G : scene de jeu
         *  - H : scene highs scores
         */
        switch (scene) {
            case 'G':
                this.scene = newGameScene();
                break;
            case 'M':
                this.scene = newMenuScene();
                break;
            case 'H':
                this.scene = newHighScoreScene();
                break;
        };
        primaryStage.setScene(this.scene);
    }

    
    
    /* Instanciation d'une nouvelle scene */
    
    private Scene newGameScene() {
        /* Nouvelle scene de jeu */
        this.elementController = new ElementsController();
        this.gameScene = new GameScene(this.elementController, this);
        this.gameScene.setScene();
        return this.gameScene.getScene();
    }

    private Scene newMenuScene() {
        /* Nouvelle scene menu */
        this.menuScene = new MenuScene(this);
        this.menuScene.setScene();
        return this.menuScene.getScene();
    }

    private Scene newHighScoreScene() {
        /* Nouvelle scene highs scores */
        this.highScoreScene = new HighScoreScene(this);
        this.highScoreScene.setScene();
        return this.highScoreScene.getScene();
    }

    /* Lorsque la partie est termine, update le fichier High Scores */

    public void writeScore(int coins) {
        this.highScoreScene.writeHighScores(coins);
    }
}