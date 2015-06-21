package dk.mrspring.kitchen.api_impl.common.board;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.api.board.IBoardItemHandler;
import dk.mrspring.kitchen.api.board.ICuttingBoard;
import dk.mrspring.kitchen.recipe.KnifeRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

/**
 * Created by Konrad on 10-05-2015.
 */
public class KnifeItemHandler implements IBoardItemHandler
{
    public static final int MAX_SLICE_COUNT = 3;
    public static final String SLICE_COUNT = "SliceCount";

    @Override
    public boolean isForItem(ICuttingBoard tileEntityBoard, ItemStack stack, EntityPlayer player)
    {
        int lc = tileEntityBoard.getLayerCount();
        return (KnifeRecipes.instance().hasOutput(stack) && (lc == 0 || lc == 1)) || (stack.getItem() == KitchenItems.knife && lc == 1);
    }

    @Override
    public boolean canAdd(ICuttingBoard tileEntityBoard, ItemStack adding, EntityPlayer player)
    {
        return tileEntityBoard.getLayerCount() <= 0;
    }

    @Override
    public ItemStack onAdded(ICuttingBoard tileEntityBoard, ItemStack added, EntityPlayer player)
    {
        ItemStack copy = added.copy();
        copy.stackSize = 1;
        added.stackSize--;
        return copy;
    }

    @Override
    public boolean onRightClicked(ICuttingBoard tileEntityBoard, ItemStack clicked, EntityPlayer player)
    {
        if (clicked.getItem() == KitchenItems.knife)
        {
            NBTTagCompound compound = tileEntityBoard.getSpecialInfo();
            int sliceCount = 0;
            if (compound.hasKey(SLICE_COUNT))
                sliceCount = compound.getInteger(SLICE_COUNT);
            sliceCount++;
            if (sliceCount >= MAX_SLICE_COUNT)
            {
                ItemStack output = KnifeRecipes.instance().getOutputFor(tileEntityBoard.getTopItem());
                tileEntityBoard.spawnItemInWorld(output);
                tileEntityBoard.clearBoard();
            } else compound.setInteger(SLICE_COUNT, sliceCount);
            return true;
        } else return false;
    }

    @Override
    public boolean canBeRemoved(ICuttingBoard tileEntityBoard, ItemStack topMostItem, EntityPlayer player)
    {
        return true;
    }

    @Override
    public ItemStack onRemoved(ICuttingBoard tileEntityBoard, ItemStack removed, EntityPlayer player)
    {
        return removed;
    }

    @Override
    public String[] getWailaMessages(ICuttingBoard tileEntityBoard, ItemStack slicing, EntityPlayer player)
    {
        ItemStack result = KnifeRecipes.instance().getOutputFor(slicing);
        return new String[]{StatCollector.translateToLocal("waila.slicing_result") + ": " + result.getDisplayName()};
    }
}
