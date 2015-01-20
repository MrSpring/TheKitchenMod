package dk.mrspring.kitchen;

import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by MrSpring on 18-01-2015 for TheKitchenMod.
 */
public class GameRegisterer
{
    public static void registerItemAndRenderer(Item item, String itemName, ModelResourceLocation modelResourceLocation)
    {
    	registerItem(item, itemName);
        TheKitchenMod.proxy.registerItemRenderer(item, 0, modelResourceLocation);
    }
    
    public static void registerItemAndRenderer(Item item, String itemName, String modelName)
    {
    	registerItem(item, itemName);
    	TheKitchenMod.proxy.registerItemRenderer(item, 0, modelName);
    }
    
    public static void registerItemAndRenderer(Item item, ModelResourceLocation modelResourceLocation)
    {
    	registerItemAndRenderer(item, item.getUnlocalizedName().replace("item.", ""), modelResourceLocation);
    }
    
    public static void registerItemAndRenderer(Item item, String modelName)
    {
    	registerItemAndRenderer(item, item.getUnlocalizedName().replace("item.", ""), modelName);
    }
    
    
    public static void registerItem(Item item, String itemName)
    {
    	GameRegistry.registerItem(item, itemName);
    }
    
    public static void registerItem(Item item)
    {
    	registerItem(item, item.getUnlocalizedName().replace("item.", ""));
    }

    
    public static void registerBlockAndRenderer(Block block, String blockName, ModelResourceLocation location)
    {
    	GameRegistry.registerBlock(block, blockName);
    	TheKitchenMod.proxy.registerBlockRenderer(block, 0, location);
    }
}
