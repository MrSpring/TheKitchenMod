package dk.mrspring.kitchen.api;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MrSpring on 30-09-2014 for TheKitchenMod.
 */
public class SandwichableRenderingRegistry
{
	static Map<String, ISandwichableRenderingHandler> renderers = new HashMap<String, ISandwichableRenderingHandler>();

	public static ISandwichableRenderingHandler getRenderingHandlerFor(String itemName)
	{
		if (renderers.containsKey(itemName))
			return renderers.get(itemName);
		else return new ISandwichableRenderingHandler()
		{
			@Override
			public ModelBase getModel(List<ItemStack> itemStackList, int indexInList)
			{
				return null;
			}

			@Override
			public int getModelHeight(List<ItemStack> itemStackList, int indexInList)
			{
				return 1;
			}
		};
	}

	public static ISandwichableRenderingHandler getRenderingHandlerFor(ItemStack item)
	{
		String key = GameRegistry.findUniqueIdentifierFor(item.getItem()).toString();
		return getRenderingHandlerFor(key);
	}

	public static void registerRenderingHandler(String itemName, ISandwichableRenderingHandler renderingHandler)
	{
		if (!renderers.containsKey(itemName))
			renderers.put(itemName, renderingHandler);
	}

	public static void registerRenderingHandler(Item item, ISandwichableRenderingHandler renderingHandler)
	{
		registerRenderingHandler(GameRegistry.findUniqueIdentifierFor(item).toString(), renderingHandler);
	}

	public static void registerRenderingHandler(Block block, ISandwichableRenderingHandler renderingHandler)
	{
		registerRenderingHandler(GameRegistry.findUniqueIdentifierFor(block).toString(), renderingHandler);
	}
}
