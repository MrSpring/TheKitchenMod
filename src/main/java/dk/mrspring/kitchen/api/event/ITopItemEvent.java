package dk.mrspring.kitchen.api.event;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Created by Konrad on 30-09-2014 for TheKitchenMod.
 */
public interface ITopItemEvent extends IBoardEvent
{
    public boolean canAddItemOnTop(List<ItemStack> layers, ItemStack tryingToAdd, NBTTagCompound specialTagInfo);

	/***
	 * Gets called when the top item is removed.
	 * @param layers The layers currently on the Board.
	 * @param removed The ItemStack that is being removed.
	 * @param specialTagInfo The special info tag, be sure to null check, since this might be null!
	 * @return Returns the ItemStack that should be spawned in the world when the item is removed from the Board, null if nothing is dropped.
	 */
	public ItemStack getDroppeditem(List<ItemStack> layers, ItemStack removed, NBTTagCompound specialTagInfo);
}
