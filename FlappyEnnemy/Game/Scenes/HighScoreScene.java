package FlappyEnnemy.Game.Scenes;
import FlappyEnnemy.Game.Controllers.SceneController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.scene.control.*;

public class HighScoreScene {

    /* Controleur de scene */
    private SceneController Scenecontroller;

    /* Conteneurs */
    private BorderPane root;
    private Scene scene;
    private VBox highScores; // Pour y mettre les elemens Text
    private HBox menuHbox; // Pour le bouton menu

    /* Titre de la scene et titre du tableau */
    private Text title, headerName;

    /* Image back ground */
    private ImageView bg;
    
    /* Pour retourner au menu */
    private Button menuButton;
    
    /* Nombre de score affiché; Position des scores */
    private int count;

    public HighScoreScene(SceneController controller) {
        this.Scenecontroller = controller;
        root = new BorderPane();
        scene = new Scene(root, 640, 400);
        bg = new ImageView(new Image("/Img/bg.png"));
        highScores = new VBox();
        menuHbox = new HBox();
        title = new Text("High Scores");
        headerName = new Text("Coins, date");
        menuButton = new Button("Menu");
        count = 0;
    }

    public Scene getScene() {
        return this.scene;
    }
    
    public void setScene() {
        /* Crée la scene */

        /* Affiche le background avec transparence */
        root.getChildren().addAll(bg);
        bg.setOpacity(0.3);

        /* Les high scores */
        root.setTop(highScores);
        highScores.setAlignment(Pos.CENTER);
        highScores.getChildren().add(title); // Ajoute le titre en haut
        title.setFont(new Font("Copperplate Gothic Bold", 40));
        highScores.setSpacing(20);

        Font font = new Font("Bold",12); // Font pour les elements text de score et le titre du tableau;
        highScores.getChildren().add(headerName);
        headerName.setFont(font);
        highScores.setSpacing(10);
        for (String scores : getHighScores()) {
            /* Ajoute seulement les elements non null de score (si moins que 10 scores sauvegardés); max de 10 éléments */
            if (scores != null) {
                Text text = new Text((count + 1) + ". " + scores.replace(',', ' '));
                highScores.getChildren().add(text);
                text.setFont(font);
                count++;
            }
            else {
                break;
            }
        }

        /* Pour retourner au menu */
        root.setBottom(menuHbox);
        menuHbox.setAlignment(Pos.CENTER);
        menuHbox.getChildren().add(menuButton);
        /* Event handler pour le menu */
        menuButton.setOnMouseClicked((menuEvent) -> {
            this.Scenecontroller.newScene('M');
        });
    }

    public void writeHighScores(int coins) {
        /* Utilise java.time pour la date */

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
        String date = formatter.format(LocalDateTime.now());
        String lineH, lineC; // lineH : lignes dans le fichier High Scores.tkt -- lineC : lignes dans le fichier cache.tkt
        Boolean written = false;

        try {
            /* La methode de tri pour les scores et par comparaison, en raison du petit nombre de comparaison et sa facilité d'implémentation
             * Les donné du fichier high score sont réécris dans le fichier cache, et chacun des éléments est comparé au nouveau score à ajouter;
             * Le score est placé à la bonne position, le reste des scores est écrit sans comparaison; 
             * Il y au pire n comparaison, plus le temps de lecture et ecriture pour le fichier cache; Cela ce fait relativement rapidement, puisque les fichiers sont petits
             * Le score et la date sont écris sou forme csv pour facilité la manipulation des données 
             */
            FileReader fileReader = new FileReader("scores\\High Scores.txt");
            BufferedReader reader = new BufferedReader(fileReader);

            FileWriter cacheWriter = new FileWriter("scores\\cache.txt", false);
            BufferedWriter BWcache = new BufferedWriter(cacheWriter);
            
            while ((lineH = reader.readLine()) != null) {
                BWcache.write(lineH);
                BWcache.newLine();
                BWcache.flush();
            }

            BWcache.close();
            reader.close();

            FileWriter fileWriter = new FileWriter("scores\\High Scores.txt", false);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            FileReader cachReader = new FileReader("scores\\cache.txt");
            BufferedReader BRcache = new BufferedReader(cachReader);
            
            while ((lineC = BRcache.readLine()) != null) {
                if (coins > Integer.parseInt(lineC.split(",", 2)[0]) && !written) {
                    
                    writer.write(coins + ", " + date);
                    writer.newLine();
                    writer.write(lineC);
                    writer.newLine();
                    written = true;
                }
                else {
                    writer.write(lineC);
                    writer.newLine();
                }
            }

            if (!written) {
                writer.write(coins + ", " + date);
            }
            writer.flush();
            writer.close();
            BRcache.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getHighScores() {

        /* Les scores en ordre croissant, (max 10) sont souvegardé et retourné par la fonction
         * dans un tableau de String;
         */

        String[] highScores = new String[10];
        String line;

        try {
            FileReader fileReader = new FileReader("scores\\High Scores.txt");
            BufferedReader reader = new BufferedReader(fileReader);

            for (int i = 0; i < 10; i++) {
                if ((line = reader.readLine()) != null) {
                    highScores[i] = line;
                }
                else {
                    break;
                }
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return highScores;
    }
}
