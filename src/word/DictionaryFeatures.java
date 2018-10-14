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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
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

public class DictionaryFeatures{
  ListView<String> listView = new ListView<String>();
  ObservableList<String> oListStavaka = FXCollections.observableArrayList();
  ObservableList<String> oListStavaka1 = FXCollections.observableArrayList();
  TextField textField = new TextField();
  Scanner data;
  File file = new File("src/word/E_V.txt");	//
  int lines = 0;
  Button add = new Button("ADD");
  Button delete = new Button("DELETE");
  Button replace = new Button("REPLACE");
  Button done = new Button("DONE");
  Button speech = new Button("SPEECH");
  WebView browser = new WebView();
  WebEngine webEngine = browser.getEngine();
  Path path = Paths.get("src/word/E_V.txt");//
  VoiceProvider tts = new VoiceProvider("f6190da583764c68b99019f36a32cbc5");
  byte[] voice = new byte[0];
  File soundFile = new File("src/word/voice.mp3");
  Clip clip;


  public DictionaryFeatures() {
  }


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

  public void addButton(Stage primaryStage) {
    add.setOnAction(event -> {
      VBox secondaryLayout = new VBox();
      Stage newWindow = new Stage();
      secondaryLayout.setSpacing(5);

      Scene secondScene = new Scene(secondaryLayout, 230, 200);
      TextField addTextField = new TextField();
      TextField addWordMean = new TextField();
      done.setOnAction(event1 -> {
        int i;
        for(i = 0; i<lines; i++){
            if(addTextField.getText().equals(oListStavaka.get(i))) {
                PopUp popUp = new PopUp();
                popUp.popMessage(newWindow);
                break;
            }
        }
        if(i == lines){
          lines++;
          String x = addTextField.getText() + "<html> <head> <title> -" + addTextField.getText() + "</title> </head> <body> " + addWordMean.getText() + "</body> </html>\n";
          oListStavaka.add(addTextField.getText());
          oListStavaka1.add(addWordMean.getText());
          byte[] writeFile = x.getBytes();//
          try{
            Files.write(path, writeFile, StandardOpenOption.APPEND);//
          }catch (Exception e){
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
    });

  }

  public void deleteButton(Stage primaryStage) throws IOException {
    delete.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event)  {
        VBox secondaryLayout = new VBox();
        secondaryLayout.setSpacing(5);

          Scene secondScene = new Scene(secondaryLayout, 230, 100);
          TextField deleteWord = new TextField();
          done.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
              String word = deleteWord.getText();
              int tempLine = 0;
              for(int i=0; i<=lines; i++){
                if(word.equals(oListStavaka.get(i))){
                  oListStavaka.remove(i);
                  oListStavaka1.remove(i);
                  break;
                }
                tempLine++;
              }
              try{
                data = new Scanner(file);
                File temp_file = new File("src/word/E_V_Temp.txt");
                if(temp_file.createNewFile()){
                  System.out.println("File da tao");
                }else{
                  System.out.println("File chua tao");
                }

                Path temp_path = Paths.get("src/word/E_V_Temp.txt");
                for(int i=0;i <lines; i++){
                  if(i == tempLine) continue;
                  String temp_line = new String(data.nextLine() + "\n");
                  Files.write(temp_path,temp_line.getBytes(), StandardOpenOption.APPEND);
                }
                Files.delete(path);
                Files.move(temp_path, temp_path.resolveSibling("E_V.txt"));
              } catch (Exception e){
                System.out.println("Loi");
              }
              lines--;
            }
          });

          deleteWord.setMaxWidth(150);
          secondaryLayout.getChildren().add(new Label("Tu can xoa: "));
          secondaryLayout.getChildren().add(deleteWord);
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

  public void speechButton(){
    speech.setOnAction(event -> {
      String soundWord = listView.getSelectionModel().getSelectedItem().toString();
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
      } catch (UnsupportedAudioFileException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (LineUnavailableException e) {
        e.printStackTrace();
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
