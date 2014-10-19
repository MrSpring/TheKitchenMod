package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.block.container.Ingredient;
import dk.mrspring.kitchen.block.container.IngredientStack;
import dk.mrspring.kitchen.pot.Jam;
import dk.mrspring.kitchen.pot.Soup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrSpring on 09-10-2014 for TheKitchenMod.
 */
public class TileEntityCookingPot extends TileEntity
{
	List<IngredientStack> ingredients = new ArrayList<IngredientStack>();
	int boilTime = 0;
	boolean isOpen = true;
	boolean isFinished = false;
	boolean isSoup = false;

	int finishedAmount = 0;
	Jam finishedJam = Jam.EMPTY;
	Soup finishedSoup;

	public boolean addIngredient(ItemStack toAdd)
	{
		Ingredient ingredientForItem = Ingredient.getMatchingIngredient(toAdd);
		if (ingredientForItem != null)
		{
			if (this.ingredients.contains(ingredientForItem))
			{
				int index = this.ingredients.indexOf(ingredientForItem);
				IngredientStack stack = this.ingredients.remove(index);
				stack.setStackSize(stack.getStackSize() + 1);
				this.ingredients.add(index, stack);
			} else
				this.ingredients.add(new IngredientStack(ingredientForItem));
			return true;
		} else return false;
	}

	public List<IngredientStack> getIngredients()
	{
		return ingredients;
	}

	public void toggleLid()
	{
		this.isOpen = !this.isOpen();
	}

	public boolean isOpen()
	{
		return isOpen;
	}

	@Override
	public void updateEntity()
	{
		if (!this.isOpen() && !this.isFinished)
		{
			boilTime++;
			if (boilTime > 200)
			{
				this.finishContents();
				boilTime = 200;
			}
		} else if (this.boilTime != 0) this.boilTime = 0;
	}

	public void finishContents()
	{
		if (this.getIngredients().contains(Ingredient.WATER))
			this.finishSoup();
		else this.finishJam();
	}

	public void finishSoup()
	{
		this.ingredients.clear();
	}

	public void finishJam()
	{
		this.ingredients.clear();
		this.isFinished = true;

	}
}
