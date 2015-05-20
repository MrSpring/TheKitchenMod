package dk.mrspring.kitchen.util;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.api.stack.MixingBowlStack;
import dk.mrspring.kitchen.api.stack.Stack;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 15-05-2015.
 */
public class StackUtils
{
    public static Stack fromItemStack(ItemStack stack)
    {
        if (stack.getItem() == KitchenItems.mixing_bowl)
        {
            int usesLeft = stack.getItemDamage();
            String mixType = "";
            if (stack.hasTagCompound())
                mixType = stack.getTagCompound().getString("MixType");
            else usesLeft = 0;
            return new MixingBowlStack(mixType, usesLeft);
        } else return new Stack(stack);
    }
}
