package dk.mrspring.kitchen.api_impl.common.board;

import dk.mrspring.kitchen.api.board.IBoardItemHandler;
import dk.mrspring.kitchen.api.board.ICuttingBoard;
import dk.mrspring.kitchen.api.sandwichable.ISandwichable;
import dk.mrspring.kitchen.api_impl.common.SandwichableRegistry;
import dk.mrspring.kitchen.util.SandwichUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

/**
 * Created by Konrad on 10-05-2015.
 */
public class SandwichableItemHandler implements IBoardItemHandler
{
    @Override
    public boolean isForItem(ICuttingBoard tileEntityBoard, ItemStack stack, EntityPlayer player)
    {
        return SandwichableRegistry.getInstance().isSandwichable(stack) &&
                (tileEntityBoard.getLayerCount() <= 0 || SandwichUtils.isAllSandwichable(tileEntityBoard.getLayers()));
    } // TODO: Fix for jam

    @Override
    public boolean canAdd(ICuttingBoard tileEntityBoard, ItemStack adding, EntityPlayer player)
    {
        return true;
    }

    @Override
    public ItemStack onAdded(ICuttingBoard tileEntityBoard, ItemStack added, EntityPlayer player)
    {
        ISandwichable sandwichable = SandwichableRegistry.getInstance().getSandwichableForItem(added);
        ItemStack stack = sandwichable.getBoardStack(added);
        sandwichable.onAdded(added);
        stack.stackSize = 1;
        return stack;
    }

    @Override
    public boolean onRightClicked(ICuttingBoard tileEntityBoard, ItemStack clicked, EntityPlayer player)
    {
        return false;
    }

    @Override
    public boolean canBeRemoved(ICuttingBoard tileEntityBoard, ItemStack topMostItem, EntityPlayer player)
    {
        return true;
    }

    @Override
    public ItemStack onRemoved(ICuttingBoard tileEntityBoard, ItemStack removed, EntityPlayer player)
    {
        ISandwichable sandwichable = SandwichableRegistry.getInstance().getSandwichableForItem(removed);
        return sandwichable.getDropItem() ? removed : null;
    }

    @Override
    public String[] getWailaMessages(ICuttingBoard tileEntityBoard, ItemStack top, EntityPlayer player)
    {
        boolean isReady = SandwichUtils.isSandwichReady(tileEntityBoard.getLayers());
        return new String[]{StatCollector.translateToLocal("waila.is_sandwich_ready") + ": " + StatCollector.translateToLocal("waila." + isReady)};
    }
}
