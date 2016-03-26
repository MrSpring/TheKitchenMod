package dk.mrspring.kitchen.common.util;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * Created on 07-03-2016 for TheKitchenMod.
 */
public class RegistryUtils
{
    public static void registerBlock(Block block)
    {
        registerBlock(block, block.getUnlocalizedName().substring(5));
    }

    public static void registerBlock(Block block, String name)
    {
        GameRegistry.registerBlock(block, name);
    }

    public static void registerItem(Item item)
    {
        registerItem(item, item.getUnlocalizedName().substring(5));
    }

    public static void registerItem(Item item, String name)
    {
        GameRegistry.registerItem(item, name);
    }
}
