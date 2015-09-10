package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.Kitchen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by MrSpring on 15-12-2014 for TheKitchenMod.
 */
public class ItemCookingBook extends ItemBase
{
    public ItemCookingBook()
    {
        super("cooking_book", true);

        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        player.openGui(Kitchen.instance, 0, world, 0, 0, 0);
        return super.onItemRightClick(stack, world, player);
    }
}
