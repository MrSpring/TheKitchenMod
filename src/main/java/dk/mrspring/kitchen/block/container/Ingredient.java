package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.KitchenItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by MrSpring on 09-10-2014 for TheKitchenMod.
 */
public enum Ingredient
{
	STRAWBERRY(new ItemStack(KitchenItems.cut_strawberry)),
	APPLE(new ItemStack(KitchenItems.cut_apple),true),
	SUGAR(new ItemStack(Items.sugar),true),
	WATER(new ItemStack(Items.potionitem,0),true);

	final ItemStack stack;
	final boolean canBeUsedForSoup;

	private Ingredient(ItemStack matchingItem)
	{
		this(matchingItem,false);
	}

	private Ingredient(ItemStack matchingItem, boolean soup)
	{
		this.canBeUsedForSoup = soup;
		this.stack=matchingItem;
	}

	public boolean matches(ItemStack item)
	{
		return OreDictionary.itemMatches(item, this.stack, true);
	}

	public static Ingredient getMatchingIngredient(ItemStack item)
	{
		for (Ingredient ingredient : values())
		{
			if (ingredient.matches(item))
				return ingredient;
		} return null;
	}
}
