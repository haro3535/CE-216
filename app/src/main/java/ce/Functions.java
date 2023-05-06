package ce;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Functions implements Runnable {

    private final ArrayList<LinkedList<String>> meanings = new ArrayList<>();
    private String filepath;
    private String searchIn;
    private String foundIn;
    private String word;
    StringBuilder meaningTextContent = new StringBuilder();

    protected void searchWord(){

        String target = getWord().toLowerCase(Locale.ENGLISH);

        try {

            FileReader fileReader = new FileReader(filepath, StandardCharsets.UTF_8);
            Scanner reader = new Scanner(fileReader);

            ArrayList<String> foundWords = new ArrayList<>();

            int goAFewLine = 0;
            boolean isFound = false;
            while (reader.hasNext()){
                String data = reader.nextLine();
                String[] splitData = data.split(";");
                if (splitData.length > 0) {
                    if (splitData[0].toLowerCase(Locale.ENGLISH).equals(target)) {
                        foundWords.add(data);
                        isFound = true;
                    }
                }
                if (isFound) {
                    goAFewLine++;
                    if (goAFewLine > 5) {
                        break;
                    }
                }
            }

            parseMeanings(foundWords);
            mergeMeanings(foundWords);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void parseMeanings(ArrayList<String> foundWords){

        for (String data:
             foundWords) {

            String meaningPart = data.split(";")[1];

            String[] meanings = meaningPart.split("&");

            getMeanings().add(new LinkedList<>());
            for (String meaning:
                 meanings) {
                getMeanings().get(getMeanings().size()-1).add(meaning);
            }
        }
    }

    protected void mergeMeanings(ArrayList<String> foundWords){

        int meaningCounter = 1;
        for (int i = 0; i < foundWords.size(); i++) {

            /*
                - foundWords.get(i).split(";")[0] -> Word that we are looking for
                - foundWords.get(i).split(";")[1] -> Meanings of the Word
                - foundWords.get(i).split(";")[2] -> Synonyms of the Word
            */

            String[] graphParts = foundWords.get(i).split(";");

            meaningTextContent.append(foundIn.toUpperCase(Locale.ENGLISH)).append("\n");

            String[] meanings = graphParts[1].split("&");

            for (String meaning : meanings) {
                meaningTextContent.append(meaningCounter).append(". ").append(meaning).append("\n");
                meaningCounter++;
            }
            meaningCounter = 1;

            if (foundWords.size() != (i+1)) {
                meaningTextContent.append("--------\n");
            }
        }
        meaningTextContent.append("______________________________________\n");
    }

    protected void splitFileName(){

        String fileName = filepath;
        char separator = '\\';
        String[] str_arr= fileName.replaceAll(Pattern.quote(String.valueOf(separator)), "\\\\").split("\\\\");
        String baseName = str_arr[1];
        String[] tokens = baseName.split("[-.]");
        setSearchIn(tokens[0]);
        setFoundIn(tokens[1]);

    }


    @Override
    public void run() {

        splitFileName();
        searchWord();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getSearchIn() {
        return searchIn;
    }

    public void setSearchIn(String searchIn) {
        this.searchIn = searchIn;
    }

    public String getFoundIn() {
        return foundIn;
    }

    public void setFoundIn(String foundIn) {
        this.foundIn = foundIn;
    }

    public ArrayList<LinkedList<String>> getMeanings() {
        return meanings;
    }

    public StringBuilder getMeaningTextContent() {
        return meaningTextContent;
    }
}
