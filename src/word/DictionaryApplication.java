package word;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class DictionaryApplication extends Application {
    DictionaryFeatures dicF = new DictionaryFeatures();

  public DictionaryApplication() throws FileNotFoundException {
  }

  public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
      BorderPane root = new BorderPane();
      HBox buttonCon = new HBox();
      buttonCon.setSpacing(10);
      dicF.element.listView.setItems(dicF.element.oListStavaka);
      dicF.element.listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
      dicF.data = new Scanner(dicF.file);
      BufferedReader reader = new BufferedReader(new FileReader("src/word/E_V.txt"));
      while (reader.readLine() != null) {
        dicF.lines++;
      }
      int j = 0;
      while(j < dicF.lines){
        String test = dicF.data.nextLine();
        String[] word = test.split("(?=<)", 2);
        dicF.element.oListStavaka.add(word[0]);
        dicF.element.oListStavaka1.add(word[1]);
        j++;
      }
      dicF.clickWord();

      dicF.enterPress();

      dicF.textField.textProperty().addListener((ChangeListener) (oLisFtStavaka, oldVal, newVal) -> dicF.search((String) oldVal, (String) newVal));

      dicF.addButton(primaryStage);
      dicF.deleteButton(primaryStage);
      dicF.speechButton();

      root.getChildren().add(new Label("Enter message:"));


      dicF.textField.setMaxHeight(20);
      dicF.textField.setMaxWidth(300);
      dicF.element.listView.setMaxWidth(400);



      buttonCon.getChildren().add(dicF.textField);
      buttonCon.getChildren().add(dicF.add);
      buttonCon.getChildren().add(dicF.delete);
      buttonCon.getChildren().add(dicF.speech);


      dicF.textField.setPadding(new Insets(10, 100, 10, 5));

      dicF.element.listView.setPadding(new Insets(10, 10, 10, 10));
      root.setLeft(dicF.element.listView);
      BorderPane.setMargin(dicF.element.listView, new Insets(10, 10, 10, 10));
      
      root.setCenter(dicF.browser);
      BorderPane.setMargin(dicF.browser, new Insets(10, 10, 10, 10));



      buttonCon.setPadding(new Insets(10,10,10,0));
      root.setTop(buttonCon);
      BorderPane.setMargin(buttonCon, new Insets(10, 10, 10, 10));


      Scene scene = new Scene(root, 700, 600);
      primaryStage.setTitle("JavaFX TextArea (o7planning.org)");
      primaryStage.setScene(scene);
      primaryStage.show();

    }
}
