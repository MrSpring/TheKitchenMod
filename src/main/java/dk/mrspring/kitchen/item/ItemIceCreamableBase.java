package dk.mrspring.kitchen.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 25-01-2015.
 */
public class ItemIceCreamableBase extends ItemFoodBase
{
    public ItemIceCreamableBase(String name, int healAmount, boolean canWolfEat, CreativeTabs useDefaultCreativeTab)
    {
        super(name, healAmount, canWolfEat, useDefaultCreativeTab);
    }

    @Override
    public int func_150905_g(ItemStack stack)
    {
        int healAmount = super.func_150905_g(stack);

        if (stack.hasTagCompound())
            healAmount += (stack.getTagCompound().getTagList("IceCream", 8).tagCount());
        return healAmount;
    }
}
