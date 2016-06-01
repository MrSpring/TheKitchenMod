package dk.mrspring.kitchen;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.api.event.BoardEventRegistry;
import dk.mrspring.kitchen.block.BlockBase;
import dk.mrspring.kitchen.event.SandwichableTooltipEvent;
import dk.mrspring.kitchen.item.ItemBase;
import dk.mrspring.kitchen.pan.Ingredient;
import dk.mrspring.kitchen.pan.Jam;
import dk.mrspring.kitchen.recipe.OvenRecipes;
import dk.mrspring.kitchen.recipe.RecipeRegistry;
import dk.mrspring.kitchen.tileentity.*;
import dk.mrspring.kitchen.world.gen.WorldGenWildPlants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

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
        // Loads the mods logger
        ModLogger.initializeLogger(event);

        // Loading the config files
        ModConfig.load(event.getModConfigurationDirectory(), event.getSide());
        proxy.getConfigs();

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
        ItemBase.load();
        BlockBase.load();
        // Registers the default Board events
        BoardEventRegistry.registerDefaultEvents();
    }

    @EventHandler
    public static void init(FMLInitializationEvent event)
    {
        ModLogger.print(ModLogger.INFO, "Loading Custom Oven recipes...");
        // Loading Custom Oven recipes
        OvenRecipes.load();

        // Registering the Lettuce world generator
        GameRegistry.registerWorldGenerator(new WorldGenWildPlants(), 1);

        /**
         * RECIPES
         */
        RecipeRegistry.registerRecipes();

        FMLInterModComms.sendMessage("Waila", "register", "dk.mrspring.kitchen.comp.waila.WailaDataProvider.callbackRegister");
        FMLInterModComms.sendRuntimeMessage(ModInfo.modid, "VersionChecker", "addVersionCheck", "http://www.mrspring.dk/mods/kitchen/vchecker.json");
        FMLInterModComms.sendMessage("cfm", "register", "dk.mrspring.kitchen.comp.furniture.CrayfishFurnitureRegister.registerRecipes");

        MinecraftForge.EVENT_BUS.register(new SandwichableTooltipEvent());

        Jam.registerJam(new Jam("strawberry", 16196364, "kitchen:strawberry_jam"));
        Jam.registerJam(new Jam("apple", 14415786, "kitchen:apple_jam"));
        Jam.registerJam(new Jam("peanut", 9659689, "kitchen:peanut_jam"));

        Ingredient.registerIngredient(new Ingredient("strawberry", "jam.strawberry.name", "strawberry"));
        Ingredient.registerIngredient(new Ingredient("apple", "jam.apple.name", "apple"));
        Ingredient.registerIngredient(new Ingredient("peanut", "jam.peanut.name", "peanut"));
        Ingredient.registerIngredient(new Ingredient("bacon", "ingredient.bacon.name", new ItemStack(KitchenItems.bacon, 1)));
        Ingredient.registerIngredient(new Ingredient("chicken_fillet", "ingredient.chicken_fillet.name", KitchenItems.chicken_fillet));

        KitchenItems.linkToIngredient(KitchenItems.jammable_strawberry, "strawberry");
        KitchenItems.linkToIngredient(KitchenItems.cut_apple, "apple");
        KitchenItems.linkToIngredient(KitchenItems.raw_bacon, "bacon");
        KitchenItems.linkToIngredient(KitchenItems.peanut, "peanut");
        KitchenItems.linkToIngredient(KitchenItems.raw_chicken_fillet, "chicken_fillet");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        OvenRecipes.addFoodRecipes();

        // Registering renderers
        proxy.registerRenderers();
    }

    @EventHandler
    public void interCommHandler(FMLInterModComms.IMCEvent event)
    {
        for (FMLInterModComms.IMCMessage message : event.getMessages())
            if (message != null)
                IMCHandler.handleMessage(message);
    }
}
