package dk.mrspring.kitchen.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.ModLogger;
import dk.mrspring.kitchen.config.JsonBasicRecipe;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Konrad on 01-07-2014.
 */
public class OvenRecipes
{
//    public static String[] defaultInput = new String[]{"kitchen:raw_roast_beef", "kitchen:chicken_fillet_raw", "kitchen:bacon_raw"};
//    public static String[] defaultOutput = new String[]{"kitchen:roast_beef", "kitchen:chicken_fillet_cooked", "kitchen:bacon_cooked"};

    //    A HashMap storing all the recipes. Keys are input, values are output.
//    static Map<ItemStack, ItemStack> customOvenRecipes = new HashMap<ItemStack, ItemStack>();
    static List<BasicRecipe> customOvenRecipes = new ArrayList<BasicRecipe>();

    public static void load()
    {
        // TODO: Possible in-game recipe changer. Per world?
        // TODO: Make config sync. from server when client joins?

        addDefaultRecipes();
        addFoodRecipes();
        addAll(ModConfig.getOvenConfig().custom_oven_recipes);

//        if (ModConfig.getOvenConfig().custom_oven_recipes_input.length == ModConfig.getOvenConfig().custom_oven_recipes_output.length)
//        {
//            ArrayList<ItemStack> input = getArrayFromStringList(ModConfig.getOvenConfig().custom_oven_recipes_input, "Custom Oven Recipes Input");
//            ArrayList<ItemStack> output = getArrayFromStringList(ModConfig.getOvenConfig().custom_oven_recipes_output, "Custom Oven Recipes Output");
//
//            if (input.size() == output.size())
//            {
//                setCustomOvenRecipes(input, output);
//            } else
//            {
//                ModLogger.print(ModLogger.INFO, "Unable to load Custom oven recipes! Loading defaults.");
//                ModLogger.print(ModLogger.DEBUG, "Some of the Items it was trying to load has wrong names. Correct this issue to load custom recipes!");
//
//                setCustomOvenRecipes(input, output);
//            }
//        } else
//        {
//            ModLogger.print(ModLogger.INFO, "Unable to load Custom oven recipes! Loading defaults.");
//            ModLogger.print(ModLogger.DEBUG, "One of the list were bigger than the other! Input length: " + ModConfig.getOvenConfig().custom_oven_recipes_input.length + ", Output length: " + ModConfig.getOvenConfig().custom_oven_recipes_output.length);
//
//            setCustomOvenRecipes(getArrayFromStringList(defaultInput, "Default Oven Input"), getArrayFromStringList(defaultOutput, "Default Oven Output"));
//        }
    }

    private static void addAll(JsonBasicRecipe[] array)
    {
        BasicRecipe[] recipes = new BasicRecipe[array.length];
        for (int i = 0; i < array.length; i++)
        {
            JsonBasicRecipe recipe = array[i];
            recipes[i] = recipe.toBasicRecipe();
        }
        addAll(recipes);
    }

    private static void addAll(BasicRecipe[] array)
    {
        Collections.addAll(customOvenRecipes, array);
    }

    private static void addFoodRecipes()
    {
        Map<ItemStack, ItemStack> smeltingRecipes = FurnaceRecipes.smelting().getSmeltingList();

        for (Map.Entry<ItemStack, ItemStack> entry : smeltingRecipes.entrySet())
            if (entry.getValue().getItem() instanceof ItemFood)
            {
                ItemStack input = entry.getKey();
                if (input.getItemDamage() > 1000)
                    input.setItemDamage(0);
                customOvenRecipes.add(new BasicRecipe(input, entry.getValue()));
            }
    }

    private static void addDefaultRecipes()
    {
        BasicRecipe[] defaultRecipes = new BasicRecipe[]{
                new BasicRecipe(new ItemStack(KitchenItems.raw_roast_beef), new ItemStack(KitchenItems.roast_beef)),
                new BasicRecipe(new ItemStack(KitchenItems.raw_chicken_fillet), new ItemStack(KitchenItems.chicken_fillet)),
                new BasicRecipe(new ItemStack(KitchenItems.raw_bacon), new ItemStack(KitchenItems.bacon))
        };
        addAll(defaultRecipes);
    }

    public static List<BasicRecipe> getCustomOvenRecipes()
    {
        return customOvenRecipes;
    }

    /**
     * @param input1 The ItemStack to get result of.
     * @return Returns the result of the item stack when cooked in the Oven. Returns null when nothing was found
     */
    public static ItemStack getCookingResult(ItemStack input1)
    {
        for (BasicRecipe entry : customOvenRecipes)
        {
            ItemStack input2 = entry.getInput();
            if (input1.isItemEqual(input2))
                return entry.getOutput();
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
            customOvenRecipes.add(new BasicRecipe(input, output));
        }
    }
}
