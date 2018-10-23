package word;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;


public class DictionaryApplication extends Application {
    private DictionaryFeatures dicF = new DictionaryFeatures();

    public DictionaryApplication() throws SQLException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        HBox buttonCon = new HBox();
        buttonCon.setSpacing(10);
        VBox testBox = new VBox();
        VBox testBox2 = new VBox();
        dicF.element.jfxListView.maxHeightProperty().bind(testBox.heightProperty());
        testBox2.setSpacing(10);
        testBox.setSpacing(10);
        dicF.webEngine.setUserStyleSheetLocation(getClass().getResource("styleweb.css").toString());

        dicF.testTextF.textProperty().addListener((ChangeListener) (oListStavaka, oldVal, newVal) -> {
            dicF.search((String) oldVal, (String) newVal);
        });

        testBox.getChildren().add(dicF.testTextF);
        testBox.getChildren().add(dicF.element.jfxListView);

        testBox2.getChildren().add(buttonCon);
        testBox2.getChildren().add(dicF.browser);

        dicF.run(primaryStage);
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


        final Scene scene = new Scene(root, 750, 600, Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.UNIFIED);
        scene.getStylesheets().add(DictionaryApplication.class.getResource("JMetroLightTheme.css").toExternalForm());
        primaryStage.setTitle("DICTIONARY");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
