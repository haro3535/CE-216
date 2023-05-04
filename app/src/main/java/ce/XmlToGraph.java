package ce;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.ArrayList;

public class XmlToGraph {

    public void transformXmlToGraph(String filepath){

        String pathWithoutExtension = filepath.split("\\.")[0];
        String nameOfFile = pathWithoutExtension.split("\\\\")[1];

        File graphFile = new File("GraphFiles/"+nameOfFile+".txt");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();

        try {

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filepath));

            document.normalize();

            NodeList entries = document.getElementsByTagName("entry");

            for (int i = 0; i < entries.getLength(); i++) {

                Element entryElement = (Element) entries.item(i);

                NodeList quotes = entryElement.getElementsByTagName("quote");
                NodeList defs = entryElement.getElementsByTagName("def");

                ArrayList<String> meanings = new ArrayList<>();
                for (int j = 0; j < quotes.getLength(); j++) {
                    meanings.add(quotes.item(j).getTextContent());
                }
                if (quotes.getLength() == 0) {
                    for (int j = 0; j < defs.getLength(); j++) {
                        meanings.add(defs.item(j).getTextContent());
                    }
                }

                NodeList refs = entryElement.getElementsByTagName("ref");

                ArrayList<String> synonyms = new ArrayList<>();
                for (int j = 0; j < refs.getLength(); j++) {
                    synonyms.add(refs.item(j).getTextContent());
                }

                GraphElement graphElement = new GraphElement(entryElement.getElementsByTagName("orth").item(0).getTextContent(),meanings,synonyms);
                saveToFile(graphElement,graphFile);

            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public void transformXmlToGraphUsingStAX(String filepath){

        String pathWithoutExtension = filepath.split("\\.")[0];
        String nameOfFile = pathWithoutExtension.split("\\\\")[1];

        File graphFile = new File("GraphFiles/"+nameOfFile+".txt");

        XMLInputFactory xmlInputFactory = XMLInputFactory.newDefaultFactory();

        try {

            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(filepath));

            String word = "";
            ArrayList<String> meanings = new ArrayList<>();
            ArrayList<String> synonyms = new ArrayList<>();


            boolean isQuoteExist = false;
            boolean isOrthValue = false;
            boolean isTranslateValue = false;
            boolean isSynonymValue = false;
            boolean isCitTrans = false;

            while (xmlEventReader.hasNext()){

                XMLEvent nextEvent = xmlEventReader.nextEvent();

                if (nextEvent.isStartElement()) {

                    StartElement startElement = nextEvent.asStartElement();

                    switch (startElement.getName().getLocalPart()) {
                        case "orth" -> {
                            isOrthValue = true;
                        }
                        case "cit" -> {
                            if (startElement.getAttributeByName(QName.valueOf("type")).getValue().equals("trans")) {
                                isCitTrans = true;
                            }
                        }
                        case "quote" -> {
                            isQuoteExist = true;
                            isTranslateValue = true;
                        }
                        case "def" -> {
                            if (!isQuoteExist) {
                                isTranslateValue = true;
                            }
                        }
                        case "ref" -> {
                            isSynonymValue = true;
                        }
                        default -> {
                            continue;
                        }
                    }

                }

                if (nextEvent.isCharacters()) {
                    if (isOrthValue) {
                        word = nextEvent.asCharacters().getData();
                    }

                    if (isTranslateValue && isQuoteExist && isCitTrans) {
                        // eğer quote tagı varsa gördüğüm kadarıyla def tagı çevirme değil tanım olarak kullanılıyor
                        meanings.add(nextEvent.asCharacters().getData());
                    }
                    if (isTranslateValue && !isQuoteExist) {
                        // sadece def tagı olanlar
                        meanings.add(nextEvent.asCharacters().getData());
                    }

                    if (isSynonymValue) {
                        synonyms.add(nextEvent.asCharacters().getData());
                    }
                }

                if (nextEvent.isEndElement()) {

                    EndElement endElement = nextEvent.asEndElement();

                    switch (endElement.getName().getLocalPart()) {
                        case "orth" -> {
                            isOrthValue = false;
                        }
                        case "cit" -> {
                            isCitTrans = false;
                        }
                        case "quote" -> {
                            isTranslateValue = false;
                        }
                        case "def" -> {
                            if (!isQuoteExist) {
                                isTranslateValue = false;
                            }
                        }
                        case "ref" -> {
                            isSynonymValue = false;
                        }
                        case "entry" -> {
                            if (word != null) {
                                GraphElement graphElement = new GraphElement(word,meanings,synonyms);
                                saveToFile(graphElement,graphFile);
                            }
                            word = "";
                            meanings.clear();
                            synonyms.clear();
                        }
                        default -> {}
                    }
                }
            }

        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void saveToFile(GraphElement graphElement, File graphFile){

        try {

            FileWriter fileWriter = new FileWriter(graphFile,true);

            fileWriter.write(graphElement.getWord() + ";");

            for (int i = 0; i < graphElement.getMeanings().size(); i++) {
                fileWriter.write(graphElement.getMeanings().get(i));
                if (graphElement.getMeanings().size() != (i+1)) {
                    fileWriter.write("&");
                }
            }
            fileWriter.write(";");

            for (int i = 0; i < graphElement.getSynonyms().size(); i++) {
                fileWriter.write(graphElement.getSynonyms().get(i));
                if (graphElement.getSynonyms().size() != (i+1)) {
                    fileWriter.write("&");
                }
            }
            fileWriter.write(";");
            fileWriter.write("\n");
            fileWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
