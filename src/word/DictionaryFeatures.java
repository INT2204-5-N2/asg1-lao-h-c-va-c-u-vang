package word;


import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class DictionaryFeatures{
  Create element = new Create();
  TextField textField = new TextField();
  Scanner data;
  File file = new File("src/word/E_V.txt");	//
  int lines = 0;
  Button add = new Button("ADD");
  Button delete = new Button("DELETE");
  Button replace = new Button("REPLACE");
  private Button done = new Button("DONE");
  Button speech = new Button("SPEECH");
  WebView browser = new WebView();
  private WebEngine webEngine = browser.getEngine();
  private Path path = Paths.get("src/word/E_V.txt");//
  private VoiceProvider tts = new VoiceProvider("f6190da583764c68b99019f36a32cbc5");
  private byte[] voice = new byte[0];
  private File soundFile = new File("src/word/voice.mp3");
  private Clip clip;


  public DictionaryFeatures() {
  }


  void search(String oldVal, String newVal){
    if(oldVal == null || newVal.isEmpty() || newVal.length() < oldVal.length()){
      element.listView.setItems(element.oListStavaka);
    }
      String searchWord = newVal.toUpperCase();
      ObservableList<String> newList = FXCollections.observableArrayList();
      for(String entry : element.listView.getItems()){
          if(entry.toUpperCase().startsWith(searchWord)){
          newList.add(entry);
      }
        element.listView.setItems(newList);
    }
  }

    void addButton(Stage primaryStage) {
    add.setOnAction(event -> {
      VBox secondaryLayout = new VBox();
      Stage newWindow = new Stage();
      secondaryLayout.setSpacing(5);
      AtomicBoolean isAdd = new AtomicBoolean(true);
      Scene secondScene = new Scene(secondaryLayout, 700, 500);
      TextField addTextField = new TextField();
      HTMLEditor htmlEditor = new HTMLEditor();
      htmlEditor.setPrefHeight(245);
      htmlEditor.setMinHeight(220);
      done.setOnAction(event1 -> {
        int i;
        for(i = 0; i<lines; i++){
            if(addTextField.getText().equals(element.oListStavaka.get(i))) {
                PopUp popUp = new PopUp();
                popUp.popMessage(newWindow);
                isAdd.set(false);
                break;
            }
        }
        if(i == lines){
          lines++;
          element.oListStavaka.add(addTextField.getText());
          element.oListStavaka1.add(htmlEditor.getHtmlText());
          System.out.println(htmlEditor.getHtmlText());
          String x = addTextField.getText() + htmlEditor.getHtmlText() + "\n";
          byte[] writeFile = x.getBytes();//
          try{
            Files.write(path, writeFile, StandardOpenOption.APPEND);//
          }catch (Exception e){
            System.out.println("Loi");
          }
        }
        webEngine.loadContent("");
        textField.setText("");
        element.listView.setItems(element.oListStavaka);
        if(isAdd.get() == true) ((Stage)done.getScene().getWindow()).close();

        });

      addTextField.setMaxWidth(150);
      secondaryLayout.getChildren().add(new Label("Tu can them:"));
      secondaryLayout.getChildren().add(addTextField);
      secondaryLayout.getChildren().add(new Label("Nghia cua tu: "));
      secondaryLayout.getChildren().add(htmlEditor);
      secondaryLayout.getChildren().add(done);

        // New window (Stage)
      newWindow.setTitle("Second Stage");
      newWindow.setScene(secondScene);

      // Specifies the modality for new window.
      newWindow.initModality(Modality.WINDOW_MODAL);

      /*// Specifies the owner Window (parent) for new window
      newWindow.initOwner(primaryStage);*/

      // Set position of second window, related to primary window.
      newWindow.setX(primaryStage.getX() + 200);
      newWindow.setY(primaryStage.getY() + 100);

      newWindow.show();
    });

  }

  void deleteButton(Stage primaryStage) throws IOException {
    delete.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event) {
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
      }
    });
  }

  void speechButton(){
    speech.setOnAction(event -> {
      String soundWord = element.listView.getSelectionModel().getSelectedItem().toString();
      VoiceParameters params = new VoiceParameters(soundWord, Languages.English_UnitedStates);
      params.setCodec(AudioCodec.WAV);
      params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
      params.setBase64(false);
      params.setSSML(false);
      params.setRate(0);
      try {
        voice = tts.speech(params);
        FileOutputStream fos =  new FileOutputStream("src/word/voice.mp3");
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      try {
        AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
        DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(sound);
        clip.start();
        soundFile.delete();
      } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
        e.printStackTrace();
      }
    });
  }

    //An vao tu
    void clickWord(){
      element.listView.setOnMouseClicked(new EventHandler<MouseEvent>()  {
        @Override
        public void handle(MouseEvent event) {
          for(int i=0; i<lines; i++){
            if(element.listView.getSelectionModel().getSelectedItem().toString().equals(element.oListStavaka.get(i))){
              webEngine.loadContent(element.oListStavaka1.get(i));
              break;
            }
          }
      }
      });
    }

    //An enter
    void enterPress(){
      element.listView.setOnKeyPressed(new EventHandler<KeyEvent>(){
        @Override
        public void handle(KeyEvent event) {
          if(event.getCode() == KeyCode.ENTER) {
            for(int i=0; i<lines; i++){
              if(element.listView.getSelectionModel().getSelectedItem().toString().equals(element.oListStavaka.get(i))){
                webEngine.loadContent(element.oListStavaka1.get(i));
                break;
              }
            }
          }
        }
      });
    }

}
