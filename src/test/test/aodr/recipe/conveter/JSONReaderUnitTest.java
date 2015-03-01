package test.aodr.recipe.conveter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import ador.recipe.converter.JSONReader;
import ador.recipe.domain.Ingredient;
import ador.recipe.domain.Recipe;
import ador.recipe.domain.Unit;
import ador.recipe.util.Comparator;

import com.google.gson.reflect.TypeToken;

public class JSONReaderUnitTest extends TestCase {

	String requestFileName = "MoGroupAddRequest";
	
	public void testProcessorSuccess(){
		String fileName = "Recipe.txt";
		//String objectType = "java.util.List";
		//String objectType = "pri.ador.toy.Recipe";
		Type listType = new TypeToken<ArrayList<ador.recipe.domain.Recipe>>(){}.getType();
		JSONReader jr = new JSONReader();
		List<Object> r = jr.convertRecipeListJsonToObjectList(fileName, listType);
		System.out.println(r);
		//assertEquals(null, responseStatus);
		//assertEquals(null, errorCode);
		
		//Expected Object
		Ingredient ingredient1 = new Ingredient("bread", 2.0, Unit.slices, null);
		Ingredient ingredient2 = new Ingredient("cheese", 2.0, Unit.slices, null);
		Ingredient[] ingredients = {ingredient1,ingredient2};
		Recipe r1 = new Recipe("grilled cheese on toast", ingredients);
		
		Recipe r2 = (Recipe) r.get(0);
		
		List<Recipe> compareObjects = new ArrayList<Recipe>();
		compareObjects.add(r1);
		compareObjects.add(r2);


		assertEquals(r1.getName(),r2.getName());
		assertTrue(Comparator.compareArray(r1.getIngredients(), r2.getIngredients()));
	}
	
	
}
