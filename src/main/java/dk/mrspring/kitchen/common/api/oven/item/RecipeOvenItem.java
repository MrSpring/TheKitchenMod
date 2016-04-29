package dk.mrspring.kitchen.common.api.oven.item;

import dk.mrspring.kitchen.common.api.oven.IOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created on 03-04-2016 for TheKitchenMod.
 */
public class RecipeOvenItem extends OvenItem
{
    final String INPUT_ITEM = "InputItem";

    @Override
    public boolean canAddToOven(EntityPlayer player, IOven oven, int[] slots)
    {
        if (slots.length >= 1 && player.getCurrentEquippedItem() != null)
        {
            OvenItemStack stack = new OvenItemStack(this);
            ItemStack item = player.getCurrentEquippedItem().copy();
            item.stackSize = 1;
            player.getCurrentEquippedItem().stackSize--;
            stack.getCompound().setTag(INPUT_ITEM, item.writeToNBT(new NBTTagCompound()));
            oven.fillSlots(stack, slots[0]);
            return true;
        } else return false;
    }

    @Override
    public boolean onItemRightClick(EntityPlayer player, IOven oven, OvenItemStack stack, int slot)
    {
        System.out.println("Right click");
        if (player.getCurrentEquippedItem() == null)
        {
            NBTTagCompound itemStackCompound = stack.getCompound().getCompoundTag(INPUT_ITEM);
            ItemStack itemStack = ItemStack.loadItemStackFromNBT(itemStackCompound);
            oven.spawnItem(itemStack);
            oven.clearSlots(stack, slot);
            oven.markForUpdate();
            return true;
        } else return false;
    }

    @Override
    public void writeRenderingInfoToClient(IOven oven, OvenItemStack stack, int slot, NBTTagCompound compound)
    {
        compound.setString(RENDER_AS, "ItemStack");
        NBTTagCompound itemCompound = stack.getCompound().getCompoundTag(INPUT_ITEM);
        compound.setTag("RenderItemStack", itemCompound);
    }
}
