package dk.mrspring.kitchen;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemBlock;

public class GameRegisterer
{
	public static void registerBlock(Block block, Class<? extends ItemBlock> itemBlock, String name)
	{
		GameRegistry.registerBlock(block, itemBlock, name);
	}

	public static void registerBlock(Block block, String name)
	{
		GameRegistry.registerBlock(block, name);
	}
	
	public static void registerBlock(Block block)
	{
		registerBlock(block, block.getUnlocalizedName().replace("tile.", ""));
	}
	
	
	public static void registerItem(Item item, String name)
	{
		GameRegistry.registerItem(item, name);
	}
	
	public static void registerItem(Item item)
	{
		registerItem(item, item.getUnlocalizedName().replace("item.", ""));
	}
	
	
	public static Block findBlock(String modId, String name)
	{
		return GameRegistry.findBlock(modId, name);
	}
	
	public static Block findBlock(String name)
	{
		return findBlock(ModInfo.modid, name);
	}
	
	
	public static Item findItem(String modId, String name)
	{
		return GameRegistry.findItem(modId, name);
	}
	
	public static Item findItem(String name)
	{
		return findItem(ModInfo.modid, name);
	}
}
