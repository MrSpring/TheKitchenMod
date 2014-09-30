package dk.mrspring.kitchen.api.event;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Created by Konrad on 30-09-2014 for TheKitchenMod.
 */
public interface IOnBoardRightClickedEvent extends IBoardEvent
{
    public void onRightClicked(List<ItemStack> layers,ItemStack rightClicked,NBTTagCompound specialTagInfo);
}
