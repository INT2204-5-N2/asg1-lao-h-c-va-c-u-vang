package word;

import java.io.*;
import java.util.Scanner;
import java.nio.file.*;
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
import javafx.scene.web.WebView;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
//import java.nio.file.Paths;

public class DictionaryFeatures{
  ListView<String> listView = new ListView<String>();
  ObservableList<String> oListStavaka = FXCollections.observableArrayList();
  ObservableList<String> oListStavaka1 = FXCollections.observableArrayList();
  TextField textField = new TextField();
  Scanner data;
  File file = new File("src/word/E_V.txt");	//
  int lines = 0;
  Button add = new Button("ADD");
  Button done = new Button("DONE");
  WebView browser = new WebView();
  WebEngine webEngine = browser.getEngine();
  Path path = Paths.get("src/word/E_V.txt");//

  public void search(String oldVal, String newVal){
    if(oldVal == null || newVal.isEmpty() || newVal.length() < oldVal.length()){
      listView.setItems(oListStavaka);
    }
      String searchWord = newVal.toUpperCase();
      ObservableList<String> newList = FXCollections.observableArrayList();
      for(String entry : listView.getItems()){
        String searching = entry;
        if(searching.toUpperCase().startsWith(searchWord)){
          newList.add(searching);
      }
      listView.setItems(newList);
    }
  }

  public void addButton(Stage primaryStage) throws IOException {
    add.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event)  {
        VBox secondaryLayout = new VBox();
        //secondaryLayout.setPadding(new Insets(10));
        secondaryLayout.setSpacing(5);

          Scene secondScene = new Scene(secondaryLayout, 230, 100);
          TextField addTextField = new TextField();
          TextField addWordMean = new TextField();
          done.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
              lines++;
              String x = new String(addTextField.getText() + "<html> <head> <title> -" + addTextField.getText() + "</title> </head> <body> " + addWordMean.getText() + "</body> </html>");
              oListStavaka.add(addTextField.getText());
              oListStavaka1.add(addWordMean.getText());
              byte[] writeFile = x.getBytes();//
              try{
              Files.write(path, writeFile, StandardOpenOption.APPEND);//
            } catch (Exception e){
              System.out.println("Loi");
            }

            }
          });

          addTextField.setMaxWidth(150);
          secondaryLayout.getChildren().add(new Label("Tu can them:"));
          secondaryLayout.getChildren().add(addTextField);
          secondaryLayout.getChildren().add(new Label("Nghia cua tu: "));
          secondaryLayout.getChildren().add(addWordMean);
          secondaryLayout.getChildren().add(done);

          // New window (Stage)
          Stage newWindow = new Stage();
          newWindow.setTitle("Second Stage");
          newWindow.setScene(secondScene);

          // Specifies the modality for new window.
          newWindow.initModality(Modality.WINDOW_MODAL);

          // Specifies the owner Window (parent) for new window
          newWindow.initOwner(primaryStage);

          // Set position of second window, related to primary window.
          newWindow.setX(primaryStage.getX() + 200);
          newWindow.setY(primaryStage.getY() + 100);

          newWindow.show();
      }
    });

  }


    //An vao tu
    public void clickWord(){
      listView.setOnMouseClicked(new EventHandler<MouseEvent>()  {
        @Override
        public void handle(MouseEvent event) {
          for(int i=0; i<lines; i++){
            if(listView.getSelectionModel().getSelectedItem().toString().equals(oListStavaka.get(i))){
              webEngine.loadContent(oListStavaka1.get(i));
              break;
            }
          }
      }
      });
    }

    //An enter
    public void enterPress(){
      listView.setOnKeyPressed(new EventHandler<KeyEvent>(){
        @Override
        public void handle(KeyEvent event) {
          if(event.getCode() == KeyCode.ENTER) {
            for(int i=0; i<lines; i++){
              if(listView.getSelectionModel().getSelectedItem().toString().equals(oListStavaka.get(i))){
                webEngine.loadContent(oListStavaka1.get(i));
                break;
              }
            }
          }
        }
      });
    }

}
