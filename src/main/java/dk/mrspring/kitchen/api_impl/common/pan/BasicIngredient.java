package dk.mrspring.kitchen.api_impl.common.pan;

import dk.mrspring.kitchen.api.pan.IFryingPan;
import dk.mrspring.kitchen.api.pan.IIngredient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 14-05-2015.
 */
public class BasicIngredient implements IIngredient
{
    @Override
    public String getName()
    {
        return "empty";
    }

    @Override
    public String getLocalizedName()
    {
        return "Empty";
    }

    @Override
    public boolean isForItem(IFryingPan pan, ItemStack clicked, EntityPlayer player)
    {
        return false;
    }

    @Override
    public boolean canAdd(IFryingPan pan, ItemStack adding, EntityPlayer player)
    {
        return false;
    }

    @Override
    public void onRightClicked(IFryingPan pan, ItemStack clicked, EntityPlayer player)
    {

    }

    @Override
    public int getCookTime(IFryingPan pan)
    {
        return 200;
    }

    @Override
    public boolean canBeRemoved(IFryingPan pan, EntityPlayer player)
    {
        return false;
    }

    @Override
    public ItemStack onRemoved(IFryingPan pan, ItemStack clicked, EntityPlayer player)
    {
        return null;
    }
}
