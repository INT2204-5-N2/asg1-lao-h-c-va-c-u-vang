package word;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
      //root.setPadding(new Insets(10));
      dicF.listView.setItems(dicF.oListStavaka);
      dicF.listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
      dicF.data = new Scanner(dicF.file);
      BufferedReader reader = new BufferedReader(new FileReader("/home/quanghuy/BTL/asg1-lao-h-c-va-c-u-vang/src/word/E_V.txt"));
      while (reader.readLine() != null) {
        dicF.lines++;
      }
      int j = 0;
      while(j < dicF.lines){
        String test = dicF.data.nextLine();
        String[] word = test.split("(?=<)", 2);
        dicF.oListStavaka.add(word[0]);
        dicF.oListStavaka1.add(word[1]);
        j++;
      }
      dicF.clickWord();

      dicF.enterPress();

      dicF.textField.textProperty().addListener((ChangeListener) (oListStavaka, oldVal, newVal) -> dicF.search((String) oldVal, (String) newVal));

      dicF.addButton(primaryStage);
      dicF.deleteButton(primaryStage);
      dicF.speechButton();

      root.getChildren().add(new Label("Enter message:"));


      dicF.textField.setMaxHeight(20);
      dicF.textField.setMaxWidth(300);
      dicF.listView.setMaxWidth(400);
      //dicF.browser.setMaxWidth(200);



      buttonCon.getChildren().add(dicF.textField);
      buttonCon.getChildren().add(dicF.add);
      buttonCon.getChildren().add(dicF.delete);
      buttonCon.getChildren().add(dicF.speech);


      dicF.textField.setPadding(new Insets(10, 100, 10, 5));
      /*root.setTop(dicF.textField);
      BorderPane.setMargin(dicF.textField, new Insets(10, 10, 10, 10));*/

      dicF.listView.setPadding(new Insets(10, 10, 10, 10));
      root.setLeft(dicF.listView);
      BorderPane.setMargin(dicF.listView, new Insets(10, 10, 10, 10));
      
      root.setCenter(dicF.browser);
      BorderPane.setMargin(dicF.browser, new Insets(10, 10, 10, 10));



      buttonCon.setPadding(new Insets(10,10,10,0));
      root.setTop(buttonCon);
      BorderPane.setMargin(buttonCon, new Insets(10, 10, 10, 10));

      /*root.setTopAnchor(dicF.textField,20.0);
      root.setLeftAnchor(dicF.textField, 10.0);
      root.setRightAnchor(dicF.textField, 290.0);
      root.setTopAnchor(dicF.listView, 50.5);
      root.setLeftAnchor(dicF.listView, 10.0);
      root.setRightAnchor(dicF.listView, 290.0);

      root.setTopAnchor(dicF.browser, 20.0);
      root.setLeftAnchor(dicF.browser, 220.0);
      root.setRightAnchor(dicF.browser, 10.0);
      root.setBottomAnchor(dicF.browser, 10.0);*/


      //root.getChildren().add(dicF.textField);
      //root.getChildren().add(dicF.listView);
      //root.getChildren().add(dicF.browser);
      //root.getChildren().add(dicF.add);
      //root.getChildren().add(dicF.delete);
      //root.getChildren().add(dicF.speech);
      Scene scene = new Scene(root, 700, 600);
      primaryStage.setTitle("JavaFX TextArea (o7planning.org)");
      primaryStage.setScene(scene);
      primaryStage.show();

    }
}
