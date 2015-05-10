package dk.mrspring.kitchen.api.event;

import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Konrad on 10-05-2015.
 */
public class BasicItemHandler implements IBoardItemHandler
{
    @Override
    public boolean isForItem(ItemStack stack)
    {
        return false;
    }

    @Override
    public boolean canAdd(List<ItemStack> layersOnBoard, ItemStack adding)
    {
        return false;
    }

    @Override
    public void onAdded(List<ItemStack> layersOnBoard, ItemStack added)
    {

    }

    @Override
    public boolean onRightClicked(List<ItemStack> layersOnBoard, ItemStack clicked)
    {
        return false;
    }
}
