package ce;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class FunctionsTest {

    @Test
    public void parseMeaningTest(){

        Functions nFunction = new Functions();
        ArrayList<String> testArray = new ArrayList<>();

        nFunction.parseMeanings(testArray);
        assertEquals(0,nFunction.getMeanings().size());

        // I found a bug here and debugged it
        Functions functions1 = new Functions();
        // if data has not any ';'
        String data = "harun onur bu&bir&denemedir";
        ArrayList<String> testArray1 = new ArrayList<>();
        testArray1.add(data);

        functions1.parseMeanings(testArray1);
        assertEquals(0,functions1.getMeanings().size());

        Functions functions2 = new Functions();
        // if data has not any '&' at the meaning part
        String data1 = "harun;onur bu bir denemedir;bunlar da synonym;";
        ArrayList<String> testArray2 = new ArrayList<>();
        testArray2.add(data1);

        functions2.parseMeanings(testArray2);
        assertEquals(1,functions2.getMeanings().size());
        assertEquals(1,functions2.getMeanings().get(0).size());

        // I found a bug here and debugged it
        Functions functions3 = new Functions();

        functions3.parseMeanings(null);
        assertEquals(0,functions3.getMeanings().size());

    }

    @Test
    public void searchWordTest(){

        Functions functions1 = new Functions();
        functions1.setFilepath("eng-tur.txt");
        functions1.setWord(null);

        assertEquals(0,functions1.getMeanings().size());

    }
}
