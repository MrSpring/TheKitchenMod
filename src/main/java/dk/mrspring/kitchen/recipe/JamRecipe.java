package dk.mrspring.kitchen.recipe;

import dk.mrspring.kitchen.block.container.Ingredient;
import dk.mrspring.kitchen.block.container.IngredientStack;
import dk.mrspring.kitchen.pot.Jam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrSpring on 12-10-2014 for TheKitchenMod.
 */
public class JamRecipe
{
	final List<IngredientStack> requiredIngredients;
	final Jam result;
	final int resultAmount;

	public JamRecipe(Jam result, int resultAmount, Object... requirements)
	{
		List<IngredientStack> list = new ArrayList<IngredientStack>();

		for (Object ingredient : requirements)
		{
			if (ingredient instanceof Ingredient)
				list.add(new IngredientStack((Ingredient) ingredient));
			else if (ingredient instanceof IngredientStack)
				list.add((IngredientStack) ingredient);
		}

		this.requiredIngredients = list;
		this.result = result;
		this.resultAmount=resultAmount;
	}

	public Jam getResult()
	{
		return result;
	}

	public int getResultAmount()
	{
		return resultAmount;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof ArrayList)
		{
			List list = (ArrayList) obj;
			if (!list.isEmpty())
			{
				if (list.containsAll(this.requiredIngredients) && this.requiredIngredients.containsAll(list))
					return true;
			}
			return false;
		} else return super.equals(obj);
	}
}
