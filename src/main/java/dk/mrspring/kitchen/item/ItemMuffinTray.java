package dk.mrspring.kitchen.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.util.ItemUtils;
import dk.mrspring.nbtjson.NBTType;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;

/**
 * Created on 04-10-2015 for TheKitchenMod.
 */
public class ItemMuffinTray extends ItemBase
{
    public static final String MUFFINS = "MuffinList";

    public static String[] getMuffins(ItemStack stack)
    {
        String[] muffins = new String[stack.getItemDamage()];
        if (muffins.length == 0) return muffins;
        NBTTagList muffinList = ItemUtils.getListTag(stack, MUFFINS, NBTType.STRING);
        for (int i = 0; i < muffinList.tagCount() && i < muffins.length; i++)
            muffins[i] = muffinList.getStringTagAt(i);
        return muffins;
    }

    @SideOnly(Side.CLIENT)
    IIcon[] icons;

    public ItemMuffinTray(String prefix)
    {
        super(prefix + "muffin_tray", true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        super.registerIcons(register);
        icons = new IIcon[6];
        for (int i = 0; i < icons.length; i++) register.registerIcon(this.getIconString() + "_" + i);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public int getRenderPasses(int metadata)
    {
        return metadata > 0 ?
                metadata + 1 :
                1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int meta, int pass)
    {
        if (pass == 0) return super.getIconFromDamageForRenderPass(meta, pass);
        if (pass > 0 && pass <= meta) return icons[pass - 1];
        else return super.getIconFromDamageForRenderPass(meta, pass);
    }
}
