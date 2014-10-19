package dk.mrspring.kitchen.block.container;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by MrSpring on 12-10-2014 for TheKitchenMod.
 */
public class IngredientStack
{
	Ingredient ingredient;
	int stackSize;

	public IngredientStack(Ingredient ingredient, int stackSize)
	{
		this.ingredient = ingredient;
		this.stackSize = stackSize;
	}

	public IngredientStack(Ingredient ingredient)
	{
		this(ingredient, 1);
	}

	public void setIngredient(Ingredient ingredient)
	{
		this.ingredient = ingredient;
	}

	public void setStackSize(int stackSize)
	{
		this.stackSize = stackSize;
	}

	public Ingredient getIngredient()
	{
		return ingredient;
	}

	public int getStackSize()
	{
		return stackSize;
	}

	public NBTTagCompound toTagCompound()
	{
		NBTTagCompound compound = new NBTTagCompound();
		compound.setString("Ingredient",this.getIngredient().name());
		compound.setInteger("Amount",this.getStackSize());
		return compound;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Ingredient)
			return this.getIngredient().equals(obj);
		else return super.equals(obj);
	}
}
