package dk.mrspring.kitchen.api.board;

import dk.mrspring.kitchen.tileentity.TileEntityBoard;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Created by Konrad on 13-05-2015.
 */
public class BoardItemRenderingHandler implements IBoardRenderingHandler
{

    @Override
    public boolean shouldBeUsed(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagCompound, ItemStack rendering)
    {
        return true;
    }

    @Override
    public void render(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagCompound, ItemStack rendering)
    {
        // TODO: Render. See SandwichRender
    }

    @Override
    public double getModelHeight(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagInfo, ItemStack rendering)
    {
        return 0; // TODO: Return height. See SandwichRender
    }
}
