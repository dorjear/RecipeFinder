package ador.recipe.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ador.recipe.converter.CSVReader;
import ador.recipe.domain.Ingredient;

public class FridgeService {
	private List<Ingredient> stock;
	
	public void loadIngredientByCSV(String fileName){
		CSVReader csvReader = new CSVReader();
		stock = csvReader.readCSVFileToObject(fileName, IngredientUtil.getCellProcessorOfIngredient(), Ingredient.class);
	}

	public boolean isIngredientAvailable(Ingredient ingredient) {
		Date today = new Date();
	    for(Ingredient i : stock){
	    	if(ingredient.getItem().equals(i.getItem()) && ingredient.getAmount()<=i.getAmount() && today.getTime()<i.getUseBy().getTime()){
	    		return true;
	    	}
	    }
	    return false;
	}

	public boolean isAllIngredientAvailable(List<Ingredient> ingredients) {
	    for(Ingredient i : ingredients){
	    	if(!isIngredientAvailable(i)){
	    		return false;
	    	}
	    }
	    return true;
	}

	public long shortestSelfLifeOfIngredient(Ingredient ingredient) {
		long result = -1;
		List<Long> selfLifeList = new ArrayList<Long>();
		Date today = new Date();
	    for(Ingredient i : stock){
	    	if(ingredient.getItem().equals(i.getItem()) && ingredient.getAmount()<=i.getAmount() && today.getTime()<i.getUseBy().getTime()){
		    	Long selfLife = i.getUseBy().getTime() - today.getTime();
	    		selfLifeList.add(selfLife);
	    	}
	    }
	    if(selfLifeList.size()>0){
	    	result = Collections.min(selfLifeList);
	    }
    	return result;
	}

	public long shortestSelfLifeOfIngredientList(List<Ingredient> ingredientList) {
		
		List<Long> selfLifeList = new ArrayList<Long>();
	    for(Ingredient i : ingredientList){
	    	Long selfLife = shortestSelfLifeOfIngredient(i);
	    	selfLifeList.add(selfLife);
	    }
    	return Collections.min(selfLifeList);
	}
}
