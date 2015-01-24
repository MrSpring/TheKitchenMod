package dk.mrspring.kitchen.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.ModLogger;
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
import static net.minecraft.init.Blocks.*;
import static net.minecraft.init.Items.*;

/**
 * Created by MrSpring on 04-11-2014 for TheKitchenMod.
 */
public class RecipeRegistry
{
    public static void registerRecipes()
    {
        // Cutting Board recipe
        GameRegistry.addRecipe(new ShapedOreRecipe(KitchenBlocks.board, "SPS", valueOf('S'), "slabWood", valueOf('P'), wooden_pressure_plate));
        // Oven recipe
        GameRegistry.addRecipe(new ItemStack(KitchenBlocks.oven, 1, 0), "III", "ICI", "IFI", valueOf('I'), new ItemStack(iron_ingot), valueOf('C'), new ItemStack(coal), valueOf('F'), new ItemStack(flint_and_steel));
        // Tile recipe
        GameRegistry.addRecipe(new ItemStack(KitchenBlocks.tiles, 2), "IB", "CC", "CC", valueOf('I'), new ItemStack(dye, 1, 0), valueOf('B'), new ItemStack(dye, 1, 15), valueOf('C'), clay_ball);
        // Frying Pan recipe
        GameRegistry.addRecipe(new ItemStack(KitchenBlocks.frying_pan, 1), "III", "II ", valueOf('I'), new ItemStack(iron_ingot));
        // Cabinet recipe
        GameRegistry.addRecipe(new ItemStack(KitchenBlocks.kitchen_cabinet, 1), "QQQ", "PPP", "PPP", valueOf('Q'), quartz, valueOf('P'), new ItemStack(planks, OreDictionary.WILDCARD_VALUE));

        // Jam Jar recipe
        GameRegistry.addRecipe(new ItemStack(jam_jar, 1), " I ", "G G", "GGG", valueOf('I'), iron_ingot, valueOf('G'), glass);

        // Mixing Bowl recipes
        GameRegistry.addShapelessRecipe(getMixingBowlStack("waffle_dough", 3), getMixingBowlStack(null, 0), egg, Items.wheat, milk_bucket, sugar);
        GameRegistry.addShapelessRecipe(getMixingBowlStack("pancake_dough", 3), getMixingBowlStack(null, 0), egg, flour, milk_bucket, sugar, butter);
        GameRegistry.addShapelessRecipe(getMixingBowlStack("vanilla_ice_cream", 3), getMixingBowlStack(null, 0), milk_bucket, crushed_ice, crushed_vanilla, sugar);
        GameRegistry.addShapelessRecipe(getMixingBowlStack("strawberry_ice_cream", 3), getMixingBowlStack(null, 0), milk_bucket, crushed_ice, crushed_vanilla, sugar, cut_strawberry);
        GameRegistry.addShapelessRecipe(getMixingBowlStack("strawberry_ice_cream", 3), getMixingBowlStack(null, 0), milk_bucket, crushed_ice, crushed_vanilla, jammable_strawberry);

        /**
         * Knife recipes
         */
        addKnifeRecipe(new ItemStack(raw_bacon, 3), new ItemStack(porkchop));
        addKnifeRecipe(new ItemStack(tomato_slice, 4), new ItemStack(tomato));
        addKnifeRecipe(new ItemStack(lettuce_leaf, 2), new ItemStack(lettuce));
        addKnifeRecipe(new ItemStack(carrot_slice, 2), new ItemStack(carrot));
        addKnifeRecipe(new ItemStack(potato_slice, 3), new ItemStack(potato));
        addKnifeRecipe(new ItemStack(bread_slice, 2), new ItemStack(bread));
        addKnifeRecipe(new ItemStack(raw_roast_beef, 2), new ItemStack(beef));
        addKnifeRecipe(new ItemStack(raw_chicken_fillet, 3), new ItemStack(chicken));
        addKnifeRecipe(new ItemStack(chicken_leg, 2), new ItemStack(cooked_chicken));
        addKnifeRecipe(new ItemStack(cheese_slice, 2), new ItemStack(cheese));
        addKnifeRecipe(new ItemStack(cut_strawberry, 2), new ItemStack(strawberry));
        addKnifeRecipe(new ItemStack(jammable_strawberry, 2), new ItemStack(strawberry), new ItemStack(sugar));
        addKnifeRecipe(new ItemStack(cut_apple, 2), new ItemStack(apple));

        /**
         * Mortar and Pestle recipes
         */
        GameRegistry.addShapelessRecipe(new ItemStack(flour, 1), new ItemStack(mortar_and_pestle, 1), new ItemStack(Items.wheat, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addShapelessRecipe(new ItemStack(flour, 3), new ItemStack(mortar_and_pestle, 1), new ItemStack(bread, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addShapelessRecipe(new ItemStack(butter, 1), new ItemStack(mortar_and_pestle, 1), new ItemStack(milk_bucket, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addShapelessRecipe(new ItemStack(peanut, 2), new ItemStack(mortar_and_pestle, 1), new ItemStack(peanuts_in_shell, 1));
        GameRegistry.addShapelessRecipe(new ItemStack(crushed_vanilla, 2), new ItemStack(mortar_and_pestle, 1), new ItemStack(dried_vanilla, 1));
        GameRegistry.addShapelessRecipe(new ItemStack(crushed_ice, 2), new ItemStack(mortar_and_pestle, 1), new ItemStack(ice, 1));

        GameRegistry.addShapelessRecipe(new ItemStack(cheese, 2), new ItemStack(milk_bucket));
        GameRegistry.addShapelessRecipe(new ItemStack(jammable_strawberry), new ItemStack(cut_strawberry), new ItemStack(sugar));

        GameRegistry.addShapelessRecipe(new ItemStack(mortar_and_pestle, 1), new ItemStack(mortar), new ItemStack(pestle));

        GameRegistry.addRecipe(new ItemStack(mortar), "S S", " S ", valueOf('S'), stone);
        GameRegistry.addRecipe(new ItemStack(pestle), "S ", " S", valueOf('S'), stone);

        // Registers the Knife recipe based on the Config value
        switch (ModConfig.getKnifeConfig().knife_recipe)
        {
            case 0:
                GameRegistry.addRecipe(new ShapedOreRecipe(knife, "I ", " S", valueOf('S'), "stickWood", valueOf('I'), iron_ingot));
                break;
            case 1:
                GameRegistry.addRecipe(new ShapedOreRecipe(knife, "I", "S", valueOf('S'), "stickWood", valueOf('I'), iron_ingot));
                break;
            case 2:
                GameRegistry.addRecipe(new ShapedOreRecipe(knife, "IS", valueOf('S'), "stickWood", valueOf('I'), iron_ingot));
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
                recipe.add(iron_ingot);
                recipe.add(valueOf('S'));
                recipe.add("stickWood");

                GameRegistry.addRecipe(new ShapedOreRecipe(knife, recipe.toArray()));

                break;
            }
            //
            default:
                GameRegistry.addRecipe(new ShapedOreRecipe(knife, "I ", " S", valueOf('S'), "stickWood", valueOf('I'), iron_ingot));
                break;
        }

        GameRegistry.addRecipe(new ItemStack(KitchenBlocks.plate, 1, 0), "CBC", " C ", valueOf('C'), clay_ball, valueOf('B'), new ItemStack(dye, 1, 15));


        /**
         * Smelting recipes
         */
        GameRegistry.addSmelting(raw_bacon, new ItemStack(bacon, 1, 0), 3F);
        GameRegistry.addSmelting(flour, new ItemStack(toast, 2, 0), 3F);
        GameRegistry.addSmelting(raw_chicken_fillet, new ItemStack(chicken_fillet, 1, 0), 3F);
        GameRegistry.addSmelting(raw_roast_beef, new ItemStack(roast_beef, 1, 0), 3F);
        GameRegistry.addSmelting(raw_vanilla, new ItemStack(dried_vanilla, 1, 0), 3F);
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
        ItemStack bowl = new ItemStack(mixing_bowl, 1, usesLeft);
        if (mixType != null)
        {
            NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setString("MixType", mixType);
            bowl.setTagCompound(tagCompound);
        }
        return bowl;
    }
}
