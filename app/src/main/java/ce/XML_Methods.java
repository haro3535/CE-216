package ce;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class XML_Methods implements Runnable {

    private Node foundEntries;
    private final ArrayList<LinkedList<String>> meanings = new ArrayList<>();
    private String filepath;
    private String word;

    public void arrangeEntriesTextContents(){

        // To arrange each entry that we found in xml file.
        if (foundEntries != null) {
            NodeList entries = foundEntries.getChildNodes();

            for (int i = 0; i < entries.getLength(); i++) {
                Element entry = (Element) entries.item(i);


                // Some XML files have more than one <sense> tag, so if he encounters such a situation, so that there are no problems.
                NodeList senses = entry.getElementsByTagName("sense");


                // Eş sesli kelimeler için
                meanings.add(new LinkedList<>());

                for (int j = 0; j < senses.getLength(); j++) {
                    // Some XML dictionaries also have multiple <cit> tags or <def> tag inside the <sense> tag.
                    // For that reason, we take them all and
                    Element currentSense = (Element) senses.item(j);

                    NodeList senseChildNodes = currentSense.getChildNodes();


                    for (int k = 0; k < senseChildNodes.getLength(); k++) {
                        Element child = (Element) senseChildNodes.item(k);
                        if (child.getTagName().equals("cit") && child.getAttribute("type").equals("trans")) {
                            meanings.get(meanings.size()-1).add(child.getElementsByTagName("quote").item(0).getTextContent());
                        }
                        else if (child.getTagName().equals("def")) {
                            meanings.get(meanings.size()-1).add(child.getTextContent());
                        }
                    }
                }
            }
        }


             /*


            String[] mainTags = {"form","gramGrp","sense"};



            NodeList children = entry.getChildNodes();

            for (int i = 0; i < children.getLength(); i++) {
                Element child = (Element) children.item(i);

                switch (child.getTagName()){
                    case "form":
                        Element form = (Element) child.getElementsByTagName("form").item(0);

                        Element orth = (Element) form.getElementsByTagName("orth").item(0);




                    case "gramGrp":
                    case "sense":
                }
            }

             */
    }

    public void findWord() {

        File file = new File(filepath);

        // These three lines of code below for parsing the xml file.
        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();


        try {
            DocumentBuilder builder = fac.newDocumentBuilder();
            Document doc = builder.parse(file);

            // It is not necessary but recommended for parsing
            doc.getDocumentElement().normalize();

            NodeList nodeL = doc.getElementsByTagName("entry");
            for (int i=0;i<nodeL.getLength();i++){
                Element element = (Element) nodeL.item(i);
                if (element.getElementsByTagName("orth").item(0).getTextContent().equals(word)){
                    System.out.println("Word found!");

                    // It saves the found entry so as not to search the file again later.
                    //foundEntries.add(nodeL.item(i));
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
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

            Node rootNode = doc.createElement("entry");

            ArrayList<String> tagQueue = new ArrayList<>();
            String currentTextValue = "";

            boolean isInsideTheEntry = false;
            boolean isItCorrectEntry = true;
            while (xmlEventReader.hasNext()){

                XMLEvent nextEvent = xmlEventReader.nextEvent();

                if (nextEvent.isStartElement() && isItCorrectEntry) {

                    StartElement startElement = nextEvent.asStartElement();


                    if (startElement.getName().getLocalPart().equals("entry") && !isInsideTheEntry) {
                        isInsideTheEntry = true;
                        continue;
                    }else if (isInsideTheEntry){
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
                                System.out.println(attribute.getName().getLocalPart() + " - " + attribute.getValue());
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
                                rootNode = null;
                            }
                        }
                    }
                }

                if (nextEvent.isEndElement()) {
                    EndElement endElement = nextEvent.asEndElement();

                    if (endElement.getName().getLocalPart().equals("entry")) {
                        if (rootNode != null) {
                            setFoundEntries(rootNode);
                        }else {
                            tagQueue.clear();
                            rootNode = doc.createElement("entry");
                        }
                        isItCorrectEntry = true;
                        continue;
                    }

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



        } catch (FileNotFoundException | XMLStreamException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        findEntries();
        arrangeEntriesTextContents();
        //searchMeaning();
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

    public Node getFoundEntries() {
        return foundEntries;
    }

    public void setFoundEntries(Node foundEntries) {
        this.foundEntries = foundEntries;
    }

    public ArrayList<LinkedList<String>> getMeanings() {
        return meanings;
    }
}
