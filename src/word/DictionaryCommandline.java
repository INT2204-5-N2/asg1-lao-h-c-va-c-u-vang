package word;

public class DictionaryCommandline{
  DictionaryManagement x = new DictionaryManagement();
  public void showAllWords(){
    System.out.println("No    |English      |VietNam\n");
    for(int i=0; i<x.getWordCount(); i++){
      System.out.println(i + "    " + x.a.dictionary[i].word_target + "     " + x.a.dictionary[i].word_explain + "\n");
        }
  }
  public void dictionaryBasic(){
    x.insertFromCommandline();
    showAllWords();
  }

  public static void main(String args[]){
    DictionaryCommandline a = new DictionaryCommandline();
    a.dictionaryBasic();
  }
}
