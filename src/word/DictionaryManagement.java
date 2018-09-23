package word;
import java.util.Scanner;

public class DictionaryManagement{
      private int wordCount;
      Dictionary a = new Dictionary();
      public int getWordCount(){
        return wordCount;
      }

  public void insertFromCommandline(){
    Scanner keyboard = new Scanner(System.in);
    Scanner word = new Scanner(System.in);
    System.out.println("Nhap so luong tu: ");
    wordCount = keyboard.nextInt();
    for(int i=0; i<wordCount; i++){
      a.dictionary[i].word_target = word.nextLine();
      a.dictionary[i].word_explain = word.nextLine();
    }
  }

}
