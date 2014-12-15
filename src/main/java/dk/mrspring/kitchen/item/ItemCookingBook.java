package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.Kitchen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by MrSpring on 15-12-2014 for TheKitchenMod.
 */
public class ItemCookingBook extends ItemBase
{
    public ItemCookingBook()
    {
        super("cooking_book", true);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        p_77659_3_.openGui(Kitchen.instance, 0, p_77659_2_, 0, 0, 0);
        return super.onItemRightClick(p_77659_1_, p_77659_2_, p_77659_3_);
    }
}
