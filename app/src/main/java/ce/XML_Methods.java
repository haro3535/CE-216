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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class XML_Methods implements Runnable {

    private Node foundEntries;
    private final ArrayList<LinkedList<String>> meanings = new ArrayList<>();
    private String filepath;
    private String searchIn;
    private String foundIn;
    private String word;
    StringBuilder meaningTextContent = new StringBuilder();

    protected void arrangeEntriesTextContents(){

        // To arrange each entry that we found in xml file.

        if (foundEntries != null) {
            NodeList entries = foundEntries.getChildNodes();

            for (int i = 0; i < entries.getLength(); i++) {
                Element entry = (Element) entries.item(i);
                //System.out.println(entry.getTextContent());

                // Some XML files have more than one <sense> tag, so if he encounters such a situation, so that there are no problems.
                NodeList senses = entry.getElementsByTagName("sense");
                //System.out.println(senses.getLength());


                // Eş sesli kelimeler için
                meanings.add(new LinkedList<>());

                for (int j = 0; j < senses.getLength(); j++) {
                    // Some XML dictionaries also have multiple <cit> tags or <def> tag inside the <sense> tag.
                    // Also, some of has multiple <quote> tag inside <cit> tag
                    // For that reason, we take them all and
                    Element currentSense = (Element) senses.item(j);

                    NodeList senseChildNodes = currentSense.getChildNodes();


                    for (int k = 0; k < senseChildNodes.getLength(); k++) {
                        // her sense çoçuğunu elemente çevirme
                        Element child = (Element) senseChildNodes.item(k);

                        // Çocuğu kontrol etmek için
                        if (child.getTagName().equals("cit") && !child.getAttribute("type").equals("example")) {

                            // cit in varsa birden fazla quote unu almak için
                            NodeList citChildren = child.getChildNodes();

                            // Şu an için aynı cit in içinde bulundan birden fazla quote u yan yana yazdırmak için
                            StringBuilder multipleQuotes = new StringBuilder();
                            for (int z = 0; z < citChildren.getLength(); z++) {
                                Element citChild = (Element) citChildren.item(z);
                                if (citChild.getTagName().equals("quote")){
                                    multipleQuotes.append(citChild.getTextContent());
                                }
                                if ((z+1) < citChildren.getLength()) {
                                    Element nextCitChild = (Element) citChildren.item((z+1));
                                    if (nextCitChild.getTagName().equals("quote")) {
                                        multipleQuotes.append(", ");
                                    }
                                }
                            }
                            meanings.get(meanings.size()-1).add(String.valueOf(multipleQuotes));
                        }
                        else if (child.getTagName().equals("def")) {
                            meanings.get(meanings.size()-1).add(child.getTextContent());
                        }
                    }
                }
            }
        }
    }

    protected void findEntries(){

        String target = getWord();

        XMLInputFactory xmlInputFactory = XMLInputFactory.newDefaultFactory();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();

        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(filepath));
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Node rootNode = doc.createElement("body");

            ArrayList<String> tagQueue = new ArrayList<>();
            String currentTextValue = "";

            boolean isInsideTheEntry = false;
            boolean isItCorrectEntry = true;
            boolean isAllFound = false;
            while (xmlEventReader.hasNext() && !isAllFound){

                XMLEvent nextEvent = xmlEventReader.nextEvent();

                if (isItCorrectEntry) {
                    if (nextEvent.isStartElement()) {

                        StartElement startElement = nextEvent.asStartElement();

                        // entry nin içinde olup olmadığını anlamak için
                        if (startElement.getName().getLocalPart().equals("entry") && !isInsideTheEntry) {
                            isInsideTheEntry = true;
                        }

                        if (isInsideTheEntry){
                            tagQueue.add(startElement.getName().getLocalPart());
                            Element currentNode = doc.createElement(startElement.getName().getLocalPart());
                            if (tagQueue.size() == 1){
                                // Direkt entry ye ekliyor
                                rootNode.appendChild(currentNode);
                            }
                            if (tagQueue.size() > 1) {
                                Node parentE = rootNode;
                                Node childE = parentE.getLastChild();

                                for (int i = 0; i < (tagQueue.size()-1); i++) {
                                    parentE = childE;
                                    // Son eleman daha eklenmediği için atlama yaptırıyorum
                                    if ((i+1) != (tagQueue.size()-1)) {
                                        childE = parentE.getLastChild();
                                    }
                                }


                                if (startElement.getAttributes().hasNext()) {
                                    Attribute attribute = startElement.getAttributes().next();
                                    currentNode.setAttribute(attribute.getName().getLocalPart(),attribute.getValue());
                                    //System.out.println(attribute.getName().getLocalPart() + " - " + attribute.getValue());
                                }

                                parentE.appendChild(currentNode);
                            }
                        }
                    }

                    if (nextEvent.isCharacters()) {
                        if (!nextEvent.asCharacters().getData().equals("") && isInsideTheEntry
                                && !nextEvent.asCharacters().getData().contains("\n")) {
                            currentTextValue = nextEvent.asCharacters().getData();
                        }
                        if (!tagQueue.isEmpty()) {
                            // Burada entry nin bizim istedğimiz entry olup olmadığına bakıyor
                            if (tagQueue.get(tagQueue.size()-1).equals("orth")) {
                                if(!target.equals(nextEvent.asCharacters().getData())){
                                    isItCorrectEntry = false;
                                    if (rootNode.getChildNodes().getLength() > 0 ) {
                                        rootNode.removeChild(rootNode.getLastChild());
                                    }
                                    if (rootNode.getChildNodes().getLength() > 1 ) {
                                        isAllFound = true;
                                    }
                                    continue;
                                }
                            }
                        }
                    }
                }


                if (nextEvent.isEndElement()) {
                    EndElement endElement = nextEvent.asEndElement();

                    if (endElement.getName().getLocalPart().equals("entry")) {
                        isItCorrectEntry = true;
                        tagQueue.clear();
                        continue;
                    }

                    // Eğer doğru entry değil ise devam ediyor
                    if (!endElement.getName().getLocalPart().equals("entry") && !isItCorrectEntry){
                        continue;
                    }

                    if (!tagQueue.isEmpty()) {
                        if (endElement.getName().getLocalPart().equals(tagQueue.get(tagQueue.size()-1))) {

                            if (!currentTextValue.equals("")) {
                                Node parentE = rootNode;
                                Node childE = parentE.getLastChild();

                                for (int i = 0; i < (tagQueue.size()-1); i++) {
                                    parentE = childE;
                                    childE = parentE.getLastChild();
                                }
                                childE.setTextContent(currentTextValue);
                                currentTextValue = "";
                            }

                            tagQueue.remove(tagQueue.size()-1);
                        }
                    }
                }
            }

            // Bulduğumuz entryleri bir <body> inin içine ekleyip foundEntriese ekliyoruz
            setFoundEntries(rootNode);
            //System.out.println(rootNode.getChildNodes().getLength());

        } catch (FileNotFoundException | XMLStreamException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    protected void mergeMeanings(){

        //System.out.println("Oğuz");

        if (getMeanings().size()>0) {

            meaningTextContent.append(foundIn).append("\n");

            for (LinkedList<String> linkedList:
                    getMeanings()) {
                int meaningCounter = 1;
                for (String meaning:
                        linkedList) {
                    meaningTextContent.append(meaningCounter).append(". ").append(meaning);
                    meaningTextContent.append("\n");
                    //System.out.println("Berke");
                    meaningCounter++;
                }
                meaningTextContent.append("--------\n");
                //System.out.println("Ali");
            }
            meaningTextContent.append("______________________________________\n");
        }

    }

    protected void splitFileName(){

        String fileName = filepath;
        char separator = '\\';
        String[] str_arr= fileName.replaceAll(Pattern.quote(String.valueOf(separator)), "\\\\").split("\\\\");
        String baseName = str_arr[1];
        String[] tokens = baseName.split("[-.]");
        setSearchIn(tokens[0]);
        setFoundIn(tokens[1]);

        // Bu aşağıdakiler farklı yerde kullanılcak
        String firstFileName = tokens[0].concat("-eng").concat(".xml");
        String secondFileName = "eng-".concat(tokens[1]).concat(".xml");
        System.out.println(firstFileName);
        System.out.println(secondFileName);

    }

    @Override
    public void run() {

        splitFileName();
        findEntries();
        arrangeEntriesTextContents();
        mergeMeanings();
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

    public Node getFoundEntries() {
        return foundEntries;
    }

    public void setFoundEntries(Node foundEntries) {
        this.foundEntries = foundEntries;
    }

    public ArrayList<LinkedList<String>> getMeanings() {
        return meanings;
    }

    public StringBuilder getMeaningTextContent() {
        return meaningTextContent;
    }
}
