package word;

public class Word{
    String word_target = new String();
    String word_explain = new String();

    @Override
    public String toString(){
      return this.word_target;
    }

    public int compare(Word o1, Word o2){
      return o1.word_target.compareTo(o2.word_target);
    }
}
