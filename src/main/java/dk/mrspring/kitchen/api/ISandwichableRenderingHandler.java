package dk.mrspring.kitchen.api;

import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by MrSpring on 30-09-2014 for TheKitchenMod.
 */
public interface ISandwichableRenderingHandler
{
	public ModelBase getModel(List<ItemStack> itemStackList, int indexInList);

	public int getModelHeight(List<ItemStack> itemStackList, int indexInList);
}
