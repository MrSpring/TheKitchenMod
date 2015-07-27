package dk.mrspring.kitchen.api_impl.common.oven;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.api.oven.IOven;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 28-07-2015.
 */
public class MuffinTrayItem extends RecipeOvenItem
{
    @Override
    public String getName()
    {
        return super.getName() + ":muffin_tray";
    }

    @Override
    protected int getMaxOvenStackSize(IOven oven, ItemStack stack, int slot)
    {
        return 1;
    }

    @Override
    public boolean itemOverride(ItemStack item)
    {
        return ItemUtils.item(item, KitchenItems.empty_muffin_tray);
    }

    @Override
    public int getSize(IOven oven)
    {
        return 4;
    }

    @Override
    public boolean consecutive(IOven oven)
    {
        return true;
    }
}
