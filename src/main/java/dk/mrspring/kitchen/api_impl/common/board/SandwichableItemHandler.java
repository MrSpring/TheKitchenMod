package dk.mrspring.kitchen.api_impl.common.board;

import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.api.board.IBoardItemHandler;
import dk.mrspring.kitchen.api_impl.common.SandwichableRegistry;
import dk.mrspring.kitchen.tileentity.TileEntityBoard;
import dk.mrspring.kitchen.util.SandwichUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 10-05-2015.
 */
public class SandwichableItemHandler implements IBoardItemHandler
{
    @Override
    public boolean isForItem(TileEntityBoard tileEntityBoard, ItemStack stack, EntityPlayer player)
    {
        return SandwichableRegistry.getInstance().isSandwichable(stack) &&
                (tileEntityBoard.getLayerCount() <= 0 || SandwichUtils.isAllSandwichable(tileEntityBoard.getLayers()));
    }

    @Override
    public boolean canAdd(TileEntityBoard tileEntityBoard, ItemStack adding, EntityPlayer player)
    {
        return true;
    }

    @Override
    public ItemStack onAdded(TileEntityBoard tileEntityBoard, ItemStack added, EntityPlayer player)
    {
        ItemStack copy = added.copy();
        copy.stackSize = 1;
        added.stackSize--;
        return copy;
    }

    @Override
    public boolean onRightClicked(TileEntityBoard tileEntityBoard, ItemStack clicked, EntityPlayer player)
    {
        return true;
    }

    @Override
    public boolean canBeRemoved(TileEntityBoard tileEntityBoard, ItemStack topMostItem, EntityPlayer player)
    {
        return true;
    }

    @Override
    public ItemStack onRemoved(TileEntityBoard tileEntityBoard, ItemStack removed, EntityPlayer player)
    {
        return removed;
    }
}
