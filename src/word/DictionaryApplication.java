package word;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class DictionaryApplication extends Application {
    DictionaryFeatures dicF = new DictionaryFeatures();

  public DictionaryApplication() throws SQLException {
  }

  public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
    String urlCon = "jdbc:sqlite:src/word/dictionaries.db";
    Connection conn = DriverManager.getConnection(urlCon);

      BorderPane root = new BorderPane();
      HBox buttonCon = new HBox();
      buttonCon.setSpacing(10);
      VBox testBox = new VBox();
      VBox testBox2 = new VBox();
      dicF.element.jfxListView.maxHeightProperty().bind( testBox.heightProperty());
      testBox2.setSpacing(10);
      testBox.setSpacing(10);
      dicF.element.jfxListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
      dicF.element.jfxListView.getStyleClass().add("myListView");
      dicF.test.getStyleClass().add("button-raised");
      dicF.add.getStyleClass().add("button-raised");
      dicF.delete.getStyleClass().add("button-raised");
      dicF.replace.getStyleClass().add("button-raised");
      dicF.speech.getStyleClass().add("button-raised");
      dicF.webEngine.setUserStyleSheetLocation(getClass().getResource("styleweb.css").toString());
      dicF.testTextF.setPromptText("Tim kiem");
      String url = "SELECT word,detail from tbl_edict ";
      Statement statement = conn.createStatement();
      ResultSet rs = statement.executeQuery(url);
      while(rs.next()){
        dicF.element.oListStavaka.add(rs.getString("word"));
        dicF.element.oListStavaka1.add(rs.getString("detail"));
        DictionaryFeatures.lines++;
      }

      dicF.element.jfxListView.setItems(dicF.element.oListStavaka);
      dicF.clickWord();

      dicF.enterPress();

      dicF.testTextF.textProperty().addListener((ChangeListener) (oLisFtStavaka, oldVal, newVal) -> dicF.search((String) oldVal, (String) newVal));

      testBox.getChildren().add(dicF.testTextF);
      testBox.getChildren().add(dicF.element.jfxListView);

      testBox2.getChildren().add(buttonCon);
      testBox2.getChildren().add(dicF.browser);

      dicF.addButton(primaryStage);
      dicF.deleteButton();
      dicF.speechButton();
      dicF.translateButton(primaryStage);
      dicF.replaceButton(primaryStage);
      root.getChildren().add(new Label("Enter message:"));


      dicF.testTextF.setPrefHeight(40);
      dicF.testTextF.setPrefWidth(100);
      dicF.element.jfxListView.setPrefHeight(1000);
      dicF.browser.setPrefHeight(dicF.element.jfxListView.getPrefHeight());
      dicF.element.jfxListView.setPadding(new Insets(0, 0, 0, 0));



      buttonCon.getChildren().add(dicF.add);
      buttonCon.getChildren().add(dicF.delete);
      buttonCon.getChildren().add(dicF.speech);
      buttonCon.getChildren().add(dicF.replace);
      buttonCon.getChildren().add(dicF.translate);


      root.setLeft(testBox);
      BorderPane.setMargin(testBox, new Insets(10, 10, 10, 10));

      
      root.setCenter(testBox2);
      BorderPane.setMargin(testBox2, new Insets(10, 10, 10, 10));

      /*buttonCon.setPadding(new Insets(10, 10, 10, 10));
      root.setBottom(buttonCon);
      BorderPane.setMargin(buttonCon, new Insets(10, 10, 10, 10));*/



      //dicF.testTextF.setPadding(new Insets(10,10,10,0));
      /*root.setTop(dicF.testTextF);
      BorderPane.setMargin(dicF.testTextF, new Insets(10, 10, 5, 0));*/




      final Scene scene = new Scene(root, 750, 600, Color.WHITE);
      scene.getStylesheets().add(DictionaryApplication.class.getResource("JMetroLightTheme.css").toExternalForm());
      primaryStage.setTitle("JavaFX TextArea (o7planning.org)");
      primaryStage.setScene(scene);
      primaryStage.show();

    }
}
