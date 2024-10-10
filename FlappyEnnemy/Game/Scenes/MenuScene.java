package FlappyEnnemy.FlappyEnnemy.Game.Scenes;
import Game.Controllers.SceneController;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;


public class MenuScene {

    /* Conteneur */
    private BorderPane root; /* Pour le background */
    private Scene scene;
    private VBox VBox; /* Pour le titre et les boutons */

    /* Titre */
    private Text title;

    /* Image de background */
    private ImageView bg;

    /* Boutons de menu */
    private Button startButton, highScoreButton;

    /* Controlleur de scene */
    private SceneController controller;

    public MenuScene(SceneController appController) {
        this.controller = appController;
        root = new BorderPane();
        scene = new Scene(root, 640, 400);
        VBox = new VBox();
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene() {
        /* Mise en place de la scene */

        title = new Text("Flappy Ennemy");
        bg = new ImageView(new Image("/Img/bg.png"));
        startButton = new Button("New Game");
        highScoreButton = new Button("Highest scores");
        root.getChildren().add(bg);
        root.setCenter(VBox);
        VBox.getChildren().addAll(title, startButton, highScoreButton);
        VBox.setAlignment(Pos.CENTER);
        VBox.maxHeight(400);
        VBox.maxWidth(600);
        VBox.setSpacing(30);
        bg.setOpacity(0.5);
        title.setFont(new Font("Copperplate Gothic Bold", 40));

        /* 
         * Event handler pour les boutons menu et highscore
         * Fait appel au controlleur de scene pour acceder aux scenes de jeu ou de high scores
         */
        
        startButton.setOnMouseClicked((startAction) -> {
            this.controller.newScene('G');
        });

        highScoreButton.setOnMouseClicked((highScoreEvent) -> {
            this.controller.newScene('H');
        });

    }
}
