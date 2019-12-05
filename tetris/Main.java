package tetris;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    //Variables
    public static Scene menuScene;
    public static Scene gameScene;
    public static Scene gameOverScene;
    public static Stage currStage;
    public static int sceneWidth = 1280;
    public static int sceneHeight = 720;

    public static boolean playingGame = true;
    public static final int SQUARESIZE = 25;
    public static  int width = SQUARESIZE * 12;
    public static int height = SQUARESIZE * 24;
    public static int[][] GRID = new int [width/SQUARESIZE][height/SQUARESIZE];
    public static int xMin = 336;
    public static int xMax = 336 + width;
    public static int yMin = 116;
    public static int yMax = 116 + height;

    @Override
    public void start(Stage primaryStage) throws Exception{
        currStage = primaryStage;
        AnchorPane menu = FXMLLoader.load(getClass().getResource("menu.fxml"));
        primaryStage.setTitle("T E T R I S");

        menuScene = new Scene(menu, sceneWidth, sceneHeight);
        primaryStage.setScene(menuScene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    @Override
    public void stop(){
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
