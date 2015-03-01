package test.aodr.recipe.service;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import ador.recipe.converter.JSONReader;
import ador.recipe.domain.Ingredient;
import ador.recipe.domain.Recipe;
import ador.recipe.service.FridgeService;

import com.google.gson.reflect.TypeToken;

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

	public void testShortestSelfLifeOfIngredient() throws ParseException{
		
		Ingredient ingredientAvaliable = ((Recipe)recipeList.get(3)).getIngredients()[0];
		Ingredient ingredientNotEnough = ((Recipe)recipeList.get(3)).getIngredients()[1];
		Ingredient ingredientPassUsedBy = ((Recipe)recipeList.get(3)).getIngredients()[2];
		
		Date today = new Date();
		Long expectedSelfLife = fridgeService.getStock().get(0).getUseBy().getTime() - today.getTime();
		Long selfLifeOfIngredientAvaliable = fridgeService.shortestSelfLifeOfIngredient(ingredientAvaliable);
		assertEquals(expectedSelfLife, selfLifeOfIngredientAvaliable);
		assertEquals(-1, fridgeService.shortestSelfLifeOfIngredient(ingredientNotEnough));
		assertEquals(-1, fridgeService.shortestSelfLifeOfIngredient(ingredientPassUsedBy));
		
	}
	
	public void testShortestSelfLifeOfIngredients() throws ParseException{
		
		List<Ingredient> ingredientListAvaliable = Arrays.asList(((Recipe)recipeList.get(0)).getIngredients());
		List<Ingredient> ingredientListSomeNotEnough = Arrays.asList(((Recipe)recipeList.get(1)).getIngredients());
		List<Ingredient> ingredientListSomeExpired = Arrays.asList(((Recipe)recipeList.get(2)).getIngredients());
		List<Ingredient> ingredientListSomeNotEnoughAndSomeExpired = Arrays.asList(((Recipe)recipeList.get(3)).getIngredients());
		
		Date today = new Date();
		Long expectedSelfLife = fridgeService.getStock().get(3).getUseBy().getTime() - today.getTime();
		Long selfLifeOfIngredientAvaliable = fridgeService.shortestSelfLifeOfIngredientList(ingredientListAvaliable);
		assertEquals(expectedSelfLife, selfLifeOfIngredientAvaliable);
		assertEquals(-1, fridgeService.shortestSelfLifeOfIngredientList(ingredientListSomeNotEnough));
		assertEquals(-1, fridgeService.shortestSelfLifeOfIngredientList(ingredientListSomeExpired));
		assertEquals(-1, fridgeService.shortestSelfLifeOfIngredientList(ingredientListSomeNotEnoughAndSomeExpired));
		
	}	
}
