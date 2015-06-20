package dk.mrspring.kitchen.comp.furniture;

import com.mrcrayfish.furniture.api.IRecipeRegistry;
import com.mrcrayfish.furniture.api.RecipeVariables;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModLogger;
import dk.mrspring.kitchen.recipe.INEIRecipeHelper;
import dk.mrspring.kitchen.recipe.IRecipe;
import dk.mrspring.kitchen.recipe.KnifeRecipes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Created by MrSpring on 08-12-2014 for TheKitchenMod.
 */
public class CrayfishFurnitureRegister
{
    public static void registerRecipes(IRecipeRegistry registryComm)
    {
        ModLogger.print(ModLogger.INFO, "Loading Crayfish Furniture Mod compatibility...");

        for (IRecipe recipe : KnifeRecipes.instance().getRecipes())
        {
            if (recipe instanceof INEIRecipeHelper)
            {
                INEIRecipeHelper recipeHelper = (INEIRecipeHelper) recipe;
                RecipeVariables variables = new RecipeVariables();
                ItemStack input = recipeHelper.getExpectedInput();
                variables.addValue("input", input);
                variables.addValue("output", recipeHelper.getExpectedOutput(input));
                registryComm.registerRecipe("choppingboard", variables);
            }
        }


        RecipeVariables variables = new RecipeVariables();
        variables.addValue("name", "Strawberry Smoothie");
        variables.addValue("heal", 6);
        variables.addValue("ingredients", new ItemStack[]{new ItemStack(KitchenItems.cut_strawberry, 8), new ItemStack(Items.sugar, 4), new ItemStack(Blocks.ice, 1)});
        variables.addValue("colour", new int[]{255, 0, 0});
        registryComm.registerRecipe("blender", variables);
    }
}
