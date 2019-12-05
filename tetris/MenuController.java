package tetris;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    public void ChangeScene(ActionEvent event) throws IOException {
        AnchorPane game = FXMLLoader.load(getClass().getResource("main.fxml"));
        Main.gameScene = new Scene(game, Main.sceneWidth, Main.sceneHeight);

        Stage currentStage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        currentStage.setScene(Main.gameScene);
        currentStage.show();
    }

}
