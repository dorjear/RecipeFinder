package ador.recipe.domain;


public class Recipe {
	String name;
	Ingredient[] ingredients;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Ingredient[] getIngredients() {
		return ingredients;
	}
	public void setIngredients(Ingredient[] ingredients) {
		this.ingredients = ingredients;
	}
	public Recipe(String name, Ingredient[] ingredients) {
		super();
		this.name = name;
		this.ingredients = ingredients;
	}

}
