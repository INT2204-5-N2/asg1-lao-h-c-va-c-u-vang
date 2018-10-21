package word;



import com.jfoenix.controls.JFXButton;
import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;



public class DictionaryFeatures{
  private static final String VOICE_FILE = "src/word/voice.wav";

  Create element = new Create();
  static int lines = 0;
  Button add = new Button("ADD");
  Button delete = new Button("DELETE");
  Button replace = new Button("REPLACE");
  Button done = new Button("DONE");
  Button test = new Button("TEST");
  Button speech = new Button("SPEECH");
  Button translate = new Button("TRANSLATE");
  TextField testTextF = new TextField();
  WebView browser = new WebView();
  WebEngine webEngine = browser.getEngine();
  private VoiceProvider tts = new VoiceProvider("f6190da583764c68b99019f36a32cbc5");
  private byte[] voice = new byte[0];
  private SQLCon sqlCon = new SQLCon();
  private PopUp popUp;

  public DictionaryFeatures() throws SQLException {
      popUp = new PopUp();
  }


  void search(String oldVal, String newVal){
    if(oldVal == null || newVal.isEmpty() || newVal.length() < oldVal.length()){
      element.jfxListView.setItems(element.oListStavaka);
    }
      String searchWord = newVal.toUpperCase();
      ObservableList<String> newList = FXCollections.observableArrayList();
      for(String entry : element.jfxListView.getItems()){
          if(entry.toUpperCase().startsWith(searchWord)){
          newList.add(entry);
      }
        element.jfxListView.setItems(newList);
    }
  }

  void addButton(Stage primaryStage) {
      add.setOnAction(event ->{
          VBox secondaryLayout = new VBox();
          Stage newWindow = new Stage();
          secondaryLayout.setSpacing(5);
          AtomicBoolean isAdd = new AtomicBoolean(true);
          Scene secondScene = new Scene(secondaryLayout, 700, 500);
          TextField addTextField = new TextField();
          addTextField.setMaxWidth(200);
          done.getStyleClass().add("button-raised");
          HTMLEditor htmlEditor = new HTMLEditor();
          htmlEditor.setPrefHeight(245);
          htmlEditor.setMinHeight(220);
          done.setOnAction(event1 -> {
              if(htmlEditor.getHtmlText().equals("")  || addTextField.getText().equals("")){
                  popUp.popAddMessage();
              }
              else {
                  int i;
                  for(i = 0; i<lines; i++){
                      if(addTextField.getText().equals(element.oListStavaka.get(i))) {
                          popUp.popMessage();
                          isAdd.set(false);
                          break;
                      }
                  }
                  if(i == lines){
                      isAdd.set(true);
                      lines++;
                      element.oListStavaka.add(addTextField.getText());
                      element.oListStavaka1.add(htmlEditor.getHtmlText());
                      try {
                          sqlCon.addToSQL(addTextField.getText(),htmlEditor.getHtmlText());
                      } catch (SQLException e) {
                          e.printStackTrace();
                      }
                  }
                  webEngine.loadContent("");
                  testTextF.setText("");
                  element.jfxListView.setItems(element.oListStavaka);
                  if(isAdd.get()) ((Stage)done.getScene().getWindow()).close();
              }

          });

          secondaryLayout.getChildren().add(new Label("Tu can them:"));
          secondaryLayout.getChildren().add(addTextField);
          secondaryLayout.getChildren().add(new Label("Nghia cua tu: "));
          secondaryLayout.getChildren().add(htmlEditor);
          secondaryLayout.getChildren().add(done);

          secondScene.getStylesheets().add(DictionaryApplication.class.getResource("JMetroLightTheme.css").toExternalForm());
          // New window (Stage)
          newWindow.setTitle("Second Stage");
          newWindow.setScene(secondScene);

          // Specifies the modality for new window.
          newWindow.initModality(Modality.WINDOW_MODAL);

          newWindow.initOwner(primaryStage);

          // Set position of second window, related to primary window.
          newWindow.setX(primaryStage.getX() + 200);
          newWindow.setY(primaryStage.getY() + 100);

          newWindow.show();
      });

  }

  void deleteButton() {
    delete.setOnAction(event -> {
        if(element.jfxListView.getSelectionModel().getSelectedItem() == null) popUp.popDeleteMessage();
        else deletePopUp();
    });
  }

  void speechButton(){
    speech.setOnAction(event -> {
      String soundWord = element.jfxListView.getSelectionModel().getSelectedItem();
      VoiceParameters params = new VoiceParameters(soundWord, Languages.English_UnitedStates);
      params.setCodec(AudioCodec.WAV);
      params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_8bit_mono);
      params.setBase64(false);
      params.setSSML(false);
      params.setRate(-2);
      try {
        voice = tts.speech(params);
        FileOutputStream fos =  new FileOutputStream(VOICE_FILE);
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      try {
        File soundFile = new File(VOICE_FILE);
        AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
        Clip clip;
        clip = AudioSystem.getClip();
        clip.open(sound);
        clip.start();
        soundFile.delete();
      } catch (UnsupportedAudioFileException | IOException e) {
        e.printStackTrace();
      } catch (LineUnavailableException e) {
          e.printStackTrace();
      }
    });
  }

  void translateButton(Stage primaryStage){
      translate.setOnAction(event->{
          Stage newWindow = new Stage();
          Button dich = new Button("DICH");
          VBox vBox = new VBox();
          vBox.setSpacing(10);
          HBox hbox = new HBox();
          hbox.setSpacing(5);
          Scene secondScene = new Scene(hbox, 700, 500);
          TextArea inputText = new TextArea();
          inputText.setPromptText("Vui long dien vao");
          TextArea outputText = new TextArea();
          outputText.setEditable(false);
          dich.setOnAction(event1 ->{
              try {
                  outputText.setText(Translator.translate("en", "vi", inputText.getText()));
              } catch (IOException e) {
                  e.printStackTrace();
              }
          });
          vBox.getChildren().add(inputText);
          vBox.getChildren().add(dich);
          hbox.getChildren().add(vBox);
          hbox.getChildren().add(outputText);
          secondScene.getStylesheets().add(DictionaryApplication.class.getResource("JMetroLightTheme.css").toExternalForm());
          // New window (Stage)
          newWindow.setTitle("Second Stage");
          newWindow.setScene(secondScene);

          // Specifies the modality for new window.
          newWindow.initModality(Modality.WINDOW_MODAL);
          newWindow.initOwner(primaryStage);

          newWindow.show();
      });

  }

  void replaceButton(Stage primaryStage){
      replace.setOnAction(event->{
          if(element.jfxListView.getSelectionModel().getSelectedItem() == null) popUp.popReplaceMessage();
          else{
              VBox secondaryLayout = new VBox();
              Stage newWindow = new Stage();
              secondaryLayout.setSpacing(5);
              Scene secondScene = new Scene(secondaryLayout, 700, 500);
              HTMLEditor htmlEditor = new HTMLEditor();
              htmlEditor.setPrefHeight(245);
              htmlEditor.setMinHeight(220);
              done.setOnAction(event1->{
                  try {
                      sqlCon.fixFromSQL(element.jfxListView.getSelectionModel().getSelectedItem(), htmlEditor.getHtmlText());
                  } catch (SQLException e) {
                      e.printStackTrace();
                  }
                  webEngine.loadContent(htmlEditor.getHtmlText());
                  ((Stage)done.getScene().getWindow()).close();
              });
              secondaryLayout.getChildren().add(new Label("Sua thanh :"));
              secondaryLayout.getChildren().add(htmlEditor);
              secondaryLayout.getChildren().add(done);

              secondScene.getStylesheets().add(DictionaryApplication.class.getResource("JMetroLightTheme.css").toExternalForm());


              // New window (Stage)
              newWindow.setTitle("Second Stage");
              newWindow.setScene(secondScene);


              // Specifies the modality for new window.
              newWindow.initModality(Modality.WINDOW_MODAL);

              newWindow.initOwner(primaryStage);

              // Set position of second window, related to primary window.
              newWindow.setX(primaryStage.getX() + 200);
              newWindow.setY(primaryStage.getY() + 100);

              newWindow.show();
          }

      });
  }

    //An vao tu
    void clickWord(){
      element.jfxListView.setOnMouseClicked(event -> {
          try {
              webEngine.loadContent( sqlCon.getMeanFromSQL(element.jfxListView.getSelectionModel().getSelectedItem()));
          } catch (SQLException e) {
              e.printStackTrace();
          }
    });
    }

    //An enter
    void enterPress(){
      element.jfxListView.setOnKeyPressed(new EventHandler<KeyEvent>(){
        @Override
        public void handle(KeyEvent event) {
          if(event.getCode() == KeyCode.ENTER) {
              try {
                 webEngine.loadContent( sqlCon.getMeanFromSQL(element.jfxListView.getSelectionModel().getSelectedItem()));
              } catch (SQLException e) {
                  e.printStackTrace();
              }
          }
        }
      });
    }

    private void deletePopUp(){
        Stage deletePop = new Stage();
        HBox deleteHBox = new HBox();
        Label deleteLabel = new Label("BAN MUON XOA TU NAY?");
        deleteLabel.setFont(Font.font("Cambria", 12));
        deleteHBox.setSpacing(20);
        JFXButton okay = new JFXButton("OKAY");
        JFXButton no = new JFXButton("NO");
        okay.getStyleClass().add("button-raised");
        no.getStyleClass().add("button-raised");
        VBox deleteVBox = new VBox();
        Scene deleteScene = new Scene(deleteVBox, 200, 100);
        deleteVBox.setSpacing(10);
        deletePop.setScene(deleteScene);
        deleteVBox.getChildren().add(deleteLabel);
        deleteHBox.getChildren().add(okay);
        deleteHBox.getChildren().add(no);
        deleteVBox.getChildren().add(deleteHBox);
        deletePop.setTitle("Are you sure?");
        deleteScene.getStylesheets().add(DictionaryApplication.class.getResource("JMetroLightTheme.css").toExternalForm());
        okay.setOnAction(event ->{
            String word = element.jfxListView.getSelectionModel().getSelectedItem();
            for (int i = 0; i <= lines; i++) {
                if (word.equals(element.oListStavaka.get(i))) {
                    element.oListStavaka.remove(i);
                    element.oListStavaka1.remove(i);
                    break;
                }
            }
            lines--;
            try {
                sqlCon.removeFromSQL(word);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            webEngine.loadContent("");
            testTextF.setText("");
            element.jfxListView.setItems(element.oListStavaka);
            ((Stage)okay.getScene().getWindow()).close();
        });

        no.setOnAction(event ->{
            ((Stage)no.getScene().getWindow()).close();
        });
        deletePop.show();
    }

}
