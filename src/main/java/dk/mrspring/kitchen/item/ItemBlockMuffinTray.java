package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.tileentity.TileEntityMuffinTray;
import dk.mrspring.nbtjson.NBTType;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.List;

import static dk.mrspring.kitchen.tileentity.TileEntityMuffinTray.ITEM_LIST;
import static dk.mrspring.kitchen.tileentity.TileEntityMuffinTray.MUFFIN_TRAY_INFO;

/**
 * Created on 14-11-2015 for TheKitchenMod.
 */
public class ItemBlockMuffinTray extends ItemBlock
{
    public ItemBlockMuffinTray(Block p_i45328_1_)
    {
        super(p_i45328_1_);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        if (stack.hasTagCompound())
            if (stack.getTagCompound().hasKey(MUFFIN_TRAY_INFO, NBTType.COMPOUND.getId()))
            {
                NBTTagCompound trayInfo = stack.getTagCompound().getCompoundTag(MUFFIN_TRAY_INFO);
                if (trayInfo.getTagList(ITEM_LIST, NBTType.COMPOUND.getId()).tagCount() > 0)
                    return "item.filled_muffin_tray";
            }
        return "item.empty_muffin_tray";
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
    {
        super.addInformation(stack, player, list, bool);

        this.addTrayInformation(stack, list);
    }

    public void addTrayInformation(ItemStack stack, List list)
    {
        Tray tray = new Tray(stack);
        for (int i = 0; i < tray.getMuffinCount(); i++)
        {
            ItemStack inSlot = tray.getInSlot(i);
            if (inSlot != null) list.add(" " + (i + 1) + ": " + inSlot.getDisplayName());
        }
    }

    public static class Tray
    {
        ItemStack[] stacks;

        public Tray(ItemStack input)
        {
            stacks = new ItemStack[6];
            if (!input.hasTagCompound()) input.setTagCompound(new NBTTagCompound());
            NBTTagCompound trayInfo = input.getTagCompound().getCompoundTag(MUFFIN_TRAY_INFO);
            NBTTagList itemList = trayInfo.getTagList(TileEntityMuffinTray.ITEM_LIST, 10);
            for (int i = 0; i < itemList.tagCount(); i++)
            {
                NBTTagCompound itemCompound = itemList.getCompoundTagAt(i);
                int slot = itemCompound.getInteger(TileEntityMuffinTray.ITEM_SLOT);
                ItemStack stack = ItemStack.loadItemStackFromNBT(itemCompound);
                stacks[slot] = stack;
            }
        }

        public int getMuffinCount()
        {
            return stacks.length;
        }

        public ItemStack getInSlot(int slot)
        {
            return stacks[slot];
        }
    }
}
