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


    public void searchMeaning(String word){
        /*File file = new File("eng-tur.xml");
        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = fac.newDocumentBuilder();
        Document doc = builder.parse(file);
        doc.getDocumentElement().normalize();
        NodeList nodeL = doc.getElementsByTagName("entry");
        for (int i=0;i<nodeL.getLength();i++){
                Element element = (Element) nodeL.item(i);
                if (element.getElementsByTagName("orth").item(0).getTextContent().equals(word)){
                    Node node = element.getElementsByTagName("orth").item(0);
                    NodeList nodeL2 = node.getParentNode().getParentNode().getChildNodes();
                    for (int j=3;j<nodeL2.getLength();j++){
                        if(Objects.equals(nodeL2.item(j).getNodeName(), "sense")){
                            System.out.println("Meaning " + (j/2) + ": " + nodeL2.item(j).getChildNodes().item(1).getChildNodes().item(1).getTextContent());
                        }
                    }
                }
            }
         */

        for (Node foundedEntry : foundedEntries) {

            Element entry = (Element) foundedEntry;

            // Bazı XML dosyaları birden fazla sense tagına sahip o yüzden eğer böyle bir durumla karşılaşırsa sıkıntı çıkmasın diye
            NodeList senses = entry.getElementsByTagName("sense");

            for (int j = 0; j < senses.getLength(); j++) {

                Element currentSense = (Element) senses.item(j);

                NodeList cites = currentSense.getElementsByTagName("cit");

                for (int k = 0; k < cites.getLength(); k++) {
                    Element citeT = (Element) cites.item(k);

                    int meaningIndex = 1;
                    if (citeT.getAttribute("type").equals("trans")) {
                        String quote = citeT.getElementsByTagName("quote").item(0).getTextContent();
                        System.out.println("Meaning " + (meaningIndex) + ": " + quote);
                    }
                }
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

                // Sonradan tekrar dosyayı aratmamak için bulunan entry i kayıt ediyor
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
