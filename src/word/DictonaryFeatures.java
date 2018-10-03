import java.io.*;
import java.util.Scanner;
import java.nio.file.Files;
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

public class DictionaryFeatures{
  ListView<String> listView = new ListView<String>();
  ObservableList<String> oListStavaka = FXCollections.observableArrayList();
  ObservableList<String> oListStavaka1 = FXCollections.observableArrayList();
  TextField textField = new TextField();
  Scanner data;
  File file = new File("src/word/E_V.txt");
  int lines = 0;

  public void search(String oldVal, String newVal){
    if(oldVal == null || newVal.isEmpty() || newVal.length() < oldVal.length()){
      listView.setItems(oListStavaka);
    }
      String searchWord = newVal.toUpperCase();
      ObservableList<String> newList = FXCollections.observableArrayList();
      for(String entry : listView.getItems()){
        String searching = entry;
        if(searching.toUpperCase().contains(searchWord)){
          newList.add(searching);
      }
      listView.setItems(newList);
    }
  }
}
