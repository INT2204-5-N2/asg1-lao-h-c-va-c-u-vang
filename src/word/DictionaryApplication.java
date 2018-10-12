package word;

import java.io.*;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.awt.Dimension;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView ;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;

public class DictionaryApplication extends Application {

    /*ListView<String> listView = new ListView<String>();
    ObservableList<String> oListStavaka = FXCollections.observableArrayList();
    ObservableList<String> oListStavaka1 = FXCollections.observableArrayList();
    TextField textField = new TextField();
    Scanner data;
    Scanner data1;
    File file = new File("src/word/E_V.txt");
    int lines = 0;*/
    //Button add = new Button("ADD");
    DictionaryFeatures dicF = new DictionaryFeatures();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException,IOException {
      VBox root = new VBox();
      root.setPadding(new Insets(10));
      root.setSpacing(5);
      dicF.listView.setItems(dicF.oListStavaka);
      dicF.listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
      /*WebView browser = new WebView();
      WebEngine webEngine = browser.getEngine();*/
      dicF.data = new Scanner(dicF.file);
      BufferedReader reader = new BufferedReader(new FileReader("/home/quanghuy/BTL/src/word/E_V.txt"));
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

      dicF.textField.textProperty().addListener(new ChangeListener(){
        public void changed(ObservableValue oListStavaka, Object oldVal, Object newVal){
          dicF.search((String) oldVal, (String) newVal);
        }
      });

      dicF.addButton(primaryStage);
      dicF.deleteButton(primaryStage);

      root.getChildren().add(new Label("Enter message:"));


      dicF.textField.setMaxHeight(20);
      dicF.textField.setMaxWidth(200);
      dicF.listView.setMaxWidth(200);

      root.getChildren().add(dicF.textField);
      root.getChildren().add(dicF.listView);
      root.getChildren().add(dicF.browser);
      root.getChildren().add(dicF.add);
      root.getChildren().add(dicF.delete);
      root.getChildren().add(dicF.speech);
      Scene scene = new Scene(root, 500, 500);
      primaryStage.setTitle("JavaFX TextArea (o7planning.org)");
      primaryStage.setScene(scene);
      primaryStage.show();

    }
}
