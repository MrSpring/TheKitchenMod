package dk.mrspring.kitchen.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.ModLogger;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrSpring on 10-12-2014 for TheKitchenMod.
 */
public class ToasterRecipes
{
    static HashMap<ItemStack, ItemStack> recipes = new HashMap<ItemStack, ItemStack>();
    public static String[] defaultInput = new String[]{"kitchen:toast"};
    public static String[] defaultOutput = new String[]{"kitchen:toasted_toast"};

    public static void load()
    {
        // TODO: Possible in-game recipe changer. Per world?
        // TODO: Make config sync. from server when client joins?
        if (ModConfig.getToasterConfig().custom_toaster_recipes_input.length == ModConfig.getToasterConfig().custom_toaster_recipes_output.length)
        {
            ArrayList<ItemStack> input = getArrayFromStringList(ModConfig.getToasterConfig().custom_toaster_recipes_input, "Custom Toaster Recipes Input");
            ArrayList<ItemStack> output = getArrayFromStringList(ModConfig.getToasterConfig().custom_toaster_recipes_output, "Custom Toaster Recipes Output");

            if (input.size() == output.size())
            {
                setCustomToasterRecipes(input, output);
            } else
            {
                ModLogger.print(ModLogger.INFO, "Unable to load Custom toaster recipes! Loading defaults.");
                ModLogger.print(ModLogger.DEBUG, "Some of the Items it was trying to load has wrong names. Correct this issue to load custom recipes!");

                setCustomToasterRecipes(input, output);
            }
        } else
        {
            ModLogger.print(ModLogger.INFO, "Unable to load Custom toaster recipes! Loading defaults.");
            ModLogger.print(ModLogger.DEBUG, "One of the list were bigger than the other! Input length: " + ModConfig.getToasterConfig().custom_toaster_recipes_input.length + ", Output length: " + ModConfig.getToasterConfig().custom_toaster_recipes_output.length);

            setCustomToasterRecipes(getArrayFromStringList(defaultInput, "Default Toaster Input"), getArrayFromStringList(defaultOutput, "Default Toaster Output"));
        }
    }

    public static Map<ItemStack, ItemStack> getToasterRecipes()
    {
        return recipes;
    }

    private static void setCustomToasterRecipes(ArrayList<ItemStack> input, ArrayList<ItemStack> output)
    {
        for (int i = 0; i < input.size(); i++)
        {
            ItemStack inputStack = input.get(i), outputStack = output.get(i);
            recipes.put(inputStack, outputStack);
        }
    }

    /**
     * @param input1 The ItemStack to get result of.
     * @return Returns the result of the item stack when cooked in the Toaster. Returns null when nothing was found
     */
    public static ItemStack getToastingResult(ItemStack input1)
    {
        for (Map.Entry<ItemStack, ItemStack> entry : recipes.entrySet())
        {
            ItemStack input2 = entry.getKey();
            if (input1.isItemEqual(input2))
                return entry.getValue();
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
            } else
                ModLogger.print(ModLogger.DEBUG, "Unable to add ItemStack to '" + type + "', it returned null. ModID: " + modId + ", name: " + itemName + ".");
        }

        return itemStackArrayList;
    }

    public static void addRecipe(ItemStack input, ItemStack output)
    {
        if (input != null && output != null)
        {
            recipes.put(input, output);
        }
    }
}
