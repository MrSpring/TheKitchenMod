package dk.mrspring.kitchen.api.grinder;

import dk.mrspring.kitchen.KitchenItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Konrad on 02-06-2015.
 */
public enum GrinderOpening
{
    FLAT(KitchenItems.flat_funnel),
    ROUND(KitchenItems.round_funnel),
    SQUARE(KitchenItems.square_funnel);

    Item linkedTo;

    GrinderOpening(Item item)
    {
        this.linkedTo = item;
    }

    public static GrinderOpening getOpening(ItemStack stack)
    {
        return stack != null ? getOpening(stack.getItem()) : null;
    }

    public static GrinderOpening getOpening(Item item)
    {
        if (item == null)
            return null;
        for (GrinderOpening opening : values())
            if (opening.linkedTo == item)
                return opening;
        return null;
    }
}
