package dk.mrspring.kitchen.api_impl.common.oven;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.api.oven.IOven;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 27-07-2015.
 */
public class BurgerBunOvenItem extends RecipeOvenItem
{
    @Override
    public String getName()
    {
        return super.getName() + ":burger_bun";
    }

    @Override
    protected int getMaxOvenStackSize(IOven oven, ItemStack stack, int slot)
    {
        return 1;
    }

    @Override
    public boolean isForItem(IOven oven, ItemStack item, EntityPlayer player, boolean[] freeSlots)
    {
        return super.isForItem(oven, item, player, freeSlots) && ItemUtils.item(item, KitchenItems.raw_burger_bun);
    }

    @Override
    public int getCookTime(IOven oven)
    {
        return 400;
    }
}
