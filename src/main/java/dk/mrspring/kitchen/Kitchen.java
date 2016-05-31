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
import dk.mrspring.kitchen.model.ModelBaconCooked;
import dk.mrspring.kitchen.model.ModelBaconRaw;
import dk.mrspring.kitchen.pan.*;
import dk.mrspring.kitchen.recipe.OvenRecipes;
import dk.mrspring.kitchen.recipe.RecipeRegistry;
import dk.mrspring.kitchen.tileentity.*;
import dk.mrspring.kitchen.world.gen.WorldGenWildPlants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

        // Registering renderers
        proxy.registerRenderers();
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

        Jam.registerJam(new Jam("empty", 000000, "null"));
        Jam.registerJam(new Jam("strawberry", 16196364, "kitchen:strawberry_jam"));
        Jam.registerJam(new Jam("apple", 14415786, "kitchen:apple_jam"));
        Jam.registerJam(new Jam("peanut", 9659689, "kitchen:peanut_jam"));

        Ingredient.registerIngredient(new Ingredient("empty", new JamBaseRenderingHandler(new float[]{0, 0, 0}), "empty"));
        Ingredient.registerIngredient(new Ingredient("strawberry", new JamBaseRenderingHandler(new float[]{255F, 60, 53}), "strawberry"));
        Ingredient.registerIngredient(new Ingredient("apple", new JamBaseRenderingHandler(new float[]{224, 255, 163}), "apple"));
        Ingredient.registerIngredient(new Ingredient("peanut", new JamBaseRenderingHandler(new float[]{147, 101, 41}), "peanut"));
        Ingredient.registerIngredient(new Ingredient("bacon", new IIngredientRenderingHandler()
        {
            ModelBaconRaw rawBaconModel = new ModelBaconRaw();
            ModelBaconCooked cookedBaconModel = new ModelBaconCooked();

            @Override
            public boolean translateModel(int cookTime, Ingredient ingredient)
            {
                return true;
            }

            @Override
            public void render(int cookTime, Ingredient ingredient)
            {
                if (cookTime >= 300) cookedBaconModel.simpleRender(0F);
                else rawBaconModel.simpleRender(0F);
            }
        }, new ItemStack(KitchenItems.bacon, 1)));
        Ingredient.registerIngredient(new Ingredient("chicken_fillet", new ItemBaseRenderingHandler(new ItemStack(KitchenItems.raw_chicken_fillet), new ItemStack(KitchenItems.chicken_fillet)), new ItemStack(KitchenItems.chicken_fillet)));

        KitchenItems.linkToIngredient(KitchenItems.jammable_strawberry, "strawberry");
        KitchenItems.linkToIngredient(KitchenItems.cut_apple, "apple");
        KitchenItems.linkToIngredient(KitchenItems.raw_bacon, "bacon");
        KitchenItems.linkToIngredient(KitchenItems.peanut, "peanut");
        KitchenItems.linkToIngredient(KitchenItems.raw_chicken_fillet, "chicken_fillet");

		/*JamRecipeRegistry.registerRecipe(Jam.STRAWBERRY, 2, new IngredientStack(Ingredient.STRAWBERRY, 2),Ingredient.SUGAR);
        JamRecipeRegistry.registerRecipe(Jam.APPLE, 2, new IngredientStack(Ingredient.APPLE, 3),Ingredient.SUGAR);*/

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        OvenRecipes.addFoodRecipes();
    }

    @EventHandler
    public void interCommHandler(FMLInterModComms.IMCEvent event)
    {
        for (FMLInterModComms.IMCMessage message : event.getMessages())
            if (message != null)
                IMCHandler.handleMessage(message);
    }

    public static ItemStack getJamJarItemStack(Jam jam, int usesLeft)
    {
        ItemStack jamStack = new ItemStack(KitchenItems.jam_jar, 1, 1);

        if (jam == Jam.getJam("empty"))
        {
            jamStack.setItemDamage(0);
            return jamStack;
        } else
        {
            String jamName = jam.getName();
            NBTTagCompound compound = new NBTTagCompound();
            compound.setString("JamType", jamName);
            compound.setInteger("UsesLeft", usesLeft);

            jamStack.setTagInfo("JamInfo", compound);
            return jamStack;
        }
    }
}
