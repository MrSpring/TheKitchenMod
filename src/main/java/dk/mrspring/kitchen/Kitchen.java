package dk.mrspring.kitchen;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dk.mrspring.kitchen.common.CommonProxy;
import dk.mrspring.kitchen.common.api.oven.recipe.OvenRecipeHandler;
import dk.mrspring.kitchen.common.api.oven.item.OvenItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import static dk.mrspring.kitchen.ModInfo.*;

/**
 * Created on 07-03-2016 for TheKitchenMod.
 */
@Mod(modid = MOD_ID, name = NAME, version = VERSION)
public class Kitchen
{
    public static final CreativeTabs mainTab = new CreativeTabs("tabKitchen")
    {
        @Override
        public Item getTabIconItem()
        {
            return Items.bread;
        }
    };

    @Mod.Instance(MOD_ID)
    public static Kitchen instance;

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY, modId = MOD_ID)
    public static CommonProxy proxy;

    public static final KitchenItems items = new KitchenItems();
    public static final KitchenBlocks blocks = new KitchenBlocks();
    public static final KitchenRecipes recipes = new KitchenRecipes();

    public static final OvenItemRegistry ovenItems = new OvenItemRegistry();
    public static final OvenRecipeHandler ovenRecipes = new OvenRecipeHandler();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }
}
