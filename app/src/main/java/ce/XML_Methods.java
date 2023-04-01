package ce;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class XML_Methods implements Runnable {

    private final ArrayList<Node> foundedEntries = new ArrayList<>();


    public void searchMeaning(){
        for (Node foundedEntry : foundedEntries) {

            Element entry = (Element) foundedEntry;

            // Some XML files have more than one sense tag, so if he encounters such a situation, so that there are no problems.
            NodeList senses = entry.getElementsByTagName("sense");

            for (int j = 0; j < senses.getLength(); j++) {

                Element currentSense = (Element) senses.item(j);

                NodeList cites = currentSense.getElementsByTagName("cit");

                int meaningIndex = 1;
                System.out.print("Meaning " +(j+1) +": ");
                for (int k = 0; k < cites.getLength(); k++) {
                    Element citeT = (Element) cites.item(k);
                    if (citeT.getAttribute("type").equals("trans")) {
                        String quote = citeT.getElementsByTagName("quote").item(0).getTextContent();
                        if (k+1!= cites.getLength()){
                            System.out.print(quote + ", ");
                        }
                        else{
                            System.out.print(quote);
                        }
                        meaningIndex++;
                    }
                }
                System.out.println();
            }
        }
    }

    public void findWord(String word) throws ParserConfigurationException, IOException, SAXException {

        File file = new File("eng-tur.xml");
        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = fac.newDocumentBuilder();
        Document doc = builder.parse(file);
        doc.getDocumentElement().normalize();
        NodeList nodeL = doc.getElementsByTagName("entry");
        for (int i=0;i<nodeL.getLength();i++){
            Element element = (Element) nodeL.item(i);
            if (element.getElementsByTagName("orth").item(0).getTextContent().equals(word)){
                System.out.println("Word found!");

                // It saves the found entry so as not to search the file again later.
                foundedEntries.add(nodeL.item(i));
            }
        }
    }

    protected void search(String filepath){

        File file = new File(filepath);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);


        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {

    }
}
