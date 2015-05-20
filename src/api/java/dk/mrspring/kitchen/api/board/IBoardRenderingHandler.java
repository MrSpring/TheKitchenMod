package dk.mrspring.kitchen.api.board;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Created by MrSpring on 30-09-2014 for TheKitchenMod.
 */
public interface IBoardRenderingHandler
{
    /**
     * @param layers             The list of layers being rendered.
     * @param indexInList        The index of the item the model is coming from. Used by Bread Slice, to get separate
     *                           top and bottom model and heights.
     * @param specialTagCompound The tag that holds information for the top most layer.
     * @return Returns true if this render handler should be used to render the current layer. False if not.
     */
    boolean shouldBeUsed(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagCompound, ItemStack rendering);

    /**
     * Called to render using this render handler. Is already positioned at the right height.
     *
     * @param layers             The list of layers being rendered.
     * @param indexInList        Where in the list this layer is being rendered.
     * @param specialTagCompound The tag that holds information for the top most layer.
     */
    void render(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagCompound, ItemStack rendering);

    /**
     * @param layers         The list of layers being rendered.
     * @param indexInList    The index of the item the model is coming from. Used by Bread Slice, to get separate top
     *                       and bottom model heights.
     * @param specialTagInfo A tag compound used to store information for the top most layer. Is reset whenever a new
     *                       item is placed on top, or the top item is removed.
     * @return Returns the height of the model.
     */
    double getModelHeight(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagInfo, ItemStack rendering);
}
