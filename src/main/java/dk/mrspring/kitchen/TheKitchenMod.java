package dk.mrspring.kitchen;

import scala.actors.threadpool.Arrays;
import dk.mrspring.kitchen.item.ItemBase;
import dk.mrspring.kitchen.item.ItemBreadSlice;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by MrSpring on 01-12-2014 for TheKitchenMod.
 */
@Mod(modid = ModInfo.MOD_ID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class TheKitchenMod
{
    public static Item knife;
    public static Item bread_slice;

    @SidedProxy(clientSide = "dk.mrspring.kitchen.ClientProxy", serverSide = "dk.mrspring.kitchen.CommonProxy")
    public static CommonProxy proxy;

    public CreativeTabs baseTab;

    @Mod.Instance
    public static TheKitchenMod instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        baseTab = new CreativeTabs("tabKitchen")
        {
            @Override
            public Item getTabIconItem()
            {
                return Items.acacia_door;
            }
        };
        knife = new ItemBase("knife", true);
        bread_slice = new ItemBreadSlice("bread_slice");

//        GameRegistry.registerItem(knife, "knife");
//        GameRegistry.registerItem(bread_slice, "bread_slice");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {

        GameRegisterer.registerItem(knife);
        GameRegisterer.registerItem(bread_slice);
        proxy.registerRenderers();
    }
}
