package dk.mrspring.kitchen.api.event;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.recipe.KnifeRecipes;
import dk.mrspring.kitchen.tileentity.TileEntityBoard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Konrad on 10-05-2015.
 */
public class KnifeItemHandler implements IBoardItemHandler
{
    private static final String SLICE_COUNT = "SliceCount";

    @Override
    public boolean isForItem(TileEntityBoard tileEntityBoard, ItemStack stack, EntityPlayer player)
    {
        System.out.println("Has output: " + String.valueOf(KnifeRecipes.instance().hasOutput(stack)) + ", layer count: " + tileEntityBoard.getLayerCount());
        int lc = tileEntityBoard.getLayerCount();
        return (KnifeRecipes.instance().hasOutput(stack) && lc == 0) || (stack.getItem() == KitchenItems.knife && lc == 1);
    }

    @Override
    public boolean canAdd(TileEntityBoard tileEntityBoard, ItemStack adding, EntityPlayer player)
    {
        if (adding.getItem() == KitchenItems.knife)
        {
            NBTTagCompound compound = tileEntityBoard.getSpecialInfo();
            int sliceCount = 0;
            if (compound.hasKey(SLICE_COUNT))
                sliceCount = compound.getInteger(SLICE_COUNT);
            sliceCount++;
            if (sliceCount > 5)
            {
                ItemStack output = KnifeRecipes.instance().getOutputFor(tileEntityBoard.getTopItem());
                tileEntityBoard.spawnItemInWorld(output);
                tileEntityBoard.clearBoard();
            } else compound.setInteger(SLICE_COUNT, sliceCount);
            return false;
        } else return tileEntityBoard.getLayerCount() == 0;
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
        return false;
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
