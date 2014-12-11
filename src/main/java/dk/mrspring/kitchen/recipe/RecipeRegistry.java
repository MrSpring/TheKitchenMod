package dk.mrspring.kitchen.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.ModLogger;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static dk.mrspring.kitchen.KitchenItems.*;
import static java.lang.Character.valueOf;

/**
 * Created by MrSpring on 04-11-2014 for TheKitchenMod.
 */
public class RecipeRegistry
{
    public static void registerRecipes()
    {
        // Cutting Board recipe
        GameRegistry.addRecipe(new ShapedOreRecipe(KitchenBlocks.board, "SPS", valueOf('S'), "slabWood", valueOf('P'), Blocks.wooden_pressure_plate));
        // Oven recipe
        GameRegistry.addRecipe(new ItemStack(KitchenBlocks.oven, 1, 0), "III", "ICI", "IFI", valueOf('I'), new ItemStack(Items.iron_ingot), valueOf('C'), new ItemStack(Items.coal), valueOf('F'), new ItemStack(Items.flint_and_steel));
        // Tile recipe
        GameRegistry.addRecipe(new ItemStack(KitchenBlocks.tiles, 2), "IB", "CC", "CC", valueOf('I'), new ItemStack(Items.dye, 1, 0), valueOf('B'), new ItemStack(Items.dye, 1, 15), valueOf('C'), Items.clay_ball);
        // Frying Pan recipe
        GameRegistry.addRecipe(new ItemStack(KitchenBlocks.frying_pan, 1), "III", "II ", valueOf('I'), new ItemStack(Items.iron_ingot));
        // Cabinet recipe
        GameRegistry.addRecipe(new ItemStack(KitchenBlocks.kitchen_cabinet, 1), "QQQ", "PPP", "PPP", valueOf('Q'), Items.quartz, valueOf('P'), new ItemStack(Blocks.planks, OreDictionary.WILDCARD_VALUE));

        // Jam Jar recipe
        GameRegistry.addRecipe(new ItemStack(jam_jar, 1), " I ", "G G", "GGG", valueOf('I'), Items.iron_ingot, valueOf('G'), Blocks.glass);

        // Mixing Bowl recipes
        GameRegistry.addShapelessRecipe(getMixingBowlStack("waffle_dough", 3), getMixingBowlStack(null, 0), Items.egg, Items.wheat, Items.milk_bucket, Items.sugar);
        GameRegistry.addShapelessRecipe(getMixingBowlStack("pancake_dough", 3), getMixingBowlStack(null, 0), Items.egg, KitchenItems.flour, Items.milk_bucket, Items.sugar, KitchenItems.butter);

        /**
         * Knife recipes
         */
        addKnifeRecipe(new ItemStack(raw_bacon, 3), new ItemStack(Items.porkchop));
        addKnifeRecipe(new ItemStack(tomato_slice, 4), new ItemStack(tomato));
        addKnifeRecipe(new ItemStack(lettuce_leaf, 2), new ItemStack(lettuce));
        addKnifeRecipe(new ItemStack(carrot_slice, 2), new ItemStack(Items.carrot));
        addKnifeRecipe(new ItemStack(potato_slice, 3), new ItemStack(Items.potato));
        addKnifeRecipe(new ItemStack(bread_slice, 2), new ItemStack(Items.bread));
        addKnifeRecipe(new ItemStack(raw_roast_beef, 2), new ItemStack(Items.beef));
        addKnifeRecipe(new ItemStack(raw_chicken_fillet, 3), new ItemStack(Items.chicken));
        addKnifeRecipe(new ItemStack(chicken_leg, 2), new ItemStack(Items.cooked_chicken));
        addKnifeRecipe(new ItemStack(cheese_slice, 2), new ItemStack(cheese));
        addKnifeRecipe(new ItemStack(cut_strawberry, 2), new ItemStack(strawberry));
        addKnifeRecipe(new ItemStack(jammable_strawberry, 2), new ItemStack(strawberry), new ItemStack(Items.sugar));
        addKnifeRecipe(new ItemStack(cut_apple, 2), new ItemStack(Items.apple));

        /**
         * Mortar and Pestle recipes
         */
        GameRegistry.addShapelessRecipe(new ItemStack(flour, 1), new ItemStack(mortar_and_pestle, 1), new ItemStack(Items.wheat, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addShapelessRecipe(new ItemStack(flour, 3), new ItemStack(mortar_and_pestle, 1), new ItemStack(Items.bread, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addShapelessRecipe(new ItemStack(butter, 1), new ItemStack(mortar_and_pestle, 1), new ItemStack(Items.milk_bucket, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addShapelessRecipe(new ItemStack(peanut, 2), new ItemStack(mortar_and_pestle, 1), new ItemStack(peanuts_in_shell, 1));
        //GameRegistry.addShapelessRecipe(getJamJarItemStack(Jam.STRAWBERRY, 6), new ItemStack(KitchenItems.cheese_slice), new ItemStack(Items.sugar), new ItemStack(KitchenItems.jam_jar, 1, 0));

        GameRegistry.addShapelessRecipe(new ItemStack(cheese, 2), new ItemStack(Items.milk_bucket));
        GameRegistry.addShapelessRecipe(new ItemStack(jammable_strawberry), new ItemStack(cut_strawberry), new ItemStack(Items.sugar));

        GameRegistry.addShapelessRecipe(new ItemStack(mortar_and_pestle, 1), new ItemStack(mortar), new ItemStack(pestle));

        GameRegistry.addRecipe(new ItemStack(mortar), "S S", " S ", valueOf('S'), Blocks.stone);
        GameRegistry.addRecipe(new ItemStack(pestle), "S ", " S", valueOf('S'), Blocks.stone);

        // Registers the Knife recipe based on the Config value
        switch (ModConfig.getKnifeConfig().knife_recipe)
        {
            case 0:
                GameRegistry.addRecipe(new ShapedOreRecipe(knife, "I ", " S", valueOf('S'), "stickWood", valueOf('I'), Items.iron_ingot));
                break;
            case 1:
                GameRegistry.addRecipe(new ShapedOreRecipe(knife, "I", "S", valueOf('S'), "stickWood", valueOf('I'), Items.iron_ingot));
                break;
            case 2:
                GameRegistry.addRecipe(new ShapedOreRecipe(knife, "IS", valueOf('S'), "stickWood", valueOf('I'), Items.iron_ingot));
                break;
            case 3:
            {
                ModLogger.print(ModLogger.INFO, "Registering custom Knife recipe...");

                ArrayList<String> lines = new ArrayList<String>();

                for (String line : ModConfig.getKnifeConfig().custom_knife_recipe)
                {
                    if (!line.equals("BBB"))
                        lines.add(line);
                }

                ArrayList<Object> recipe = new ArrayList<Object>();

                recipe.addAll(lines);
                recipe.add(valueOf('I'));
                recipe.add(Items.iron_ingot);
                recipe.add(valueOf('S'));
                recipe.add("stickWood");

                GameRegistry.addRecipe(new ShapedOreRecipe(knife, recipe.toArray()));

                break;
            }
            //
            default:
                GameRegistry.addRecipe(new ShapedOreRecipe(knife, "I ", " S", valueOf('S'), "stickWood", valueOf('I'), Items.iron_ingot));
                break;
        }

        GameRegistry.addRecipe(new ItemStack(KitchenBlocks.plate, 1, 0), "CBC", " C ", valueOf('C'), Items.clay_ball, valueOf('B'), new ItemStack(Items.dye, 1, 15));


        /**
         * Smelting recipes
         */
        GameRegistry.addSmelting(raw_bacon, new ItemStack(bacon, 1, 0), 3.0F);
        GameRegistry.addSmelting(flour, new ItemStack(toast, 2, 0), 3.0F);
        GameRegistry.addSmelting(raw_chicken_fillet, new ItemStack(chicken_fillet, 1, 0), 3.0F);
        GameRegistry.addSmelting(raw_roast_beef, new ItemStack(roast_beef, 1, 0), 3.0F);
    }

    private static void addKnifeRecipe(ItemStack output, Object... input)
    {
        List<Object> recipe = new ArrayList<Object>();

        Collections.addAll(recipe, input);
        recipe.add(new ItemStack(knife));

        GameRegistry.addShapelessRecipe(output, recipe.toArray());
    }

    private static ItemStack getMixingBowlStack(String mixType, int usesLeft)
    {
        ItemStack bowl = new ItemStack(KitchenItems.mixing_bowl, 1, usesLeft);
        if (mixType != null)
        {
            NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setString("MixType", mixType);
            bowl.setTagCompound(tagCompound);
        }
        return bowl;
    }
}
