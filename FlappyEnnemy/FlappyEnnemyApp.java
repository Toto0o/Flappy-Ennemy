package FlappyEnnemy;
import Game.Controllers.SceneController;

import javafx.application.Application;
import javafx.stage.Stage;

public class FlappyEnnemyApp extends Application {

    private SceneController sceneController;

    public static void main(String[] args) {
        FlappyEnnemyApp.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {  
        this.sceneController = new SceneController(primaryStage);
        this.sceneController.start();
    }

}

/*
 * FlappyEnnemy utilise le package Game (le jeu que j'ai créé); "FlappyEnnemyApp.java" s'occupe d'extends les méthodes
 * de l'interface Application de javafx et fait appel au package Game pour afficher et controller le jeu;
 * 
 * Le packagage Game comprend trois fonctions principales :
 *  
 *  - Controllers :
 *      Contient le controlleur des éléments, qui s'occupe de faire le pont entre la structure des elements et la structure de javafx;
 *      Contient le controlleur des scenes; Permet d'effectuer les transitions entres les scenes; 
 * 
 *  - Elements :
 *      Structure interne des éléments du jeu : Ennemy (personnage principal), Coins (les pieces), Hero (Tank, Furtif, Corps à corps)
 *      La structure permet u sauvegarde des attributs des elements tel que leurs positions, leur grandeur (rayon) et d'autres paramètre specifiques
 *      comme la vitesse et le type de déplacements à travers le temps;
 * 
 *  - Scenes :
 *      Contients les différentes scenes de jeu :
 *       - Menu Scene : contient le titre du jeu et les deux options de scene : Voir les meilleures scores // Nouvelle partie
 *       - Game Scene : contient les differens element du jeu et fait appel au controlleur d'element pour animer le jeu à travers une animation (AnimationTimer : javafx)
 *       - High Score Scene : affiche les meilleur score à l'aide du systeme de traitement de fichier; utilise les fichiers .txt dans scores du package FlappyEnnemy
 * 
 * Les images utilisés dans ce jeu sont sauvegardé dans le dossier Img; 
 * Les images du personnage ont été acheté sur "itch.io" et les images des heros ont été trouvé sur internet en tant qu'image libre de droit;
 */
