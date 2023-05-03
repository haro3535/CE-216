package ce;

import java.util.ArrayList;

public class GraphElement {

    private String word;
    private ArrayList<String> meanings;
    private ArrayList<String> synonyms;


    public GraphElement(String word, ArrayList<String> meanings, ArrayList<String> synonyms) {
        this.word = word;
        this.meanings = meanings;
        this.synonyms = synonyms;
    }


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public ArrayList<String> getMeanings() {
        return meanings;
    }

    public void setMeanings(ArrayList<String> meanings) {
        this.meanings = meanings;
    }

    public ArrayList<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(ArrayList<String> synonyms) {
        this.synonyms = synonyms;
    }
}
