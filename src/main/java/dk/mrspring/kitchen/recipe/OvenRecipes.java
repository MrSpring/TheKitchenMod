package dk.mrspring.kitchen.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.ModLogger;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Konrad on 01-07-2014.
 */
public class OvenRecipes
{
    public static String[] defaultInput = new String[]{"kitchen:raw_roast_beef", "kitchen:chicken_fillet_raw", "kitchen:bacon_raw"};
    public static String[] defaultOutput = new String[]{"kitchen:roast_beef", "kitchen:chicken_fillet_cooked", "kitchen:bacon_cooked"};

    // A HashMap storing all the recipes. Keys are input, values are output.
    static Map<ItemStack, ItemStack> customOvenRecipes = new HashMap<ItemStack, ItemStack>();

    public static void load()
    {
        // TODO Possible in-game recipe changer. Per world?
        if (ModConfig.getOvenConfig().custom_oven_recipes_input.length == ModConfig.getOvenConfig().custom_oven_recipes_output.length)
        {
            ArrayList<ItemStack> input = getArrayFromStringList(ModConfig.getOvenConfig().custom_oven_recipes_input, "Custom Oven Recipes Input");
            ArrayList<ItemStack> output = getArrayFromStringList(ModConfig.getOvenConfig().custom_oven_recipes_output, "Custom Oven Recipes Output");

            if (input.size() == output.size())
            {
                setCustomOvenRecipes(input, output);
            } else
            {
                ModLogger.print(ModLogger.INFO, "Unable to load Custom oven recipes! Loading defaults.");
                ModLogger.print(ModLogger.DEBUG, "Some of the Items it was trying to load has wrong names. Correct this issue to load custom recipes!");

                setCustomOvenRecipes(input, output);
            }
        } else
        {
            ModLogger.print(ModLogger.INFO, "Unable to load Custom oven recipes! Loading defaults.");
            ModLogger.print(ModLogger.DEBUG, "One of the list were bigger than the other! Input length: " + ModConfig.getOvenConfig().custom_oven_recipes_input.length + ", Output length: " + ModConfig.getOvenConfig().custom_oven_recipes_output.length);

            setCustomOvenRecipes(getArrayFromStringList(defaultInput, "Default Oven Input"), getArrayFromStringList(defaultOutput, "Default Oven Output"));
        }
    }

    public static void addFoodRecipes()
    {
        Map<ItemStack, ItemStack> smeltingRecipes = FurnaceRecipes.smelting().getSmeltingList();

        for (Map.Entry<ItemStack, ItemStack> entry : smeltingRecipes.entrySet())
            if (entry.getValue().getItem() instanceof ItemFood)
            {
                ItemStack input=entry.getKey();
                if (input.getItemDamage()>1000)
                    input.setItemDamage(0);
                customOvenRecipes.put(input, entry.getValue());
            }
    }

    public static Map<ItemStack, ItemStack> getCustomOvenRecipes()
    {
        return customOvenRecipes;
    }

    private static void setCustomOvenRecipes(ArrayList<ItemStack> input, ArrayList<ItemStack> output)
    {
        for (int i = 0; i < input.size(); i++)
        {
            ItemStack inputStack = input.get(i), outputStack = output.get(i);
            customOvenRecipes.put(inputStack, outputStack);
        }
    }

    /**
     * @param input1 The ItemStack to get result of.
     * @return Returns the result of the item stack when cooked in the Oven. Returns null when nothing was found
     */
    public static ItemStack getCookingResult(ItemStack input1)
    {
        for (Map.Entry<ItemStack, ItemStack> entry : customOvenRecipes.entrySet())
        {
            ItemStack input2 = entry.getKey();
            if (input1.isItemEqual(input2))
                return entry.getValue();
        }

        /*for (int i = 0; i < customOvenRecipes[0].size(); i++)
        {
            ItemStack stack = customOvenRecipes[0].get(i);
            if (itemStack.isItemEqual(stack))
                return customOvenRecipes[1].get(i);
        }*/

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
            } else
                ModLogger.print(ModLogger.DEBUG, "Unable to add ItemStack to '" + type + "', it returned null. ModID: " + modId + ", name: " + itemName + ".");
        }

        return itemStackArrayList;
    }

    public static void addRecipe(ItemStack input, ItemStack output)
    {
        if (input != null && output != null)
        {
            customOvenRecipes.put(input, output);
        }
    }
}
