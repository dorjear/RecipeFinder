package ador.recipe.service;

import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class IngredientUtil {

	public static CellProcessor[] getCellProcessorOfIngredient() {
	    CellProcessor[] ingredientProcessors = new CellProcessor[] {
	            new NotNull(), // item
	            new ParseDouble(), // amount
	            new NotNull(), // unit
	            new ParseDate("dd/MM/yyyy"), // useBy
	    };
		return ingredientProcessors;
	}

}
