package dk.mrspring.kitchen;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.api_impl.common.registry.SandwichableRegistry;
import dk.mrspring.kitchen.block.BlockBase;
import dk.mrspring.kitchen.comp.nei.NEIKitchenConfig;
import dk.mrspring.kitchen.event.ModEventHandler;
import dk.mrspring.kitchen.gui.GuiHandler;
import dk.mrspring.kitchen.item.ItemBase;
import dk.mrspring.kitchen.recipe.*;
import dk.mrspring.kitchen.tileentity.*;
import dk.mrspring.kitchen.world.gen.WorldGenWildPlants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = ModInfo.modid, name = ModInfo.name, version = ModInfo.version)
public class Kitchen
{
    @Instance(ModInfo.modid)
    public static Kitchen instance;
    @SidedProxy(serverSide = "dk.mrspring.kitchen.CommonProxy", clientSide = "dk.mrspring.kitchen.ClientProxy")
    public static CommonProxy proxy;
    public CreativeTabs tab;
    public CreativeTabs foodTab;
    public OvenRecipes ovenRecipes;
    public ToasterRecipes toasterRecipes;
    public KnifeRecipes knifeRecipes;
    public FryingPanRecipes fryingPanRecipes;
    public FryingPanJamRecipes fryingPanJamRecipes;
    public GrinderRecipeHandler grinderRecipes;
    private GuiHandler guiHandler = new GuiHandler();

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
                return KitchenItems.knife;
            }
        };

        instance.foodTab = new CreativeTabs("tabKitchenFood")
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
        GameRegistry.registerTileEntity(TileEntityWaffleIron.class, "kitchen.tileEntityWaffleIron");
        GameRegistry.registerTileEntity(TileEntityToaster.class, "kitchen.tileEntityToaster");
        GameRegistry.registerTileEntity(TileEntityCraftingCabinet.class, "kitchen.tileEntityCraftingCabinet");
        GameRegistry.registerTileEntity(TileEntityMuffinTray.class, "kitchen.muffinTray");
        GameRegistry.registerTileEntity(TileEntityGrinder.class, "kitchen.grinder");


        // Loading Blocks and Items
        ItemBase.load();
        BlockBase.load();

        // Registering renderers
        proxy.registerRenderers();
    }

    @EventHandler
    public static void init(FMLInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, instance.guiHandler);

        ModLogger.print(ModLogger.INFO, "Loading Custom Oven recipes...");

        // Registering the Lettuce world generator
        GameRegistry.registerWorldGenerator(new WorldGenWildPlants(), 1);

        /**
         * RECIPES
         */
        RecipeRegistry.registerRecipes();

        // Loads the recipe handlers
        instance.ovenRecipes = new OvenRecipes();
        instance.toasterRecipes = new ToasterRecipes();
        instance.knifeRecipes = new KnifeRecipes();
        instance.fryingPanRecipes = new FryingPanRecipes();
        instance.fryingPanJamRecipes = new FryingPanJamRecipes();
        instance.grinderRecipes = new GrinderRecipeHandler();

        instance.ovenRecipes.load();
        instance.toasterRecipes.load();
        instance.knifeRecipes.load();
        instance.fryingPanRecipes.load();
        instance.fryingPanJamRecipes.load();
        instance.grinderRecipes.load();

        TileEntityWaffleIron.load();

        FMLInterModComms.sendMessage("Waila", "register", "dk.mrspring.kitchen.comp.waila.WailaCompatibility.callbackRegister");
        FMLInterModComms.sendRuntimeMessage(ModInfo.modid, "VersionChecker", "addVersionCheck", "http://www.mrspring.dk/mods/kitchen/vchecker.json");
        FMLInterModComms.sendMessage("cfm", "register", "dk.mrspring.kitchen.comp.furniture.CrayfishFurnitureRegister.registerRecipes");

        MinecraftForge.EVENT_BUS.register(new ModEventHandler());

        if (Loader.isModLoaded("NotEnoughItems"))
        {
            try
            {
                codechicken.nei.NEIModContainer.plugins.add(new NEIKitchenConfig());
            } catch (Exception e)
            {
                ModLogger.print(ModLogger.ERROR, "Failed loading compatibility with NEI:", e);
            }
        }

        SandwichableRegistry.getInstance().loadFromConfig(ModConfig.getSandwichConfig());
    }

    @EventHandler
    public void interCommHandler(FMLInterModComms.IMCEvent event)
    {
        for (FMLInterModComms.IMCMessage message : event.getMessages())
            if (message != null)
                IMCHandler.handleMessage(message);
    }
}
