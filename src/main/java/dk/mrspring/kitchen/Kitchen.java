package dk.mrspring.kitchen;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.api.event.BoardEventRegistry;
import dk.mrspring.kitchen.block.BlockBase;
import dk.mrspring.kitchen.combo.SandwichCombo;
import dk.mrspring.kitchen.event.SandwichableTooltipEvent;
import dk.mrspring.kitchen.item.ItemBase;
import dk.mrspring.kitchen.pan.Ingredient;
import dk.mrspring.kitchen.pan.Jam;
import dk.mrspring.kitchen.tileentity.*;
import dk.mrspring.kitchen.world.gen.WorldGenWildPlants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.io.File;
import java.util.ArrayList;

import static java.lang.Character.valueOf;

@Mod(modid = ModInfo.modid, name = ModInfo.name, version = ModInfo.version)
public class Kitchen
{
    @Instance(ModInfo.modid)
    public static Kitchen instance;

    @SidedProxy(serverSide = "dk.mrspring.kitchen.CommonProxy", clientSide = "dk.mrspring.kitchen.ClientProxy")
    public static CommonProxy proxy;

    public CreativeTabs tab;

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event)
    {
		// Loading the config files
        ModConfig.load(new File("config"));

        // Initializing the Creative Tab
        instance.tab = new CreativeTabs("tabKitchen")
        {
            @Override
            public Item getTabIconItem()
            {
                return KitchenItems.basic_sandwich.getItem();
            }
        };

        // Registering Tile Entities
        GameRegistry.registerTileEntity(TileEntityBoard.class, "tileEntityBoard");
        GameRegistry.registerTileEntity(TileEntityOven.class, "tileEntityOven");
        GameRegistry.registerTileEntity(TileEntityPlate.class, "tileEntityPlate");
        GameRegistry.registerTileEntity(TileEntityKitchenCabinet.class, "tileEntityKitchenCabinet");
        GameRegistry.registerTileEntity(TileEntityPan.class, "tileEntityFryingPan");


        // Loading Blocks and Items
        BlockBase.load();
        ItemBase.load();

        // Registering renderers
        proxy.registerRenderers();
        // Registers the default Board events
        BoardEventRegistry.registerDefaultEvents();
    }

    @EventHandler
    public static void init(FMLInitializationEvent event)
    {
        ModLogger.print(ModLogger.INFO, "Loading Combos...");
        // Loading Combos
        SandwichCombo.load();

        ModLogger.print(ModLogger.INFO, "Loading Custom Oven recipes...");
        // Loading Custom Oven recipes
        OvenRecipes.load();

        // Registering the Lettuce world generator
        GameRegistry.registerWorldGenerator(new WorldGenWildPlants(), 1);

        /**
         * RECIPES
         */

        // Cutting Board recipe
        GameRegistry.addRecipe(new ShapedOreRecipe(KitchenBlocks.board, "SPS", valueOf('S'), "slabWood", valueOf('P'), Blocks.wooden_pressure_plate));
        // Oven recipe
        GameRegistry.addRecipe(new ItemStack(KitchenBlocks.oven, 1, 0), "III", "ICI", "IFI", valueOf('I'), new ItemStack(Items.iron_ingot), valueOf('C'), new ItemStack(Items.coal), valueOf('F'), new ItemStack(Items.flint_and_steel));
        // Tile recipe
        GameRegistry.addRecipe(new ItemStack(KitchenBlocks.tiles, 2), "IB", "CC", "CC", valueOf('I'), new ItemStack(Items.dye, 1, 0), valueOf('B'), new ItemStack(Items.dye, 1, 15), valueOf('C'), Items.clay_ball);

        /**
         * Knife recipes
         */
        GameRegistry.addShapelessRecipe(new ItemStack(KitchenItems.raw_bacon, 3), new ItemStack(KitchenItems.knife), new ItemStack(Items.porkchop));
        GameRegistry.addShapelessRecipe(new ItemStack(KitchenItems.tomato_slice, 4), new ItemStack(KitchenItems.knife), new ItemStack(KitchenItems.tomato));
        GameRegistry.addShapelessRecipe(new ItemStack(KitchenItems.lettuce_leaf, 2), new ItemStack(KitchenItems.knife), new ItemStack(KitchenItems.lettuce));
        GameRegistry.addShapelessRecipe(new ItemStack(KitchenItems.carrot_slice, 2), new ItemStack(KitchenItems.knife), new ItemStack(Items.carrot));
        GameRegistry.addShapelessRecipe(new ItemStack(KitchenItems.potato_slice, 3), new ItemStack(KitchenItems.knife), new ItemStack(Items.potato));
        GameRegistry.addShapelessRecipe(new ItemStack(KitchenItems.bread_slice, 2), new ItemStack(KitchenItems.knife), new ItemStack(Items.bread));
        GameRegistry.addShapelessRecipe(new ItemStack(KitchenItems.raw_roast_beef, 2), new ItemStack(KitchenItems.knife), new ItemStack(Items.beef));
        GameRegistry.addShapelessRecipe(new ItemStack(KitchenItems.raw_chicken_fillet, 4), new ItemStack(KitchenItems.knife), new ItemStack(Items.chicken));
        GameRegistry.addShapelessRecipe(new ItemStack(KitchenItems.chicken_leg, 2), new ItemStack(KitchenItems.knife), new ItemStack(Items.cooked_chicken));
        GameRegistry.addShapelessRecipe(new ItemStack(KitchenItems.cheese_slice, 2), new ItemStack(KitchenItems.knife), new ItemStack(KitchenItems.cheese));

        GameRegistry.addShapelessRecipe(getJamJarItemStack(Jam.STRAWBERRY, 6), new ItemStack(KitchenItems.cheese_slice), new ItemStack(Items.sugar), new ItemStack(KitchenItems.jam_jar, 1, 0));

        GameRegistry.addShapelessRecipe(new ItemStack(KitchenItems.cheese, 2), new ItemStack(KitchenItems.knife), new ItemStack(Items.milk_bucket));

        GameRegistry.addShapelessRecipe(new ItemStack(KitchenItems.mortar_and_pestle, 1), new ItemStack(KitchenItems.mortar), new ItemStack(KitchenItems.pestle));

        GameRegistry.addRecipe(new ItemStack(KitchenItems.mortar), "S S", " S ", valueOf('S'), Blocks.stone);
        GameRegistry.addRecipe(new ItemStack(KitchenItems.pestle), "S ", " S", valueOf('S'), Blocks.stone);

        switch (ModConfig.getKnifeConfig().knife_recipe)
        {
            case 0:
                GameRegistry.addRecipe(new ShapedOreRecipe(KitchenItems.knife, "I ", " S", valueOf('S'), "stickWood", valueOf('I'), Items.iron_ingot));
                break;
            case 1:
                GameRegistry.addRecipe(new ShapedOreRecipe(KitchenItems.knife, "I", "S", valueOf('S'), "stickWood", valueOf('I'), Items.iron_ingot));
                break;
            case 2:
                GameRegistry.addRecipe(new ShapedOreRecipe(KitchenItems.knife, "IS", valueOf('S'), "stickWood", valueOf('I'), Items.iron_ingot));
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

                GameRegistry.addRecipe(new ShapedOreRecipe(KitchenItems.knife, recipe.toArray()));

                break;
            }
            //
            default:
                GameRegistry.addRecipe(new ShapedOreRecipe(KitchenItems.knife, "I ", " S", valueOf('S'), "stickWood", valueOf('I'), Items.iron_ingot));
                break;
        }

        GameRegistry.addRecipe(new ItemStack(KitchenBlocks.plate, 1, 0), "CBC", " C ", valueOf('C'), Items.clay_ball, valueOf('B'), new ItemStack(Items.dye, 1, 15));

        /**
         * Mortar and Pestle recipes
         */
        GameRegistry.addShapelessRecipe(new ItemStack(KitchenItems.flour, 1), new ItemStack(KitchenItems.mortar_and_pestle, 1), new ItemStack(Items.wheat, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addShapelessRecipe(new ItemStack(KitchenItems.flour, 3), new ItemStack(KitchenItems.mortar_and_pestle, 1), new ItemStack(Items.bread, 1, OreDictionary.WILDCARD_VALUE));

        /**
         * Smelting recipes
         */
        GameRegistry.addSmelting(KitchenItems.raw_bacon, new ItemStack(KitchenItems.bacon, 1, 0), 3.0F);
        GameRegistry.addSmelting(KitchenItems.flour, new ItemStack(KitchenItems.toast, 2, 0), 3.0F);
        GameRegistry.addSmelting(KitchenItems.raw_chicken_fillet, new ItemStack(KitchenItems.chicken_fillet, 1, 0), 3.0F);
        GameRegistry.addSmelting(KitchenItems.raw_roast_beef, new ItemStack(KitchenItems.roast_beef, 1, 0), 3.0F);

        FMLInterModComms.sendMessage("Waila", "register", "dk.mrspring.kitchen.comp.waila.WailaDataProvider.callbackRegister");

        MinecraftForge.EVENT_BUS.register(new SandwichableTooltipEvent());

        KitchenItems.linkToIngredient(KitchenItems.cut_strawberry, Ingredient.STRAWBERRY);
        KitchenItems.linkToIngredient(KitchenItems.cut_apple, Ingredient.APPLE);
		KitchenItems.linkToIngredient(KitchenItems.raw_bacon,Ingredient.BACON);
		KitchenItems.linkToIngredient(KitchenItems.peanut,Ingredient.PEANUT);

		/*JamRecipeRegistry.registerRecipe(Jam.STRAWBERRY, 2, new IngredientStack(Ingredient.STRAWBERRY, 2),Ingredient.SUGAR);
        JamRecipeRegistry.registerRecipe(Jam.APPLE, 2, new IngredientStack(Ingredient.APPLE, 3),Ingredient.SUGAR);*/

    }

    public static ItemStack getJamJarItemStack(Jam jam, int usesLeft)
    {
        ItemStack jamStack = new ItemStack(KitchenItems.jam_jar, 1, 1);

        if (jam == Jam.EMPTY)
        {
            jamStack.setItemDamage(0);
            return jamStack;
        } else
        {
            String jamName = jam.name();
            NBTTagCompound compound = new NBTTagCompound();
            compound.setString("JamType", jamName);
            compound.setInteger("UsesLeft", usesLeft);

            jamStack.setTagInfo("JamInfo", compound);
            return jamStack;
        }
    }
}
