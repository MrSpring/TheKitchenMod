package dk.mrspring.kitchen.item.food;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.item.food.ItemFoodBase;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.Arrays;

/**
 * Created by Konrad on 25-01-2015.
 */
public class ItemIceCreamableBase extends ItemFoodBase
{
    public static final String ICE_CREAM = "IceCream";

    public ItemIceCreamableBase(String name, int healAmount, boolean canWolfEat, CreativeTabs useDefaultCreativeTab)
    {
        super(name, healAmount, canWolfEat, useDefaultCreativeTab);
    }

    public static boolean hasEqualIceCream(ItemStack stack1, ItemStack stack2)
    {
        if (stack1 == null || stack2 == null) return false;
        if (!(stack1.getItem() instanceof ItemIceCreamableBase || stack2.getItem() instanceof ItemIceCreamableBase))
            return false;
        String[] cream1 = getIceCreamFromStack(stack1), cream2 = getIceCreamFromStack(stack2);
        return Arrays.equals(cream1, cream2);
    }

    public static String[] getIceCreamFromStack(ItemStack stack)
    {
        if (stack == null) return new String[0];
        if (stack.getTagCompound() == null) return new String[0];
        NBTTagCompound compound = stack.getTagCompound();
        if (!compound.hasKey(ICE_CREAM, 9)) return new String[0];
        NBTTagList creamList = compound.getTagList(ICE_CREAM, 8);
        String[] result = new String[creamList.tagCount()];
        for (int i = 0; i < creamList.tagCount(); i++)
            result[i] = creamList.getStringTagAt(i);
        return result;
    }

    @Override
    public int func_150905_g(ItemStack stack)
    {
        int healAmount = super.func_150905_g(stack);

        if (stack.hasTagCompound())
            healAmount += (stack.getTagCompound().getTagList(ICE_CREAM, 8).tagCount());
        return healAmount;
    }
}
