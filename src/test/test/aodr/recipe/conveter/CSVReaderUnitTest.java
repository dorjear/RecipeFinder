package test.aodr.recipe.conveter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.supercsv.cellprocessor.ift.CellProcessor;

import ador.recipe.converter.CSVReader;
import ador.recipe.domain.Ingredient;
import ador.recipe.domain.Unit;
import ador.recipe.service.IngredientUtil;
import ador.recipe.util.Comparator;
import ador.recipe.util.RecipeFinderConstant;

public class CSVReaderUnitTest extends TestCase {

	public void testProcessorSuccess() throws ParseException{
		String fileName = "Fridge.csv";
		CSVReader cvsReader = new CSVReader();

	    CellProcessor[] ingredientProcessors = IngredientUtil.getCellProcessorOfIngredient();

		List<Ingredient> ingredientList = cvsReader.readCSVFileToObject(fileName, ingredientProcessors, Ingredient.class);
		for (Ingredient bookBean:ingredientList){
			System.out.printf("%s %-10s %-10s %tD",
                    bookBean.getItem(), bookBean.getAmount(),
                    bookBean.getUnit(), bookBean.getUseBy());
            System.out.println();
		}

		List<Ingredient> ingredientListExpected = new ArrayList<Ingredient>();
		ingredientListExpected.add(new Ingredient("bread", 10.0, Unit.slices, new SimpleDateFormat(RecipeFinderConstant.AU_DATE_FORMAT).parse("25/12/2014")));
		ingredientListExpected.add(new Ingredient("cheese", 10.0, Unit.slices, new SimpleDateFormat(RecipeFinderConstant.AU_DATE_FORMAT).parse("25/12/2014")));
		ingredientListExpected.add(new Ingredient("butter", 250.0, Unit.grams, new SimpleDateFormat(RecipeFinderConstant.AU_DATE_FORMAT).parse("25/12/2014")));
		ingredientListExpected.add(new Ingredient("peanut butter", 250.0, Unit.grams, new SimpleDateFormat(RecipeFinderConstant.AU_DATE_FORMAT).parse("02/12/2014")));
		ingredientListExpected.add(new Ingredient("mixed salad", 150.0, Unit.grams, new SimpleDateFormat(RecipeFinderConstant.AU_DATE_FORMAT).parse("26/12/2013")));

		assertTrue(Comparator.compareArray(ingredientList.toArray(), ingredientListExpected.toArray()));
	}
	
	
}
