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

public class XML_Methods implements Runnable {


    public static void SearchMeaning(String word) throws ParserConfigurationException, IOException, SAXException {
        File file = new File("eng-tur.xml");
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
                    if(nodeL2.item(j).getNodeName()=="sense"){
                        System.out.println("Meaning " + (j/2) + ": " + nodeL2.item(j).getChildNodes().item(1).getChildNodes().item(1).getTextContent());
                    }
                }
            }
        }
    }

    public static void FindWord(String word) throws ParserConfigurationException, IOException, SAXException {

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
                System.out.println(word);
                System.out.println(element.getElementsByTagName("orth").item(0).getTextContent());
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
