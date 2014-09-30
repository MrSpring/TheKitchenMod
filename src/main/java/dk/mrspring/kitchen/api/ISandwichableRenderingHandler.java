package dk.mrspring.kitchen.api;

import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by MrSpring on 30-09-2014 for TheKitchenMod.
 */
public interface ISandwichableRenderingHandler
{
	/***
	 * @param itemStackList The list of items. Used by Bread Slice, to get separate top and bottom models.
	 * @param indexInList The index of the item the model is coming from. Used by Bread Slice, to get separate top and bottom models.
	 * @return Returns the model associated with the rendering handler.
	 */
	public ModelBase getModel(List<ItemStack> itemStackList, int indexInList);

	/***
	 * @param itemStackList The list of items. Used by Bread Slice, to get separate top and bottom model heights.
	 * @param indexInList The index of the item the model is coming from. Used by Bread Slice, to get separate top and bottom model heights.
	 * @return Returns the height of the model.
	 */
	public int getModelHeight(List<ItemStack> itemStackList, int indexInList);
}
