package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.Kitchen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created on 04-12-2015 for TheKitchenMod.
 */
public class ItemManual extends ItemBase
{
    public String name;
    public int guiId = -1;

    public ItemManual(String manualName, int guiId)
    {
        super("manual_" + manualName, true);
        this.name = manualName;
        this.guiId = guiId;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (guiId != -1) player.openGui(Kitchen.instance, guiId, world, 0, 0, 0);
        return super.onItemRightClick(stack, world, player);
    }
}
