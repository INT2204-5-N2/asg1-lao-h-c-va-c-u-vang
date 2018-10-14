package word;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopUp  {
    VBox layOut = new VBox();
    Stage extraStage = new Stage();
    Scene extraScene = new Scene(layOut, 230, 200);

    public void popMessage(Stage button){
        extraStage.setScene(extraScene);
        extraStage.initOwner(button);
        layOut.setSpacing(5);
        layOut.getChildren().add(new javafx.scene.control.Label("Tu da co san. Neu can thiet vui long FIX"));
        extraStage.setTitle("Tu da co san");
        extraStage.initModality(Modality.WINDOW_MODAL);
        extraStage.setX(button.getX()+200);
        extraStage.setY(button.getY()+200);
        extraStage.show();
    }

}