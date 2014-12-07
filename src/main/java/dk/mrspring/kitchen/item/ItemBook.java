package dk.mrspring.kitchen.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by MrSpring on 06-12-2014 for TheKitchenMod.
 */
public class ItemBook extends ItemBase
{
    public ItemBook(String name)
    {
        super(name, true);
    }

    @Override
    public boolean isFull3D()
    {
        return true;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        System.out.println("Clicking!");
        return true;
    }
}
