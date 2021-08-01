package stg.challenges;

import com.opencsv.CSVIterator;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Challenge10 {

    @DataProvider(name = "test")
    public Iterator<Object[]> provider(){
        List<Object[]> testCases = new ArrayList<>();
        String[] data;
        try {
            CSVIterator iterator = new CSVIterator(new CSVReader
                    (new FileReader("src/test/resources/cars.csv")));
            while (iterator.hasNext()){  // While there are lines in the .csv
                data = iterator.next();  // add a line that was read to the array "data"
                testCases.add(data);     // To List of Object[] add an array as an element
            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
        return testCases.iterator();  // Return the Iterator of Object[]
    }

    @Test(dataProvider = "test")
    public void testWithDataProvider(String arg1, String arg2, String arg3, String arg4 ){
        System.out.println(arg1 + " " + arg2 + " " + arg3 + " " + arg4);
    }

    // Thoughts: Using JUnit 5 this should be much simpler using @ParametrizedTest and @CsvFileSource
}
