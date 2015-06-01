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
        return "kitchen:raw_bacon";
    }

    @Override
    public String getLocalizedName()
    {
        return "Bacon";
    }

    @Override
    public boolean isForItem(IFryingPan pan, ItemStack clicked, EntityPlayer player)
    {
        System.out.println("Clicked with: " + clicked.getDisplayName());
        return clicked.getItem() == KitchenItems.raw_bacon;
    }

    @Override
    public boolean canAdd(IFryingPan pan, ItemStack adding, EntityPlayer player)
    {
        System.out.println("Clicked with: " + adding.getDisplayName());
        return adding.getItem() == KitchenItems.raw_bacon && pan.getIngredient() == null;
    }

    @Override
    public void onAdded(IFryingPan pan, ItemStack added, EntityPlayer player)
    {
        added.stackSize--;
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
        return pan.isFinished();
    }

    @Override
    public ItemStack onRemoved(IFryingPan pan, ItemStack clicked, EntityPlayer player)
    {
        return new ItemStack(KitchenItems.bacon);
    }

    @Override
    public boolean readyToCook(IFryingPan pan)
    {
        return true;
    }
}
