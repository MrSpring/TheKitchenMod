package dk.mrspring.kitchen.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.KitchenItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

import java.util.List;

/**
 * Created on 04-12-2015 for TheKitchenMod.
 */
public class ItemManual extends ItemBase
{
    @SideOnly(Side.CLIENT)
    IIcon[] icons;

    public ItemManual()
    {
        super("manual", true);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        ItemStack manual = new ItemStack(item);
        ItemStack display = new ItemStack(KitchenBlocks.oven);
        manual.setTagInfo("ManualType", display.writeToNBT(new NBTTagCompound()));
        list.add(manual);
    }

    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public int getRenderPasses(int metadata)
    {
        return 2;
    }
}
