package dk.mrspring.kitchen.api.board;

import dk.mrspring.kitchen.tileentity.TileEntityBoard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 10-05-2015.
 */
public class BasicItemHandler implements IBoardItemHandler
{
    @Override
    public boolean isForItem(TileEntityBoard board, ItemStack stack, EntityPlayer player)
    {
        return false;
    }

    @Override
    public boolean canAdd(TileEntityBoard tileEntityBoard, ItemStack adding, EntityPlayer player)
    {
        return false;
    }

    @Override
    public ItemStack onAdded(TileEntityBoard tileEntityBoard, ItemStack added, EntityPlayer player)
    {
        return null;
    }

    @Override
    public boolean onRightClicked(TileEntityBoard tileEntityBoard, ItemStack clicked, EntityPlayer player)
    {
        return true;
    }

    @Override
    public boolean canBeRemoved(TileEntityBoard tileEntityBoard, ItemStack topMostItem, EntityPlayer player)
    {
        return false;
    }

    @Override
    public ItemStack onRemoved(TileEntityBoard tileEntityBoard, ItemStack removed, EntityPlayer player)
    {
        return null;
    }
}
