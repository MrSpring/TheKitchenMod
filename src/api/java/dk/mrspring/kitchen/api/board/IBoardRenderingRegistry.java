package dk.mrspring.kitchen.api.board;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Created by Konrad on 13-05-2015.
 */
public interface IBoardRenderingRegistry
{
    /**
     * Registers a rendering handler.
     *
     * @param handler The handler to register.
     */
    void registerRenderingHandler(IBoardRenderingHandler handler);


    IBoardRenderingHandler getHandlerFor(List<ItemStack> layers, int index, NBTTagCompound compound, ItemStack rendering);
}
