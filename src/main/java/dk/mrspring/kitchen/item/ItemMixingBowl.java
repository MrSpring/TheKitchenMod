package dk.mrspring.kitchen.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

/**
 * Created by MrSpring on 09-12-2014 for TheKitchenMod.
 */
public class ItemMixingBowl extends ItemBase
{
    public ItemMixingBowl(String name)
    {
        super(name, true);
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack)
    {
        if (itemStack.getItemDamage() == 0)
            return StatCollector.translateToLocal("item.mixing_bowl.empty.name");
        else return StatCollector.translateToLocal("item.mixing_bowl.full.name");
    }
}
