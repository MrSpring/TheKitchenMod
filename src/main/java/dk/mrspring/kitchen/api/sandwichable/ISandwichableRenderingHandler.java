package dk.mrspring.kitchen.api.sandwichable;

import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Created by MrSpring on 30-09-2014 for TheKitchenMod.
 */
public interface ISandwichableRenderingHandler
{
	/***
	 * @param itemStacks The list of items. Used by Bread Slice, to get separate top and bottom models.
	 * @param indexInList The index of the item the model is coming from. Used by Bread Slice, to get separate top and bottom models.
	 * @return Returns the model associated with the rendering handler.
	 */
	ModelBase getModel(ItemStack[] itemStacks, int indexInList, NBTTagCompound specialTagInfo);

	/***
	 * @param itemStacks The list of items. Used by Bread Slice, to get separate top and bottom model heights.
	 * @param indexInList The index of the item the model is coming from. Used by Bread Slice, to get separate top and bottom model heights.
	 * @return Returns the height of the model.
	 */
	double getModelHeight(ItemStack[] itemStacks, int indexInList, NBTTagCompound specialTagInfo);
}
