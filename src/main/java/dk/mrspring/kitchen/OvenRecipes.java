package dk.mrspring.kitchen;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * Created by Konrad on 01-07-2014.
 */
public class OvenRecipes
{
    static ArrayList<ItemStack>[] customOvenRecipes = new ArrayList[2];

    public static void load()
    {
        // TODO Use a custom JSON file instead of the Config
        // TODO Possible in-game recipe changer
        if (ModConfig.getOvenConfig().custom_oven_recipes_input.length == ModConfig.getOvenConfig().custom_oven_recipes_output.length)
        {
            ArrayList<ItemStack> input = getArrayFromStringList(ModConfig.getOvenConfig().custom_oven_recipes_input, "Custom Oven Recipes Input");
            ArrayList<ItemStack> output = getArrayFromStringList(ModConfig.getOvenConfig().custom_oven_recipes_output, "Custom Oven Recipes Output");

            if (input.size() == output.size())
            {
                customOvenRecipes[0] = input;
                customOvenRecipes[1] = output;
            }
            else
            {
                ModLogger.print(ModLogger.INFO, "Unable to load Custom oven recipes! Loading defaults.");
                ModLogger.print(ModLogger.DEBUG, "Some of the Items it was trying to load has wrong names. Correct this issue to load custom recipes!");

                customOvenRecipes[0] = getArrayFromStringList(ModConfig.getOvenConfig().custom_oven_recipes_input, "Default Oven Recipes Input");
                customOvenRecipes[1] = getArrayFromStringList(ModConfig.getOvenConfig().custom_oven_recipes_output, "Default Oven Recipes Output");
            }
        }
        else
        {
            ModLogger.print(ModLogger.INFO, "Unable to load Custom oven recipes! Loading defaults.");
            ModLogger.print(ModLogger.DEBUG, "One of the list were bigger than the other! Input length: " + ModConfig.getOvenConfig().custom_oven_recipes_input.length + ", Output length: " + ModConfig.getOvenConfig().custom_oven_recipes_output.length);

            customOvenRecipes[0] = getArrayFromStringList(ModConfig.getOvenConfig().custom_oven_recipes_input, "Default Oven Recipes Input");
            customOvenRecipes[1] = getArrayFromStringList(ModConfig.getOvenConfig().custom_oven_recipes_output, "Default Oven Recipes Output");
        }
    }

    /***
     *
     * @param itemStack The ItemStack to get result of.
     * @return Returns the result of the item stack when cooked in the Oven. Returns null when nothing was found
     */
    public static ItemStack getCookingResult(ItemStack itemStack)
    {
        for (int i = 0; i < customOvenRecipes[0].size(); i++)
        {
            ItemStack stack = customOvenRecipes[0].get(i);
            if (itemStack.isItemEqual(stack))
                return customOvenRecipes[1].get(i);
        }

        return null;
    }

    public static ArrayList<ItemStack> getArrayFromStringList(String[] list, String type)
    {
        ArrayList<ItemStack> itemStackArrayList = new ArrayList<ItemStack>();

        for (String aList : list)
        {
            String modId = "minecraft";
            String itemName;

            if (aList.contains(":"))
            {
                modId = aList.split(":")[0];
                itemName = aList.split(":")[1];
            } else
                itemName = aList;

            ItemStack stack = GameRegistry.findItemStack(modId, itemName, 1);
            if (stack != null)
            {
                ModLogger.print(ModLogger.DEBUG, "Adding " + stack.getDisplayName() + " to '" + type + "'.");
                itemStackArrayList.add(stack);
            }
            else
                ModLogger.print(ModLogger.DEBUG, "Unable to add ItemStack to '" + type + "', it returned null. ModID: " + modId + ", name: " + itemName + ".");
        }

        return itemStackArrayList;
    }
}
