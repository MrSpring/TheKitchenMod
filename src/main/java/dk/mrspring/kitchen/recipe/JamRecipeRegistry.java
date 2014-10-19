package dk.mrspring.kitchen.recipe;

import dk.mrspring.kitchen.block.container.IngredientStack;
import dk.mrspring.kitchen.pot.Jam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrSpring on 12-10-2014 for TheKitchenMod.
 */
public class JamRecipeRegistry
{
	private static List<JamRecipe> recipeList = new ArrayList<JamRecipe>();

	public static void registerRecipe(Jam output, int amount, Object... requirements)
	{
		JamRecipe recipe = new JamRecipe(output, amount, requirements);
		registerRecipe(recipe);
	}

	public static void registerRecipe(JamRecipe recipe)
	{
		if (recipe != null)
			recipeList.add(recipe);
	}

	/***
	 * @param ingredients The list of IngredientStacks to match recipes with.
	 * @return Returns the result of the IngredientStacks, EMPTY if no result was found.
	 */
	public static JamRecipe getResult(ArrayList<IngredientStack> ingredients)
	{
		for (JamRecipe recipe : recipeList)
		{
			if (recipe.equals(ingredients))
				return recipe;
		} return null;
	}
}
