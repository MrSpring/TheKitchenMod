package dk.mrspring.kitchen;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import dk.mrspring.kitchen.block.*;
import dk.mrspring.kitchen.item.*;

/**
 * Created by MrSpring on 01-12-2014 for TheKitchenMod.
 */
@Mod(modid = ModInfo.MOD_ID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class TheKitchenMod
{
    public static Item knife;
    public static Item bread_slice;
    
    public static Block board;

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
        
        knife = new ItemBase("knife");
        bread_slice = new ItemBreadSlice("bread_slice");
        
        board = new BlockBoard();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        GameRegisterer.registerItem(bread_slice);
        GameRegisterer.registerBlockAndRenderer(board, "board", null);
        
        proxy.registerRenderers();
    }
}
