package ce;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

public class GUIActionsTest {

    @Test
    public void buildTextBodyTest(){

        GUI_Actions guiActions = new GUI_Actions();

        guiActions.buildTextBody("",new ArrayList<>());
        assertEquals("",String.valueOf(guiActions.buildText));
    }

    @Test
    public void searchThreadsTest(){

        GUI_Actions guiActions = new GUI_Actions();

        guiActions.searchThreads("a","eng",new ArrayList<>(),false);
        assertEquals(0,guiActions.getXmlMethodsArrayList().size());

        GUI_Actions guiActions1 = new GUI_Actions();

        guiActions1.searchThreads("a","eng",new ArrayList<>(),true);
        assertEquals(0,guiActions1.getXmlMethodsArrayList().size());

        GUI_Actions guiActions2 = new GUI_Actions();

        ArrayList<String> paths = new ArrayList<>();
        paths.add("GraphFiles\\eng-tur.txt");
        paths.add("GraphFiles\\eng-fra.txt");
        paths.add("GraphFiles\\eng-swe.txt");

        guiActions2.searchThreads("car","eng",paths,false);
        assertEquals(3,guiActions2.getXmlMethodsArrayList().size());

        GUI_Actions guiActions3 = new GUI_Actions();

        ArrayList<String> paths1 = new ArrayList<>();
        paths.add("GraphFiles\\eng-tur.txt");
        paths.add("GraphFiles\\eng-fra.txt");
        paths.add("GraphFiles\\eng-swe.txt");

        guiActions3.searchThreads("car","eng",paths1,true);
        assertEquals(0,guiActions3.getXmlMethodsArrayList().size());
    }
}
