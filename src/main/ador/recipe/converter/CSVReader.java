package ador.recipe.converter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

public class CSVReader {

	public <T> List<T> readCSVFileToObject(String csvFileName, CellProcessor[] processors, Class<T> clazz) {
	    ICsvBeanReader beanReader = null;
        List<T> objectList = new ArrayList<T>();
	    try {
	        beanReader = new CsvBeanReader(new FileReader(csvFileName),
	                CsvPreference.STANDARD_PREFERENCE);
	        String[] header = beanReader.getHeader(true);
	        T bean = null;
	        while ((bean = beanReader.read(clazz, header, processors)) != null) {
	        	objectList.add(bean);
	        }
	    } catch (FileNotFoundException ex) {
	        System.err.println("Could not find the CSV file: " + ex);
	    } catch (IOException ex) {
	        System.err.println("Error reading the CSV file: " + ex);
	    } finally {
	        if (beanReader != null) {
	            try {
	                beanReader.close();
	            } catch (IOException ex) {
	                System.err.println("Error closing the reader: " + ex);
	            }
	        }
	    }
        return objectList;
	}
}
