package stg.challenges;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CSVReadingExample{

    public static List<List<String>> parseCSV() {
        List<List<String>> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader("src/test/resources/cars.csv"))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return records;
    }

    @Test(testName = "CSV parsing Test")
    public void csvTest() {
        for(List<String> list : parseCSV()) {
            System.out.println(list);
        }
    }
}
