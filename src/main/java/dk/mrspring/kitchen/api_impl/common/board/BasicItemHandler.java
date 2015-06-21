package dk.mrspring.kitchen.api_impl.common.board;

import dk.mrspring.kitchen.api.board.IBoardItemHandler;
import dk.mrspring.kitchen.api.board.ICuttingBoard;
import dk.mrspring.kitchen.tileentity.TileEntityBoard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 10-05-2015.
 */
public class BasicItemHandler implements IBoardItemHandler
{
    @Override
    public boolean isForItem(ICuttingBoard board, ItemStack stack, EntityPlayer player)
    {
        return false;
    }

    @Override
    public boolean canAdd(ICuttingBoard board, ItemStack adding, EntityPlayer player)
    {
        return false;
    }

    @Override
    public ItemStack onAdded(ICuttingBoard board, ItemStack added, EntityPlayer player)
    {
        return null;
    }

    @Override
    public boolean onRightClicked(ICuttingBoard board, ItemStack clicked, EntityPlayer player)
    {
        return false;
    }

    @Override
    public boolean canBeRemoved(ICuttingBoard board, ItemStack topMostItem, EntityPlayer player)
    {
        return false;
    }

    @Override
    public ItemStack onRemoved(ICuttingBoard board, ItemStack removed, EntityPlayer player)
    {
        return null;
    }

    @Override
    public String[] getWailaMessages(ICuttingBoard tileEntityBoard, ItemStack top, EntityPlayer player)
    {
        return null;
    }
}
