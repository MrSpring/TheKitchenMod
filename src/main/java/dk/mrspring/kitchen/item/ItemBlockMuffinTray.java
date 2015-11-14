package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.nbtjson.NBTType;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.List;

/**
 * Created on 14-11-2015 for TheKitchenMod.
 */
public class ItemBlockMuffinTray extends ItemBlock
{
    public static final String ITEM_LIST = "ItemList", ITEM_SLOT = "Slot";

    public static final String MUFFIN_TRAY_INFO = "MuffinTrayInfo";

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
            if (!input.hasTagCompound()) input.setTagCompound(new NBTTagCompound());
            NBTTagCompound trayInfo = input.getTagCompound().getCompoundTag(MUFFIN_TRAY_INFO);
            this.loadFrom(trayInfo);
        }

        public Tray(NBTTagCompound trayInfo)
        {
            this.loadFrom(trayInfo);
        }

        private void loadFrom(NBTTagCompound trayInfo)
        {
            stacks = new ItemStack[6];
            NBTTagList itemList = trayInfo.getTagList(ITEM_LIST, 10);
            for (int i = 0; i < itemList.tagCount(); i++)
            {
                NBTTagCompound itemCompound = itemList.getCompoundTagAt(i);
                int slot = itemCompound.getInteger(ITEM_SLOT);
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

        public void cook()
        {
            for (int i = 0; i < getMuffinCount(); i++)
            {
                ItemStack inSlot = getInSlot(i);
                if (inSlot != null)
                {
                    if (inSlot.getItem() == KitchenItems.raw_muffin)
                        inSlot.func_150996_a(KitchenItems.cooked_muffin);
                    else if (inSlot.getItem() == KitchenItems.cooked_muffin)
                        inSlot.func_150996_a(KitchenItems.burnt_muffin);
                    else inSlot = null;
                    setInSlot(i, inSlot);
                }
            }
        }

        public void setInSlot(int slot, ItemStack stack)
        {
            this.stacks[slot] = stack;
        }

        public void writeToStack(ItemStack stack)
        {
            NBTTagCompound trayInfo = new NBTTagCompound();
            this.writeTo(trayInfo);
            stack.setTagInfo(MUFFIN_TRAY_INFO, trayInfo);
        }

        public void writeTo(NBTTagCompound trayInfo)
        {
            NBTTagList itemList = new NBTTagList();
            for (int i = 0; i < stacks.length; i++)
            {
                ItemStack inSlot = getInSlot(i);
                if (inSlot != null)
                {
                    NBTTagCompound itemCompound = new NBTTagCompound();
                    inSlot.writeToNBT(itemCompound);
                    itemCompound.setInteger(ITEM_SLOT, i);
                    itemList.appendTag(itemCompound);
                }
            }
            trayInfo.setTag(ITEM_LIST, itemList);
        }

        public ItemStack[] getMuffins()
        {
            return stacks;
        }

        public void clearSlots()
        {
            for (int i = 0; i < stacks.length; i++)
                stacks[i] = null;
        }
    }
}
