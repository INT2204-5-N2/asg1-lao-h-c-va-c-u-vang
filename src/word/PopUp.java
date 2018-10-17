package word;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class PopUp  {
    VBox layOut = new VBox();
    Stage extraStage = new Stage();
    Scene extraScene = new Scene(layOut, 400, 400);

    public void popMessage(Stage button){
        extraStage.setScene(extraScene);
        extraStage.initOwner(button);
        layOut.setSpacing(5);
        layOut.getChildren().add(new javafx.scene.control.Label("TU NAY DA CO SAN"));
        extraStage.setTitle("Tu da co san");
        extraStage.initModality(Modality.WINDOW_MODAL);
        extraStage.setX(button.getX()+200);
        extraStage.setY(button.getY()+200);
        extraStage.show();
    }

    /*public void deletePopUp(ListView listView, ){
        Button okay = new Button("OKAY");
        Button no = new Button("NO");
        Stage deletePop = new Stage();
        VBox deleteVBox = new VBox();
        Scene deleteScene = new Scene(deleteVBox, 400, 400);
        deleteVBox.setSpacing(10);
        deletePop.setScene(deleteScene);
        deleteVBox.getChildren().add(new Label("Ban co chac la muon xoa tu nay khong"))
        deleteVBox.getChildren().add(okay);
        deleteVBox.getChildren().add(no);
        deletePop.setTitle("Are you sure?");
        okay.setOnAction(event ->{
            String word = element.listView.getSelectionModel().getSelectedItem();
            int tempLine = 0;
            for (int i = 0; i <= lines; i++) {
                if (word.equals(element.oListStavaka.get(i))) {
                    element.oListStavaka.remove(i);
                    element.oListStavaka1.remove(i);
                    break;
                }
                tempLine++;
            }
            try {
                data = new Scanner(file);
                File temp_file = new File("src/word/E_V_Temp.txt");
                if (temp_file.createNewFile()) {
                    System.out.println("File da tao");
                } else {
                    System.out.println("File chua tao");
                }

                Path temp_path = Paths.get("src/word/E_V_Temp.txt");
                for (int i = 0; i < lines; i++) {
                    if (i == tempLine) continue;
                    String temp_line = data.nextLine() + "\n";
                    Files.write(temp_path, temp_line.getBytes(), StandardOpenOption.APPEND);
                }
                Files.delete(path);
                Files.move(temp_path, temp_path.resolveSibling("E_V.txt"));
            } catch (Exception e) {
                System.out.println("Loi");
            }
            lines--;
            webEngine.loadContent("");
            textField.setText("");
            element.listView.setItems(element.oListStavaka);
            ((Stage)okay.getScene().getWindow()).close();
        });

        no.setOnAction(event ->{
            ((Stage)no.getScene().getWindow()).close();
        });
    }*/

}