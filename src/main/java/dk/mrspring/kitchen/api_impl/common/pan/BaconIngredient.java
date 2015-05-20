package dk.mrspring.kitchen.api_impl.common.pan;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.api.pan.IFryingPan;
import dk.mrspring.kitchen.api.pan.IIngredient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 20-05-2015.
 */
public class BaconIngredient implements IIngredient
{
    @Override
    public String getName()
    {
        return "bacon";
    }

    @Override
    public String getLocalizedName()
    {
        return "Bacon";
    }

    @Override
    public boolean isForItem(IFryingPan pan, ItemStack clicked, EntityPlayer player)
    {
        return clicked.getItem() == KitchenItems.raw_bacon;
    }

    @Override
    public boolean canAdd(IFryingPan pan, ItemStack adding, EntityPlayer player)
    {
        return adding.getItem() == KitchenItems.raw_bacon && pan.getIngredient() == null;
    }

    @Override
    public void onRightClicked(IFryingPan pan, ItemStack clicked, EntityPlayer player)
    {

    }

    @Override
    public int getCookTime(IFryingPan pan)
    {
        return 0;
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
