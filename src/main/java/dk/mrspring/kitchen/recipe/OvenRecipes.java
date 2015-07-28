package dk.mrspring.kitchen.recipe;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModConfig;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.Map;

/**
 * Created by Konrad on 01-07-2014.
 */
public class OvenRecipes extends BasicRecipeHandler
{
    public static OvenRecipes instance()
    {
        return Kitchen.instance.ovenRecipes;
    }

    @Override
    public void load()
    {
        super.load();

        this.addFoodRecipes();
        this.addDefaultRecipes();
        this.addAll(ModConfig.getOvenConfig().custom_oven_recipes);
    }

    private void addFoodRecipes()
    {
        Map<ItemStack, ItemStack> smeltingRecipes = FurnaceRecipes.smelting().getSmeltingList();

        for (Map.Entry<ItemStack, ItemStack> entry : smeltingRecipes.entrySet())
            if (entry.getValue().getItem() instanceof ItemFood)
            {
                ItemStack input = entry.getKey();
                if (input.getItemDamage() > 1000)
                    input.setItemDamage(0);
                recipes.add(new BasicRecipe(input, entry.getValue()));
            }
    }

    private void addDefaultRecipes()
    {
        BasicRecipe[] defaultRecipes = new BasicRecipe[]{
                new BasicRecipe(new ItemStack(KitchenItems.raw_roast_beef), new ItemStack(KitchenItems.roast_beef)),
                new BasicRecipe(new ItemStack(KitchenItems.raw_chicken_fillet), new ItemStack(KitchenItems.chicken_fillet)),
                new BasicRecipe(new ItemStack(KitchenItems.raw_bacon), new ItemStack(KitchenItems.bacon)),
                new BasicRecipe(new ItemStack(KitchenItems.empty_muffin_tray), new ItemStack(KitchenItems.filled_muffin_tray))
        };
        addAll(defaultRecipes);
    }

    public boolean hasBurntResult(ItemStack input)
    {
        return getBurntResult(input) != null;
    }

    public ItemStack getBurntResult(ItemStack input)
    {
        IRecipe recipe = getRecipeFor(input);
        if (recipe instanceof OvenRecipe) return ((OvenRecipe) recipe).getBurntResult(input);
        else return null;
    }
}
