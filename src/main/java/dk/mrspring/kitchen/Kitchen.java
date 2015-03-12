package dk.mrspring.kitchen;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.api.event.BoardEventRegistry;
import dk.mrspring.kitchen.block.BlockBase;
import dk.mrspring.kitchen.event.ModEventHandler;
import dk.mrspring.kitchen.event.PlayerEvents;
import dk.mrspring.kitchen.gui.GuiHandler;
import dk.mrspring.kitchen.item.ItemBase;
import dk.mrspring.kitchen.model.ModelBaconCooked;
import dk.mrspring.kitchen.model.ModelBaconRaw;
import dk.mrspring.kitchen.model.ModelPancakeCooked;
import dk.mrspring.kitchen.model.ModelPancakeUncooked;
import dk.mrspring.kitchen.pan.*;
import dk.mrspring.kitchen.recipe.OvenRecipes;
import dk.mrspring.kitchen.recipe.RecipeRegistry;
import dk.mrspring.kitchen.recipe.ToasterRecipes;
import dk.mrspring.kitchen.tileentity.*;
import dk.mrspring.kitchen.world.gen.WorldGenWildPlants;
import net.minecraft.client.model.ModelBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = ModInfo.modid, name = ModInfo.name, version = ModInfo.version)
public class Kitchen
{
    @Instance(ModInfo.modid)
    public static Kitchen instance;

    private GuiHandler guiHandler = new GuiHandler();

    @SidedProxy(serverSide = "dk.mrspring.kitchen.CommonProxy", clientSide = "dk.mrspring.kitchen.ClientProxy")
    public static CommonProxy proxy;

    public CreativeTabs tab;
    public CreativeTabs foodTab;

    public OvenRecipes ovenRecipes;
    public ToasterRecipes toasterRecipes;

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event)
    {

        // Loads the mods logger
        ModLogger.initializeLogger(event);

        // Loading the config files
        ModConfig.load(event.getModConfigurationDirectory());

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
        GameRegistry.registerTileEntity(TileEntityWaffleIron.class, "tileEntityWaffleIron");
        GameRegistry.registerTileEntity(TileEntityToaster.class, "tileEntityToaster");


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

        instance.ovenRecipes.load();
        instance.toasterRecipes.load();

        TileEntityWaffleIron.load();

        FMLInterModComms.sendMessage("Waila", "register", "dk.mrspring.kitchen.comp.waila.WailaDataProvider.callbackRegister");
        FMLInterModComms.sendRuntimeMessage(ModInfo.modid, "VersionChecker", "addVersionCheck", "http://www.mrspring.dk/mods/kitchen/vchecker.json");
        FMLInterModComms.sendMessage("cfm", "register", "dk.mrspring.kitchen.comp.furniture.CrayfishFurnitureRegister.registerRecipes");

        MinecraftForge.EVENT_BUS.register(new ModEventHandler());

        Jam.registerJam(new Jam("empty", 000000, "null"));
        Jam.registerJam(new Jam("strawberry", 16196364, "kitchen:strawberry_jam"));
        Jam.registerJam(new Jam("apple", 14415786, "kitchen:apple_jam"));
        Jam.registerJam(new Jam("peanut", 9659689, "kitchen:peanut_jam"));
        Jam.registerJam(new Jam("cocoa", 0x895836, "kitchen:cocoa_jam"));

        Ingredient.registerIngredient(new Ingredient("empty", new JamBaseRenderingHandler(new float[]{0, 0, 0}), "empty"));
        Ingredient.registerIngredient(new Ingredient("strawberry", new JamBaseRenderingHandler(new float[]{255F, 60, 53}), "strawberry"));
        Ingredient.registerIngredient(new Ingredient("apple", new JamBaseRenderingHandler(new float[]{224, 255, 163}), "apple"));
        Ingredient.registerIngredient(new Ingredient("peanut", new JamBaseRenderingHandler(new float[]{147, 101, 41}), "peanut"));
        Ingredient.registerIngredient(new Ingredient("bacon", new IIngredientRenderingHandler()
        {
            ModelBase rawBaconModel = new ModelBaconRaw();
            ModelBase cookedBaconModel = new ModelBaconCooked();

            @Override
            public ModelBase getModel(int boilTime, Ingredient ingredient)
            {
                if (boilTime >= 400)
                    return cookedBaconModel;
                else return rawBaconModel;
            }

            @Override
            public boolean useColorModifier(int boilTime, Ingredient ingredient)
            {
                return false;
            }

            @Override
            public float[] getColorModifier(int boilTime, Ingredient ingredient)
            {
                return new float[0];
            }

            @Override
            public boolean scaleOnPan(int boilTime, Ingredient ingredient)
            {
                return true;
            }
        }, new ItemStack(KitchenItems.bacon, 1))
        {
            @Override
            public void onIngredientFinished(ItemStack result, EntityPlayer player)
            {
                player.triggerAchievement(ModAchievements.bacon);
            }
        });
        Ingredient.registerIngredient(new Ingredient("chicken_fillet", new ItemBaseRenderingHandler(new ItemStack(KitchenItems.raw_chicken_fillet), new ItemStack(KitchenItems.chicken_fillet)), new ItemStack(KitchenItems.chicken_fillet)));
        Ingredient.registerIngredient(new Ingredient("pancake_dough", new IIngredientRenderingHandler()
        {
            ModelBase uncookedModel = new ModelPancakeUncooked();
            ModelBase cookedModel = new ModelPancakeCooked();

            @Override
            public ModelBase getModel(int boilTime, Ingredient ingredient)
            {
                if (boilTime > 400)
                    return cookedModel;
                else return uncookedModel;
            }

            @Override
            public boolean useColorModifier(int boilTime, Ingredient ingredient)
            {
                return false;
            }

            @Override
            public float[] getColorModifier(int boilTime, Ingredient ingredient)
            {
                return new float[0];
            }

            @Override
            public boolean scaleOnPan(int boilTime, Ingredient ingredient)
            {
                return false;
            }
        }, new ItemStack(KitchenItems.pancake))
        {
            @Override
            public boolean canAdd(ItemStack stack)
            {
                if (stack.getTagCompound() != null && stack.getItemDamage() > 0)
                {
                    String mixType = stack.getTagCompound().getString("MixType");
                    if (mixType != null)
                        return mixType.equals("pancake_dough");
                }
                return false;
            }

            @Override
            public void onAdded(ItemStack clickedStack)
            {
                clickedStack.setItemDamage(clickedStack.getItemDamage() - 1);
            }
        });
        Ingredient.registerIngredient(new Ingredient("cocoa", new JamBaseRenderingHandler(new float[]{137, 88, 54}), "cocoa")
        {
            @Override
            public boolean canAdd(ItemStack stack)
            {
                return stack.getItemDamage() == 3;
            }
        });
        Ingredient.registerIngredient(new Ingredient("fried_egg", new ItemBaseRenderingHandler(new ItemStack(KitchenItems.fried_egg), new ItemStack(KitchenItems.fried_egg)), new ItemStack(KitchenItems.fried_egg)));

        KitchenItems.linkToIngredient(KitchenItems.jammable_strawberry, "strawberry");
        KitchenItems.linkToIngredient(KitchenItems.cut_apple, "apple");
        KitchenItems.linkToIngredient(KitchenItems.raw_bacon, "bacon");
        KitchenItems.linkToIngredient(KitchenItems.peanut, "peanut");
        KitchenItems.linkToIngredient(KitchenItems.raw_chicken_fillet, "chicken_fillet");
        KitchenItems.linkToIngredient(KitchenItems.mixing_bowl, "pancake_dough");
        KitchenItems.linkToIngredient(Items.dye, "cocoa");
        KitchenItems.linkToIngredient(Items.egg, "fried_egg");

        ModAchievements.load();
        FMLCommonHandler.instance().bus().register(new PlayerEvents());
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
