package ador.recipe.main;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import ador.recipe.converter.JSONReader;
import ador.recipe.domain.Ingredient;
import ador.recipe.domain.Recipe;
import ador.recipe.service.FridgeService;

public class RecipeFinder {

	public static void main(String[] args) {
		
		String fridgeFileName = "Fridge.csv";
		String recipeFileName = "Recipe.txt";

		if(args.length>=2 && args[0]!=null && args[1]!=null){
			fridgeFileName = args[0];
			recipeFileName = args[1];
		}
		
		FridgeService fridgeService = new FridgeService();
		fridgeService.loadIngredientByCSV(fridgeFileName);
		
		Type listType = new TypeToken<ArrayList<ador.recipe.domain.Recipe>>(){}.getType();
		JSONReader jr = new JSONReader();
		List<Object> recipeList = jr.convertRecipeListJsonToObjectList(recipeFileName, listType);
		
		String outputMsg = "Order Takeout";
		long targetRecipeSelfLife = -1;
		for(Object o : recipeList){
			Recipe recipe = (Recipe) o;
			List<Ingredient> ingredientList = Arrays.asList(recipe.getIngredients());
			long recipeSelfLife = fridgeService.shortestSelfLifeOfIngredientList(ingredientList);
			if(recipeSelfLife!=-1 && (recipeSelfLife<targetRecipeSelfLife || targetRecipeSelfLife==-1)){
				targetRecipeSelfLife = recipeSelfLife;
				outputMsg = recipe.getName();
			}
		}
		System.out.println(outputMsg);

	}

}
