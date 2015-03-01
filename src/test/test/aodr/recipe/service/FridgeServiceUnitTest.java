package test.aodr.recipe.service;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import junit.framework.TestCase;
import ador.recipe.converter.JSONReader;
import ador.recipe.domain.Ingredient;
import ador.recipe.domain.Recipe;
import ador.recipe.domain.Unit;
import ador.recipe.service.FridgeService;
import ador.recipe.util.RecipeFinderConstant;

public class FridgeServiceUnitTest extends TestCase {

	FridgeService fridgeService;
	List<Object> recipeList;

	protected void setUp(){
		String fridgeFileName = "FridgeForUnitTest.csv";
		fridgeService = new FridgeService();
		fridgeService.loadIngredientByCSV(fridgeFileName);
		
		String recipeFileName = "RecipeForFridgeUnitTest.txt";
		Type listType = new TypeToken<ArrayList<ador.recipe.domain.Recipe>>(){}.getType();
		JSONReader jr = new JSONReader();
		recipeList = jr.convertRecipeListJsonToObjectList(recipeFileName, listType);
	}
	
	public void testIsIngredientAvailable() throws ParseException{
	
		Ingredient ingredientAvaliable = ((Recipe)recipeList.get(3)).getIngredients()[0];
		Ingredient ingredientNotEnough = ((Recipe)recipeList.get(3)).getIngredients()[1];
		Ingredient ingredientPassUsedBy = ((Recipe)recipeList.get(3)).getIngredients()[2];
		
		assertTrue(fridgeService.isIngredientAvailable(ingredientAvaliable));
		assertFalse(fridgeService.isIngredientAvailable(ingredientNotEnough));
		assertFalse(fridgeService.isIngredientAvailable(ingredientPassUsedBy));
	}
	
	public void testIsAllIngredientAvailable() throws ParseException{
		
		List<Ingredient> ingredientListAllAvailable = Arrays.asList(((Recipe)recipeList.get(0)).getIngredients());
		assertTrue(fridgeService.isAllIngredientAvailable(ingredientListAllAvailable));
		
		List<Ingredient> ingredientListSomeNotEnough = Arrays.asList(((Recipe)recipeList.get(1)).getIngredients());
		assertFalse(fridgeService.isAllIngredientAvailable(ingredientListSomeNotEnough));

		List<Ingredient> ingredientListSomeExpired = Arrays.asList(((Recipe)recipeList.get(2)).getIngredients());
		assertFalse(fridgeService.isAllIngredientAvailable(ingredientListSomeExpired));
		
		List<Ingredient> ingredientListSomeNotEnoughSomeExpired = Arrays.asList(((Recipe)recipeList.get(3)).getIngredients());
		assertFalse(fridgeService.isAllIngredientAvailable(ingredientListSomeNotEnoughSomeExpired));
	}
	
	public void testIsAllIngredientAvailable() throws ParseException{
		
		List<Ingredient> ingredientListAllAvailable = Arrays.asList(((Recipe)recipeList.get(0)).getIngredients());
		assertTrue(fridgeService.isAllIngredientAvailable(ingredientListAllAvailable));
		
		List<Ingredient> ingredientListSomeNotEnough = Arrays.asList(((Recipe)recipeList.get(1)).getIngredients());
		assertFalse(fridgeService.isAllIngredientAvailable(ingredientListSomeNotEnough));

		List<Ingredient> ingredientListSomeExpired = Arrays.asList(((Recipe)recipeList.get(2)).getIngredients());
		assertFalse(fridgeService.isAllIngredientAvailable(ingredientListSomeExpired));
		
		List<Ingredient> ingredientListSomeNotEnoughSomeExpired = Arrays.asList(((Recipe)recipeList.get(3)).getIngredients());
		assertFalse(fridgeService.isAllIngredientAvailable(ingredientListSomeNotEnoughSomeExpired));
	}
	
}
